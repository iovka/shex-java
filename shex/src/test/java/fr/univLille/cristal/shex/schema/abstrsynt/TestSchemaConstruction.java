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
