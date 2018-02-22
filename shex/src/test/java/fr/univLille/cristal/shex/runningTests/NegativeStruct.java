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
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RecursiveValidation;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class NegativeStruct {
	private static final RDFFactory RDF_FACTORY = RDFFactory.getInstance();

	protected static final String TEST_DIR = "/home/jdusart/Documents/Shex/workspace/shexTest/";
	protected static final String GITHUB_URL = "https://raw.githubusercontent.com/shexSpec/shexTest/master/";
	protected static final String MANIFEST_FILE = TEST_DIR + "negativeStructure/manifest.ttl";
	private static final String SCHEMAS_DIR = TEST_DIR + "schemas/";
	private static final String DATA_DIR = TEST_DIR + "validation/";

	private static final IRI RDF_TYPE = RDF_FACTORY.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	private static final Resource NEGATIVE_SYNTAX = RDF_FACTORY.createIRI("http://www.w3.org/ns/shacl/test-suite#NegativeStructure");
	private static final IRI TEST_NAME_IRI = RDF_FACTORY.createIRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name");
	private static final IRI TEST_SHEX_IRI = RDF_FACTORY.createIRI("https://shexspec.github.io/shexTest/ns#shex");
	
	
	private static int nbFail = 0;
	private static int nbError = 0;


	// TODO: update command line for rdf4j
	// command line
	// java -cp bin:lib/jena-core-3.0.0.jar:lib/slf4j-api-1.7.12.jar:lib/slf4j-log4j12-1.7.12.jar:lib/log4j-1.2.17.jar:lib/xercesImpl-2.11.0.jar:lib/xml-apis-1.4.01.jar:lib/jena-base-3.0.0.jar:lib/jena-iri-3.0.0.jar:lib/jena-shaded-guava-3.0.0.jar:lib/javax.json-api-1.0.jar:lib/javax.json-1.0.4.jar:lib/jgrapht-core-0.9.2.jar runningTests.RunTests 


	public static void main(String[] args) throws IOException, ParseException {
		Model manifest = parseTurtleFile(MANIFEST_FILE,MANIFEST_FILE);
		if (args.length == 0) {
			runAllTests(manifest);
			System.out.println(new Date(System.currentTimeMillis()));
			System.out.println("FAILS  : " + nbFail);
			System.out.println("ERRORS: " + nbError);
		} else {
			for (String arg: args)
				runTestByName(arg, manifest);
		}
	}

	
	//--------------------------------------------------
	// Tests functions
	//--------------------------------------------------
		
	protected static List<TestResultForTestReport> runAllTests (Model manifest) throws IOException, ParseException {
		List<TestResultForTestReport> report = new ArrayList<>();
		try (
			PrintStream passLog = new PrintStream(Files.newOutputStream(Paths.get("report/PASS"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
			PrintStream failLog = new PrintStream(Files.newOutputStream(Paths.get("report/FAIL"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
			PrintStream errorLog = new PrintStream(Files.newOutputStream(Paths.get("report/ERROR"), StandardOpenOption.WRITE, StandardOpenOption.CREATE));
		){
			for (Resource testNode : manifest.filter(null,RDF_TYPE,NEGATIVE_SYNTAX).subjects()) 
				report.add(runTest(testNode, manifest, passLog, failLog, errorLog));
		}
		return report;
	}

	

	private static void runTestByName (String testName, Model manifest) throws IOException, ParseException {
		Value testNameString = RDF_FACTORY.createLiteral(testName);
		Set<Resource> testsWithThisName = manifest.filter(null, TEST_NAME_IRI, testNameString).subjects();

		if (testsWithThisName.size() != 1)
			throw new IllegalArgumentException("No test with name " + testName);

		Resource testNode = testsWithThisName.iterator().next();
		runTest(testNode, manifest, System.out, System.out, System.err);
	}
	
	
	private static TestResultForTestReport runTest(Resource testNode, Model manifest, PrintStream passLog, PrintStream failLog, PrintStream errorLog) throws IOException {
		String testName = getTestName(manifest, testNode);
		Path schemaFile = getSchemaFileName(manifest, testNode);
		System.err.println(schemaFile);
		ShexSchema schema = null;
		try {
			schema = GenParser.parseSchema(schemaFile,Paths.get(SCHEMAS_DIR)); // exception possible
			System.out.println("Failing: "+testName);
			System.out.println(schema);
			nbFail++;
			return new TestResultForTestReport(testName, false, null, "negative syntax");
		}catch (Exception e) {
			e.printStackTrace(errorLog);
			nbError++;
			System.err.println("Exception: "+testName);
			System.err.println(e.getClass());
			return new TestResultForTestReport(testName, false, null, "negative syntax");
		}

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
;		return Paths.get(TEST_DIR,filename);
	}

	protected static Model parseTurtleFile(String filename,String baseURI) throws IOException{
		java.net.URL documentUrl = new URL("file://"+filename);
		InputStream inputStream = documentUrl.openStream();
				
		return Rio.parse(inputStream, baseURI, RDFFormat.TURTLE, new ParserConfig(), RDF_FACTORY, new ParseErrorLogger());
	}

	private static String logMessage (TestCase testCase, ShexSchema schema, Model data, String customMessage) {
		String result = "";
		result += ">>----------------------------\n";
		result += testCase.toString();
		if (schema != null)
			result += "Schema : " + schema.toString() + "\n";
		if (data != null)
			result += "Data   : " + data.toString() + "\n";
		result += customMessage + "\n"; 
		result += "<<----------------------------";
		return result;
	}
}
