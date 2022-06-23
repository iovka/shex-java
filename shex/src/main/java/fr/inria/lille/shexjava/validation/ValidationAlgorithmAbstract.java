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

import fr.inria.lille.shexjava.schema.Label;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.ResultShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSeletorRDFTerm;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelectorLabel;


/** An implementation of {@link ValidationAlgorithm} that offers some common utilities.
 * 
 * @author Iovka Boneva
 * 2 août 2018
 */
public abstract class ValidationAlgorithmAbstract implements ValidationAlgorithm {
	protected Graph graph;
	protected ShexSchema schema;
	protected ComputationController compController;
	
	protected DynamicCollectorOfTripleConstraints collectorTC;
	
	private Set<MatchingCollector> matchingObservers;

	
	public ValidationAlgorithmAbstract(ShexSchema schema, Graph graph) {
		initialize(schema, graph);
	}



	private void initialize(ShexSchema schema, Graph graph) {
		this.graph = graph;
		this.schema = schema;

		resetTyping();

		this.collectorTC = new DynamicCollectorOfTripleConstraints();
		this.matchingObservers = new HashSet<>();
	}

	protected abstract boolean performValidation(RDFTerm focusNode, Label label) throws Exception;

	/** (non-Javadoc)
	 * @see fr.inria.lille.shexjava.validation.ValidationAlgorithm#validate(org.apache.commons.rdf.api.RDFTerm, fr.inria.lille.shexjava.schema.Label)
	 */
	@Override
	public boolean validate(RDFTerm focusNode, Label label){
		try {
			return validate(focusNode, label, null);
		} catch (Exception e) {
			// we should never be here
			e.printStackTrace();
			return false;
		}	
	}

	public boolean validate (RDFTerm focusNode, Label label, ComputationController compController) throws Exception {
		if (focusNode==null || label==null)
			throw new IllegalArgumentException("Invalid argument value: focusNode or label cannot be null.");
		if (!schema.getShapeExprsMap().containsKey(label))
			throw new IllegalArgumentException("Unknown label: "+label);
		this.compController = compController;
		if (this.compController!=null) this.compController.start();
		boolean res = performValidation(focusNode,label);
		this.compController = null;
		return res;
	}

	public ResultShapeMap validate(BaseShapeMap shapeMap) {
		try {
			return validate(shapeMap,null);
		} catch (Exception e) {
			// we should never be here
			System.err.println("Exception during the validation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultShapeMap validate(BaseShapeMap inputShapeMap, ComputationController compController) throws Exception {
		List<ShapeAssociation> results = new ArrayList<>();
		
		for (ShapeAssociation sa: inputShapeMap.getAssociations()) {
			Label seLabel = sa.getShapeSelector().apply(schema);
			for(RDFTerm node:sa.getNodeSelector().apply(graph)) {
				ShapeAssociation saRes = new ShapeAssociation(new NodeSeletorRDFTerm(node), new ShapeSelectorLabel(seLabel));
				if (validate(node,seLabel,compController))
					saRes.setStatus(Status.CONFORMANT);
				else 
					saRes.setStatus(Status.NONCONFORMANT);
				results.add(saRes);
			}
		}
		
		return new ResultShapeMap(results);
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

	
	protected ComputationController getCompController() {
		return compController;
	}

	
	
}
