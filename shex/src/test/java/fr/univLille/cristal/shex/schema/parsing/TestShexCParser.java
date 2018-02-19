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
package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;

class TestShexCParser {
	protected static final String TEST_DIR = "/home/jdusart/Documents/Shex/workspace/shexTest/";
	private static final String SCHEMAS_DIR = TEST_DIR + "schemas/";

	@Test
	void importTest() { 
		Path schema = Paths.get(SCHEMAS_DIR,"2EachInclude1-IS2.shex");
		System.err.println("Parsing schema: "+schema);
		try {
			GenParser.parseSchema(schema);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CyclicReferencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotStratifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	
	@Test
	void focusPatternTest() { 
		Path schema = Paths.get(SCHEMAS_DIR,"1focusPatternB-dot.shex");
		System.err.println("Parsing schema: "+schema);
		try {
			GenParser.parseSchema(schema);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CyclicReferencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotStratifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@Test
	void focusPatternTest2() { 
		Path schema = Paths.get(SCHEMAS_DIR,"1focusPattern-dot.shex");
		System.err.println("Parsing schema: "+schema);
		try {
			GenParser.parseSchema(schema);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CyclicReferencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotStratifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	
	@Test
	void patternTest() { 
		Path schema = Paths.get(SCHEMAS_DIR,"1val1vExprRefOR3.shex");
		System.err.println("Parsing schema: "+schema);
		try {
			GenParser.parseSchema(schema);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CyclicReferencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotStratifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
}
