package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.Pair;

public class MatchingCollector {
	protected Map<Pair<RDFTerm, Label>,List<Pair<Triple,Label>>> matchings;

	public MatchingCollector() {
		matchings = new HashMap<>();
	}
	
	public void setMatch(RDFTerm node, Label label, List<Pair<Triple, Label>> match) {
		matchings.put(new Pair<RDFTerm, Label>(node,label), match);	
	}
	
	public List<Pair<Triple, Label>> getMatch(RDFTerm node, Label label) {
		return matchings.get(new Pair<RDFTerm, Label>(node,label));		
	}
	
	public void tryToGuess(RDFTerm node, Shape shape, ShapeMap typing, List<TripleConstraint> constraints, List<Triple> neighbourhood) {
		if (shape.getTripleExpression() instanceof EachOf) {			
			EachOf root = (EachOf) shape.getTripleExpression();
			
			Matcher matcher1 = new MatcherPredicateOnly();
			LinkedHashMap<Triple,List<TripleConstraint>> matchingTC1 = matcher1.collectMatchingTC(node, neighbourhood, constraints);	
			Map<TripleConstraint,List<Triple>> countTC1 = countTC(matchingTC1);
			
			Matcher matcher2 = new MatcherPredicateAndValue(typing); 
			LinkedHashMap<Triple,List<TripleConstraint>> matchingTC2 = matcher2.collectMatchingTC(node, neighbourhood, constraints);
			Map<TripleConstraint,List<Triple>> countTC2 = countTC(matchingTC2);
			
			for (TripleExpr sub:root.getSubExpressions()) {
				if (sub instanceof RepeatedTripleExpression) {
					RepeatedTripleExpression rep = (RepeatedTripleExpression) sub;
					if(rep.getSubExpression() instanceof TripleConstraint) {
						TripleConstraint key = (TripleConstraint) rep.getSubExpression();
						if (countTC2.get(key).size()<rep.getCardinality().min) {
							// Not enough neighbour with type 
						} else if (rep.getCardinality().max<countTC2.get(key).size()) {
							boolean success = false;
							for (TCProperty extra : shape.getExtraProperties())
								if (extra.getIri().equals(key.getProperty().getIri()))
									success = true;
							if(!success ) {
								// Too many neighbor with type 
							}
						} 						
					} else if(sub instanceof TripleConstraint) {
						if (countTC2.get(sub).size()==0) {
							// no neighbour to match the TC2
							
						} else if (countTC2.get(sub).size()>1) {
							// too many neighbour to match the TC
						}
					}
				}
			}
		}
	}

	
	protected Map<TripleConstraint,List<Triple>> countTC(LinkedHashMap<Triple,List<TripleConstraint>> matchingTC) {
		Map<TripleConstraint,List<Triple>> counts = new HashMap<TripleConstraint,List<Triple>>();
		for (Triple tr:matchingTC.keySet()) {
			for(TripleConstraint tc:matchingTC.get(tr)) {
				if (!counts.containsKey(tc))
					counts.put(tc,new ArrayList<Triple>());	
				counts.get(tc).add(tr);					
			}
		}
		return counts;
	}
	
}
