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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.inria.lille.shexjava.schema.Label;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.util.Pair;


/** An implementation of {@link Typing} used during validation.
 * @author jdusart
 *
 */
public class TypingForValidation implements Typing {
	private Map<Pair<RDFTerm, Label>,Status> status;
	
	private Map<Label,Set<RDFTerm>> nodes;
	private Map<RDFTerm,Set<Label>> labels;

	
	public TypingForValidation() {
		status = new HashMap<>();
		nodes = new HashMap<>();
		labels = new HashMap<>();
	}

	public void setStatus(RDFTerm node, Label label,Status status) {
		this.status.put(new Pair<RDFTerm,Label>(node,label), status);
		if (!nodes.containsKey(label))
			nodes.put(label, new HashSet<>());
		nodes.get(label).add(node);
		if (!labels.containsKey(node))
			labels.put(node, new HashSet<>());
		labels.get(node).add(label);
	}
	
	@Override
	public Status getStatus(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<>(node,label);
		if (status.containsKey(key))
			return status.get(key);
		return Status.NOTCOMPUTED;
	}
	
	/**
	 * @deprecated  As of release 1.1a, replaced by {@link #isConformant(RDFTerm,Label)}
	 */
	@Deprecated
	public boolean contains(RDFTerm node, Label label) {
		return isConformant(node, label);
	}
	
	public boolean isConformant(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<>(node,label);
		if (status.containsKey(key))
			return status.get(key).equals(Status.CONFORMANT);
		return false;
	}
	
	public boolean isNonConformant(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<>(node,label);
		if (status.containsKey(key))
			return !status.get(key).equals(Status.CONFORMANT);
		return true;
	}
		
	public Set<Label> getShapesLabel(RDFTerm node){
		if (labels.containsKey(node))
			return labels.get(node);
		return Collections.emptySet();
	}
	
	public Set<RDFTerm> getNodes(Label label){
		if (nodes.containsKey(label))
			return nodes.get(label);
		return Collections.emptySet();
	}
	
	
	public void removeNodeLabel(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<>(node,label);
		status.remove(key);
		if (nodes.containsKey(label))
			nodes.get(label).remove(node);
		if (labels.containsKey(node))
			labels.get(node).remove(label);	
	}
	
	@Override
	public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
		return status;
	}

}
