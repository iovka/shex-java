/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;


public class FailureAnalyzerSimple extends FailureAnalyzer{
	public FailureAnalyzerSimple() {
		super();
	}

	@Override
	public void addFailureReportNoTCFound(RDFTerm node, Shape shape, Typing typing, Triple neighbour) {
		String message = "No TripleConstraint found to match the neighbour "+neighbour+".";
		message += " Maybe "+neighbour.getPredicate()+" should be in extra or verify the shape for "+getOther(neighbour,node)+".";
		this.setReport(new FailureReport(node,shape.getId(),message));
	}
	
	@Override
	public void addFailureReportNoMatchingFound(RDFTerm node, Shape shape, Typing typing, List<Triple> neighbourhood) {
		List<TripleConstraint> constraints = collectorTC.getTCs(shape.getTripleExpression());
		Matcher matcher = ValidationUtils.getPredicateAndValueMatcher(typing);
		Map<Triple,List<TripleConstraint>> matchingTC =
				// TODO here check whether the empty set is appropriate for the set of extra properties
				ValidationUtils.computePreMatching(node, neighbourhood, constraints, Collections.emptySet(), matcher).getPreMatching();
		
		LinkedHashMap<TripleConstraint,List<Triple>> revMatchingTC = new LinkedHashMap<>();
		for (TripleConstraint tc:constraints)
			revMatchingTC.put(tc, new ArrayList<Triple>());
		for (Triple tr:neighbourhood) {
			for (TripleConstraint tc:matchingTC.get(tr)) {
				revMatchingTC.get(tc).add(tr);
			}
		}
		
		if (shape.getTripleExpression() instanceof EachOf) {
			EachOf root = (EachOf) shape.getTripleExpression();
			for (TripleExpr sub:root.getSubExpressions()) {
				if (sub instanceof TripleConstraint) {
					TripleConstraint tc = (TripleConstraint) sub;
					if (revMatchingTC.get(sub).isEmpty()) {
						// No neighbour found
						String message = "No Triple found to match the TripleConstraint "+tc+".";
						
						Triple neighbour = null;
						for (Triple tmp:neighbourhood)
							if (tmp.getPredicate().equals(tc.getProperty().getIri()))
								neighbour = tmp;
						
						if (neighbour != null)
							message += " A neighbour with the right predicate has been found, maybe the shape for "+getOther(neighbour,node)+" need to be checked.";
						else
							message += " No neighbour found with the right predicate, maybe the graph need to be corrected.";					
						this.setReport(new FailureReport(node,shape.getId(),message));
						return;
					}
					if (revMatchingTC.get(sub).size()>1) {
						// maybe too many neighbor found to match
						// No neighbour found
						String message = "More than one Triple found to match the TripleConstraint "+tc+". Maybe there is too many of them.";				
						this.setReport(new FailureReport(node,shape.getId(),message));
						return;
					}
				}
				if (sub instanceof RepeatedTripleExpression) {
					RepeatedTripleExpression rep = (RepeatedTripleExpression) sub;
					if (rep.getSubExpression() instanceof TripleConstraint) {
						TripleConstraint tc = (TripleConstraint) rep.getSubExpression();
						if (revMatchingTC.get(tc).size()<rep.getCardinality().min) {
							// not enough neighbor found to match
							// maybe too many neighbor found to match
							String message = "Less than "+rep.getCardinality().min+" Triple found to match the TripleConstraint "+tc+". Maybe there is not enough of them.";				
							this.setReport(new FailureReport(node,shape.getId(),message));
							return;
						}
						if (revMatchingTC.get(tc).size()>rep.getCardinality().max) {
							// maybe too many neighbor found to match
							String message = "More than "+rep.getCardinality().max+" Triple found to match the TripleConstraint "+tc+". Maybe there is too many of them.";				
							this.setReport(new FailureReport(node,shape.getId(),message));
							return;
						}
					}
				}
			}
		}
	}
	
	
	private  RDFTerm getOther(Triple t, RDFTerm n){
		if (t.getObject().equals(n))
			return t.getSubject();
		else
			return t.getObject();
	}
}
