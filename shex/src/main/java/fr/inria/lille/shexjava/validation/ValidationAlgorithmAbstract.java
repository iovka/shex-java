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

import fr.inria.lille.shexjava.schema.Label;
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
	
	protected Set<FailureAnalyzer> frcs;
	
	protected DynamicCollectorOfTripleConstraints collectorTC;
	
	private Set<MatchingCollector> matchingObservers;

	
	public ValidationAlgorithmAbstract(ShexSchema schema, Graph graph) {
		this.graph = graph;
		this.schema = schema;
		resetTyping();
	
		this.collectorTC = new DynamicCollectorOfTripleConstraints();
		this.frcs = new HashSet<>();
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

	// ---------------------------------------------------------------------------------
	// Failure report related
	// ---------------------------------------------------------------------------------	
	
	
	
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
	
	
}
