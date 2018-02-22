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
package sandbox;

import static org.junit.Assert.assertTrue;

import java.io.StringBufferInputStream;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;
import org.junit.Test;

public class TestJenaRelated {

	@Test
	public void testParseBlankInModel() {
		String turtle = "_:abcd  <http://example.org/prop> <http://example.org/object> ." ;
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringBufferInputStream(turtle), null, "TURTLE");
		StmtIterator it = model.listStatements();
		Statement stmt = it.next();
		Resource res = stmt.getSubject().asResource();
		assertTrue(stmt.getSubject().isAnon());		
	}
	
	@Test
	public void testParseDatatype () {
		String lex = "_:abcd";
		TypeMapper tm = TypeMapper.getInstance();
		XSDDatatype.loadXSDSimpleTypes(tm);
		Resource res = ResourceFactory.createResource(lex);
		System.err.println(res.getURI());
		System.err.println(res.isAnon());
		System.err.println(res.getNameSpace());
		System.err.println(res.getLocalName());
	}
	
	
	@Test
	public void testBNodeFactory() {
		String lex = "\"0e0\"^^http://www.w3.org/2001/XMLSchema#double";
		TypeMapper tm = TypeMapper.getInstance();
		XSDDatatype.loadXSDSimpleTypes(tm);
		RDFDatatype type = tm.getTypeByName("http://www.w3.org/2001/XMLSchema#double");
		Literal lit = ResourceFactory.createTypedLiteral("0e0", type);
		System.out.println(lit + " " + lit.getClass());
		
		Object value = type.parse("0e0");
		System.out.println(value + " " + value.getClass());
		
	}
	
	@Test
	public void testIncorrectLexicalForm() {
		
		TypeMapper tm = TypeMapper.getInstance();
		XSDDatatype.loadXSDSimpleTypes(tm);
		RDFDatatype type = tm.getTypeByName("http://www.w3.org/2001/XMLSchema#decimal");
		
		Literal lit = ResourceFactory.createTypedLiteral("1.23ab", type);
		System.out.println(lit);
	}
		
}
