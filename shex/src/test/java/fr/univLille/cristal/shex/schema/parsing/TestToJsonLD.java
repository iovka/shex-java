package fr.univLille.cristal.shex.schema.parsing;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.Configuration;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;

public class TestToJsonLD {

	public static void main(String[] args) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException, IOException, JsonLdError, UnsupportedOperationException, ParseException {
		Path schemaFile = Paths.get(Configuration.shexTestPath.toString(),"success","TestReferences","ShapeReferences.json");;
		JsonldParser parser = new JsonldParser(schemaFile);
		ShexSchema schema = parser.parseSchema();
		schema.finalize();
		
		Path destFile = Paths.get(Configuration.shexTestPath.toString(),"..","Output-ShapeExtra.json");;
		schema.toJsonLD(destFile);
	}

}
