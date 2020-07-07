package fr.inria.lille.shexjava.validation;

import java.util.Map.Entry;

import fr.inria.lille.shexjava.schema.abstrsynt.*;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;

public class DataExtractorConformant{
	protected ShexSchema schema;
	protected Graph inputGraph;
	protected RecursiveValidationWithMemorization validation;
	protected MatchingCollector mColl;
	
	public DataExtractorConformant(ShexSchema schema, Graph inputGraph) {
		super();
		this.schema = schema;
		this.inputGraph = inputGraph;
		validation = new RecursiveValidationWithMemorization(schema, inputGraph);
		mColl = new MatchingCollector();
		validation.addMatchingObserver(mColl);
	}
	
	
	public DataView extractConformantPart(BaseShapeMap shapeMap) {
		Graph graph = GlobalFactory.RDFFactory.createGraph();
		return extractConformantPart(shapeMap,graph);
	}
	

	public DataView extractConformantPart(BaseShapeMap shapeMap, Graph resultGraph) {
		validation.validate(shapeMap);
		VisitorValidPart visitor = new VisitorValidPart(schema,validation.getTyping(),mColl,resultGraph);

		for (ShapeAssociation sa:shapeMap.getAssociations()) {
			Label seLabel = sa.getShapeSelector().apply(schema);
			for(RDFTerm node:sa.getNodeSelector().apply(inputGraph)) {
				if (validation.getTyping().getStatus(node, seLabel).equals(Status.CONFORMANT)) {
					visitor.setCurrentNode(node);
					schema.getShapeExprsMap().get(seLabel).accept(visitor);
				}
			}
		}
		
		return new DataView(resultGraph, visitor.getResTyping(), visitor.getResMColl());
	}
	
	
	class VisitorValidPart extends ShapeExpressionVisitor<Object> {
		protected ShexSchema schema;
		protected Typing t;
		protected MatchingCollector mColl;
		protected Graph resultGraph;
		protected RDFTerm currentNode;
		protected TypingForValidation resTyping;
		protected MatchingCollector resMColl;
				
		public VisitorValidPart(ShexSchema schema, Typing t, MatchingCollector mColl, Graph resultGraph) {
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
			if (validation.getTyping().getStatus(currentNode, expr.getId()).equals(Status.CONFORMANT)) {
				resTyping.setStatus(currentNode, expr.getId(), Status.CONFORMANT);
				for (ShapeExpr subExpr: expr.getSubExpressions()) {
					resTyping.setStatus(currentNode, subExpr.getId(), validation.getTyping().getStatus(currentNode, subExpr.getId()));
					subExpr.accept(this, arguments);
				}
			}
		}
		

		public void visitShapeOr (ShapeOr expr, Object ... arguments) {
			if (validation.getTyping().getStatus(currentNode, expr.getId()).equals(Status.CONFORMANT)) {
				resTyping.setStatus(currentNode, expr.getId(), Status.CONFORMANT);
				for (ShapeExpr subExpr: expr.getSubExpressions()) {
					resTyping.setStatus(currentNode, subExpr.getId(), validation.getTyping().getStatus(currentNode, subExpr.getId()));
					if (validation.getTyping().getStatus(currentNode, subExpr.getId()).equals(Status.CONFORMANT)) 
						subExpr.accept(this, arguments);
				}
			}
		}
		
		
		public void visitShapeNot (ShapeNot expr, Object ...arguments) {
			System.err.println("Negation "+expr.toPrettyString()+" found during the extraction of the conformant part.");
		}
		

		@Override
		public void visitShape(Shape expr, Object... arguments) {
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
		}

		
		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			resTyping.setStatus(currentNode, expr.getId(), validation.getTyping().getStatus(currentNode, expr.getId()));
		}
		

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
			shapeRef.getShapeDefinition().accept(this);
			if (validation.getTyping().getStatus(currentNode, shapeRef.getId()).equals(Status.CONFORMANT)) {
				resTyping.setStatus(currentNode, shapeRef.getId(), Status.CONFORMANT);
				shapeRef.getShapeDefinition().accept(this);
			}
		}

	}
}
