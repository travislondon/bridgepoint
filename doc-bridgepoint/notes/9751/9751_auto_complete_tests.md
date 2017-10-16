---

This work is licensed under the Creative Commons CC0 License

---

# Implement OAL editor auto completion tests
### xtUML Project Design Note


### 1. Abstract

This note describes the approach to testing OAL auto completion.   

### 2. Document References
 
<a id="2.1"></a>2.1 [BridgePoint DEI #9751](https://support.onefact.net/issues/9751) Using test matrix implement tests for oal context sensitive help.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9750](https://support.onefact.net/issues/9750) Develop test matrix for oal autocompletion.  
<a id="2.3"></a>2.3 [BridgePoint DEI #9749](https://support.onefact.net/issues/9749) Determine use cases for OAL autocompletion.  
<a id="2.4"></a>2.4 [oal_autocomplete](https://github.com/travislondon/models/tree/9751_autocomplete_tests/test/oal_autocomplete) Test model based on matrix.  

### 3. Background

Issues 9749 and 9750 determined and developed a test matrix.  This issue will implement those tests.   

### 4. Requirements

4.1 Implement tests that verify existence of auto complete sugesstions    

4.2 Implement tests that verify non-existence of auto complete suggestions   

### 5. Analysis

There are five degrees of freedom.  

* Locations (L)  
* Possibilities (P)    
* Action Homes (AH)    
* Scope (S)  
* Visibility (V)  

The matrix is designed to test a text entry in a possible location, and test what possibility should be there.  

### 6. Design

6.1 Setup  

A new test model has been created.  It contains all action homes to be tested underneath a container component.  This model is loaded once before testing.  Additionally, this model is parsed during setup.  

6.1.1 Model creation  

The model referenced in 2.4 has the following Action Homes which are derived from the matrix:  

* State Action Body  
* Derived Attribute Body  
* Function Body  
* Operation Body  
* Bridge Body  
* Provided Operation Body  
* Provided Signal Body  
* Required Operation Body  
* Required Signal Body  
* Transition Action Body  

The following OAL is used in all action homes:  

<pre> 
x = 0;
select any l2_var from instances of L2Class;
select any l8_var_one from instances of L8Class;
select any l8_var_two from instances of L8Class_two;
create object instance l11_var of L11Class;
create event instance l11_inst_event of L11Class1:event to l11_var;
select many l13_vars from instances of L2Class;
create object instance l16_var of L8Class;
create object instance l16_var_2 of L8Class_two;
create object instance l17_var of L8Class;
create object instance l17_var_2 of L8Class_two;
create object instance l18_var of L8Class;
create object instance l18_var_2 of L8Class_two;
create object instance l19_var of L19;
create object instance l19_var_other of L19_other;
create object instance l19_var_link of L19_link;
create object instance l20_var of L8Class;
create object instance l20_var_2 of L8Class_two;
create object instance l21_var of L8Class;
create object instance l21_var_2 of L8Class_two;
create object instance l22_var of L8Class;
create object instance l22_var_2 of L8Class_two;
create object instance l23_var of L19;
create object instance l23_other of L19_other;
create object instance l23_link of L19_link;
// insert test oal
if(x == 0)
select any l2_var_if from instances of L2Class;
select any l8_var_one_if from instances of L8Class;
select any l8_var_two_if from instances of L8Class_two;
create object instance l11_var of L11Class;
create event instance l11_inst_event_if of L11Class1:event to l11_var;
select many l13_vars_if from instances of L2Class;
create object instance l16_var_if of L8Class;
create object instance l16_var_2_if of L8Class_two;
create object instance l17_var_if of L8Class;
create object instance l17_var_2_if of L8Class_two;
create object instance l18_var_if of L8Class;
create object instance l18_var_2_if of L8Class_two;
create object instance l19_var_if of L19;
create object instance l19_var_other_if of L19_other;
create object instance l19_var_link_if of L19_link;
create object instance l20_var_if of L8Class;
create object instance l20_var_2_if of L8Class_two;
create object instance l21_var_if of L8Class;
create object instance l21_var_2_if of L8Class_two;
create object instance l22_var_if of L8Class;
create object instance l22_var_2_if of L8Class_two;
create object instance l23_var_if of L19;
create object instance l23_other_if of L19_other;
create object instance l23_link_if of L19_link;
// insert test oal
else
select any l2_var_else from instances of L2Class;
select any l8_var_one_else from instances of L8Class;
select any l8_var_two_else from instances of L8Class_two;
create object instance l11_var_else of L11Class;
create event instance l11_inst_event_else of L11Class1:event to l11_var;
select many l13_vars from instances of L2Class;
create object instance l16_var_else of L8Class;
create object instance l16_var_2_else of L8Class_two;
create object instance l17_var_else of L8Class;
create object instance l17_var_2_else of L8Class_two;
create object instance l18_var_else of L8Class;
create object instance l18_var_2_else of L8Class_two;
create object instance l19_var_else of L19;
create object instance l19_var_other_else of L19_other;
create object instance l19_var_link_else of L19_link;
create object instance l20_var_else of L8Class;
create object instance l20_var_2_else of L8Class_two;
create object instance l21_var_else of L8Class;
create object instance l21_var_2_else of L8Class_two;
create object instance l22_var_else of L8Class;
create object instance l22_var_2_else of L8Class_two;
create object instance l23_var_else of L19;
create object instance l23_othe_elser of L19_other;
create object instance l23_link_else of L19_link;
// insert test oal
end if;
select many set from instances of Class1;
for each part in set
select any l2_var_for from instances of L2Class;
select any l8_var_one_for from instances of L8Class;
select any l8_var_two_for from instances of L8Class_two;
create object instance l11_var_for of L11Class;
create event instance l11_inst_event_for of L11Class1:event to l11_var;
select many l13_vars_for from instances of L2Class;
create object instance l16_var_for of L8Class;
create object instance l16_var_2_for of L8Class_two;
create object instance l17_var_for of L8Class;
create object instance l17_var_2_for of L8Class_two;
create object instance l18_var_for of L8Class;
create object instance l18_var_2_for of L8Class_two;
create object instance l19_var_for of L19;
create object instance l19_var_other_for of L19_other;
create object instance l19_var_link_for of L19_link;
create object instance l20_var_for of L8Class;
create object instance l20_var_2_for of L8Class_two;
create object instance l21_var_for of L8Class;
create object instance l21_var_2_for of L8Class_two;
create object instance l22_var_for of L8Class;
create object instance l22_var_2_for of L8Class_two;
create object instance l23_var_for of L19;
create object instance l23_other_for of L19_other;
create object instance l23_link_for of L19_link;
// insert test oal
end for;
while(x < 0)
select any l2_var_while from instances of L2Class;
select any l8_var_one_while from instances of L8Class;
select any l8_var_two_while from instances of L8Class_two;
create object instance l11_var_while of L11Class;
create event instance l11_inst_event_while of L11Class1:event to l11_var;
select many l13_vars_while from instances of L2Class;
create object instance l16_var_while of L8Class;
create object instance l16_var_2_while of L8Class_two;
create object instance l17_var_while of L8Class;
create object instance l17_var_2_while of L8Class_two;
create object instance l18_var_while of L8Class;
create object instance l18_var_2_while of L8Class_two;
create object instance l19_va_whiler of L19;
create object instance l19_var_other_while of L19_other;
create object instance l19_var_link_while of L19_link;
create object instance l20_var_while of L8Class;
create object instance l20_var_2_while of L8Class_two;
create object instance l21_var_while of L8Class;
create object instance l21_var_2_while of L8Class_two;
create object instance l22_var_while of L8Class;
create object instance l22_var_2_while of L8Class_two;
create object instance l23_var_while of L19;
create object instance l23_other_while of L19_other;
create object instance l23_link_while of L19_link;
// insert test oal
end while;
// insert test oal
</pre>

6.2 Test implementation  

The pre-created model is used to insert text at the Locations defined by the matrix and shared with the test model.  These are the locations that text may be inserted:  

* Before an enclosing block  
* In an enclosing block  
* After an enclosing block  

In test implementation these are lines 26, 52 and 135.  

The tests take the original text and these line numbers and create an IDocument.  That IDocument is modified with the line determined by the Location (L) value.  For instance L5 gives a value of "send Port1::".  After the expected text has been added the TextParser class is called just as if parsing while editing was enabled.  This triggers the parser to run and create the necessary Proposal_c instances.  

6.2.1 Test before implemenation  

This work is intended to be completed prior to implementation.  There are two possible results in the original test matrix:  

* doesExist (in proposals)  
* doesNotExist (in proposals)  

A test first approach in both cases will result in all tests failing.  The doesNotExist tests check the result by using a combination of Class.forName() and java reflection.  If the implementation class Proposal_c does not exist the test is failed.  Without this approach the implementation would be required to compile.  

6.2.2 Results  

After the proposal list has been created it is checked for two things, doesExist or doesNotExist according the results defined in the matrix.  The Proposal_c classes have the necessary text to compare with the (P) entries defined in the test.  Those were created manually based on the matrix and test model.  

The result tests are as follows:  
```java
String[] possibilities = getPossibilities(getName());
for(String actual : actualProposals) {
    // make sure every possibility is present
    for(String possibility : possibilities) {
        if(actual.equals(possibility)) {
            return true;
        }
    }
}
return false;
```

### 7. Design Comments

A good deal of work has been done combing through the current failures.  What is being found is that there are four categories of issue:  

* Test matrix result entries are incorrect  
* Test main parent class is not checking things correctly  
* Test model imcomplete  
* Issues in actual auto complete implementation  

At this point the two most likely to still have issues are with the test matrix results not matching reality and actual implementation issues.  

Taking into consideration that this work is to take a test first approach, failures of the test matrix results and actual implementation shall be ignored.  Once the tests are promoted to master the branch for the actual implementation may then use the tests working through these types of issues.  

### 8. User Documentation

The only documentation is written in the matrix.  

### 9. Unit Test

See [[2.1]](#2.3).   

### End
