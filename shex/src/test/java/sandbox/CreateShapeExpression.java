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
package sandbox;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.util.Interval;

public class CreateShapeExpression {
	
	public static void main(String[] args) {
		ValueFactory RDF_FACTORY = SimpleValueFactory.getInstance();
		
		ShapeExprRef ref = new ShapeExprRef(new Label(RDF_FACTORY.createIRI("http://example.org/S")));
		TCProperty prop = TCProperty.createFwProperty(RDF_FACTORY.createIRI("http://example.org/p"));
		TripleConstraint tc =  new TripleConstraint(prop, ref);
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tc, Interval.PLUS);
		
		System.out.println(expr);
		
	}

}
