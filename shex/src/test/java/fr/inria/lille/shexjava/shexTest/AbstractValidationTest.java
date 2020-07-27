package fr.inria.lille.shexjava.shexTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.validation.Status;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public abstract class AbstractValidationTest {
	public static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();
	public static final String TEST_DIR = Paths.get("..", "..", "shexTest").toAbsolutePath().normalize().toString();
	protected static String MANIFEST_FILE = Paths.get(TEST_DIR, "validation", "manifest.ttl").toString();
	protected static final String DATA_DIR = Paths.get(TEST_DIR, "validation").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR, "schemas").toString();
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	protected static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	protected static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	protected static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");

	@Parameter
	public TestCase testCase;

	// For debugging : allows to select the tests to be run
	protected static Predicate<TestCase> selectedTestCases;
	static {
		// trait filter
//			String traitIriString = "http://www.w3.org/ns/shacl/test-suite#Extends";
//			selectedTestCases = tc -> /* !tc.testName.contains("vitals") && */ tc.traits.stream()
//					.map( it -> it.stringValue())
//					.anyMatch( it -> it.equals(traitIriString));

		//exclude vitals
		selectedTestCases = tc -> !tc.testName.contains("vitals-"); /* && (
					tc.testName.equals("extends-abstract-multi-empty_fail-Ref1ExtraP")
					|| tc.testName.equals("ExtendANDExtend3GAND3G-t23")
					|| tc.testName.equals("start2RefS2-IstartS1")
					|| tc.testName.equals("start2RefS1-IstartS2"));*/

		// name filter
		selectedTestCases = tc -> tc.testName.equals("extends-abstract-multi-empty_pass");
	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() throws IOException {
		if (Paths.get(MANIFEST_FILE).toFile().exists()) {
			Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
			List<TestCase> testCases = new ArrayList<>();

			for (Resource res : manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects()) {
				TestCase testCase = new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,res);
				testCases.add(testCase);
			}

			for (Resource res : manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects()) {
				TestCase testCase = new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,res);
				testCases.add(testCase);
			}

			if (selectedTestCases != null) {
				testCases = testCases.parallelStream().filter(selectedTestCases).collect(Collectors.toList());
			}
			return testCases.parallelStream().map(tc -> {Object[] params =  {tc}; return params;}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Test
	public void runTest() {
		if (! testCase.isWellDefined())
			fail("Incorrect test definition of test " + testCase.testName);
		try {
			fixTest();
			Status status = performValidation();
			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && Status.CONFORMANT.equals(status))
					|| (testCase.testKind.equals(VALIDATION_FAILURE_CLASS) && Status.NONCONFORMANT.equals(status))){
				// do nothing, test succeed
			} else {
				fail("Validation did not compute the expected result: test " + testCase.testName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception during the validation for testCase :" + testCase.testName);
		}
	}


	private void fixTest() {
		if (testCase.focusNode instanceof org.apache.commons.rdf.api.IRI) {
			org.apache.commons.rdf.api.IRI focus = (org.apache.commons.rdf.api.IRI) testCase.focusNode;
			if (focus.getIRIString().startsWith(GITHUB_URL)) {
				if (TEST_DIR.contains(":")) {
					String newURI = TEST_DIR.substring(0,TEST_DIR.indexOf(":")+1);
					newURI += focus.getIRIString().substring(GITHUB_URL.length()+11);
					testCase.focusNode = GlobalFactory.RDFFactory.createIRI(newURI);
				}else {
					Path fullpath = Paths.get(TEST_DIR,focus.getIRIString().substring(GITHUB_URL.length()));
					testCase.focusNode = GlobalFactory.RDFFactory.createIRI("file://"+fullpath.toString());
				}
			}
		}
	}

	public Status performValidation() throws Exception {
		Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
		ShexSchema schema = GenParser.parseSchema(schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
		Graph dataGraph = getRDFGraph();

		ValidationAlgorithmAbstract validation = getValidationAlgorithm(schema, dataGraph);

		return validation.validate(testCase.focusNode, testCase.shapeLabel) ? Status.CONFORMANT : Status.NONCONFORMANT;
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
