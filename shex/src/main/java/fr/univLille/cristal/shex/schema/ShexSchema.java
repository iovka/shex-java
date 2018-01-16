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

package fr.univLille.cristal.shex.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;

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
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;
import fr.univLille.cristal.shex.schema.analysis.ShapeExpressionVisitor;
import fr.univLille.cristal.shex.schema.analysis.TripleExpressionVisitor;
import fr.univLille.cristal.shex.util.Pair;

/** A ShEx schema.
 * 
 * An instance of this class is represents a well-defined schema, that is, all shape labels are defined, and the set of rules is stratified.
 * The stratification is a most refined stratification.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class ShexSchema extends HashMap<ShapeExprLabel, ShapeExpr> implements Map<ShapeExprLabel, ShapeExpr> {
	private boolean finalized = false;
	private List<Set<ShapeExprLabel>> stratification = null;
	private Map<ShapeExprLabel,ShapeExpr> shapeMap;
	private Map<TripleExprLabel,TripleExpr> tripleMap;

	public void finalize () {
		Set<ShapeExpr> allShapes = SchemaRulesStaticAnalysis.collectAllShapes(this);
		shapeMap = new HashMap<ShapeExprLabel,ShapeExpr>();
		for(ShapeExpr shexp:allShapes) {
			shapeMap.put(shexp.getId(),shexp);
			//System.out.println(shexp.getId()+" : "+shexp.getClass());
		}
		
		// Check shape references
		for (Map.Entry<ShapeExprLabel,ShapeExpr> entry:shapeMap.entrySet()){
			if (entry.getValue() instanceof ShapeExprRef) {
				ShapeExprRef ref = (ShapeExprRef) entry.getValue();
				if (shapeMap.containsKey(ref.getLabel())) {
					ref.setShapeDefinition(shapeMap.get(ref.getLabel()));
				}else {
					throw new IllegalArgumentException("Undefined shape labels: " + ref.getLabel());
				}
			}
		}
		
		Set<TripleExpr> allTriples = SchemaRulesStaticAnalysis.collectAllTriples(this);
		tripleMap = new HashMap<TripleExprLabel,TripleExpr>();
		for (TripleExpr tcexp:allTriples) {
			tripleMap.put(tcexp.getId(),tcexp);
			//System.out.println(tcexp.getId()+" : "+tcexp.getClass());
		}
		
		// Check triple references
		for (Map.Entry<TripleExprLabel,TripleExpr> entry:tripleMap.entrySet()){
			if (entry.getValue() instanceof TripleExprRef) {
				TripleExprRef ref = (TripleExprRef) entry.getValue();
				if (tripleMap.containsKey(ref.getLabel())) {
					ref.setTripleDefinition(tripleMap.get(ref.getLabel()));
				}else {
					throw new IllegalArgumentException("Undefined triple labels: " + ref.getLabel());
				}
			}
		}
		
		DefaultDirectedGraph<Label,DefaultEdge> referencesGraph = this.computeReferencesGraph();
		TarjanSimpleCycles<Label,DefaultEdge> cyclesFinder = new TarjanSimpleCycles<Label,DefaultEdge>(referencesGraph);
		List<List<Label>> allcycles = cyclesFinder.findSimpleCycles();
		
		if (! allcycles.isEmpty())
			throw new IllegalArgumentException("Cyclic dependency of shape refences: " + allcycles);
		
//		// Check that there are no cyclic shape ref dependencies
//		List<List<ShapeExprLabel>> cyclicShapeRefDependencies = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(this);
//		if (! cyclicShapeRefDependencies.isEmpty())
//			throw new IllegalArgumentException("Cyclic dependency of shape refences: " + cyclicShapeRefDependencies.get(0));
//		
//		/*
//		// Enrich all shape references with the corresponding shape definition
//		Set<ShapeExprRef> shapeRefs = SchemaRulesStaticAnalysis.collectAllShapeRefs(this.values());
//		for (ShapeExprRef ref : shapeRefs)
//			ref.setShapeDefinition(rules.get(ref.getLabel()));
//		 */
//		
//		
//		Map<ShapeExprLabel, ShapeExpr> additionalRules = new HashMap<>(); 
//		//InstrumentationAdditionalShapeDefinitions.getInstance().apply(this, additionalRules);
//		Map<ShapeExprLabel, ShapeExpr> allRules = new HashMap<>();
//		allRules.putAll(this);
//		allRules.putAll(additionalRules);
//		
//		List<Set<ShapeExprLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(allRules);
//		if (stratification == null)
//			throw new IllegalArgumentException("The set of rules is not stratified.");
//		else
//			setStratification(stratification);
//		
//		this.finalized = true;
	}
	
	@Override
	public ShapeExpr put(ShapeExprLabel key, ShapeExpr value) {
		if (finalized)
			throw new IllegalStateException("A finalized schema cannot be modified");
		return super.put(key,value);
	}

	@Override
	public void putAll(Map<? extends ShapeExprLabel,? extends ShapeExpr> m) {
		if (finalized)
			throw new IllegalStateException("A finalized schema cannot be modified");
		super.putAll(m);
	}
	
	@Override
	public ShapeExpr remove(Object key) {
		if (finalized)
			throw new IllegalStateException("A finalized schema cannot be modified");
		return super.remove(key);
	}
	
	@Override
	public String toString() {
		return super.toString();
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
			//set.add(new Pair<Label,Label>(expr.getId(),expr.getTripleExpression().getId()));
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
			//set.add(new Pair<Label,Label>(tc.getId(),tc.getShapeExpr().getId()));
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
		for (ShapeExpr expr: this.values()) {
			expr.accept(collector);
		}
		
		// build the graph
		GraphBuilder<Label,DefaultEdge,DefaultDirectedGraph<Label,DefaultEdge>> builder;
		builder = new GraphBuilder<Label,DefaultEdge,DefaultDirectedGraph<Label,DefaultEdge>>(new DefaultDirectedGraph<Label,DefaultEdge>(DefaultEdge.class));

		for (Label label : this.shapeMap.keySet()) {
			builder.addVertex(label);
			//System.out.println(label+":"+shapeMap.get(label).getClass());
		}
		for (Label label : this.tripleMap.keySet()) {
			builder.addVertex(label);
			//System.out.println(label+":"+tripleMap.get(label).getClass());
		}

		for (Pair<Label,Label> edge : collector.getResult()) {
			builder.addEdge(edge.one, edge.two);
			//System.out.println(edge);
		}
		return builder.build();
	}
	
	// -------------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------------

	/** The set of shape labels on a given stratum.
	 * 
	 * @param i
	 * @return
	 */
	public Set<ShapeExprLabel> getStratum (int i) {
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
	public int hasStratum (ShapeExprLabel label) {
		for (int i = 0; i < getNbStratums(); i++)
			if (getStratum(i).contains(label))
				return i;
		throw new IllegalArgumentException("Unknown shape label: " + label);
	}
	
	public List<Set<ShapeExprLabel>> getStratification() {
		return this.stratification;
	}

	private void setStratification(List<Set<ShapeExprLabel>> stratification) {
		if (this.stratification != null)
			throw new IllegalStateException();
		
		List<Set<ShapeExprLabel>> tmp = new ArrayList<>();
		for (Set<ShapeExprLabel> strat : stratification) {
			tmp.add(Collections.unmodifiableSet(strat));
		}
		this.stratification = Collections.unmodifiableList(tmp);		
	}

}

