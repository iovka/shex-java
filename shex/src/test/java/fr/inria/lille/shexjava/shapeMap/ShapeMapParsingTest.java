package fr.inria.lille.shexjava.shapeMap;


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
		System.out.println(shMap);
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test1() {
		String shMap = "a @<http://inria.fr/Shape>";
		System.out.println(shMap);
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void test2() {
		String shMap = "{ FOCUS <http://inria.fr/node1> _ } @START, { _ <http://inria.fr/node1> FOCUS } @START";
		System.out.println(shMap);
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void test3() {
		String shMap = "{ FOCUS <http://inria.fr/node1> true } @START";
		System.out.println(shMap);
		try {
			BaseShapeMap result = parser.parse(new ByteArrayInputStream(shMap.getBytes()));
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
