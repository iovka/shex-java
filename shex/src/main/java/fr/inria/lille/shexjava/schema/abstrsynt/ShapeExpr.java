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

import java.util.Collections;
import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public abstract class ShapeExpr{
	
	protected Label id = null;
	
	public void setId(Label id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once ( is:"+this.id+", to:"+id+").");
		this.id = id;
	}
	
	public Label getId () {
		return this.id;
	}
	
	public void resolveReferences (Map<Label,ShapeExpr> shexprsMap) throws UndefinedReferenceException {
	}

	public abstract <ResultType> void accept (ShapeExpressionVisitor<ResultType> visitor, Object ... arguments);
	
	@Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public String toPrettyString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public abstract String toPrettyString(Map<String,String> prefixes);
}
