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

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.util.XPath;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class FacetStringConstraint implements Constraint {
	
	private Integer length, minlength, maxlength;
	private String patternString;
	private String flags;
	
	public void setFlags(String flags) {
		if (this.flags == null)
			this.flags = flags;
		else throw new IllegalStateException("flags already set");
	}
		
	public void setLength(Integer length) {
		if (this.length == null)
			this.length = length;
		else throw new IllegalStateException("length already set");
	}
	public void setMinLength(Integer minlength) {
		if (this.minlength == null)
			this.minlength = minlength;
		else throw new IllegalStateException("minlength already set");
	}
	public void setMaxLength(Integer maxlength) {
		if (this.maxlength == null)
			this.maxlength = maxlength;
		else throw new IllegalStateException("maxlength already set");
	}
	public void setPattern(String patternString) {
		if (patternString == null)
			return;
		if (this.patternString == null)
			this.patternString = patternString;
		else throw new IllegalStateException("pattern already set");
	}
	@Override
	public boolean contains(RDFTerm node) {
		String lex = null;
		if (node instanceof Literal)
			lex = ((Literal)node).getLexicalForm();
		else if (node instanceof IRI)
			lex = ((IRI)node).getIRIString();
		else if (node instanceof BlankNode)
			lex = ((BlankNode)node).ntriplesString().substring(2);
		if (patternString != null && ! XPath.matches(lex, patternString,flags))
			return false;
		if (length != null && lex.length() != length)
			return false;
		if (minlength != null && lex.length() < minlength)
			return false;
		if (maxlength != null && lex.length() > maxlength)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String len = length == null ? ""    :  " length: " + length.toString();
		String min = minlength == null ? "" :  " minlength: " + minlength.toString();
		String max = maxlength == null ? "" :  " maxlength: " + maxlength.toString();
		String pat = patternString == null ? ""   :  " pattern: " + patternString.toString();
		return len + min + max + pat;
	}
	
	@Override
	public String toPrettyString() {
		return this.toString();
	}

	/** Equals if obj has the same constraints.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacetStringConstraint other = (FacetStringConstraint) obj;

		if (getPatternString()!=null) {
			if (! getPatternString().equals(other.getPatternString()))
				return false;
		} else
			if (other.getPatternString()!=null)
				return false;
		
		if (getFlags()!=null) {
			if (! getFlags().equals(other.getFlags()))
				return false;
		} else
			if (other.getFlags()!=null)
				return false;
		
		if (getLength()!=null) {
			if (other.getLength()==null)
				return false;
			if (getLength().compareTo(other.getLength()) != 0)
				return false;
		} else
			if (other.getLength()!=null)
				return false;
		
		if (getMaxlength()!=null) {
			if (other.getMaxlength()==null)
				return false;
			if (getMaxlength().compareTo(other.getMaxlength()) != 0)
				return false;
		} else
			if (other.getMaxlength()!=null)
				return false;
	
		if (getMinlength()!=null) {
			if (other.getMinlength()==null)
				return false;
			if (getMinlength().compareTo(other.getMinlength()) != 0)
				return false;
		} else
			if (other.getMinlength()!=null)
				return false;
		
		return true;
	}
	
	public Integer getMinlength() {
		return minlength;
	}

	public void setMinlength(Integer minlength) {
		this.minlength = minlength;
	}

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	public String getPatternString() {
		return patternString;
	}

	public void setPatternString(String patternString) {
		this.patternString = patternString;
	}

	public Integer getLength() {
		return length;
	}

	public String getFlags() {
		return flags;
	}
}
