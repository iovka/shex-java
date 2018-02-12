package sandbox;

import static org.junit.Assert.assertTrue;

import java.io.StringBufferInputStream;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Test;

public class TestJenaRelated {

	@Test
	public void testParseBlankInModel() {
		
		String turtle = "_:abcd  <http://example.org/prop> <http://example.org/object> ." ;
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringBufferInputStream(turtle), null, "TURTLE");
		StmtIterator it = model.listStatements();
		Statement stmt = it.next();
		assertTrue(stmt.getSubject().isAnon());		
	}
	
	@Test
	public void testBlankInResourceFactory() {
		
		String turtle = "_:abcd  <http://example.org/prop> <http://example.org/object> ." ;
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringBufferInputStream(turtle), null, "TURTLE");
		StmtIterator it = model.listStatements();
		Statement stmt = it.next();
		assertTrue(stmt.getSubject().isAnon());		
	}

	@Test
	public void testParseDatatype () {
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
