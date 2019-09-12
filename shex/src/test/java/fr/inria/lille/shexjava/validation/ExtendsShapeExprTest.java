package fr.inria.lille.shexjava.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.ShExCParser;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.ResultShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParsing;

public class ExtendsShapeExprTest {
	
	protected ShapeMapParsing parser;
	protected ShExCParser shexParser;

	static IRI a = GlobalFactory.RDFFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	static IRI n1 = GlobalFactory.RDFFactory.createIRI("http://a.b/n1");
	static IRI n2 = GlobalFactory.RDFFactory.createIRI("http://a.b/n2");
	static IRI n3 = GlobalFactory.RDFFactory.createIRI("http://a.b/n3");
	static IRI john = GlobalFactory.RDFFactory.createIRI("http://a.b/node/john");
	static IRI smith = GlobalFactory.RDFFactory.createIRI("http://a.b/node/smith");
	static IRI paul = GlobalFactory.RDFFactory.createIRI("http://a.b/node/paul");
	static IRI eastwood = GlobalFactory.RDFFactory.createIRI("http://a.b/node/eastwood");
	static IRI alien = GlobalFactory.RDFFactory.createIRI("http://a.b/node/alien");
	static IRI first = GlobalFactory.RDFFactory.createIRI("http://a.b/first");
	static IRI last = GlobalFactory.RDFFactory.createIRI("http://a.b/last");
	static IRI human = GlobalFactory.RDFFactory.createIRI("http://a.b/human");

	static Triple n1_a_human = GlobalFactory.RDFFactory.createTriple(n1, a, human);
	static Triple n1_first_john = GlobalFactory.RDFFactory.createTriple(n1, first, john);
	static Triple n1_last_smith = GlobalFactory.RDFFactory.createTriple(n1, last, smith);
	static Triple n2_a_human = GlobalFactory.RDFFactory.createTriple(n2, a, human);
	static Triple n2_first_paul = GlobalFactory.RDFFactory.createTriple(n2, first, paul);
	static Triple n2_last_east = GlobalFactory.RDFFactory.createTriple(n2, last, eastwood);
	static Triple n3_a_human = GlobalFactory.RDFFactory.createTriple(n3, a, human);
	static Triple n3_first_alien = GlobalFactory.RDFFactory.createTriple(n3, first, alien);

	Graph graph;
	
	public ExtendsShapeExprTest() {
		parser = new ShapeMapParsing();
		shexParser = new ShExCParser();
		
		graph = GlobalFactory.RDFFactory.createGraph();
		graph.add(n1_a_human);
		graph.add(n1_first_john);
		graph.add(n1_last_smith);
		graph.add(n2_a_human);
		graph.add(n2_first_paul);
		graph.add(n2_last_east);
		graph.add(n3_a_human);
		graph.add(n3_first_alien);
	}
	
	@Test
	public void basicExtendsWithNoRepetition() {		
		String schemaSt = "<http://inria.fr/Human> { a IRI } \n"
						+ "<http://inria.fr/Person> EXTENDS @<http://inria.fr/Human> { <http://a.b/first> IRI; }";
		String shMap = "{ FOCUS a _ } @<http://inria.fr/Person>";
		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));		
			BaseShapeMap shapeMap = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			RecursiveValidation valago = new RecursiveValidation(schema, graph);
			
			ResultShapeMap result = valago.validate(shapeMap);
			
			for(ShapeAssociation asso:result.getAssociations())
				assertTrue(asso.getStatus().get().equals(Status.CONFORMANT));			
		} catch ( Exception e) {
			fail("Exception during the parsing");
		}
	}
	
	@Test
	public void basicExtendsWithNoRepetition2() {		
		String schemaSt = "<http://inria.fr/Human> { a IRI } \n"
						+ "<http://inria.fr/Person> EXTENDS @<http://inria.fr/Human> { <http://a.b/first> IRI; <http://a.b/last> IRI }";
		String shMap = "{ FOCUS a _ } @<http://inria.fr/Person>";

		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));			
			BaseShapeMap shapeMap = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			RecursiveValidation valago = new RecursiveValidation(schema, graph);
			
			ResultShapeMap result = valago.validate(shapeMap);

			for(ShapeAssociation asso:result.getAssociations()) {
				RDFTerm node = asso.getNodeSelector().apply(graph).iterator().next();
				if (node.equals(n3))
					assertTrue(asso.getStatus().get().equals(Status.NONCONFORMANT));
				else
					assertTrue(asso.getStatus().get().equals(Status.CONFORMANT));
			}
		} catch ( Exception e) {
			e.printStackTrace();
			fail("Exception during the parsing");
		}
	}
	
}
