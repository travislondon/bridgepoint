---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced OAL Editor (phase 1)
### xtUML Project Design Note

### 1. Abstract

This note provdes the design for phase 1 of the Enhanced OAL editor project. 

### 2. Document References

<a id="2.1"></a>2.1 [#9571 Enhanced OAL Editor analysis note (phase 1)](9571_oal_xtext_editor_option2_ant.md)  
<a id="2.2"></a>2.2 [#9415 Enahnced OAL Editor SRS](https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit)  
<a id="2.3"></a>2.3 [#9718 Testing for Enhanced OAL Editor phase 1](https://support.onefact.net/issues/9718)  
<a id="2.4"></a>2.4 [Code Completion Using Antrl](http://www.soft-gems.net/index.php/tools/47-universal-code-completion-using-antlr)  


### 3. Background

The background for this work is fully described in this issue's analaysis note [[2.1]](#2.1). The reader should read that note. However, be aware that you may encounter places that refer to this task as "OAL Xtext Editor" instead of "Enhanced OAL Editor". This is simply an artifact of the mindset that was initially in the analyst's mind when the orginal analysis of this task was performed. In the final analysis the order in which the full project's requirements are being fullfilled was changed. This resulted in an observeration that Xtext was desirable, but not required. After that observation the note names were changed acccordingly to remove the reference to Xtext. The note contents/identifiers were not changed, just the name of the notes.

### 4. Requirements

The following requirements are copied from the analaysis note [[2.1]](#2.1) which in turn came from the SRS [[2.2]](#2.2). They are carried forward for convience. There are no changes from the SRS.  

| ID  | Description                                                                                                                                                  |
|:----|:-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| AE1 | OAL keywords are highlighted when editing an OAL activity.                                                                                                   |
| AE2 | When editing an OAL activity each user-defined identifier within the activity is validated to ensure it is legal within the context in which it is used.  Invalid identifiers are marked, and a message explaining the error is provided. |
| AE3 | Context-sensitive completion assistance for user-defined identifiers is provided while editing OAL activities.                                               |
| AE5 | Problem markers shall be created in the problems view for errors OAL editors present in the activity editor.                                                 |
| AE6 | Opening a problem marker in the problems view shall open the activity editor and position the cursor at the error.                                           |
| AE8 | When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance. |
| AE9 | When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.                                      |



### 5. Analysis

See the analaysis note [[2.1]](#2.1).  
The analysis note's work required section is written based on an implementation that focuses on enhancing the existing BridgePoint code base to add all functionality. While that approach is being used, in this section we are going to perform some additional analysis that seeks to take advange of BridgePoint's use of Antrl to perform the functionality required by the requirements. 

5.1 Antr and BrigePoint  
Explaination BridgePoint's current use of Antrl  
TODO  
5.1.1 Modifying the current Antrl Grammar to support  
TODO: Link to old project and explanation of where it stands  
That project was for a multi-buffer editor. It is likely not helpful for this project. It is further observered that we should not have to modify the grammar for this project because we and not including signatures. This project extends the current editor behavior.  

5.1.1 Antrl code completetion    
Code Completetion [[2.4]](#2.4)  
5.1.2 Versions of Antr  
TODO: What version should we use and why    
Note that there is an old note about this. We should also ask Jan.  

5.1.3 Extending the bridge between Antlr and ooaofooa  
OAL Validation Utility Functions to leverage Antrl code completion  
5.1.3.1  

5.2 Looking up declarations from selected instance (Requirements AE8 and AE9)  
This functionality shall be added by extending the current BridgePoint search infrastructure.  
5.2.1 Extend search to allow declaration and reference searching.  
This shall allow the search infrastructure to search by name, and by id.  
5.2.2 References shall be located in OAL  
5.2.3 Declarations shall go to where appropriate (diagram, tree if not on the diagram).  
5.2.4 Add an OAL editor declaration search for local variables.    



### 6. Design  

TODO:
Examine the work required in the analaysis closer and capture information from it in the approproate sections below.

6.1  Update BridgePoint to use Antrl v<TODO ??>  
TODO  
<pre>
I started some experiments with simple antrl 4 command-completion. Here are 
current notes about how I did this:
0. There are a couple small, simple test projects here:
   https://github.com/rmulvey/sandbox/tree/master/BobsAntrl4TestProject
   https://github.com/rmulvey/sandbox/tree/master/BobsAntrl4TestProject2-SimpleCodeCompletion

1. References
1.1 Help Setting up Antrl 4 https://stackoverflow.com/questions/30128961/trouble-setting-up-antlr-4-ide-on-eclipse-luna-4-4
1.2 HOWTO Implement Antrl4 autocomplete https://blog.logentries.com/2015/06/how-to-implement-antlr4-autocomplete/


2. Setting up a simple Antrl 4 test from the BP Dev installation:

2.1. Install Marketplace Client
2.1.1 Add Mars update site
     http://download.eclipse.org/releases/mars/
2.1.2 Search for "marketplace" and install the client
2.1.3 Install it
2.2. install Antrl 4
2.2.1 From Help > Eclipse Marketplace...
2.2.2 search for "antrl"
2.2.3 Install Antlr 4 IDE
    Note: This installs the IDE and Antrl 4
2.3. Install Faceted Project Framework
2.3.1 Help > Install New Software...
2.3.2 Use the mars update site
2.3.3 type "facet"
2.3.4 Select Eclipse Faceted Project Framework and Eclipse Faceted Project Framework JDT Enablement
2.4 Download antlr-4.x-complete.jar from http://www.antlr.org/download.html and copy it to the Eclipse installation's plugin folder
2.4. Follow "Creating an ANTRL 4 Project" from 1.1 above

</pre>

6.2 Looking up declarations from selected instance (Requirements AE8 and AE9)  
This shall be a stand-alone project  

6.3 Testing  
This shall be a stand alone prject (see section 9) 


### 7. Design Comments

none

### 8. User Documentation

TODO: Describe the end user documentation that was added for this change. 

### 9. Unit Test

Test first design shall be used in this project. As such, a seperate issue has been raised for 
testing [[2.3]](#2.3). [[2.3]](#2.3) is a blocker to promotion of this work. 

9.1 Action homes  
9.1.1 Action Home: State Action Body
9.1.1.1 For Stmt
9.1.1.2 While Stmt
9.1.1.3 If Stmt
9.1.1.4 ElseIf Stmt
9.1.1.5 Else Stmt
9.1.1.6 Bridge Invocation
9.1.1.7 Function Invocation
9.1.1.8 Return Stmt
9.1.1.9 Operation Invocation
9.1.1.10 Assign to Member
9.1.1.11 Delete
9.1.1.12 Create No Variable
9.1.1.13 Create
9.1.1.13 Select Related By
9.1.1.14 Select From Instances
9.1.1.15 Select From Instances Where
9.1.1.16 Unrelate Using
9.1.1.17 Unrelate
9.1.1.18 Relate Using
9.1.1.19 Relate
9.1.1.20 Control
9.1.1.21 Break
9.1.1.22 Continue
9.1.1.22 Generate to Creator
9.1.1.23 Generate Preexisting Event
9.1.1.24 Interface Operation Invocation
9.1.1.25 Signal Invocation
9.1.1.26 Instance Handle
9.1.1.27 Instance Set
9.1.1.28 Transient Var:Multiplicity
9.1.1.29 Transient Var:Justification
9.1.1.30 Transient Var:Style
9.1.1.31 Transient Var:End
9.1.1.32 Transient Var:ParseStatus
9.1.1.33 Transient Var:Scope
9.1.1.34 Transient Var:ModelEventNotification
9.1.1.35 Transient Var:Visibility
9.1.1.36 Transient Var:instance
9.1.1.37 Transient Var:Token
9.1.1.38 Transient Var:OalConstants
9.1.1.39 Transient Var:RunStateType
9.1.1.40 Transient Var:Breakpoint_Type
9.1.1.41 Transient Var:StateChangeType
9.1.1.42 Transient Var:EventProcessType
9.1.1.43 Transient Var:IFDirectionType
9.1.1.44 Transient Var:StateEnum
9.1.1.45 Transient Var:ReentrantLock
9.1.1.46 Transient Var:ElementTypeConstants
9.1.1.47 Transient Var:long
9.1.1.48 Transient Var:SearchScope
9.1.1.49 Transient Var:SynchronizationType
9.1.1.50 Transient Var:Severity
9.1.1.51 Transient Var:ActionDialect
9.1.1.52 Transient Var:void
9.1.1.53 Transient Var:boolean
9.1.1.54 Transient Var:integer
9.1.1.55 Transient Var:real
9.1.1.56 Transient Var:string
9.1.1.57 Transient Var:unique_id
9.1.1.58 Transient Var:state<State_Model>
9.1.1.59 Transient Var:same_as<Base_Attribute>
9.1.1.60 Transient Var:inst_ref<Object>
9.1.1.61 Transient Var:inst_ref_set<Object>
9.1.1.62 Transient Var:inst<Event>
9.1.1.63 Transient Var:inst<Mapping>
9.1.1.64 Transient Var:inst_ref<Mapping>
9.1.1.65 Transient Var:component_ref
9.1.1.66 Transient Var:date
9.1.1.67 Transient Var:inst_ref<Timer>
9.1.1.68 Transient Var:timestamp
9.1.1.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.1.70 self is invalid in this action home
9.1.2 Action Home: Derived Attribute Body
9.1.2.1 For Stmt
9.1.2.2 While Stmt
9.1.2.3 If Stmt
9.1.2.4 ElseIf Stmt
9.1.2.5 Else Stmt
9.1.2.6 Bridge Invocation
9.1.2.7 Function Invocation
9.1.2.8 Return Stmt
9.1.2.9 Operation Invocation
9.1.2.10 Assign to Member
9.1.2.11 Delete
9.1.2.12 Create No Variable
9.1.2.13 Create
9.1.2.13 Select Related By
9.1.2.14 Select From Instances
9.1.2.15 Select From Instances Where
9.1.2.16 Unrelate Using
9.1.2.17 Unrelate
9.1.2.18 Relate Using
9.1.2.19 Relate
9.1.2.20 Control
9.1.2.21 Break
9.1.2.22 Continue
9.1.2.22 Generate to Creator
9.1.2.23 Generate Preexisting Event
9.1.2.24 Interface Operation Invocation
9.1.2.25 Signal Invocation
9.1.2.26 Instance Handle
9.1.2.27 Instance Set
9.1.2.28 Transient Var:Multiplicity
9.1.2.29 Transient Var:Justification
9.1.2.30 Transient Var:Style
9.1.2.31 Transient Var:End
9.1.2.32 Transient Var:ParseStatus
9.1.2.33 Transient Var:Scope
9.1.2.34 Transient Var:ModelEventNotification
9.1.2.35 Transient Var:Visibility
9.1.2.36 Transient Var:instance
9.1.2.37 Transient Var:Token
9.1.2.38 Transient Var:OalConstants
9.1.2.39 Transient Var:RunStateType
9.1.2.40 Transient Var:Breakpoint_Type
9.1.2.41 Transient Var:StateChangeType
9.1.2.42 Transient Var:EventProcessType
9.1.2.43 Transient Var:IFDirectionType
9.1.2.44 Transient Var:StateEnum
9.1.2.45 Transient Var:ReentrantLock
9.1.2.46 Transient Var:ElementTypeConstants
9.1.2.47 Transient Var:long
9.1.2.48 Transient Var:SearchScope
9.1.2.49 Transient Var:SynchronizationType
9.1.2.50 Transient Var:Severity
9.1.2.51 Transient Var:ActionDialect
9.1.2.52 Transient Var:void
9.1.2.53 Transient Var:boolean
9.1.2.54 Transient Var:integer
9.1.2.55 Transient Var:real
9.1.2.56 Transient Var:string
9.1.2.57 Transient Var:unique_id
9.1.2.58 Transient Var:state<State_Model>
9.1.2.59 Transient Var:same_as<Base_Attribute>
9.1.2.60 Transient Var:inst_ref<Object>
9.1.2.61 Transient Var:inst_ref_set<Object>
9.1.2.62 Transient Var:inst<Event>
9.1.2.63 Transient Var:inst<Mapping>
9.1.2.64 Transient Var:inst_ref<Mapping>
9.1.2.65 Transient Var:component_ref
9.1.2.66 Transient Var:date
9.1.2.67 Transient Var:inst_ref<Timer>
9.1.2.68 Transient Var:timestamp
9.1.2.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.2.70 self is valid in this action home.
9.1.3 Action Home: Function Body
9.1.3.1 For Stmt
9.1.3.2 While Stmt
9.1.3.3 If Stmt
9.1.3.4 ElseIf Stmt
9.1.3.5 Else Stmt
9.1.3.6 Bridge Invocation
9.1.3.7 Function Invocation
9.1.3.8 Return Stmt
9.1.3.9 Operation Invocation
9.1.3.10 Assign to Member
9.1.3.11 Delete
9.1.3.12 Create No Variable
9.1.3.13 Create
9.1.3.13 Select Related By
9.1.3.14 Select From Instances
9.1.3.15 Select From Instances Where
9.1.3.16 Unrelate Using
9.1.3.17 Unrelate
9.1.3.18 Relate Using
9.1.3.19 Relate
9.1.3.20 Control
9.1.3.21 Break
9.1.3.22 Continue
9.1.3.22 Generate to Creator
9.1.3.23 Generate Preexisting Event
9.1.3.24 Interface Operation Invocation
9.1.3.25 Signal Invocation
9.1.3.26 Instance Handle
9.1.3.27 Instance Set
9.1.3.28 Transient Var:Multiplicity
9.1.3.29 Transient Var:Justification
9.1.3.30 Transient Var:Style
9.1.3.31 Transient Var:End
9.1.3.32 Transient Var:ParseStatus
9.1.3.33 Transient Var:Scope
9.1.3.34 Transient Var:ModelEventNotification
9.1.3.35 Transient Var:Visibility
9.1.3.36 Transient Var:instance
9.1.3.37 Transient Var:Token
9.1.3.38 Transient Var:OalConstants
9.1.3.39 Transient Var:RunStateType
9.1.3.40 Transient Var:Breakpoint_Type
9.1.3.41 Transient Var:StateChangeType
9.1.3.42 Transient Var:EventProcessType
9.1.3.43 Transient Var:IFDirectionType
9.1.3.44 Transient Var:StateEnum
9.1.3.45 Transient Var:ReentrantLock
9.1.3.46 Transient Var:ElementTypeConstants
9.1.3.47 Transient Var:long
9.1.3.48 Transient Var:SearchScope
9.1.3.49 Transient Var:SynchronizationType
9.1.3.50 Transient Var:Severity
9.1.3.51 Transient Var:ActionDialect
9.1.3.52 Transient Var:void
9.1.3.53 Transient Var:boolean
9.1.3.54 Transient Var:integer
9.1.3.55 Transient Var:real
9.1.3.56 Transient Var:string
9.1.3.57 Transient Var:unique_id
9.1.3.58 Transient Var:state<State_Model>
9.1.3.59 Transient Var:same_as<Base_Attribute>
9.1.3.60 Transient Var:inst_ref<Object>
9.1.3.61 Transient Var:inst_ref_set<Object>
9.1.3.62 Transient Var:inst<Event>
9.1.3.63 Transient Var:inst<Mapping>
9.1.3.64 Transient Var:inst_ref<Mapping>
9.1.3.65 Transient Var:component_ref
9.1.3.66 Transient Var:date
9.1.3.67 Transient Var:inst_ref<Timer>
9.1.3.68 Transient Var:timestamp
9.1.3.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.3.70 self is invalid in this action home
9.1.4 Action Home: Operation Body
9.1.4.1 For Stmt
9.1.4.2 While Stmt
9.1.4.3 If Stmt
9.1.4.4 ElseIf Stmt
9.1.4.5 Else Stmt
9.1.4.6 Bridge Invocation
9.1.4.7 Function Invocation
9.1.4.8 Return Stmt
9.1.4.9 Operation Invocation
9.1.4.10 Assign to Member
9.1.4.11 Delete
9.1.4.12 Create No Variable
9.1.4.13 Create
9.1.4.13 Select Related By
9.1.4.14 Select From Instances
9.1.4.15 Select From Instances Where
9.1.4.16 Unrelate Using
9.1.4.17 Unrelate
9.1.4.18 Relate Using
9.1.4.19 Relate
9.1.4.20 Control
9.1.4.21 Break
9.1.4.22 Continue
9.1.4.22 Generate to Creator
9.1.4.23 Generate Preexisting Event
9.1.4.24 Interface Operation Invocation
9.1.4.25 Signal Invocation
9.1.4.26 Instance Handle
9.1.4.27 Instance Set
9.1.4.28 Transient Var:Multiplicity
9.1.4.29 Transient Var:Justification
9.1.4.30 Transient Var:Style
9.1.4.31 Transient Var:End
9.1.4.32 Transient Var:ParseStatus
9.1.4.33 Transient Var:Scope
9.1.4.34 Transient Var:ModelEventNotification
9.1.4.35 Transient Var:Visibility
9.1.4.36 Transient Var:instance
9.1.4.37 Transient Var:Token
9.1.4.38 Transient Var:OalConstants
9.1.4.39 Transient Var:RunStateType
9.1.4.40 Transient Var:Breakpoint_Type
9.1.4.41 Transient Var:StateChangeType
9.1.4.42 Transient Var:EventProcessType
9.1.4.43 Transient Var:IFDirectionType
9.1.4.44 Transient Var:StateEnum
9.1.4.45 Transient Var:ReentrantLock
9.1.4.46 Transient Var:ElementTypeConstants
9.1.4.47 Transient Var:long
9.1.4.48 Transient Var:SearchScope
9.1.4.49 Transient Var:SynchronizationType
9.1.4.50 Transient Var:Severity
9.1.4.51 Transient Var:ActionDialect
9.1.4.52 Transient Var:void
9.1.4.53 Transient Var:boolean
9.1.4.54 Transient Var:integer
9.1.4.55 Transient Var:real
9.1.4.56 Transient Var:string
9.1.4.57 Transient Var:unique_id
9.1.4.58 Transient Var:state<State_Model>
9.1.4.59 Transient Var:same_as<Base_Attribute>
9.1.4.60 Transient Var:inst_ref<Object>
9.1.4.61 Transient Var:inst_ref_set<Object>
9.1.4.62 Transient Var:inst<Event>
9.1.4.63 Transient Var:inst<Mapping>
9.1.4.64 Transient Var:inst_ref<Mapping>
9.1.4.65 Transient Var:component_ref
9.1.4.66 Transient Var:date
9.1.4.67 Transient Var:inst_ref<Timer>
9.1.4.68 Transient Var:timestamp
9.1.4.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.4.70 self is valid in this action home, unless the operation is class based.
9.1.5 Action Home: Bridge Body
9.1.5.1 For Stmt
9.1.5.2 While Stmt
9.1.5.3 If Stmt
9.1.5.4 ElseIf Stmt
9.1.5.5 Else Stmt
9.1.5.6 Bridge Invocation
9.1.5.7 Function Invocation
9.1.5.8 Return Stmt
9.1.5.9 Operation Invocation
9.1.5.10 Assign to Member
9.1.5.11 Delete
9.1.5.12 Create No Variable
9.1.5.13 Create
9.1.5.13 Select Related By
9.1.5.14 Select From Instances
9.1.5.15 Select From Instances Where
9.1.5.16 Unrelate Using
9.1.5.17 Unrelate
9.1.5.18 Relate Using
9.1.5.19 Relate
9.1.5.20 Control
9.1.5.21 Break
9.1.5.22 Continue
9.1.5.22 Generate to Creator
9.1.5.23 Generate Preexisting Event
9.1.5.24 Interface Operation Invocation
9.1.5.25 Signal Invocation
9.1.5.26 Instance Handle
9.1.5.27 Instance Set
9.1.5.28 Transient Var:Multiplicity
9.1.5.29 Transient Var:Justification
9.1.5.30 Transient Var:Style
9.1.5.31 Transient Var:End
9.1.5.32 Transient Var:ParseStatus
9.1.5.33 Transient Var:Scope
9.1.5.34 Transient Var:ModelEventNotification
9.1.5.35 Transient Var:Visibility
9.1.5.36 Transient Var:instance
9.1.5.37 Transient Var:Token
9.1.5.38 Transient Var:OalConstants
9.1.5.39 Transient Var:RunStateType
9.1.5.40 Transient Var:Breakpoint_Type
9.1.5.41 Transient Var:StateChangeType
9.1.5.42 Transient Var:EventProcessType
9.1.5.43 Transient Var:IFDirectionType
9.1.5.44 Transient Var:StateEnum
9.1.5.45 Transient Var:ReentrantLock
9.1.5.46 Transient Var:ElementTypeConstants
9.1.5.47 Transient Var:long
9.1.5.48 Transient Var:SearchScope
9.1.5.49 Transient Var:SynchronizationType
9.1.5.50 Transient Var:Severity
9.1.5.51 Transient Var:ActionDialect
9.1.5.52 Transient Var:void
9.1.5.53 Transient Var:boolean
9.1.5.54 Transient Var:integer
9.1.5.55 Transient Var:real
9.1.5.56 Transient Var:string
9.1.5.57 Transient Var:unique_id
9.1.5.58 Transient Var:state<State_Model>
9.1.5.59 Transient Var:same_as<Base_Attribute>
9.1.5.60 Transient Var:inst_ref<Object>
9.1.5.61 Transient Var:inst_ref_set<Object>
9.1.5.62 Transient Var:inst<Event>
9.1.5.63 Transient Var:inst<Mapping>
9.1.5.64 Transient Var:inst_ref<Mapping>
9.1.5.65 Transient Var:component_ref
9.1.5.66 Transient Var:date
9.1.5.67 Transient Var:inst_ref<Timer>
9.1.5.68 Transient Var:timestamp
9.1.5.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.5.70 self is invalid in this action home
9.1.6 Action Home: Provided Operation Body
9.1.6.1 For Stmt
9.1.6.2 While Stmt
9.1.6.3 If Stmt
9.1.6.4 ElseIf Stmt
9.1.6.5 Else Stmt
9.1.6.6 Bridge Invocation
9.1.6.7 Function Invocation
9.1.6.8 Return Stmt
9.1.6.9 Operation Invocation
9.1.6.10 Assign to Member
9.1.6.11 Delete
9.1.6.12 Create No Variable
9.1.6.13 Create
9.1.6.13 Select Related By
9.1.6.14 Select From Instances
9.1.6.15 Select From Instances Where
9.1.6.16 Unrelate Using
9.1.6.17 Unrelate
9.1.6.18 Relate Using
9.1.6.19 Relate
9.1.6.20 Control
9.1.6.21 Break
9.1.6.22 Continue
9.1.6.22 Generate to Creator
9.1.6.23 Generate Preexisting Event
9.1.6.24 Interface Operation Invocation
9.1.6.25 Signal Invocation
9.1.6.26 Instance Handle
9.1.6.27 Instance Set
9.1.6.28 Transient Var:Multiplicity
9.1.6.29 Transient Var:Justification
9.1.6.30 Transient Var:Style
9.1.6.31 Transient Var:End
9.1.6.32 Transient Var:ParseStatus
9.1.6.33 Transient Var:Scope
9.1.6.34 Transient Var:ModelEventNotification
9.1.6.35 Transient Var:Visibility
9.1.6.36 Transient Var:instance
9.1.6.37 Transient Var:Token
9.1.6.38 Transient Var:OalConstants
9.1.6.39 Transient Var:RunStateType
9.1.6.40 Transient Var:Breakpoint_Type
9.1.6.41 Transient Var:StateChangeType
9.1.6.42 Transient Var:EventProcessType
9.1.6.43 Transient Var:IFDirectionType
9.1.6.44 Transient Var:StateEnum
9.1.6.45 Transient Var:ReentrantLock
9.1.6.46 Transient Var:ElementTypeConstants
9.1.6.47 Transient Var:long
9.1.6.48 Transient Var:SearchScope
9.1.6.49 Transient Var:SynchronizationType
9.1.6.50 Transient Var:Severity
9.1.6.51 Transient Var:ActionDialect
9.1.6.52 Transient Var:void
9.1.6.53 Transient Var:boolean
9.1.6.54 Transient Var:integer
9.1.6.55 Transient Var:real
9.1.6.56 Transient Var:string
9.1.6.57 Transient Var:unique_id
9.1.6.58 Transient Var:state<State_Model>
9.1.6.59 Transient Var:same_as<Base_Attribute>
9.1.6.60 Transient Var:inst_ref<Object>
9.1.6.61 Transient Var:inst_ref_set<Object>
9.1.6.62 Transient Var:inst<Event>
9.1.6.63 Transient Var:inst<Mapping>
9.1.6.64 Transient Var:inst_ref<Mapping>
9.1.6.65 Transient Var:component_ref
9.1.6.66 Transient Var:date
9.1.6.67 Transient Var:inst_ref<Timer>
9.1.6.68 Transient Var:timestamp
9.1.6.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.6.70 self is invalid in this action home
9.1.7 Action Home: Provided Signal Body
9.1.7.1 For Stmt
9.1.7.2 While Stmt
9.1.7.3 If Stmt
9.1.7.4 ElseIf Stmt
9.1.7.5 Else Stmt
9.1.7.6 Bridge Invocation
9.1.7.7 Function Invocation
9.1.7.8 Return Stmt
9.1.7.9 Operation Invocation
9.1.7.10 Assign to Member
9.1.7.11 Delete
9.1.7.12 Create No Variable
9.1.7.13 Create
9.1.7.13 Select Related By
9.1.7.14 Select From Instances
9.1.7.15 Select From Instances Where
9.1.7.16 Unrelate Using
9.1.7.17 Unrelate
9.1.7.18 Relate Using
9.1.7.19 Relate
9.1.7.20 Control
9.1.7.21 Break
9.1.7.22 Continue
9.1.7.22 Generate to Creator
9.1.7.23 Generate Preexisting Event
9.1.7.24 Interface Operation Invocation
9.1.7.25 Signal Invocation
9.1.7.26 Instance Handle
9.1.7.27 Instance Set
9.1.7.28 Transient Var:Multiplicity
9.1.7.29 Transient Var:Justification
9.1.7.30 Transient Var:Style
9.1.7.31 Transient Var:End
9.1.7.32 Transient Var:ParseStatus
9.1.7.33 Transient Var:Scope
9.1.7.34 Transient Var:ModelEventNotification
9.1.7.35 Transient Var:Visibility
9.1.7.36 Transient Var:instance
9.1.7.37 Transient Var:Token
9.1.7.38 Transient Var:OalConstants
9.1.7.39 Transient Var:RunStateType
9.1.7.40 Transient Var:Breakpoint_Type
9.1.7.41 Transient Var:StateChangeType
9.1.7.42 Transient Var:EventProcessType
9.1.7.43 Transient Var:IFDirectionType
9.1.7.44 Transient Var:StateEnum
9.1.7.45 Transient Var:ReentrantLock
9.1.7.46 Transient Var:ElementTypeConstants
9.1.7.47 Transient Var:long
9.1.7.48 Transient Var:SearchScope
9.1.7.49 Transient Var:SynchronizationType
9.1.7.50 Transient Var:Severity
9.1.7.51 Transient Var:ActionDialect
9.1.7.52 Transient Var:void
9.1.7.53 Transient Var:boolean
9.1.7.54 Transient Var:integer
9.1.7.55 Transient Var:real
9.1.7.56 Transient Var:string
9.1.7.57 Transient Var:unique_id
9.1.7.58 Transient Var:state<State_Model>
9.1.7.59 Transient Var:same_as<Base_Attribute>
9.1.7.60 Transient Var:inst_ref<Object>
9.1.7.61 Transient Var:inst_ref_set<Object>
9.1.7.62 Transient Var:inst<Event>
9.1.7.63 Transient Var:inst<Mapping>
9.1.7.64 Transient Var:inst_ref<Mapping>
9.1.7.65 Transient Var:component_ref
9.1.7.66 Transient Var:date
9.1.7.67 Transient Var:inst_ref<Timer>
9.1.7.68 Transient Var:timestamp
9.1.7.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.7.70 self is invalid in this action home
9.1.8 Action Home: Required Operation Body
9.1.8.1 For Stmt
9.1.8.2 While Stmt
9.1.8.3 If Stmt
9.1.8.4 ElseIf Stmt
9.1.8.5 Else Stmt
9.1.8.6 Bridge Invocation
9.1.8.7 Function Invocation
9.1.8.8 Return Stmt
9.1.8.9 Operation Invocation
9.1.8.10 Assign to Member
9.1.8.11 Delete
9.1.8.12 Create No Variable
9.1.8.13 Create
9.1.8.13 Select Related By
9.1.8.14 Select From Instances
9.1.8.15 Select From Instances Where
9.1.8.16 Unrelate Using
9.1.8.17 Unrelate
9.1.8.18 Relate Using
9.1.8.19 Relate
9.1.8.20 Control
9.1.8.21 Break
9.1.8.22 Continue
9.1.8.22 Generate to Creator
9.1.8.23 Generate Preexisting Event
9.1.8.24 Interface Operation Invocation
9.1.8.25 Signal Invocation
9.1.8.26 Instance Handle
9.1.8.27 Instance Set
9.1.8.28 Transient Var:Multiplicity
9.1.8.29 Transient Var:Justification
9.1.8.30 Transient Var:Style
9.1.8.31 Transient Var:End
9.1.8.32 Transient Var:ParseStatus
9.1.8.33 Transient Var:Scope
9.1.8.34 Transient Var:ModelEventNotification
9.1.8.35 Transient Var:Visibility
9.1.8.36 Transient Var:instance
9.1.8.37 Transient Var:Token
9.1.8.38 Transient Var:OalConstants
9.1.8.39 Transient Var:RunStateType
9.1.8.40 Transient Var:Breakpoint_Type
9.1.8.41 Transient Var:StateChangeType
9.1.8.42 Transient Var:EventProcessType
9.1.8.43 Transient Var:IFDirectionType
9.1.8.44 Transient Var:StateEnum
9.1.8.45 Transient Var:ReentrantLock
9.1.8.46 Transient Var:ElementTypeConstants
9.1.8.47 Transient Var:long
9.1.8.48 Transient Var:SearchScope
9.1.8.49 Transient Var:SynchronizationType
9.1.8.50 Transient Var:Severity
9.1.8.51 Transient Var:ActionDialect
9.1.8.52 Transient Var:void
9.1.8.53 Transient Var:boolean
9.1.8.54 Transient Var:integer
9.1.8.55 Transient Var:real
9.1.8.56 Transient Var:string
9.1.8.57 Transient Var:unique_id
9.1.8.58 Transient Var:state<State_Model>
9.1.8.59 Transient Var:same_as<Base_Attribute>
9.1.8.60 Transient Var:inst_ref<Object>
9.1.8.61 Transient Var:inst_ref_set<Object>
9.1.8.62 Transient Var:inst<Event>
9.1.8.63 Transient Var:inst<Mapping>
9.1.8.64 Transient Var:inst_ref<Mapping>
9.1.8.65 Transient Var:component_ref
9.1.8.66 Transient Var:date
9.1.8.67 Transient Var:inst_ref<Timer>
9.1.8.68 Transient Var:timestamp
9.1.8.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.8.70 self is invalid in this action home
9.1.9 Action Home: Required Signal Body
9.1.9.1 For Stmt
9.1.9.2 While Stmt
9.1.9.3 If Stmt
9.1.9.4 ElseIf Stmt
9.1.9.5 Else Stmt
9.1.9.6 Bridge Invocation
9.1.9.7 Function Invocation
9.1.9.8 Return Stmt
9.1.9.9 Operation Invocation
9.1.9.10 Assign to Member
9.1.9.11 Delete
9.1.9.12 Create No Variable
9.1.9.13 Create
9.1.9.13 Select Related By
9.1.9.14 Select From Instances
9.1.9.15 Select From Instances Where
9.1.9.16 Unrelate Using
9.1.9.17 Unrelate
9.1.9.18 Relate Using
9.1.9.19 Relate
9.1.9.20 Control
9.1.9.21 Break
9.1.9.22 Continue
9.1.9.22 Generate to Creator
9.1.9.23 Generate Preexisting Event
9.1.9.24 Interface Operation Invocation
9.1.9.25 Signal Invocation
9.1.9.26 Instance Handle
9.1.9.27 Instance Set
9.1.9.28 Transient Var:Multiplicity
9.1.9.29 Transient Var:Justification
9.1.9.30 Transient Var:Style
9.1.9.31 Transient Var:End
9.1.9.32 Transient Var:ParseStatus
9.1.9.33 Transient Var:Scope
9.1.9.34 Transient Var:ModelEventNotification
9.1.9.35 Transient Var:Visibility
9.1.9.36 Transient Var:instance
9.1.9.37 Transient Var:Token
9.1.9.38 Transient Var:OalConstants
9.1.9.39 Transient Var:RunStateType
9.1.9.40 Transient Var:Breakpoint_Type
9.1.9.41 Transient Var:StateChangeType
9.1.9.42 Transient Var:EventProcessType
9.1.9.43 Transient Var:IFDirectionType
9.1.9.44 Transient Var:StateEnum
9.1.9.45 Transient Var:ReentrantLock
9.1.9.46 Transient Var:ElementTypeConstants
9.1.9.47 Transient Var:long
9.1.9.48 Transient Var:SearchScope
9.1.9.49 Transient Var:SynchronizationType
9.1.9.50 Transient Var:Severity
9.1.9.51 Transient Var:ActionDialect
9.1.9.52 Transient Var:void
9.1.9.53 Transient Var:boolean
9.1.9.54 Transient Var:integer
9.1.9.55 Transient Var:real
9.1.9.56 Transient Var:string
9.1.9.57 Transient Var:unique_id
9.1.9.58 Transient Var:state<State_Model>
9.1.9.59 Transient Var:same_as<Base_Attribute>
9.1.9.60 Transient Var:inst_ref<Object>
9.1.9.61 Transient Var:inst_ref_set<Object>
9.1.9.62 Transient Var:inst<Event>
9.1.9.63 Transient Var:inst<Mapping>
9.1.9.64 Transient Var:inst_ref<Mapping>
9.1.9.65 Transient Var:component_ref
9.1.9.66 Transient Var:date
9.1.9.67 Transient Var:inst_ref<Timer>
9.1.9.68 Transient Var:timestamp
9.1.9.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.9.70 self is invalid in this action home
9.1.10 Action Home: Transition Action Body
9.1.10.1 For Stmt
9.1.10.2 While Stmt
9.1.10.3 If Stmt
9.1.10.4 ElseIf Stmt
9.1.10.5 Else Stmt
9.1.10.6 Bridge Invocation
9.1.10.7 Function Invocation
9.1.10.8 Return Stmt
9.1.10.9 Operation Invocation
9.1.10.10 Assign to Member
9.1.10.11 Delete
9.1.10.12 Create No Variable
9.1.10.13 Create
9.1.10.13 Select Related By
9.1.10.14 Select From Instances
9.1.10.15 Select From Instances Where
9.1.10.16 Unrelate Using
9.1.10.17 Unrelate
9.1.10.18 Relate Using
9.1.10.19 Relate
9.1.10.20 Control
9.1.10.21 Break
9.1.10.22 Continue
9.1.10.22 Generate to Creator
9.1.10.23 Generate Preexisting Event
9.1.10.24 Interface Operation Invocation
9.1.10.25 Signal Invocation
9.1.10.26 Instance Handle
9.1.10.27 Instance Set
9.1.10.28 Transient Var:Multiplicity
9.1.10.29 Transient Var:Justification
9.1.10.30 Transient Var:Style
9.1.10.31 Transient Var:End
9.1.10.32 Transient Var:ParseStatus
9.1.10.33 Transient Var:Scope
9.1.10.34 Transient Var:ModelEventNotification
9.1.10.35 Transient Var:Visibility
9.1.10.36 Transient Var:instance
9.1.10.37 Transient Var:Token
9.1.10.38 Transient Var:OalConstants
9.1.10.39 Transient Var:RunStateType
9.1.10.40 Transient Var:Breakpoint_Type
9.1.10.41 Transient Var:StateChangeType
9.1.10.42 Transient Var:EventProcessType
9.1.10.43 Transient Var:IFDirectionType
9.1.10.44 Transient Var:StateEnum
9.1.10.45 Transient Var:ReentrantLock
9.1.10.46 Transient Var:ElementTypeConstants
9.1.10.47 Transient Var:long
9.1.10.48 Transient Var:SearchScope
9.1.10.49 Transient Var:SynchronizationType
9.1.10.50 Transient Var:Severity
9.1.10.51 Transient Var:ActionDialect
9.1.10.52 Transient Var:void
9.1.10.53 Transient Var:boolean
9.1.10.54 Transient Var:integer
9.1.10.55 Transient Var:real
9.1.10.56 Transient Var:string
9.1.10.57 Transient Var:unique_id
9.1.10.58 Transient Var:state<State_Model>
9.1.10.59 Transient Var:same_as<Base_Attribute>
9.1.10.60 Transient Var:inst_ref<Object>
9.1.10.61 Transient Var:inst_ref_set<Object>
9.1.10.62 Transient Var:inst<Event>
9.1.10.63 Transient Var:inst<Mapping>
9.1.10.64 Transient Var:inst_ref<Mapping>
9.1.10.65 Transient Var:component_ref
9.1.10.66 Transient Var:date
9.1.10.67 Transient Var:inst_ref<Timer>
9.1.10.68 Transient Var:timestamp
9.1.10.69 All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:

sel shows select and any class that starts with sel
f shows for and any class that starts with f

9.1.10.70 self is invalid in this action home
 
9.2 TODO - Create Test Matrix  
9.2.1 TODO - Formal Review of the test matrix  
9.3 TODO - Implement tests for matrix  

### End
