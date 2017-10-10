/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.schema.abstrsynt;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.univLille.cristal.shex.schema.ShapeLabel;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestShapeLabel {
	
	@Test
	public void testEquals() {
		ShapeLabel lab1 = new ShapeLabel("SL1");
		ShapeLabel lab2 = new ShapeLabel(" SL1 ".substring(1, 4));
		assertEquals(lab1, lab2);
		assertFalse(lab1 == lab2);
		
		assertEquals(lab1.hashCode(), lab2.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		ShapeLabel lab1 = new ShapeLabel("SL1");
		ShapeLabel lab2 = new ShapeLabel(" SL1 ".substring(1, 3));
		assertNotEquals(lab1, lab2);
	}

}
