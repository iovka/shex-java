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
import java.util.stream.Collectors;

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
		if (focusNode==null || label==null)
			throw new IllegalArgumentException("Invalid argument value: focusNode or label cannot be null.");
		if (!schema.getShapeExprsMap().containsKey(label))
			throw new IllegalArgumentException("Unknown label: "+label);
		this.getController().notifyComputationStart();
		if (!this.getController().shouldContinue())
			return false;
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
										  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
										  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {

		if (!this.typing.getStatus(focusNode, label).equals(Status.NOTCOMPUTED))
			return this.typing.isConformant(focusNode, label);
		Pair<RDFTerm,Label> key = new Pair<>(focusNode,label);
		if (hyp.contains(key))
			return true;
		if (g.containsVertex(key))
			return unsavedResults.get(key).equals(Status.CONFORMANT);
		
		if (schema.getShapeExprsMap().get(label) instanceof NodeConstraint) {
			boolean res = ((NodeConstraint)schema.getShapeExprsMap().get(label)).contains(focusNode);
			updateGraph(focusNode, label, res?Status.CONFORMANT:Status.NONCONFORMANT, 
						Collections.emptySet(), hyp, g, unsavedResults, lowestReqHyp);		
			return res;		
		}
		
		hyp.addLast(key);
		
		boolean res=false;
		if (schema.getShapeExprsMap().get(label) instanceof ShapeNot)
			res=recursiveValidationShapeNot(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeExprRef)
			res=recursiveValidationShapeExprRef(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeOr)
			res=recursiveValidationShapeOr(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeAnd)
			res=recursiveValidationShapeAnd(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
		if (schema.getShapeExprsMap().get(label) instanceof Shape)
			res=recursiveValidationShape(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
				
		hyp.remove(key);
		if (g.containsVertex(key)) {
			unsavedResults.put(key, res?Status.CONFORMANT:Status.NONCONFORMANT);
			memorize(focusNode,label,hyp,g,unsavedResults,lowestReqHyp);
		}
		return res;
	}
	
	
	protected boolean recursiveValidationShapeNot(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		ShapeNot shape = (ShapeNot) schema.getShapeExprsMap().get(label);
		
		boolean res = ! this.recursiveValidation(focusNode, shape.getSubExpression().getId(),hyp,g,unsavedResults,lowestReqHyp);
				
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getSubExpression().getId()));
				
		updateGraph(focusNode, label, getStatus(res), required, hyp, g, unsavedResults, lowestReqHyp);			
		
		return res;
	}
	
	
	
	protected boolean recursiveValidationShapeExprRef(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		ShapeExprRef shape = (ShapeExprRef) schema.getShapeExprsMap().get(label);
		
		boolean res = this.recursiveValidation(focusNode, shape.getLabel(),hyp,g,unsavedResults,lowestReqHyp);
		
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getLabel()));
				
		updateGraph(focusNode, label, getStatus(res), required, hyp, g, unsavedResults, lowestReqHyp);			
		
		return res;
	}
	
	protected boolean recursiveValidationShapeAnd(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		ShapeAnd shape = (ShapeAnd) schema.getShapeExprsMap().get(label);
		boolean res = true;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, unsavedResults, lowestReqHyp);
			if (res) {
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required.clear();
				required.add(new Pair<>(focusNode,next.getId()));
			}
		}
		
		updateGraph(focusNode, label, getStatus(res), required, hyp, g, unsavedResults, lowestReqHyp);			
		
		return res;	
	}
	
	protected boolean recursiveValidationShapeOr(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		ShapeOr shape = (ShapeOr) schema.getShapeExprsMap().get(label);
		boolean res = false;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(!res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId(), hyp, g, unsavedResults, lowestReqHyp);
			if (res) {
				required.clear();
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required.add(new Pair<>(focusNode,next.getId()));
			}
		}
				
		updateGraph(focusNode, label, getStatus(res), 
					required, hyp, g, unsavedResults, lowestReqHyp);			
		
		return res;	
	}
	
	
	private boolean recursiveValidationShape (RDFTerm node,
			Label label, 
			LinkedList<Pair<RDFTerm,Label>> hyp,
			DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp){

		List<Triple> extraNeighbours = new ArrayList<>();
		
		Shape shape = (Shape) schema.getShapeExprsMap().get(label);
		
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);		
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());
		
		// Match using only predicate 
		TypingForValidation localTyping = new TypingForValidation();
		Matcher matcher = ValidationUtils.getPredicateOnlyMatcher();
		Map<Triple,List<TripleConstraint>> matchingTC1 = 
				ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(), matcher).getPreMatching();

		// Do the recursive call
		for(Triple curTr:matchingTC1.keySet()) {	
			List<TripleConstraint> curLTCs = matchingTC1.get(curTr);
			
			boolean tripleCanBeMatched=false;
			RDFTerm destNode=curLTCs.size()>0&&curLTCs.get(0).getProperty().isForward()? curTr.getObject():curTr.getSubject();

			for (TripleConstraint tc:curLTCs) {
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED)) {
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId(),hyp,g,unsavedResults,lowestReqHyp)) 
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.CONFORMANT);
					else 
						localTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.NONCONFORMANT);
				} else 
					localTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));

				tripleCanBeMatched = tripleCanBeMatched || localTyping.isConformant(destNode, tc.getShapeExpr().getId());
			}

			if (!(tripleCanBeMatched || shape.getExtraProperties().contains(curTr.getPredicate()))) {
					// Looking at the calls that fails
					Set<Pair<RDFTerm,Label>> required = new HashSet<>();
					matchingTC1.get(curTr).stream().forEach(tc->required.add(new Pair<>(destNode,tc.getShapeExpr().getId())));
					updateGraph(node, label, Status.NONCONFORMANT, required, hyp, g, unsavedResults, lowestReqHyp);
					return false;
			}
		}

		Map<Triple, Label> result = this.findMatching(node, shape, localTyping).getMatching();
		// a matching has been found
		if (result!=null) {
			Set<Pair<RDFTerm,Label>> required = new HashSet<>();
			// add in the requirement the matching
			for (Triple tr:result.keySet()) {
				Label shapeLab = ((TripleConstraint) schema.getTripleExprsMap().get(result.get(tr))).getShapeExpr().getId();
				required.add(new Pair<>(getOther(tr,node), shapeLab ));
			}
			// add the triple that were matched to extra
			for (Triple tri:extraNeighbours)
				for (Label l:localTyping.getShapesLabel(getOther(tri,node)))
					required.add(new Pair<>(getOther(tri,node), l));
			updateGraph(node, label, Status.CONFORMANT, required, hyp, g, unsavedResults, lowestReqHyp);		

			return true;
		}
		
		// No matching has been found
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		localTyping.getStatusMap().keySet().stream().filter(pair -> localTyping.isNonConformant(pair.one,pair.two))
							      .forEach(req -> required.add(req));
		updateGraph(node, label, Status.NONCONFORMANT, required, hyp, g, unsavedResults, lowestReqHyp);
		return false;
	}
	
	

	// for updating the graph
	
	protected void memorize(RDFTerm focusNode, 
			  Label label, 
			  LinkedList<Pair<RDFTerm,Label>> hyp,
			  DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			  Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			  Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		LinkedList<Pair<RDFTerm,Label>> S = new LinkedList<>();
		Pair<RDFTerm,Label> baseKey = new Pair<>(focusNode,label);
		S.add(baseKey);
		
		if (unsavedResults.get(new Pair<>(focusNode,label)).equals(Status.CONFORMANT)) {
			//memorize is call on a vertex that was in hyp and so the status has been assumed conformant ann
			// we are in the case where the computed status and the hyp are in agreement.
			while (! S.isEmpty()) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				if (isNotComputed(key) && !hyp.contains(lowestReqHyp.get(key)))
					// the key is ready to be saved
					this.typing.setStatus(key.one, key.two, unsavedResults.get(key));
				if (!isNotComputed(key))
					// the key has been saved and we are now go through all the other vertex that was dependent of it
					g.incomingEdgesOf(key).stream().map(edge -> g.getEdgeSource(edge))
							.filter(dest -> isNotComputed(dest))
							.forEach(dest -> S.add(dest));
			}
		} else {
			// The result is in disagreement with the hypothesis. 
			// So we removed all vertex that were assuming it to be true and save nothing
			while (! S.isEmpty()) {
				Pair<RDFTerm,Label> key = S.pollFirst();
				g.incomingEdgesOf(key).forEach(edge -> S.add(g.getEdgeSource(edge)));
				if (!key.equals(baseKey)) {
					// I want to remove the baseKey vertex last to prevent some problem in the structure of the graph.
					g.removeVertex(key);
					notifyMatchingFound(key.one, key.two, null);
				}
			}
			g.removeVertex(baseKey);
			notifyMatchingFound(focusNode, label, null);
			
		}
	}
	
	
	
	// Update the graph of dependencies
	
	protected void updateGraph(RDFTerm focusNode,
			Label label,
			Status valRes,
			Set<Pair<RDFTerm,Label>> required,
			LinkedList<Pair<RDFTerm,Label>> hyp,
			DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g,
			Map<Pair<RDFTerm,Label>,Status> unsavedResults,
			Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp) {
		
		Pair<RDFTerm,Label> baseKey = new Pair<>(focusNode,label);

		boolean canSave = true;
		for(Pair<RDFTerm,Label> depKey:required) {
			if (this.typing.getStatus(depKey.one, depKey.two).equals(Status.NOTCOMPUTED)) {
				canSave = false;

				if (!g.containsVertex(baseKey)) g.addVertex(baseKey);
				if (!g.containsVertex(depKey)) g.addVertex(depKey);
				g.addEdge(baseKey, depKey);
				
				// Since the dep has not been saved, it mean that it must have a dependence to an hyp
				// the following code initialize or update the dep for the base node
				if (!lowestReqHyp.containsKey(baseKey)) {
					// the dep have to be initialize
					if (lowestReqHyp.containsKey(depKey)) 
						lowestReqHyp.put(baseKey, lowestReqHyp.get(depKey));
					else // depKey must be in hyp since it is not in typing and not in g
						lowestReqHyp.put(baseKey, depKey);
				} else {
					// The lowestReqHyp may have to be update for the baseKey. 
					// It can happen if we have multiple required dependencies that have not been saved.
					// We want to update it to the lowest hypo in the stach hyp.
					if (lowestReqHyp.containsKey(depKey)) {
						if (hyp.indexOf(lowestReqHyp.get(baseKey))>hyp.indexOf(lowestReqHyp.get(depKey))) 
							lowestReqHyp.put(baseKey, lowestReqHyp.get(depKey));						
					} else { //depKey must be in hyp since it is not in typing and not in g
						if (hyp.indexOf(lowestReqHyp.get(baseKey))>hyp.indexOf(depKey)) 
							lowestReqHyp.put(baseKey, depKey);
					}
				}
			}
		}
		
		if (canSave) 
			this.typing.setStatus(focusNode, label, valRes);

	}
	
	// ----------------------------------------------------------
	// Utils
	// ----------------------------------------------------------
	
	private  RDFTerm getOther(Triple t, RDFTerm n){
		if (t.getObject().equals(n))
			return t.getSubject();
		else
			return t.getObject();
	}

	
	private Status getStatus(boolean res) {
		return res?Status.CONFORMANT:Status.NONCONFORMANT;
	}
	
	private boolean isNotComputed(Pair<RDFTerm,Label> key) {
		return this.typing.getStatus(key.one, key.two).equals(Status.NOTCOMPUTED);
	}
}
