package fr.inria.lille.shexjava.validation;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.junit.Test;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

public class DebugValidation {
	
	@Test
	public void runDebugValidation() throws Exception {
		RDF4J factory = (RDF4J) GlobalFactory.RDFFactory;
		
		
		Path schemaFile = Paths.get("schema.shex"); //to change with what you want 
		Path dataFile = Paths.get("data.ttl"); //to change with what you want 
		
		ShexSchema schema = GenParser.parseSchema(schemaFile);
		for (Label label:schema.getRules().keySet())
			System.out.println(label+": "+schema.getRules().get(label));
		
		
		 // load the model
		System.out.println("Reading data");
		String baseIRI = "http://a.example.shex/";
		Model data = Rio.parse(new FileInputStream(dataFile.toFile()), baseIRI, RDFFormat.TURTLE);
		Iterator<Statement> ite = data.iterator();
		while (ite.hasNext())
			System.out.println(ite.next());
		
		// create the graph
		Graph dataGraph = factory.asGraph(data);
		
		ValidationAlgorithmAbstract validation = new RefineValidation(schema, dataGraph);  
		FailureAnalyzer fa = new FailureAnalyzerSimple();
		validation.addFailureReportsCollector(fa);
		//validate
		validation.validate(null, null);
	}

}
