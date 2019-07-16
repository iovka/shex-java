package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

public class NodeSelectorFilterSubject implements NodeSelector {
	protected IRI predicate;
	protected RDFTerm object;
	
		
	public NodeSelectorFilterSubject(IRI predicate, RDFTerm object) {
		super();
		this.predicate = predicate;
		this.object = object;
	}

	
	@Override
	public Collection<RDFTerm> apply(Graph g){
		return g.stream(null, predicate, object).map(tr -> tr.getSubject()).collect(Collectors.toSet());
	}

}
