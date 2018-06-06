package fr.inria.lille.shexjava.validation;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.junit.Test;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.Pair;

public class TestMemorizationAlgorithm {
	private final static RDF4J rdfFactory = new RDF4J();

	@Test
	public void test0Cardinality1() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","0cardinality.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		System.out.println("SCHEMA:");
		System.out.println(schema);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		//create an user
		BlankNodeOrIRI n1 =  rdfFactory.createIRI("http://a.example/n1");
		BlankNodeOrIRI n2 =  rdfFactory.createIRI("http://a.example/n2");
		graph.add(n1,rdfFactory.createIRI("http://a.example/a"),n2);
		graph.add(n2,rdfFactory.createIRI("http://a.example/b"),n1);		
		graph.add(n1,rdfFactory.createIRI("http://a.example/a"),rdfFactory.createLiteral("test"));
		
		System.out.println();
		System.out.println("MODEL:");
		Iterator<Triple> ite = graph.iterate().iterator();
		while(ite.hasNext())
			System.out.println(ite.next());
		
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(n1, new Label(rdfFactory.createIRI("http://a.example/S")));
		System.out.println();
		System.out.println("TYPING:");
		for (Pair<RDFTerm,Label> pair:validation.getTyping().getAllStatus().keySet())
			if (!pair.two.isGenerated())
				System.out.println(pair+":"+validation.getTyping().getStatus(pair.one,pair.two));
		if (validation.getTyping().isConformant(n1, new Label(rdfFactory.createIRI("http://a.example/S"))))
				fail();
	}
	
}
