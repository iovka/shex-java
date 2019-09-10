package fr.inria.lille.shexjava.validation;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.ShExCParser;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParsing;

public class ComputationControllerTest {
	protected ShapeMapParsing parser = new ShapeMapParsing();
	protected ShExCParser shexParser = new ShExCParser();

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
	static Triple n2_a_v23 = GlobalFactory.RDFFactory.createTriple(n2, a, human);
	static Triple n2_first_paul = GlobalFactory.RDFFactory.createTriple(n2, first, paul);
	static Triple n2_last_east = GlobalFactory.RDFFactory.createTriple(n2, last, eastwood);
	static Triple n3_a_v23 = GlobalFactory.RDFFactory.createTriple(n3, a, human);
	static Triple n3_first_alien = GlobalFactory.RDFFactory.createTriple(n3, first, alien);
	
	@Test
	public void testNoComputationController() {
		Graph graph = GlobalFactory.RDFFactory.createGraph();
		graph.add(n1_a_human);
		graph.add(n1_first_john);
		graph.add(n1_last_smith);
		graph.add(n2_a_v23);
		graph.add(n2_first_paul);
		graph.add(n2_last_east);
		graph.add(n3_a_v23);
		graph.add(n3_first_alien);

		String schemaSt = "<http://inria.fr/Person> { a IRI; <http://a.b/first> IRI; <http://a.b/last> IRI }";
		String shMap = "{ FOCUS a _ } @<http://inria.fr/Person>";
		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));
			BaseShapeMap shapeMap = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(shapeMap.getAssociations().size(), 1);
			
			RecursiveValidationWithMemorization algo = new RecursiveValidationWithMemorization(schema, graph);
			algo.validate(shapeMap,new NoComputationController());
			fail("exception expected.");
		} catch ( Exception e) {
			if (!(e instanceof CompControllerException))
				fail("Not the right exception");
		}
	}
	
	class CompControllerException extends Exception{}
	
	class NoComputationController implements ComputationController{

		@Override
		public void start() {
		}

		@Override
		public void canContinue() throws Exception {
			throw new CompControllerException();			
		}
		
	}

	@Test
	public void testWithShapeLabel() {
		Graph graph = GlobalFactory.RDFFactory.createGraph();
		graph.add(n1_a_human);
		graph.add(n1_first_john);
		graph.add(n1_last_smith);
		graph.add(n2_a_v23);
		graph.add(n2_first_paul);
		graph.add(n2_last_east);
		graph.add(n3_a_v23);
		graph.add(n3_first_alien);

		String schemaSt = "<http://inria.fr/Person> { a IRI; <http://a.b/first> IRI; <http://a.b/last> IRI }";
		String shMap = "{ FOCUS a _ } @<http://inria.fr/Person>";
		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));
			BaseShapeMap shapeMap = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(shapeMap.getAssociations().size(), 1);
			
			RecursiveValidationWithMemorization algo = new RecursiveValidationWithMemorization(schema, graph);
			algo.validate(shapeMap,new LimitComputationController());
			fail("exception expected.");
		} catch ( Exception e) {
			if (!(e instanceof CompControllerException))
				fail("Not the right exception");
		}
	}
	
	
	class LimitComputationController implements ComputationController{
		int i=0;
		
		@Override
		public void start() {
		}

		@Override
		public void canContinue() throws Exception {
			i++;
			if (i>2)
				throw new CompControllerException();			
		}
		
	}

}
