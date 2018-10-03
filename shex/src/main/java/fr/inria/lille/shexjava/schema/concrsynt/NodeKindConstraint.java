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
package fr.inria.lille.shexjava.schema.concrsynt;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * @author Jérémie Dusart
 */
public class NodeKindConstraint implements Constraint {
	
	public static final NodeKindConstraint BNodeKind = new NodeKindConstraint(){

		@Override
		public boolean contains(RDFTerm node) {
			return node instanceof BlankNode;
		}
		
		@Override
		public String toString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString(Map<String,String> prefixes) {
			return "BNODE";
		}
	};
	
	public static final NodeKindConstraint IRIKind = new NodeKindConstraint() {

		@Override
		public boolean contains(RDFTerm node) {
			return node instanceof IRI;
		}
		
		@Override
		public String toString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString(Map<String,String> prefixes) {
			return "IRI";
		}		
	};

	public static final NodeKindConstraint LiteralKind = new NodeKindConstraint() {
		
		@Override
		public boolean contains(RDFTerm node) {
			return node instanceof Literal;
		}
		
		@Override
		public String toString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString(Map<String,String> prefixes) {
			return "LITERAL";
		}
	};
	
	public static final NodeKindConstraint NonLiteralKind = new NodeKindConstraint() {
		
		@Override
		public boolean contains(RDFTerm node) {
			return !(node instanceof Literal);
		}
		
		@Override
		public String toString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString() {
			return toPrettyString(Collections.emptyMap());
		}
		
		@Override
		public String toPrettyString(Map<String,String> prefixes) {
			return "NONLITERAL";
		}
	};

	
	
	@Override
	public boolean contains(RDFTerm node) {
		return false;
	}	
	
	@Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	@Override
	public String toPrettyString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		return null;
	}	
}
