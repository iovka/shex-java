package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

import es.weso.shex.Schema;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;
import scala.util.Try;

public class ShExCParser implements Parser{
	private Path path;

	public ShExCParser(Path path) {
		this.path = path;
	}


	@Override
	public ShexSchema parseSchema() throws IOException, ParseException, UndefinedReferenceException,
	CyclicReferencesException, NotStratifiedException {
		byte[] encoded = Files.readAllBytes(this.path);
		String fileContent = new String(encoded, Charset.defaultCharset());
		Try<Schema> trySchema = Schema.fromString(fileContent, "SHEXC",null);
		Schema sh = trySchema.get();
		ConverterFromShaclex converter = new ConverterFromShaclex(sh);
		converter.convert();
		ShexSchema result = converter.convert();
		return result;
	}
}
