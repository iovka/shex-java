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
package fr.univLille.cristal.shex.validation;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.eachof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.util.RDFFactory;

class TestGenerateSORBE {
	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();

	@Test
	void testTripleConstraint() {
		System.out.println("Test copy TC:");
		SORBEGenerator generator = new SORBEGenerator();
		
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		
		ShapeExprLabel label = new ShapeExprLabel(RDF_FACTORY.createIRI("_:test"));
		
		Shape test = new Shape(tca,Collections.EMPTY_SET,false);
		test.setId(label);
		
		System.out.println(test);
		
		System.out.println("Generate version: "+generator.getSORBETripleExpr(test));
	}
	
	@Test
	void testEachOf() {
		System.out.println("Test copy eachof:");
		SORBEGenerator generator = new SORBEGenerator();
		
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		
		EachOf each = eachof(tca,tcb);
		
		ShapeExprLabel label = new ShapeExprLabel(RDF_FACTORY.createIRI("_:test"));
		
		Shape test = new Shape(each,Collections.EMPTY_SET,false);
		test.setId(label);
		
		System.out.println(test);
		
		System.out.println("Generate version: "+generator.getSORBETripleExpr(test));
	}

	@Test
	void testRepeated1() {
		System.out.println("Test repeated");
		SORBEGenerator generator = new SORBEGenerator();
		
		TripleConstraint tca = (TripleConstraint) tc("a :: .");
		TripleConstraint tcb = (TripleConstraint) tc("b :: .");
		EachOf each = eachof(tca,tcb);
		RepeatedTripleExpression rte = new RepeatedTripleExpression(each, new Interval(2,5));
		
		ShapeExprLabel label = new ShapeExprLabel(RDF_FACTORY.createIRI("_:test"));
		
		Shape test = new Shape(rte,Collections.EMPTY_SET,false);
		test.setId(label);
		
		System.out.println(test);
		TripleExpr res = generator.getSORBETripleExpr(test);
		
		System.out.println("Generate version: "+res);
		System.out.println(((EachOf) res).getSubExpressions().size());
	}
	
}
