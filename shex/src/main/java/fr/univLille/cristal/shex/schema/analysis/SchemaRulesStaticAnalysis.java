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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.DirectedWeightedGraphBuilder;

import org.jgrapht.graph.builder.GraphBuilder;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExternal;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;
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
	// COMMON Shape
	// -------------------------------------------------------------------------
	
	public static Set<ShapeExpr> collectAllShapes (Map<ShapeExprLabel, ShapeExpr> rules) { 
		Set<ShapeExpr> set = new HashSet<>();
		CollectElementsFromShape<ShapeExpr> collector = 
				new CollectElementsFromShape<ShapeExpr>((Object ast) -> (ast instanceof ShapeExpr), 
						                   set,
						                   true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}
	

	public static Set<ShapeExprRef> collectAllShapeRefs (Map<ShapeExprLabel, ShapeExpr> rules) { 
		Set<ShapeExprRef> set = new HashSet<>();
		CollectElementsFromShape<ShapeExprRef> collector = 
				new CollectElementsFromShape<ShapeExprRef>((Object ast) -> (ast instanceof ShapeExprRef), 
						                   set,
						                   true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}

	
	// -------------------------------------------------------------------------
	// COMMON Triple
	// -------------------------------------------------------------------------

	public static Set<TripleExpr> collectAllTriples (Map<ShapeExprLabel, ShapeExpr> rules) { 
		Set<TripleExpr> set = new HashSet<>();
		CollectElementsFromShape<TripleExpr> collector = 
				new CollectElementsFromShape<TripleExpr>((Object ast) -> (ast instanceof TripleExpr), 
						                   set,
						                   true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}

	public static Set<TripleExpr> collectAllTriplesRef (Map<ShapeExprLabel, ShapeExpr> rules) { 
		Set<TripleExpr> set = new HashSet<>();
		CollectElementsFromShape<TripleExpr> collector = 
				new CollectElementsFromShape<TripleExpr>((Object ast) -> (ast instanceof TripleExprRef), 
						                   set,
						                   true);
		for (ShapeExpr expr: rules.values()) {
			expr.accept(collector);
		}
		return set;
	}
	
	//---------------------------------------------------------------------------
	// Graph references computation
	//---------------------------------------------------------------------------
	
//	class CollectGraphReferencesFromShape extends ShapeExpressionVisitor<Set<Pair<ShapeExprLabel,ShapeExprLabel>>> {
//		private Set<Pair<ShapeExprLabel,ShapeExprLabel>> set;
//
//		public CollectGraphReferencesFromShape () {	
//			this.set = new HashSet<Pair<ShapeExprLabel,ShapeExprLabel>>();
//		}
//		
//		
//		@Override
//		public Set<Pair<ShapeExprLabel,ShapeExprLabel>> getResult() {
//			return set;
//		}
//
//		
//		@Override
//		public void visitShape(Shape expr, Object... arguments) {}
//
//		
//		@Override
//		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}
//
//		
//		@Override
//		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
//			set.add(new Pair<ShapeExprLabel,ShapeExprLabel>(shapeRef.getId(),shapeRef.getLabel()));
//		}
//
//		
//		@Override
//		public void visitShapeExternal(ShapeExternal shapeExt, Object[] arguments) {}
//		
//		
//		@Override
//		public void visitShapeAnd(ShapeAnd expr, Object... arguments) {
//			for (ShapeExpr subExpr: expr.getSubExpressions()) {
//				set.add(new Pair<ShapeExprLabel,ShapeExprLabel>(expr.getId(),subExpr.getId()));
//			}
//			super.visitShapeAnd(expr, arguments);
//		}
//		
//		
//		@Override
//		public void visitShapeOr(ShapeOr expr, Object... arguments) {
//			for (ShapeExpr subExpr: expr.getSubExpressions()) {
//				set.add(new Pair<ShapeExprLabel,ShapeExprLabel>(expr.getId(),subExpr.getId()));
//			}
//			super.visitShapeOr(expr, arguments);
//		}
//		
//		
//		@Override
//		public void visitShapeNot(ShapeNot expr, Object... arguments) {
//			set.add(new Pair<ShapeExprLabel,ShapeExprLabel>(expr.getId(),expr.getSubExpression().getId()));
//			super.visitShapeNot(expr, arguments);
//		}
//
//	}
//	
//	
//	
//	public static List<List<ShapeExprLabel>> computeCyclicShapeRefDependencies (Map<ShapeExprLabel, ShapeExpr> rules) {
//
//		Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>> directlyReferencedShapeLabelsCollector = new Function<ShapeExpr, List<Pair<ShapeExprLabel,Integer>>>() {
//
//			@Override
//			public List<Pair<ShapeExprLabel, Integer>> apply(ShapeExpr expr) {
//				CollectDirectlyReferencedShapeExprLabelsVisitor visitor = staticInstance.new CollectDirectlyReferencedShapeExprLabelsVisitor();
//				expr.accept(visitor);
//				return visitor.getResult();
//			}
//		};
//		DefaultDirectedGraph<ShapeExprLabel, DefaultEdge> directShapeReferenceDependency =
//				computeDependencyGraph(rules, directlyReferencedShapeLabelsCollector);
//
//		TarjanSimpleCycles<ShapeExprLabel, DefaultWeightedEdge> tarjan = new TarjanSimpleCycles<>(directShapeReferenceDependency);
//		return tarjan.findSimpleCycles();		
//	}
//

//
//	private static DefaultDirectedGraph<ShapeExprLabel,DefaultEdge> computeDependencyGraph 
//	(Map<ShapeExprLabel, ShapeExpr> rules,
//			Function<ShapeExpr, List<Pair<ShapeExprLabel, Integer>>> dependencyCollector) {
//
//		//DirectedWeightedGraphBuilder<ShapeExprLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeExprLabel, DefaultWeightedEdge>> builder2 = 
//		//		new DirectedWeightedGraphBuilder<ShapeExprLabel, DefaultWeightedEdge, DefaultDirectedWeightedGraph<ShapeExprLabel,DefaultWeightedEdge>>(new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class));
//
//		GraphBuilder<ShapeExprLabel,DefaultEdge,DefaultDirectedGraph<ShapeExprLabel,DefaultEdge>> builder;
//		builder = new GraphBuilder<ShapeExprLabel,DefaultEdge,DefaultDirectedGraph<ShapeExprLabel,DefaultEdge>>(new DefaultDirectedGraph<ShapeExprLabel,DefaultEdge>(DefaultEdge.class));
//
//		for (ShapeExprLabel label : rules.keySet()) {
//			builder.addVertex(label);
//		}
//
//		for (Map.Entry<ShapeExprLabel, ShapeExpr> def : rules.entrySet()) {
//			ShapeExprLabel label = def.getKey();
//			ShapeExpr expr = def.getValue();
//
//			for (Pair<ShapeExprLabel, Integer> pair : dependencyCollector.apply(expr))
//				builder.addEdge(label, pair.one, pair.two);
//		}
//		return builder.build();
//	}
//	
//	public static void main(String[] args) throws IOException, JsonLdError, UnsupportedOperationException, ParseException {
//		Path schemaFile = Paths.get("test","test.json");
//		JsonldParser parser = new JsonldParser(schemaFile);
//		ShexSchema schema = parser.parseSchema();
//		//Set<ShapeExprLabel> undefinedLabel = SchemaRulesStaticAnalysis.computeUndefinedShapeLabels(schema);
//		//System.out.println(undefinedLabel);
//		//System.out.println(schema.keySet());
//	}
}
