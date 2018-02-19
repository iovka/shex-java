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
