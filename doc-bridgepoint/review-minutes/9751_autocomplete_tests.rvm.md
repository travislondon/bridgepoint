---

This work is licensed under the Creative Commons CC0 License

---

# Review implementation note for auto complete tests
### xtUML Project Review Minutes

Reviewed:  https://github.com/travislondon/bridgepoint/blob/9751_autocomplete_tests/doc-bridgepoint/notes/9751/9751_auto_complete_tests.md  
8e1b26e  
Present:  Travis, Bob, Levi

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  9750      Update https://github.com/xtuml/bptest/pull/53 for 9750 to include an implemenation note and reissue the pull. This introduces the matrix but no tests yet.
x- 02  min   skb  4.1      sugesstions
x- 03  min   skb  6.1      add reference to the test model
x- 04  min   skb  6.2      "In test implementation these are lines 26, 52 and 135."  There are more than 3, plus calling out line numbers is not helpful to the reader (lines will change). Everything is being tested under the "if block", not for or while. This should be described here. Call this out and raise follow-on issues as needed.
x- 05  min   skb  6.2      "This triggers the parser to run and create the necessary Proposal_c instances." There was confusing about this. It is invoked through the content assist. Describe this.
x- 06  maj   bob  6,2,2      The test run durations need to be called out. How we move forward with this test will require this information
x- 07  min   bob  5       For each of these 5 items include a short description to assist the reader. If the description can be found in the matrix tell the read and give them a link.
x- 08  min   bob  7      use numbers not bullets to help us talk about the specific items
x- 10  min   bob  7.1      describe what this means
x- 11  min   bob  6.1.1    Add a description that helps the reader understand this OAL. Travis described this in the meeting,
                           but that descrition is needed for the reader.
x- 12  min   bob  7      Use can raise follow-on issues as needed for 7.1 to 7.3 and descriptions for each here 
x- 13  maj   bob  ??      Define a mechanism that allows this test work to be promoted but has a way to leave these tests   disabled until the implentation is promoted. 

</pre>

Major observations were recorded, a re-review is required.


End
---
