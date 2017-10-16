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

The model has the following Action Homes which are derived from the matrix:  

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

if(x == 0)

else

end if;
select many set from instances of Class1;
for each part in set

end for;
while(x < 0)

end while;

self.DerivedAttribute = 1;
</pre>

6.2 Test implementation  

The pre-created model is used to insert text at the Locations defined by the matrix and shared with the test model.  They are the locations that text may be inserted:  

* Before an enclosing block  
* In an enclosing block  
* After an enclosing block  

In test implementation these are lines 26, 52 and 135.  

The tests take the original text and these line numbers and create an IDocument.  That IDocument is modified with the line determined by the Location (L) value.  For instance L5 gives a value of "send Port1::".  After the expected text has been added the TextParser class is called just as if parsing while editing was enabled.  This triggers the parser to run and create the necessary Proposal_c instances.  

6.2.1 Results  

After the proposal list has been created it is checked for two things, exists or non-existent.  The Proposal_c classes have the necessary text to compare with the (P) entries.  Those were created manually based on the matrix.  

The result tests are simply as follows:  
```java
for(String actual : actualProposals) {
    // make sure every possibility is present
    for(String possibility : getPossibilities(getName())) {
        if(actual.equals(possibility)) {
            return true;
        }
    }
}
return false;
```

### 7. Design Comments

None.  

### 8. User Documentation

The only documentation is written in the matrix.  

### 9. Unit Test

See [[2.1]](#2.3).   

### End
