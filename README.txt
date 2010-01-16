OTC Trade Parser

TODO:
 - add licence
 - check library licences

This is a small experiment to see how effectively OTC trade information can be parsed using Prolog DCGs.

The Prolog engine used is jtrolog which is written in Java, and allows simpler pre-processing of the sample files.

To install jtrolog under Linux, it needs to be inserted into the Maven repo. Under Unix, type:

> ./install-prolog.sh

To check everything is working, run the tests:

> mvn test

Note: the word "experiment" above is important; the code is not particularly readable or well-factored, nor is the
separation between Java & Prolog particularly great.