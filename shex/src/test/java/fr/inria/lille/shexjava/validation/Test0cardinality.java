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

public class Test0cardinality {
	private final static RDF4J rdfFactory = new RDF4J();

	@Test
	public void test0Cardinality1() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","0cardinality.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		BlankNodeOrIRI s1 =  rdfFactory.createIRI("http://a.example/s1");
		graph.add(s1,rdfFactory.createIRI("http://a.example/a"),rdfFactory.createLiteral("test"));
				
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(s1, new Label(rdfFactory.createIRI("http://a.example/S")));

		if (validation.getTyping().isConformant(s1, new Label(rdfFactory.createIRI("http://a.example/S"))))
				fail();
	}
	
	@Test
	public void test0Cardinality2() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","0cardinality2.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		//create an user
		BlankNodeOrIRI s1 =  rdfFactory.createIRI("http://a.example/s1");
		graph.add(s1,rdfFactory.createIRI("http://a.example/c"),rdfFactory.createLiteral("test"));
		
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(s1, new Label(rdfFactory.createIRI("http://a.example/S")));
		
		if (validation.getTyping().isNonConformant(s1, new Label(rdfFactory.createIRI("http://a.example/S"))))
			fail();
	}

}
