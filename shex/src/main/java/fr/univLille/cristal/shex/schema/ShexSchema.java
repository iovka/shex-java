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
package fr.univLille.cristal.shex.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;
import org.jgrapht.Graph;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.builder.GraphBuilder;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.schema.analysis.SchemaCollectors;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.Pair;
import fr.univLille.cristal.shex.util.RDFFactory;

/** A ShEx schema.
 * 
 * An instance of this class represents a well-defined schema, that is, all shape labels are defined, and the set of rules is stratified.
 * The stratification is a most refined stratification.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * @author Jérémie Dusart
 */
public class ShexSchema {
	private Map<Integer,Set<Label>> stratification = null;
	private Map<Label, ShapeExpr> rules;
	private Map<Label,ShapeExpr> shapeMap;
	private Map<Label,TripleExpr> tripleMap;

	// generate ID, check cycle in reference and stratification
	public ShexSchema(Map<Label, ShapeExpr> rules) throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		//check that id are unique
		
		this.rules = Collections.unmodifiableMap(new HashMap<Label, ShapeExpr>(rules));
		// Collect all the ShapeExpr
		Set<ShapeExpr> allShapes = SchemaCollectors.collectAllShapes(this.rules);
		Map<Label,ShapeExpr> shapeMapTmp = new HashMap<Label,ShapeExpr>();
		for(ShapeExpr shexp:allShapes) {
			checkShapeID(shexp);
			if (shapeMapTmp.containsKey(shexp.getId()))
				throw new IllegalArgumentException("Label "+shexp.getId()+" allready used.");
			shapeMapTmp.put(shexp.getId(),shexp);
		}
		this.shapeMap = Collections.unmodifiableMap(new HashMap<Label, ShapeExpr>(shapeMapTmp));
		// Check that all the shape references are defined
		for (Map.Entry<Label,ShapeExpr> entry:shapeMap.entrySet()){
			if (entry.getValue() instanceof ShapeExprRef) {
				ShapeExprRef ref = (ShapeExprRef) entry.getValue();
				if (shapeMap.containsKey(ref.getLabel())) {
					ref.setShapeDefinition(shapeMap.get(ref.getLabel()));
				}else {
					throw new UndefinedReferenceException("Undefined shape label: " + ref.getLabel());
				}
			}
		}
		
		// Collect all TripleExpr
		Set<TripleExpr> allTriples = SchemaCollectors.collectAllTriples(this.rules);
		Map<Label,TripleExpr> tripleMapTmp = new HashMap<Label,TripleExpr>();
		for (TripleExpr tcexp:allTriples) {
			checkTripleID(tcexp);
			if (shapeMap.containsKey(tcexp.getId()) || tripleMapTmp.containsKey(tcexp.getId()))
				throw new IllegalArgumentException("Label "+tcexp.getId()+" allready used.");
			tripleMapTmp.put(tcexp.getId(),tcexp);
			
			//System.out.println("ID:"+tcexp.getId()+" : "+tcexp+ "("+tcexp.getClass()+")");
		}
		tripleMap = Collections.unmodifiableMap(new HashMap<Label,TripleExpr>(tripleMapTmp));
		
		// Check that all the triple references are defined
		for (Map.Entry<Label,TripleExpr> entry:tripleMap.entrySet()){
			if (entry.getValue() instanceof TripleExprRef) {
				TripleExprRef ref = (TripleExprRef) entry.getValue();
				if (tripleMap.containsKey(ref.getLabel())) {
					ref.setTripleDefinition(tripleMap.get(ref.getLabel()));
				}else {
					throw new UndefinedReferenceException("Undefined triple label: " + ref.getLabel());
				}
			}
		}
		
		// Check that there is no cycle in the definition of the references
		DefaultDirectedGraph<Label,DefaultEdge> referencesGraph = this.computeReferencesGraph();
		TarjanSimpleCycles<Label,DefaultEdge> cyclesFinder = new TarjanSimpleCycles<Label,DefaultEdge>(referencesGraph);
		List<List<Label>> allcycles = cyclesFinder.findSimpleCycles();
		
		if (! allcycles.isEmpty())
			throw new CyclicReferencesException("Cyclic dependencies of refences found: " + allcycles);
		
		//Starting to check and compute stratification
		DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> dependecesGraph = this.computeDependencesGraph();
		
		// Compute strongly connected components
		KosarajuStrongConnectivityInspector<Label,DefaultWeightedEdge> kscInspector;
		kscInspector = new KosarajuStrongConnectivityInspector<Label,DefaultWeightedEdge>(dependecesGraph);
		List<Graph<Label,DefaultWeightedEdge>> strConComp = kscInspector.getStronglyConnectedComponents();
		
		// Check that there is no negative edge in a strongly connected component
		for (Graph<Label,DefaultWeightedEdge> scc:strConComp) {
			for (DefaultWeightedEdge wedge:scc.edgeSet()) {
				if(scc.getEdgeWeight(wedge)<0) {
					throw new NotStratifiedException("The set of rules is not stratified (negative edge found in a strongly connected component).");
				}
			}
		}
		
		//	Create a directed acyclic graph to compute the topological sort
		DirectedAcyclicGraph<Label,DefaultEdge> dag = new DirectedAcyclicGraph<Label,DefaultEdge>(DefaultEdge.class);
		
		// create an index map 
		Map<Label,Label> index = new HashMap<Label,Label>();
		Map<Label,Set<Label>> revIndex = new HashMap<Label,Set<Label>>();
		for (Graph<Label,DefaultWeightedEdge> scc:strConComp) {
			Label representant = null;
			for (Label dest:scc.vertexSet()) {
				if (representant==null) {
					representant=dest;
					revIndex.put(representant, scc.vertexSet());
					dag.addVertex(representant);
				}
				index.put(dest,representant);
			}
		}
		
		// add the edges
		for(DefaultWeightedEdge wedge:dependecesGraph.edgeSet()) {
			Label source = index.get(dependecesGraph.getEdgeSource(wedge));
			Label target = index.get(dependecesGraph.getEdgeTarget(wedge));
			if(!source.equals(target))
				dag.addEdge(source,target);
		}
		
		// Compute Stratification using an iterator of the dag
		stratification = new HashMap<Integer,Set<Label>>();
		int counterStrat = dag.vertexSet().size()-1;
		for (Label S:dag) {
			Set<Label> tmp = new HashSet<Label>();
			for (Label l:revIndex.get(S))
				tmp.add((Label) l);
			stratification.put(counterStrat,tmp);
			counterStrat--;
		}
	
	}

	
	public Map<Label, ShapeExpr> getRules() {
		return rules;
	}

	public Map<Label, ShapeExpr> getShapeMap() {
		return shapeMap;
	}

	public Map<Label, TripleExpr> getTripleMap() {
		return tripleMap;
	}
	
	@Override
	public String toString() {
		return rules.toString();
	}
	
	//--------------------------------------------------------------------------------
	// ID  function
	//--------------------------------------------------------------------------------
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();
	
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
	
	private static Label createShapeLabel (String string,boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else 
			return new Label(rdfFactory.createBNode(string),generated);
	}
	
	private void checkShapeID(ShapeExpr shape) {
		if (shape.getId() == null) {
			shape.setId(createShapeLabel(String.format("%s_%04d", SHAPE_LABEL_PREFIX,shapeLabelNb),true));
			shapeLabelNb++;
		}
	}
	
	private static Label createTripleLabel (String string,boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else 
			return new Label(rdfFactory.createBNode(string),generated);
	}
	
	private void checkTripleID(TripleExpr triple) {
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
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {}
		
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
			tc.getShapeExpr().accept(visitor,arguments);		}

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

		for (Label label : this.shapeMap.keySet()) {
			builder.addVertex(label);
		}
		for (Label label : this.tripleMap.keySet()) {
			builder.addVertex(label);
		}

		for (Pair<Label,Label> edge : collector.getResult()) {
			builder.addEdge(edge.one, edge.two);
			//System.out.println(edge.one +" -> "+edge.two);
		}
		return builder.build();
	}
	

	// -------------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------------

	class CollectGraphDependencyFromShape extends ShapeExpressionVisitor<Set<Pair<Pair<Label,Label>,Integer>>> {
		private Set<Label> visited;
		private Set<Pair<Pair<Label,Label>,Integer>> set;

		public CollectGraphDependencyFromShape () {	
			this.set = new HashSet<Pair<Pair<Label,Label>,Integer>>();
			this.visited = new HashSet<Label>();
		}
		
		public CollectGraphDependencyFromShape (Set<Pair<Pair<Label,Label>,Integer>> set) {	
			this.set = set;
			this.visited = new HashSet<Label>();
		}	
		
		@Override
		public Set<Pair<Pair<Label,Label>,Integer>> getResult() {
			return set;
		}
		
		public Set<Label> getVisited() {
			return visited;
		}
		
		@Override
		public void visitShape(Shape expr, Object... arguments) {
			this.visited.add(expr.getId());
			
			CollectTripleConstraintDependentOfAShape visitor = new CollectTripleConstraintDependentOfAShape();
			expr.getTripleExpression().accept(visitor,arguments);
			Set<TripleConstraint> triples = visitor.getResult();
			
			Set<TCProperty> extra = expr.getExtraProperties();
			for(TripleConstraint texpr:triples) {
				//System.out.println(texpr.getProperty());
				if (extra.contains(texpr.getProperty())) {
					Pair<Label,Label> edge =new Pair<Label,Label>(expr.getId(),texpr.getShapeExpr().getId());
					set.add(new Pair<Pair<Label,Label>,Integer>(edge,-1));
				}else {
					Pair<Label,Label> edge =new Pair<Label,Label>(expr.getId(),texpr.getShapeExpr().getId());
					set.add(new Pair<Pair<Label,Label>,Integer>(edge,1));
				}
				
			}
		}
		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			this.visited.add(expr.getId());
		}
		
		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			this.visited.add(shapeRef.getId());
			
			Integer parity = 1;
			Pair<Label,Label> edge =new Pair<Label,Label>(shapeRef.getId(),shapeRef.getLabel());
			set.add(new Pair<Pair<Label,Label>,Integer>(edge,parity));
			
		}
		
		@Override
		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {
			this.visited.add(shapeExt.getId());
		}
		
		@Override
		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
			this.visited.add(expr.getId());
			
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				Pair<Label,Label> edge =new Pair<Label,Label>(expr.getId(),subExpr.getId());
				set.add(new Pair<Pair<Label,Label>,Integer>(edge,1));
			}
			super.visitShapeAnd(expr, arguments);
		}
		
		@Override
		public void visitShapeOr(ShapeOr expr, Object... arguments) {
			this.visited.add(expr.getId());
			
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				Pair<Label,Label> edge =new Pair<Label,Label>(expr.getId(),subExpr.getId());
				set.add(new Pair<Pair<Label,Label>,Integer>(edge,1));
			}
			super.visitShapeOr(expr, arguments);
		}
		
		@Override
		public void visitShapeNot(ShapeNot expr, Object... arguments) {
			this.visited.add(expr.getId());
			
			Pair<Label,Label> edge =new Pair<Label,Label>(expr.getId(),expr.getSubExpression().getId());
			set.add(new Pair<Pair<Label,Label>,Integer>(edge,-1));
			super.visitShapeNot(expr, arguments);
		}
	}
	
	
	class CollectTripleConstraintDependentOfAShape extends TripleExpressionVisitor<Set<TripleConstraint>> {
		private Set<TripleConstraint> set;

		public CollectTripleConstraintDependentOfAShape(){
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

	
	private DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge> computeDependencesGraph () {
		// Visit the schema to collect the references
		CollectGraphDependencyFromShape collector = new CollectGraphDependencyFromShape();
		for (ShapeExpr expr: shapeMap.values()) {
			if (!(collector.getVisited().contains(expr.getId()))) {
				expr.accept(collector);
			}
		}
		
		// build the graph
		GraphBuilder<Label,DefaultWeightedEdge,DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>> builder;
		builder = new GraphBuilder<Label,DefaultWeightedEdge,DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>>(new DefaultDirectedWeightedGraph<Label,DefaultWeightedEdge>(DefaultWeightedEdge.class));
		for (Label label : this.shapeMap.keySet()) {
			builder.addVertex(label);
		}
		for (Pair<Pair<Label,Label>,Integer> weightededge : collector.getResult()) {
			double weight = weightededge.two;
			Pair<Label,Label> edge = weightededge.one;
			builder.addEdge(edge.one, edge.two,weight);
		}
		return builder.build();
	}
	
	
	/** The set of shape labels on a given stratum.
	 * 
	 * @param i
	 * @return
	 */
	public Set<Label> getStratum (int i) {
		if (i < 0 && i >= this.getStratification().size())
			throw new IllegalArgumentException("Stratum " + i + " does not exist");
		return Collections.unmodifiableSet(this.getStratification().get(i));
	}

	/** The number of stratums of the schema.
	 * 
	 * @return
	 */
	public int getNbStratums () {
		return this.getStratification().size();
	}

	/** The stratum of a given shape label.
	 * 
	 * @param label
	 * @return
	 */
	public int hasStratum (Label label) {
		for (int i = 0; i < getNbStratums(); i++)
			if (getStratum(i).contains(label))
				return i;
		throw new IllegalArgumentException("Unknown shape label: " + label);
	}
	
	public Map<Integer,Set<Label>> getStratification() {
		return this.stratification;
	}

}

