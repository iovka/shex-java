/*******************************************************************************
 * Copyright (C) 2019 Université de Lille - Inria
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

package fr.inria.lille.shexjava.sandbox;

import java.io.ByteArrayInputStream;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.RecursiveValidationWithMemorization;
import fr.inria.lille.shexjava.validation.RefineValidation;
import fr.inria.lille.shexjava.validation.ValidationAlgorithm;

/**
 * @author Iovka Boneva
 *
 */
public class TestMatchingCollector {

	public static void main (String[] args) throws Exception {
		// TODO: dans cet exemple il manque l'information d'où vient le besoin de tester ce matching, qui a échoué
		// Introduire une notification de type "matching attempt"
		String url = "file:///tmp/shexjavatest/";
		String prefix = "<" + url + ">"; 
		String schemaText = "PREFIX : " + prefix +"\n"+
							":s {:a @:t ; :b .}" + "\n" +
							":t {:c .}";
		String graphText = "PREFIX : " + prefix + "\n" +
							":n :a :m." + "\n" +
							":n :b 1." + "\n" +
							":m :c 3." + "\n" +
							":m :c 4." + "\n";
		
		ShexSchema schema = GenParser.parseSchema(new ByteArrayInputStream(schemaText.getBytes()), ".shex");
		Model model = Rio.parse(new ByteArrayInputStream(graphText.getBytes()), 
				prefix, RDFFormat.TURTLE);
		RDF4J rdf4j = new RDF4J();
		Graph graph = rdf4j.asGraph(model);
		
		ValidationAlgorithm algorithm;

		// Ne notifie pas toujours quand on essaye de matcher noeud avec label et que ça ne réussit pas
		//algorithm = new RecursiveValidationWithMemorization(schema, graph);
		
		// TODO: pourquoi on essaie de matcher des entiers avec des shapes ?! 
		// Est-ce uniquement parce qu'on teste tout par rapport à tout ?
		// En tout cas il faudrait l'enlever 
		//algorithm = new RefineValidation(schema, graph);
		
		// Ici j'ai bien la trace de tous les matchings qui ont été testés, mais ce serait bien d'avoir
		// une structure arborescente: pour chaque matching testé je veux avoir son père qui l'a commandité
		algorithm = new RecursiveValidation(schema, graph);
		
		MyMatchingCollector collector = new MyMatchingCollector();
		algorithm.addMatchingObserver(collector);
		
		RDFTerm node = GlobalFactory.RDFFactory.createIRI(url+"n");
		Label label = new Label(GlobalFactory.RDFFactory.createIRI(url+"s")); 
		algorithm.validate(node, label);
		
		System.out.println("--Matchings--------------");
		collector.getMatchingMap().forEach( (k,v)-> System.out.println(k + "\n   " + v));
		System.out.println("--Unsuccessful matchings--------------");
		collector.getUnsuccessfulMatchings().forEach(x -> System.out.println(x));
	}
}
