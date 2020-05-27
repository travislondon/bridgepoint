package lib;

import java.util.ArrayList;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.PendingEvent_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.Timer_c;
import org.xtuml.bp.core.Util_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.util.OoaofooaUtil;

//========================================================================
//
//File:      $RCSfile: TIM.java,v $
//Version:   $Revision: 1.22 $
//Modified:  $Date: 2012/01/23 21:27:42 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

public class TIM {

  private static Thread realThread = null;
  private static Thread simThread = null;

  private static final int VIEWERS_REFRESH_RATE_MILLISECS = 1000;

  private static boolean SIM_TIME = false;

  private static ArrayList<Timer_c> timersList = new ArrayList<Timer_c>();
  private static ArrayList<ComponentInstance_c> eelist = new ArrayList<ComponentInstance_c>();
  private static ArrayList<Boolean> eeIdleList = new ArrayList<Boolean>();
  private static HashMap<Timer_c ,ComponentInstance_c> timersMap = new HashMap<Timer_c, ComponentInstance_c >();

  private static Object idleBusyLock = new Object();
  private static Object timerLock = new Object();

  private static boolean allIdle = true;
  private static boolean suspended = false;
  private static long simulatedTime;
  private static long suspendMark;
  private static long suspendTime = 0;
  private static boolean running = false;

  private static Instant systemEpoch = Instant.EPOCH;
  private static long systemEpochOffsetMicros = 0;

  // Adapted from solution at https://stackoverflow.com/a/38658066
  // Multiple sources say the Java 8 implementation of Clock only supports millisecond resolution
  // This is supposedly resolved in Java 9 and above.
  private static class NanoClock extends Clock {
    private final Clock clock;
    private final long initialNanos;
    private final Instant initialInstant;

    public NanoClock()
    {
        this(Clock.systemUTC());
    }

    public NanoClock(final Clock clock)
    {
        this.clock = clock;
        initialInstant = clock.instant();
        initialNanos = getSystemNanos();
    }

    @Override
    public ZoneId getZone()
    {
        return clock.getZone();
    }

    @Override
    public Instant instant()
    {
        return initialInstant.plusNanos(getSystemNanos() - initialNanos);
    }

    @Override
    public Clock withZone(final ZoneId zone)
    {
        return new NanoClock(clock.withZone(zone));
    }

    private long getSystemNanos()
    {
        return System.nanoTime();
    }
  }

  private static Clock epochClock = new NanoClock();
  
  /*
   * Initializes the real-time verifier timer thread
   */
  private static void initializeRealTime() {
    running = true;
    realThread = new Thread(new Runnable() {
      public void run() {
        long remTime;
        while (running) {
          try {
            synchronized(timerLock) {
              if (!timersList.isEmpty() && !suspended){
                remTime = timersList.get(0).getExpiration()- current_clock();
                if (remTime > VIEWERS_REFRESH_RATE_MILLISECS*1000)
                  timerLock.wait(VIEWERS_REFRESH_RATE_MILLISECS);
                else if (remTime > 0)
                  timerLock.wait(remTime/1000,(int)((remTime-(remTime/1000)*1000)*1000));
              }
              else {
                //Thread waiting until a timer gets added to the empty timers list
                timerLock.wait();
              }
            }

            if (!timersList.isEmpty() && !suspended){
              ComponentInstance_c ee = ComponentInstance_c.ComponentInstanceInstance(timersList.get(0).getModelRoot());
              if (ee != null){
                try {
                  ModelRoot.disableChangeNotification();
                  timersList.get(0).Tick();
                }
                catch (Exception e) {
                  CorePlugin.logError("Exception found during verifier timer execution.", e);
                }
                finally {
                  ModelRoot.enableChangeNotification();
                }
                //Refreshing viewers
                ee.Notify();
                // Signal single execution thread if needed.
                SystemModel_c system = OoaofooaUtil.getSystemForElement(ee);
                if (system != null) {
                  synchronized (system) {
                    system.notify();
                  }
                }

              }
            }
          } catch (InterruptedException e) {
            // Normal behavior
          }
        }

      }
    }, "Timer Clock");
    realThread.start();
  }

  /*
   * Initializes the simulated-time verifier timer thread
   */
  private static void initializeSimTime() {
    running = true;
    simThread = new Thread(new Runnable() {
      public void run() {
        while (running) {
          boolean eventsDelivered = false;
          if (allIdle && !timersList.isEmpty()) {
            eventsDelivered = TIM.performSimulatedTime();
            if (suspended || !eventsDelivered ) {
              synchronized (idleBusyLock) {
                try {
                  idleBusyLock.wait();
                } catch (InterruptedException e) {
                  // normal operation
                }
              }
            }
          }
        }
      }
    }, "Simulated Timer Clock");
    simThread.start();
  }

  public static void stopTimers() {
    running = false;
    try {
      if (simThread != null) {
        synchronized(idleBusyLock) {
          idleBusyLock.notify();
        }
        simThread.join();
        simThread = null;
      }
      if (realThread != null) {
        synchronized(timerLock) {
          timerLock.notify();
        }
        if(realThread != null) {
          realThread.join();
        }
        realThread = null;
      }
    } catch (InterruptedException e) {
      CorePlugin.logError("Unexpected Interrupted exception waiting for timers to stop", e);
    }
  }
  /**
   * This function advances the timers when using simulated time.
   *
   * @return true if time was advanced for a component instance and
   *         false if it was not.
   */
  public static boolean performSimulatedTime() {
    boolean eventDelivered = false;
    if (!suspended && !timersList.isEmpty()) {
      ComponentInstance_c ee = ComponentInstance_c
          .ComponentInstanceInstance(timersList.get(0).getModelRoot());
      if (ee != null) {
        try {
          ModelRoot.disableChangeNotification();
          simulatedTime = timersList.get(0).getExpiration();
          timersList.get(0).Tick();
          eventDelivered = true;
        } catch (Exception e) {
          CorePlugin.logError(
              "Exception found during verifier timer execution.",
              e);
        } finally {
          ModelRoot.enableChangeNotification();
        }
      }
    }
    return eventDelivered;
  }

  public static void addExecEngines(ComponentInstance_c ee){
    eelist.add(ee);
    eeIdleList.add(false);
  }

  /**
   * When we initialize a launch we support SimTime or
   * Clock, but not both at the same time.
   *
   * @param simTime
   */
  public static void init(boolean simTime){
    if (simTime) {
      if (simThread == null) {
        initializeSimTime();
      }
    } else {
      if (realThread == null)
        initializeRealTime();
    }
  }

  public static void terminate(ComponentInstance_c ee){
    suspended = false;
    allIdle = false;
    suspendTime = 0;
    synchronized (idleBusyLock) {
      if (eelist.contains(ee)) {
        eeIdleList.remove(eelist.indexOf(ee));
        eelist.remove(ee);
      }
    }
    ArrayList<Timer_c> timersToBeRemoved = new ArrayList<Timer_c>();
    synchronized (timerLock) {
      for (Entry<Timer_c, ComponentInstance_c> entry : timersMap
          .entrySet()) {
        if (entry.getValue().equals(ee)) {
          Timer_c timTemp = entry.getKey();
          timersList.remove(timTemp);
          timersToBeRemoved.add(timTemp);
        }
      }
      for (int i=0 ; i< timersToBeRemoved.size(); i++){
        timersMap.remove(timersToBeRemoved.get(i));
      }
    }
  }


  public static void busyNotification(ComponentInstance_c ee){
    synchronized (idleBusyLock){
      for (int i = 0; i < eelist.size(); i++){
        if (eelist.get(i).equals(ee)){
          eeIdleList.set(i, false);
          break;
        }
      }
      allIdle = false;
    }
  }

  public static void idleNotification(ComponentInstance_c ee){
    synchronized (idleBusyLock){
      for (int i = 0; i < eelist.size(); i++){
        if (eelist.get(i).equals(ee)){
          eeIdleList.set(i, true);
          break;
        }
      }

      allIdle = true;
      for (int i = 0; i < eeIdleList.size(); i++){
        allIdle &= eeIdleList.get(i);
      }
      if (eeIdleList.isEmpty())
        allIdle = false;

      if (allIdle){
        idleBusyLock.notify();
      }
    }
  }

  public static boolean isSIM_TIME() {
    return SIM_TIME;
  }

  public static void setSIM_TIME(boolean sim_time) {
    SIM_TIME = sim_time;
  }

  public static void suspendTime(){
    suspended = true;
    suspendMark = System.nanoTime()/1000;
  }

  public static void resumeTime(){
    if (suspended){
      suspendTime += System.nanoTime()/1000 - suspendMark;
      suspended = false;
      if (isSIM_TIME()){
        synchronized(idleBusyLock){
          idleBusyLock.notify();
        }
      }
      else {
        synchronized(timerLock){
          timerLock.notify();
        }
      }
    }
  }

  public static void insertTimer(Timer_c timer , ComponentInstance_c compInst){
    synchronized (timerLock) {
      if (timersList.isEmpty()){
        timersList.add(timer);
        timersMap.put(timer, compInst);
        if (!SIM_TIME)
          timerLock.notify();
        return;
      }
      for (int i = 0; i < timersList.size(); i++) {
        if (timersList.get(i) != null) {
          if(timer.getExpiration() < timersList.get(i).getExpiration()){
            timersList.add(i, timer);
            timersMap.put(timer, compInst);
            return;
          }
        }
      }
      timersList.add(timer);
      timersMap.put(timer, compInst);
    }
  }

  public static void removeTimer(Timer_c timer){
    synchronized (timerLock) {
      for (int i = 0; i < timersList.size(); i++) {
        if (timersList.get(i) != null) {
          if(timer.equals(timersList.get(i))){
            timersList.remove(i);
            timersMap.remove(timer);
            break;
          }
        }
      }
    }
  }

  public static long getCurrentTime(){
    if (SIM_TIME)
      return simulatedTime;
    else
      return (System.nanoTime()/1000 - suspendTime);
  }

  // ========================================================================
  // Bridge: timer_start
  // ========================================================================
  public static Object timer_start(Object event_inst, int microseconds) {
    if (event_inst instanceof PendingEvent_c) {
      PendingEvent_c pendingEvent = (PendingEvent_c) event_inst;
      // create object instance timer of I_TIM;
      Timer_c timer = new Timer_c(pendingEvent.getModelRoot());
      // timer.set(param.microseconds);
      timer.Set(microseconds);
      // relate timer to event_inst across R937;
      timer.relateAcrossR2940To(pendingEvent);
      timer.setRunning(true);

      insertTimer(timer,ComponentInstance_c.getOneI_EXEOnR2964(pendingEvent));

      return timer;
    }
    return null;
  }

  // ========================================================================
  // Bridge: timer_start_recurring
  // ========================================================================
  public static Object timer_start_recurring(Object event_inst,
      int microseconds) {
    Timer_c result = (Timer_c) timer_start(event_inst, microseconds);
    if (result != null) {
      result.setRecurring(true);
      return result;
    }
    return null;
  }

  // ========================================================================
  // Bridge: timer_cancel
  // ========================================================================
  public static boolean timer_cancel(Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      removeTimer(timer);
      timer.Dispose();
      return true;
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_remaining_time
  // ========================================================================
  public static int timer_remaining_time(Object timer_inst) {
    int left = 0;
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      left = (int)(timer.getExpiration() - getCurrentTime());
    }
    return left;
  }

  // ========================================================================
  // Bridge: timer_reset_time
  // ========================================================================
  public static boolean timer_reset_time(int microseconds, Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      ComponentInstance_c compInst = timersMap.get(timer);
      removeTimer(timer);
      timer.setExpiration(Util_c.Addinttolong(microseconds, getCurrentTime()));
      timer.setRunning(true);
      insertTimer(timer,compInst);
      return true; // successfully reset
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_add_time
  // ========================================================================
  public static boolean timer_add_time(int microseconds, Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      ComponentInstance_c compInst = timersMap.get(timer);
      removeTimer(timer);
      long newExpiration = 0;
      newExpiration = timer.getExpiration() + microseconds;
      timer.setExpiration(newExpiration);
      insertTimer(timer,compInst);
      return true; // successfully added time
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_pause
  // ========================================================================
  public static void timer_pause(Object timer_inst) {
    if (timer_inst instanceof Timer_c) {
      Timer_c timer = (Timer_c) timer_inst;
      timer.setRunning(false);
    }
  }

  // ========================================================================
  // Bridge: timer_restart
  // ========================================================================
  public static void timer_restart(Object timer_inst) {
    if (timer_inst instanceof Timer_c) {
      Timer_c timer = (Timer_c) timer_inst;
      timer.setRunning(true);
    }
  }

  // ========================================================================
  // Bridge: get_second
  // ========================================================================
  public static int get_second(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.SECOND_OF_DAY);
    }
    return -1;
  }

  // ========================================================================
  // Bridge: get_minute
  // ========================================================================
  public static int get_minute(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.MINUTE_OF_DAY);
    }
    return -1;
  }

  // ========================================================================
  // Bridge: get_hour
  // ========================================================================
  public static int get_hour(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.HOUR_OF_DAY);
    }
    return -1;
  }

  // ========================================================================
  // Bridge: get_day
  // ========================================================================
  public static int get_day(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.DAY_OF_MONTH);
    }
    return -1;
  }

  // ========================================================================
  // Bridge: get_month
  // ========================================================================
  public static int get_month(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.MONTH_OF_YEAR);
    }
    return -1; // cannot return 0, represents January
  }

  // ========================================================================
  // Bridge: get_year
  // ========================================================================
  public static int get_year(Object date) {
    if (date instanceof Instant){
      Instant cal = (Instant) date;
      return cal.get(ChronoField.YEAR);
    }
    return -1;
  }

  // ========================================================================
  // Bridge: current_clock
  // ========================================================================
  public static long current_clock() {
    long timeValue = (TimeUnit.SECONDS.toMicros(Instant.now().getEpochSecond()) + TimeUnit.NANOSECONDS.toMicros(Instant.now(epochClock).getNano()));
    if (SIM_TIME)
      timeValue = simulatedTime;
    else
      timeValue -= systemEpochOffsetMicros;
    return timeValue;
  }

  // ========================================================================
  // Bridge: create_date
  // ========================================================================
  public static Object create_date(int day, int hour, int min, int month,
      int sec, int year) {
    Instant cal = LocalDateTime.of(year, month, day, hour, min, sec).toInstant(ZoneOffset.UTC);
    cal.minusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffsetMicros));
    return (Object) cal;
  }

  // ========================================================================
  // Bridge: current_date
  // ========================================================================
  public static Object current_date() {
    Instant cal = Instant.now().minusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffsetMicros));
    return (Object) cal;
  }

  // ========================================================================
  // Bridge: current_seconds
  // ========================================================================
   public static long current_seconds() {
     if (SIM_TIME)
       return current_clock();
     else
       return Instant.now().getEpochSecond() - TimeUnit.MICROSECONDS.toSeconds(systemEpochOffsetMicros);
   }

  // ========================================================================
  // Bridge: set_epoch
  // ========================================================================
   public static void set_epoch(int day, int month, int year) {
     systemEpoch = LocalDate.of(year, month, day).atStartOfDay(ZoneOffset.UTC).toInstant();
     systemEpochOffsetMicros = TimeUnit.SECONDS.toMicros(systemEpoch.getEpochSecond() - Instant.EPOCH.getEpochSecond());
   }

  // ========================================================================
  // Bridge: time_of_day
  // ========================================================================
  public static long time_of_day(long timeval) {
    // Convert back to Instant.EPOCH value, before calling Java Time/Date functions.
    long nanoVal = TimeUnit.MICROSECONDS.toNanos(timeval) + TimeUnit.MICROSECONDS.toNanos(systemEpochOffsetMicros);
	  return LocalTime.from(Instant.ofEpochSecond(0, nanoVal).atZone(ZoneOffset.UTC)).getLong(ChronoField.MICRO_OF_DAY);
  }

  // ========================================================================
  // Bridge: timestamp_format
  // ========================================================================
  public static String timestamp_format(long ts, String format) {
    // Convert back to Instant.EPOCH value, before calling Java Time/Date functions.
    long nanoVal = TimeUnit.MICROSECONDS.toNanos(ts) + TimeUnit.MICROSECONDS.toNanos(systemEpochOffsetMicros);
	// Discard all '[' ']' '{' '}', as they throw an error.
	String stripped = format.replaceAll("\\[|\\]", " ").replaceAll("\\{|\\}", " ");
	if (!stripped.contentEquals(format)) {
		System.out.println("All [, {, ], and } characters were stripped from the output string." );
	}
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(0, nanoVal), ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(stripped));
  }

  // ========================================================================
  // Bridge: timestamp_to_string
  // ========================================================================
  public static String timestamp_to_string(long timestamp) {
    // Convert back to Instant.EPOCH value, before calling Java Time/Date functions.
    long nanoVal = TimeUnit.MICROSECONDS.toNanos(timestamp) + TimeUnit.MICROSECONDS.toNanos(systemEpochOffsetMicros);
    return Instant.ofEpochSecond(0, nanoVal).toString();
  }
  
  // ========================================================================
  // Bridge: set_time
   // TIM::set_time( year, month, day, hour, minute, second ) : timestamp
  // ========================================================================

  // ========================================================================
  // Bridge: advance_time
   // TIM::advance_time( seconds ) : timestamp
  // ========================================================================
}
