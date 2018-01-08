/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.schema.analysis;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.DirectedWeightedGraphBuilder;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.AbstractASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
import fr.univLille.cristal.shex.util.Pair;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SchemaRulesStaticAnalysis {
	
	private static SchemaRulesStaticAnalysis staticInstance = new SchemaRulesStaticAnalysis();
	public static SchemaRulesStaticAnalysis getInstance () {
		return staticInstance;
	}
	
	// -------------------------------------------------------------------------
	// COMMON
	// -------------------------------------------------------------------------
	
	public static Set<ShapeExprLabel> computeUndefinedShapeLabels (Map<ShapeExprLabel, ShapeExpr> rules) {
		Set<ShapeExprLabel> definedLabels = rules.keySet();
		Set<ShapeExprLabel> referencedLabels = collectAllShapeRefs(rules).stream().map(ref -> ref.getLabel()).collect(Collectors.toSet()); 
		if (definedLabels.containsAll(referencedLabels))
			return Collections.emptySet();
		
		referencedLabels.removeAll(definedLabels);
		return referencedLabels;
	}
	
	
	
	/** The dependency is defined as a shape label that directly references another shape label through a {@link ShapeExprRef}, without {@link Shape}
	 * 
	 * @param rules
	 * @return
	 */
	public static List<List<ShapeExprLabel>> computeCyclicShapeRefDependencies (Map<ShapeExprLabel, ShapeExpr> rules) {
		
		Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>> directlyReferencedShapeLabelsCollector = new Function<ShapeExpr, List<Pair<ShapeExprLabel,Integer>>>() {
				
			@Override
			public List<Pair<ShapeExprLabel, Integer>> apply(ShapeExpr expr) {
				CollectDirectlyReferencedShapeExprLabelsVisitor visitor = staticInstance.new CollectDirectlyReferencedShapeExprLabelsVisitor();
				expr.accept(visitor);
				return visitor.getResult();
			}
		};
		DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> directShapeReferenceDependency =
			computeDependencyGraph(rules, directlyReferencedShapeLabelsCollector);
		
		TarjanSimpleCycles<ShapeExprLabel, DefaultWeightedEdge> tarjan = new TarjanSimpleCycles<>(directShapeReferenceDependency);
		return tarjan.findSimpleCycles();		
	}
	
	public static Set<ShapeExprRef> collectAllShapeRefs (Map<ShapeExprLabel, ShapeExpr> rules) { 
		Set<ShapeExprRef> set = new HashSet<>();
		CollectASTElementsS<ShapeExprRef> collector = 
				new CollectASTElementsS<>((ASTElement ast) -> ast.getClass().equals(ShapeExprRef.class), 
						                   set,
						                   true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}
	
	private static List<Pair<ShapeExprLabel, Integer>> collectDependsOnShapeLabels (ShapeExpr expr, int polarity, Map<ShapeExprLabel, ShapeExpr> rules) {
		
		CollectDependsOnShapeLabelsVisitor visitor = staticInstance.new CollectDependsOnShapeLabelsVisitor();
		expr.accept(visitor, polarity, rules);
		return visitor.getResult();
	}
	
	private static Set<ShapeExprRef> collectReferencedShapeLabels (NonRefTripleExpr texpr) {
		
		Set<ShapeExprRef> shapeRefs = new HashSet<>();
		for (TripleConstraint tc: collectTripleConstraints(texpr)) {
			shapeRefs.add((ShapeExprRef) tc.getShapeExpr());
		}
		return shapeRefs;
	}
	
	public static Set<TripleConstraint> collectTripleConstraints (NonRefTripleExpr expr) {
		Set<TripleConstraint> set = new HashSet<>();
		CollectASTElementsT<TripleConstraint> collector = 
				new CollectASTElementsT<>((ASTElement ast) -> ast.getClass().equals(TripleConstraint.class), 
										  set,
										  false);

		expr.accept(collector);
		return set;
	}
	
	public static Set<Shape> collectShapes (ShapeExpr expr) {
		Set<Shape> set = new HashSet<>();
		CollectASTElementsS<Shape> collector = 
				new CollectASTElementsS<Shape>((ASTElement ast) -> ast.getClass().equals(Shape.class), 
						                       set,
						                       false);
		expr.accept(collector);
		return set;
	}
	
	class CollectShapeRefsVisitor extends ShapeExpressionVisitor<Set<ShapeExprRef>> {

		private HashSet<ShapeExprRef> theSet = new HashSet<>();
		
		@Override
		public Set<ShapeExprRef> getResult() {
			return Collections.unmodifiableSet(theSet);
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			theSet.addAll(collectReferencedShapeLabels(expr.getTripleExpression()));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			theSet.add(shapeRef);
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}
		
	}
	
	
	/** Collects the neighbourhood constraints together with their polarity (positive or negative).
	 * 
	 * @author Iovka Boneva
	 * @author Antonin Durey
	 *
	 */
	class CollectDependsOnShapeLabelsVisitor extends ShapeExpressionVisitor<List<Pair<ShapeExprLabel, Integer>>> {
		
		private ArrayList<Pair<ShapeExprLabel, Integer>> theList = new ArrayList<>();
	
		@Override
		public List<Pair<ShapeExprLabel, Integer>> getResult() {
			return theList;
		}

		@Override
		public void visitShape(Shape nc, Object... arguments) {
			int polarity = (Integer) arguments[0];
			for (ShapeExprRef ref : collectReferencedShapeLabels(nc.getTripleExpression())) 
				theList.add(new Pair<>(ref.getLabel(), polarity));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			int polarity = (Integer) arguments[0];
			Object[] newArguments = new Object[2];
			newArguments[0] = polarity * (-1);
			newArguments[1] = arguments[1];
			expr.getSubExpression().accept(this, newArguments);
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			int polarity = (Integer) arguments[0];
			theList.add(new Pair<>(shapeRef.getLabel(), polarity));
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
		
	class CollectDirectlyReferencedShapeExprLabelsVisitor extends ShapeExpressionVisitor<List<Pair<ShapeExprLabel,Integer>>> {

		private List<Pair<ShapeExprLabel,Integer>> result = new ArrayList<>();
		
		@Override
		public List<Pair<ShapeExprLabel,Integer>> getResult() { return result; }

		@Override
		public void visitShape(Shape expr, Object... arguments) {}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			result.add(new Pair<>(shapeRef.getLabel(), +1));
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}
	
	
	// -------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------
	
	/** Computes a stratification, or null if the set of rules is not stratified. */ 
	public static List<Set<ShapeExprLabel>> computeStratification (Map<ShapeExprLabel, ShapeExpr> rules) {
		DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph = computeDependencyGraph(rules);

		if(! checkIsStratified(depGraph))
			return null; 

		List<Set<ShapeExprLabel>> strats = getStronglyConnectedComponents(depGraph);
		List<Set<ShapeExprLabel>> orderedStrats = constructTopologicalOrder(depGraph, strats);
		return orderedStrats;
	}
	
	protected static DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> computeDependencyGraph 
		(Map<ShapeExprLabel, ShapeExpr> rules) {
		
		Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>> dependsOnShapeLabelsCollector = new Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>>() {
			
			@Override
			public List<Pair<ShapeExprLabel, Integer>> apply(ShapeExpr e) {
				return collectDependsOnShapeLabels(e, 1, rules);
			}
		};
		return computeDependencyGraph(rules, dependsOnShapeLabelsCollector);
	}
	
	private static DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> computeDependencyGraph 
		(Map<ShapeExprLabel, ShapeExpr> rules,
		Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>> dependencyCollector) {
		
		DirectedWeightedGraphBuilder<ShapeExprLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge>> builder = 
				new DirectedWeightedGraphBuilder<ShapeExprLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeExprLabel,DefaultWeightedEdge>>(new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class));


		for (ShapeExprLabel label : rules.keySet()) {
			// FIXME ensure that ShapeExprLabel has the appropriate equals method
			builder.addVertex(label);
		}

		for (Map.Entry<ShapeExprLabel, ShapeExpr> def : rules.entrySet()) {
			ShapeExprLabel label = def.getKey();
			ShapeExpr expr = def.getValue();
			
			for (Pair<ShapeExprLabel, Integer> pair : dependencyCollector.apply(expr))
				builder.addEdge(label, pair.one, pair.two);
		}
		return builder.build();
	}
	
	private static boolean checkIsStratified (DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph) {
		TarjanSimpleCycles<ShapeExprLabel, DefaultWeightedEdge> tarjan = new TarjanSimpleCycles<>(depGraph);
		
		// get all cycles
		List<List<ShapeExprLabel>> cycles = tarjan.findSimpleCycles();
		
		// check if in one cycle, there is an edge with a negative value
		for(List<ShapeExprLabel> cycle : cycles){
			
			int cycleSize = cycle.size();
			for (int i = 0; i < cycleSize; i++) {
				ShapeExprLabel src = cycle.get(i);
				ShapeExprLabel tgt = cycle.get((i+1) % cycleSize);
				if (hasNegativeEdge(depGraph, src,tgt))
					return false;
			}
		}
		return true;
	}
	
	private static boolean hasNegativeEdge(DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> graph, ShapeExprLabel src, ShapeExprLabel tgt){
			
		// check all edges between 2 nodes
		for(DefaultWeightedEdge edge : graph.getAllEdges(src, tgt)){
			
			if (graph.getEdgeWeight(edge) < 0) {
				return true;
			}
		}
		return false;		
	}
	
	private static List<Set<ShapeExprLabel>> getStronglyConnectedComponents(DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph){
		KosarajuStrongConnectivityInspector<ShapeExprLabel, DefaultWeightedEdge> kscInspector = new KosarajuStrongConnectivityInspector<>(depGraph); 
		return kscInspector.stronglyConnectedSets();
	}
	
	private static List<Set<ShapeExprLabel>> constructTopologicalOrder(DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph,
			List<Set<ShapeExprLabel>> strats){
		DirectedAcyclicGraph<String, DefaultWeightedEdge> acyclicGraph =
				new DirectedAcyclicGraph<String, DefaultWeightedEdge>(new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(DefaultWeightedEdge.class));

		Map<String, Set<ShapeExprLabel>> map = new HashMap<>();
		// Creating a map to have a link between a node into the strongly connected graph and the nodes forming the strongly connected parts
		for(int i=0;i<strats.size();i++){
			String nodeName = i + "";
			map.put(nodeName, strats.get(i));
			acyclicGraph.addVertex(nodeName);
		}


		addAllEdgesIntoStronglyConnectedGraph(strats, acyclicGraph, map, depGraph);

		return getTopologicalOrderFromNonCyclicGraph(acyclicGraph, map);
	}

	private static void addAllEdgesIntoStronglyConnectedGraph(List<Set<ShapeExprLabel>> strats, DirectedAcyclicGraph<String,
			DefaultWeightedEdge> acyclicGraph, Map<String, Set<ShapeExprLabel>> map,
			DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph){
		for(String newNode : map.keySet()){
			Set<ShapeExprLabel> set = map.get(newNode);

			for(String otherNode : map.keySet()){
				if(!newNode.equals(otherNode)){
					Set<ShapeExprLabel> otherSet = map.get(otherNode);

					for(ShapeExprLabel label : set){
						for(ShapeExprLabel otherLabel : otherSet){
							// If there is a edge between these two vertex, and there is not yet an edge between the two concerned vertex into the acyclic graph
							// then add an edge into the two concerned vertex in the directed acyclic graph
							if(depGraph.containsEdge(label, otherLabel) && !acyclicGraph.containsEdge(newNode, otherNode))
								acyclicGraph.addEdge(newNode, otherNode);
						}
					}
				}
			}
		}
	}

	private static List<Set<ShapeExprLabel>> getTopologicalOrderFromNonCyclicGraph(DirectedAcyclicGraph<String, DefaultWeightedEdge> graph, Map<String, Set<ShapeExprLabel>> map){

		Iterator<String> it = graph.iterator();

		List<Set<ShapeExprLabel>> stratsOrdered = new ArrayList<>();
		while(it.hasNext()){
			String newNode = it.next();

			stratsOrdered.add(0, map.get(newNode));
		}
		return stratsOrdered;
	}
	
	
	// -------------------------------------------------------------------------
	// DNF
	// -------------------------------------------------------------------------
	
	public static ShapeOr computeDNF (ShapeExpr expr) {

		PushNegationsDownVisitor pushNegationsVisitor = staticInstance.new PushNegationsDownVisitor();
		expr.accept(pushNegationsVisitor, false);
		ShapeExpr negationsOnLeavesExpr = pushNegationsVisitor.getResult();
		
		FlattenAndOr flattenVisitor = staticInstance.new FlattenAndOr();
		negationsOnLeavesExpr.accept(flattenVisitor, new Object());
		ShapeExpr flattenedExpr = flattenVisitor.getResult(); 
		
		CreateDNFVisitor createDNFVisitor = staticInstance.new CreateDNFVisitor();
		flattenedExpr.accept(createDNFVisitor);
		ShapeExpr preDNF = createDNFVisitor.getResult();
		
		// Flatten the result
		flattenVisitor = staticInstance.new FlattenAndOr();
		preDNF.accept(flattenVisitor, new Object());
		ShapeExpr flatDNF = flattenVisitor.getResult();
		
		if (! (flatDNF instanceof ShapeOr))
			return new ShapeOr(Collections.singletonList(flatDNF));
		else return (ShapeOr) flatDNF;
	}
	
	
	public class PushNegationsDownVisitor extends ShapeExpressionVisitor<ShapeExpr>{

		private Deque<ShapeExpr> stack = new ArrayDeque<>(); 
		
		@Override
		public ShapeExpr getResult() {
			return stack.pop();
		}

		@Override
		public void visitShape(Shape nc, Object... arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpr result;
			if (isNegated) result = new ShapeNot(nc);
			else result = nc;

			stack.push(result);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint nc, Object... arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpr result;
			if (isNegated) result = new ShapeNot(nc);
			else result = nc;

			stack.push(result);
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpr result;
			if (isNegated) result = new ShapeNot(shapeRef);
			else result = shapeRef;
			
			stack.push(result);
		}
				
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			super.visitShapeAnd(expr, arguments);
			List<ShapeExpr> subExpressions = new ArrayList<>();
			for (int i = 0; i < expr.getSubExpressions().size(); i++) {
				subExpressions.add(stack.pop());
			}
			
			boolean isNegated = (Boolean) arguments[0];
			ShapeExpr result;
			if (isNegated) result = new ShapeOr(subExpressions);
			else result = new ShapeAnd(subExpressions);
			
			stack.push(result);
		}

		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			super.visitShapeOr(expr, arguments);
			List<ShapeExpr> subExpressions = new ArrayList<>();
			for (int i = 0; i < expr.getSubExpressions().size(); i++) {
				subExpressions.add(stack.pop());
			}
			
			boolean isNegated = (Boolean) arguments[0];
			ShapeExpr result;
			if (isNegated) result = new ShapeAnd(subExpressions);
			else result = new ShapeOr(subExpressions);
			
			stack.push(result);
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			super.visitShapeNot(expr, ! (Boolean)arguments[0]);
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}

	
	
	protected class FlattenAndOr extends ShapeExpressionVisitor<ShapeExpr> {
		
		private Deque<ShapeExpr> stack = new ArrayDeque<>();
		private final Object AND = new Object();
		private final Object OR = new Object();
		
		
		@Override
		public ShapeExpr getResult() {
			return stack.pop();
		}

		@Override
		public void visitShape(Shape nc, Object... arguments) {
			stack.push(nc);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint nc, Object... arguments) {
			stack.push(nc);
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			stack.push(shapeRef);
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			ShapeExpr subExpr = expr.getSubExpression();
			if (! (subExpr instanceof Shape || subExpr instanceof NodeConstraint))
				throw new IllegalArgumentException("Negation accepted only on the leaves");
			stack.push(expr);	
		}
		
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			if (arguments[0].equals(AND)) {
				for (ShapeExpr e : expr.getSubExpressions()) {
					e.accept(this, AND);
				}
			} 
			else { // the father is not an AND
				List<ShapeExpr> subExpressions = new ArrayList<>();
				for (ShapeExpr e : expr.getSubExpressions()) {
					int stackSizeBefore = stack.size();
					e.accept(this, AND);
					int numberChildren = stack.size() - stackSizeBefore;
					for (int i = 0; i < numberChildren; i++) {
						subExpressions.add(stack.pop());
					}
				}
				stack.push(new ShapeAnd(subExpressions));	
			}
		}

		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			if (arguments[0].equals(OR)) {
				for (ShapeExpr e : expr.getSubExpressions()) {
					e.accept(this, OR);
				}
			} 
			else { // the father is not an OR
				List<ShapeExpr> subExpressions = new ArrayList<>();
				for (ShapeExpr e : expr.getSubExpressions()) {
					int stackSizeBefore = stack.size();
					e.accept(this, OR);
					int numberChildren = stack.size() - stackSizeBefore;
					for (int i = 0; i < numberChildren; i++) {
						subExpressions.add(stack.pop());
					}
				}
				stack.push(new ShapeOr(subExpressions));	
			}
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}		
		
	}
	
	/** To be applied only on expressions in which negation has been pushed down to the leaves. */
	protected class CreateDNFVisitor extends ShapeExpressionVisitor<ShapeExpr> {
		
		private Deque<ShapeExpr> stack = new ArrayDeque<>(); 
		
		@Override
		public ShapeExpr getResult() {
			return stack.pop();
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			stack.push(expr);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			stack.push(expr);
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			stack.push(shapeRef);
		}
		
		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			ShapeExpr subExpr = expr.getSubExpression();
			if (! (subExpr instanceof Shape || subExpr instanceof NodeConstraint))
				throw new IllegalArgumentException("Negation accepted only on the leaves");
			stack.push(expr);		
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			List<ShapeExpr> newSubExpressions = new ArrayList<>();
			for (ShapeExpr e : expr.getSubExpressions()) {
				e.accept(this);
				newSubExpressions.add(stack.pop());
			}
			FlattenAndOr flattenVisitor = new FlattenAndOr();
			new ShapeOr(newSubExpressions).accept(flattenVisitor);
			stack.push(flattenVisitor.getResult());
		}

		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			int stackSizeBefore = stack.size();
			List<ShapeExpr> subExpressions = new ArrayList<>(expr.getSubExpressions());
			
			ShapeOr orSubExpression = getRemoveOrSubExpression(subExpressions);
			if (orSubExpression != null) {
				for (ShapeExpr e : orSubExpression.getSubExpressions()) {
					ShapeExpr andExpr = newFlattenedAndShapeExpression(subExpressions, e);
					andExpr.accept(this);
				}
			
				int numberChildren = stack.size() - stackSizeBefore;
				List<ShapeExpr> newOrSubexpressions = new ArrayList<>();
				for (int i = 0; i < numberChildren; i++) {
					newOrSubexpressions.add(stack.pop());
				}
				ShapeOr newOr = new ShapeOr(newOrSubexpressions);
				stack.push(newOr);
			}
			else
				// Correct because the expression is flattened
				stack.push(expr);
		}

		/** Removes and returns an or sub-expression in the list, if any.
		 * Returns null if none such or sub-expression.
		 * @param subExpressions
		 * @return
		 */
		private ShapeOr getRemoveOrSubExpression(List<ShapeExpr> subExpressions) {
			Iterator<ShapeExpr> it = subExpressions.iterator();
			while (it.hasNext()) {
				ShapeExpr e = it.next();
				if (e instanceof ShapeOr) {
					it.remove();
					return (ShapeOr) e;
				}
			}
			return null;
		}

		private ShapeExpr newFlattenedAndShapeExpression (List<ShapeExpr> subExpressions, ShapeExpr additionalSubExpr) {
			List<ShapeExpr> list = new ArrayList<>(subExpressions);
			list.add(additionalSubExpr);
			FlattenAndOr flattenVisitor = new FlattenAndOr();
			new ShapeAnd(list).accept(flattenVisitor, new Object());
			return flattenVisitor.getResult();
		}

		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented.");
		}
		
	}
	
	
	protected static String depGraphToString (DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge> depGraph) {
		StringBuilder s = new StringBuilder();
		s.append("(");
		s.append(depGraph.vertexSet());
		s.append(",");
		s.append("[");
		for (DefaultWeightedEdge edge: depGraph.edgeSet()) {
			String dep = depGraph.getEdgeWeight(edge) < 0 ? "--" : "++";
			s.append(String.format("(%s%s%s) ", depGraph.getEdgeSource(edge), dep, depGraph.getEdgeTarget(edge)));
		}
		s.append("]");
		s.append(")");
		return s.toString();
	}
	
	// ------------------------------------------------------------------------------------
	// UNFOLDING
	// ------------------------------------------------------------------------------------
	public static NonRefTripleExpr computeUnfoldedRepetitions (NonRefTripleExpr expr) {
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		return visitor.getResult();
	}
	
}
