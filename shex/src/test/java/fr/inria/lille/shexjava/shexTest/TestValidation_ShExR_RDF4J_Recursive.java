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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
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
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.Typing;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;


/** Run the validation tests of the shexTest suite using ShExC parser, RDF4JGraph and recursive validation.
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestValidation_ShExR_RDF4J_Recursive extends AbstractValidationTest {
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

			String selectedTest = "";
			if (!selectedTest.equals(""))
				testCases = testCases.parallelStream().filter(tc -> tc.testName.equals(selectedTest)).collect(Collectors.toList());
			
			return testCases.parallelStream().map(tc -> {Object[] params =  {tc}; return params;}).collect(Collectors.toList());

    	}
    	return Collections.emptyList();
	}

    
    @Before
	public void beforeMethod() {
		List<Object> reasons = testCase.traits.stream().filter(trait -> skippedIris.contains(trait)).collect(Collectors.toList());
		assumeTrue(reasons.size()==0);

		Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
		assumeTrue(schemaFile.toFile().exists());	
		
		Path dataFile = Paths.get(getDataFileName(testCase.dataFileName));
		assumeTrue(!schemaFile.toString().equals(dataFile.toString()));
	}

    
	@Test
    public void runTest() {
		if (! testCase.isWellDefined()) {
			fail("Incorrect test definition.");
		}
    	try {
			Status status = performValidation();
			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && Status.CONFORMANT.equals(status))
					|| (testCase.testKind.equals(VALIDATION_FAILURE_CLASS) && Status.NONCONFORMANT.equals(status))){
				fail("Validation did not compute the expected result: test " + testCase.testName);
			}			
    	} catch (Exception e) {
			fail("Exception during the validation: "+e.getMessage());
		}
    }
	
    public String getSchemaFileName (Resource res) {
    	String fp = res.toString().substring(GITHUB_URL.length());
    	fp = fp.substring(0,fp.length()-4)+"ttl";
		return Paths.get(TEST_DIR,Paths.get(fp).toString()).toString();
	}
	
	public Graph getRDFGraph() throws IOException {
		Model data = parseTurtleFile(getDataFileName(testCase.dataFileName),GITHUB_URL+"validation/");
		return (new RDF4J()).asGraph(data);
	}
	
	public ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		return new RecursiveValidation(schema, dataGraph);
	}

}
