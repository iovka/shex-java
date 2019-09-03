package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.Graph;

import fr.inria.lille.shexjava.schema.ShexSchema;

public class DataExtractor2{
	
	public void extractValidPart(ShexSchema schema, MatchingCollector mColl, Graph resultGraph) {
		mColl.getMatchingMap().keySet().stream().forEach(key-> mColl.getMatching(key.one, key.two).getMatching().keySet().stream()
															   .forEach(tr -> resultGraph.add(tr))
														);		
	}
	
	
}
