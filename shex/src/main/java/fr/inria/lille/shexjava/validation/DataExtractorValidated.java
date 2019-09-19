package fr.inria.lille.shexjava.validation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.GlobalFactory;
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
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;

public class DataExtractorValidated{
	protected ShexSchema schema;
	protected Graph inputGraph;
	protected RecursiveValidationWithMemorization validation;
	protected MatchingCollector mColl;
	protected DynamicCollectorOfTripleConstraints collectorTC;
	protected SORBEGenerator sorbeGenerator;

	
	public DataExtractorValidated(ShexSchema schema, Graph inputGraph) {
		super();
		this.schema = schema;
		this.inputGraph = inputGraph;
		validation = new RecursiveValidationWithMemorization(schema, inputGraph);
		mColl = new MatchingCollector();
		validation.addMatchingObserver(mColl);
		this.collectorTC = new DynamicCollectorOfTripleConstraints();
		this.sorbeGenerator = new SORBEGenerator(schema.getRdfFactory());
	}
	
	
	public DataView extractValidatedPart(BaseShapeMap shapeMap) {
		Graph graph = GlobalFactory.RDFFactory.createGraph();
		return extractValidatedPart(shapeMap,graph);
	}
	

	public DataView extractValidatedPart(BaseShapeMap shapeMap, Graph resultGraph) {
		validation.validate(shapeMap);
		VisitorValidatedPart visitor = new VisitorValidatedPart(schema,validation.getTyping(),mColl,resultGraph);

		for (ShapeAssociation sa:shapeMap.getAssociations()) {
			Label seLabel = sa.getShapeSelector().apply(schema);
			for(RDFTerm node:sa.getNodeSelector().apply(inputGraph)) {
				visitor.setCurrentNode(node);
				schema.getShapeExprsMap().get(seLabel).accept(visitor);
			}
		}
		
		return new DataView(resultGraph, visitor.getResTyping(), visitor.getResMColl());
	}
	
	
	class VisitorValidatedPart extends ShapeExpressionVisitor<Object> {
		protected ShexSchema schema;
		protected Typing t;
		protected MatchingCollector mColl;
		protected Graph resultGraph;
		protected RDFTerm currentNode;
		protected TypingForValidation resTyping;
		protected MatchingCollector resMColl;
				
		public VisitorValidatedPart(ShexSchema schema, Typing t, MatchingCollector mColl, Graph resultGraph) {
			super();
			this.schema = schema;
			this.t = t;
			this.mColl = mColl;
			this.resultGraph = resultGraph;
			this.resTyping = new TypingForValidation();
			this.resMColl = new MatchingCollector();
		}


		public void setCurrentNode(RDFTerm currentNode) {
			this.currentNode = currentNode;
		}


		public TypingForValidation getResTyping() {
			return resTyping;
		}


		public MatchingCollector getResMColl() {
			return resMColl;
		}


		@Override
		public Object getResult() {
			return null;
		}

		
		public void visitShapeAnd (ShapeAnd expr, Object ... arguments) {
			if (t.getStatus(currentNode, expr.getId()).equals(Status.NOTCOMPUTED))
				validation.validate(currentNode, expr.getId());

			resTyping.setStatus(currentNode, expr.getId(), t.getStatus(currentNode, expr.getId()));
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				if (t.getStatus(currentNode, subExpr.getId()).equals(Status.NOTCOMPUTED))
					validation.validate(currentNode, subExpr.getId());
				
				resTyping.setStatus(currentNode, subExpr.getId(), validation.getTyping().getStatus(currentNode, subExpr.getId()));
				subExpr.accept(this, arguments);
			}
		}
		

		public void visitShapeOr (ShapeOr expr, Object ... arguments) {
			if (t.getStatus(currentNode, expr.getId()).equals(Status.NOTCOMPUTED))
				validation.validate(currentNode, expr.getId());

			resTyping.setStatus(currentNode, expr.getId(), t.getStatus(currentNode, expr.getId()));
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				if (t.getStatus(currentNode, subExpr.getId()).equals(Status.NOTCOMPUTED))
					validation.validate(currentNode, subExpr.getId());
				
				resTyping.setStatus(currentNode, subExpr.getId(), validation.getTyping().getStatus(currentNode, subExpr.getId()));
				subExpr.accept(this, arguments);
			}
		}
		
		
		public void visitShapeNot (ShapeNot expr, Object ...arguments) {
			if (t.getStatus(currentNode, expr.getId()).equals(Status.NOTCOMPUTED))
				validation.validate(currentNode, expr.getId());
			
			resTyping.setStatus(currentNode, expr.getId(), t.getStatus(currentNode, expr.getId()));
			expr.getSubExpression().accept(this);
		}
		

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			if (t.getStatus(currentNode, expr.getId()).equals(Status.NOTCOMPUTED))
				validation.validate(currentNode, expr.getId());
			
			if (t.getStatus(currentNode, expr.getId()).equals(Status.CONFORMANT)) {
				RDFTerm baseNode = currentNode;
				LocalMatching matching = mColl.getMatching(currentNode, expr.getId());

				resTyping.setStatus(currentNode, expr.getId(), Status.CONFORMANT);
				resMColl.updateMatching(currentNode, expr.getId(), matching);
				
				for (Entry<Triple, Label> match:matching.getMatching().entrySet()) {
					resultGraph.add(match.getKey());
					if (match.getKey().getObject().equals(baseNode))
						currentNode = match.getKey().getSubject();
					else
						currentNode = match.getKey().getObject();		
					TripleConstraint tc = (TripleConstraint) schema.getTripleExprsMap().get(match.getValue());
					tc.getShapeExpr().accept(this);
					currentNode = baseNode;
				}
			} 
			if (t.getStatus(currentNode, expr.getId()).equals(Status.NONCONFORMANT)) {
				TripleExpr tripleExpression = sorbeGenerator.getSORBETripleExpr(expr);

				List<TripleConstraint> constraints = collectorTC.getTCs(tripleExpression);	
				List<Triple> neighbourhood = ValidationUtils.getMatchableNeighbourhood(inputGraph, currentNode, constraints, expr.isClosed());

				// Match using only predicate and recursive test. The following lines is the only big difference with refine validation. 
				TypingForValidation localTyping = new TypingForValidation();
				
				PreMatching preMatching = ValidationUtils.computePreMatching(currentNode, neighbourhood, constraints, expr.getExtraProperties(), ValidationUtils.getPredicateOnlyMatcher());
				Map<Triple,List<TripleConstraint>> matchingTC1 = preMatching.getPreMatching();
					
				for(Entry<Triple,List<TripleConstraint>> entry:matchingTC1.entrySet()) {		
					for (TripleConstraint tc:entry.getValue()) {
						RDFTerm destNode = entry.getKey().getObject();
						if (!tc.getProperty().isForward())
							destNode = entry.getKey().getSubject();

						if (t.getStatus(destNode, tc.getShapeExpr().getId()).equals(Status.NOTCOMPUTED))
							validation.validate(destNode, tc.getShapeExpr().getId());
								
						resTyping.setStatus(currentNode, expr.getId(), t.getStatus(destNode, tc.getShapeExpr().getId()));
						resultGraph.add(entry.getKey());
					}			
				}
			}
		}

		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			resTyping.setStatus(currentNode, expr.getId(), validation.getTyping().getStatus(currentNode, expr.getId()));
		}
		

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
			if (t.getStatus(currentNode, shapeRef.getId()).equals(Status.NOTCOMPUTED))
				validation.validate(currentNode, shapeRef.getId());
			
			resTyping.setStatus(currentNode, shapeRef.getId(), t.getStatus(currentNode, shapeRef.getId()));
			shapeRef.getShapeDefinition().accept(this);
		}
		
	}
}
