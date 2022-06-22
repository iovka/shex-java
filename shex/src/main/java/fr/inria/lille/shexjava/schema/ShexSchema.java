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
package fr.inria.lille.shexjava.schema;

import java.util.*;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.schema.analysis.*;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import org.apache.commons.rdf.api.RDF;
import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.builder.GraphBuilder;

import com.moz.kiji.annotations.ApiStability.Stable;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.exception.CyclicReferencesException;
import fr.inria.lille.shexjava.exception.NotStratifiedException;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;
import fr.inria.lille.shexjava.util.Pair;

/** A ShEx schema.
 * 
 * An instance of this class represents a well-defined schema, that is, all shape labels are defined, there are no circular dependences between {@link ShapeExprRef} or {@link TripleExprRef}, and the set of rules is stratified.
 * The set of rules is not modifiable after construction.
 * All {@link ShapeExpr} and {@link TripleExpr} in the constructed schema have a {@link Label}, allowing to refer to the corresponding expression.
 * The stratification is a most refined stratification.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * @author Jérémie Dusart
 */
@Stable
public class ShexSchema {
	private final ShapeExpr start;
	private final Map<Label, ShapeExpr> rules;
	private final Map<Label,ShapeExpr> shexprsMap;
	private final Map<Label,TripleExpr> texprsMap;
	private final Map<Integer,Set<Label>> stratification;
	private final Map<ShapeExpr, Set<Shape>> concreteSubShapesMap;
	
	/** The factory used for creating fresh {@link Label}s */
	private RDF rdfFactory;

	public ShexSchema(RDF rdfFactory, Map<Label, ShapeExpr> rules, ShapeExpr start) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this.start = start;
		this.rdfFactory = rdfFactory;

		this.rules = Collections.unmodifiableMap(rules);
		this.shexprsMap = Collections.unmodifiableMap(constructShexprMapAndCheckIdsAreUnique(this.rules));
		checkThatAllShapeExprRefsAreDefined(this.shexprsMap);
		this.texprsMap = Collections.unmodifiableMap(constructTexprsMapAndCheckIdsAreUnique(this.rules, this.shexprsMap));
		checkThatAllTripleExprRefsAreDefined(this.texprsMap);

		// TODO integrate circularity in EXTENDS
		checkNoCyclicReferences(this.rules, this.shexprsMap, this.texprsMap);

		this.concreteSubShapesMap = Collections.unmodifiableMap(collectSubShapes(this.shexprsMap));
		this.stratification = Collections.unmodifiableMap(computeStratification(this.shexprsMap, this.concreteSubShapesMap));

		/*
		DefaultDirectedWeightedGraph<Label, DefaultWeightedEdge> depGraph = ShexSchema.computeGraphOfDependences(schema.getShapeExprsMap(), schema.getConcreteSubShapesMap());

        for (Map.Entry<Label, ShapeExpr> e : schema.getShapeExprsMap().entrySet()) {
            System.out.println(e.getKey() + "->" + e.getValue() +
                    ((e.getValue() instanceof Shape) ? " (Shape)" : ""));
        }

        System.out.println("");
        for (DefaultWeightedEdge e : depGraph.edgeSet()) {
            System.out.println(depGraph.getEdgeSource(e) + " -> " + depGraph.getEdgeTarget(e));
        }
		*/
	}



	/** Constructs a ShEx schema whenever the set of rules defines a well-defined schema.
	 * Otherwise, an exception is thrown.
	 * Uses {@link GlobalFactory#RDFFactory} for creating the fresh labels.
	 * 
	 * @param rules
	 * @throws UndefinedReferenceException
	 * @throws CyclicReferencesException
	 * @throws NotStratifiedException
	 */
	@Stable
	public ShexSchema(Map<Label, ShapeExpr> rules) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this(GlobalFactory.RDFFactory, rules, null);
	}
	
	/** Constructs a ShEx schema whenever the set of rules defines a well-defined schema.
	 * Otherwise, an exception is thrown.
	 * Uses {@link GlobalFactory#RDFFactory} for creating the fresh labels.
	 * 
	 * @param rules
	 * @param start
	 * @throws UndefinedReferenceException
	 * @throws CyclicReferencesException
	 * @throws NotStratifiedException
	 */
	public ShexSchema(Map<Label, ShapeExpr> rules, ShapeExpr start) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this(GlobalFactory.RDFFactory, rules, start);
	}
	
	/** Constructs a ShEx schema whenever the set of rules defines a well-defined schema.
	 * Otherwise, an exception is thrown.
	 * Allows to specify the factory to be used for creating the fresh labels.  
	 * @param rules
	 * @param rdfFactory
	 * @throws UndefinedReferenceException
	 * @throws CyclicReferencesException
	 * @throws NotStratifiedException
	 */
	public ShexSchema(RDF rdfFactory, Map<Label, ShapeExpr> rules) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this(rdfFactory,rules,null);
	}
	
		

	
	/** The rules of the schema.
	 * @return the rules of the schema.
	 */
	@Stable
	public Map<Label, ShapeExpr> getRules() {
		return rules;
	}

	@Stable
	public Map<Integer, Set<Label>> getStratification() {
		return stratification;
	}
	
	/** All the shape expressions that appear in the schema indexed by their label.	 */
	public Map<Label, ShapeExpr> getShapeExprsMap() {
		return shexprsMap;
	}

	/** All the triple expressions that appear in the schema indexed by their label. */
	public Map<Label, TripleExpr> getTripleExprsMap() {
		return texprsMap;
	}
	
	/** Return the start shape expression, or null if there is no start shape expression. */
	public ShapeExpr getStart() {
		return start;
	}

	/** Returns the concrete shape expressions that extend a given abstract shape, or null if the argument is not an abstract shape. */
	public Set<Shape> getConcreteSubShapes(ShapeExpr base) {
		return concreteSubShapesMap.get(base);
	}

	public Map<ShapeExpr, Set<Shape>> getConcreteSubShapesMap() {
		return concreteSubShapesMap;
	}

	/** The set of shape labels that are on a given stratum.
	 * 
	 * @param i
	 * @return the labels of the shapes on stratum i
	 * @deprecated Use {@link #getStratification()} instead
	 */
	@Deprecated
	public Set<Label> getLabelsAtStratum (int i) {
		if (i < 0 && i >= this.getStratification().size())
			throw new IllegalArgumentException("Stratum " + i + " does not exist");
		return Collections.unmodifiableSet(this.getStratification().get(i));
	}
	
	
	/** Get the number of stratums of the schema.
	 * @deprecated Use {@link #getStratification()} instead
	 */
	@Deprecated
	public int getNbStratums () {
		return this.getStratification().size();
	}

	
	/** The stratum of a given label.
	 * 
	 * @param label
	 * @return
	 * @deprecated Use {@link #getStratification()} instead
	 */
	@Deprecated
	public int getStratum (Label label) {
		for (int i = 0; i < getNbStratums(); i++)
			if (getLabelsAtStratum(i).contains(label))
				return i;
		throw new IllegalArgumentException("Unknown shape label: " + label);
	}


	public RDF getRdfFactory() {
		return rdfFactory;
	}

	
	@Override
	public String toString() {
		return rules.toString();
	}
	

	/** Populates {@link #shexprsMap} */
	private Map<Label, ShapeExpr> constructShexprMapAndCheckIdsAreUnique(Map<Label, ShapeExpr> rulesMap) {
		Map<Label, ShapeExpr> result = new HashMap<>();
		Set<ShapeExpr> allShapes = SchemaCollectors.collectAllShapeExprs(rulesMap);
		for(ShapeExpr shexpr : allShapes) {
			addIdIfNone(shexpr);
			if (result.containsKey(shexpr.getId()))
				throw new IllegalArgumentException("Label "+shexpr.getId()+" already used.");
			result.put(shexpr.getId(),shexpr );
		}
		return result;
	}
	

	/** Computes and populates {@link #texprsMap} */
	private Map<Label, TripleExpr> constructTexprsMapAndCheckIdsAreUnique(Map<Label, ShapeExpr> rulesMap, Map<Label, ShapeExpr> shapeExprsMap) {
		Map<Label,TripleExpr> result = new HashMap<>();
		Set<TripleExpr> allTriples = SchemaCollectors.collectAllTriples(rulesMap);
		for (TripleExpr tcexp : allTriples) {
			addIdIfNone(tcexp);
			if (shapeExprsMap.containsKey(tcexp.getId()) || result.containsKey(tcexp.getId()))
				throw new IllegalArgumentException("Label "+tcexp.getId()+" allready used.");
			result.put(tcexp.getId(),tcexp);
		}
		return result;
	}

		
	private void checkThatAllShapeExprRefsAreDefined(Map<Label, ShapeExpr> shapeExprsMap) throws UndefinedReferenceException {
		for (ShapeExpr sexpr : shapeExprsMap.values()) {
			if (sexpr instanceof ShapeExprRef) {
				ShapeExprRef ref = (ShapeExprRef) sexpr;
				if (shapeExprsMap.containsKey(ref.getLabel()))
					ref.setShapeDefinition(shapeExprsMap.get(ref.getLabel()));
				else
					throw new UndefinedReferenceException("Undefined shape label: " + ref.getLabel());
			}
		}
	}

	static Map<ShapeExpr, Set<Shape>> collectSubShapes(Map<Label, ShapeExpr> shapeExprsMap) throws CyclicReferencesException {
		DirectedAcyclicGraph<ShapeExpr, DefaultEdge> extendsRelationGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
		Set<Shape> allShapes = shapeExprsMap.values().stream().filter(it -> it instanceof Shape).map(it -> (Shape) it).collect(Collectors.toSet());
		for (Shape superShape: allShapes) {
			extendsRelationGraph.addVertex(superShape);
			for (ShapeExprRef base : superShape.getBases()) {
				extendsRelationGraph.addVertex(base.getShapeDefinition());
				try {
					extendsRelationGraph.addEdge(base.getShapeDefinition(), superShape);
				} catch (IllegalArgumentException x) {
					throw new CyclicReferencesException("Cyclic EXTENDS in Shape " + superShape.getId() + " caused by EXTENDS " + base.getShapeDefinition().getId());
				}
			}
		}
		TransitiveClosure.INSTANCE.closeDirectedAcyclicGraph(extendsRelationGraph);
		Map<ShapeExpr, Set<Shape>> result = new HashMap<>(allShapes.size());
		for (Shape shape : allShapes) {
			Set<Shape> set = new HashSet<>();
			result.put(shape, set);
			for (DefaultEdge outEdge : extendsRelationGraph.outgoingEdgesOf(shape)) {
				Shape subShape = (Shape) extendsRelationGraph.getEdgeTarget(outEdge);
				if (! subShape.isAbstract())
					set.add(subShape);
			}
		}
		return result;
	}

	private void checkThatAllTripleExprRefsAreDefined(Map<Label, TripleExpr> tripleExprsMap) throws UndefinedReferenceException {
		for (TripleExpr texpr : tripleExprsMap.values()){
			if (texpr instanceof TripleExprRef) {
				TripleExprRef ref = (TripleExprRef) texpr;
				if (tripleExprsMap.containsKey(ref.getLabel()))
					ref.setTripleDefinition(tripleExprsMap.get(ref.getLabel()));
				else 
					throw new UndefinedReferenceException("Undefined triple label: " + ref.getLabel());
			}
		}
	}
	
	private void checkNoCyclicReferences(
		Map<Label, ShapeExpr> rulesMap, Map<Label, ShapeExpr> shapeExprsMap, Map<Label, TripleExpr> tripleExprMap)
			throws CyclicReferencesException {

		DefaultDirectedGraph<Label,DefaultEdge> referencesGraph = this.computeReferencesGraph(
				rulesMap, shapeExprsMap, tripleExprMap);
		CycleDetector<Label, DefaultEdge> detector = new CycleDetector<>(referencesGraph);
		if (detector.detectCycles())
			throw new CyclicReferencesException("Cyclic dependencies of references found: "+detector.findCycles()+"." );
	}
	
	
	static private Map<Integer, Set<Label>> computeStratification (
			Map<Label, ShapeExpr> shapeExprMap, Map<ShapeExpr, Set<Shape>> subShapesMap) throws NotStratifiedException {

		DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> depG = computeGraphOfDependences(shapeExprMap, subShapesMap);
		List<Graph<Label, DefaultWeightedEdge>> strConComp = checkIfGraphCanBeStratified(depG);

		// Shrink the strongly connected component and memorize how the shrink was performed.
		Map<Label,Set<Label>> revIndex = new HashMap<>(); // for a vertex, contains the set of the vertices shrink on it
		strConComp.stream().forEach(scc -> revIndex.put(scc.vertexSet().iterator().next(), scc.vertexSet()));
		Map<Label,Label> index = new HashMap<>(); // for a vertex, the map contains the vertex on which it has been shrink
		revIndex.entrySet().stream().forEach(entry -> entry.getValue().stream().forEach(v -> index.put(v,entry.getKey())));
		
		// A new graph is going to be created with the strongly connected component shrink to one vertex
		DirectedAcyclicGraph<Label,DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);
		revIndex.keySet().stream().forEach(v -> dag.addVertex(v) );
		depG.edgeSet().stream()
			.map(edge -> new Pair<Label,Label>(index.get(depG.getEdgeSource(edge)),index.get(depG.getEdgeTarget(edge))))
			.filter(pair -> !pair.one.equals(pair.two)).forEach(pair -> dag.addEdge(pair.one,pair.two));
				
		// Compute the stratification using an iterator of the dag
		Map<Integer, Set<Label>> result = new HashMap<>();
		int counterStrat = dag.vertexSet().size()-1;
		for (Label s:dag) {
			result.put(counterStrat,Collections.unmodifiableSet(revIndex.get(s)));
			counterStrat--;
		}
		return result;
	}
	
	//--------------------------------------------------------------------------------
	// ID  function
	//--------------------------------------------------------------------------------
	
	private static int shapeLabelNb = 0;
	private static String SHAPE_LABEL_PREFIX = "SLGEN";
	private static int tripleLabelNb = 0;
	private static String TRIPLE_LABEL_PREFIX = "TLGEN";
	
	private static boolean isIriString (String s) {
		if (s.indexOf(':') < 0) {
			return false;
		}
		return true;
	}

	private void addIdIfNone(Expression expr) {
		if (expr.getId() == null) {
			expr.setId(LabelGenerated.getNew());
		}
	}

	
	//--------------------------------------------------------------------------------
	// Graph References computation
	//--------------------------------------------------------------------------------

	static class CollectGraphReferencesFromShape extends ShapeExpressionVisitor<Set<Pair<Label,Label>>> {

		public CollectGraphReferencesFromShape () {	
			setResult(new HashSet<>());
		}
		
		public CollectGraphReferencesFromShape (Set<Pair<Label,Label>> set) {	
			setResult(set);
		}	

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			CollectGraphReferencesFromTriple visitor = new CollectGraphReferencesFromTriple(getResult());
			expr.getTripleExpression().accept(visitor,arguments);
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) { /* Do nothing */ }
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			getResult().add(new Pair<>(shapeRef.getId(),shapeRef.getLabel()));
		}
		
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			super.visitShapeAnd(expr, arguments);
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				getResult().add(new Pair<>(expr.getId(),subExpr.getId()));
			}
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			super.visitShapeOr(expr, arguments);
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				getResult().add(new Pair<>(expr.getId(),subExpr.getId()));
			}
		}
		
		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			super.visitShapeNot(expr, arguments);
			getResult().add(new Pair<>(expr.getId(),expr.getSubExpression().getId()));
		}
	}
	
	
	static class CollectGraphReferencesFromTriple extends TripleExpressionVisitor<Set<Pair<Label,Label>>> {

		public CollectGraphReferencesFromTriple(Set<Pair<Label,Label>> set){
			setResult(set);
		}

		@Override		
		public void visitEachOf (EachOf expr, Object ... arguments) {
			super.visitEachOf(expr, arguments);
			for (TripleExpr subExpr: expr.getSubExpressions()) {
				getResult().add(new Pair<>(expr.getId(),subExpr.getId()));
			}
		}
		
		@Override		
		public void visitOneOf (OneOf expr, Object ... arguments) {
			super.visitOneOf(expr, arguments);
			for (TripleExpr subExpr: expr.getSubExpressions()) {
				getResult().add(new Pair<>(expr.getId(),subExpr.getId()));
			}
		}

		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			CollectGraphReferencesFromShape visitor = new CollectGraphReferencesFromShape(getResult());
			tc.getShapeExpr().accept(visitor,arguments);		
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			getResult().add(new Pair<>(expr.getId(),expr.getLabel()));
		}

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) { /* Do nothing */ }
		
	
	}
	
	
	private DefaultDirectedGraph<Label,DefaultEdge> computeReferencesGraph (
			Map<Label, ShapeExpr> rulesMap, Map<Label, ShapeExpr> shapeExprsMap, Map<Label, TripleExpr> tripleExprMap) {
		// Visit the schema to collect the references
		CollectGraphReferencesFromShape collector = new CollectGraphReferencesFromShape();
		for (ShapeExpr expr: rulesMap.values()) {
			expr.accept(collector);
		}
		
		// build the graph
		GraphBuilder<Label,DefaultEdge,DefaultDirectedGraph<Label,DefaultEdge>> builder;
		builder = new GraphBuilder<>(new DefaultDirectedGraph<>(DefaultEdge.class));

		for (Label label : shapeExprsMap.keySet()) {
			builder.addVertex(label);
		}
		for (Label label : tripleExprMap.keySet()) {
			builder.addVertex(label);
		}
		for (Pair<Label,Label> edge : collector.getResult()) {
			builder.addEdge(edge.one, edge.two);
		}
		return builder.build();
	}

	
	// -------------------------------------------------------------------------------
	// Stratification computation and access v2
	// -------------------------------------------------------------------------------

	static private List<Graph<Label, DefaultWeightedEdge>> checkIfGraphCanBeStratified(DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> dependecesGraph) throws NotStratifiedException {
		// Compute strongly connected components
		KosarajuStrongConnectivityInspector<Label,DefaultWeightedEdge> kscInspector;
		kscInspector = new KosarajuStrongConnectivityInspector<>(dependecesGraph);
		List<Graph<Label,DefaultWeightedEdge>> strConComp = kscInspector.getStronglyConnectedComponents();

		// Check that there is no negative edge in a strongly connected component
		for (Graph<Label,DefaultWeightedEdge> scc:strConComp) 
			for (DefaultWeightedEdge wedge:scc.edgeSet()) 
				if(scc.getEdgeWeight(wedge)<0) 
					throw new NotStratifiedException("The set of rules is not stratified (negative edge found in a strongly connected component).");

		return strConComp;
	}


	public static DefaultDirectedWeightedGraph<Label, DefaultWeightedEdge> computeGraphOfDependences(
			Map<Label, ShapeExpr> shapeExprsMap, Map<ShapeExpr, Set<Shape>> subShapesMap) {

		Set<ShapeExpr> extendedNonShapes =
				subShapesMap.keySet().stream()
					.filter(se -> !(se instanceof Shape))
						.collect(Collectors.toSet());

		GraphBuilder<Label,DefaultWeightedEdge,DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>> builder;
		builder = new GraphBuilder(new DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>(DefaultWeightedEdge.class));

		// There is an edge from 'shape1' to 'shape2' in the dependency graph if
		for (ShapeExpr shapeExpr : shapeExprsMap.values()) {
			if (! (shapeExpr instanceof Shape)) continue;
			Shape shape1 = (Shape) shapeExpr;
			builder.addVertex(shape1.getId());

			for (Shape shape2 : subShapesMap.get(shape1)) {
				// - 'shape2' is sub-shape of 'shape1'
				builder.addVertex(shape2.getId());
				builder.addEdge(shape1.getId(), shape2.getId(), 1);
			}

			for (TripleConstraint tc : collectTripleConstraintsOfShape(shape1)) {
				Pair<Set<Pair<Shape, Boolean>>, Set<ShapeExpr>> collected =
						collectAllShapesAndTheirSignsAndTraversedShapeRefs(tc.getShapeExpr(), extendedNonShapes);

				Set<Pair<Shape,Boolean>> shapesAndTheirSigns = collected.one;
				for (Pair<Shape, Boolean> shapeAndSign : shapesAndTheirSigns) {
					Shape shape2 = shapeAndSign.one;
					// - there is triple constraint 'tc' in the triple expression of 'shape1'
					//   s.t. 'shape2' appears as atomic shape in 'tc'.getShapeExpr()
					//   The label of the edge is +1 (positive) if 'shape2' appears positively in the shape expression of 'tc',
					//   and is -1 otherwise
					builder.addEdge(shape1.getId(), shape2.getId(), shapeAndSign.two ? 1 : -1);
				}

				// - there is a triple constraint 'tc' in the triple expression of 'shape1',
				//   there is a shape expression 'se' referenced in 'tc'.getShapeExpr(),
				//   'shape2' is sub-shape of 'se'
				Set<ShapeExpr> traversedExtendedNonShapes = collected.two;
				for (ShapeExpr se : traversedExtendedNonShapes)
					for (Shape shape2 : subShapesMap.get(se)) {
						builder.addVertex(shape2.getId());
						builder.addEdge(shape1.getId(), shape2.getId(), 1);
					}
			}
		}
		return builder.build();
	}
	
	
	static private Map<Pair<Label,Label>,Boolean> computeTheSetOfEdgesForTheGraphOfDependences(
			List<Label> shapes, Map<Label, ShapeExpr> shapeExprsMap) {
		Map<Pair<Label,Label>,Boolean> edges = new HashMap<>();
		
		ComputeReferenceSign signComputator = new ComputeReferenceSign();
		for (Label sourceShape:shapes) {
			Shape shape = ((Shape) shapeExprsMap.get(sourceShape));

			for (TripleConstraint tc : getSetOfTripleConstraintOfAShape(shape)) {
				signComputator.setRoot(tc.getShapeExpr().getId());
				tc.getShapeExpr().accept(signComputator);
				
				for (Label destination:getSetOfShapeInTheShapeExprOfATripleConstraint(tc)) {
					Pair<Label,Label> key = new Pair<>(sourceShape,destination);
					Pair<Label,Label> subKey = new Pair<>(tc.getShapeExpr().getId(), destination);

					if (shape.getExtraProperties().contains(tc.getProperty().getIri())) {
						if (!edges.containsKey(key) || signComputator.getResult().get(subKey))
							edges.put(key,!signComputator.getResult().get(subKey));
					} else {
						if (!edges.containsKey(key) || !signComputator.getResult().get(subKey))
							edges.put(key,signComputator.getResult().get(subKey));
					}
				}	
			}
		}
		return edges;
	}
	
	
	static private List<TripleConstraint> getSetOfTripleConstraintOfAShape(Shape shape) {
		CollectTripleConstraintsTE tcCollector = new CollectTripleConstraintsTE();
		shape.getTripleExpression().accept(tcCollector);
		return tcCollector.getResult().one;
	}
	
	
	static private Set<Label> getSetOfShapeInTheShapeExprOfATripleConstraint(TripleConstraint tc) {
		ShapeCollectorOfAShapeExpr shapeCol = new ShapeCollectorOfAShapeExpr();
		tc.getShapeExpr().accept(shapeCol);
		return shapeCol.getResult();
	}

	static private Set<TripleConstraint> collectTripleConstraintsOfShape (Shape shape) {
		CollectElementsFromTriple<TripleConstraint> collector = new CollectElementsFromTriple(e -> e instanceof TripleConstraint, new HashSet<>(), false);
		shape.getTripleExpression().accept(collector);
		return collector.getResult();
	}

	static class ComputeReferenceSign extends ShapeExpressionVisitor<Map<Pair<Label,Label>,Boolean>> {
		private Label root;
		private boolean isPositive;

		public ComputeReferenceSign () {	
			setResult(new HashMap<>());
			isPositive = true;
		}

		public void setRoot(Label rootLabel) {
			root = rootLabel;
		}
		
		private void updateSign(Label shapeExpr) {
			Pair<Label, Label> key = new Pair<>(root, shapeExpr);
			if (!getResult().containsKey(key))
				getResult().put(key, isPositive);
			else 
				if (!isPositive)
					getResult().put(key, isPositive);
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			updateSign(expr.getId());
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			shapeRef.getShapeDefinition().accept(this, arguments);			
		}

		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {		
			for (ShapeExpr subExpr: expr.getSubExpressions()) 
				subExpr.accept(this, arguments);
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr subExpr: expr.getSubExpressions()) 
				subExpr.accept(this, arguments);
		}


		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			isPositive = !isPositive;
			expr.getSubExpression().accept(this, arguments);
			isPositive = !isPositive;
		}
	}

	
	static class ShapeCollectorOfAShapeExpr extends ShapeExpressionVisitor<Set<Label>> {

		public ShapeCollectorOfAShapeExpr () {
			setResult(new HashSet<>());
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			getResult().add(expr.getId());
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			shapeRef.getShapeDefinition().accept(this, arguments);			
		}

		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {		
			for (ShapeExpr subExpr: expr.getSubExpressions()) 
				subExpr.accept(this, arguments);
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr subExpr: expr.getSubExpressions()) 
				subExpr.accept(this, arguments);
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			expr.getSubExpression().accept(this, arguments);
		}
		
	}

	public static Pair<Set<Pair<Shape, Boolean>>, Set<ShapeExpr>> collectAllShapesAndTheirSignsAndTraversedShapeRefs
			(ShapeExpr shapeExpr, Set<ShapeExpr> detectIfTraversedAsReference) {
		Set<ShapeExpr> traversedAsReferenceResult = new HashSet<>();
		CollectShapesOfShapeExprAndTheirSigns collector = new CollectShapesOfShapeExprAndTheirSigns();
		shapeExpr.accept(collector, true, detectIfTraversedAsReference, traversedAsReferenceResult);
		return new Pair<>(collector.getResult(), traversedAsReferenceResult);
	}

	/** For a shape expression, collects all its leaf shapes and their sign, i.e. whether they appear negated (sign false) or non negated.
	 * Additionally, monitors which of a given set of shape expression labels are referenced in the shape expression.
	 * To simplify the data structures, the referenced labels of interest are collected in a set passed as parameter, not in the result. */
	static class CollectShapesOfShapeExprAndTheirSigns
			extends ShapeExpressionVisitor<Set<Pair<Shape, Boolean>>>{

		public CollectShapesOfShapeExprAndTheirSigns() {
			setResult(new HashSet<>());
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			boolean sign = (Boolean) arguments[0];
			getResult().add(new Pair<>(expr, sign));
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			/* Do nothing */
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
			Set<Label> detectIfTraversedAsReference = (Set) arguments[1];
			Set<Label> detectIfTraversedAsReferencResult = (Set) arguments[2];
			shapeRef.getShapeDefinition().accept(this, arguments);
			if (detectIfTraversedAsReference.contains(shapeRef.getLabel()))
				detectIfTraversedAsReferencResult.add(shapeRef.getLabel());
		}

		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			boolean sign = (Boolean) arguments[0];
			arguments[0] = sign;
			expr.getSubExpression().accept(this, arguments);
		}
	}
}

