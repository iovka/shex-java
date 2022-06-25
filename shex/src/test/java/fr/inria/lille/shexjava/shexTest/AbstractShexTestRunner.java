package fr.inria.lille.shexjava.shexTest;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.util.TestResultForTestReport;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

@RunWith(Parameterized.class)
public abstract class AbstractShexTestRunner {

    // the following collections are used for the test report
    protected static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
    protected static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
    protected static final Set<TestResultForTestReport> skiped = new HashSet<TestResultForTestReport>();
    protected static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();

    public static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();
    protected static final String BASE_IRI = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
    public static final Path TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize();
    public static final Path SCHEMAS_DIR = TEST_DIR.resolve("schemas");
    public static final Path DATA_DIR = TEST_DIR.resolve("validation");
    public static final Path MANIFEST_FILE = TEST_DIR.resolve("validation").resolve("manifest.ttl");

    protected static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    protected static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
    protected static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");

    @Parameterized.Parameter
    public TestCase testCase;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws IOException {
        return allValidationTestsInManifestFile().parallelStream()
                .map(tc -> new Object[]{tc})
                .collect(Collectors.toList());
    }

    @Test
    public void runTest() {
        // TODO testType in reporting test results should be parametrized
        String skipTestCaseReason = skipTestReason();
        if(skipTestCaseReason != null) {
            skiped.add(new TestResultForTestReport(testCase.testName, false, skipTestCaseReason, "validation"));
            assumeTrue(skipTestCaseReason, false);
        }
        if (! testCase.isWellDefined()) {
            String reason = String.format("Test [%s] failed. Incorrect test definition");
            failed.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
            fail(reason);
        }

        try {
            executeTest();
        } catch (Exception e) {
            String reason = String.format("Test [%s] failed. Exception during test execution : %s", testCase.testName, e.getMessage());
            errors.add(new TestResultForTestReport(testCase.testName, false, reason, "validation"));
            fail(reason);
        }
    }

    abstract protected void executeTest () throws Exception;

    /** Returns a non-null string if and only if the test case should be skipped.
     * @return the reason why the test should be skipped
     */
    protected String skipTestReason() {
        return UnsupportedFeatures.isNotSupportedReason(testCase);
    }

    public static Collection<TestCase> allValidationTestsInManifestFile() throws IOException {
        if (Files.exists(MANIFEST_FILE)) {
            Model manifest = parseTurtleFile(MANIFEST_FILE, MANIFEST_FILE.toString());
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

    public static Path getDataFile (Resource res) {
        return DATA_DIR.resolve(((IRI)res).getLocalName());
    }

    public static Path getSchemaFile(Resource res, String extension) {
        String schemaName = ((IRI)res).getLocalName();
        schemaName = schemaName.substring(0, schemaName.length()-5);
        return SCHEMAS_DIR.resolve(schemaName+"."+extension);
    }

    public static Model parseTurtleFile(Path file, String baseURI) throws IOException {
        InputStream inputStream = Files.newInputStream(file);
        return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
    }

    public static Graph getRDFGraph_RDF4J(Resource res) throws IOException {
        Model data = parseTurtleFile(getDataFile(res), BASE_IRI);
        return (new RDF4J()).asGraph(data);
    }

    public static Graph getRDFGraph_Jena(Resource res) throws IOException {
        org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel() ;
        model.read(getDataFile(res).toString());
        return (new JenaRDF()).asGraph(model);
    }

}
