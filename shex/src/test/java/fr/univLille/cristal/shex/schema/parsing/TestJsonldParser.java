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

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.schema.ShexSchema;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
@RunWith(Parameterized.class)
public class TestJsonldParser {
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		List<Object[]> listOfParameters = new LinkedList<Object[]>();
	
//		String[] selected = {"1Include1-after","1Include1",
//				"2EachInclude1-after","2EachInclude1-IS2",
//				"2EachInclude1","2EachInclude1-S2",
//				"2OneInclude1-after","2OneInclude1"};
//		//String[] selected = {"test"};
//		Path[] paths = new Path[selected.length];
//		for (int i=0;i<selected.length;i++) {
//			paths[i]=Paths.get("/home/jdusart/Documents/Shex/workspace/shex-java/",selected[i]+".json");
//			paths[i]=Paths.get("/home/jdusart/Documents/Shex/workspace/shexTest/schemas/",selected[i]+".json");
//		}

		DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("/home/jdusart/Documents/Shex/workspace/shexTest/schemas/"));	
		
		HashSet<Path> exclude = new HashSet<Path>();
		exclude.add(Paths.get("/home/jdusart/Documents/Shex/workspace/shexTest/schemas/coverage.json"));
		
		for (Path path : paths) {
			if (! exclude.contains(path)) {
				if (path.toString().endsWith(".json")) {
					Object [] parameters = new Object[1]; 
					parameters[0] = path;
					listOfParameters.add(parameters);
				}
			}
		}
		return listOfParameters;
	}
		
	@Parameter
	public Path schemaFile;
		
	@Test
	public void parse (){
		try {
			JsonldParser parser = new JsonldParser(schemaFile);
			ShexSchema schema = parser.parseSchema();
			//System.out.println(schemaFile);
			//System.out.println("\t" + schema);
		}catch(UnsupportedOperationException e){
			System.out.println(schemaFile);
			System.out.println(e.getClass().getName()+':'+e.getMessage());
			fail(schemaFile+" create error "+e.getClass().getName()+": "+e.getMessage());
		}catch(Exception e){
			System.out.println(schemaFile);
			System.out.println(e.getClass().getName()+':'+e.getMessage());
			//e.printStackTrace();
			fail(schemaFile+" create error "+e.getClass().getName()+": "+e.getMessage());
		}
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
	
}
