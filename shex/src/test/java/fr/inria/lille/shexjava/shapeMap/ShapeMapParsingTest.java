package fr.inria.lille.shexjava.shapeMap;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParsing;

public class ShapeMapParsingTest {
	protected ShapeMapParsing parser;
	
	public ShapeMapParsingTest() {
		parser = new ShapeMapParsing();
	}
	
	@Test
	public void test() {
		String shMap = "{ FOCUS <http://inria.fr/node1> _ } @START";
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(result.getAssociations().size(), 1);
			assertEquals(result.getAssociations().iterator().next().toString(), "{ FOCUS <http://inria.fr/node1> _ } @ START");
		} catch (IOException e) {
			fail("Exception during the parsing");
		}
	}
	
	
	@Test
	public void test1() {
		String shMap = "a @<http://inria.fr/Shape>";
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(result.getAssociations().size(), 1);
			assertEquals(result.getAssociations().iterator().next().toString(), "a @<http://inria.fr/Shape>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void test2() {
		String shMap = "{ FOCUS <http://inria.fr/node1> _ } @START, { _ <http://inria.fr/node1> FOCUS } @START";
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(result.getAssociations().size(), 2);
			assertEquals(result.getAssociations().iterator().next().toString(), "{ FOCUS <http://inria.fr/node1> _ } @ START");
		} catch (IOException e) {
			fail("Exception during the parsing");
		}
	}

	
	@Test
	public void test3() {
		String shMap = "{ FOCUS <http://inria.fr/node1> true } @START";
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			assertEquals(result.getAssociations().size(), 1);
			assertEquals(result.getAssociations().iterator().next().toString(), "{ FOCUS <http://inria.fr/node1> \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean> } @ START");
		} catch (IOException e) {
			fail("Exception during the parsing");
		}
	}
	
}
