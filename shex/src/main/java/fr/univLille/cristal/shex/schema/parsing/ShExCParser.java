package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import es.weso.shex.Schema;
import scala.util.Try;

public class ShExCParser {
	static String readFile(String path) throws IOException 	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
	
	public static void main(String[] args) throws IOException {
		String fileContent = readFile("../../shexTest/schemas/1bnodeRef1.shex");
		Try<Schema> trySchema = Schema.fromString(fileContent, "SHEXC",null);
		Schema sh = trySchema.get();
		ShaclexConverter converter = new ShaclexConverter(sh);
		converter.convert();
	}
}
