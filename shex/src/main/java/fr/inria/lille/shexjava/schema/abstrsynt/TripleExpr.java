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

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public abstract class TripleExpr {
	protected Label id;
	
	public void setId(Label id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once");
		this.id = id;
	}
	
	public Label getId () {
		return this.id;
	}
	
	public abstract <ResultType> void accept (TripleExpressionVisitor<ResultType> visitor, Object... arguments);
	
	public abstract String toPrettyString();

}
