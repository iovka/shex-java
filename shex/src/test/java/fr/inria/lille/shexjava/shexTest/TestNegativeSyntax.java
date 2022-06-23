/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.inria.lille.shexjava.shexTest;

import static org.junit.Assume.assumeTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.simple.SimpleRDF;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestResultForTestReport;

/** Run the negative syntax test of the shexTest suite.
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestNegativeSyntax {
	private static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();

	protected static final String TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize().toString();
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final String MANIFEST_FILE = Paths.get(TEST_DIR,"negativeSyntax","manifest.ttl").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR,"schemas").toString();

	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	private static final Resource NEGATIVE_SYNTAX = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#NegativeSyntax");

	protected static final Set<IRI> skippedIris = new HashSet<>(Arrays.asList(new IRI[] {
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start"), // average number of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"), // lot of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),  // 4 tests
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"), // few test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Greedy"),
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"relativeIRI")
	}));
	
	// the following collections are used for the test report
	public static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();
	
	@Parameters
    public static Collection<Object[]> parameters() throws IOException {
    	if (Paths.get(MANIFEST_FILE).toFile().exists()) {
			Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
			List<Resource> testNodes = manifest.filter(null,RDF_TYPE,NEGATIVE_SYNTAX).subjects()
											   .parallelStream().collect(Collectors.toList());

			String selectedTest = "";
			if (!selectedTest.equals(""))
				testNodes = testNodes.parallelStream().filter(node -> getTestName(manifest, node).equals(selectedTest)).collect(Collectors.toList());
			
			return testNodes.parallelStream()
					.map(node -> {Object[] params={getTestName(manifest, node),getSchemaFileName(manifest, node)};
								  return params;}).collect(Collectors.toList());
    	}
    	return Collections.emptyList();
    }
    
  //A list of tests to ignore because they contain a test on the name of a bnode and jena do not give stable bnode name
  	protected static final Set<String> ignoredTests = new HashSet<>(Arrays.asList(new String[] {
  			"1datatypeRef1",
  			"1unknowndatatypeMaxInclusive"}));
    
  	@Before
	public void beforeMethod() {
  		if (ignoredTests.contains(testName))
			failed.add(new TestResultForTestReport(testName, false, null, "negativeSyntax"));
		assumeTrue(!ignoredTests.contains(testName));
	}
  	
    @Parameter
    public String testName;
    @Parameter(1)
    public Path schemaFile;   	
	
    @Test
    public void runTest() {
		ShexSchema schema = null;
		try {
			schema = GenParser.parseSchema(schemaFile,Collections.singletonList(Paths.get(SCHEMAS_DIR)),new SimpleRDF()); // exception possible
			failed.add(new TestResultForTestReport(testName, false, null, "negativeSyntax"));
		}catch (Exception e) {
			errors.add(new TestResultForTestReport(testName, true, e.getMessage(), "negativeSyntax"));
		}

    }
    
	
	private static final IRI TEST_NAME_IRI = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name");
    private static String getTestName (Model manifest, Resource testNode) {
		return Models.getPropertyString(manifest, testNode, TEST_NAME_IRI).get();
	}

	private static final IRI TEST_SHEX_IRI = RDF_FACTORY.createIRI("https://shexspec.github.io/shexTest/ns#shex");
	private static Path getSchemaFileName (Model manifest, Resource testNode) {
		String filename = Models.getPropertyString(manifest, testNode, TEST_SHEX_IRI).get();
		filename = filename.substring(GITHUB_URL.length());
		return Paths.get(TEST_DIR,filename);
	}

	public static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		Path fp = Paths.get(filename);
		InputStream inputStream = new FileInputStream(fp.toFile());

		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}
}
