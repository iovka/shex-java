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
package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class NodeKindConstraint implements Constraint {
	
	public static final NodeKindConstraint Blank = new NodeKindConstraint(){

		@Override
		public boolean contains(Value node) {
			return node instanceof BNode;
		}
		
		@Override
		public String toString() {
			return "BLANK";
		}		
	};
	
	public static final NodeKindConstraint AllIRI = new NodeKindConstraint() {

		@Override
		public boolean contains(Value node) {
			return node instanceof IRI;
		}
		
		@Override
		public String toString() {
			return "ALL_IRI";
		};
				
	};

	public static final NodeKindConstraint AllLiteral = new NodeKindConstraint() {
		
		@Override
		public boolean contains(Value node) {
			return node instanceof Literal;
		}
		
		public String toString() {
			return "ALL_LITERALS";
		}
	};
	
	public static final NodeKindConstraint AllNonLiteral = new NodeKindConstraint() {
		
		@Override
		public boolean contains(Value node) {
			return !(node instanceof Literal);
		}
		
		public String toString() {
			return "ALL_NON_LITERALS";
		}
	};

	@Override
	public boolean contains(Value node) {
		return false;
	}	
	
}
