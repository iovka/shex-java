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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.CommonFactory;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.util.TestResultForTestReport;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.ValidationAlgorithm;


/** Run the validation tests of the shexTest suite using ShExC parser, RDF4JGraph and recursive validation.
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestValidation_ShExC_RDF4J_Recursive {
	protected static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();
	protected static final String TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize().toString();	
	protected static String MANIFEST_FILE = Paths.get(TEST_DIR,"validation","manifest.ttl").toString();
	protected static final String DATA_DIR = Paths.get(TEST_DIR,"validation").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR,"schemas").toString();
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final Resource VALIDATION_FAILURE_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationFailure");
	protected static final Resource VALIDATION_TEST_CLASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#ValidationTest");
	protected static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	protected static final IRI TEST_TRAIT_IRI = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#trait");

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
	
	public static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> skiped = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();
	
	@Parameters
	public static Collection<Object[]> parameters() throws IOException {
    	if (Paths.get(MANIFEST_FILE).toFile().exists()) {
			Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
			List<Object[]> parameters = new ArrayList<Object[]>();
			String selectedTest = "";
	    	for (Resource testNode : manifest.filter(null,RDF_TYPE,VALIDATION_TEST_CLASS).subjects()) {
	    		TestCase tc = new TestCase(manifest,testNode);
		    	Object[] params =  {tc};
		    	if (selectedTest.equals("") || tc.testName.equals(selectedTest))
		    		parameters.add(params);
			}
	    	for (Resource testNode : manifest.filter(null,RDF_TYPE,VALIDATION_FAILURE_CLASS).subjects()) {
	    		TestCase tc = new TestCase(manifest,testNode);
		    	Object[] params =  {tc};
		    	if (selectedTest.equals("") || tc.testName.equals(selectedTest))
		    		parameters.add(params);
			}
			return parameters;
    	}
    	return Collections.emptyList();
	}

    
    @Parameter
    public TestCase testCase;
        	
	@Test
    public void runTest() {
    	List<Object> reasons = new ArrayList<>();
    	for (Value object: testCase.traits) {
    		if (skippedIris.contains(object)) {
    			reasons.add(object);
    		}
    	}
    	if (reasons.size()>0) {
    		String message = "Skipping test because some trait is not supported.";
    		skiped.add(new TestResultForTestReport(testCase.testName, false, message, "validation"));
    		return;
    	}	
    	if (! testCase.isWellDefined()) {
    		System.err.println("! well defined: "+testCase.testName);
    		System.err.println("! well defined: "+testCase.traits);
    		failed.add(new TestResultForTestReport(testCase.testName, false, "Incorrect test definition.", "validation"));
    		return;
    	}

    	try {
    		Path schemaFile = Paths.get(getSchemaFileName(testCase.schemaFileName));
   
    		if(! schemaFile.toFile().exists()) {
    			String message = "Skipping test because schema file does not exists.";	
    			skiped.add(new TestResultForTestReport(testCase.testName, false, message, "validation"));
    		}
    		//System.out.println(testCase);
    		ShexSchema schema = GenParser.parseSchema(new CommonFactory(),schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
    		//System.out.println(schema.getRules());
 
    		//System.out.println(dataGraph.stream().collect(Collectors.toList()));
    		Graph dataGraph = getRDFGraph();    		
    		ValidationAlgorithm validation = getValidationAlgorithm(schema, dataGraph);   
    		
    		validation.validate(testCase.focusNode, testCase.shapeLabel);

    		if ((testCase.testKind.equals(VALIDATION_TEST_CLASS) && 
    				validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))
    				||
    				(testCase.testKind.equals(VALIDATION_FAILURE_CLASS) &&
    						! validation.getTyping().contains(testCase.focusNode, testCase.shapeLabel))){
    			passed.add(new TestResultForTestReport(testCase.testName, true, null, "validation"));
    		} else {
    			failed.add(new TestResultForTestReport(testCase.testName, false, null, "validation"));
    		}			
    	}catch (Exception e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    		errors.add(new TestResultForTestReport(testCase.testName, false, e.getMessage(), "validation"));
    	}
    }
    
	
    @AfterClass
	public static void ending() {
    	System.out.println("Result for validation (ShExC, RDF4J, recursive) tests:");
		System.out.println("Skipped: "+skiped.size());
		printTestCaseNames("  > ",skiped);
		System.out.println("Passed : "+passed.size());
		System.out.println("Failed : "+failed.size());
		printTestCaseNames("  > ",failed);
		System.out.println("Errors : "+errors.size());
		printTestCaseNames("  > ",errors);
	}
    
    public static void printTestCaseNames(String prefix, Set<TestResultForTestReport> reports) {
    	for (TestResultForTestReport report:reports)
    		System.out.println(prefix+report.name);
    }
	
	
	//--------------------------------------------------
	// Utils functions for test
	//--------------------------------------------------

    public String getSchemaFileName (Resource res) {
    	String fp = res.toString().substring(GITHUB_URL.length());

    	String result = Paths.get(TEST_DIR).toString();
    	Iterator<Path> iter = Paths.get(fp).iterator();
    	while(iter.hasNext())
    		result = Paths.get(result,iter.next().toString()).toString();
    	
		return result;
	}
	
	public Graph getRDFGraph() throws IOException {
		Model data = parseTurtleFile(getDataFileName(testCase.dataFileName),GITHUB_URL+"validation/");
		return (new RDF4J()).asGraph(data);
	}
	
	public ValidationAlgorithm getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		return new RecursiveValidation(schema, dataGraph);
	}
	

	public String getDataFileName (Resource res) {
		String fp = res.toString().substring(GITHUB_URL.length());
		
		String result = Paths.get(TEST_DIR).toString();
    	Iterator<Path> iter = Paths.get(fp).iterator();
    	while(iter.hasNext())
    		result = Paths.get(result,iter.next().toString()).toString();
    	
		return result;	
	}


	public static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		Path fp = Paths.get(filename);
		InputStream inputStream = new FileInputStream(fp.toFile());
		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}
}
