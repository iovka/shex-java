package sandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.graph.JenaGraph;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;
import fr.univLille.cristal.shex.validation.RefineValidation;
import fr.univLille.cristal.shex.validation.ValidationAlgorithm;

public class HelloWorld {
	
	public static void main(String[] args) throws IOException, JsonLdError {
		String schemaFileName = "/tmp/schema.json"; //
		String dataFileName = "/tmp/data.ttl";

		
		JsonldParser parser = new JsonldParser(Paths.get(schemaFileName));
		ShexSchema schema = parser.parseSchema();
		
		Model model = ModelFactory.createDefaultModel();
		model.read(new FileInputStream(dataFileName), null, "TURTLE");
		RDFGraph graph = new JenaGraph(model);

		System.out.println(graph);
		
		ValidationAlgorithm val = new RefineValidation(schema, graph);
		val.validate(null, null);
		System.out.println(val.getTyping());
	}
	

}
