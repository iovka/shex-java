package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import es.weso.shex.Schema;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import scala.util.Try;

public class ShExCParser {
	static String readFile(String path) throws IOException 	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
	
	public static void main(String[] args) throws IOException, UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		String fileContent = readFile("../../shexTest/schemas/1bnodeRef1.shex");
		Try<Schema> trySchema = Schema.fromString(fileContent, "SHEXC",null);
		Schema sh = trySchema.get();
		ConverterFromShaclex converter = new ConverterFromShaclex(sh);
		converter.convert();
	}
}
