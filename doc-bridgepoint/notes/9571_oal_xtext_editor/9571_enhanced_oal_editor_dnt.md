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

### 3. Background

The background for this work is fully described in this issue's analaysis note [[2.1]](#2.1). The reader should 
read that note. However, be aware that you may encounter places that refer to this task as "OAL Xtext Editor"
instead of "Enhanced OAL Editor". This is simply an artifact of the mindset that was initially in the 
analyst's mind when the orginal analysis of this task was performed. In the final analysis the order in which  
the full project's requirements are being fullfilled was changed. This resulted in an observeration that Xtext 
was desirable, but not required. After that observation the note names were changed acccordingly to 
remove the reference to Xtext. The note contents/identifiers were not changed, just the name of the notes.

### 4. Requirements

The following requirements are copied from the analaysis note [[2.1]](#2.1) which in turn came from the SRS [[2.2]](#2.2). They are carried forward for convience. There are no changes from the SRS.  
| ID | Description                                                                                                        |
|:---|:-------------------------------------------------------------------------------------------------------------------|
| B1 | All BridgePoint action homes shall support the functionality described by the other requirements of this document. |

4.2 Activity editor requirements

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

5.1 Explaination BridgePoint's current use of Antrl  
TODO  
5.2 Features available in Antrl that can be leveraged  


### 6. Design

6.1  Update BridgePoint to use Antrl v<TODO ??>  
TODO  

6.2  Modify grammar OAL Validation Functions  
Without as small a change as possible to the existing OAL Validation Utility functions introduce changes that leverage the Antrl command-completion against the OAL instance poputation.


6.3 
TODO:
In this section, describe in detail each step of the Work Required section of
the analysis, how the task will be accomplished, what technologies will
be used, algorithms, etc. Here is an example reference to the Document References section [[2.1]](#2.1)


### 7. Design Comments

none

### 8. User Documentation

TODO: Describe the end user documentation that was added for this change. 

### 9. Unit Test

Test first design shall be used in this project. As such, a seperate issue has been raised for 
testing [[2.3]](#2.3). [[2.3]](#2.3) is a blocker to promotion of this work. 

9.1 TODO - Create Test Use cases  
9.2 TODO - Create Test Matrix  
9.2.1 TODO - Formal Review of the test matrix  
9.3 TODO - Implement tests for matrix  

### End
