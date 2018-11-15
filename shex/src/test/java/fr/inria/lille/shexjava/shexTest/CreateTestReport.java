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

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.runner.JUnitCore;

import fr.inria.lille.shexjava.util.TestResultForTestReport;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class CreateTestReport {
	String ASSERTED_BY;
	String SUBJECT = "<https://github.com/iovka/shex-java>";
	String BRANCH = "master";
	String WHEN = "\"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())+"\""+ "^^<http://www.w3.org/2001/XMLSchema#dateTime>";
	PrintStream out;
	
	public CreateTestReport(String logPath) throws IOException {
		if (logPath == null)
			out = System.out;
		else 
			out = new PrintStream(Files.newOutputStream(Paths.get(logPath), StandardOpenOption.CREATE));
	}
	
	public static void usage() {
		StringBuilder text = new StringBuilder();
		text.append("Usage:\n");
		text.append("  <id>          : id to use for the field asserted-by\n");
		System.err.println(text);
	}
	
	public static void main(String[] args) throws IOException  {
		CreateTestReport t = new CreateTestReport("shexjava-earl.ttl");

		if (args.length!=1) {
			usage();
			System.exit(1);
		}

		t.ASSERTED_BY = "<"+args[0]+">";
				
		t.printHeader();
		
		JUnitCore.runClasses(TestValidation_ShExC_RDF4J_Recursive.class);
		for (TestResultForTestReport r : TestValidation_ShExC_RDF4J_Recursive.passed)
			t.printTestResult(r);
		for (TestResultForTestReport r : TestValidation_ShExC_RDF4J_Recursive.skiped)
			t.printTestResult(r);
		for (TestResultForTestReport r : TestValidation_ShExC_RDF4J_Recursive.errors)
			t.printTestResult(r);
		for (TestResultForTestReport r : TestValidation_ShExC_RDF4J_Recursive.failed)
			t.printTestResult(r);
		
		JUnitCore.runClasses(TestNegativeStruct.class);
		for (TestResultForTestReport r : TestNegativeStruct.errors)
			t.printTestResult(r);
		for (TestResultForTestReport r : TestNegativeStruct.failed)
			t.printTestResult(r);
		
		JUnitCore.runClasses(TestNegativeSyntax.class);
		for (TestResultForTestReport r : TestNegativeSyntax.errors)
			t.printTestResult(r);
		for (TestResultForTestReport r : TestNegativeSyntax.failed)
			t.printTestResult(r);
		
		t.printFooter();
	}
	
	private void printTestResult (TestResultForTestReport res)  {
		StringBuilder s = new StringBuilder();
		s.append(String.format("[ a earl:Assertion;\n"));
		s.append(String.format("  earl:assertedBy %s;\n", ASSERTED_BY));
		s.append(String.format("  earl:subject %s;\n", SUBJECT));
		s.append(String.format("  earl:test <https://raw.githubusercontent.com/shexSpec/shexTest/%s/%s/manifest#%s>;\n", BRANCH, res.testType, res.name));
		s.append(String.format("  earl:result [\n"));
		s.append(String.format("    a earl:TestResult;\n"));
		s.append(String.format("    earl:outcome earl:%s;\n", res.passed ? "passed" : "failed"));
		if (res.description != null)
			s.append(String.format("    earl:description \"%s\";\n", res.description));
		s.append(String.format("    dc:date %s^^xsd:dateTime;\n", WHEN));
		s.append(String.format("  ];\n"));
		s.append(String.format("  earl:mode earl:automatic\n"));
		s.append(String.format("] .\n"));
		out.println(s);
	}
	
	private void printHeader () {
		String prefixes = "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
				+ "@prefix doap:  <http://usefulinc.com/ns/doap#> .\n" 
				+ "@prefix earl:  <http://www.w3.org/ns/earl#> .\n"
				+ "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .\n"
				+ "@prefix foaf:  <http://xmlns.com/foaf/0.1/> .\n"
				+ "@prefix dc:    <http://purl.org/dc/terms/> .\n\n";
		
		String something = SUBJECT + " a doap:Project, earl:TestSubject, earl:Software ;\n"
				+ "  doap:name \"ShEx validator v0.5\" ;\n"
				+ "  doap:developer " + ASSERTED_BY + " .\n\n";
				
		String somethingElse = "<> foaf:primaryTopic " + SUBJECT + ";\n"
				+ "  dc:issued " + WHEN + ";\n"
				+ "  foaf:maker " + ASSERTED_BY + ".\n\n";	  
		out.println(prefixes + something + somethingElse);
	}
	
	private void printFooter () {}
	
}
