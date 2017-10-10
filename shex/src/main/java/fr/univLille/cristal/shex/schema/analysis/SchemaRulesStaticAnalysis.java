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
import java.util.Collection;
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

import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeDefinition;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
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
	
	public static Set<ShapeLabel> computeUndefinedShapeLabels (Map<ShapeLabel, ShapeDefinition> rules) {
		Set<ShapeLabel> definedLabels = rules.keySet();
		Set<ShapeRef> shapeRefs = collectAllShapeRefs(rules.values()); 
		if (definedLabels.containsAll(shapeRefs))
			return Collections.emptySet();
		
		Set<ShapeLabel> difference = new HashSet<>(shapeRefs.stream().map(ref -> ref.getLabel()).collect(Collectors.toSet()));
		difference.removeAll(definedLabels);
		return difference;
	}
	
	
	
	/** The dependency is defined as a shape label that directly references another shape label through a {@link ShapeRef}, without {@link NeighbourhoodConstraint}
	 * 
	 * @param rules
	 * @return
	 */
	public static List<List<ShapeLabel>> computeCyclicShapeRefDependencies (Map<ShapeLabel, ShapeDefinition> rules) {
		
		Function<ShapeExpression, List<Pair<ShapeLabel, Integer>>> directlyReferencedShapeLabelsCollector = new Function<ShapeExpression, List<Pair<ShapeLabel,Integer>>>() {
				
			@Override
			public List<Pair<ShapeLabel, Integer>> apply(ShapeExpression expr) {
				CollectDirectlyReferencedShapeLabelsVisitor visitor = staticInstance.new CollectDirectlyReferencedShapeLabelsVisitor();
				expr.accept(visitor);
				return visitor.getResult();
			}
		};
		DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> directShapeReferenceDependency =
			computeDependencyGraph(rules, directlyReferencedShapeLabelsCollector);
		
		TarjanSimpleCycles<ShapeLabel, DefaultWeightedEdge> tarjan = new TarjanSimpleCycles<>(directShapeReferenceDependency);
		return tarjan.findSimpleCycles();		
	}
	
	public static Set<ShapeRef> collectAllShapeRefs (Collection<ShapeDefinition> expressions) {
		Set<ShapeRef> set = new HashSet<>();
		
		for (ShapeDefinition def: expressions) {
			set.addAll(collectReferencedShapeLabels(def.expression));
		}
		return set;
	}
	
	private static Set<ShapeRef> collectReferencedShapeLabels (ShapeExpression expr) {
		CollectShapeRefsVisitor visitor = staticInstance.new CollectShapeRefsVisitor();
		expr.accept(visitor);
		return visitor.getResult();
	}
	
	
	private static List<Pair<ShapeLabel, Integer>> collectDependsOnShapeLabels (ShapeExpression expr, int polarity, Map<ShapeLabel, ShapeDefinition> rules) {
		
		CollectDependsOnShapeLabelsVisitor visitor = staticInstance.new CollectDependsOnShapeLabelsVisitor();
		expr.accept(visitor, polarity, rules);
		return visitor.getResult();
	}
	
	private static Set<ShapeRef> collectReferencedShapeLabels (TripleExpression texpr) {
		
		Set<ShapeRef> shapeRefs = new HashSet<>();
		for (TripleConstraint tc: collectTripleConstraints(texpr)) {
			shapeRefs.add(tc.getShapeRef());
		}
		return shapeRefs;
	}
	
	public static List<TripleConstraint> collectTripleConstraints (TripleExpression expr) {
		CollectTripleConstraintsVisitor visitor = new CollectTripleConstraintsVisitor();
		expr.accept(visitor);
		return visitor.getResult();
	}
	
	public static List<NeighbourhoodConstraint> collectNeighbourhoodConstraints (ShapeExpression expr) {
		CollectNeighbourhoodConstraintsVisitor visitor = new CollectNeighbourhoodConstraintsVisitor();
		expr.accept(visitor);
		return visitor.getResult();
	}
	
	class CollectShapeRefsVisitor extends ShapeExpressionVisitor<Set<ShapeRef>> {

		private HashSet<ShapeRef> theSet = new HashSet<>();
		
		@Override
		public Set<ShapeRef> getResult() {
			return Collections.unmodifiableSet(theSet);
		}

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint expr, Object... arguments) {
			theSet.addAll(collectReferencedShapeLabels(expr.getTripleExpression()));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			theSet.add(shapeRef);
		}
		
	}
	
	
	/** Collects the neighbourhood constraints together with their polarity (positive or negative).
	 * 
	 * @author Iovka Boneva
	 * @author Antonin Durey
	 *
	 */
	class CollectDependsOnShapeLabelsVisitor extends ShapeExpressionVisitor<List<Pair<ShapeLabel, Integer>>> {
		
		private ArrayList<Pair<ShapeLabel, Integer>> theList = new ArrayList<>();
	
		@Override
		public List<Pair<ShapeLabel, Integer>> getResult() {
			return theList;
		}

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint nc, Object... arguments) {
			int polarity = (Integer) arguments[0];
			for (ShapeRef ref : collectReferencedShapeLabels(nc.getTripleExpression())) 
				theList.add(new Pair<>(ref.getLabel(), polarity));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
			int polarity = (Integer) arguments[0];
			Object[] newArguments = new Object[2];
			newArguments[0] = polarity * (-1);
			newArguments[1] = arguments[1];
			expr.getSubExpression().accept(this, newArguments);
		}
		
		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			int polarity = (Integer) arguments[0];
			theList.add(new Pair<>(shapeRef.getLabel(), polarity));
		}
	}
		
	class CollectDirectlyReferencedShapeLabelsVisitor extends ShapeExpressionVisitor<List<Pair<ShapeLabel,Integer>>> {

		private List<Pair<ShapeLabel,Integer>> result = new ArrayList<>();
		
		@Override
		public List<Pair<ShapeLabel,Integer>> getResult() { return result; }

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint expr, Object... arguments) {}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			result.add(new Pair<>(shapeRef.getLabel(), +1));
		}
	}
	
	
	// -------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------
	
	/** Computes a stratification, or null if the set of rules is not stratified. */ 
	public static List<Set<ShapeLabel>> computeStratification (Map<ShapeLabel, ShapeDefinition> rules) {
		DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph = computeDependencyGraph(rules);

		if(! checkIsStratified(depGraph))
			return null; 

		List<Set<ShapeLabel>> strats = getStronglyConnectedComponents(depGraph);
		List<Set<ShapeLabel>> orderedStrats = constructTopologicalOrder(depGraph, strats);
		return orderedStrats;
	}
	
	protected static DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> computeDependencyGraph 
		(Map<ShapeLabel, ShapeDefinition> rules) {
		
		Function<ShapeExpression, List<Pair<ShapeLabel, Integer>>> dependsOnShapeLabelsCollector = new Function<ShapeExpression, List<Pair<ShapeLabel, Integer>>>() {
			
			@Override
			public List<Pair<ShapeLabel, Integer>> apply(ShapeExpression e) {
				return collectDependsOnShapeLabels(e, 1, rules);
			}
		};
		return computeDependencyGraph(rules, dependsOnShapeLabelsCollector);
	}
	
	private static DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> computeDependencyGraph 
		(Map<ShapeLabel, ShapeDefinition> rules,
		Function<ShapeExpression, List<Pair<ShapeLabel, Integer>>> dependencyCollector) {
		
		DirectedWeightedGraphBuilder<ShapeLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge>> builder = 
				new DirectedWeightedGraphBuilder<ShapeLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeLabel,DefaultWeightedEdge>>(new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class));


		for (ShapeLabel label : rules.keySet()) {
			// FIXME ensure that ShapeLabel has the appropriate equals method
			builder.addVertex(label);
		}

		for (Map.Entry<ShapeLabel, ShapeDefinition> def : rules.entrySet()) {
			ShapeLabel label = def.getKey();
			ShapeExpression expr = def.getValue().expression;
			
			for (Pair<ShapeLabel, Integer> pair : dependencyCollector.apply(expr))
				builder.addEdge(label, pair.one, pair.two);
		}
		return builder.build();
	}
	
	private static boolean checkIsStratified (DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph) {
		TarjanSimpleCycles<ShapeLabel, DefaultWeightedEdge> tarjan = new TarjanSimpleCycles<>(depGraph);
		
		// get all cycles
		List<List<ShapeLabel>> cycles = tarjan.findSimpleCycles();
		
		// check if in one cycle, there is an edge with a negative value
		for(List<ShapeLabel> cycle : cycles){
			
			int cycleSize = cycle.size();
			for (int i = 0; i < cycleSize; i++) {
				ShapeLabel src = cycle.get(i);
				ShapeLabel tgt = cycle.get((i+1) % cycleSize);
				if (hasNegativeEdge(depGraph, src,tgt))
					return false;
			}
		}
		return true;
	}
	
	private static boolean hasNegativeEdge(DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> graph, ShapeLabel src, ShapeLabel tgt){
			
		// check all edges between 2 nodes
		for(DefaultWeightedEdge edge : graph.getAllEdges(src, tgt)){
			
			if (graph.getEdgeWeight(edge) < 0) {
				return true;
			}
		}
		return false;		
	}
	
	private static List<Set<ShapeLabel>> getStronglyConnectedComponents(DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph){
		KosarajuStrongConnectivityInspector<ShapeLabel, DefaultWeightedEdge> kscInspector = new KosarajuStrongConnectivityInspector<>(depGraph); 
		return kscInspector.stronglyConnectedSets();
	}
	
	private static List<Set<ShapeLabel>> constructTopologicalOrder(DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph,
			List<Set<ShapeLabel>> strats){
		DirectedAcyclicGraph<String, DefaultWeightedEdge> acyclicGraph =
				new DirectedAcyclicGraph<String, DefaultWeightedEdge>(new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(DefaultWeightedEdge.class));

		Map<String, Set<ShapeLabel>> map = new HashMap<>();
		// Creating a map to have a link between a node into the strongly connected graph and the nodes forming the strongly connected parts
		for(int i=0;i<strats.size();i++){
			String nodeName = i + "";
			map.put(nodeName, strats.get(i));
			acyclicGraph.addVertex(nodeName);
		}


		addAllEdgesIntoStronglyConnectedGraph(strats, acyclicGraph, map, depGraph);

		return getTopologicalOrderFromNonCyclicGraph(acyclicGraph, map);
	}

	private static void addAllEdgesIntoStronglyConnectedGraph(List<Set<ShapeLabel>> strats, DirectedAcyclicGraph<String,
			DefaultWeightedEdge> acyclicGraph, Map<String, Set<ShapeLabel>> map,
			DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph){
		for(String newNode : map.keySet()){
			Set<ShapeLabel> set = map.get(newNode);

			for(String otherNode : map.keySet()){
				if(!newNode.equals(otherNode)){
					Set<ShapeLabel> otherSet = map.get(otherNode);

					for(ShapeLabel label : set){
						for(ShapeLabel otherLabel : otherSet){
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

	private static List<Set<ShapeLabel>> getTopologicalOrderFromNonCyclicGraph(DirectedAcyclicGraph<String, DefaultWeightedEdge> graph, Map<String, Set<ShapeLabel>> map){

		Iterator<String> it = graph.iterator();

		List<Set<ShapeLabel>> stratsOrdered = new ArrayList<>();
		while(it.hasNext()){
			String newNode = it.next();

			stratsOrdered.add(0, map.get(newNode));
		}
		return stratsOrdered;
	}
	
	
	// -------------------------------------------------------------------------
	// DNF
	// -------------------------------------------------------------------------
	
	public static ShapeOrExpression computeDNF (ShapeExpression expr) {

		PushNegationsDownVisitor pushNegationsVisitor = staticInstance.new PushNegationsDownVisitor();
		expr.accept(pushNegationsVisitor, false);
		ShapeExpression negationsOnLeavesExpr = pushNegationsVisitor.getResult();
		
		FlattenAndOr flattenVisitor = staticInstance.new FlattenAndOr();
		negationsOnLeavesExpr.accept(flattenVisitor, new Object());
		ShapeExpression flattenedExpr = flattenVisitor.getResult(); 
		
		CreateDNFVisitor createDNFVisitor = staticInstance.new CreateDNFVisitor();
		flattenedExpr.accept(createDNFVisitor);
		ShapeExpression preDNF = createDNFVisitor.getResult();
		
		// Flatten the result
		flattenVisitor = staticInstance.new FlattenAndOr();
		preDNF.accept(flattenVisitor, new Object());
		ShapeExpression flatDNF = flattenVisitor.getResult();
		
		if (! (flatDNF instanceof ShapeOrExpression))
			return new ShapeOrExpression(Collections.singletonList(flatDNF));
		else return (ShapeOrExpression) flatDNF;
	}
	
	
	public class PushNegationsDownVisitor extends ShapeExpressionVisitor<ShapeExpression>{

		private Deque<ShapeExpression> stack = new ArrayDeque<>(); 
		
		@Override
		public ShapeExpression getResult() {
			return stack.pop();
		}

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint nc, Object... arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpression result;
			if (isNegated) result = new ShapeNotExpression(nc);
			else result = nc;

			stack.push(result);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint nc, Object... arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpression result;
			if (isNegated) result = new ShapeNotExpression(nc);
			else result = nc;

			stack.push(result);
		}
		
		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			boolean isNegated = (Boolean) arguments[0];
			
			ShapeExpression result;
			if (isNegated) result = new ShapeNotExpression(shapeRef);
			else result = shapeRef;
			
			stack.push(result);
		}
				
		@Override
		public void visitShapeAnd(ShapeAndExpression expr, Object... arguments) {
			super.visitShapeAnd(expr, arguments);
			List<ShapeExpression> subExpressions = new ArrayList<>();
			for (int i = 0; i < expr.getSubExpressions().size(); i++) {
				subExpressions.add(stack.pop());
			}
			
			boolean isNegated = (Boolean) arguments[0];
			ShapeExpression result;
			if (isNegated) result = new ShapeOrExpression(subExpressions);
			else result = new ShapeAndExpression(subExpressions);
			
			stack.push(result);
		}

		@Override
		public void visitShapeOr(ShapeOrExpression expr, Object... arguments) {
			super.visitShapeOr(expr, arguments);
			List<ShapeExpression> subExpressions = new ArrayList<>();
			for (int i = 0; i < expr.getSubExpressions().size(); i++) {
				subExpressions.add(stack.pop());
			}
			
			boolean isNegated = (Boolean) arguments[0];
			ShapeExpression result;
			if (isNegated) result = new ShapeAndExpression(subExpressions);
			else result = new ShapeOrExpression(subExpressions);
			
			stack.push(result);
		}

		@Override
		public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
			super.visitShapeNot(expr, ! (Boolean)arguments[0]);
		}
	}

	
	
	protected class FlattenAndOr extends ShapeExpressionVisitor<ShapeExpression> {
		
		private Deque<ShapeExpression> stack = new ArrayDeque<>();
		private final Object AND = new Object();
		private final Object OR = new Object();
		
		
		@Override
		public ShapeExpression getResult() {
			return stack.pop();
		}

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint nc, Object... arguments) {
			stack.push(nc);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint nc, Object... arguments) {
			stack.push(nc);
		}
		
		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			stack.push(shapeRef);
		}

		@Override
		public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
			ShapeExpression subExpr = expr.getSubExpression();
			if (! (subExpr instanceof NeighbourhoodConstraint || subExpr instanceof NodeConstraint))
				throw new IllegalArgumentException("Negation accepted only on the leaves");
			stack.push(expr);	
		}
		
		@Override
		public void visitShapeAnd(ShapeAndExpression expr, Object... arguments) {
			if (arguments[0].equals(AND)) {
				for (ShapeExpression e : expr.getSubExpressions()) {
					e.accept(this, AND);
				}
			} 
			else { // the father is not an AND
				List<ShapeExpression> subExpressions = new ArrayList<>();
				for (ShapeExpression e : expr.getSubExpressions()) {
					int stackSizeBefore = stack.size();
					e.accept(this, AND);
					int numberChildren = stack.size() - stackSizeBefore;
					for (int i = 0; i < numberChildren; i++) {
						subExpressions.add(stack.pop());
					}
				}
				stack.push(new ShapeAndExpression(subExpressions));	
			}
		}

		@Override
		public void visitShapeOr(ShapeOrExpression expr, Object... arguments) {
			if (arguments[0].equals(OR)) {
				for (ShapeExpression e : expr.getSubExpressions()) {
					e.accept(this, OR);
				}
			} 
			else { // the father is not an OR
				List<ShapeExpression> subExpressions = new ArrayList<>();
				for (ShapeExpression e : expr.getSubExpressions()) {
					int stackSizeBefore = stack.size();
					e.accept(this, OR);
					int numberChildren = stack.size() - stackSizeBefore;
					for (int i = 0; i < numberChildren; i++) {
						subExpressions.add(stack.pop());
					}
				}
				stack.push(new ShapeOrExpression(subExpressions));	
			}
		}		
		
	}
	
	/** To be applied only on expressions in which negation has been pushed down to the leaves. */
	protected class CreateDNFVisitor extends ShapeExpressionVisitor<ShapeExpression> {
		
		private Deque<ShapeExpression> stack = new ArrayDeque<>(); 
		
		@Override
		public ShapeExpression getResult() {
			return stack.pop();
		}

		@Override
		public void visitNeighbourhoodConstraint(NeighbourhoodConstraint expr, Object... arguments) {
			stack.push(expr);
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			stack.push(expr);
		}
		
		@Override
		public void visitShapeRef(ShapeRef shapeRef, Object[] arguments) {
			stack.push(shapeRef);
		}
		
		@Override
		public void visitShapeNot(ShapeNotExpression expr, Object... arguments) {
			ShapeExpression subExpr = expr.getSubExpression();
			if (! (subExpr instanceof NeighbourhoodConstraint || subExpr instanceof NodeConstraint))
				throw new IllegalArgumentException("Negation accepted only on the leaves");
			stack.push(expr);		
		}
		
		@Override
		public void visitShapeOr(ShapeOrExpression expr, Object... arguments) {
			List<ShapeExpression> newSubExpressions = new ArrayList<>();
			for (ShapeExpression e : expr.getSubExpressions()) {
				e.accept(this);
				newSubExpressions.add(stack.pop());
			}
			FlattenAndOr flattenVisitor = new FlattenAndOr();
			new ShapeOrExpression(newSubExpressions).accept(flattenVisitor);
			stack.push(flattenVisitor.getResult());
		}

		@Override
		public void visitShapeAnd(ShapeAndExpression expr, Object... arguments) {
			int stackSizeBefore = stack.size();
			List<ShapeExpression> subExpressions = new ArrayList<>(expr.getSubExpressions());
			
			ShapeOrExpression orSubExpression = getRemoveOrSubExpression(subExpressions);
			if (orSubExpression != null) {
				for (ShapeExpression e : orSubExpression.getSubExpressions()) {
					ShapeExpression andExpr = newFlattenedAndShapeExpression(subExpressions, e);
					andExpr.accept(this);
				}
			
				int numberChildren = stack.size() - stackSizeBefore;
				List<ShapeExpression> newOrSubexpressions = new ArrayList<>();
				for (int i = 0; i < numberChildren; i++) {
					newOrSubexpressions.add(stack.pop());
				}
				ShapeOrExpression newOr = new ShapeOrExpression(newOrSubexpressions);
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
		private ShapeOrExpression getRemoveOrSubExpression(List<ShapeExpression> subExpressions) {
			Iterator<ShapeExpression> it = subExpressions.iterator();
			while (it.hasNext()) {
				ShapeExpression e = it.next();
				if (e instanceof ShapeOrExpression) {
					it.remove();
					return (ShapeOrExpression) e;
				}
			}
			return null;
		}

		private ShapeExpression newFlattenedAndShapeExpression (List<ShapeExpression> subExpressions, ShapeExpression additionalSubExpr) {
			List<ShapeExpression> list = new ArrayList<>(subExpressions);
			list.add(additionalSubExpr);
			FlattenAndOr flattenVisitor = new FlattenAndOr();
			new ShapeAndExpression(list).accept(flattenVisitor, new Object());
			return flattenVisitor.getResult();
		}
		
	}
	
	
	protected static String depGraphToString (DefaultDirectedWeightedGraph<ShapeLabel, DefaultWeightedEdge> depGraph) {
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
	public static TripleExpression computeUnfoldedRepetitions (TripleExpression expr) {
		ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
		expr.accept(visitor);
		return visitor.getResult();
	}
	
}
