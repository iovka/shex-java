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
package fr.inria.lille.shexjava.validation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;

/** This class implement the algorithm to find a matching based on SORBE.
 * @author jdusart
 *
 */
public abstract class SORBEBasedValidation extends ValidationAlgorithmAbstract {
	protected SORBEGenerator sorbeGenerator;
	
	public SORBEBasedValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
	}
	
	/** Try to find a matching for the shape on the node using the typing. See also the MatchingCollector or FailureReportsCollecto.
	 * 
	 * @param node
	 * @param shape
	 * @param typing
	 * @return a matching or null if none was found or cannot be found. 
	 * @throws Exception 
	 */
	protected LocalMatching findMatching (RDFTerm node, Shape shape, Typing typing)  throws Exception {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());
		PreMatching preMatching = ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(),
				ValidationUtils.getPredicateAndValueMatcher(typing));
		
		LocalMatching result = null;
		// Look for correct matching within the pre-matching
		if (preMatching.getUnmatched().size()==0) {
			BagIterator bagIt = new BagIterator(preMatching);
			IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
			while(result==null && bagIt.hasNext()){
				if (this.compController != null) compController.canContinue();
				Bag bag = bagIt.next();
				tripleExpression.accept(intervalComputation, bag, this);
				if (intervalComputation.getResult().contains(1)) {
					Map<Triple, Label> matching = bagIt.getCurrentBag();
					matching = matching.entrySet().stream()
							.collect(Collectors.toMap(x -> x.getKey(), x -> sorbeGenerator.getOriginalNonsorbeVersion(x.getValue())));
					result = new LocalMatching(matching, preMatching.getMatchedToExtra(), preMatching.getUnmatched());
					notifyMatchingFound(node, shape.getId(), result);
				}

			}
		}		

		if (result == null) {
			result = new LocalMatching(null, preMatching.getMatchedToExtra(), preMatching.getUnmatched());
			notifyMatchingFound(node, shape.getId(), null);
		}
		
		return result;
	}
	
}
