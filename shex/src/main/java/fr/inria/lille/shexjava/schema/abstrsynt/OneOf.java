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

import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class OneOf extends AbstractNaryTripleExpr implements AnnotedObject {
	private List<Annotation> annotations;

	public OneOf(List<TripleExpr> subExpressions) {
		super(subExpressions);
		this.annotations = null;
	}
	
	public OneOf(List<TripleExpr> subExpressions, List<Annotation> annotations) {
		super(subExpressions);
		this.annotations = annotations;
	}
	
	public void setAnnotations (List<Annotation> annotations) {
		if (this.annotations == null)
			this.annotations = annotations;
		else throw new IllegalStateException("Annotations already set");
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	
	@Override
	public String toString() {
		String result ="";
		if (id!=null)
			result += getId().toString()+"=";
		result += CollectionToString.collectionToString(getSubExpressions(), " || ", "OneOf(", ")");;
		if (this.annotations!=null && this.annotations.size()>0)
			result +=CollectionToString.collectionToString(annotations," ; ","// [", "]");
		return result;
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitOneOf(this, arguments);
	}
	
	@Override
	public String toPrettyString() {
		String result ="";
		if (id!=null && !id.isGenerated())
			result += getId().toString()+"=";
		result += CollectionToString.collectionToString(getSubExpressions(), " || ", "OneOf(", ")");;
		if (this.annotations!=null && this.annotations.size()>0)
			result +=CollectionToString.collectionToString(annotations," ; ","// [", "]");
		return result;
	}
	
}
