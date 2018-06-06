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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.util.CommonGraph;
import fr.inria.lille.shexjava.util.Pair;


/** Implements the Recursive validation algorithm.
 * This algorithm will check only the shape definition necessary, but can return false positive.
 * 
 * @author Jérémie Dusart 
 */
public class RecursiveMemValidation implements ValidationAlgorithm {
	private Graph graph;
	private SORBEGenerator sorbeGenerator;
	private ShexSchema schema;
	private Typing typing;
	
	private DynamicCollectorOfTripleConstraint collectorTC;
	
	
	public RecursiveMemValidation(ShexSchema schema, Graph graph) {
		super();
		this.graph = graph;
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
		this.schema = schema;
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
		this.typing = new Typing();
	}
	
	public void resetTyping() {
		this.typing = new Typing();
	}
	
	@Override
	public Typing getTyping() {
		return typing;
	}	
	
	@Override
	public boolean validate(RDFTerm focusNode, Label label) throws Exception {
		if (label == null || !schema.getShapeMap().containsKey(label))
			throw new Exception("Unknown label: "+label);
		this.resetTyping();
		boolean result = recursiveValidation(focusNode,
											 label,
									 		 new LinkedList<>(),
									 		 new DefaultDirectedGraph<>(DefaultEdge.class),
											 new HashMap<>(),
											 new HashMap<>());		
		return result;
	}

	
	protected boolean recursiveValidation(RDFTerm focusNode, 
										  Label label, 
										  LinkedList<Pair<RDFTerm,Label>> hyp,
										  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
										  Map<Pair<RDFTerm,Label>,Boolean> results,
										  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		Pair<RDFTerm,Label> key = new Pair<>(focusNode,label);
		if (hyp.contains(key))
			return true;
		if (!this.typing.getStatus(focusNode, label).equals(TypingStatus.NOTCOMPUTED))
			return this.typing.isConformant(focusNode, label);
		if (g.containsVertex(key))
			return results.get(key);
		
		if (schema.getShapeMap().get(label) instanceof NodeConstraint) {
			boolean res = ((NodeConstraint)schema.getShapeMap().get(label)).contains(focusNode);
			updateGraph(focusNode, label, Collections.emptySet(), res, hyp, g, results, lowestDep);		
			return res;		
		}
		
		hyp.addLast(key);
		
		boolean res=false;
		if (schema.getShapeMap().get(label) instanceof ShapeNot)
			res=recursiveValidationShapeNot(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeMap().get(label) instanceof ShapeExprRef)
			res=recursiveValidationShapeExprRef(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeMap().get(label) instanceof ShapeOr)
			res=recursiveValidationShapeOr(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeMap().get(label) instanceof ShapeAnd)
			res=recursiveValidationShapeAnd(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeMap().get(label) instanceof Shape)
			res=recursiveValidationShape(focusNode,label,hyp,g,results,lowestDep);
				
		hyp.remove(key);
		if (g.containsVertex(key)) {
			results.put(key, res);
			memorize(focusNode,label,hyp,g,results,lowestDep);
		}
		return res;
	}
	
	
	protected boolean recursiveValidationShapeNot(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeNot shape = (ShapeNot) schema.getShapeMap().get(label);
		
		boolean res = ! this.recursiveValidation(focusNode, shape.getSubExpression().getId(),hyp,g,results,lowestDep);
				
		Set<Label> required = new HashSet<>();
		required.add(shape.getSubExpression().getId());
		
		
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;
	}
	
	
	
	protected boolean recursiveValidationShapeExprRef(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeExprRef shape = (ShapeExprRef) schema.getShapeMap().get(label);
		
		boolean res = this.recursiveValidation(focusNode, shape.getLabel(),hyp,g,results,lowestDep);
		
		Set<Label> required = new HashSet<>();
		required.add(shape.getLabel());
		
		
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;
	}
	
	protected boolean recursiveValidationShapeAnd(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeAnd shape = (ShapeAnd) schema.getShapeMap().get(label);
		boolean res = true;
		Set<Label> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, results, lowestDep);
			if (res) {
				required.add(next.getId());
			} else {
				required = new HashSet<>();
				required.add(next.getId());
			}
		}
		
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;	
	}
	
	protected boolean recursiveValidationShapeOr(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeOr shape = (ShapeOr) schema.getShapeMap().get(label);
		boolean res = false;
		Set<Label> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(!res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, results, lowestDep);
			if (res) {
				required = new HashSet<>();
				required.add(next.getId());
			} else {
				required.add(next.getId());				
			}
		}
				
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;	
	}
	
	
	private boolean recursiveValidationShape (RDFTerm node,
			Label label, 
			LinkedList<Pair<RDFTerm,Label>> hyp,
			DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			Map<Pair<RDFTerm,Label>,Boolean> results,
			Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep){

		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		List<Triple> extraNeighbours = new ArrayList<>();
		
		Shape shape = (Shape) schema.getShapeMap().get(label);
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);

		List<TripleConstraint> constraints = collectorTC.getResult(tripleExpression);
		if (constraints.size() == 0) {
			if (!shape.isClosed()) {
				updateGraphShape(node, label, required, true, hyp, g, results, lowestDep);		
				return true;
			} else {
				if (CommonGraph.getOutNeighbours(graph, node).size()==0) {
					updateGraphShape(node, label, required, true, hyp, g, results, lowestDep);		
					return true;
				} else {
					updateGraphShape(node, label, required, false, hyp, g, results, lowestDep);		
					return false;
				}
			}
		}
		
		
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


		// Match using only predicate and recursive test.
		Typing localTyping = new Typing();
		Matcher matcher = new MatcherPredicateOnly();
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTC1 = matcher.collectMatchingTC(node, neighbourhood, constraints);	

		for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {
			if (entry.getValue().isEmpty()) {
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(entry.getKey().getPredicate()))
						success = true;
				if (!success) {
					updateGraphShape(node, label, required, false, hyp, g, results, lowestDep);		
					return false;
				}
			}
			
			for (TripleConstraint tc:entry.getValue()) {
				RDFTerm destNode = entry.getKey().getObject();
				if (!tc.getProperty().isForward())
					destNode = entry.getKey().getSubject();
	
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(TypingStatus.NOTCOMPUTED)) {
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId(),hyp,g,results,lowestDep)) 
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),TypingStatus.CONFORMANT);
					else
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),TypingStatus.NONCONFORMANT);	
				} else {
					localTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));
				}
			}

		}
		
		// Add the detected node value to the typing
		Matcher matcher2 = new MatcherPredicateAndValue(localTyping); 
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTC2 = matcher2.collectMatchingTC(node, neighbourhood, constraints);

		// Check that the neighbor that cannot be match to a constraint are in extra
		Iterator<Map.Entry<Triple,List<TripleConstraint>>> iteMatchingTC = matchingTC2.entrySet().iterator();
		while(iteMatchingTC.hasNext()) {
			Entry<Triple, List<TripleConstraint>> listTC = iteMatchingTC.next();
			if (listTC.getValue().isEmpty()) {
				extraNeighbours.add(listTC.getKey());
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(listTC.getKey().getPredicate()))
						success = true;
				if (!success) {
					// Looking at the calls that fails
					for (TripleConstraint tc:matchingTC1.get(listTC.getKey())){
						RDFTerm destNode = listTC.getKey().getObject();
						required.add(new Pair<>(destNode,tc.getShapeExpr().getId()));
					}
					updateGraphShape(node, label, required, false, hyp, g, results, lowestDep);
					return false;
				}
				
				iteMatchingTC.remove();
			}
		}
		
		// Create a BagIterator for all possible bags induced by the matching triple constraints
		ArrayList<Triple> selectedNeighbourhood = new ArrayList<Triple>();
		ArrayList<List<TripleConstraint>> listMatchingTC = new ArrayList<List<TripleConstraint>>();
		for(Triple nt:matchingTC2.keySet()) {
			selectedNeighbourhood.add(nt);
			listMatchingTC.add(matchingTC2.get(nt));
		}
		
		BagIterator bagIt = new BagIterator(selectedNeighbourhood,listMatchingTC);

		IntervalComputation intervalComputation = new IntervalComputation(this.collectorTC);
		
		while(bagIt.hasNext()){
			Bag bag = bagIt.next();
			tripleExpression.accept(intervalComputation, bag, this);
			if (intervalComputation.getResult().contains(1)) {
				List<Pair<Triple,Label>> result = new ArrayList<Pair<Triple,Label>>();
				for (Pair<Triple,Label> pair:bagIt.getCurrentBag()) {
					result.add(new Pair<Triple,Label>(pair.one,sorbeGenerator.removeSORBESuffixe(pair.two)));
				}
				typing.setMatch(node, shape.getId(), result);
				// Update the graph if necessary
				for (Pair<Triple,Label> pair:result) {
					RDFTerm other = getOther(pair.one,node);
					Label depLabel = ((TripleConstraint) schema.getTripleMap().get(pair.two)).getShapeExpr().getId();
					
						required.add(new Pair<>(other, depLabel));
					
				}
				for (Triple tri:extraNeighbours) {
					RDFTerm other = getOther(tri,node);
					for (Label l:localTyping.getShapesLabel(other)) {
						required.add(new Pair<>(other, l));
					}
				}
				updateGraphShape(node, label, required, true, hyp, g, results, lowestDep);		

				return true;
			}
		}
		// Update the graph if necessary
		Pair<RDFTerm,Label> key1 = new Pair<>(node,label);
		for (Pair<RDFTerm,Label> pair:localTyping.getAllStatus().keySet()) {
			if (localTyping.isNonConformant(pair.one,pair.two)) {
				required.add(pair);				
			}
		}
		updateGraphShape(node, label, required, false, hyp, g, results, lowestDep);		
		
		
		return false;
	}
	
	

	// for updating the graph
	
	protected void memorize(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		LinkedList<Pair<RDFTerm,Label>> S = new LinkedList<>();
		S.add(new Pair<>(focusNode,label));
		if (results.get(new Pair<>(focusNode,label))) {
			while (S.size()>0) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				if (this.typing.getStatus(key.one, key.two).equals(TypingStatus.NOTCOMPUTED) &&
						!hyp.contains(lowestDep.get(key))) {
					if (results.get(key))
						this.typing.setStatus(key.one, key.two, TypingStatus.CONFORMANT);
					else
						this.typing.setStatus(key.one, key.two, TypingStatus.NONCONFORMANT);
				}
				if (this.typing.getStatus(key.one, key.two).equals(TypingStatus.NOTCOMPUTED)) {
					for(DefaultEdge edge: g.incomingEdgesOf(key))
						S.add(g.getEdgeSource(edge));
				}
			}
		} else {
			while (S.size()>0) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				for(DefaultEdge edge: g.incomingEdgesOf(key))
					S.add(g.getEdgeSource(edge));	
				g.removeVertex(key);
			}
		}
	}
	
	// Update the graph of dependencies
	
	private void updateGraph(RDFTerm focusNode,
			Label label,
			Set<Label> required,
			boolean res,
			LinkedList<Pair<RDFTerm,Label>> hyp,
			DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			Map<Pair<RDFTerm,Label>,Boolean> results,
			Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		Pair<RDFTerm,Label> key1 = new Pair<>(focusNode,label);
		boolean canSave = true;
		for(Label dep:required) {
			if (this.typing.getStatus(focusNode, dep).equals(TypingStatus.NOTCOMPUTED)) {
				Pair<RDFTerm,Label> key2 = new Pair<>(focusNode,dep);
				if (!g.containsVertex(key1))
					g.addVertex(key1);
				if (!g.containsVertex(key2))
					g.addVertex(key2);
				results.put(key2, res);
				g.addEdge(key1, key2);
				if (!lowestDep.containsKey(key1)) {	
					if (lowestDep.containsKey(key2)) {
						lowestDep.put(key1, lowestDep.get(key2));
					} else { //key2 must be in hyp since not in typing and not in g
						lowestDep.put(key1, key2);
					}
					
				} else {
					if (lowestDep.containsKey(key2)) {
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(lowestDep.get(key2))) {
							lowestDep.put(key1, lowestDep.get(key2));
						}
					} else { //key2 must be in hyp since not in typing and not in g
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(key2)) {
							lowestDep.put(key1, key2);
						}
					}
				}
				canSave = false;
			}
		}

		if (canSave) {
			if (res) {
				this.typing.setStatus(focusNode, label, TypingStatus.CONFORMANT);
			} else {
				this.typing.setStatus(focusNode, label, TypingStatus.NONCONFORMANT);
			}
		}
	}
	
	private void updateGraphShape(RDFTerm focusNode,
			Label label,
			Set<Pair<RDFTerm,Label>> required,
			boolean res,
			LinkedList<Pair<RDFTerm,Label>> hyp,
			DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			Map<Pair<RDFTerm,Label>,Boolean> results,
			Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		Pair<RDFTerm,Label> key1 = new Pair<>(focusNode,label);

		boolean canSave = true;
		for(Pair<RDFTerm,Label> key2:required) {
			if (this.typing.getStatus(key2.one, key2.two).equals(TypingStatus.NOTCOMPUTED)) {
				if (!g.containsVertex(key1))
					g.addVertex(key1);
				if (!g.containsVertex(key2))
					g.addVertex(key2);
				results.put(key2, res);
				g.addEdge(key1, key2);
				if (!lowestDep.containsKey(key1)) {	
					if (lowestDep.containsKey(key2)) {
						lowestDep.put(key1, lowestDep.get(key2));
					} else { //key2 must be in hyp since not in typing and not in g
						lowestDep.put(key1, key2);
					}
					
				} else {
					if (lowestDep.containsKey(key2)) {
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(lowestDep.get(key2))) {
							lowestDep.put(key1, lowestDep.get(key2));
						}
					} else { //key2 must be in hyp since not in typing and not in g
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(key2)) {
							lowestDep.put(key1, key2);
						}
					}
				}
				canSave = false;
			}
		}
		if (canSave) {
			if (res) {
				this.typing.setStatus(focusNode, label, TypingStatus.CONFORMANT);
			} else {
				this.typing.setStatus(focusNode, label, TypingStatus.NONCONFORMANT);
			}
		}
	}
	
	// Util
	
	private  RDFTerm getOther(Triple t, RDFTerm n){
		if (t.getObject().equals(n))
			return t.getSubject();
		else
			return t.getObject();
	}
}
