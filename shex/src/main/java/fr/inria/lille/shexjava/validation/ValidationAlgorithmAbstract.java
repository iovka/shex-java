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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.util.CommonGraph;

/** An implementation of {@link ValidationAlgorithm} that offers some common utilities.
 * 
 * @author Iovka Boneva
 * 2 août 2018
 */
public abstract class ValidationAlgorithmAbstract implements ValidationAlgorithm {
	
	protected Graph graph;
	protected ShexSchema schema;
	
	protected Set<MatchingCollector> mcs;
	protected Set<FailureAnalyzer> frcs;
	
	protected DynamicCollectorOfTripleConstraints collectorTC;
	
	public ValidationAlgorithmAbstract(ShexSchema schema, Graph graph) {
		this.graph = graph;
		this.schema = schema;
		resetTyping();
	
		this.collectorTC = new DynamicCollectorOfTripleConstraints();
		this.mcs = new HashSet<>();
		this.mcs.add(new MatchingCollector());
		this.frcs = new HashSet<>();
	}


	/** Add a collector for the matching computed by the validation algorithm.
	 * 
	 */
	public void addMatchingCollector(MatchingCollector m) {
		mcs.add(m);
	}
	
	/** remove a collector for the matching computed by the validation algorithm.
	 * 
	 */
	public void removeMatchingCollector(MatchingCollector m){
		mcs.remove(m);
	}
	
	/** get the set of the current matching collectors.
	 * 
	 */
	public Set<MatchingCollector> getMatchingCollector() {
		return mcs;
	}
	
	/** Add a failure reports collector.
	 * 
	 */
	public void addFailureReportsCollector(FailureAnalyzer frc) {
		frcs.add(frc);
	}
	
	/** remove a failure reports collector.
	 * 
	 */
	public void removeFailureReportsCollector(FailureAnalyzer frc){
		frcs.remove(frc);
	}
	
	/** Get the set of the current failure reports collectors.
	 * 
	 */
	public Set<FailureAnalyzer> getFailureReportsCollector() {
		return frcs;
	}
	
	/** Select the neighborhood that must be matched for the given shape.
	 * 
	 * @param node
	 * @param shape
	 * @return
	 */
	protected ArrayList<Triple> getNeighbourhood(RDFTerm node, Shape shape) {
		List<TripleConstraint> constraints = collectorTC.getTCs(shape.getTripleExpression());
		
		Set<IRI> inversePredicate = new HashSet<>();
		Set<IRI> forwardPredicate = new HashSet<>();
		for (TripleConstraint tc:constraints)
			if (tc.getProperty().isForward())
				forwardPredicate.add(tc.getProperty().getIri());
			else
				inversePredicate.add(tc.getProperty().getIri());

		ArrayList<Triple> neighbourhood = new ArrayList<>();
		neighbourhood.addAll(CommonGraph.getInNeighboursWithPredicate(graph, node, inversePredicate));
		if (shape.isClosed())
			neighbourhood.addAll(CommonGraph.getOutNeighbours(graph, node));
		else
			neighbourhood.addAll(CommonGraph.getOutNeighboursWithPredicate(graph, node,forwardPredicate));
		
		return neighbourhood;
	}
	
	
	
}
