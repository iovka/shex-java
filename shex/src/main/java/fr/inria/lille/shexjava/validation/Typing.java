package fr.inria.lille.shexjava.validation;

import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;

import com.moz.kiji.annotations.ApiStability.Stable;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

/** A simpler version of a shape map that associates a {@link Status} to pairs of a node and a label.
 * 
 * @author Iovka Boneva
 * 2 août 2018
 */
@Stable
public interface Typing {
	
	/** The status of the pair (node, label).
	 * 
	 * @param node
	 * @param label
	 * @return
	 */
	@Stable
	public Status getStatus (RDFTerm node, Label label);
	
	/** Returns all pairs in the typing with theeir status.
	 * 
	 * @return
	 */
	@Stable
	public Map<Pair<RDFTerm, Label>, Status> getStatusMap(); 
	
	public default boolean isConformant (RDFTerm node, Label label) {
		return getStatus(node, label) == Status.CONFORMANT;
	}

}
