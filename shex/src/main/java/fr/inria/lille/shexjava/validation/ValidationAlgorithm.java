package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import com.moz.kiji.annotations.ApiStability.Stable;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.ResultShapeMap;

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
public interface ValidationAlgorithm {

	/** Constructs a shape map that allows to validate a focus node against a type.
	 * Returns true if the focus node is conformant with the expression which label is given, and returns false otherwise.
	 * 
	 * @param focusNode The focus node for which the shape map is to be complete. The node must belong to the graph.
	 * @param label The label against which the node is being tested.  
	 * @return true iff the subsequent call to {@link #getTyping() Typing#getStatus} returns {@link Status#CONFORMANT}
	 * @exception IllegalArgumentException if the label does not belong to the schema
	 */ 
	@Stable
	public boolean validate (RDFTerm focusNode, Label label);
	
	/** Constructs a shape map that allows to validate a focus node against a type.
	 * Returns a shape map with the results of the associations found in the shapeMap passed to the function.
	 * 
	 * @param shapeMap The shapeMap with the asociations requested.
	 * @return a shape map with the result
	 * @exception IllegalArgumentException if the label does not belong to the schema
	 */ 
	public ResultShapeMap validate (BaseShapeMap shapeMap);
		
	/** The typing that proves the result returned by previous validations.  */
	@Stable
	public Typing getTyping();
	
	/** Resets the typing, thus erasing all previously computed validation results.  */
	@Stable
	public void resetTyping();
	
	/** Notifies all observers that a matching was found, or that no matching is known for the given focusNode and label.
	 * The matching provided by this notification is not guaranteed to be valid w.r.t. the eventually computed correct typing that will be returned by {@link #getTyping()}.	 
	 * The {@link ValidationAlgorithm} guarantees however that for any given focusNode and label, the notification regarding these focusNode and label that is the last one between two consecutive calls of {@link #notifyStartValidation()} and {@link #notifyValidationComplete()}, contains a matching that is valid within the typing returned by {@link #getTyping()}.      
	 *  
	 * @param focusNode
	 * @param label
	 * @param matching null means that no matching is known
	 */
	public void notifyMatchingFound (RDFTerm focusNode, Label label, LocalMatching matching);
	
	/** Notifies all observers that a validation just started.
	 * Is called at the begining of {@link #validate(RDFTerm, Label)}
	 */
	public void notifyStartValidation();
	
	/** Notifies all observers that a validation just ended.  
	 * 
	 */
	public void notifyValidationComplete();
	
	public void addMatchingObserver (MatchingCollector o);
	public void removeMatchingObserver (MatchingCollector o);
	
	

}
