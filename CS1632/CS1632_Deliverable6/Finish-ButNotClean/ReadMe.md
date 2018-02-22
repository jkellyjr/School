To my lasting shame, I am unable to figure out how to pass command line arguments to a gradle script.

Therefore, while this project is in gradle, it cannot be run from it.

To Run:
cd run
(if you have not compiled) javac *.java
java RPN <files to read in>

To Test:
gradle test <whatever gradle args you want>

The tests and the running both work as I intend them to, I just had trouble with turning stuff I passed to gradle into stuff
gradle passed to main. Hopefully no points off for that, because gradle isn't part of the assignment. You can think of it as
a really fancy test runner in this context.
