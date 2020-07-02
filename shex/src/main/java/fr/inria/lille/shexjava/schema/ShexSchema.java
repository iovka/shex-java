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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.RDF;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
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
import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.OneOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExternal;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.analysis.SchemaCollectors;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
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
	private ShapeExpr start;
	private Map<Label, ShapeExpr> rules;
	private Map<Label,ShapeExpr> shexprsMap;
	private Map<Label,TripleExpr> texprsMap;
	private Map<Integer,Set<Label>> stratification;
	
	/** The factory used for creating fresh {@link Label}s */
	private RDF rdfFactory;
	
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
	
		
	public ShexSchema(RDF rdfFactory, Map<Label, ShapeExpr> rules, ShapeExpr start) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this.start = start;
		this.rdfFactory = rdfFactory;
		
		this.rules = new HashMap<>(rules);
		if (start!=null && !this.rules.containsKey(start.getId()))
			this.rules.put(start.getId(),start);

		constructShexprMapAndCheckIdsAreUnique();
		checkThatAllShapeExprRefsAreDefined();
		constructTexprsMapAndCheckIdsAreUnique();
		checkThatAllTripleExprRefsAreDefined();

		checkNoCyclicReferences();
		computeStratification();
		
		this.rules = Collections.unmodifiableMap(rules);
		this.texprsMap = Collections.unmodifiableMap(texprsMap);
		this.shexprsMap = Collections.unmodifiableMap(shexprsMap);
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
	
	/** Return the start shapeExpr. */
	public ShapeExpr getStart() {
		return start;
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
	

	/** Computes and populates {@link #shexprsMap} */
	private void constructShexprMapAndCheckIdsAreUnique() {
		shexprsMap = new HashMap<>();
		Set<ShapeExpr> allShapes = SchemaCollectors.collectAllShapeExprs(this.rules);
		for(ShapeExpr shexpr : allShapes) {
			addIdIfNone(shexpr);
			if (shexprsMap.containsKey(shexpr .getId()))
				throw new IllegalArgumentException("Label "+shexpr.getId()+" already used.");
			shexprsMap.put(shexpr .getId(),shexpr );
		}
	}
	

	/** Computes and populates {@link #texprsMap} */
	private void constructTexprsMapAndCheckIdsAreUnique() {
		texprsMap = new HashMap<>();
		Set<TripleExpr> allTriples = SchemaCollectors.collectAllTriples(this.rules);
		for (TripleExpr tcexp : allTriples) {
			addIdIfNone(tcexp);
			if (shexprsMap.containsKey(tcexp.getId()) || texprsMap.containsKey(tcexp.getId()))
				throw new IllegalArgumentException("Label "+tcexp.getId()+" allready used.");
			texprsMap.put(tcexp.getId(),tcexp);
		}
	}

		
	private void checkThatAllShapeExprRefsAreDefined() throws UndefinedReferenceException {
		for (ShapeExpr sexpr : shexprsMap.values()){
			if (sexpr instanceof ShapeExprRef) {
				ShapeExprRef ref = (ShapeExprRef) sexpr;
				if (shexprsMap.containsKey(ref.getLabel())) 
					ref.setShapeDefinition(shexprsMap.get(ref.getLabel()));
				 else 
					throw new UndefinedReferenceException("Undefined shape label: " + ref.getLabel());
			}
		}
	}
	
	
	private void checkThatAllTripleExprRefsAreDefined() throws UndefinedReferenceException {
		for (TripleExpr texpr:texprsMap.values()){
			if (texpr instanceof TripleExprRef) {
				TripleExprRef ref = (TripleExprRef) texpr;
				if (texprsMap.containsKey(ref.getLabel())) 
					ref.setTripleDefinition(texprsMap.get(ref.getLabel()));
				else 
					throw new UndefinedReferenceException("Undefined triple label: " + ref.getLabel());
			}
		}
	}
	
	private void checkNoCyclicReferences() throws CyclicReferencesException {
		DefaultDirectedGraph<Label,DefaultEdge> referencesGraph = this.computeReferencesGraph();
		CycleDetector<Label, DefaultEdge> detector = new CycleDetector<>(referencesGraph);
		if (detector.detectCycles())
			throw new CyclicReferencesException("Cyclic dependencies of refences found: "+detector.findCycles()+"." );
	}
	
	
	private void computeStratification () throws NotStratifiedException {
		DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> depG = computeGraphOfDependences();
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
		stratification = new HashMap<>();
		int counterStrat = dag.vertexSet().size()-1;
		for (Label s:dag) {
			stratification.put(counterStrat,Collections.unmodifiableSet(revIndex.get(s)));
			counterStrat--;
		}
		
		stratification = Collections.unmodifiableMap(stratification);
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
	
	private Label createShapeLabel (String string, boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else 
			return new Label(rdfFactory.createBlankNode(string),generated);
	}
	
	private void addIdIfNone(ShapeExpr shape) {
		if (shape.getId() == null) {
			shape.setId(createShapeLabel(String.format("%s_%04d", SHAPE_LABEL_PREFIX,shapeLabelNb),true));
			shapeLabelNb++;
			//TODO pkoi ne pas laisser la factory gérer le compteur ? Sans doute pour le débuggage
		}
	}
	
	private Label createTripleLabel (String string,boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else 
			return new Label(rdfFactory.createBlankNode(string),generated);
	}
	
	private void addIdIfNone (TripleExpr triple) {
		if (triple.getId() == null) {
			triple.setId(createTripleLabel(String.format("%s_%04d", TRIPLE_LABEL_PREFIX,tripleLabelNb),true));
			tripleLabelNb++;
		}
	}
	
	
	//--------------------------------------------------------------------------------
	// Graph References computation
	//--------------------------------------------------------------------------------

	class CollectGraphReferencesFromShape extends ShapeExpressionVisitor<Set<Pair<Label,Label>>> {
		private Set<Pair<Label,Label>> set;

		public CollectGraphReferencesFromShape () {	
			this.set = new HashSet<Pair<Label,Label>>();
		}
		
		public CollectGraphReferencesFromShape (Set<Pair<Label,Label>> set) {	
			this.set = set;
		}	
		
		@Override
		public Set<Pair<Label,Label>> getResult() {
			return set;
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			CollectGraphReferencesFromTriple visitor = new CollectGraphReferencesFromTriple(set);
			expr.getTripleExpression().accept(visitor,arguments);
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			set.add(new Pair<Label,Label>(shapeRef.getId(),shapeRef.getLabel()));
		}
		
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				set.add(new Pair<Label,Label>(expr.getId(),subExpr.getId()));
			}
			super.visitShapeAnd(expr, arguments);
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				set.add(new Pair<Label,Label>(expr.getId(),subExpr.getId()));
			}
			super.visitShapeOr(expr, arguments);
		}
		
		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			set.add(new Pair<Label,Label>(expr.getId(),expr.getSubExpression().getId()));
			super.visitShapeNot(expr, arguments);
		}
		
		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
		}
	}
	
	
	class CollectGraphReferencesFromTriple extends TripleExpressionVisitor<Set<Pair<Label,Label>>> {
		private Set<Pair<Label,Label>> set;

		public CollectGraphReferencesFromTriple(Set<Pair<Label,Label>> set){
			this.set = set;
		}
		
		@Override
		public Set<Pair<Label, Label>> getResult() {
			return set;
		}

		@Override		
		public void visitEachOf (EachOf expr, Object ... arguments) {
			for (TripleExpr subExpr: expr.getSubExpressions()) {
				set.add(new Pair<Label,Label>(expr.getId(),subExpr.getId()));
			}
			super.visitEachOf(expr, arguments);
		}
		
		@Override		
		public void visitOneOf (OneOf expr, Object ... arguments) {
			for (TripleExpr subExpr: expr.getSubExpressions()) {
				set.add(new Pair<Label,Label>(expr.getId(),subExpr.getId()));
			}
			super.visitOneOf(expr, arguments);
		}
		
		@Override		
		public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
			expr.getSubExpression().accept(this, arguments);
		}
		
		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			CollectGraphReferencesFromShape visitor = new CollectGraphReferencesFromShape(set);
			tc.getShapeExpr().accept(visitor,arguments);		
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			set.add(new Pair<Label,Label>(expr.getId(),expr.getLabel()));
		}

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {}
		
	
	}
	
	
	private DefaultDirectedGraph<Label,DefaultEdge> computeReferencesGraph () {
		// Visit the schema to collect the references
		CollectGraphReferencesFromShape collector = new CollectGraphReferencesFromShape();
		for (ShapeExpr expr: this.rules.values()) {
			//System.out.println("Rule: "+expr.getId().toString());
			expr.accept(collector);
		}
		
		// build the graph
		GraphBuilder<Label,DefaultEdge,DefaultDirectedGraph<Label,DefaultEdge>> builder;
		builder = new GraphBuilder<Label,DefaultEdge,DefaultDirectedGraph<Label,DefaultEdge>>(new DefaultDirectedGraph<Label,DefaultEdge>(DefaultEdge.class));

		for (Label label : this.shexprsMap.keySet()) {
			builder.addVertex(label);
		}
		for (Label label : this.texprsMap.keySet()) {
			builder.addVertex(label);
		}

		for (Pair<Label,Label> edge : collector.getResult()) {
			builder.addEdge(edge.one, edge.two);
			//System.out.println(edge.one +" -> "+edge.two);
		}
		return builder.build();
	}

	
	// -------------------------------------------------------------------------------
	// Stratification computation and access v2
	// -------------------------------------------------------------------------------

	private List<Graph<Label, DefaultWeightedEdge>> checkIfGraphCanBeStratified(DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> dependecesGraph) throws NotStratifiedException {
		// Compute strongly connected components
		KosarajuStrongConnectivityInspector<Label,DefaultWeightedEdge> kscInspector;
		kscInspector = new KosarajuStrongConnectivityInspector<Label,DefaultWeightedEdge>(dependecesGraph);
		List<Graph<Label,DefaultWeightedEdge>> strConComp = kscInspector.getStronglyConnectedComponents();

		// Check that there is no negative edge in a strongly connected component
		for (Graph<Label,DefaultWeightedEdge> scc:strConComp) 
			for (DefaultWeightedEdge wedge:scc.edgeSet()) 
				if(scc.getEdgeWeight(wedge)<0) 
					throw new NotStratifiedException("The set of rules is not stratified (negative edge found in a strongly connected component).");

		return strConComp;
	}
	
	// TODO EXTENDS : the stratification graph should be re-defined
	private DefaultDirectedWeightedGraph<Label, DefaultWeightedEdge> computeGraphOfDependences() {
		GraphBuilder<Label,DefaultWeightedEdge,DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>> builder;
		builder = new GraphBuilder(new DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>(DefaultWeightedEdge.class));
		
		List<Label> shapes = this.shexprsMap.keySet().stream().filter(la -> (this.shexprsMap.get(la) instanceof Shape))
															  .collect(Collectors.toList());
		shapes.stream().forEach(la -> builder.addVertex(la));
		
		Map<Pair<Label,Label>,Boolean> edges = computeTheSetOfEdgesForTheGraphOfDependences(shapes);
		edges.entrySet().stream().forEach(entry -> builder.addEdge(entry.getKey().one, entry.getKey().two, entry.getValue()?1:-1));
		
		return builder.build();
	}
	
	
	private Map<Pair<Label,Label>,Boolean> computeTheSetOfEdgesForTheGraphOfDependences(List<Label> shapes) {
		Map<Pair<Label,Label>,Boolean> edges = new HashMap<>();
		
		ComputeReferenceSign signComputator = new ComputeReferenceSign();
		for (Label sourceShape:shapes) {
			Shape shape = ((Shape) this.shexprsMap.get(sourceShape));

			for (TripleConstraint tc:getSetOfTripleConstraintOfAShape(shape)) {
				signComputator.setRoot(tc.getShapeExpr().getId());
				tc.getShapeExpr().accept(signComputator);
				
				for (Label destination:getSetOfShapeInTheShapeExprOfATripleConstraint(tc)) {
					Pair<Label,Label> key = new Pair<Label,Label>(sourceShape,destination);
					Pair<Label,Label> subKey = new Pair<Label,Label>(tc.getShapeExpr().getId(), destination);

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
	
	
	private Set<TripleConstraint> getSetOfTripleConstraintOfAShape(Shape shape) {
		CollectTripleConstraintOfAShape tcCollector = new CollectTripleConstraintOfAShape();
		shape.getTripleExpression().accept(tcCollector);
		return tcCollector.getResult();
	}
	
	
	private Set<Label> getSetOfShapeInTheShapeExprOfATripleConstraint(TripleConstraint tc) {
		ShapeCollectorOfAShapeExpr shapeCol = new ShapeCollectorOfAShapeExpr();
		tc.getShapeExpr().accept(shapeCol);
		return shapeCol.getResult();
	}
	
	
	class CollectTripleConstraintOfAShape extends TripleExpressionVisitor<Set<TripleConstraint>> {
		private Set<TripleConstraint> set;

		public CollectTripleConstraintOfAShape(){
			this.set = new HashSet<TripleConstraint>();
		}
		
		@Override
		public Set<TripleConstraint> getResult() {
			return set;
		}

				
		@Override		
		public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
			expr.getSubExpression().accept(this, arguments);
		}
		
		@Override
		public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
			set.add(tc);
		}

		@Override
		public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
			expr.getTripleExp().accept(this, arguments);
		}

		@Override
		public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {}	
	}
	
	
	class ComputeReferenceSign extends ShapeExpressionVisitor<Map<Pair<Label,Label>,Boolean>> {
		private Label root;
		private boolean isPositive;
		private Map<Pair<Label,Label>,Boolean> collectedSign;

		public ComputeReferenceSign () {	
			this.collectedSign = new HashMap<Pair<Label,Label>,Boolean>();
			isPositive = true;
		}
		
		@Override
		public Map<Pair<Label, Label>, Boolean> getResult() {
			return collectedSign;
		}

		
		public void setRoot(Label rootLabel) {
			root = rootLabel;
		}
		
		private void updateSign(Label shapeExpr) {
			Pair<Label, Label> key = new Pair<Label,Label>(root, shapeExpr);
			if (!collectedSign.containsKey(key))
				collectedSign.put(key, isPositive);
			else 
				if (!isPositive)
					collectedSign.put(key, isPositive);
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
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
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
	
	
	class ShapeCollectorOfAShapeExpr extends ShapeExpressionVisitor<Set<Label>> {
		private Set<Label> shapes;

		public ShapeCollectorOfAShapeExpr () {
			shapes = new HashSet<Label>();
		}
		
		@Override
		public Set<Label> getResult() {
			return shapes;
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			shapes.add(expr.getId());
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			shapeRef.getShapeDefinition().accept(this, arguments);			
		}
		
		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
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

}

