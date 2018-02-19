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
package fr.univLille.cristal.shex.runningTests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RecursiveValidation;


@RunWith(Parameterized.class)
public class RunTestParametrized {
	private static final RDFFactory RDF_FACTORY = RDFFactory.getInstance();
	private static final String TEST_DIR = "/home/jdusart/Documents/Shex/workspace/shexTest/";
	private static final String DATA_DIR = TEST_DIR + "validation/";
	private static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	private static final String MANIFEST_FILE = TEST_DIR + "validation/manifest.ttl";
	private static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	private static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	
	private static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	private static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
	private static final Set<TestResultForTestReport> skiped = new HashSet<TestResultForTestReport>();
	private static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();
	
	
	@Parameters
    public static Collection<Object[]> parameters() throws IOException {
	    	Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
	    	List<Object[]> parameters = new ArrayList<Object[]>();
	    	for (Resource testNode : manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects()) {
		    	Object[] params =  {new TestCase(manifest,testNode)};
		    	parameters.add(params);
			}
	    	for (Resource testNode : manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects()) {
	    		Object[] params =  {new TestCase(manifest,testNode)};
		    	parameters.add(params);
			}
	    	System.out.println("# of tests:"+parameters.size());
	        return parameters;
    	
    }
    
    
    public static boolean shouldRunTest (TestCase test) {
    	Set<IRI> skippedIris = new HashSet<>(Arrays.asList(new IRI[] {
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"Start"), // average number of test
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"SemanticAction"), // lot of test
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ExternalShape"),  // 4 tests
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"LiteralFocus"), //no test
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"ShapeMap"), // few test
    			RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#"+"IncorrectSyntax") //no test
    	}));
    	
		for (Value object: test.traits) {
			if (skippedIris.contains(object)) {
				return false;
			}
		}
		return true;
	}
    
    @Parameter
    public TestCase testCase;
        	
	@Test
	public void runTest() {
		System.err.println("Testing... "+testCase);
		if (! testCase.isWellDefined()) {
			failed.add(new TestResultForTestReport(testCase.testName, false, "Incorrect test definition.", "validation"));
			return;
		}		
		if (shouldRunTest(testCase)) {
			String message = "Skipping test because some trait is not supported.";
			skiped.add(new TestResultForTestReport(testCase.testName, false, message, "validation"));
			return;
		}
		try {
			System.err.println("Test: "+testCase.testName);
			JsonldParser parser = new JsonldParser();
			//ShExCParser parser = new ShExCParser();
			//ShExRParser parser = new ShExRParser();
			ShexSchema schema = parser.parseSchema(Paths.get(getSchemaFileName(testCase.schemaFileName))); // exception possible

			Model data = parseTurtleFile(Paths.get(DATA_DIR,getDataFileName(testCase.dataFileName)).toString(),GITHUB_URL+"validation/");
			RDF4JGraph dataGraph = new RDF4JGraph(data);

			//RefineValidation validation = new RefineValidation(schema, dataGraph);
			//validation.validate(testCase.focusNode, null);

			RecursiveValidation validation = new RecursiveValidation(schema, dataGraph);
			validation.validate(testCase.focusNode, testCase.shapeLabel);

			if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && 
					validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))
					||
					(testCase.testKind.equals(VALIDATION_FAILURE_CLASS) &&
							! validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))){
				//passLog.println(logMessage(test, schema, data, "PASS"));
				passed.add(new TestResultForTestReport(testCase.testName, true, null, "validation"));
			} else {
				//failLog.println(logMessage(test, schema, data, "FAIL"));
				//System.err.println("Failling: "+testName);
				failed.add(new TestResultForTestReport(testCase.testName, false, null, "validation"));
			}			
			//		} catch(CyclicReferencesException e) {
			//		} catch(UndefinedReferenceException e) {
			//		} catch(NotStratifiedException e) {
		}catch (Exception e) {
			//errorLog.println(logMessage(test, schema, data, "ERROR"));
			e.printStackTrace();
			//System.err.println("Exception: "+testName);
			//System.err.println(e.getClass());
			errors.add(new TestResultForTestReport(testCase.testName, false, null, "validation"));
		}
	}
	
	
	@AfterClass
	public static void ending() {
		System.err.println("Skipped: "+skiped.size());
		System.err.println("Passed : "+passed.size());
		System.err.println("Failed : "+failed.size());
		System.err.println("Errors : "+errors.size());
	}



	//--------------------------------------------------
	// Utils functions for test
	//--------------------------------------------------

	public static String getSchemaFileName (Resource res) {
		String fp = res.toString().substring(res.toString().indexOf("/master/")+8);
		fp = fp.substring(0, fp.length()-5)+".json";
		//fp = fp.substring(0, fp.length()-5)+".ttl";
		return TEST_DIR+fp;
	}

	public static String getDataFileName (Resource res) {
		String[] parts = res.toString().split("/");
		return parts[parts.length-1];
	}


	public static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		java.net.URL documentUrl = new URL("file://"+filename);
		InputStream inputStream = documentUrl.openStream();

		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}
}
