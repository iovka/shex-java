package fr.inria.lille.shexjava.shexTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.util.TestResultForTestReport;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.Typing;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public abstract class AbstractValidationTest {
	public static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();
	public static final String TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize().toString();
	protected static String MANIFEST_FILE = Paths.get(TEST_DIR,"validation","manifest.ttl").toString();
	protected static final String DATA_DIR = Paths.get(TEST_DIR,"validation").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR,"schemas").toString();
	protected static final String BASE_IRI = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	protected static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	protected static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	protected static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");

	// the following collections are used for the test report
	protected static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> skiped = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();

	protected static final Set<IRI> traitsToSkip = new HashSet<>(Arrays.asList(RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"), // lot of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),  // 4 tests
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"), // few test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Greedy"),
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"relativeIRI")));

    @Parameter
    public TestCase testCase;

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() throws IOException {
		return allValidationTestsInManifestFile().parallelStream().map(tc -> new Object[]{tc}).collect(Collectors.toList());
	}

	protected static Collection<TestCase> allValidationTestsInManifestFile() throws IOException {
		if (Paths.get(MANIFEST_FILE).toFile().exists()) {
			Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
			List<TestCase> testCases = manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects().parallelStream()
					.map(node -> new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,node))
					.collect(Collectors.toList());
			testCases.addAll(manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects().parallelStream()
					.map(node -> new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,node))
					.collect(Collectors.toList()));
			return testCases;
		}
		return Collections.emptyList();
	}

	@Test
	public void runTest() {
		String skipTestCaseReason = skipTestCaseReason();
		if(skipTestCaseReason != null) {
			skiped.add(new TestResultForTestReport(testCase.testName, false, skipTestCaseReason, "validation"));
			assumeTrue(skipTestCaseReason, false);
		}

		/*
		Path schemaFile = getSchemaFile(testCase.schemaFileName);
		if(! schemaFile.toFile().exists()) {
			String reason = String.format("Test [%s] skipped. Schema file missing : %s", testCase.testName, schemaFile);
			skiped.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
			fail(reason);
		}
		*/
		if (! testCase.isWellDefined()) {
			String reason = String.format("Test [%s] failed. Incorrect test definition");
			failed.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
			fail(reason);
		}

		try {
			fixTest();
			Typing typing = performValidation().getTyping();
			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && typing.isConformant(testCase.focusNode, testCase.shapeLabel))
					|| (testCase.testKind.equals(VALIDATION_FAILURE_CLASS) && typing.getStatus(testCase.focusNode, testCase.shapeLabel) == Status.NONCONFORMANT)){
				passed.add(new TestResultForTestReport(testCase.testName, true, null, "validation"));
			} else {
				String reason = String.format("Test [%s] failed. Unexpected validation result", testCase.testName);
				failed.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
				fail(reason);
			}
		} catch (Exception e) {
			String reason = String.format("test [%s] failed; Exception during the validation : %s", testCase.testName, e.getMessage());
			errors.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
			fail(reason);
		}
	}


	public ValidationAlgorithmAbstract performValidation() throws Exception {
		Path schemaFile = Paths.get(getSchemaFileName());
		
		ShexSchema schema = GenParser.parseSchema(schemaFile, Collections.singletonList(Paths.get(SCHEMAS_DIR)), null); // exception possible
		Graph dataGraph = getRDFGraph();
		ValidationAlgorithmAbstract validation = getValidationAlgorithm(schema, dataGraph);   
		
		if (testCase.traits.contains(RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start")))
			testCase.shapeLabel = schema.getStart().getId();
			
		validation.validate(testCase.focusNode, testCase.shapeLabel);
		return validation;
	}

	/** Returns a non null string if and only if the test case should be skipped.
	 * In this case the string is the reason for skipping the test. */
	protected String skipTestCaseReason () {
		List<Object> skippedTraits = testCase.traits.stream().filter(trait -> traitsToSkip.contains(trait)).collect(Collectors.toList());
		if (! skippedTraits.isEmpty())
			return String.format("Test [%s] skipped. Unsupported traits : %s", testCase.testName, skippedTraits);
		else
			return null;
	}

	/** The file name depends on the schema input format. */
	protected abstract String getSchemaFileName ();

	/** The graph depends on the RDF library being used. */
	protected abstract Graph getRDFGraph() throws IOException;

	protected abstract ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph );

	protected void fixTest () {
		// Does nothing, can be overridden
	}



	private static String getDataFileName (Resource res) {
		String fp = res.toString().substring(BASE_IRI.length());
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	public static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		Path fp = Paths.get(filename);
		InputStream inputStream = new FileInputStream(fp.toFile());
		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}

	protected static String getSchemaFileName_ShExC (Resource res) {
		String fp = res.toString().substring(BASE_IRI.length());
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	public String getSchemaFileName_ShExJ (Resource res) {
		String fp = res.toString().substring(BASE_IRI.length());
		fp = fp.substring(0,fp.length()-4)+"shex";
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	protected static String getSchemaFileName_ShExR (Resource res) {
		String fp = res.toString().substring(BASE_IRI.length());
		fp = fp.substring(0,fp.length()-4)+"ttl";
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	protected static Graph getRDFGraph_RDF4J(Resource res) throws IOException {
		Model data = parseTurtleFile(getDataFileName(res), BASE_IRI+DATA_DIR+"/");
		return (new RDF4J()).asGraph(data);
	}

	protected static Graph getRDFGraph_Jena(Resource res) throws IOException {
		org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel() ;
		model.read(getDataFileName(res));
		return (new JenaRDF()).asGraph(model);
	}

}
