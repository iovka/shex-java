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


import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

/** <p>Allows to validate a graph against a ShEx schema.</p>
 * 
 * <p>A {@link ValidationAlgorithm} must be initialized with an {@link RDFGraph} and a {@link ShexSchema}.
 * Then the {@link #validate(RDFTerm, Label)} method allows to construct a typing of the graph by the schema.
 * The {@link #getTyping()} method allows to retrieve the typing constructed by {@link #validate(RDFTerm, Label)}.</p>
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
	protected Typing typing;
	protected Set<MatchingCollector> mc;
	protected DynamicCollectorOfTripleConstraint collectorTC;

	public ValidationAlgorithm(ShexSchema schema, Graph graph) {
		this.graph = graph;
		this.schema = schema;
		this.typing = new Typing();
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
	}

	/** Constructs a typing that allows to validate a focus node against a type.
	 * return true is focusNode has shape label, false otherwise.
	 * The constructed typing can be retrieved using {@link #getTyping()}
	 * 
	 * @param focusNode The focus node for which the typing is to be complete. If null, then the typing will be complete for all nodes. The node might not belong to the graph, in which case it does not have a neighborhood.
	 * @param label The label for which the typing is to be complete. If null, then the typing will be complete for all labels.
	 */
	public abstract boolean validate(RDFTerm focusNode, Label label)  throws Exception;
	
	/** Retrieves the typing constructed by a previous call of {@link #validate(RDFTerm, Label)}.
	 * 
	 */
	public Typing getTyping() {
		return typing;
	}
	
	public void resetTyping() {
		this.typing = new Typing();
	}
	
	/** Add a collector for the matching computed by the validation algorithm.
	 * 
	 */
	public void addMatchingCollector(MatchingCollector m) {
		mc.add(m);
	}
	
	/** remove a collector for the matching computed by the validation algorithm.
	 * 
	 */
	public void removeMatchingCollector(MatchingCollector m){
		mc.remove(m);
	}
	
	/** remove a collector for the matching computed by the validation algorithm.
	 * 
	 */
	public Set<MatchingCollector> getMatchingCollector(MatchingCollector m) {
		return mc;
	}
}
