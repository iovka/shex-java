package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Optional;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ObjectLiteral implements SetOfNodes {

	private String value;
	private String language;
	private IRI type;
	
	public ObjectLiteral (String value, String language, IRI type) {
		this.value = value;
		this.language = language;
		this.type = type;
	}
		
	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal))
			return false;
		Literal l = (Literal) node;
		if (! l.stringValue().equals(value))
			return false;
		if (language != null) {
			Optional<String> lang = l.getLanguage();
			if (! lang.isPresent())
				return false;
			if (! lang.get().equals(language))
				return false;	
		}
		if (type != null && !l.getDatatype().equals(type))
			return false;
		
		return true;
	}
	
	public String toString(){
		return "Literal=("+value+","+language+","+type+")";
	}

}
