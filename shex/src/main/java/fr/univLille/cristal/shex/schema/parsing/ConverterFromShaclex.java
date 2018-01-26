package fr.univLille.cristal.shex.schema.parsing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.concrsynt.ConjunctiveSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.NumericFacetSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StringFacetSetOfNodes;


public class ConverterFromShaclex {
	private final static Pattern LANG_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*(\")(@[a-zA-Z]+)(-[a-zA-Z0-9]+)*");
	private final static Pattern DATATYPE_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*\"\\^\\^" + "([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");
	private final static Pattern IRI_PATTERN = Pattern.compile("([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");
	// TODO is this correct ?
	private static final Pattern STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*\"\\^\\^"); 

	private final static ValueFactory RDF_FACTORY = SimpleValueFactory.getInstance();
	
	// TODO: would it be preferable to have blank node identifiers ?
	private static int shapeLabelNb = 0;
	private static String SHAPE_LABEL_PREFIX = "SLGEN";
	private static int tripleLabelNb = 0;
	private static String TRIPLE_LABEL_PREFIX = "TLGEN";
	//private static String EXTRA_SHAPE_LABEL_PREFIX = "SL_EXTRA";

	private boolean semActsWarning = false;
	private boolean annotationsWarning = false;
	
	private es.weso.shex.Schema scalaSchema;
	
	
	public ConverterFromShaclex(es.weso.shex.Schema sch) {
		this.scalaSchema=sch;
	}
	
	public ShexSchema convert() throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		Map<ShapeExprLabel,ShapeExpr> rules = new HashMap<ShapeExprLabel,ShapeExpr>();

		scala.collection.immutable.List<es.weso.shex.ShapeLabel> listLabels = this.scalaSchema.labels();
		scala.collection.Iterator<es.weso.shex.ShapeLabel> llite = listLabels.iterator();
		while (llite.hasNext()) {
			es.weso.shex.ShapeLabel label = llite.next();
			es.weso.shex.ShapeExpr shapeEx = this.scalaSchema.getShape(label).get();
			
			ShapeExpr newShape = this.convertShapeExpr(shapeEx);
			ShapeExprLabel newLabel = newShape.getId();
			System.out.println(newLabel+" : "+newShape);
			rules.put(newLabel, newShape);
		}
		ShexSchema shexSchema = new ShexSchema(rules);
		return shexSchema;
	}
	
	
	// ----------------------------------------------------------------------
	// SHAPE METHODS
	// ----------------------------------------------------------------------

	private ShapeExpr convertShapeExpr(es.weso.shex.ShapeExpr shapeEx) {
		if (shapeEx instanceof es.weso.shex.ShapeAnd)
			return convertShapeAnd((es.weso.shex.ShapeAnd) shapeEx);
		if (shapeEx instanceof es.weso.shex.ShapeOr)
			return convertShapeOr((es.weso.shex.ShapeOr) shapeEx);
		if (shapeEx instanceof es.weso.shex.ShapeNot)
			return convertShapeNot((es.weso.shex.ShapeNot) shapeEx);
		if (shapeEx instanceof es.weso.shex.Shape)
			return convertShape((es.weso.shex.Shape) shapeEx);
		if (shapeEx instanceof es.weso.shex.ShapeRef)
			return convertShapeRef((es.weso.shex.ShapeRef) shapeEx);
		if (shapeEx instanceof es.weso.shex.NodeConstraint)
			return convertNodeConstraint((es.weso.shex.NodeConstraint) shapeEx);
		
		System.err.println("Unknown shape type:"+shapeEx.getClass());
		return null;
	}

	private ShapeExpr convertShapeAnd(es.weso.shex.ShapeAnd shape) {
		List<ShapeExpr> resShapeExprs = new LinkedList<ShapeExpr>();
		
		scala.collection.immutable.List<es.weso.shex.ShapeExpr> shapeExprs = shape.shapeExprs();
		scala.collection.Iterator<es.weso.shex.ShapeExpr> llite = shapeExprs.iterator();
		while (llite.hasNext()) {
			resShapeExprs.add(convertShapeExpr(llite.next()));
		}
		
		ShapeAnd result = new ShapeAnd(resShapeExprs);
		setShapeId(result, shape);
		return result;
	}
	
	private ShapeExpr convertShapeOr(es.weso.shex.ShapeOr shape) {
		List<ShapeExpr> resShapeExprs = new LinkedList<ShapeExpr>();
		
		scala.collection.immutable.List<es.weso.shex.ShapeExpr> shapeExprs = shape.shapeExprs();
		scala.collection.Iterator<es.weso.shex.ShapeExpr> llite = shapeExprs.iterator();
		while (llite.hasNext()) {
			resShapeExprs.add(convertShapeExpr(llite.next()));
		}
		
		ShapeAnd result = new ShapeAnd(resShapeExprs);
		setShapeId(result, shape);
		return result;
	}
	
	private ShapeExpr convertShapeNot(es.weso.shex.ShapeNot shape) {
		ShapeNot result = new ShapeNot(convertShapeExpr(shape.shapeExpr()));
		setShapeId(result, shape);
		return result;
	}
	
	private ShapeExpr convertShapeRef(es.weso.shex.ShapeRef shape) {
		ShapeExprRef result = new ShapeExprRef(createShapeLabel(shape.reference().toString(), false));
		return result;
	}

	private NodeConstraint convertNodeConstraint(es.weso.shex.NodeConstraint node) {
		List<SetOfNodes> constraints = new LinkedList<SetOfNodes>();
		
		if (node.nodeKind().nonEmpty()) {
			if (node.nodeKind().get() instanceof es.weso.shex.BNodeKind$) 
				constraints.add(SetOfNodes.Blank);
			if (node.nodeKind().get() instanceof es.weso.shex.IRIKind$) 
				constraints.add(SetOfNodes.AllIRI);
			if (node.nodeKind().get() instanceof es.weso.shex.LiteralKind$) 
				constraints.add(SetOfNodes.AllLiteral);
			if (node.nodeKind().get() instanceof es.weso.shex.NonLiteralKind$) 
				constraints.add(SetOfNodes.complement(SetOfNodes.AllLiteral));
		}
		
		
		//TODO: finish converting nodeconstraint
		if (node.datatype().nonEmpty()) {
			//constraints.add(createDatatypeSet(node.datatype().get().));
			System.err.println("Implementation of nodekind datatype..");
		}

		scala.collection.immutable.List<es.weso.shex.XsFacet> facets = node.xsFacets();
		scala.collection.Iterator<es.weso.shex.XsFacet> facite = facets.iterator();
		while (facite.hasNext()) {
			es.weso.shex.XsFacet facet = facite.next();
			if (facet instanceof es.weso.shex.StringFacet) {
				es.weso.shex.StringFacet stFacet = (es.weso.shex.StringFacet) facet;
				
			}
			System.err.println("Implementation of nodekind facet..");
		}
		
		if(node.values().nonEmpty()) {
			scala.collection.immutable.List<es.weso.shex.ValueSetValue> listValues = node.values().get();
			scala.collection.Iterator<es.weso.shex.ValueSetValue> valite = listValues.iterator();
			while (valite.hasNext()) {
				es.weso.shex.ValueSetValue val = valite.next();
				System.err.println("Implementation of value:"+val);
			}
		}
//		List values = (List) (map.get("values"));
//		if (values != null) {
//			constraints.addAll(parseValueSetValueList(values));
//		}
//		
		NodeConstraint result;
		if (constraints.size() == 1)
			result = new NodeConstraint(constraints.get(0));
		else 
			result = new NodeConstraint(new ConjunctiveSetOfNodes(constraints)); 

		setShapeId(result, node);

		return result;
	}
	
	private ShapeExpr convertShape(es.weso.shex.Shape shape) {
		TripleExpr triple = convertTripleExpr(shape.expression().get());
		Set<TCProperty> extra = new HashSet<TCProperty>();
		//TODO extra
		boolean closed = (Boolean) shape.closed().get();
		
		Shape result = new Shape(triple,extra,closed);
		setShapeId(result, shape);

		return result;
	}
	

	// ----------------------------------------------------------------------
	// TRIPLE METHODS
	// ----------------------------------------------------------------------
	
	private TripleExpr convertTripleExpr(es.weso.shex.TripleExpr triple) {
		if (triple instanceof es.weso.shex.TripleConstraint)
			return convertTripleConstraint((es.weso.shex.TripleConstraint) triple);
		if (triple instanceof es.weso.shex.EachOf)
			return convertEachOf((es.weso.shex.EachOf) triple);
		if (triple instanceof es.weso.shex.OneOf)
			return convertOneOf((es.weso.shex.OneOf) triple);
		if (triple instanceof es.weso.shex.Inclusion)
			return convertInclusion((es.weso.shex.Inclusion) triple);
		
		System.err.println("Unknown triple type:"+triple.getClass());
		return null;
	}
	
	private TripleExpr convertEachOf(es.weso.shex.EachOf triple) {
		List<TripleExpr> resTripleExprs = new LinkedList<TripleExpr>();
		
		scala.collection.immutable.List<es.weso.shex.TripleExpr> tripleExprs = triple.expressions();
		scala.collection.Iterator<es.weso.shex.TripleExpr> llite = tripleExprs.iterator();
		while (llite.hasNext()) {
			resTripleExprs.add(convertTripleExpr(llite.next()));
		}
		
		EachOf result = new EachOf(resTripleExprs);
		setTripleId(result, triple);
		
		return result;
	}
	
	private TripleExpr convertOneOf(es.weso.shex.OneOf triple) {
		return null;
	}
	
	private TripleExpr convertInclusion(es.weso.shex.Inclusion triple) {
		return null;
	}
	
	private TripleExpr convertTripleConstraint(es.weso.shex.TripleConstraint triple) {
		TCProperty property;
		if (triple.inverse()) {
			property = TCProperty.createInvProperty(createIri(triple.predicate().toString()));
		}else {
			property = TCProperty.createFwProperty(createIri(triple.predicate().toString()));
		}
		ShapeExpr valueExpr = convertShapeExpr(triple.valueExpr().get());
		
		// TODO: deal with cardinality
		//System.out.println(triple.min());
		//System.out.println(triple.max());
		
		TripleConstraint result;
		result = TripleConstraint.newSingleton(property,valueExpr);
		return result;
	}
	
	
	private static SetOfNodes getNumericFacet (Map map) {
		BigDecimal minincl = null, minexcl = null, maxincl = null, maxexcl = null;
		Number n;
		
		n = (Number)(map.get("mininclusive"));
		if (n != null)
			minincl = new BigDecimal(n.toString());
		n = (Number)(map.get("minexclusive"));
		if (n != null)
			minexcl = new BigDecimal(n.toString());
		n = (Number)(map.get("maxinclusive"));
		if (n != null) 
			maxincl = new BigDecimal(n.toString());
		n = (Number)(map.get("maxexclusive"));
		if (n != null)
			maxexcl = new BigDecimal(n.toString());
		
		Integer totalDigits = (Integer) (map.get("totaldigits"));
		Integer fractionDigits = (Integer) (map.get("fractiondigits"));

		if (minincl != null || minexcl != null || maxincl != null || maxexcl != null || totalDigits != null || fractionDigits != null) {
			NumericFacetSetOfNodes facet = new NumericFacetSetOfNodes();
			facet.setMinincl(minincl);
			facet.setMinexcl(minexcl);
			facet.setMaxincl(maxincl);
			facet.setMaxexcl(maxexcl);
			facet.setTotalDigits(totalDigits);
			facet.setFractionDigits(fractionDigits);
			return facet;
		} 
		return null;
	}
	
	// stringFacet = (length|minlength|maxlength):INTEGER | pattern:STRING flags:STRING?
	
	private static SetOfNodes getStringFacet (Map map) {		
		Integer length = (Integer) (map.get("length"));
		Integer minlength = (Integer) (map.get("minlength"));
		Integer maxlength = (Integer) (map.get("maxlength"));
		String patternString = (String) (map.get("pattern"));
		String flags = (String) (map.get("flags"));
		// TODO: flags is not used
		
		if (length != null || minlength != null || maxlength != null || patternString != null) {
			StringFacetSetOfNodes facet = new StringFacetSetOfNodes();
			facet.setLength(length);
			facet.setMinLength(minlength);
			facet.setMaxLength(maxlength);
			facet.setPattern(patternString);
			return facet;
		} 
		else return null;
	}
	
	
	// ----------------------------------------------------------------------
	// FACTORY METHODS
	// ----------------------------------------------------------------------
	private static IRI createIri (String s) {
		return RDF_FACTORY.createIRI(s);
	}
	
	private static SetOfNodes createDatatypeSet (String datatypeIri) {
		return new DatatypeSetOfNodes(createIri(datatypeIri));
	}
	
	private static ShapeExprLabel createShapeLabel (String string,boolean generated) {
		if (isIriString(string))
			return new ShapeExprLabel(createIri(string),generated);
		else 
			return new ShapeExprLabel(RDF_FACTORY.createBNode(string),generated);
	}
	
	private static TripleExprLabel createTripleLabel (String string,boolean generated) {
		if (isIriString(string))
			return new TripleExprLabel(createIri(string),generated);
		else 
			return new TripleExprLabel(RDF_FACTORY.createBNode(string),generated);
	}

	private static TCProperty createTCProperty(IRI iri, boolean isFwd){
		if(isFwd){
			return TCProperty.createFwProperty(iri);
		} else {
			return TCProperty.createInvProperty(iri);
		}
	}


//	public static ShapeExprLabel createFreshExtraLabel () {
//		return createShapeLabel(EXTRA_SHAPE_LABEL_PREFIX+(shapeLabelNb++));
//	}
// 
	
	// ----------------------------------------------------------------------
	// UTILITY METHODS
	// ----------------------------------------------------------------------
	private void warningSemanticActions () {
		if (! this.semActsWarning) {
			System.out.println("Semantic actions not supported.");
			this.semActsWarning = true;
		}
	}
	
	private void warningAnnotations () {
		if (! this.annotationsWarning) {
			System.out.println("Annotations not supported.");
			this.annotationsWarning = true;
		}
	}
	
	
	private String getType (Map map) {
		return (String) (map.get("type"));
	}
	
	private String getId (Map map) {
		return (String) (map.get("id"));
	}

	private void setShapeId (ShapeExpr shapeRes, es.weso.shex.ShapeExpr shape) {
		if (shape.id().nonEmpty()) {
			shapeRes.setId(createShapeLabel(shape.id().get().toString(), false));
		}else {
			shapeRes.setId(createShapeLabel(String.format("%s_%04d", SHAPE_LABEL_PREFIX,shapeLabelNb),true));
			shapeLabelNb++;
		}
	}
	
	private void setTripleId (TripleExpr tripleRes,  es.weso.shex.TripleExpr triple) {
		if (triple instanceof es.weso.shex.EachOf) {
			es.weso.shex.EachOf  trExp = (es.weso.shex.EachOf) triple;
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(createTripleLabel(trExp.id().get().toString(),false));
			}else {
				tripleRes.setId(createTripleLabel(String.format("%s_%04d", TRIPLE_LABEL_PREFIX,tripleLabelNb),true));
				tripleLabelNb++;
			}	
		}
		if (triple instanceof es.weso.shex.OneOf) {
			es.weso.shex.OneOf  trExp = (es.weso.shex.OneOf) triple;
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(createTripleLabel(trExp.id().get().toString(),false));
			}else {
				tripleRes.setId(createTripleLabel(String.format("%s_%04d", TRIPLE_LABEL_PREFIX,tripleLabelNb),true));
				tripleLabelNb++;
			}		
		}
		if (triple instanceof es.weso.shex.TripleConstraint) {
			es.weso.shex.TripleConstraint  trExp = (es.weso.shex.TripleConstraint) triple;
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(createTripleLabel(trExp.id().get().toString(),false));
			}else {
				tripleRes.setId(createTripleLabel(String.format("%s_%04d", TRIPLE_LABEL_PREFIX,tripleLabelNb),true));
				tripleLabelNb++;
			}
		}
	}
	
	
	private boolean isLangString (String s) {
		return LANG_STRING_PATTERN.matcher(s).matches();
	}
	
	private boolean isDatatypeString (String s) {
		return DATATYPE_STRING_PATTERN.matcher(s).matches();
	}
	
	private static boolean isIriString (String s) {
		if (s.indexOf(':') < 0) {
			return false;
		}
		return true;//IRI_PATTERN.matcher(s).matches();
	}

	

}
