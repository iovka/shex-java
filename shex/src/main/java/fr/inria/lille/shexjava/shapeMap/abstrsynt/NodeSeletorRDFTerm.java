package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

public class NodeSeletorRDFTerm implements NodeSelector{
	protected RDFTerm node;
	protected HashSet<RDFTerm> result;
		
	public NodeSeletorRDFTerm(RDFTerm node) {
		super();
		this.node = node;
		result = new HashSet<RDFTerm>();
		result.add(node);
	}

	@Override
	public Collection<RDFTerm> apply(Graph g){
		return result;
				
	}
}
