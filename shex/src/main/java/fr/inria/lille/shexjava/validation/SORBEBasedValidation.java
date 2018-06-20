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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;

/** This class implement the algorithm to find a matching based on SORBE.
 * @author jdusart
 *
 */
public abstract class SORBEBasedValidation extends ValidationAlgorithm{
	protected SORBEGenerator sorbeGenerator;
	protected DynamicCollectorOfTripleConstraint collectorTC;
	
	public SORBEBasedValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
	}
	
	/** Try to find a matching for the shape on the node using the typing. See also the MatchingCollector or FailureReportsCollecto.
	 * 
	 * @param node
	 * @param shape
	 * @param typing
	 * @return a matching or null if none was found or cannot be found. 
	 */
	public List<Pair<Triple,Label>> findMatching(RDFTerm node, Shape shape, ShapeMap typing) {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getResult(tripleExpression);
		if (constraints.size() == 0) {
			if (!shape.isClosed()) {
				List<Pair<Triple,Label>> result = new ArrayList<Pair<Triple,Label>>();
				for (MatchingCollector m:mcs)
					m.setMatch(node, shape.getId(), result);
				return result;
			} else {
				if (CommonGraph.getOutNeighbours(graph, node).size()==0) {
					List<Pair<Triple,Label>> result = new ArrayList<Pair<Triple,Label>>();
					for (MatchingCollector m:mcs)
						m.setMatch(node, shape.getId(), result);
					return result;
				} else {
					for (FailureAnalyzer fr:frcs)
						fr.setReport(new FailureReport(node,shape.getId(),"Shape is closed with no constraint, but "+node+" has neighbour"));
					return null;
				}
			}
		}
				
		ArrayList<Triple> neighbourhood = getNeighbourhood(node,shape);
		
		Matcher matcher = new MatcherPredicateAndValue(typing); 
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTC = matcher.collectMatchingTC(node, neighbourhood, constraints);
		// Check that the neighbor that cannot be match to a constraint are in extra
		Iterator<Map.Entry<Triple,List<TripleConstraint>>> iteMatchingTC = matchingTC.entrySet().iterator();
		while(iteMatchingTC.hasNext()) {
			Entry<Triple, List<TripleConstraint>> listTC = iteMatchingTC.next();
			if (listTC.getValue().isEmpty()) {
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(listTC.getKey().getPredicate()))
						success = true;
				if (!success) {
					for (FailureAnalyzer fr:frcs)
						fr.addFailureReportNoTCFound(node, shape, typing, listTC.getKey());
					return null;
				}
				iteMatchingTC.remove();
			}
		}

		// Create a BagIterator for all possible bags induced by the matching triple constraints
		ArrayList<List<TripleConstraint>> listMatchingTC = new ArrayList<List<TripleConstraint>>();
		for(Triple nt:matchingTC.keySet())
			listMatchingTC.add(matchingTC.get(nt));

		BagIterator bagIt = new BagIterator(neighbourhood,listMatchingTC);

		IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag, this);
			if (intervalComputation.getResult().contains(1)) {
				List<Pair<Triple,Label>> result = new ArrayList<Pair<Triple,Label>>();
				for (Pair<Triple,Label> pair:bagIt.getCurrentBag())
					result.add(new Pair<Triple,Label>(pair.one,sorbeGenerator.removeSORBESuffixe(pair.two)));
				for (MatchingCollector m:mcs)
					m.setMatch(node, shape.getId(), result);
				return result;
			}
		}
		
		for (FailureAnalyzer fr:frcs)
			fr.addFailureReportNoMatchingFound(node, shape, typing, neighbourhood);
		
		return null;
	}

	
}
