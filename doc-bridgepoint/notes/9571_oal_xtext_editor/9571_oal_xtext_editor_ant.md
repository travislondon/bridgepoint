---

This work is licensed under the Creative Commons CC0 License

---

# OAL Xtext Editor
### xtUML Project Analysis Note

### 1. Abstract

The MASL Xtext editor has been a successful project. We now would like to
analyze an enhanced OAL editor using Xtext.

This note serves to analyze the OAL Xtext editor from the perspective of
providing a plan for a "first step". This note will recommend a subset of the
requirements to implement in phase one and provide analysis for them.

### 2. Document References

<a id="2.1"></a>2.1 [#9571 Provide an analysis for OAL Xtext Editor](https://support.onefact.net/issues/9571)  
<a id="2.2"></a>2.2 [OAL Xtext Editor SRS](https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit)  
<a id="2.3"></a>2.3 [#8417 design note for persistence as files](../8417_action_dialect_files/8417_action_dialect_files.dnt.md)  
<a id="2.4"></a>2.4 [#8417 implementation note for persistence as files](../8417_action_dialect_files/8417_action_dialect_files.int.md)  
<a id="2.5"></a>2.5 [#8887 implementation note for cleaning up remaining persistence issues](../8887_persistence_cleanup.int.md)  
<a id="2.6"></a>2.6 [#8917 Fully support persistence as activity files for OAL](https://support.onefact.net/issues/8917)  
<a id="2.7"></a>2.7 [#8941 design note for import parser improvements](../8941_parser_safety/8941_parser_safety_dnt.md)  
<a id="2.8"></a>2.8 [#9041 design note for preventing persistence of unsupported MASL activities](../9041_activities/9041_activities_dnt.md)  
<a id="2.9"></a>2.9 [#9057 analysis note on synchronization of activities](../9057_signature_editing/9057_signature_editing_ant.md)  
<a id="2.10"></a>2.10 [#9416 analysis note of enhanced type system](../9416_type_system/9416_type_system_ant.md)  

### 3. Background

An enhanced BridgePoint OAL editor will provide a richer user experience with a
goal of increasing engineering productivity. Many of these tooling capabilities
are currently provided in BridgePoint as part of an advanced, MASL-aware
activity editor. The MASL editor shown in the figure below supports syntax
highlighting, context-sensitive editing assistance, and real-time semantic
validation. References to structural in the action language is refactored upon
name changes and deletions.

![editor1.png](editor1.png)

3.1 Terminology

Where necessary the usage of certain terms is clarified in this section.  
* eclipse project, xtUML project - Implemented as a folder on the filesystem, a
  project can hold any number of xtUML model elements.  
* MASL - a Shlaer-Mellor dialect action language and structural modeling
  language supported by BridgePoint.  
* Action Home - For the purpose of this document, an Action Home may hold many
  action bodies. Examples of action homes are classes, state machines,
  interface instances, and packages. For example, a class may have many
  operations, a state machine may have many states, a package may have many
  functions.  
* Action Body - For the purpose of this document, an Action Body represents a
  single entity that calls or may be called. For example: a single function, a
  single operation, or a single event.  
* CME - Context Menu Entry  
* Structural Element - A model consists of data, control, and processing. A
  structural element is the data and control part of the model. For example,
  classes, attributes, interfaces and state machines are structural elements.
  The OAL bnf does not include structural elements.  

### 4. Requirements

Sourced from the SRS [[2.2]](#2.2)

4.1 All BridgePoint action homes shall support the functionality described by
the other requirements of this document.  
4.2 Textual OAL shall be persisted into `.oal` files.  
4.3 Each `.oal` file shall be associated with its action home.  
4.4 Each `.oal` file shall be persisted beside the `.xtuml` file that
represents the action home.  
4.5 Rename refactor  
4.5.1 When a structural element is renamed in Model Explorer, referring
instances shall be updated with the new name.  
4.5.2 When a structural element is renamed on the Canvas, referring instances
shall be updated with the new name.  
4.6 Activity editor  
4.6.1 OAL keywords are highlighted when editing an OAL activity.  
4.6.2 When editing an OAL activity each user-defined identifier within the
activity is validated to ensure it is legal within the context in which it is
used. Invalid identifiers are marked, and a message explaining the error is
provided.  
4.6.3 Context-sensitive completion assistance for user-defined identifiers is
provided while editing OAL activities.  
4.6.4 The editor shall link to the Model Explorer element being edited when
Eclipse “link with editor” is enabled.  
4.6.5 Problem markers shall be created in the problems view for errors OAL
editors present in the activity editor.  
4.6.6 Opening a problem marker in the problems view shall open the activity
editor and position the cursor at the error.  
4.6.7 Signatures shall be viewable in the activity editor. This includes all
action bodies that have a signature. Examples: function, operation, event, etc.  
4.6.8 When a variable representing an OAL instance is selected in the editor, a
CME shall be present that allows the user to find the declaration of the
instance.  
4.6.9 When a declaration is found using Find Declaration, the user shall be
able to select it to navigate to the declaration.  
4.6.10 When a variable representing an OAL instance is selected in the editor,
a CME shall be present that allows the user to find all instances of that
signature that are used in the project.  
4.6.11 When an instance is found using Find Instances, the user shall be able
to select it to navigate to the instance.  
4.7 A single body editor shall not be implemented.  
4.8 Multi-body editing  
4.8.1 All action bodies associated with an action home shall be present and
editable.  
4.8.2 User shall be able to delete one or many action bodies at once.  
4.8.3 User shall be able to add an action body by manually typing into the
activity editor.  
4.8.4 User shall be able to add one or many action bodies by pasting them into
the activity editor.  
4.9 Signature editing  
4.9.1 Users shall be able to edit the signature name.  
4.9.2 Users shall be able to add parameters to a signature.  
4.9.3 Users shall be able to delete parameters from a signature.  
4.9.4 Signature changes made in the editor shall be reflected in Model
Explorer.  
4.10 Every requirement shall have an associated unit test that shall validate
its functionality.  

### 5. Analysis

5.1 Overview

The requirements of this project can be categorized into two basic categories:
architectural requirements and editor features. The architectural requirements
influence the uderlying way that the editor functions. For example, persistence
in `.oal` files, signature editing, problem markers, etc. are all architectural
requriements. Editor features are requirements that do not change the essential
way the editor functions, but add additional assistance on top. For example,
rename refactoring, linking between model explorer and the editor,
context-sensitive completion, etc. are all editor features.

Most (if not all) of the architectural requirements must be completed in a
single phase. Attempting to split up architectural requirements across phases
may cause the editor to be practically unuseable or produce a great deal of
"throw away" engineering effort to implement a partial solution.

5.2 Phase one requirements

Below is a possible subset of the overall requirements to be tackled in phase
one. The theme will be creating a strong base on which to build up the required
editor features in future phases. The deliverable will be a functional and
useful OAL editor, but will not be rich with features.

> 4.1 All BridgePoint action homes shall support the functionality described by
> the other requirements of this document.  
> 4.2 Textual OAL shall be persisted into `.oal` files.  
> 4.3 Each `.oal` file shall be associated with its action home.  
> 4.4 Each `.oal` file shall be persisted beside the `.xtuml` file that
> represents the action home.  
> 4.6 Activity editor  
> 4.6.1 OAL keywords are highlighted when editing an OAL activity.  
> 4.6.2 When editing an OAL activity each user-defined identifier within the
> activity is validated to ensure it is legal within the context in which it is
> used. Invalid identifiers are marked, and a message explaining the error is
> provided.  
> 4.6.5 Problem markers shall be created in the problems view for errors OAL
> editors present in the activity editor.  
> 4.6.6 Opening a problem marker in the problems view shall open the activity
> editor and position the cursor at the error.  
> 4.6.7 Signatures shall be viewable in the activity editor. This includes all
> action bodies that have a signature. Examples: function, operation, event, etc.  
> 4.7 A single body editor shall not be implemented.  
> 4.8 Multi-body editing  
> 4.8.1 All action bodies associated with an action home shall be present and
> editable.  
> 4.8.2 User shall be able to delete one or many action bodies at once.  
> 4.8.3 User shall be able to add an action body by manually typing into the
> activity editor.  
> 4.8.4 User shall be able to add one or many action bodies by pasting them into
> the activity editor.  
> 4.9 Signature editing  
> 4.9.1 Users shall be able to edit the signature name.  
> 4.9.2 Users shall be able to add parameters to a signature.  
> 4.9.3 Users shall be able to delete parameters from a signature.  
> 4.9.4 Signature changes made in the editor shall be reflected in Model
> Explorer.  
> 4.10 Every requirement shall have an associated unit test that shall validate
> its functionality.  

5.2.1 Summary

This subset of requirements can be summarized by three basic technical
requirements:  
* OAL shall be persisted in `.oal` files  
* `.oal` files shall be the source for the structural elements representing
  signatures  
* A grammar shall be produced which parses `.oal` files and provides
  validation equal to the current OAL editor  

"Going whole hog" on architectural requirements in a single phase prevents work
from being done which is not necessary if all of the architectural requirements
are met. There are two good examples of this in the previous MASL work:

5.2.1.1 Snippet editor

While implementing the MASL Xtext editor, the began to be persisted in text
rather than in the SQL insert statements, however all of the instances that
represent signatures (data types, parameters, dimensions, etc.) were still
persisted in SQL. Because of this decision, it was dangerous to edit the
multi-body files which contained the action language. To solve this problem a
"snippet" editor for MASL was introduced which could edit a single body at a
time. If the signatures were persisted fully as text, this snippet editor could
be completely avoided. With respect to this work, we would like to avoid
unnecessarily creating an "OAL snippet editor" because the requirement of the
project is to provide a multi-body editor only.

5.2.1.2 Problem markers

Eclipse problem markers are generally associated with the file in which they
originate. Because of the problem above, MASL problems were required to open the
snippet editor for a problem associated with a `.masl` file. This required extra
work and wasn't the natural way. Again, we would be able to avoid this if the
persisted `.oal` file is the file that is edited by a user (using a multi-body
editor).

5.2.1.3 Conclusion

Tackling all of the architectural requirements in phase one will save the
engineering team time and headache. It will "frontload" the project with
technical challenges with tangible features coming more in a second phase. This
is the approach that will be taken. The rest of the analysis section of this
note will focus on the technical challenges of the bulleted list in section
5.2.1

5.3 Persistence

A good deal of work has already been done in the area of persisting action
language in separate files, distinguished by dialect. MASL have already been
using this capability.

Before continuing, the reader should briefly familiarize himself with the work
that has been done by reading the design note [[2.3]](#2.3) and the code cleanup
implementation note [[2.5]](#2.5).

More reading on work in this area can be found at [[2.7]](#2.7) and
[[2.8]](#2.8)

5.3.1 Extending/completing textual persistence of actions

An issue has already been raised for this piece of the work [[2.6]](#2.6). In
the comments on the issue, remaining tasks have been noted.

MASL has fewer types of action bodies than xtUML/OAL. Transition activities,
derived attributes, and bridge operations must have an associated signature
added to the grammar.  Additionally, MASL does not allow any activity to exist
outside a MASL domain or project, therefore each activity can be fully qualified
with the name of the containing project or domain. Since xtUML allows activities
to reside outside components, a signature with a non-existent or generic domain
identifier must be added to the grammar.

Testing and documentation has not been done for the OAL side of persistence and
must be considered as part of this work.

5.4 Text is king

The phrase "text is king" represents the largest (and most technically risky)
part of this analysis. Before continuing the reader should absorb the argument
in [[2.9]](#2.9), paying special attention to sections 1, 3, and 5.1.

5.4.1 Signature export

Signatures are already generated from the in memory model for most types of
activities (see section 5.3.1). Very little work needs to be done to export
structural signatures as text in `.oal` files.

5.4.2 Signature import

At the moment, signatures are parsed and compared with existing activities in
the in-memory xtUML model. If a match is found, the action body is inserted into
the `Action_Semantics` field of the in-memory model. New infrastructure will
need to be put into place to create and relate instances associated with textual
signatures upon parse of a `.oal` file. It is possible that this could be
incorporated into the current OAL parser. Another approach would be to directly
integrate it into the import framework. This second approach may be more useful
down the road when/if the MASL editor is moved to a "text is king" approach.

5.4.1 Type system

An enhanced type system with scoping of types and identification of types by
name is a prerequisite to this work. [[2.10]](#2.10) analyzes a plan for a new
type system. The entirety of that plan is not necessary for this work, but for
signatures to identify types, types must be referrable by name and not UUID
only. A concept of type reference must be introduced and scoping rules must be
established.

5.5 Editor grammar

5.5.1 Current OAL grammar

Although to completely fulfill the requirements it is necessary to implement the
OAL grammar in Xtext, however, if it is easy to leverage the current OAL parser
and editor for phase one without working in the wrong direction, this may help
lighten the load of phase one.

5.5.1 Xtext

### 6. Work Required

### 7. Acceptance Test

### End