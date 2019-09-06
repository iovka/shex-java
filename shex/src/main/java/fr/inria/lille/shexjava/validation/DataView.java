package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.Graph;

public class DataView {
	private Graph graph;
	private Typing typing;
	private MatchingCollector mColl;
	
	
	public DataView(Graph graph, Typing typing, MatchingCollector mColl) {
		super();
		this.graph = graph;
		this.typing = typing;
		this.mColl = mColl;
	}


	public Graph getGraph() {
		return graph;
	}


	public Typing getTyping() {
		return typing;
	}


	public MatchingCollector getMatchingCollector() {
		return mColl;
	}
	
	
	
}
