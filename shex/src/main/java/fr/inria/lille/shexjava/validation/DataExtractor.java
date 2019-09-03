package fr.inria.lille.shexjava.validation;

import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

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
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.Pair;

public class DataExtractor{
	public void extractValidPart(ShexSchema schema, Typing t, MatchingCollector mColl, Graph resultGraph) {
		HashSet<Pair<RDFTerm,Label>> visited = new HashSet<>();
		VisitorValidPart visitor = new VisitorValidPart(schema,t,mColl,resultGraph,visited);
		for (Pair<RDFTerm, Label> key:t.getStatusMap().keySet()) {
			if (!visited.contains(key) && t.getStatus(key.one, key.two).equals(Status.CONFORMANT)) {
				visitor.setCurrentNode(key.one);
				schema.getShapeExprsMap().get(key.two).accept(visitor);
			}
		}
	}
	
	
	class VisitorValidPart extends ShapeExpressionVisitor<Object> {
		protected ShexSchema schema;
		protected Typing t;
		protected MatchingCollector mColl;
		protected Graph resultGraph;
		protected HashSet<Pair<RDFTerm,Label>> visited;
		protected RDFTerm currentNode;
				
		public VisitorValidPart(ShexSchema schema, Typing t, MatchingCollector mColl, Graph resultGraph,
				HashSet<Pair<RDFTerm, Label>> visited) {
			super();
			this.schema = schema;
			this.t = t;
			this.mColl = mColl;
			this.resultGraph = resultGraph;
			this.visited = visited;
		}


		public void setCurrentNode(RDFTerm currentNode) {
			this.currentNode = currentNode;
		}


		@Override
		public Object getResult() {
			return null;
		}

		
		public void visitShapeAnd (ShapeAnd expr, Object ... arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, expr.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);
			
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				key = new Pair<RDFTerm,Label>(currentNode, subExpr.getId());
				if (!visited.contains(key)) {
					subExpr.accept(this, arguments);
				}
			}
		}

		public void visitShapeOr (ShapeOr expr, Object ... arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, expr.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);
			
			for (ShapeExpr subExpr: expr.getSubExpressions()) {
				key = new Pair<RDFTerm,Label>(currentNode, subExpr.getId());
				if (!visited.contains(key)) {
					subExpr.accept(this, arguments);
				}
			}
		}
		
		public void visitShapeNot (ShapeNot expr, Object ...arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, expr.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);
			expr.getSubExpression().accept(this, arguments);
		}

		@Override
		public void visitShape(Shape expr, Object... arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, expr.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);
			if (t.getStatus(key.one, key.two).equals(Status.CONFORMANT)) {
				LocalMatching matching = mColl.getMatching(key.one,key.two);
				for (Entry<Triple, Label> match:matching.getMatching().entrySet()) {
					resultGraph.add(match.getKey());
					if (match.getKey().getObject().equals(key.one))
						currentNode = match.getKey().getSubject();
					else
						currentNode = match.getKey().getObject();		
					TripleConstraint tc = (TripleConstraint) schema.getTripleExprsMap().get(match.getValue());
					tc.getShapeExpr().accept(this);
					currentNode = key.one;
				}
			}
		}

		@Override
		public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, expr.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);			
		}

		@Override
		public void visitShapeExprRef(ShapeExprRef shapeRef, Object[] arguments) {
			Pair<RDFTerm, Label> key = new Pair<RDFTerm,Label>(currentNode, shapeRef.getId());
			if (visited.contains(key))
				return ;
			visited.add(key);
			shapeRef.getShapeDefinition().accept(this);
		}
		
	}
}
