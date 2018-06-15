package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
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

public abstract class SORBEBasedValidation extends ValidationAlgorithm{
	protected SORBEGenerator sorbeGenerator;
	protected DynamicCollectorOfTripleConstraint collectorTC;
	
	public SORBEBasedValidation(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
	}
	
	
	public List<Pair<Triple,Label>> findMatching(RDFTerm node, Shape shape, ShapeMap typing) {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getResult(tripleExpression);
		if (constraints.size() == 0) {
			if (!shape.isClosed())
				return new ArrayList<Pair<Triple,Label>>();
			else {
				if (CommonGraph.getOutNeighbours(graph, node).size()==0)
					return new ArrayList<Pair<Triple,Label>>();
				 else
					return null;
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
				if (mc !=null)
					for (MatchingCollector m:mc)
						m.setMatch(node, shape.getId(), result);
				return result;
			}
		}
		
		return null;
	}

	
	protected ArrayList<Triple> getNeighbourhood(RDFTerm node, Shape shape) {
		List<TripleConstraint> constraints = collectorTC.getResult(this.sorbeGenerator.getSORBETripleExpr(shape));
		
		Set<IRI> inversePredicate = new HashSet<IRI>();
		Set<IRI> forwardPredicate = new HashSet<IRI>();
		for (TripleConstraint tc:constraints)
			if (tc.getProperty().isForward())
				forwardPredicate.add(tc.getProperty().getIri());
			else
				inversePredicate.add(tc.getProperty().getIri());

		ArrayList<Triple> neighbourhood = new ArrayList<>();
		neighbourhood.addAll(CommonGraph.getInNeighboursWithPredicate(graph, node, inversePredicate));
		if (shape.isClosed())
			neighbourhood.addAll(CommonGraph.getOutNeighbours(graph, node));
		else
			neighbourhood.addAll(CommonGraph.getOutNeighboursWithPredicate(graph, node,forwardPredicate));
		
		return neighbourhood;
	}
	
}
