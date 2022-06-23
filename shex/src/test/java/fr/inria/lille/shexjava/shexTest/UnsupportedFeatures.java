package fr.inria.lille.shexjava.shexTest;


import fr.inria.lille.shexjava.util.TestCase;
import org.eclipse.rdf4j.model.IRI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Allows to determine skipped test cases. */
public class UnsupportedFeatures {

    /** Tests whether the test case is NOT supported by this implementation.
     * @param testCase
     * @return null if the test case is supported, or a non null string explaining the reason why the test case is not supported
     */
    public static String isNotSupportedReason (TestCase testCase) {
        List<Object> skippedTraits = testCase.traits.stream().filter(trait -> unsupportedTraits.contains(trait)).collect(Collectors.toList());
        if (! skippedTraits.isEmpty())
            return String.format("Test [%s] skipped. Unsupported traits : %s", testCase.testName, skippedTraits);
        else
            return null;

    }

    protected static final Set<IRI> unsupportedTraits = new HashSet<>(Arrays.asList(
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Greedy"),
            AbstractShexTestRunner.RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"relativeIRI")));
}
