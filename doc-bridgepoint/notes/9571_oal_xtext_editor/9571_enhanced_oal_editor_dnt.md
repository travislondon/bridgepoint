---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced OAL Editor (phase 1)
### xtUML Project Design Note

### 1. Abstract

This note provdes the design for phase 1 of the Enhanced OAL editor project. 

### 2. Document References

<a id="2.1"></a>2.1 [#9571 Enhanced OAL Editor analysis note (phase 1)](9571_oal_xtext_editor_iption2_ant.md)  
<a id="2.2"></a>2.2 [#9415 Enahnced OAL Editor SRS](https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit)  
<a id="2.3"></a>2.3 [#9718 Testing for Enhanced OAL Editor phase 1](https://support.onefact.net/issues/9718)  

### 3. Background

The background for this work is fully described in this issue's analaysis note [[2.1]](#2.1). The reader should 
read that note. However, be aware that you may encounter places that refer to this task as "OAL Xtext Editor"
instead of "Enhanced OAL Editor". This is simply an artifact of the mindset that was initially in the 
analyst's mind when the orginal analysis of this task was performed. In the final analysis the order in which  
the full project's requirements are being fullfilled was changed. This resulted in an observeration that Xtext 
was desirable, but not required. After that observation the note names were changed acccordingly to 
remove the reference to Xtext. The note contents/identifiers were not changed, just the name of the notes.

### 4. Requirements

See the analaysis note [[2.1]](#2.1). 

### 5. Analysis

See the analaysis note [[2.1]](#2.1). 

### 6. Design

TODO:
In this section, describe in detail each step of the Work Required section of
the analysis, how the task will be accomplished, what technologies will
be used, algorithms, etc. Here is an example reference to the Document References section [[2.1]](#2.1)

6.1 Item 1  
```java
    // java code example
    public void clearDatabase(IProgressMonitor pm) 
    {
        // clear the corresponding graphics-root's database
        OoaofgraphicsUtil.clearGraphicsDatabase(rootId, pm);

        Ooaofooa.getDefaultInstance().fireModelElementUnloaded(this);
    }
```
6.1.1 Example sub-item
* Example List Element

6.2 Item 2  
6.2.1 Example sub-item
* Example List Element

### 7. Design Comments

none

### 8. User Documentation

TODO: Describe the end user documentation that was added for this change. 

### 9. Unit Test

Test first design is being used in this project. As such, a seperate issue has been raised for 
testing [[2.3]](#2.3). [[2.3]](#2.3) is a blocker to promotion of this work.  

### End
