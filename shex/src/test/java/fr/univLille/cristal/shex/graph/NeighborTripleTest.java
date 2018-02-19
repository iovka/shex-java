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
package fr.univLille.cristal.shex.graph;

import static org.junit.jupiter.api.Assertions.fail;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NeighborTripleTest {
	private ValueFactory vf;
	private Value focus;
	private Value opposite;
	private TCProperty forwd;
	private NeighborTriple nt1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.vf = SimpleValueFactory.getInstance();
		forwd = TCProperty.createFwProperty(this.vf.createIRI("http://a.example/child"));
		focus = this.vf.createIRI("http://a.example/Marie");
		opposite = this.vf.createIRI("http://a.example/Jesus");
		nt1 = new NeighborTriple(focus,forwd,opposite);
	}

	@Test
	void testGetFocus() {
		if (! nt1.getFocus().equals(this.focus))
			fail("Not the right focus");
	}

	@Test
	void testOpposite() {
		if (! nt1.getOpposite().equals(this.opposite))
			fail("Not the right focus");
	}
	
	@Test
	void testGetPredicate() {
		if (! nt1.getPredicate().equals(this.forwd))
			fail("Not the right focus");
	}

	@Test
	void testToString() {
		if (! nt1.toString().equals("NeighbourTriple:(http://a.example/Marie http://a.example/child http://a.example/Jesus)"))
			fail("Not the right focus");
	}
	
}
