package fr.univLille.cristal.shex.schema.abstrsynt;
/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.*;

import org.junit.Test;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;

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
