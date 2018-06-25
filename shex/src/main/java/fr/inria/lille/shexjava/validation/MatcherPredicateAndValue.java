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
package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;

/** Match the predicate and the value. The typing providing in the constructor is used to check the value.
 * 
 * @author Jérémie Dusart
 */
public class MatcherPredicateAndValue extends Matcher {
	private Typing shapeMap;
	
	public MatcherPredicateAndValue(Typing shapeMap) {
		this.shapeMap = shapeMap;
	}
	
	@Override
	public boolean apply(RDFTerm focusNode, Triple triple, TripleConstraint tc) {
		if (tc.getProperty().isForward() && triple.getSubject().ntriplesString().equals(focusNode.ntriplesString()))
			if (tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString())) 
				return shapeMap.isConformant(triple.getObject(), tc.getShapeExpr().getId());
		if (!tc.getProperty().isForward() && triple.getObject().ntriplesString().equals(focusNode.ntriplesString()))
			if (tc.getProperty().getIri().ntriplesString().equals(triple.getPredicate().ntriplesString())) 
				return shapeMap.isConformant(triple.getSubject(), tc.getShapeExpr().getId());
		return false;
	}


}
