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

import java.util.List;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TripleConstraint extends TripleExpr implements AnnotedObject {
	private TCProperty property;	
	private ShapeExpr shapeExpr;
	private List<Annotation> annotations;	
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr ) {
		this.property = property;
		this.shapeExpr = shapeExpr;
	}
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr, List<Annotation> annotations) {
		this.property = property;
		this.shapeExpr = shapeExpr;
		this.annotations = annotations;
	}

	public TCProperty getProperty(){
		return property;
	}
	
	public ShapeExpr getShapeExpr(){
		return shapeExpr;
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitTripleConstraint(this, arguments);
	}
	
	@Override
	public TripleConstraint clone() {
		return new TripleConstraint(this.property, this.shapeExpr);
	}
	

	@Override
	public String toString() {
		return String.format("%s::%s",
				property.toString(),
				shapeExpr.toString());
	}

}

