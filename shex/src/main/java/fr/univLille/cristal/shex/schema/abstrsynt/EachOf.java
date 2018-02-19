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

import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.CollectionToString;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class EachOf extends AbstractNaryTripleExpr implements AnnotedObject{
	private List<Annotation> annotations;

	public EachOf(List<TripleExpr> subExpressions) {
		super(subExpressions);
	}
	
	public EachOf(List<TripleExpr> subExpressions, List<Annotation> annotations) {
		super(subExpressions);
		this.annotations = annotations;
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	
	@Override
	public String toString() {
		return CollectionToString.collectionToString(getSubExpressions(), " ; ", "EachOf(", ")");
	}

	@Override
	public <ResultType> void accept(TripleExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitEachOf(this, arguments);
	}

	
}
