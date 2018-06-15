package fr.inria.lille.shexjava.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class ShapeMap {
	private Map<Pair<RDFTerm,Label>,TypingStatus> status;
	private Map<Label,Set<RDFTerm>> nodes;
	private Map<RDFTerm,Set<Label>> labels;

	
	public ShapeMap() {
		status = new HashMap<>();
		nodes = new HashMap<>();
		labels = new HashMap<>();
	}

	public void setStatus(RDFTerm node, Label label,TypingStatus status) {
		this.status.put(new Pair<RDFTerm,Label>(node,label), status);
		if (!nodes.containsKey(label))
			nodes.put(label, new HashSet<>());
		nodes.get(label).add(node);
		if (!labels.containsKey(node))
			labels.put(node, new HashSet<>());
		labels.get(node).add(label);
	}
	
	public TypingStatus getStatus(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(node,label);
		if (status.containsKey(key))
			return status.get(key);
		return TypingStatus.NOTCOMPUTED;
	}
	
	public boolean isConformant(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(node,label);
		if (status.containsKey(key))
			return status.get(key).equals(TypingStatus.CONFORMANT);
		return false;
	}
	
	public boolean isNonConformant(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(node,label);
		if (status.containsKey(key))
			return !status.get(key).equals(TypingStatus.CONFORMANT);
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
		Pair<RDFTerm, Label> key = new Pair<RDFTerm, Label>(node,label);
		status.remove(key);
		if (nodes.containsKey(label))
			nodes.get(label).remove(node);
		if (labels.containsKey(node))
			labels.get(node).remove(label);	
	}
	
	public Map<Pair<RDFTerm, Label>, TypingStatus> getAllStatus() {
		return status;
	}

}
