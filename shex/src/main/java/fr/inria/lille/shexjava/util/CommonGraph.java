package fr.inria.lille.shexjava.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

public class CommonGraph {

	public static List<Triple> getOutNeighbours(Graph g,RDFTerm focusNode){
		if (focusNode instanceof Literal)
			return Collections.emptyList();
		return g.stream((BlankNodeOrIRI) focusNode, null, null).collect(Collectors.toList());
	}
	
	public static List<Triple> getOutNeighboursWithPredicate(Graph g,RDFTerm focusNode, Set<IRI> predicates){
		if (focusNode instanceof Literal)
			return Collections.emptyList();
		List<Triple> result = new LinkedList<>();
		for (IRI pred:predicates)
			result.addAll(g.stream((BlankNodeOrIRI) focusNode, pred, null).collect(Collectors.toList()));
		return result;
	}
	
	public static List<Triple> getInNeighboursWithPredicate(Graph g,RDFTerm focusNode, Set<IRI> predicates){
		if (focusNode instanceof Literal)
			return Collections.emptyList();
		List<Triple> result = new LinkedList<>();
		for (IRI pred:predicates)
			result.addAll(g.stream(null, pred, focusNode).collect(Collectors.toList()));
		return result;
	}
	
	public static HashSet<RDFTerm> getAllNodes(Graph g){
		HashSet<RDFTerm> result = new HashSet<RDFTerm>();
		Iterator<Triple> iter = g.iterate().iterator();
		while(iter.hasNext()) {
			Triple next = iter.next();
			result.add(next.getObject());
			result.add(next.getSubject());
		}
		return result;
	}

}
