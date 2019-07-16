package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

public class NodeSelectorFilterObject implements NodeSelector {
	protected BlankNodeOrIRI subject;
	protected IRI predicate;
	
		
	public NodeSelectorFilterObject(BlankNodeOrIRI subject, IRI predicate) {
		super();
		this.subject = subject;
		this.predicate = predicate;
	}

	
	@Override
	public Collection<RDFTerm> apply(Graph g){
		return g.stream(subject, predicate, null).map(tr -> tr.getObject()).collect(Collectors.toSet());
	}

}
