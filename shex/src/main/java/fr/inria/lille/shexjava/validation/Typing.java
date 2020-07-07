package fr.inria.lille.shexjava.validation;

import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import org.apache.commons.rdf.api.RDFTerm;

import com.moz.kiji.annotations.ApiStability.Stable;

import fr.inria.lille.shexjava.util.Pair;

/** A simpler version of a shape map that associates a {@link Status} to pairs of a node and a label.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
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
	
	/** Returns all pairs in the typing with their status.
	 * 
	 * @return
	 */
	@Stable
	public Map<Pair<RDFTerm, Label>, Status> getStatusMap(); 
	
	public default boolean isConformant (RDFTerm node, Label label) {
		return Status.CONFORMANT.equals(getStatus(node, label));
	}

}
