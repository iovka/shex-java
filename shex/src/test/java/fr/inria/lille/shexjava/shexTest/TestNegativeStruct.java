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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.rdf.simple.SimpleRDF;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.Models;
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

import fr.inria.lille.shexjava.exception.CyclicReferencesException;
import fr.inria.lille.shexjava.exception.NotStratifiedException;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.CommonFactory;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.util.TestResultForTestReport;


/** Run the negative structure test of the shexTest suite.
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestNegativeStruct {
	private static final RDF4JFactory RDF_FACTORY = RDF4JFactory.getInstance();

	protected static final String TEST_DIR = Paths.get("..","..","shexTest").toAbsolutePath().normalize().toString();
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final String MANIFEST_FILE = Paths.get(TEST_DIR,"negativeStructure","manifest.ttl").toString();
	protected static final String SCHEMAS_DIR = Paths.get(TEST_DIR,"schemas").toString();

	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	private static final Resource NEGATIVE_STRUCT_PASS = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#NegativeStructureTest");
	private static final Resource NEGATIVE_STRUCT_FAIL = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#NegativeStructureFailure");

	private static final IRI TEST_NAME_IRI = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name");
	private static final IRI TEST_SHEX_IRI = RDF_FACTORY.createIRI("https://shexspec.github.io/shexTest/ns#shex");
	
	public static final Set<TestResultForTestReport> passed = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> failed = new HashSet<TestResultForTestReport>();
	public static final Set<TestResultForTestReport> errors = new HashSet<TestResultForTestReport>();
	
	@Parameters
    public static Collection<Object[]> parameters() throws IOException {
    	if (Paths.get(MANIFEST_FILE).toFile().exists()) {
	    	Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
	    	List<Object[]> parameters = new ArrayList<Object[]>();
			for (Resource testNode : manifest.filter(null,RDF_TYPE,NEGATIVE_STRUCT_PASS).subjects()) {
	    		Object[] params =  new Object[3];
	    		params[0]=getTestName(manifest, testNode);
	    		params[1]=getSchemaFileName(manifest, testNode);
	    		params[2]=true;
	    		parameters.add(params);
			}
			for (Resource testNode : manifest.filter(null,RDF_TYPE,NEGATIVE_STRUCT_FAIL).subjects()) {
	    		Object[] params =  new Object[3];
	    		params[0]=getTestName(manifest, testNode);
	    		params[1]=getSchemaFileName(manifest, testNode);
	    		params[2]=false;
	    		parameters.add(params);
			}
	        return parameters;
    	}
    	return Collections.emptyList();
    }
    
    
    @Parameter
    public String testName;
    @Parameter(1)
    public Path schemaFile; 
    @Parameter(2)
    public boolean pass;
	
    @Test
    public void runTest() {
		ShexSchema schema = null;
		try {
			schema = GenParser.parseSchema(new CommonFactory(),schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
			if (pass){
    			passed.add(new TestResultForTestReport(testName, true, null, "negativeStructurePass"));
    		} else {
				failed.add(new TestResultForTestReport(testName, false, null, "negativeStructureFailure"));					
    		}
		}catch (Exception e) {
			if (pass && ((e instanceof CyclicReferencesException)
					      || (e instanceof NotStratifiedException)
					      || (e instanceof UndefinedReferenceException)
					     ) ) {
				errors.add(new TestResultForTestReport(testName, false, e.getMessage(), "negativeStructure"));
			}else {
				passed.add(new TestResultForTestReport(testName, true, e.getMessage(), "negativeStructureFailure"));				
			}
		}

    }
    
    @AfterClass
	public static void ending() {
    	System.out.println("Result for negative structure tests:");
		System.out.println("Passed : "+passed.size());
    	System.out.println("Failed : "+failed.size());
		printTestCaseNames("  > ",failed);
		System.out.println("Errors : "+errors.size());
		printTestCaseNames("  > ",errors);
	}
    
    public static void printTestCaseNames(String prefix, Set<TestResultForTestReport> reports) {
    	for (TestResultForTestReport report:reports)
    		System.out.println(prefix+report.name+" ("+report.description+")");
    }
	
	
	//--------------------------------------------------
	// Utils functions for test
	//--------------------------------------------------

    private static String getTestName (Model manifest, Resource testNode) {
		return Models.getPropertyString(manifest, testNode, TEST_NAME_IRI).get();
	}

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
