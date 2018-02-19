package fr.univLille.cristal.shex.runningTests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RecursiveValidation;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class RunTestsRebuild {
	private static final RDFFactory RDF_FACTORY = RDFFactory.getInstance();

	protected static final String TEST_DIR = "/home/jdusart/Documents/Shex/workspace/shexTest/";
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final String MANIFEST_FILE = TEST_DIR + "validation/manifest.ttl";
	private static final String SCHEMAS_DIR = TEST_DIR + "schemas/";
	private static final String DATA_DIR = TEST_DIR + "validation/";

	private static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	private static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	private static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");
	private static final IRI TEST_NAME_IRI = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name");
		
	private static int nbPass = 0;
	private static int nbFail = 0;
	private static int nbError = 0;
	private static int nbError_pass = 0;
	private static int nbError_fail = 0;
	private static int nbSkip = 0;


	// TODO: update command line for rdf4j
	// command line
	// java -cp bin:lib/jena-core-3.0.0.jar:lib/slf4j-api-1.7.12.jar:lib/slf4j-log4j12-1.7.12.jar:lib/log4j-1.2.17.jar:lib/xercesImpl-2.11.0.jar:lib/xml-apis-1.4.01.jar:lib/jena-base-3.0.0.jar:lib/jena-iri-3.0.0.jar:lib/jena-shaded-guava-3.0.0.jar:lib/javax.json-api-1.0.jar:lib/javax.json-1.0.4.jar:lib/jgrapht-core-0.9.2.jar runningTests.RunTests 


	public static void main(String[] args) throws IOException, ParseException {
		Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
		if (args.length == 0) {
			runAllTests(manifest);
			System.out.println(new Date(System.currentTimeMillis()));
			System.out.println("PASSES : " + nbPass);
			System.out.println("FAILS  : " + nbFail);
			System.out.println("ERRORS PASS: " + nbError_pass);
			System.out.println("ERRORS FAIL: " + nbError_fail);
			System.out.println("ERRORS: " + nbError);
			System.out.println("SKIPS  : " + nbSkip);
		} else {
			for (String arg: args)
				runTestByName(arg, manifest);
		}
	}

	
	//--------------------------------------------------
	// Tests functions
	//--------------------------------------------------
	private static final Set<IRI> skippedIris = new HashSet<>(Arrays.asList(new IRI[] {
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start"), // average number of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"), // lot of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),  // 4 tests
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"), // few test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax") //no test
	}));
	
	public static Object[] shouldRunTest (Resource testNode, Model manifest) {
		List<Object> reasons = new ArrayList<>();

		for (Value object: manifest.filter(testNode, TEST_TRAIT_IRI, null).objects()) {
			if (skippedIris.contains(object)) {
				reasons.add(object);
			}
		}
		
		if (reasons.size()==0) {
			return new Object[]{true};
		} else {
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
			PrintStream errorLog = new PrintStream(Files.newOutputStream(Paths.get("report/ERROR"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
		){
			for (Resource testNode : manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects()) 
				report.add(runTest(testNode, manifest, passLog, failLog, errorLog));
			for (Resource testNode :  manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects()) 
				report.add(runTest(testNode, manifest, passLog, failLog, errorLog));
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
		
		if ( (! (Boolean) shouldRun[0]) ){
			String message = "Skipping test ";
			for (int i = 1; i < shouldRun.length; i++)
				message += "(" + shouldRun[i].toString() + ") ";
			message += ": " + testName;
			nbSkip++;
			return new TestResultForTestReport(testName, false, message, "validation");
		}
		
		TestCase testCase = new TestCase(manifest, testNode);
		if (! testCase.isWellDefined()) {
			errorLog.println(logMessage(testCase, null, null, "Incorrect test definition\nERROR"));
			nbError++;
			return new TestResultForTestReport(testName, false, "Incorrect test definition.", "validation");
		}
		ShexSchema schema = null;
		Model data = null;
		try {
			Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
			Path dataFile = Paths.get(DATA_DIR,getDataFileName(testCase.dataFileName));
			if(schemaFile.toString().equals(dataFile.toString())) {
				String message = "Skipping test because schema file is same as data file.";
				nbSkip++;
				return new TestResultForTestReport(testName, false, message, "validation");	
			}
			if(! schemaFile.toFile().exists()) {
				String message = "Skipping test because schema file does not exists.";
				nbSkip++;
				return new TestResultForTestReport(testName, false, message, "validation");	
			}
			//System.err.println("Test: "+testName);
			//JsonldParser parser = new JsonldParser();
			schema = GenParser.parseSchema(schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
			
			
			data = parseTurtleFile(dataFile.toString(),GITHUB_URL+"validation/");
			RDF4JGraph dataGraph = new RDF4JGraph(data);

//			for (Value obj:dataGraph.getAllSubjectNodes())
//				System.err.println(dataGraph.listAllNeighbours(obj));
//			
//			System.out.println(schema.getShapeMap());
//			for (ShapeExpr expr:schema.getShapeMap().values())
//				System.err.println(expr+" : "+expr.getClass());
			
			//RefineValidation validation = new RefineValidation(schema, dataGraph);
			//validation.validate(testCase.focusNode, null);

			RecursiveValidation validation = new RecursiveValidation(schema, dataGraph);
			validation.validate(testCase.focusNode, testCase.shapeLabel);
					
			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && 
					validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))
					||
					(testCase.testKind.equals(VALIDATION_FAILURE_CLASS) &&
							! validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))){
				passLog.println(logMessage(testCase, schema, data, "PASS"));
				nbPass++;
				return new TestResultForTestReport(testName, true, null, "validation");
			} else {
				failLog.println(logMessage(testCase, schema, data, "FAIL"));
				System.err.println("Failling: "+testName);
				nbFail++;
				return new TestResultForTestReport(testName, false, null, "validation");
			}			
			//		} catch(CyclicReferencesException e) {
			//		} catch(UndefinedReferenceException e) {
			//		} catch(NotStratifiedException e) {
		}catch (Exception e) {
			errorLog.println(logMessage(testCase, schema, data, "ERROR"));
			e.printStackTrace(errorLog);
			if (testCase.testKind.equals(VALIDATION_TEST_CLASS))
				nbError_pass++;
			else
				nbError_fail++;
			nbError++;
			System.err.println("Exception: "+testName);
			System.err.println(e.getClass());
			return new TestResultForTestReport(testName, false, null, "validation");
		}

	}

	//--------------------------------------------------
	// Utils functions for test
	//--------------------------------------------------

	private static String getTestName (Model manifest, Resource testNode) {
		return Models.getPropertyString(manifest, testNode, TEST_NAME_IRI).get();
	}

	private static String getSchemaFileName (Resource res) {
		String fp = res.toString().substring(res.toString().indexOf("/master/")+8);
		//fp = fp.substring(0, fp.length()-5)+".json";
		fp = fp.substring(0, fp.length()-5)+".ttl";
		return TEST_DIR+fp;
	}

	private static String getDataFileName (Resource res) {
		String[] parts = res.toString().split("/");
		return parts[parts.length-1];
	}


	protected static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		java.net.URL documentUrl = new URL("file://"+filename);
		InputStream inputStream = documentUrl.openStream();
				
		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
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
}
