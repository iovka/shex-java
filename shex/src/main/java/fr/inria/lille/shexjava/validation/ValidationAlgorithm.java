package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import com.moz.kiji.annotations.ApiStability.Stable;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

/** Allows to validate a graph against a ShEx schema.
 * 
 * A {@link ValidationAlgorithm} must be initialized with an {@link Graph} and a {@link ShexSchema}.
 * Then the {@link #validate(RDFTerm, Label)} method tests whether a node satisfies the expression corresponding to a label.
 * A call to {@link #validate(RDFTerm, Label)} also constructs a {@link Typing} that allows to prove or disprove the result returned by the method, 
   and that can be retrieved using {@link #getTyping()}. 
 * Successive calls to {@link #validate(RDFTerm, Label)} enrich the constructed {@link Typing}, unless it is reinitialized using {@link #resetTyping()}.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * 
 */
@Stable
// TODO ajouter ce qu'il faut pour les matching notifications, avant de rendre stable
public interface ValidationAlgorithm {

	// TODO this null argument stuff is not a good idea
	// What is to be the returned result if one of the two is null ?
	// The current implementation of RefineValidation returns false if one of the two is null, which is a bad idea
	/** Constructs a shape map that allows to validate a focus node against a type.
	 * Returns true if the focus node is conformant with the expression which label is given, and returns false otherwise.
	 * 
	 * @param focusNode The focus node for which the shape map is to be complete. If null, then the shape map will be complete for all nodes. The node might not belong to the graph, in which case it does not have a neighborhood.
	 * @param label The label against which the node is being tested. If null, then the node will be tested with all labels. 
	 * @return true iff the subsequent call to {@link #getTyping().Typing#getStatus()} returns {@link Status.CONFORMANT}
	 * @exception IllegalArgumentException if the label does not belong to the schema
	 */ 
	@Stable
	public abstract boolean validate(RDFTerm focusNode, Label label);
		
	/** The typing that proves the result returned by previous validations.  */
	@Stable
	public Typing getTyping();
	
	/** Resets the typing, thus erasing all previously computed validation results.  */
	@Stable
	public void resetTyping();
	

}
