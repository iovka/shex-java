package sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

public class ParseRDFWithRio {
	
	public static void main(String[] args) throws IOException {
	
		java.net.URL documentUrl = new URL("file:///home/io/Documents/Research/Projects/Shex/github/shexTest/validation/manifest.ttl");
		InputStream inputStream = documentUrl.openStream();
		
		Model results = Rio.parse(inputStream, documentUrl.toString(), RDFFormat.TURTLE);

	}
	
	

}
