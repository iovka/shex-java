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

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.inria.lille.shexjava.schema.IRILabel;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.junit.Test;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

public class TestMemorizationAlgorithm {
	private final static RDF4J rdfFactory = new RDF4J();

	@Test
	public void test0Cardinality1() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","MemValidation1.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		//create an user
		BlankNodeOrIRI n1 =  rdfFactory.createIRI("http://a.example/n1");
		BlankNodeOrIRI n2 =  rdfFactory.createIRI("http://a.example/n2");
		graph.add(n1,rdfFactory.createIRI("http://a.example/a"),n2);
		graph.add(n2,rdfFactory.createIRI("http://a.example/b"),n1);		
		graph.add(n1,rdfFactory.createIRI("http://a.example/c"),rdfFactory.createLiteral("test"));
		
		
		ValidationAlgorithmAbstract validation = new RecursiveValidationWithMemorization(schema,graph);
		validation.validate(n1, new IRILabel(rdfFactory.createIRI("http://a.example/S")));

		if (validation.getTyping().getStatus(n1, new IRILabel(rdfFactory.createIRI("http://a.example/S"))) == Status.CONFORMANT)
			fail();
	}
	
	@Test
	public void test0Cardinality2() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","MemValidation1.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);


		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		//create an user
		BlankNodeOrIRI n1 =  rdfFactory.createIRI("http://a.example/n1");
		BlankNodeOrIRI n2 =  rdfFactory.createIRI("http://a.example/n2");
		graph.add(n1,rdfFactory.createIRI("http://a.example/a"),n2);
		graph.add(n2,rdfFactory.createIRI("http://a.example/b"),n1);		
		graph.add(n1,rdfFactory.createIRI("http://a.example/c"),rdfFactory.createIRI("http://a.example/cv"));
		
		
		ValidationAlgorithmAbstract validation = new RecursiveValidationWithMemorization(schema,graph);
		validation.validate(n1, new IRILabel(rdfFactory.createIRI("http://a.example/S")));
 
		//for (Pair<RDFTerm, Label> key:validation.getTyping().getAllStatus().keySet())
		//	System.out.println(key+":"+validation.getTyping().getStatus(key.one, key.two));
		
		if (validation.getTyping().getStatus(n1, new IRILabel(rdfFactory.createIRI("http://a.example/S"))) == Status.NONCONFORMANT)
			fail();
	}
	
}
