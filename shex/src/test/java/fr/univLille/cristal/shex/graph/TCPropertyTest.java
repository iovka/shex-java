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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TCPropertyTest {
	private ValueFactory vf;
	private TCProperty tcnull1;
	private TCProperty tcnull2;
	private TCProperty tcnullbw;
	private TCProperty forwd;
	private TCProperty backwd;
	
	
	@BeforeEach
	void setUp() throws Exception {
		this.vf = SimpleValueFactory.getInstance();
		this.tcnull1 = TCProperty.createFwProperty(null);
		this.tcnull2 = TCProperty.createFwProperty(null);
		this.tcnullbw = TCProperty.createInvProperty(null);
		this.forwd = TCProperty.createFwProperty(this.vf.createIRI("http://a.example/forward"));
		this.backwd = TCProperty.createInvProperty(this.vf.createIRI("http://a.example/backward"));
	}

	@Test
	void testOrientation() {
		if (! this.forwd.isForward()) {
			fail("Error of orientation");
		}
		if (this.backwd.isForward()) {
			fail("Error of orientation");
		}
	}
	
	@Test
	void testToString() {
		if (! (tcnull1.toString().equals("null")))
			fail("Error with iri:"+tcnull1.getIri());
		if (! (tcnullbw.toString().equals("^null")))
			fail("Error with iri:"+tcnullbw.getIri());
		if (! (forwd.toString().equals("http://a.example/forward")))
			fail("Error with iri:"+forwd.getIri());
		if (! (backwd.toString().equals("^http://a.example/backward")))
			fail("Error with iri:"+backwd.getIri());	
	}
	
	@Test
	void testHashCode() {
		if (tcnull1.hashCode() != 39122)
			fail("Error with hashcode value.");
		if (tcnullbw.hashCode() != 39308)
			fail("Error with hashcode value.");
		if (forwd.hashCode() != 2026039443)
			fail("Error with hashcode value.");
		if (backwd.hashCode() != 20203507)
			fail("Error with hashcode value.");
	}
	
	@Test
	public void testSameHashCode() {
		Set<TCProperty> set= new HashSet<TCProperty>();
		TCProperty p1 =TCProperty.createFwProperty(SimpleValueFactory.getInstance().createIRI("http://a.example/p1"));
		TCProperty p2 =TCProperty.createFwProperty(SimpleValueFactory.getInstance().createIRI("http://a.example/p1"));
		set.add(p1);
		System.out.println("Sep contains p2:"+set.contains(p1));
	}
	
	@Test
	void testEquals() {
		if (! this.forwd.equals(this.forwd))
			fail("Error: object not equal to itself");
		if (this.forwd.equals(null))
			fail("Error: object equal null");
		if (this.forwd.equals("test"))
			fail("TCProperty equal string object");
		if (this.forwd.equals(this.backwd))
			fail("Error: object equals despite differents orientations");
		if (this.tcnull1.equals(this.backwd))
			fail("Error: null iri equals non null iri");
		if (this.tcnull1.equals(TCProperty.createFwProperty(this.vf.createIRI("http://a.example/forwardzer"))))
			fail("Error: different iri but equals...");
		if (! this.tcnull1.equals(tcnull2))
			fail("Error: Both null but not equals...");
		if (this.forwd.equals(TCProperty.createFwProperty(this.vf.createIRI("http://a.example/forwardzer"))))
			fail("Error: different iri but equals...");	
		if (!this.forwd.equals(TCProperty.createFwProperty(this.forwd.getIri())))
			fail("Error: Same iri but not equals...");
		
	}

}
