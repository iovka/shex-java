# shex-java
Support of Shape Expression Language 2.0 (http://shex.io/shex-semantics/index.html) and validation.

The validation algorithms implemented are the one that appears in:
```json
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

# shexTest

On validation, the current implementation, using RDF4J, pass 1033 tests, fail 3 tests and skip 41 tests.
The tests that are skipped are the one with at least one of those traits:
 - Start
 - SemanticAction
 - ExternalShape
 - ShapeMap
 - Greedy
 - relativeIRI

Using Jena, more tests are failed because new label are generated for bnode. The same mechanism is also present in RDF4J, but has been disabled in the tests.
 

On negative structure, the current implementation pass all the tests.

On negative syntax, the current implementation fail 2 tests. 


# Usage

Current implementation used RDF4J framework for the RDF manipulation. It is possible to used JENA using JenaGraph class, but we recommend the use of RDF4J.

## Maven

Create the package: 
 > mvn -DskipTests package

To test the package, the shexTest suite must be in the same directories as shex-java and the OS must use UNIX-style path.
Run the tests and create the report for shexTest: 
 > mvn exec:java -Dexec.mainClass="fr.univLille.cristal.shex.shexTest.CreateTestReport"

## Code Exemple

Classic example for validating a model and a schema.

```java
Path dataFile, schemaFile;
List<Path> importDirectories;


// load and create the shex schema
ShexSchema schema = GenParser.parseSchema(schemaFile,importDirectories);
 
 // load the model
IRI baseIRI = SimpleValueFactory.getInstance().createIRI("http://a.example.shex/");
Model data = Rio.parse(new FileInputStream(dataFile.toFile()), baseIRI, RDFFormat.TURTLE);

// create the RDF graph
RDFGraph dataGraph = new RDF4JGraph(data);
    		
// create the validation algorithm
ValidationAlgorithm validation = new RecursiveValidation(schema, dataGraph);   

// choose focus node and shapelabel
IRI focusNode = SimpleValueFactory.getInstance().createIRI(baseIRI,"node");
IRI shapeLabel = SimpleValueFactory.getInstance().createIRI(baseIRI,"Shape");

//validate
validation.validate(focusNode, shapeLabel);

//check the result
validation.getTyping().contains(focusNode, shapeLabel);

```


