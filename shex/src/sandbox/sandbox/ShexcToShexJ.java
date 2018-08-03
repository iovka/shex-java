package sandbox;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonGenerationException;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.schema.parsing.ShExJSerializer;

public class ShexcToShexJ {
	
	
	private static ShexSchema getSchema (String schemaFileName) {
		ShexSchema schema;
		try {
			schema = GenParser.parseSchema(Paths.get(schemaFileName));
		} catch (IOException e) {
			System.err.println("Error reading the schema file.");
			System.err.println("Caused by: ");
			System.err.println(e.getMessage());;
			return null;
		} catch (Exception e) {
			System.err.println("Error while parsing the schema file. Caused by: " + e.getMessage());
			e.printStackTrace(System.err);
			return null;
		}
		return schema;
	}
	
	private static void printSchema (ShexSchema schema) throws JsonGenerationException, IOException {
		ShExJSerializer.ToJson(schema, Paths.get("/tmp/schema.json"));
	}
	
	public static void main(String[] args) throws JsonGenerationException, IOException {
		ShexSchema sch = ShexcToShexJ.getSchema("/tmp/schema.shex");
		ShexcToShexJ.printSchema(sch);
	}
}
