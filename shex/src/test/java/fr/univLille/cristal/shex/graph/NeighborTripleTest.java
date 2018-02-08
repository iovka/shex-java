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
