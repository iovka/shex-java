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

# Quick guide to update to version 1.1 from 1.0

## What is broken and requires some changes

The api now uses commonsRDF (https://commons.apache.org/proper/commons-rdf/) as the core api and this allows for support for Jena, OWL, Apache Clerezza, JSONLD-Java.

The api works with a factory that implements the RDF interface of the commonsRDF api. This factory is the field RDFFactory of the class fr.inria.lille.shexjava.GlobalFactory. By default it is an RDF4J factory but can be change to Jena to something else. All the functions of the api that need to create RDF objects will use the GlobalFactory or the one passed in arguments if you want to use different factories.

Code example to set the factory:

```
org.apache.commons.rdf.rdf4j.RDF4J factory = new org.apache.commons.rdf.rdf4j.RDF4J();
GlobalFactory.RDFFactory = factory;
```

The import org.eclipse.rdf4j.model.IRI; need to change import org.apache.commons.rdf.api.IRI;. Same for Literal or BlankNode.

Remove all imports to class of the package fr.inria.lille.shexjava.graph. The graph class used is now org.apache.commons.rdf.api.Graph. 

Code example to create the graph: 
```
Graph dataGraph = factory.asGraph(data);
```

A complete small code example can be found in https://github.com/jdusart/DatatypesShExJava

## What is new

The matching found beetween the triple constraints and the RDF triple can now be access using the MatchingCollector of the ValidationAlgorithm. You can remove this functionality by removing them.

Similarly, a FailureAnalyzer can be add to the ValidationAlgorithm to try to find the reason of the failure and store the report.

Code example:
```
validation.addFailureReportsCollector(new FailureAnalyzerSimple());
```

A new algorithm for validation as been introduced: RecursiveValidationWithMemorization.



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
```
 > mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="fr.inria.lille.shexjava.shexTest.CreateTestReport" -Dexec.args="http://example.fr/~me"
 ```
 

# Usage

## Online validator

You have an online validator available at http://shexjava.lille.inria.fr/ with predefined examples.


## Command line validation

### With execution through maven

 Command line example to run a validation:
 ```
 >  mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="fr.inria.lille.shexjava.commandLine.Validate" -Dexec.args="-s  ../../shexTest/schemas/1dotSemi.shex -d file:///home/jdusart/Documents/Shex/workspace/shexTest/validation/Is1_Ip1_Io1.ttl -l http://a.example/S1 -f http://a.example/s1 -a recursive" 
 ```

### With execution without maven

```sh
mvn package
mvn dependency:build-classpath -Dmdep.includeScope=runtime -Dmdep.outputFile=cp.txt
SHEXCP="target/shexjava-core-1.1.jar:"`cat cp.txt`
java -cp $SHEXCP fr.inria.lille.shexjava.commandLine.Validate -s ../../shexTest/schemas/1dotSemi.shex -d https://raw.githubusercontent.com/shexSpec/shexTest/master/validation/Is1_Ip1_Io1.ttl   -l http://a.example/S1 -f http://a.example/s1 -a recursive
```

## Code Exemple

You can find two small projects that use the implementation:
 - https://github.com/jdusart/DatatypesShExJava
 - https://github.com/jdusart/GeneWikiShExJava
 



