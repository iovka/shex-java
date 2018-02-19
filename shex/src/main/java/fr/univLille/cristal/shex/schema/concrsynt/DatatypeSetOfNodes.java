package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class DatatypeSetOfNodes implements SetOfNodes {
	
	private IRI datatypeIri;
	
	public DatatypeSetOfNodes(IRI datatypeIri) {
		this.datatypeIri = datatypeIri;
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal)) return false;
		Literal lnode = (Literal) node;
		if (!(datatypeIri.equals(lnode.getDatatype()))) return false;
		if ((XMLDatatypeUtil.isBuiltInDatatype(lnode.getDatatype()))) {
			return XMLDatatypeUtil.isValidValue(lnode.stringValue(), lnode.getDatatype());
		}

		return true;
	}
	
	@Override
	public String toString() {
		return datatypeIri.toString();
	}

}
