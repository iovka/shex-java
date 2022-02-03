package fr.inria.lille.shexjava.shexTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.rdf.api.Graph;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.runners.Parameterized.Parameter;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;

public abstract class AbstractValidationTest {
	protected static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();
	protected static final String TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize().toString();	
	protected static String MANIFEST_FILE = Paths.get(TEST_DIR,"validation","manifest.ttl").toString();
	protected static final String DATA_DIR = Paths.get(TEST_DIR,"validation").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR,"schemas").toString();
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	protected static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	protected static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	protected static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");

    @Parameter
    public TestCase testCase;
    

	public ValidationAlgorithmAbstract performValidation() throws Exception {
		Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
		
		ShexSchema schema = GenParser.parseSchema(schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
		Graph dataGraph = getRDFGraph();    		
		ValidationAlgorithmAbstract validation = getValidationAlgorithm(schema, dataGraph);   
		
		if (testCase.shapeLabel == null)
			testCase.shapeLabel = schema.getStart().getId();
			
		validation.validate(testCase.focusNode, testCase.shapeLabel);
		return validation;
	}
	
	public abstract String getSchemaFileName (Resource res);

	public abstract Graph getRDFGraph() throws IOException;

	public abstract ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph );

	public String getDataFileName (Resource res) {
		String fp = res.toString().substring(GITHUB_URL.length());	
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	public static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		Path fp = Paths.get(filename);
		InputStream inputStream = new FileInputStream(fp.toFile());
		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}
}
