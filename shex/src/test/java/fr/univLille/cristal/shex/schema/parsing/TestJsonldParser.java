package fr.univLille.cristal.shex.schema.parsing;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.github.jsonldjava.utils.JsonUtils;

import fr.univLille.cristal.shex.ConfigurationTest;
import fr.univLille.cristal.shex.schema.ShexSchema;


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
//			//paths[i]=Paths.get("/home/jdusart/Documents/Shex/workspace/shex-java/",selected[i]+".json");
//			paths[i]=Paths.get(Configuration.shexTextPath.toString(),"schemas",selected[i]+".json");
//		}
		
		InputStream inputStream = new FileInputStream(ConfigurationTest.manifest_json.toFile());
		Object manifest = JsonUtils.fromInputStream(inputStream);
		
		List<Map> tests = (List<Map>) ((Map) ((List) ((Map) manifest).get("@graph")).get(0)).get("entries");
	
		Set<Path> selectedSchema = new HashSet<Path>();
		for (Map test:tests) {
			Set<String> traits = new HashSet<String>();
			traits.addAll((List<String>) test.get("trait"));
			if (!(traits.contains("Stem") | traits.contains("SemanticAction") | traits.contains("Start"))) {
				Map action = (Map) test.get("action");
				String jsonPath = ((String) action.get("schema")).replaceAll(".shex", ".json");
				Path path = Paths.get(ConfigurationTest.shexTestPath.toString(),"success","validation",jsonPath).normalize();
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
		return listOfParameters;
	}
		
	@Parameter
	public Path schemaFile;
	@Parameter(1)
	public int status;
		
	@Test
	public void parse (){
		try {
			ShexSchema schema = GenParser.parseSchema(schemaFile);
			if (status!=0) {
				fail("Error: test success but it should fail.");
			}
			//System.out.println(schemaFile);
			//System.out.println("\t" + schema);
		}catch(UnsupportedOperationException e){
			System.out.println(schemaFile);
			System.out.println(e.getClass().getName()+':'+e.getMessage());
			fail(schemaFile+" create error "+e.getClass().getName()+": "+e.getMessage());
		}catch(Exception e){
			System.out.println(schemaFile);
			System.out.println(e.getClass().getName()+':'+e.getMessage());
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
