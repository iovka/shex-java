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
import java.util.Set;
import java.util.stream.Collectors;

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

	protected boolean performValidation(RDFTerm focusNode, Label label) throws Exception {
		hyp = new LinkedList<>();
		g = new DefaultDirectedGraph<>(DefaultEdge.class);
		unsavedResults = new HashMap<>();
		lowestReqHyp= new HashMap<>();
		
		return recursiveValidation(focusNode,label);	
	}

	// hyp contains the stack of hypothesis perform in the recursion
	protected LinkedList<Pair<RDFTerm,Label>> hyp;
	// g contains the graph of dependencies that cannot be saved yet
	protected DefaultDirectedGraph<Pair<RDFTerm,Label>, DefaultEdge> g;
	// unsavedResults contains the result of the call that can't be put in the typing yet because they are dependent of an hypothesis
	protected Map<Pair<RDFTerm,Label>,Status> unsavedResults;
	// lowestReqHyp contains for the unsavedResults a link to the lowest hypothesis that is dependent of.
	protected Map<Pair<RDFTerm,Label>,Pair<RDFTerm,Label>> lowestReqHyp;
	

	// TODO should return a status
	protected boolean recursiveValidation(RDFTerm focusNode, Label label) throws Exception {

		if (!this.typing.getStatus(focusNode, label).equals(Status.NOTCOMPUTED))
			return this.typing.isConformant(focusNode, label);
		Pair<RDFTerm,Label> key = new Pair<>(focusNode,label);
		if (hyp.contains(key))
			return true;
		if (g.containsVertex(key))
			return unsavedResults.get(key).equals(Status.CONFORMANT);
		
		if (schema.getShapeExprsMap().get(label) instanceof NodeConstraint) {
			boolean res = ((NodeConstraint)schema.getShapeExprsMap().get(label)).contains(focusNode);
			updateGraph(focusNode, label, getStatus(res), Collections.emptySet());		
			return res;		
		}
		
		hyp.addLast(key);
		boolean res=false;
		// FIXME Should use a visitor here
		if (schema.getShapeExprsMap().get(label) instanceof ShapeNot)
			res=recursiveValidationShapeNot(focusNode,label);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeExprRef)
			res=recursiveValidationShapeExprRef(focusNode,label);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeOr)
			res=recursiveValidationShapeOr(focusNode,label);
		if (schema.getShapeExprsMap().get(label) instanceof ShapeAnd)
			res=recursiveValidationShapeAnd(focusNode,label);
		if (schema.getShapeExprsMap().get(label) instanceof Shape)
			res=recursiveValidationShape(focusNode,label);
		hyp.remove(key);
		
		if (g.containsVertex(key)) {
			unsavedResults.put(key, res ? Status.CONFORMANT : Status.NONCONFORMANT);
			memorize(focusNode,label);
		}
		return res;
	}
	
	
	protected boolean recursiveValidationShapeNot(RDFTerm focusNode, Label label) throws Exception {
		ShapeNot shape = (ShapeNot) schema.getShapeExprsMap().get(label);
		
		boolean res = ! this.recursiveValidation(focusNode, shape.getSubExpression().getId());
				
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getSubExpression().getId()));
		updateGraph(focusNode, label, getStatus(res), required);			
		
		return res;
	}
	
	
	
	protected boolean recursiveValidationShapeExprRef(RDFTerm focusNode, Label label) throws Exception {
		ShapeExprRef shape = (ShapeExprRef) schema.getShapeExprsMap().get(label);
		
		boolean res = this.recursiveValidation(focusNode, shape.getLabel());
		
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		required.add(new Pair<>(focusNode,shape.getLabel()));	
		updateGraph(focusNode, label, getStatus(res), required);			
		
		return res;
	}
	
	protected boolean recursiveValidationShapeAnd(RDFTerm focusNode, Label label) throws Exception {
		ShapeAnd shape = (ShapeAnd) schema.getShapeExprsMap().get(label);
		
		boolean res = true;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId());
			if (res) {
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required.clear();
				required.add(new Pair<>(focusNode,next.getId()));
			}
		}
		
		updateGraph(focusNode, label, getStatus(res), required);			
		
		return res;	
	}
	
	protected boolean recursiveValidationShapeOr(RDFTerm focusNode, Label label) throws Exception {
		ShapeOr shape = (ShapeOr) schema.getShapeExprsMap().get(label);
		
		boolean res = false;
		Set<Pair<RDFTerm,Label>> required = new HashSet<>();
		Iterator<ShapeExpr> iter = shape.getSubExpressions().iterator();
		
		while(!res && iter.hasNext()) {
			ShapeExpr next = iter.next();
			res = this.recursiveValidation(focusNode, next.getId());
			if (res) {
				required.clear();
				required.add(new Pair<>(focusNode,next.getId()));
			} else {
				required.add(new Pair<>(focusNode,next.getId()));
			}
		}
				
		updateGraph(focusNode, label, getStatus(res), required );			
		
		return res;	
	}
	
	
	private boolean recursiveValidationShape (RDFTerm node, Label label) throws Exception {	
		Shape shape = (Shape) schema.getShapeExprsMap().get(label);

		TypingForValidation localTyping = new TypingForValidation();
		
		Map<Triple, List<TripleConstraint>> matchingTC1 = computePreMatchingWithPredicateOnly(node, shape);
		List<Triple> extraNeighbours = new ArrayList<>();

		// the recursive call are performed in the next function and the localTyping is populated
		// the function return false if a triple cannot be math to any TC and extra
		if (!performRecursiveCallAndComputeLocalMatching(node,shape,matchingTC1,localTyping,extraNeighbours))
			// the graph is update in the function call
			return false;
		
		Map<Triple, Label> result = this.findMatching(node, shape, localTyping).getMatching();
		if (result!=null) {
			// A matching has been found
			// add in required the requirement for the matching
			Set<Pair<RDFTerm,Label>> required = result.keySet().parallelStream()
					.map(tr -> new Pair<>(getOther(tr,node), getShapeExprLabel(result.get(tr)))).collect(Collectors.toSet());
			// add the triple that were matched to extra 
			extraNeighbours.stream().forEach(tri -> localTyping.getShapesLabel(getOther(tri,node)).stream()
											 .forEach(l -> required.add(new Pair<>(getOther(tri,node),l))));
			updateGraph(node, label, Status.CONFORMANT, required);		
			return true;
		}
		
		// No matching has been found, the result can be saved only if all the failing call can be saved
		Set<Pair<RDFTerm,Label>> required = localTyping.getStatusMap().keySet().stream()
				.filter(pair -> localTyping.isNonConformant(pair.one,pair.two)).collect(Collectors.toSet());
		updateGraph(node, label, Status.NONCONFORMANT, required);
		return false;
	}
	
	
	// -------------------------------------------
	// Functions that handles the memorization
	//--------------------------------------------
	
	// memorize will do something only if you have a loop to an hypothesis
	protected void memorize(RDFTerm focusNode,  Label label) {
		
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
	
	protected void updateGraph(RDFTerm focusNode, Label label, Status valRes, Set<Pair<RDFTerm,Label>> required) {
		
		Pair<RDFTerm,Label> baseKey = new Pair<>(focusNode,label);

		boolean canSave = true;
		for(Pair<RDFTerm,Label> depKey:required) {
			if (this.typing.getStatus(depKey.one, depKey.two).equals(Status.NOTCOMPUTED)) {
				// if not in typing, then it must be in the graph
				canSave = false;

				if (!g.containsVertex(baseKey)) g.addVertex(baseKey);
				if (!g.containsVertex(depKey)) g.addVertex(depKey); // depKey is not in the graph if is in hyp
				
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
	
	private Map<Triple, List<TripleConstraint>> computePreMatchingWithPredicateOnly(RDFTerm node, Shape shape) {
		TripleExpr tripleExpression = this.sorbeGenerator.getSORBETripleExpr(shape);
		List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);		
		List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(graph, node, constraints, shape.isClosed());
		
		// Match using only predicate 
		Matcher matcher = ValidationUtils.getPredicateOnlyMatcher();
		Map<Triple,List<TripleConstraint>> matchingTC1 = 
				ValidationUtils.computePreMatching(node, neighbourhood, constraints, shape.getExtraProperties(), matcher).getPreMatching();
		return matchingTC1;
	}
	
	// Very close to the normal recursive. The difference is in the stop in the case of a triple that can't be matched.
	private boolean performRecursiveCallAndComputeLocalMatching(RDFTerm node,Shape shape,			
			Map<Triple, List<TripleConstraint>> matchingTC1,
			TypingForValidation resLocalTyping,
			List<Triple> resTripleMatchedToExtra) throws Exception {
		
		for(Triple curTr:matchingTC1.keySet()) {
			List<TripleConstraint> curLTCs = matchingTC1.get(curTr);
			
			boolean tripleCanBeMatched=false;
			RDFTerm destNode=curLTCs.size()>0&&curLTCs.get(0).getProperty().isForward()? curTr.getObject():curTr.getSubject();

			for (TripleConstraint tc:curLTCs) {
				if (this.compController != null) compController.canContinue();
				if (this.typing.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED)) {
					if (this.recursiveValidation(destNode, tc.getShapeExpr().getId())) 
						resLocalTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.CONFORMANT);
					else 
						resLocalTyping.setStatus(destNode, tc.getShapeExpr().getId(),Status.NONCONFORMANT);
				} else 
					resLocalTyping.setStatus(destNode, tc.getShapeExpr().getId(), typing.getStatus(destNode, tc.getShapeExpr().getId()));

				tripleCanBeMatched = tripleCanBeMatched || resLocalTyping.isConformant(destNode, tc.getShapeExpr().getId());
			}
			
			if (!tripleCanBeMatched) {
				if (shape.getExtraProperties().contains(curTr.getPredicate())) {
					resTripleMatchedToExtra.add(curTr);
				} else {
					// Looking at the calls that fails
					Set<Pair<RDFTerm,Label>> required = matchingTC1.get(curTr).stream()
							.map(tc->new Pair<>(destNode,tc.getShapeExpr().getId()))
							.collect(Collectors.toSet());
					updateGraph(node, shape.getId(), Status.NONCONFORMANT, required);
					return false;
				}
			}
		}
		return true;

	}
	
	
	private  RDFTerm getOther(Triple t, RDFTerm n){
		if (t.getObject().equals(n))
			return t.getSubject();
		else
			return t.getObject();
	}

	private Label getShapeExprLabel(Label tcLabel) {
		return ((TripleConstraint) schema.getTripleExprsMap().get(tcLabel)).getShapeExpr().getId();
	}
	
	
	private Status getStatus(boolean res) {
		return res?Status.CONFORMANT:Status.NONCONFORMANT;
	}
	
	private boolean isNotComputed(Pair<RDFTerm,Label> key) {
		return this.typing.getStatus(key.one, key.two).equals(Status.NOTCOMPUTED);
	}
}
