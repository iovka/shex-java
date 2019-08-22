/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.inria.lille.shexjava.validation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.Test;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.util.Pair;

public class CertificateCollector {
	private final static RDF4J rdfFactory = new RDF4J();
	private final static ValueFactory rdf4JFactory = SimpleValueFactory.getInstance();
	
	@Test
	public void CertificateCollectorTest1() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","bugreport.json");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		
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

		Iterator<Triple> ite = graph.iterate().iterator();
		
		
		RefineValidation validation = new RefineValidation(schema,graph);

		validation.validate(bug1, new Label(rdfFactory.createIRI("http://a.example/BugReport")));
		System.out.println();
		System.out.println("TYPING:");
		for (Pair<RDFTerm,Label> pair: validation.getTyping().getStatusMap().keySet())
			if (!pair.two.isGenerated())
				if (validation.getTyping().getStatus(pair.one, pair.two) == Status.NONCONFORMANT) {
					System.out.println(pair+" > "+validation.getTyping().getStatus(pair.one, pair.two));
				}
	}

}
