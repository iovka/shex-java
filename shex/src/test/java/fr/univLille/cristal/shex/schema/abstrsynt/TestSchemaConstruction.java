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
package fr.univLille.cristal.shex.schema.abstrsynt;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.not;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.se;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.shapeAnd;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.someof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;

import org.junit.Test;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestSchemaConstruction {
	
	
	@Test(expected=UndefinedReferenceException.class)
	public void testMissingShapeDefinition() throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addRule("SL1", se(tc("p :: SL")));
		constr.getSchema();
	}
	
	@Test
	public void testTreeStructuredSchema10rules() throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		TripleExpr 
			te1 = tc("ex:a :: SL1"),
			te3 = tc("ex:a :: SL3"),
			te4 = someof("ex:a :: SL4 | ex:b :: SL4bis"),
			te6 = tc("ex:a :: SL6"),
			tempty = new EmptyTripleExpression();
		
		constr.addRule("SL1", shapeAnd(te1, not(te3), te4));
		constr.addRule("SL3", se(tempty));
		constr.addRule("SL4", se(te6));
		constr.addRule("SL4bis", se(not(tempty)));
		constr.addRule("SL6", shapeAnd(tempty, not(tempty), tempty));
		
		ShexSchema schema = constr.getSchema();
		System.out.println(schema);
	}
}
