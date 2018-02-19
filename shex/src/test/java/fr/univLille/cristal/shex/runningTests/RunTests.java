package fr.univLille.cristal.shex.runningTests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RefineValidation;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RunTests {

	protected static final String TEST_DIR = "/home/jdusart/Documents/Shex/workspace/shexTest/";
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/validation/";
	protected static final String MANIFEST_FILE = TEST_DIR + "validation/manifest.ttl";
	private static final String SCHEMAS_DIR = TEST_DIR + "schemas/";
	private static final String DATA_DIR = TEST_DIR + "validation/";

	private static final RDFFactory RDF_FACTORY = RDFFactory.getInstance();

	private static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	private static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	private static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");
	private static final IRI TEST_NAME_IRI = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name");



	private static int nbPass = 0;
	private static int nbFail = 0;
	private static int nbError = 0;
	private static int nbSkip = 0;


	// TODO: update command line for rdf4j
	// command line
	// java -cp bin:lib/jena-core-3.0.0.jar:lib/slf4j-api-1.7.12.jar:lib/slf4j-log4j12-1.7.12.jar:lib/log4j-1.2.17.jar:lib/xercesImpl-2.11.0.jar:lib/xml-apis-1.4.01.jar:lib/jena-base-3.0.0.jar:lib/jena-iri-3.0.0.jar:lib/jena-shaded-guava-3.0.0.jar:lib/javax.json-api-1.0.jar:lib/javax.json-1.0.4.jar:lib/jgrapht-core-0.9.2.jar runningTests.RunTests 


	public static void main(String[] args) throws IOException, ParseException {
		//XPath.setXMLSchemaRegexMatcher(new PartialXMLSchemaRegexMatcher());

		Model manifest = loadManifest();
		if (args.length == 0) {
			runAllTests(manifest);
			System.out.println(new Date(System.currentTimeMillis()));
			System.out.println("PASSES : " + nbPass);
			System.out.println("FAILS  : " + nbFail);
			System.out.println("ERRORS : " + nbError);
			System.out.println("SKIPS  : " + nbSkip);
		}
		else
			for (String arg: args)
				runTestByName(arg, manifest);		
	}

	//--------------------------------------------------
	// Tests functions
	//--------------------------------------------------

	public static Object[] shouldRunTest (Resource testNode, Model manifest) {
		boolean shouldRun = true;
		List<String> reasons = new ArrayList<>();

		Set<String> skippedIris = new HashSet<>(Arrays.asList(new String[] {
				 "Include", "Start", "ExternalShape", "SemanticAction", 
				 "LiteralFocus", "ShapeMap", "IncorrectSyntax",	"MissingFile"}));

		for (Value object: manifest.filter(testNode, TEST_TRAIT_IRI, null).objects()) {
			for (String reason : skippedIris) {
				IRI reasonIRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+reason);
				if (object.equals(reasonIRI)) {
					shouldRun = false;
					reasons.add(reason);
				}
			}
		}
		if (shouldRun)
			return new Object[]{true};
		else {
			Object[] result = new Object[1 + reasons.size()];
			result[0] = false;
			for (int i = 0; i < reasons.size(); i++)
				result[i+1] = reasons.get(i);
			return result;
		}
	}

	
	protected static List<TestResultForTestReport> runAllTests (Model manifest) throws IOException, ParseException {
		List<TestResultForTestReport> report = new ArrayList<>();
		try (
				PrintStream passLog = new PrintStream(Files.newOutputStream(Paths.get("report/PASS"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
				PrintStream failLog = new PrintStream(Files.newOutputStream(Paths.get("report/FAIL"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
				PrintStream errorLog = new PrintStream(Files.newOutputStream(Paths.get("report/ERROR"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));)
		{

			for (Resource testNode : getValidationTestsList(manifest)) {
				TestResultForTestReport r = runTest(testNode, manifest, passLog, failLog, errorLog);
				report.add(r);
			}

			for (Resource testNode :  getFailureTestsList(manifest)) {
				TestResultForTestReport r = runTest(testNode, manifest, passLog, failLog, errorLog);
				report.add(r);
			}
		}
		return report;
	}


	private static void runTestByName (String testName, Model manifest) throws IOException, ParseException {
		Value testNameString = RDF_FACTORY.createLiteral(testName);
		Set<Resource> testsWithThisName = manifest.filter(null, TEST_NAME_IRI, testNameString).subjects();

		if (testsWithThisName.size() != 1)
			throw new IllegalArgumentException("No test with name " + testName);

		Resource testNode = testsWithThisName.iterator().next();
		runTest(testNode, manifest, System.out, System.out, System.err);
	}
	
	

	private static TestResultForTestReport runTest(Resource testNode, Model manifest, PrintStream passLog, PrintStream failLog, PrintStream errorLog) throws IOException {
		String testName = getTestName(manifest, testNode);

		Object[] shouldRun = shouldRunTest(testNode, manifest);
		if (! (Boolean) shouldRun[0]) {
			String message = "Skipping test ";
			for (int i = 1; i < shouldRun.length; i++)
				message += "(" + shouldRun[i].toString() + ") ";
			message += ": " + testName;
			//System.out.println(message);
			nbSkip++;

			return new TestResultForTestReport(testName, false, message, "validation");
		}
		TestCase testCase = parseTestCase(manifest, testNode);
		if (! testCase.isWellDefined()) {
			errorLog.println(logMessage(testCase, null, null, "Incorrect test definition\nERROR"));
			nbError++;
			return new TestResultForTestReport(testName, false, "Incorrect test definition.", "validation");
		}

		ShexSchema schema = null;
		Model data = null;
		try {
			// Run the test case, exception possible
			schema = GenParser.parseSchema(Paths.get(testCase.schemaFileName)); // exception possible
			data = parseTurtleFile(Paths.get(DATA_DIR, testCase.dataFileName).toString());
//			System.out.println("Data:"+data);			
//			System.out.println(schema.getShapeMap());
//			for (Statement st:data) {
//				System.out.println(st);
//			}
			RDF4JGraph dataGraph = new RDF4JGraph(data);
			
			RefineValidation validation = new RefineValidation(schema, dataGraph);

			validation.validate(testCase.focusNode, null);
//			System.out.println(testCase.focusNode);
//			System.out.println(dataGraph.listAllNeighbours(testCase.focusNode));
//			System.out.println(schema.getRules().get(testCase.shapeLabel));
//			System.out.println(testCase.shapeLabel);
//			System.out.println(testCase.testKind);
//			System.out.println(validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel));
//			for (Pair<Value, ShapeExprLabel> entry:validation.getTyping().asSet())
//				System.out.println(entry);
//			System.out.println();
			if (testCase.testKind.equals(VALIDATION_TEST_CLASS) &&
					validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel)
					||
					testCase.testKind.equals(VALIDATION_FAILURE_CLASS) &&
					! validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))
			{
				passLog.println(logMessage(testCase, schema, data, "PASS"));
				nbPass++;
				return new TestResultForTestReport(testName, true, null, "validation");
			}
			else {
				failLog.println(logMessage(testCase, schema, data, "FAIL"));
				System.out.println("Failing:"+testName);
				nbFail++;
				return new TestResultForTestReport(testName, false, null, "validation");
			}			
		} catch (Exception e) {
			errorLog.println(logMessage(testCase, schema, data, "ERROR"));
			e.printStackTrace(errorLog);
			System.out.println("Exception:"+testName);
			nbError++;
			return new TestResultForTestReport(testName, false, null, "validation");
		}
	}

	//--------------------------------------------------
	// Utils functions for test
	//--------------------------------------------------

	private static String getTestName (Model manifest, Resource testNode) {
		return Models.getPropertyString(manifest, testNode, TEST_NAME_IRI).get();
	}

	private static String getTestComment (Model manifest, Resource testNode) {
		return Models.getPropertyString(manifest, testNode, RDFS.COMMENT).get();
	}

	protected static Iterable<Resource> getValidationTestsList (Model manifest) {
		return manifest.filter(null, RDF_TYPE, VALIDATION_TEST_CLASS).subjects();
	}

	protected static Iterable<Resource> getFailureTestsList (Model manifest) {
		return manifest.filter(null, RDF_TYPE, VALIDATION_FAILURE_CLASS).subjects();
	}

	private static String getSchemaFileName (Resource res) {
		String s = res.toString();
		int idxLastSlash = s.lastIndexOf('/');
		String fileName = s.substring(idxLastSlash, s.length()-5);
		String filePath = SCHEMAS_DIR + fileName + ".json";
		return filePath;
	}

	private static String getDataFileName (Resource res) {
		String[] parts = res.toString().split("/");
		return parts[parts.length-1];

	}

	private static Resource getTestKind (Model manifest, Resource testNode) {
		return Models.getPropertyIRI(manifest, testNode, RDF_TYPE).get();
	}

	static Model loadManifest () throws IOException {
		return parseTurtleFile(MANIFEST_FILE);
	}

	protected static Model parseTurtleFile(String filename) throws IOException{

		java.net.URL documentUrl = new URL("file://"+filename);
		InputStream inputStream = documentUrl.openStream();
		
		return Rio.parse(inputStream, GITHUB_URL, RDFFormat.TURTLE);
	}

	private static String logMessage (TestCase testCase, ShexSchema schema, Model data, String customMessage) {
		String result = "";
		result += ">>----------------------------\n";
		result += testCase.toString();
		if (schema != null)
			result += "Schema : " + schema.toString() + "\n";
		if (data != null)
			result += "Data   : " + data.toString() + "\n";
		result += customMessage + "\n"; 
		result += "<<----------------------------";
		return result;
	}


	//--------------------------------------------------
	// Test Case class
	//--------------------------------------------------


	class TestCase {
		public final Resource testKind;
		public final String testName;
		public final String schemaFileName;
		public final String dataFileName;
		public final ShapeExprLabel shapeLabel;
		public final Value focusNode;
		public final String testComment;

		public TestCase(String testName, String schemaFileName, String dataFileName, ShapeExprLabel shapeLabel, Value focusNode, String testComment, Resource testKind) {
			super();
			this.testName = testName;
			this.schemaFileName = schemaFileName;
			this.dataFileName = dataFileName;
			this.shapeLabel = shapeLabel;
			this.focusNode = focusNode;
			this.testComment = testComment;
			this.testKind = testKind;
		}

		@Override
		public String toString() {
			String info = "";
			info += testName + "\n";
			info += testKind.toString() + "\n";
			info += "Comment    : " + testComment + "\n";
			info += "Schema file: " + schemaFileName + "\n";
			info += "Data file  : " + dataFileName + "\n";
			info += "Focus : " + focusNode + "\n";
			info += "Shape : " + shapeLabel + "\n";
			return info;
		}		

		boolean isWellDefined () {
			return schemaFileName != null && dataFileName != null && shapeLabel != null && focusNode != null;
		}
	}

	protected static TestCase parseTestCase (Model manifest, Resource testNode) {
		final IRI ACTION_PROPERTY = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#action");
		final IRI SCHEMA_PROPERTY = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#schema");
		final IRI DATA_PROPERTY = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#data");
		final IRI SHAPE_PROPERTY = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#shape");
		final IRI FOCUS_PROPERTY = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#focus");

		try {
			Resource actionNode = Models.getPropertyResource(manifest, testNode, ACTION_PROPERTY).get();
			String schemaFileName = getSchemaFileName(Models.getPropertyIRI(manifest, actionNode, SCHEMA_PROPERTY).get());  
			String dataFileName = getDataFileName(Models.getPropertyIRI(manifest, actionNode, DATA_PROPERTY).get());
			Resource labelRes = Models.getPropertyResource(manifest, actionNode, SHAPE_PROPERTY).get();

			ShapeExprLabel label;
			if (labelRes instanceof BNode)
				label = new ShapeExprLabel((BNode)labelRes);
			else
				label = new ShapeExprLabel((IRI)labelRes);

			Value focus = Models.getProperty(manifest, actionNode, FOCUS_PROPERTY).get();
			String testComment = getTestComment(manifest, testNode);
			String testName = getTestName(manifest, testNode);

			Resource testKind = getTestKind(manifest, testNode);

			return new RunTests().new TestCase(testName, schemaFileName, dataFileName, label, focus, testComment, testKind);
		} catch (Exception e) {
			System.out.println("Error on test case " + testNode);
			throw e;
		}
	}	

}
