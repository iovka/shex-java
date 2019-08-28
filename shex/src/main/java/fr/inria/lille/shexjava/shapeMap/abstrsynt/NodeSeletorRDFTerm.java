package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.util.RDFPrintUtils;

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

	@Override
	public String toString() {
		return RDFPrintUtils.toPrettyString(node, Collections.EMPTY_MAP);
	}
	
	
}
