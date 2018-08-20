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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.rdf.api.Graph;
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
import fr.inria.lille.shexjava.util.Pair;


/** Implements the Recursive validation algorithm with memorization of the result by recursive call if they are correct.
 * This algorithm will check only the shape definition necessary.
 * 
 * @author Jérémie Dusart 
 */
public class RecursiveValidationWithMemorization extends SORBEBasedValidation {
	
	public RecursiveValidationWithMemorization(ShexSchema schema, Graph graph) {
		super(schema,graph);
		this.resetTyping();

	}
	

	private TypingForValidation typing;

	@Override
	public Typing getTyping() {
		return typing;
	}

	@Override
	public void resetTyping() {
		this.typing = new TypingForValidation();
	}

	
	
	
	
	@Override
	public boolean validate(RDFTerm focusNode, Label label) {
		if (label == null || !schema.getShapeExprsMap().containsKey(label))
			throw new IllegalArgumentException("Unknown label: "+label);
		return recursiveValidation(focusNode,
								 label,
								 new LinkedList<>(),
								 new DefaultDirectedGraph<>(DefaultEdge.class),
								 new HashMap<>(),
								 new HashMap<>());		
		
	}

	
	protected boolean recursiveValidation(RDFTerm focusNode, 
										  Label label, 
										  LinkedList<Pair<RDFTerm,Label>> hyp,
										  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
										  Map<Pair<RDFTerm,Label>,Boolean> results,
										  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		Pair<RDFTerm,Label> key = new Pair<>(focusNode,label);
		if (hyp.contains(key)) {
			return true;
		}
		if (!this.typing.getStatus(focusNode, label).equals(Status.NOTCOMPUTED))
			return this.typing.isConformant(focusNode, label);
		if (g.containsVertex(key))
			return results.get(key);
		
		if (schema.getShapeExprsMap().get(label) instanceof NodeConstraint) {
			boolean res = ((NodeConstraint)schema.getShapeExprsMap().get(label)).contains(focusNode);
			updateGraph(focusNode, label, Collections.emptySet(), res, hyp, g, results, lowestDep);		
			return res;		
		}
		
		hyp.addLast(key);
		
		boolean res=false;
		if (schema.getShapeExprsMap().get(label) instanceof ShapeNot)
			res=recursiveValidationShapeNot(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeExprRef)
			res=recursiveValidationShapeExprRef(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeOr)
			res=recursiveValidationShapeOr(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeAnd)
			res=recursiveValidationShapeAnd(focusNode,label,hyp,g,results,lowestDep);
		if (schema.getShapeExprsMap().get(label) instanceof Shape)
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
		
		ShapeNot shape = (ShapeNot) schema.getShapeExprsMap().get(label);
		
		boolean res = ! this.recursiveValidation(focusNode, shape.getSubExpression().getId(),hyp,g,results,lowestDep);
				
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getSubExpression().getId()));
				
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;
	}
	
	
	
	protected boolean recursiveValidationShapeExprRef(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeExprRef shape = (ShapeExprRef) schema.getShapeExprsMap().get(label);
		
		boolean res = this.recursiveValidation(focusNode, shape.getLabel(),hyp,g,results,lowestDep);
		
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getLabel()));
				
		updateGraph(focusNode, label, required, res, hyp, g, results, lowestDep);			
		
		return res;
	}
	
	protected boolean recursiveValidationShapeAnd(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Boolean> results,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestDep) {
		
		ShapeAnd shape = (ShapeAnd) schema.getShapeExprsMap().get(label);
		boolean res = true;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, results, lowestDep);
			if (res) {
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required = new HashSet<>();
				required.add(new Pair<>(focusNode,next.getId()));
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
		
		ShapeOr shape = (ShapeOr) schema.getShapeExprsMap().get(label);
		boolean res = false;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(!res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, results, lowestDep);
			if (res) {
				required = new HashSet<>();
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required.add(new Pair<>(focusNode,next.getId()));
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
		
		Shape shape = (Shape) schema.getShapeExprsMap().get(label);
		
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);		
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());
		
		// Match using only predicate and recursive test.
		TypingForValidation localTyping = new TypingForValidation();
		Matcher matcher = ValidationUtils.getPredicateOnlyMatcher();
		Map<Triple,List<TripleConstraint>> matchingTC1 = 
				ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties2(), matcher).getPreMatching();

		for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {			
			int nb=0;
			for (TripleConstraint tc:entry.getValue()) {
				RDFTerm destNode = entry.getKey().getObject();

				if (!tc.getProperty().isForward())
					destNode = entry.getKey().getSubject();
	
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED)) {
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId(),hyp,g,results,lowestDep)) {
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.CONFORMANT);
						nb++;
					} else {
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.NONCONFORMANT);
					}
				} else {
					localTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));
					if (typing.isConformant(destNode, tc.getShapeExpr().getId()))
						nb++;
				}
			}
			
			// TODO le bug de test0Cardinaliy vient d'ici
			if (nb==0) {
				boolean success = false;
				for (TCProperty extra : shape.getExtraProperties())
					if (extra.getIri().equals(entry.getKey().getPredicate()))
						success = true;
				if (!success) {
					// Looking at the calls that fails
					for (TripleConstraint tc:matchingTC1.get(entry.getKey())){
						RDFTerm destNode = entry.getKey().getObject();
						required.add(new Pair<>(destNode,tc.getShapeExpr().getId()));
					}
					updateGraph(node, label, required, false, hyp, g, results, lowestDep);
					return false;
				}
			}

		}
		
		Map<Triple, Label> result = this.findMatching(node, shape, localTyping).getMatching();
		if (result!=null) {
			for (Triple tr:result.keySet()) {
				Label depLabel = ((TripleConstraint) schema.getTripleExprsMap().get(result.get(tr))).getShapeExpr().getId();
				required.add(new Pair<>(getOther(tr,node), depLabel));
			}
			for (Triple tri:extraNeighbours) {
				RDFTerm other = getOther(tri,node);
				for (Label l:localTyping.getShapesLabel(other))
					required.add(new Pair<>(other, l));
			}
			updateGraph(node, label, required, true, hyp, g, results, lowestDep);		

			return true;
		}
		
		// Update the graph if necessary
		for (Pair<RDFTerm,Label> pair:localTyping.getStatusMap().keySet())
			if (localTyping.isNonConformant(pair.one,pair.two))
				required.add(pair);		
		updateGraph(node, label, required, false, hyp, g, results, lowestDep);		
				
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
			while (! S.isEmpty()) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				if (this.typing.getStatus(key.one, key.two).equals(Status.NOTCOMPUTED) &&
						!hyp.contains(lowestDep.get(key))) {
					if (results.get(key))
						this.typing.setStatus(key.one, key.two, Status.CONFORMANT);
					else
						this.typing.setStatus(key.one, key.two, Status.NONCONFORMANT);
				}
				for(DefaultEdge edge: g.incomingEdgesOf(key)) {
					Pair<RDFTerm,Label> dest = g.getEdgeSource(edge);
					if (this.typing.getStatus(dest.one, dest.two).equals(Status.NOTCOMPUTED))
						S.add(dest);
				}
			}
		} else {
			while (S.isEmpty()) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				for(DefaultEdge edge: g.incomingEdgesOf(key))
					S.add(g.getEdgeSource(edge));	
				if (!key.equals(new Pair<>(focusNode,label))) {
					g.removeVertex(key);
					notifyMatchingFound(key.one, key.two, null);
					for (FailureAnalyzer fr:frcs)
						fr.removeReport(key.one, key.two);
				}
			}
			if (g.outDegreeOf(new Pair<>(focusNode,label))==0) {
				g.removeVertex(new Pair<>(focusNode,label));
				notifyMatchingFound(focusNode, label, null);
				for (FailureAnalyzer fr:frcs)
					fr.removeReport(focusNode,label);
			}
		}
	}
	
	
	
	// Update the graph of dependencies
	
	protected void updateGraph(RDFTerm focusNode,
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
			if (this.typing.getStatus(key2.one, key2.two).equals(Status.NOTCOMPUTED)) {
				if (!g.containsVertex(key1))
					g.addVertex(key1);
				if (!g.containsVertex(key2))
					g.addVertex(key2);
				results.put(key2, res);
				g.addEdge(key1, key2);
				if (!lowestDep.containsKey(key1)) {	
					if (lowestDep.containsKey(key2)) 
						lowestDep.put(key1, lowestDep.get(key2));
					else //key2 must be in hyp since not in typing and not in g
						lowestDep.put(key1, key2);
				} else {
					if (lowestDep.containsKey(key2)) {
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(lowestDep.get(key2))) 
							lowestDep.put(key1, lowestDep.get(key2));						
					} else { //key2 must be in hyp since not in typing and not in g
						if (hyp.indexOf(lowestDep.get(key1))>hyp.indexOf(key2)) 
							lowestDep.put(key1, key2);
					}
				}
				canSave = false;
			}
		}
		if (canSave) 
			if (res) 
				this.typing.setStatus(focusNode, label, Status.CONFORMANT);
			else 
				this.typing.setStatus(focusNode, label, Status.NONCONFORMANT);
	}
	
	// Util
	
	private  RDFTerm getOther(Triple t, RDFTerm n){
		if (t.getObject().equals(n))
			return t.getSubject();
		else
			return t.getObject();
	}

}
