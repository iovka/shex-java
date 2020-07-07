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
package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.List;
import java.util.Map;

import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class TripleConstraint extends TripleExpr implements AnnotedObject {
	private TCProperty property;	
	private ShapeExpr shapeExpr;
	private List<Annotation> annotations;	
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr ) {
		this.property = property;
		this.shapeExpr = shapeExpr;
		this.annotations = null;
	}
	
	public TripleConstraint (TCProperty property, ShapeExpr shapeExpr, List<Annotation> annotations) {
		this.property = property;
		this.shapeExpr = shapeExpr;
		this.annotations = annotations;
	}
	
	public void setAnnotations (List<Annotation> annotations) {
		if (this.annotations == null)
			this.annotations = annotations;
		else throw new IllegalStateException("Annotations already set");
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
	public String toPrettyString(Map<String,String> prefixes) {
		String result ="";
		if (id!=null && id.isUserDefined())
			result += getId().toString()+"=";
		result += String.format("%s %s",property.toPrettyString(prefixes),shapeExpr.toPrettyString(prefixes));
		if (this.annotations!=null && this.annotations.size()>0)
			result +=CollectionToString.collectionToString(annotations," ; ","// [", "]");
		return result;
	}
}

