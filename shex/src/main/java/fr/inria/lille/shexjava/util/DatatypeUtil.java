package fr.inria.lille.shexjava.util;

import java.math.BigDecimal;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.simple.Types;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;

public class DatatypeUtil {
	private static RDFFactory rdfFact = RDFFactory.getInstance();

	public static boolean isValidValue(Literal lnode) {
		System.out.println("Testing isValidValue: "+lnode);
		return XMLDatatypeUtil.isValidValue(lnode.getLexicalForm(), rdfFact.createIRI(lnode.getDatatype().getIRIString()));
	}
	
	public static boolean isValidDouble(Literal lnode) {
		System.out.println("Testing isValidDouble: "+lnode);
		return XMLDatatypeUtil.isValidDouble(lnode.getLexicalForm());
	}
	
	public static boolean isDecimalDatatype(Literal lnode) {
		System.out.println("Testing isDecimalDatatype: "+lnode);
		System.out.println(lnode.getDatatype().getIRIString().equals(Types.XSD_DECIMAL.getIRIString()));
		return lnode.getDatatype().getIRIString().equals(Types.XSD_DECIMAL.getIRIString());
	}
	
	public static BigDecimal getDecimalValue(Literal lnode) {
		System.out.println("Testing getDecimalValue: "+lnode);
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return rdfFact.createLiteral(value, rdfFact.createIRI(datatype.getIRIString())).decimalValue();
	}
	
	public static String normalize(Literal lnode) {
		System.out.println("Testing normalize: "+lnode);
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return XMLDatatypeUtil.normalize(value, rdfFact.createIRI(datatype.getIRIString()));	
	}

}
