# shex-java
Validation of Shape Expression Schemas

# Usage

Current implementation used RDF4J framework for the RDF manipulation. It is possible to used JENA using JenaGraph class, but we recommend the use of RDF4J.

## Maven

Create the package: mvn -DskipTests package

To test the package, the shexTest suite must be in the same directories as shex-java and the OS must use UNIX-style path.
Run the tests and create the report for shexTest: mvn exec:java -Dexec.mainClass="fr.univLille.cristal.shex.shexTest.CreateTestReport"

## Code Exemple

Classic exemple for validating a model and a schema.

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


