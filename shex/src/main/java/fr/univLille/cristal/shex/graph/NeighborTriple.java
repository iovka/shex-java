package fr.univLille.cristal.shex.graph;

import org.eclipse.rdf4j.model.Value;

/** A triple with orientation that can be forward or backward.
 * Used to represent the neighborhood of a node.
 * 
 * A forward triple is a usual RDF triple.
 * A backward triple is an RDF triple considered in backwards direction. 
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class NeighborTriple {

	private final Value focus;
	private final Value opposite;
	private final TCProperty prop;

	public NeighborTriple(Value focus, TCProperty prop, Value opposite) {
		this.focus = focus;
		this.opposite = opposite;
		this.prop = prop;
	}

	
	/** The focus node is the subject of a forward triple, or the object of a backward triple.
	 * 
	 * @return
	 */
	public final Value getFocus() {
		return focus;
	}
	
	/** The extremity of a triple that is opposite to the focus node.
	 * This is the subject of a forward triple, and is the object of a backward triple.
	 * 
	 * @return
	 */
	public final Value getOpposite() {
		return opposite;
	}
	
	/** The predicate of a neighbor triple.
	 * 
	 * @return
	 */
	public final TCProperty getPredicate() {
		return this.prop;
	}
	
	@Override
	public String toString() {
		return "NeighbourTriple:(" +
				getFocus() + " " +
				getPredicate() + " " +
				getOpposite() + ")";
	
	}
	
}
