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


import java.util.HashSet;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.ComputationController;
import fr.inria.lille.shexjava.util.SimpleComputationController;

/** An implementation of {@link ValidationAlgorithm} that offers some common utilities.
 * 
 * @author Iovka Boneva
 * 2 août 2018
 */
public abstract class ValidationAlgorithmAbstract implements ValidationAlgorithm {
	protected Graph graph;
	protected Set<RDFTerm> allGraphNodes;
	protected ShexSchema schema;
	protected ComputationController controller;
	
	protected DynamicCollectorOfTripleConstraints collectorTC;
	
	private Set<MatchingCollector> matchingObservers;

	
	public ValidationAlgorithmAbstract(ShexSchema schema, Graph graph) {
		initialize(schema, graph, new SimpleComputationController());
	}
	
	
	public ValidationAlgorithmAbstract(ShexSchema schema, Graph graph, ComputationController controller) {
		initialize(schema, graph, controller);
	}
	
	
	private void initialize(ShexSchema schema, Graph graph, ComputationController controller) {
		this.graph = graph;
		this.schema = schema;
		this.controller = controller;
		this.allGraphNodes = CommonGraph.getAllNodes(graph);
		resetTyping();
	
		this.collectorTC = new DynamicCollectorOfTripleConstraints();
		this.matchingObservers = new HashSet<>();
	}
	
	// ---------------------------------------------------------------------------------
	// Observers related
	// ---------------------------------------------------------------------------------	
	@Override
	public void notifyMatchingFound(RDFTerm focusNode, Label label, LocalMatching matching) {
		for (MatchingCollector m : matchingObservers)
			m.updateMatching(focusNode, label, matching);
	}

	@Override
	public void notifyStartValidation() {
		for (MatchingCollector m : matchingObservers)
			m.startValidation();
	}

	@Override
	public void notifyValidationComplete() {
		for (MatchingCollector m : matchingObservers)
			m.validationComplete();
	}
	
	@Override
	public void addMatchingObserver(MatchingCollector o) {
		matchingObservers.add(o);
	}


	@Override
	public void removeMatchingObserver(MatchingCollector o) {
		matchingObservers.remove(o);
	}

	public ComputationController getController() {
		return controller;
	}

	public void setController(ComputationController controller) {
		this.controller = controller;
	}

	
}
