package fr.univLille.cristal.shex.validation;

import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.util.Pair;

/** A set of associations (resource, shape labels).
 * Is produced as result of a validation, see {@link ValidationAlgorithm}
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface Typing {

	/** Checks whether a node-label association belongs to the typing. 
	 * 
	 * @param node
	 * @param label
	 * @return
	 */
	public boolean contains(Value node, ShapeExprLabel label);
	
	/** Returns the typing as a set of pairs (node, label).
	 * 
	 * @return
	 */
	public Set<Pair<Value, ShapeExprLabel>> asSet(); // For testing purposes

}
