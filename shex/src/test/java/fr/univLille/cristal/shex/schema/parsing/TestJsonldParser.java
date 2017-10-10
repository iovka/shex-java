/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package fr.univLille.cristal.shex.schema.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Test;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.schema.ShexSchema;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestJsonldParser {

	private static Path getJsonFile (String testCaseName) {
		if (testCaseName.endsWith(".json"))
			return Paths.get("../../../../github/shexTest/schemas/", testCaseName);
		else
			return Paths.get("../../../../github/shexTest/schemas/", testCaseName+".json");
	}
	
	@Test
	public void test0() throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(getJsonFile("0"));
		
		ShexSchema schema = parser.parseSchema();
		System.out.println("0: " + schema);	
	}
	
	
	@Test
	public void test0focusBNODE() throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(getJsonFile("0focusBNODE"));
		ShexSchema schema = parser.parseSchema();
		System.out.println("0focusBNODE: " + schema);
	}
	
	@Test
	public void test0focusIRI() throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(getJsonFile("0focusIRI"));
		ShexSchema schema = parser.parseSchema();
		System.out.println("0focusIRI: " + schema);
	}
	
	@Test
	public void test1Adot() throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(getJsonFile("1Adot"));
		ShexSchema schema = parser.parseSchema();
		System.out.println("1Adot: " + schema);
	}
	
	@Test
	public void test1bnode() throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(getJsonFile("1bnode"));
		ShexSchema schema = parser.parseSchema();
		System.out.println("1bnode: " + schema);
	}
		
	private static void parse (Path schemaFile) throws IOException, JsonLdError {
		JsonldParser parser = new JsonldParser(schemaFile);
		ShexSchema schema = parser.parseSchema();
		System.out.println("\t" + schema);
	}
	
	private static void printFile (Path schemaFile) {
		try (BufferedReader reader = Files.newBufferedReader(schemaFile)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	
	public static void main(String[] args) throws IOException, JsonLdError  {
//		String testName = "1val1vExprRefOR3";
//		System.out.println(testName);
//		printFile(getJsonFile(testName));
//		parse(getJsonFile(testName));
//		System.exit(0);
		
		Scanner scan = new Scanner(System.in);
		int i = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("test-resources/schemas"))) {
            for (Path path : directoryStream) {
            	if (path.toString().endsWith(".json")) {
                	System.out.println("---- " + ++i + " ----");
            		System.out.println(path.getFileName());
            		parse(path);
            		// scan.nextLine();
            	}
            }
        } catch (IOException ex) {
        	System.err.println(ex.getMessage());
        }
        scan.close();
	}
	
}
