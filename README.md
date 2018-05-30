# Shex-java
Support of Shape Expression Language 2.0 (http://shex.io/shex-semantics/index.html) and validation.

The validation algorithms implemented are the one that appears in:
```bibtex
@inproceedings{boneva:hal-01590350,
  TITLE = {{Semantics and Validation of Shapes Schemas for RDF}},
  AUTHOR = {Boneva, Iovka and Labra Gayo, Jose G and Prud 'hommeaux, Eric G},
  URL = {https://hal.archives-ouvertes.fr/hal-01590350},
  BOOKTITLE = {{ISWC2017 - 16th International semantic web conference}},
  ADDRESS = {Vienna, Austria},
  YEAR = {2017},
  MONTH = Oct,
  PDF = {https://hal.archives-ouvertes.fr/hal-01590350/file/paper-lncs.pdf},
  HAL_ID = {hal-01590350},
  HAL_VERSION = {v1},
}
```

# Install

## Maven

You just have to put in the pom.xml file of your project:
```xml
<dependency>
  	<groupId>fr.inria.lille.shexjava</groupId>
  	<artifactId>shexjava-core</artifactId>
  	<version>1.0</version>
 </dependency>
```

## Build the jar

With all the tests:
```sh
git clone https://github.com/iovka/shex-java.git
git clone https://github.com/shexSpec/shexTest
cd shex-java/shex #cd shex-java\shex for windows user
mvn clean package
```

Without the tests:
```sh
git clone https://github.com/iovka/shex-java.git
cd shex-java/shex #cd shex-java\shex for windows user
mvn clean package
```

After that, the jar file can be found in the target directory.

# shexTest

To test the package, the shexTest suite must be in the same directories as shex-java.

On validation, the current implementation, using RDF4J, passes 1033 tests, fails 3 tests and skips 41 tests.
The tests that are skipped are the one with at least one of those traits:
 - Start
 - SemanticAction
 - ExternalShape
 - ShapeMap
 - Greedy
 - relativeIRI

Using Jena, more tests are failed because new label are generated for bnode (test with trait LexicalBnode). The same mechanism is also present in RDF4J, but has been disabled for the tests.
 

On negative structure, the current implementation passes all the tests.

On negative syntax, the current implementation passes 100 out of the 102 tests. 

Command line example to run the tests and create the report for shexTest: 
 > mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="fr.univLille.cristal.shex.shexTest.CreateTestReport" -Dexec.args="http://example.fr/~me"

# Usage

## Online validator

You have an online validator available at http://shexjava.lille.inria.fr/ with predefined examples.


## Command line validation with maven

 Command line example to run a validation:
 >  mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="fr.inria.lille.shexjava.commandLine.Validate" -Dexec.args="-s  ../../shexTest/schemas/1dotSemi.shex -d file:///home/jdusart/Documents/Shex/workspace/shexTest/validation/Is1_Ip1_Io1.ttl -l http://a.example/S1 -f http://a.example/s1 -a recursive" 


## Code Exemple

Current implementation used RDF4J framework for the RDF manipulation. It is possible to used JENA using JenaGraph class, but we recommend the use of RDF4J.

You can find two small projects that use the implementation:
 - https://github.com/jdusart/DatatypesShExJava
 - https://github.com/jdusart/GeneWikiShExJava
 



