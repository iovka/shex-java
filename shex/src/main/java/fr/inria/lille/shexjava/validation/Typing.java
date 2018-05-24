package fr.inria.lille.shexjava.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class Typing {
	private Map<Pair<RDFTerm,Label>,String> messages;
	private Map<Pair<RDFTerm,Label>,TypingStatus> status;
	private Map<Label,Set<RDFTerm>> nodes;
	private Map<RDFTerm,Set<Label>> labels;
	private Map<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>> matching;

	
	public Typing() {
		messages = new HashMap<>();
		status = new HashMap<>();
		nodes = new HashMap<>();
		labels = new HashMap<>();
		matching = new HashMap<>();
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
	
	public void setMessage(RDFTerm node, Label label,String message) {
		this.messages.put(new Pair<RDFTerm,Label>(node,label), message);
	}
	
	public String getMessage(RDFTerm node, Label label) {
		return messages.get(new Pair<RDFTerm,Label>(node,label));
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
	
	public void setMatch(RDFTerm node, Label label, List<Pair<Triple, Label>> match) {
		matching.put(new Pair<RDFTerm, Label>(node,label), match);	
	}
	
	public List<Pair<Triple, Label>> getMatch(RDFTerm node, Label label) {
		return matching.get(new Pair<RDFTerm, Label>(node,label));		
	}

	public void removeNodeLabel(RDFTerm node, Label label) {
		Pair<RDFTerm, Label> key = new Pair<RDFTerm, Label>(node,label);
		status.remove(key);
		messages.remove(key);
		matching.remove(key);
		if (nodes.containsKey(label))
			nodes.get(label).remove(node);
		if (labels.containsKey(node))
			labels.get(node).remove(label);	
	}

}
