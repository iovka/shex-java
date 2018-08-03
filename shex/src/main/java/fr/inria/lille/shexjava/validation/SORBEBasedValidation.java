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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.Pair;

/** This class implement the algorithm to find a matching based on SORBE.
 * @author jdusart
 *
 */
public abstract class SORBEBasedValidation extends ValidationAlgorithmAbstract{
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
	 */
	public List<Pair<Triple,Label>> findMatching(RDFTerm node, Shape shape, Typing typing) {
		// TODO the expression should not be generated each time, but rather generated dynamically
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<Pair<Triple,Label>> result = null;
		
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);
		if (constraints.isEmpty())
			result = Collections.emptyList();
		// TODO here removed the notification failure report in the case no constraints, closed shape and non empty neighbourhood
		// fr.setReport(new FailureReport(node,shape.getId(),"Shape is closed with no constraint, but "+node+" has neighbour"));
		
		ArrayList<Triple> neighbourhood = getNeighbourhood(node,shape);
		
		PreMatching preMatching = ValidationUtils.computePreMatching(node, neighbourhood, constraints, ValidationUtils.getPredicateAndValueMatcher(getTyping()));
		
		List<Triple> notInExtra = collectNotInExtra(preMatching.getUnmatchedTriples(), shape);
		if (! notInExtra.isEmpty()) {
			result = null;
			// TODO do something about the reporting
		}
		
		// Look for correct matching within the pre-matching
		BagIterator bagIt = new BagIterator(preMatching);
		IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag, this);
			if (intervalComputation.getResult().contains(1)) {
				result = bagIt.getCurrentBag();
			}
		}
		
		for (FailureAnalyzer fr:frcs)
			fr.addFailureReportNoMatchingFound(node, shape, typing, neighbourhood);
		
		// Get back to the original triple constraints, removing their SORBE equivalent 
		if (result != null) {
			result = result.stream()
					.map(p -> new Pair<>(p.one, sorbeGenerator.removeSORBESuffixe(p.two)))
					.collect(Collectors.toList());
		}			
		notifySetMatching(node, shape, result);		
		return result;
	}

	private List<Triple> collectNotInExtra(List<Triple> unmatchedTriples, Shape shape) {
		// TODO : to be removed when extra will be defined as iri and not as tcproperty
		Set<IRI> extraIris = shape.getExtraProperties().stream()
									.map(tcprop -> tcprop.getIri())
									.collect(Collectors.toSet());
		return unmatchedTriples.stream()
				.filter(triple -> ! extraIris.contains(triple.getPredicate()))
				.collect(Collectors.toList());
	}
	
	private void notifySetMatching(RDFTerm node, Shape shape, List<Pair<Triple, Label>> result) {
		for (MatchingCollector m:mcs)
			m.setMatch(node, shape.getId(), result);
	}
	
}
