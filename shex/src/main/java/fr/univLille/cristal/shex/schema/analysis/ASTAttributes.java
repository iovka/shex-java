package fr.univLille.cristal.shex.schema.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.NonRefTripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;

public class ASTAttributes {
	
	/** Computes a map that contains the rules of the schema as well as additional rules for 
	 * all anonymous shape expressions.
	 * All triple constraints with anonymous shape expressions are set with the {@link #SHAPE_REF_ON_TRIPLE_CONSTRAINT} attribute.
	 */
	public static final ASTAttribute<ShexSchema, Map<ShapeExprLabel, ShapeExpr>> 
		ALL_RULES_ON_SCHEMA = new ASTAttribute<ShexSchema, Map<ShapeExprLabel,ShapeExpr>>() 
	{

		@Override
		public Map<ShapeExprLabel, ShapeExpr> apply(ShexSchema t) {
			// TODO Collect all triple constraints
			// For all triple constraints that are not references, set the SHAPE_REF_ON_TRIPLE_CONSTRAINT attribute
			throw new UnsupportedOperationException("Not yet implemented.");
		}
		
		@Override
		public String toString() {
			return "ALL_RULES_ON_SCHEMA";
		}
				
	};


	public static final ASTAttribute<TripleConstraint, ShapeExprRef> 
		SHAPE_REF_ON_TRIPLE_CONSTRAINT = new ASTAttribute<TripleConstraint, ShapeExprRef>() 
	{

		@Override
		public ShapeExprRef apply(TripleConstraint t) {
			if (t.getShapeExpr() instanceof ShapeExprRef)
				return (ShapeExprRef) (t.getShapeExpr());
			else {
				ShapeExprLabel label = t.getShapeExpr().getId();
				if (label == null) {
					label = new ShapeExprLabel(SimpleValueFactory.getInstance().createBNode());
					t.getShapeExpr().setId(label);
				}
				return new ShapeExprRef(label);
			}
		}
		@Override
		public String toString() {
			return "SHAPE_REF_ON_TRIPLE_CONSTRAINT";
		}
	};


	public static final ASTAttribute<Shape, Boolean>
		HAS_INVERSE_PROPERTIES_ON_SHAPE = new ASTAttribute<Shape, Boolean>() 
	{
		
		@Override
		public Boolean apply(Shape shape) {
			Set<TripleConstraint> set = SchemaRulesStaticAnalysis.collectTripleConstraints(shape.getTripleExpression());
			return set.stream().map(tc -> tc.getProperty()).anyMatch(prop -> ! prop.isForward());
		}
		
		@Override
		public String toString() {
			return "HAS_INVERSE_PROPERTIES_ON_SHAPE";
		}
	};

	public static final ASTAttribute<Shape, Set<TCProperty>>
		MENTIONED_PROPERTIES_ON_SHAPE = new ASTAttribute<Shape, Set<TCProperty>>() 
	{

		@Override
		public Set<TCProperty> apply(Shape shape) {
			Set<TripleConstraint> set = SchemaRulesStaticAnalysis.collectTripleConstraints(shape.getTripleExpression());
			return set.stream().map(tc -> tc.getProperty()).collect(Collectors.toSet());
		}
		
		@Override
		public String toString() {
			return "MENTIONED_PROPERTIES_ON_SHAPE";
		}
	};
	
	public static final ASTAttribute<TripleExpr, List<TripleConstraint>>
		LIST_TRIPLE_CONSTRAINTS_ON_TRIPLE_EXPR = new ASTAttribute<TripleExpr, List<TripleConstraint>>() 
	{
		
		@Override
		public List<TripleConstraint> apply(TripleExpr t) {
			return ((Map<TripleExpr, List<TripleConstraint>>) getContext()).get(t);
		}
		
		@Override
		public String toString() {
			return "LIST_TRIPLE_CONSTRAINTS_ON_TRIPLE_EXPR";
		}
	};
	

	public static final ASTAttribute<Shape, TripleExpr> 
		TRIPLE_EXPRESSION_FOR_VALIDATION_ON_SHAPE = new ASTAttribute<Shape, TripleExpr>() 
	{
		@Override
		public TripleExpr apply(Shape shape) {
			
			Map<ShapeExprLabel, ShapeExpr> rules = (Map<ShapeExprLabel, ShapeExpr>) context[0];
			
			TripleExpr texpr;
			
			ComputeUnfoldedArbitraryRepetitionsVisitor visitor = new ComputeUnfoldedArbitraryRepetitionsVisitor();
			shape.getTripleExpression().accept(visitor);
			texpr = visitor.getResult();

			Set<TripleConstraint> extraTripleConstraints = extraTripleConstraints(shape.getTripleExpression(), shape.getExtraProperties(), shape.isClosed(), rules);
			
			if (extraTripleConstraints.isEmpty())
				return texpr;
			else {
				List<NonRefTripleExpr> subExpressions = new ArrayList<>();
				subExpressions.addAll(extraTripleConstraints);
				subExpressions.add((NonRefTripleExpr) texpr);
				return new EachOf(subExpressions);
			} 
		}
		
		@Override
		public String toString() {
			return "TRIPLE_EXPRESSION_FOR_VALIDATION_ON_SHAPE";
		}
		
		private Set<TripleConstraint> extraTripleConstraints (NonRefTripleExpr expr, Set<TCProperty> extraProps, boolean isClosed, Map<ShapeExprLabel, ShapeExpr> rules) {
			
			List<TripleConstraint> tripleConstraints;
			CollectTripleConstraintsVisitor visitor = new CollectTripleConstraintsVisitor();
			expr.accept(visitor);
			tripleConstraints = visitor.getResult();
			
			Set<TripleConstraint> additionalTripleConstraints = new HashSet<>();
					
			Iterator<TCProperty> extraPropsIterator = extraProps.iterator();
			while (extraPropsIterator.hasNext()) {
				TCProperty extraProp = extraPropsIterator.next();
				List<ShapeExpr> refsWithThisPropNegated = 
						tripleConstraints.stream()
						.filter(tc -> tc.getProperty().equals(extraProp))
						.map(tc -> new ShapeNot(tc.getShapeExpr()))
						.collect(Collectors.toList());

				if (refsWithThisPropNegated.isEmpty() && !isClosed) {
					extraPropsIterator.remove();
				}
				else if (refsWithThisPropNegated.isEmpty() && isClosed) {
					additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, new ShapeExprRef(JsonldParser.SL_ALL))); 
				}
				else {
					ShapeExpr shapeExpression;
					if (refsWithThisPropNegated.size() == 1)
						shapeExpression = refsWithThisPropNegated.get(0);
					else
						shapeExpression = new ShapeAnd(refsWithThisPropNegated);
					
					ShapeExprLabel label = createFreshExtraLabel(extraProp, rules);
					ShapeExprRef ref = new ShapeExprRef(label);
					rules.put(label, shapeExpression);
					additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, ref));
				}
			}
			return additionalTripleConstraints;
		}
		
		private ShapeExprLabel createFreshExtraLabel(TCProperty extraProp, Map<ShapeExprLabel, ShapeExpr> rules) {
			String refLabelBase = "EXTRA#" + extraProp.getIri().toString();
			ShapeExprLabel freshLabel = new ShapeExprLabel(SimpleValueFactory.getInstance().createBNode(refLabelBase));
			if (! rules.containsKey(freshLabel))
				return freshLabel;
			
			Random rand = new Random();
			do {
				int i = rand.nextInt(100);
				freshLabel = new ShapeExprLabel(SimpleValueFactory.getInstance().createBNode(refLabelBase + "" + i));
				
			} while (rules.containsKey(freshLabel));
			return freshLabel;
		}
		
	};

	
}
/*
TRIPLE_EXPRESSION_FOR_VALIDATION_ON_SHAPE(Shape.class, TripleExpr.class, null),

*/