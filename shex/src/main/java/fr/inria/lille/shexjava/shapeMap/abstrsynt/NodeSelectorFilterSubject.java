package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

public class NodeSelectorFilterSubject implements NodeSelector {
	protected IRI predicate;
	protected RDFTerm object;
	
	
	public NodeSelectorFilterSubject(IRI predicate) {
		super();
		this.predicate = predicate;
		this.object = null;
	}
		
	public NodeSelectorFilterSubject(IRI predicate, RDFTerm object) {
		super();
		this.predicate = predicate;
		this.object = object;
	}

	
	@Override
	public Collection<RDFTerm> apply(Graph g){
		// FIXME here this should not be collected as set as anyway the stream guarantees there won't be duplicate objects
		return g.stream(null, predicate, object).map(tr -> tr.getSubject()).collect(Collectors.toSet());
	}


	@Override
	public String toString() {
		if (object == null)
			return "{ FOCUS "+predicate.ntriplesString()+" _ }";
		return "{ FOCUS "+predicate.ntriplesString()+" "+object.ntriplesString()+" }";	}

	
	
}
