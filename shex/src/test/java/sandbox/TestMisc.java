package sandbox;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Test;

public class TestMisc {

	@Test
	public void testIRIEquivalence() {
		Resource r1 = ResourceFactory.createResource("http://example.com/abc");
		Resource r2 = ResourceFactory.createResource("http://EXAMPLE.COM/abc");
		Resource r3 = ResourceFactory.createResource("http://example.com/ABC");
		Resource r4 = ResourceFactory.createResource("http://example.com/abc");
		
		System.out.println(r1.equals(r2));
		System.out.println(r1.equals(r3));
		System.out.println(r1.equals(r4));
	}
	
	@Test
	public void testCreateTypedLiteral() {
		Literal l = ResourceFactory.createTypedLiteral("true", new XSDDatatype("boolean"));
		System.out.println(l);
	}
	
	
	@Test
	public void testLiteralsResourcesEquals() {
		Literal l1 = ResourceFactory.createTypedLiteral("true", new XSDDatatype("boolean"));
		Literal l2 = ResourceFactory.createTypedLiteral("true", new XSDDatatype("boolean"));
		Literal l3 = ResourceFactory.createTypedLiteral("true", new XSDDatatype("float"));
		assertFalse(l1.equals(l2));
		assertTrue(l1.sameValueAs(l2));
		assertFalse(l1.sameValueAs(l3));
		
		Resource r1 = ResourceFactory.createResource("http://example.com/abc");
		Resource r2 = ResourceFactory.createResource("http://example.com/abc");
		assertTrue(r1.equals(r2));
	}
	
	
	@Test
	public void testCastOnNull() {
		String s = (String) null;
		
		Map map = new HashMap();
		s = (String) (map.get("toto"));
		
	}
}
