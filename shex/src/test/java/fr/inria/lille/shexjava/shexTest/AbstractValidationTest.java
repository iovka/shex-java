package fr.inria.lille.shexjava.shexTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.util.TestResultForTestReport;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.Typing;
import org.apache.commons.rdf.api.Graph;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;

import static org.junit.Assert.fail;

public abstract class AbstractValidationTest extends AbstractShexTestRunner {

	// the following collections are used for the test report
	protected static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> skiped = new HashSet<TestResultForTestReport>();
	protected static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();

	@Override
	protected void executeTest() throws Exception {
		fixTest();

		Graph dataGraph = getRDFGraph();
		Path schemaFile = getSchemaFile();
		ShexSchema schema = GenParser.parseSchema(schemaFile, Collections.singletonList(SCHEMAS_DIR), null); // exception possible
		ValidationAlgorithmAbstract validation = getValidationAlgorithm(schema, dataGraph);

		if (testCase.traits.contains(RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start")))
			testCase.shapeLabel = schema.getStart().getId();

		validation.validate(testCase.focusNode, testCase.shapeLabel);

		Typing typing = validation.getTyping();
		if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && typing.isConformant(testCase.focusNode, testCase.shapeLabel))
				|| (testCase.testKind.equals(VALIDATION_FAILURE_CLASS) && typing.getStatus(testCase.focusNode, testCase.shapeLabel) == Status.NONCONFORMANT)){
			passed.add(new TestResultForTestReport(testCase.testName, true, null, "validation"));
		} else {
			String reason = String.format("Test [%s] failed. Unexpected validation result", testCase.testName);
			failed.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
			fail(reason);
		}

	}

	/** The file name depends on the schema input format. */
	protected abstract Path getSchemaFile();

	/** The graph depends on the RDF library being used. */
	protected abstract Graph getRDFGraph() throws IOException;

	protected abstract ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph );

	protected void fixTest () {
		// Does nothing, can be overridden
	}


}
