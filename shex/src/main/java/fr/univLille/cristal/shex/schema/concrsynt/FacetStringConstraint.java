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

import fr.univLille.cristal.shex.util.XPath;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
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
	public boolean contains(Value node) {
		String lex = null;
		if (node instanceof Literal)
			lex = ((Literal)node).stringValue();
		else if (node instanceof IRI)
			lex = ((IRI)node).stringValue();
		else if (node instanceof BNode)
			lex = ((BNode)node).getID();
//		SimpleBNode bnode = (SimpleBNode) node;
//		System.err.println(bnode);
//		System.err.println(lex);
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacetStringConstraint other = (FacetStringConstraint) obj;
		if (! other.getPatternString().equals(getPatternString()))
			return false;
		if (! other.getFlags().equals(getFlags()))
			return false;
		if (other.getLength()!=getLength())
			return false;
		if (other.getMaxlength()!=getMaxlength())
			return false;
		if (other.getMinlength()!=getMinlength())
			return false;		
		
		return true;
	}
}
