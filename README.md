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

## Maven install

On GNU/Linux operation system with shexTest:

```sh
git clone https://github.com/iovka/shex-java.git
git clone https://github.com/shexSpec/shexTest
cd shex-java/shex
mvn clean install
```

On other operating system or without shexTest:
```sh
git clone https://github.com/iovka/shex-java.git
cd shex-java/shex
mvn -DskipTests clean install
```

## Build the jar

On GNU/Linux operation system with shexTest:

```sh
git clone https://github.com/iovka/shex-java.git
git clone https://github.com/shexSpec/shexTest
cd shex-java/shex
mvn clean package
```

On other operating system or without shexTest:
```sh
git clone https://github.com/iovka/shex-java.git
cd shex-java/shex
mvn -DskipTests clean package
```

After that, the jar file can be found in the target directory.

# shexTest

To test the package, the shexTest suite must be in the same directories as shex-java and the OS must use UNIX-style path.

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

Current implementation used RDF4J framework for the RDF manipulation. It is possible to used JENA using JenaGraph class, but we recommend the use of RDF4J.

## Command line validation with maven

 Command line example to run a validation:
 >  mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="fr.univLille.cristal.shex.commandLine.Validate" -Dexec.args="-s  ../../shexTest/schemas/1dotSemi.shex -d file:///home/jdusart/Documents/Shex/workspace/shexTest/validation/Is1_Ip1_Io1.ttl -l http://a.example/S1 -f http://a.example/s1 -a recursive" 

## Maven dependency

Put in the pom.xml file of your project:
```xml
<dependency>
  	<groupId>fr.univLille.cristal</groupId>
  	<artifactId>shex</artifactId>
  	<version>1.0-a1</version>
  	<scope>provided</scope>
 </dependency>
```

## Code Exemple

Classic example for validating a model and a schema.

```java
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.validation.RecursiveValidation;
import fr.univLille.cristal.shex.validation.ValidationAlgorithm;

public class Main {

	public static void main(String[] args) throws Exception {
		Path schemaFile = Paths.get("datatypes.json"); //to change with what you want 
		Path dataFile = Paths.get("datatypes-data.ttl"); //to change with what you want 
		List<Path> importDirectories = Collections.emptyList();
	
		// load and create the shex schema
		ShexSchema schema = GenParser.parseSchema(schemaFile,importDirectories);
		 
		 // load the model
		String baseIRI = "http://a.example.shex/";
		Model data = Rio.parse(new FileInputStream(dataFile.toFile()), baseIRI, RDFFormat.TURTLE);

		// create the RDF graph
		RDFGraph dataGraph = new RDF4JGraph(data);
		    		
		// create the validation algorithm
		ValidationAlgorithm validation = new RecursiveValidation(schema, dataGraph);   

		// choose focus node and shapelabel
		IRI focusNode = SimpleValueFactory.getInstance().createIRI("http://a.example/integer-p1"); //to change with what you want 
		Label shapeLabel = new Label(SimpleValueFactory.getInstance().createIRI("http://a.example/S-integer")); //to change with what you want 

		//validate
		validation.validate(focusNode, shapeLabel);

		//check the result
		System.out.println(validation.getTyping().contains(focusNode, shapeLabel));
	}

}


```


