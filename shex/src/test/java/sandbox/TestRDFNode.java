package sandbox;

import static org.junit.Assert.*;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;

public class TestRDFNode {
	
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
		
		Literal l1 = model.createTypedLiteral(5);
		Literal l2 = model.createTypedLiteral(5);
		
		System.out.println(l1.equals(l2));
	}
	
	@Test
	public void testGraphWithoutTriples() {
		Model model = ModelFactory.createDefaultModel();
		model.createResource();
		System.out.println(model);
	}

}
