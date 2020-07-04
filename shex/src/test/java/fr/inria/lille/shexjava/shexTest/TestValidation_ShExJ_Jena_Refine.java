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

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.validation.*;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.util.TestCase;

/** Run the validation tests of the shexTest suite using ShExJ parser, JenaGraph and refine validation.
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestValidation_ShExJ_Jena_Refine extends AbstractValidationTest {
	protected static final Set<IRI> skippedIris = new HashSet<>(Arrays.asList(new IRI[] {
			//RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start"), // average number of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"), // lot of test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),  // 4 tests
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"), // few test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax"), //no test
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Greedy"),
			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"relativeIRI"),
	}));

	@Parameters
	public static Collection<Object[]> parameters() throws IOException {
		if (Paths.get(MANIFEST_FILE).toFile().exists()) {
			Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
			List<TestCase> testCases = manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects().parallelStream()
					.map(node -> new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,node))
					.collect(Collectors.toList());
			testCases.addAll(manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects().parallelStream()
					.map(node -> new TestCase((RDF4J) GlobalFactory.RDFFactory,manifest,node))
					.collect(Collectors.toList()));		

			// Change here to run a unique test
			String selectedTest = "" ; //focusdatatype_pass";
			if (!selectedTest.equals("")) {
				testCases = testCases.parallelStream().filter(tc -> tc.testName.equals(selectedTest)).collect(Collectors.toList());
				TestCase tc = testCases.get(0);
				System.out.println(String.format("schema: %s, data: %s", tc.schemaFileName, tc.dataFileName));
			}
			return testCases.parallelStream().map(tc -> {Object[] params =  {tc}; return params;}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}


	//A list of tests to ignore because they contain a test on the name of a bnode and jena do not give stable bnode name
	protected static final Set<String> ignoredTests = new HashSet<>(Arrays.asList(new String[] {
			"1focusMaxLength-dot_pass-bnode-short",
			"1nonliteralMinlength_fail-bnode-short",
			"1bnodeMaxlength_pass-bnode-equal",
			"1nonliteralMaxlength_pass-bnode-short",
			"1focusBNODELength_dot_pass",
			"1nonliteralMaxlength_pass-bnode-equal",
			"1focusMaxLength-dot_pass-bnode-equal",
			"1bnodeLength_pass-bnode-equal",
			"1bnodePattern_fail-bnode-long",
			"1nonliteralLength_pass-bnode-equal",
			"1nonliteralPattern_pass-bnode-long",
			"1focusLength-dot_pass-bnode-equal",
			"1nonliteralPattern_pass-bnode-match",
			"1focusMinLength-dot_pass-bnode-long",
			"1bnodePattern_pass-bnode-match",
			"1bnodeMaxlength_pass-bnode-short",
			"1focusBNODE_dot_pass",
			"1focusPatternB-dot_pass-bnode-long",
			"1focusMinLength-dot_pass-bnode-equal",
			"1bnodeMinlength_fail-bnode-short",
			"1focusPatternB-dot_pass-bnode-match",
			"1iriPattern_fail-bnode-match",
			"1nonliteralPattern_fail-lit-match",
			"1bnodePattern_fail-bnode-short"}));

	@Before
	public void beforeMethod() {
		List<Object> reasons = testCase.traits.stream().filter(trait -> skippedIris.contains(trait)).collect(Collectors.toList());
		assumeTrue(reasons.size()==0);

		assumeTrue(!ignoredTests.contains(testCase.testName));

		Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
		assumeTrue(schemaFile.toFile().exists());
	}


	@Test
	public void runTest() {
		if (! testCase.isWellDefined()) 
			fail("Incorrect test definition.");
		
		try {
			fixTest();
			Typing typing = performValidation().getTyping();
			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && typing.isConformant(testCase.focusNode, testCase.shapeLabel))
					|| (testCase.testKind.equals(VALIDATION_FAILURE_CLASS) && typing.getStatus(testCase.focusNode, testCase.shapeLabel) == Status.NONCONFORMANT)){
				// do nothing, test succeed
			} else {
				fail("Validation did not compute the expected result: test " + testCase.testName);
			}			
		} catch (Exception e) {
			fail("Exception during the validation: "+e.getMessage());
		}
	}

	
	// Fix for dealing with the absence of namespace specification in jena.
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

	public String getSchemaFileName (Resource res) {
		String fp = res.toString().substring(GITHUB_URL.length());
		fp = fp.substring(0,fp.length()-4)+"json";
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}

	public Graph getRDFGraph() throws IOException {
		org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel() ;
		model.read(getDataFileName(testCase.dataFileName));
		Graph result = (new JenaRDF()).asGraph(model);

		// FIXME These should not be added to the graph. Validation should succeed if the node is not in the graph
		/*
		if (!result.contains(null, null, testCase.focusNode)) {
			if (!(testCase.focusNode instanceof BlankNodeOrIRI) || !result.contains((BlankNodeOrIRI) testCase.focusNode,null, null)) {
				result.add(GlobalFactory.RDFFactory.createIRI("http://test.shex/dummySource"), 
						GlobalFactory.RDFFactory.createIRI("http://test.shex/dummyPredicate"),
						testCase.focusNode);
				Iterator<? extends Triple> iter = result.stream(GlobalFactory.RDFFactory.createIRI("http://test.shex/dummySource"), 
						GlobalFactory.RDFFactory.createIRI("http://test.shex/dummyPredicate"),null).iterator();
				while (iter.hasNext())
					testCase.focusNode = iter.next().getObject();
			}
		}
		 */

		return result;
	}

	public ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		return new MyRefineValidation(schema, dataGraph);
	}


}
