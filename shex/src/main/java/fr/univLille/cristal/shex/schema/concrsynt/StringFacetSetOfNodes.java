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
public class StringFacetSetOfNodes implements SetOfNodes {
	
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
}
