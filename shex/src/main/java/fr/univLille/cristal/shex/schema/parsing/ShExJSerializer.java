package fr.univLille.cristal.shex.schema.parsing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rdf4j.model.IRI;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.Annotation;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.util.Interval;


public class ShExJSerializer {

	public Object ToJson(Map<ShapeExprLabel,ShapeExpr> rules) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("@context","http://www.w3.org/ns/shex.jsonld");
		result.put("type", "Schema");
		List<Object> shapes = new ArrayList<Object>();
		for (ShapeExpr shape:rules.values())
			shapes.add(convertShapeExpr(shape));
		result.put("shapes", shapes);
		return result;
	}
	
	//--------------------------------------------------
	// Shape conversion
	//--------------------------------------------------

	private Object convertShapeExpr(ShapeExpr shape) {
		if (shape instanceof ShapeAnd)
			return convertShapeAnd((ShapeAnd) shape);
		if (shape instanceof ShapeOr)
			return convertShapeOr((ShapeOr) shape);
		if (shape instanceof ShapeNot)
			return convertShapeNot((ShapeNot) shape);
		if (shape instanceof ShapeExprRef)
			return convertShapeExprRef((ShapeExprRef) shape);
		if (shape instanceof Shape)
			return convertShape((Shape) shape);
		if (shape instanceof NodeConstraint)
			return convertNodeConstraint((NodeConstraint) shape);
		return null;
	}
	
	private Object convertShapeAnd(ShapeAnd shape) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! shape.getId().isGenerated())
			result.put("id", shape.getId().stringValue());
		result.put("type", "ShapeAnd");
		List<Object> subExprs = new ArrayList<Object>();
		for (ShapeExpr sub:shape.getSubExpressions())
			subExprs.add(convertShapeExpr(sub));
		result.put("shapeExprs", subExprs);	
		return result;
	}
	
	private Object convertShapeOr(ShapeOr shape) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! shape.getId().isGenerated())
			result.put("id", shape.getId().stringValue());
		result.put("type", "ShapeOr");
		List<Object> subExprs = new ArrayList<Object>();
		for (ShapeExpr sub:shape.getSubExpressions())
			subExprs.add(convertShapeExpr(sub));
		result.put("shapeExprs", subExprs);	
		return result;
	}
	
	private Object convertShapeNot(ShapeNot shape) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! shape.getId().isGenerated())
			result.put("id", shape.getId().stringValue());
		result.put("type", "ShapeNot");
		result.put("shapeExpr", convertShapeExpr(shape.getSubExpression()));	
		return result;
	}
	
	private Object convertShapeExprRef(ShapeExprRef shape) {
		return shape.getLabel().stringValue();
	}
	
	private Object convertShape(Shape shape) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! shape.getId().isGenerated())
			result.put("id", shape.getId().stringValue());
		result.put("type", "Shape");
		if (shape.isClosed())
			result.put("closed", "true");
		if (shape.getExtraProperties().size()>0) {
			List<Object> extra = new ArrayList<Object>();
			for (TCProperty tcp:shape.getExtraProperties()) {
				extra.add(tcp.getIri().stringValue());
			}
			result.put("extra", extra);
		}
		if (!(shape.getTripleExpression() instanceof EmptyTripleExpression))
			result.put("expression", convertTripleExpr(shape.getTripleExpression()));
		if (shape.getAnnotations()!=null) {
			result.put("annotations", convertAnnotations(shape.getAnnotations()));
		}
		return result;
	}

	private Object convertNodeConstraint(NodeConstraint shape) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! shape.getId().isGenerated())
			result.put("id", shape.getId().stringValue());
		result.put("type", "NodeConstraint");
		
		ConjunctiveSetOfNodes constraints = (ConjunctiveSetOfNodes) shape.getSetOfNodes();
		for (SetOfNodes constraint:constraints.getConjuncts()) {
			if (constraint.equals(SetOfNodes.Blank))
				result.put("nodeKind", "bnode");
			if (constraint.equals(SetOfNodes.AllIRI))
				result.put("nodeKind", "iri");
			if (constraint.equals(SetOfNodes.AllLiteral))
				result.put("nodeKind", "literal");
			if (constraint.equals(SetOfNodes.AllNonLiteral))
				result.put("nodeKind", "nonliteral");
			if (constraint instanceof DatatypeSetOfNodes)
				result.put("datatype",((DatatypeSetOfNodes) constraint).getDatatypeIri().stringValue());
			if (constraint instanceof NumericFacetSetOfNodes)
				convertNumericFacet((NumericFacetSetOfNodes) constraint, result);
			if (constraint instanceof StringFacetSetOfNodes)
				convertStringFacet((StringFacetSetOfNodes) constraint, result);
				
		}
		
		return result;
	}
	
	//--------------------------------------------------
	// Constraint conversion
	//--------------------------------------------------
	
	public void convertNumericFacet(NumericFacetSetOfNodes facet, Map<String,Object> res) {
		if (facet.getMinincl() != null)
			res.put("mininclusive", facet.getMinincl());
		if (facet.getMinexcl() != null)
			res.put("minexclusive", facet.getMinexcl());
		if (facet.getMaxincl() != null)
			res.put("maxinclusive", facet.getMaxincl());
		if (facet.getMaxexcl() != null)
			res.put("maxexclusive", facet.getMaxexcl());
		if (facet.getTotalDigits() != null)
			res.put("totaldigits", facet.getTotalDigits());
		if (facet.getFractionDigits() != null)
			res.put("fractiondigits", facet.getFractionDigits());
	}
		
	//convertStringFacet
	public Object convertStringFacet(StringFacetSetOfNodes facet, Map<String,Object> res) {
		return null;
	}
	
	//--------------------------------------------------
	// Triple conversion
	//--------------------------------------------------

	private Object convertTripleExpr(TripleExpr triple) {
		if (triple instanceof EachOf)
			return convertEachOf((EachOf) triple);
		if (triple instanceof OneOf)
			return convertOneOf((OneOf) triple);
		if (triple instanceof TripleExprRef)
			return convertTripleExprRef((TripleExprRef) triple);
		if (triple instanceof RepeatedTripleExpression)
			return convertRepeatedTripleExpression((RepeatedTripleExpression) triple);
		if (triple instanceof TripleConstraint)
			return convertTripleConstraint((TripleConstraint) triple);
		return null;
	}
	
	private Object convertEachOf(EachOf triple) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! triple.getId().isGenerated())
			result.put("id", triple.getId().stringValue());
		result.put("type", "EachOf");
		List<Object> subExprs = new ArrayList<>();
		for (TripleExpr sub:triple.getSubExpressions()) {
			subExprs.add(convertTripleExpr(sub));
		}
		result.put("expressions", subExprs);
		if (triple.getAnnotations()!=null) {
			result.put("annotations", convertAnnotations(triple.getAnnotations()));
		}
		return result;		
	}

	private Object convertOneOf(OneOf triple) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! triple.getId().isGenerated())
			result.put("id", triple.getId().stringValue());
		result.put("type", "OneOf");
		List<Object> subExprs = new ArrayList<>();
		for (TripleExpr sub:triple.getSubExpressions()) {
			subExprs.add(convertTripleExpr(sub));
		}
		result.put("expressions", subExprs);
		if (triple.getAnnotations()!=null) {
			result.put("annotations", convertAnnotations(triple.getAnnotations()));
		}
		return result;	
	}

	private Object convertTripleExprRef(TripleExprRef triple) {
		return triple.getLabel().stringValue();
	}
	
	private Object convertRepeatedTripleExpression(RepeatedTripleExpression triple) {
		Map<String,Object> conv = (Map<String, Object>) convertTripleExpr(triple.getSubExpression());
		Interval card = triple.getCardinality();
		conv.put("min", card.min);
		if (card.isUnbound())
			conv.put("max", -1);
		else
			conv.put("max", card.max);		
		return conv;
	}
	
	private Object convertTripleConstraint(TripleConstraint triple) {
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		if (! triple.getId().isGenerated())
			result.put("id", triple.getId().stringValue());
		result.put("type", "TripleConstraint");
		if (! triple.getProperty().isForward())
			result.put("inverse", "true");
		result.put("predicate", triple.getProperty().getIri().stringValue());
		boolean addValueExpr = true;
		if (triple.getShapeExpr() instanceof NodeConstraint) {
			SetOfNodes tmp = ((NodeConstraint) triple.getShapeExpr()).getSetOfNodes();
			if (tmp instanceof ConjunctiveSetOfNodes) {
				ConjunctiveSetOfNodes tmp2 = (ConjunctiveSetOfNodes) tmp;
				if (tmp2.getConjuncts().size()==0)
					addValueExpr=false;
			}
		
		}
		if (addValueExpr)
			result.put("valueExpr", convertShapeExpr(triple.getShapeExpr()));
		if (triple.getAnnotations()!=null) 
			result.put("annotations", convertAnnotations(triple.getAnnotations()));
		return result;
	}
	
	//--------------------------------------------------
	// Utils conversion
	//--------------------------------------------------

	private Object convertAnnotations(List<Annotation> annotations) {
		List<Object> result = new ArrayList<Object>();
		for (Annotation ann:annotations){
			Map<String,Object> tmp = new LinkedHashMap<>();
			tmp.put("type", "Annotation");
			tmp.put("predicate", ann.getPredicate().stringValue());
			if (ann.getObjectValue() instanceof IRI)
				tmp.put("object", ann.getObjectValue().stringValue());
			else {
				Map<String,Object> tmp2 = new LinkedHashMap<>();
				tmp2.put("value", ann.getObjectValue().stringValue());
				tmp.put("object", tmp2);
			}
			result.add(tmp);			
		}
		return result;
	}
	
}
