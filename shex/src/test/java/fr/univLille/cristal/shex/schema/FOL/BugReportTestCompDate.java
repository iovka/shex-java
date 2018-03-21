package fr.univLille.cristal.shex.schema.FOL;

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

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.FOL.formula.Formula;
import fr.univLille.cristal.shex.schema.FOL.parsing.FOLVisitorImpl;
import fr.univLille.cristal.shex.schema.analysis.Configuration;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.util.Pair;
import fr.univLille.cristal.shex.validation.RefineValidation;

class BugReportTestCompDate {
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();

	@Test
	void testDate() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","bugreport.json");
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
		Calendar date = Calendar.getInstance();
		date.set(2012,11,4,0,0,0); //4 décembre 2012 à 00:00:00
		model.add(bug1,rdfFactory.createIRI("http://a.example/reportedOn"),rdfFactory.createLiteral(date.getTime()));
		model.add(bug1,rdfFactory.createIRI("http://a.example/reportedBy"),user1);
		date.set(2012,11,6,0,0,0); //6 décembre 2012 à 00:00:00
		model.add(bug1,rdfFactory.createIRI("http://a.example/reproducedOn"),rdfFactory.createLiteral(date.getTime()));
		model.add(bug1,rdfFactory.createIRI("http://a.example/reproducedBy"),emp1);
		//create bug2
		Resource bug2 = rdfFactory.createBNode("bug2");
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
		
		String shape = "<<http://a.example/BugReport>>(x)";
		String report = "<<http://a.example/reportDate>>(x,x1)";
		String reprod = "<<http://a.example/reproducedDate>>(x,x2)";
		String text = "forall x forall x1 forall x2 ->(and("+shape+","+report+","+reprod+"),x1<x2)";
		ArrayList<Formula> formulas = FOLVisitorImpl.parseFormulas(text);
		System.out.println();
		System.out.println("FORMULAS:");
		for (Formula f:formulas) {
			System.out.println(f);
			if (f.evaluate(values, shapes, triples))
				System.out.println("Formula is verified.");
			else
				System.out.println("Affectations "+f.getLastAffectations()+" failed the formula.");
		}
		
		shape = "<<http://a.example/BugReport>>(x)";
		report = "<<http://a.example/reportDate>>(x,x1)";
		reprod = "<<http://a.example/reproducedDate>>(x,x2)";
		text = "forall x forall x1 forall x2 ->(and("+shape+","+report+","+reprod+"),x2<x1)";
		formulas = FOLVisitorImpl.parseFormulas(text);
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
