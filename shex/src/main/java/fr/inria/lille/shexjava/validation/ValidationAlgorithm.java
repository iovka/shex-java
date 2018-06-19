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

/** <p>Allows to validate a graph against a ShEx schema.</p>
 * 
 * <p>A {@link ValidationAlgorithm} must be initialized with an {@link RDFGraph} and a {@link ShexSchema}.
 * Then the {@link #validate(RDFTerm, Label)} method allows to construct a shape map of the graph by the schema.
 * The {@link #getShapeMap()} method allows to retrieve the shape map constructed by {@link #validate(RDFTerm, Label)}.</p>
 * 
 * <p>The method {@link #validate(RDFTerm, Label)} can take as parameter a node and a shape label, which both can be <code>null</code>.</p>
 * 
 * 
  * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public abstract  class  ValidationAlgorithm {
	protected Graph graph;
	protected ShexSchema schema;
	protected ShapeMap shapeMap;
	
	protected Set<MatchingCollector> mcs;
	protected Set<FailureReportsCollector> frcs;
	
	protected DynamicCollectorOfTripleConstraint collectorTC;

	public ValidationAlgorithm(ShexSchema schema, Graph graph) {
		this.graph = graph;
		this.schema = schema;
		this.shapeMap = new ShapeMap();
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
		this.mcs = new HashSet<>();
		this.frcs = new HashSet<>();
	}

	/** Constructs a shape map that allows to validate a focus node against a type.
	 * return true is focusNode has shape label, false otherwise.
	 * The constructed shape map can be retrieved using {@link #getShapeMap()}
	 * 
	 * @param focusNode The focus node for which the shape map is to be complete. If null, then the shape map will be complete for all nodes. The node might not belong to the graph, in which case it does not have a neighborhood.
	 * @param label The label for which the shape map is to be complete. If null, then the shape map will be complete for all labels.
	 */
	public abstract boolean validate(RDFTerm focusNode, Label label)  throws Exception;
	
	
	
	/** Retrieves the shape map constructed by a previous call of {@link #validate(RDFTerm, Label)}.
	 * 
	 */
	public ShapeMap getShapeMap() {
		return shapeMap;
	}
	
	public void resetShapeMap() {
		this.shapeMap = new ShapeMap();
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
	public void addFailureReportsCollector(FailureReportsCollector frc) {
		frcs.add(frc);
	}
	
	/** remove a failure reports collector.
	 * 
	 */
	public void removeFailureReportsCollector(FailureReportsCollector frc){
		frcs.remove(frc);
	}
	
	/** Get the set of the current failure reports collectors.
	 * 
	 */
	public Set<FailureReportsCollector> getFailureReportsCollector() {
		return frcs;
	}
}
