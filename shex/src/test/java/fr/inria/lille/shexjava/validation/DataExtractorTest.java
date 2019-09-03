package fr.inria.lille.shexjava.validation;

import java.io.ByteArrayInputStream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.ShExCParser;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParsing;

public class DataExtractorTest {
	
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
	static Triple n2_a_v23 = GlobalFactory.RDFFactory.createTriple(n2, a, human);
	static Triple n2_first_paul = GlobalFactory.RDFFactory.createTriple(n2, first, paul);
	static Triple n2_last_east = GlobalFactory.RDFFactory.createTriple(n2, last, eastwood);
	static Triple n3_a_v23 = GlobalFactory.RDFFactory.createTriple(n3, a, human);
	static Triple n3_first_alien = GlobalFactory.RDFFactory.createTriple(n3, first, alien);

	Graph graph;
	
	public DataExtractorTest() {
		parser = new ShapeMapParsing();
		shexParser = new ShExCParser();
		
		graph = GlobalFactory.RDFFactory.createGraph();
		graph.add(n1_a_human);
		graph.add(n1_first_john);
		graph.add(n1_last_smith);
		graph.add(n2_a_v23);
		graph.add(n2_first_paul);
		graph.add(n2_last_east);
		graph.add(n3_a_v23);
		graph.add(n3_first_alien);
	}
	
	@Test
	public void testWithShapeLabel() {		
		String schemaSt = "<http://inria.fr/Person> { a IRI; <http://a.b/first> IRI; }";
		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));
			MatchingCollector mColl = new MatchingCollector();
			
			RefineValidation algo = new RefineValidation(schema, graph);
			algo.addMatchingObserver(mColl);
			algo.validate();
			
			Graph result = GlobalFactory.RDFFactory.createGraph();
			DataExtractor extractor = new DataExtractor();
			extractor.extractValidPart(schema, algo.getTyping(), mColl, result);
			System.out.println("Result:");
			result.stream().forEach(tr -> System.out.println(tr));
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test2WithShapeLabel() {
		String schemaSt = "<http://inria.fr/Person> { a IRI; <http://a.b/last> IRI; }";
		try {
			ShexSchema schema = new ShexSchema(shexParser.getRules(new ByteArrayInputStream(schemaSt.getBytes())));
			MatchingCollector mColl = new MatchingCollector();
			
			RefineValidation algo = new RefineValidation(schema, graph);
			algo.addMatchingObserver(mColl);
			algo.validate();
			
			Graph result = GlobalFactory.RDFFactory.createGraph();
			DataExtractor extractor = new DataExtractor();
			extractor.extractValidPart(schema, algo.getTyping(), mColl, result);
			System.out.println("Result:");
			result.stream().forEach(tr -> System.out.println(tr));
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
}
