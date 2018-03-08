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
package fr.univLille.cristal.shex.validation;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

/** Match the predicate and the value. The typing providing in the constructor is used to check the value.
 * 
 * @author Jérémie Dusart
 */
public class MatcherPredicateAndValue implements Matcher {
	private Typing typing;
	
	public MatcherPredicateAndValue(Typing typing) {
		this.typing = typing;
	}
	
	@Override
	public Boolean apply(NeighborTriple triple, TripleConstraint tc) {
		if (tc.getProperty().equals(triple.getPredicate())) 
			return typing.contains(triple.getOpposite(), tc.getShapeExpr().getId());
		return false;
	}


}
