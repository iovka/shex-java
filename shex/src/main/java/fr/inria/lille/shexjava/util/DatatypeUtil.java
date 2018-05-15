package fr.inria.lille.shexjava.util;

import java.math.BigDecimal;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;


public class DatatypeUtil {
	private static SimpleValueFactory rdfFact = SimpleValueFactory.getInstance();

	public static boolean isValidValue(Literal lnode) {
		return XMLDatatypeUtil.isValidValue(lnode.getLexicalForm(), rdfFact.createIRI(lnode.getDatatype().getIRIString()));
	}
	
	public static String normalize(Literal lnode) {
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return XMLDatatypeUtil.normalize(value, rdfFact.createIRI(datatype.getIRIString()));	
	}
	
	public static boolean isValidDouble(Literal lnode) {
		return XMLDatatypeUtil.isValidDouble(lnode.getLexicalForm());
	}
	
	public static boolean isDecimalDatatype(Literal lnode) {
		return XMLDatatypeUtil.isDecimalDatatype(rdfFact.createIRI(lnode.getDatatype().getIRIString()));
	}
	
	public static BigDecimal getDecimalValue(Literal lnode) {
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return rdfFact.createLiteral(value, rdfFact.createIRI(datatype.getIRIString())).decimalValue();
	}

	public static boolean getBooleanValue(Literal lnode) {
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return rdfFact.createLiteral(value, rdfFact.createIRI(datatype.getIRIString())).booleanValue();
	}
	
	public static int getIntegerValue(Literal lnode) {
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return rdfFact.createLiteral(value, rdfFact.createIRI(datatype.getIRIString())).integerValue().intValue();
	}
	
	public static String getStringValue(Literal lnode) {
		String value = lnode.getLexicalForm();
		IRI datatype = lnode.getDatatype();
		return rdfFact.createLiteral(value, rdfFact.createIRI(datatype.getIRIString())).stringValue();
	}
	
}
