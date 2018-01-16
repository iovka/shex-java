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
package fr.univLille.cristal.shex.schema.analysis;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.utils.JsonUtils;

import fr.univLille.cristal.shex.Configuration;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;

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
public class TestCyclicReferences {
	
	
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
//			//paths[i]=Paths.get("/home/jdusart/Documents/Shex/workspace/shex-java/",selected[i]+".json");
//			paths[i]=Paths.get(Configuration.shexTextPath.toString(),"schemas",selected[i]+".json");
//		}
		
		InputStream inputStream = new FileInputStream(Configuration.manifest_json.toFile());
		Object manifest = JsonUtils.fromInputStream(inputStream);
		
		List<Map> tests = (List<Map>) ((Map) ((List) ((Map) manifest).get("@graph")).get(0)).get("entries");
	
		Set<Path> selectedSchema = new HashSet<Path>();
		for (Map test:tests) {

			Set<String> traits = new HashSet<String>();
			traits.addAll((List<String>) test.get("trait"));
			if (!(traits.contains("Stem") | traits.contains("SemanticAction") | traits.contains("Start") | traits.contains("Import"))) {
				Map action = (Map) test.get("action");
				String jsonPath = ((String) action.get("schema")).replaceAll(".shex", ".json");
				Path path = Paths.get(Configuration.shexTestPath.toString(),"success","validation",jsonPath).normalize();
				if (path.toFile().exists()) {
					selectedSchema.add(path);
				}

			}
		}
		
		for (Path path : selectedSchema) {
			if (path.toString().endsWith(".json")) {
				Object [] parameters = new Object[2]; 
				parameters[0] = path;
				parameters[1] = 0;
				listOfParameters.add(parameters);

			}
		}
		
		listOfParameters = new LinkedList<Object[]>();
		Path undefinedShapeRefDir = Paths.get(Configuration.shexTestPath.toString(),"failure","CyclicReferences");
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(undefinedShapeRefDir);
		for (Path path : directoryStream) {
			if (path.toString().endsWith(".json")) {
				Object [] parameters = new Object[2]; 
				parameters[0] = path;
				parameters[1] = 1;
				listOfParameters.add(parameters);

			}
		}
		
		return listOfParameters;
	}
		
	@Parameter
	public Path schemaFile;
	@Parameter(1)
	public int status;
		
	@Test
	public void parse (){
		try {
			System.out.println(schemaFile);
			JsonldParser parser = new JsonldParser(schemaFile);
			ShexSchema schema = parser.parseSchema();
			schema.finalize();
//			if (!undefinedLabel.isEmpty() & status==0) {
//				System.out.println(schemaFile);
//				fail("Error: undefined label found when all should be defined.");
//			}
//			if (undefinedLabel.isEmpty() & status==1) {
//				System.out.println(schemaFile);
//				fail("Error: undefined label not found when some should be defined.");
//			}
		}catch(Exception e){
			System.out.println(schemaFile);
			System.out.println(e.getClass().getName()+':'+e.getMessage());
			e.printStackTrace();
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
