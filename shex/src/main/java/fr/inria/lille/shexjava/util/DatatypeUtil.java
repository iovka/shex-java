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
package fr.inria.lille.shexjava.util;

import java.math.BigDecimal;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

/** Utils for literal manipulation and test.
 * @author Jérémie Dusart
 *
 */
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
