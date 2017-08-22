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

9.1 TODO - Create Test Use cases  
9.2 TODO - Create Test Matrix  
9.2.1 TODO - Formal Review of the test matrix  
9.3 TODO - Implement tests for matrix  

### End
