package fr.univLille.cristal.shex.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univLille.cristal.shex.graph.TCProperty;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

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
