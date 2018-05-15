package fr.inria.lille.shexjava.schema.FOL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;

import fr.inria.lille.shexjava.graph.NeighborTriple;
import fr.inria.lille.shexjava.graph.RDF4JGraph;
import fr.inria.lille.shexjava.graph.RDFGraph;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.FOL.formula.Formula;
import fr.inria.lille.shexjava.schema.FOL.parsing.FOLVisitorImpl;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.Pair;
import fr.inria.lille.shexjava.validation.RefineValidation;

class BugReportTestPrimKeyDiff {
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();

	@Test
	void testDate() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","bugreportprimkey.json");
		ShexSchema schema = GenParser.parseSchema(schema_file);
		System.out.println("SCHEMA:");
		for (Label l:schema.getRules().keySet())
			System.out.println(l+" >> "+schema.getRules().get(l));
		
		Model model = new LinkedHashModel();
		//create an user
		Resource user1 =  rdfFactory.createBNode("user1");
		model.add(user1,rdfFactory.createIRI("http://a.example/name"),rdfFactory.createLiteral("Mr. Smith"));
		// create emp1
		Resource emp1 =  rdfFactory.createBNode("emp1");
		model.add(emp1,rdfFactory.createIRI("http://a.example/name"),rdfFactory.createLiteral("Mrs. Smith"));
		model.add(emp1,rdfFactory.createIRI("http://a.example/email"),rdfFactory.createLiteral("eva@h.org"));
		//create bug1
		Resource bug1 = rdfFactory.createBNode("bug1");
		model.add(bug1,rdfFactory.createIRI("http://a.example/descr"),rdfFactory.createLiteral("Kaboom!"));
		model.add(bug1,rdfFactory.createIRI("http://a.example/primkey"),rdfFactory.createLiteral(102));
		Calendar date = Calendar.getInstance();
		date.set(2012,11,4,0,0,0); //4 décembre 2012 à 00:00:00
		model.add(bug1,rdfFactory.createIRI("http://a.example/reportedOn"),rdfFactory.createLiteral(date.getTime()));
		model.add(bug1,rdfFactory.createIRI("http://a.example/reportedBy"),user1);
		date.set(2012,11,6,0,0,0); //6 décembre 2012 à 00:00:00
		model.add(bug1,rdfFactory.createIRI("http://a.example/reproducedOn"),rdfFactory.createLiteral(date.getTime()));
		model.add(bug1,rdfFactory.createIRI("http://a.example/reproducedBy"),emp1);
		//create bug2
		Resource bug2 = rdfFactory.createBNode("bug2");
		model.add(bug2,rdfFactory.createIRI("http://a.example/primkey"),rdfFactory.createLiteral(1052));
		model.add(bug2,rdfFactory.createIRI("http://a.example/descr"),rdfFactory.createLiteral("Bham!"));
		date.set(2013,10,2,0,0,0); //2 novembre 2013 à 00:00:00
		model.add(bug2,rdfFactory.createIRI("http://a.example/reportedOn"),rdfFactory.createLiteral(date.getTime()));
		model.add(bug2,rdfFactory.createIRI("http://a.example/reportedBy"),emp1);
		//linking bug 1 et bug 2
		model.add(bug1,rdfFactory.createIRI("http://a.example/related"),bug2);
		model.add(bug2,rdfFactory.createIRI("http://a.example/related"),bug1);
		System.out.println();
		System.out.println("MODEL:");
		Iterator<Statement> ite = model.iterator();
		while(ite.hasNext())
			System.out.println(ite.next());
		
		RDFGraph graph = new RDF4JGraph(model);
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(bug1, new Label(rdfFactory.createIRI("http://a.example/BugReport")));
		System.out.println();
		System.out.println("TYPING (only the not generated):");
		for (Pair<Value,Label> pair:validation.getTyping().asSet())
			if (!pair.two.isGenerated())
				System.out.println(pair);
		
		//build the maps for evaluation...
		List<Value> values = new ArrayList<Value>();
		Iterator<Value> ite2 = graph.listAllNodes();
		while (ite2.hasNext()) 
			values.add(ite2.next());
		
		Set<Pair<Value,Label>> shapes = new HashSet<Pair<Value,Label>>();
		for (Pair<Value,Label> pair:validation.getTyping().asSet())
			if (!pair.two.isGenerated())
				shapes.add(pair);

		Set<Pair<Pair<Value,Value>, Label>> triples = new HashSet<Pair<Pair<Value,Value>, Label>>();
		for (Pair<Value,Label> p1:shapes)
			for (Pair<NeighborTriple,Label> aff:validation.getTyping().getMatch(p1.one, p1.two))
				if (!aff.two.isGenerated()) {
					Pair<Value,Value> couple;
					if (aff.one.getFocus().equals(p1.one))
						couple = new Pair<Value,Value>(p1.one,aff.one.getOpposite());
					else
						couple = new Pair<Value,Value>(p1.one,aff.one.getFocus());
					triples.add(new Pair<Pair<Value,Value>, Label>(couple,aff.two));
				}
		
		System.out.println(triples);
		
		FOLVisitorImpl folVisitor = new FOLVisitorImpl();
		String shapeX = "<http://a.example/BugReport>(x)";
		String shapeY = "<http://a.example/BugReport>(y)";
		String keyX = "<http://a.example/primkey>(x,x1)";
		String keyY = "<http://a.example/primkey>(y,y1)";
		String text = "forall x forall y forall x1 forall y1 ->(and("+shapeX+","+shapeY+",x!=y,"+keyX+","+keyY+"),(x1!=y1))";
		ArrayList<Formula> formulas = folVisitor.visitFormulas(text);
		System.out.println();
		System.out.println("FORMULAS:");
		for (Formula f:formulas) {
			System.out.println(f);
			if (f.evaluate(values, shapes, triples))
				System.out.println("Formula is verified.");
			else
				System.out.println("Affectations "+f.getLastAffectations()+" failed the formula.");
		}
	}

}
