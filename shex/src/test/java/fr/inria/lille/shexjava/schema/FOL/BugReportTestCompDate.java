package fr.inria.lille.shexjava.schema.FOL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.FOL.formula.Formula;
import fr.inria.lille.shexjava.schema.FOL.parsing.FOLVisitorImpl;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;
import fr.inria.lille.shexjava.validation.RefineValidation;

class BugReportTestCompDate {
	private final static RDF4J rdfFactory = new RDF4J();
	private final static ValueFactory rdf4JFactory = SimpleValueFactory.getInstance();
	
	
	@Test
	void testDate() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","bugreport.json");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		System.out.println("SCHEMA:");
		for (Label l:schema.getRules().keySet())
			System.out.println(l+" >> "+schema.getRules().get(l));
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		Calendar date = Calendar.getInstance();
		//create an user
		BlankNodeOrIRI user1 =  rdfFactory.createBlankNode("user1");
		graph.add(user1,rdfFactory.createIRI("http://a.example/name"),rdfFactory.createLiteral("Mr. Smith"));
		// create emp1
		BlankNodeOrIRI emp1 =  rdfFactory.createBlankNode("emp1");
		graph.add(emp1,rdfFactory.createIRI("http://a.example/name"),rdfFactory.createLiteral("Mrs. Smith"));
		graph.add(emp1,rdfFactory.createIRI("http://a.example/email"),rdfFactory.createLiteral("eva@h.org"));
		//create bug1
		BlankNodeOrIRI bug1 = rdfFactory.createBlankNode("bug1");
		graph.add(bug1,rdfFactory.createIRI("http://a.example/descr"),rdfFactory.createLiteral("Kaboom!"));
		date.set(2012,11,4,0,0,0); //4 décembre 2012 à 00:00:00
		Literal lDate = rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(date.getTime()));
		graph.add(bug1,rdfFactory.createIRI("http://a.example/reportedOn"),lDate);
		graph.add(bug1,rdfFactory.createIRI("http://a.example/reportedBy"),user1);
		date.set(2012,11,6,0,0,0); //6 décembre 2012 à 00:00:00
		lDate = rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(date.getTime()));
		graph.add(bug1,rdfFactory.createIRI("http://a.example/reproducedOn"),lDate);
		graph.add(bug1,rdfFactory.createIRI("http://a.example/reproducedBy"),emp1);
		//create bug2
		BlankNodeOrIRI bug2 = rdfFactory.createBlankNode("bug2");
		graph.add(bug2,rdfFactory.createIRI("http://a.example/descr"),rdfFactory.createLiteral("Bham!"));
		date.set(2013,10,2,0,0,0); //2 novembre 2013 à 00:00:00
		lDate = rdfFactory.asRDFTerm(rdf4JFactory.createLiteral(date.getTime()));
		graph.add(bug2,rdfFactory.createIRI("http://a.example/reportedOn"),lDate);
		graph.add(bug2,rdfFactory.createIRI("http://a.example/reportedBy"),emp1);
		//linking bug 1 et bug 2
		graph.add(bug1,rdfFactory.createIRI("http://a.example/related"),bug2);
		graph.add(bug2,rdfFactory.createIRI("http://a.example/related"),bug1);
		System.out.println();
		System.out.println("MODEL:");
		Iterator<Triple> ite = graph.iterate().iterator();
		while(ite.hasNext())
			System.out.println(ite.next());
		
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(bug1, new Label(rdfFactory.createIRI("http://a.example/BugReport")));
		System.out.println();
		System.out.println("TYPING (only the not generated):");
		for (Pair<RDFTerm,Label> pair:validation.getTyping().asSet())
			if (!pair.two.isGenerated())
				System.out.println(pair);
		
		//build the maps for evaluation...
		List<RDFTerm> values = new ArrayList<>(CommonGraph.getAllNodes(graph));

		
		Set<Pair<RDFTerm,Label>> shapes = new HashSet<Pair<RDFTerm,Label>>();
		for (Pair<RDFTerm,Label> pair:validation.getTyping().asSet())
			if (!pair.two.isGenerated())
				shapes.add(pair);

		Set<Pair<Pair<RDFTerm,RDFTerm>, Label>> triples = new HashSet<Pair<Pair<RDFTerm,RDFTerm>, Label>>();
		for (Pair<RDFTerm,Label> p1:shapes)
			for (Pair<Triple,Label> aff:validation.getTyping().getMatch(p1.one, p1.two))
				if (!aff.two.isGenerated()) {
					Pair<RDFTerm,RDFTerm> couple;
					if (aff.one.getSubject().equals(p1.one))
						couple = new Pair<RDFTerm,RDFTerm>(p1.one,aff.one.getObject());
					else
						couple = new Pair<RDFTerm,RDFTerm>(p1.one,aff.one.getSubject());
					triples.add(new Pair<Pair<RDFTerm,RDFTerm>, Label>(couple,aff.two));
				}
		
		String shape = "<http://a.example/BugReport>(x)";
		String report = "<http://a.example/reportDate>(x,x1)";
		String reprod = "<http://a.example/reproducedDate>(x,x2)";
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
		
		shape = "<http://a.example/BugReport>(x)";
		report = "<http://a.example/reportDate>(x,x1)";
		reprod = "<http://a.example/reproducedDate>(x,x2)";
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
