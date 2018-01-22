package fr.univLille.cristal.shex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.jsonldjava.utils.JsonUtils;

public class Configuration {
	public static Path shexTestPath = Paths.get("test");
	public static Path manifest_json = Paths.get("test","success","validation","manifest.jsonld");
	
	
	public static List<Object[]> getTestFromDirectory(Path testDirectory,int result) throws IOException{
		List<Object[]> listOfParameters = new LinkedList<Object[]>();

		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(testDirectory);
		for (Path path : directoryStream) {
			if (path.toString().endsWith(".json")) {
				Object [] parameters = new Object[2]; 
				parameters[0] = path;
				parameters[1] = result;
				listOfParameters.add(parameters);

			}
		}
		
		return listOfParameters;
	}
	
	
	// TODO: finish this function....
	public static List<Object[]> getJsonTestFromManifest(Set<String> excludedTraits) throws IOException{
		List<Object[]> listOfParameters = new LinkedList<Object[]>();
		
		InputStream inputStream = new FileInputStream(Configuration.manifest_json.toFile());
		Object manifest = JsonUtils.fromInputStream(inputStream);
		
		List<Map> tests = (List<Map>) ((Map) ((List) ((Map) manifest).get("@graph")).get(0)).get("entries");
	
		Set<Path> selectedSchema = new HashSet<Path>();
		for (Map test:tests) {
			Set<String> traits = new HashSet<String>();
			traits.addAll((List<String>) test.get("trait"));
			
			boolean useThisTest = true;
			for(String trait:excludedTraits) {
				if (traits.contains(trait)){
					useThisTest=false;
				}
			}
			
			if (useThisTest) {
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
		return null;
	}
}
