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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;



public class TestRegularExpressions {
	
	@Test
	public void testDollar() {
		
		assertTrue  ( "ab".matches("ab$")  );
		assertFalse ( "xab".matches("ab$") );
		
		Pattern pattern = Pattern.compile("ab$");
		assertTrue ( pattern.matcher("ab").find() );
		assertTrue ( pattern.matcher("xab").find());

	}

	@Test
	public void testDollarInString() {
		assertFalse  (  "ab$".matches("ab$")  );
		assertTrue   (  "ab$".matches("ab\\$"));
		
		assertFalse  (  "^ab".matches("^ab")  );
		assertTrue   (  "^ab".matches("\\^ab"));
	}
	
}
