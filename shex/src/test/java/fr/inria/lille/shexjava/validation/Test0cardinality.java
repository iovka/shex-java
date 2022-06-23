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

import static org.junit.Assert.assertNotEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.inria.lille.shexjava.schema.LabelUserDefined;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.junit.Test;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

public class Test0cardinality {
	private final static RDF4J rdfFactory = new RDF4J();

	@Test
	public void test0Cardinality1() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","0cardinality.shex");
		ShexSchema schema = GenParser.parseSchema(schema_file, null, rdfFactory);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		BlankNodeOrIRI s1 =  rdfFactory.createIRI("http://a.example/s1");
		graph.add(s1,rdfFactory.createIRI("http://a.example/a"),rdfFactory.createLiteral("test"));
				
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(s1, new LabelUserDefined(rdfFactory.createIRI("http://a.example/S")));

		assertNotEquals(Status.CONFORMANT, validation.getTyping().getStatus(s1, new LabelUserDefined(rdfFactory.createIRI("http://a.example/S"))));
	}
	
	@Test
	public void test0Cardinality2() throws Exception {
		Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"other","0cardinality2.shex");
		ShexSchema schema = GenParser.parseSchema(schema_file, null, rdfFactory);
		
		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		//create an user
		BlankNodeOrIRI s1 =  rdfFactory.createIRI("http://a.example/s1");
		graph.add(s1,rdfFactory.createIRI("http://a.example/c"),rdfFactory.createLiteral("test"));
		
		RefineValidation validation = new RefineValidation(schema,graph);
		validation.validate(s1, new LabelUserDefined(rdfFactory.createIRI("http://a.example/S")));
		
		assertNotEquals(Status.CONFORMANT, validation.getTyping().getStatus(s1, new LabelUserDefined(rdfFactory.createIRI("http://a.example/S"))));
	}

}
