package fr.univLille.cristal.shex.schema.parsing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

import es.weso.shex.IRILabel;
import es.weso.shex.IRIStem;
import es.weso.shex.BNodeLabel;
import es.weso.shex.IRIValue;
import es.weso.shex.IntMax;
import es.weso.shex.Max;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.TripleExprLabel;
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
import fr.univLille.cristal.shex.schema.concrsynt.ConjunctiveSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.ExplictValuesSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.IRIStemSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.NumericFacetSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StemRangeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StringFacetSetOfNodes;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.util.RDFFactory;
import scala.tools.nsc.typechecker.ContextErrors.SymbolTypeError;


public class ConverterFromShaclex {
	private final static Pattern LANG_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*(\")(@[a-zA-Z]+)(-[a-zA-Z0-9]+)*");
	private final static Pattern DATATYPE_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*\"\\^\\^" + "([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");


	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();
	
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
			//System.out.println(newLabel+" : "+newShape);
			rules.put(newLabel, newShape);
		}
		//System.out.println(rules);
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
		
		ShapeOr result = new ShapeOr(resShapeExprs);
		setShapeId(result, shape);
		return result;
	}
	
	private ShapeExpr convertShapeNot(es.weso.shex.ShapeNot shape) {
		ShapeNot result = new ShapeNot(convertShapeExpr(shape.shapeExpr()));
		setShapeId(result, shape);
		return result;
	}
	
	private ShapeExpr convertShapeRef(es.weso.shex.ShapeRef shape) {
		return new ShapeExprRef(getShapeLabel(shape.reference()));
	}

	private ShapeExpr convertNodeConstraint(es.weso.shex.NodeConstraint node) {
		List<SetOfNodes> constraints = new ArrayList<SetOfNodes>();
				
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
		
		
		if (node.datatype().nonEmpty()) {
			constraints.add(new DatatypeSetOfNodes(RDF_FACTORY.createIRI(node.datatype().get().getLexicalForm())));
		}

		NumericFacetSetOfNodes num = null;
		StringFacetSetOfNodes string = null;
		scala.collection.immutable.List<es.weso.shex.XsFacet> facets = node.xsFacets();
		scala.collection.Iterator<es.weso.shex.XsFacet> facite = facets.iterator();
		while (facite.hasNext()) {
			es.weso.shex.XsFacet facet = facite.next();
			if (facet instanceof es.weso.shex.StringFacet) {
				if (string == null) string = new StringFacetSetOfNodes();
				updateStringFacet(string, (es.weso.shex.StringFacet) facet);
			}
			if (facet instanceof es.weso.shex.NumericFacet) {
				if (num == null) num = new NumericFacetSetOfNodes();
				updateNumericFacet(num, (es.weso.shex.NumericFacet) facet);
			}
		}
		if (num!=null) constraints.add(num);
		if (string!=null) constraints.add(string);
		
		if(node.values().nonEmpty()) {
			List<Value> explicitValuesList = new ArrayList<>();
			
			scala.collection.immutable.List<es.weso.shex.ValueSetValue> listValues = node.values().get();
			scala.collection.Iterator<es.weso.shex.ValueSetValue> valite = listValues.iterator();
			while (valite.hasNext()) {
				es.weso.shex.ValueSetValue val = valite.next();
				if (val instanceof es.weso.shex.ObjectValue) {
					explicitValuesList.add(RDF_FACTORY.createIRI(((es.weso.shex.IRIValue) val).i().getLexicalForm()));
				}else {
					constraints.add(getStem(val));
				}
			}
			if (! explicitValuesList.isEmpty())
				constraints.add(new ExplictValuesSetOfNodes(explicitValuesList));
		}
	
		ShapeExpr result;
		if (constraints.size()>0)
			result = new NodeConstraint(new ConjunctiveSetOfNodes(constraints));
		else {
			TripleExpr tmp = new EmptyTripleExpression();
			result = new Shape(tmp, Collections.EMPTY_SET, false);
		}

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
		
		TripleExpr result = new EachOf(resTripleExprs);
		setTripleId(result, triple);
		
		if (triple.optMin().nonEmpty() || triple.optMax().nonEmpty())
			result = createRepeatedTripleExpr(result, triple);
		
		return result;
	}
	
	private TripleExpr convertOneOf(es.weso.shex.OneOf triple) {
		List<TripleExpr> resTripleExprs = new LinkedList<TripleExpr>();
		
		scala.collection.immutable.List<es.weso.shex.TripleExpr> tripleExprs = triple.expressions();
		scala.collection.Iterator<es.weso.shex.TripleExpr> llite = tripleExprs.iterator();
		while (llite.hasNext()) {
			resTripleExprs.add(convertTripleExpr(llite.next()));
		}
		
		TripleExpr result = new OneOf(resTripleExprs);
		setTripleId(result, triple);
		
		if (triple.optMin().nonEmpty() || triple.optMax().nonEmpty())
			result = createRepeatedTripleExpr(result, triple);
		
		return result;	
	}
	
	private TripleExpr convertInclusion(es.weso.shex.Inclusion triple) {
		return new TripleExprRef(getTripleLabel(triple.include()));
	}
	
	private TripleExpr convertTripleConstraint(es.weso.shex.TripleConstraint triple) {
		TCProperty property;
		if (triple.inverse()) {
			property = TCProperty.createInvProperty(createIri(triple.predicate().getLexicalForm()));
		}else {
			property = TCProperty.createFwProperty(createIri(triple.predicate().getLexicalForm()));
		}
		ShapeExpr valueExpr = convertShapeExpr(triple.valueExpr().get());
		
		
		TripleExpr result = new TripleConstraint(property,valueExpr);
		setTripleId(result, triple);
		
		if (triple.optMin().nonEmpty() || triple.optMax().nonEmpty())
			result = createRepeatedTripleExpr(result, triple);
		
		return result;
	}
	
	private TripleExpr createRepeatedTripleExpr(TripleExpr subExpr,es.weso.shex.TripleExpr triple) {
		Integer min=null,max=null;
		Max intmax=null;
		if (triple instanceof es.weso.shex.TripleConstraint) {
			min =(int) ((es.weso.shex.TripleConstraint) triple).optMin().get();
			intmax =(Max) ((es.weso.shex.TripleConstraint) triple).optMax().get();
		}
		if (triple instanceof es.weso.shex.OneOf) {
			min =(int) ((es.weso.shex.TripleConstraint) triple).optMin().get();
			intmax =(Max) ((es.weso.shex.TripleConstraint) triple).optMax().get();
		}
		if (triple instanceof es.weso.shex.EachOf) {
			min =(int) ((es.weso.shex.TripleConstraint) triple).optMin().get();
			intmax =(Max) ((es.weso.shex.TripleConstraint) triple).optMax().get();
		}
		if (intmax instanceof es.weso.shex.Star$)
			max = Interval.UNBOUND;
		else
			max = ((IntMax) intmax).v();
		
		return new RepeatedTripleExpression(subExpr, new Interval(min,max));
	}
	
	
	//----------------------------------------------------------
	// Node Constraint methods
	//----------------------------------------------------------
	
	private void getObjectValue(es.weso.shex.ObjectValue val) {
		
	}
	
	private SetOfNodes getStem(es.weso.shex.ValueSetValue val) {
		if (val instanceof es.weso.shex.IRIStem)
			return getStemValue((es.weso.shex.IRIStem) val); 
		if (val instanceof es.weso.shex.StemRange)
			return getStemRange((es.weso.shex.StemRange) val); 
		System.err.println("<<<## Unknown stem: "+val.getClass());
		return null;
	}
	
	private SetOfNodes getStemValue(es.weso.shex.StemValue val) {
		if (val instanceof es.weso.shex.IRIStem)
			return new IRIStemSetOfNodes(((es.weso.shex.IRIStem) val).iri().getLexicalForm());
		if (val instanceof es.weso.shex.StemRange)
			return getStemRange((es.weso.shex.StemRange) val); 
		System.err.println("<<<## Unknown stem: "+val.getClass());
		return null;
	}
		
	private SetOfNodes getStemRange(es.weso.shex.StemRange stem) {
		SetOfNodes valStem = getStemValue(stem.stem());
		
		Set<SetOfNodes> exclusions = new HashSet<>();
		List<Value> explicitValuesList = new ArrayList<>();
		scala.collection.immutable.List<es.weso.shex.ValueSetValue> listValues = stem.exclusions().get();
		scala.collection.Iterator<es.weso.shex.ValueSetValue> valite = listValues.iterator();
		while (valite.hasNext()) {
			es.weso.shex.ValueSetValue val = valite.next();
			if (val instanceof es.weso.shex.IRIValue) {
				explicitValuesList.add(RDF_FACTORY.createIRI(((es.weso.shex.IRIValue) val).i().getLexicalForm()));
			}else {
				exclusions.add(getStem(val));
			}
		}
		if (! explicitValuesList.isEmpty())
			exclusions.add(new ExplictValuesSetOfNodes(explicitValuesList));
		return new StemRangeSetOfNodes(valStem, exclusions);
	}
	
	private void updateNumericFacet (NumericFacetSetOfNodes num, es.weso.shex.NumericFacet facet) {
		if (facet instanceof es.weso.shex.MaxExclusive) {
			double maxexcl = 0;
			es.weso.shex.MaxExclusive mfacet = ((es.weso.shex.MaxExclusive) facet);
			if (mfacet.n() instanceof es.weso.shex.NumericDecimal)
				 maxexcl = ((es.weso.shex.NumericDecimal) mfacet.n()).n().toDouble();
			if (mfacet.n() instanceof es.weso.shex.NumericInt)
				 maxexcl = ((es.weso.shex.NumericInt) mfacet.n()).n();
			if (mfacet.n() instanceof es.weso.shex.NumericDouble)
				 maxexcl = ((es.weso.shex.NumericDouble) mfacet.n()).n();
			num.setMaxexcl(new BigDecimal(maxexcl));
		}
		if (facet instanceof es.weso.shex.MinExclusive) {
			double maxexcl = 0;
			es.weso.shex.MinExclusive mfacet = ((es.weso.shex.MinExclusive) facet);
			if (mfacet.n() instanceof es.weso.shex.NumericDecimal)
				 maxexcl = ((es.weso.shex.NumericDecimal) mfacet.n()).n().toDouble();
			if (mfacet.n() instanceof es.weso.shex.NumericInt)
				 maxexcl = ((es.weso.shex.NumericInt) mfacet.n()).n();
			if (mfacet.n() instanceof es.weso.shex.NumericDouble)
				 maxexcl = ((es.weso.shex.NumericDouble) mfacet.n()).n();
			num.setMinexcl(new BigDecimal(maxexcl));
		}
		if (facet instanceof es.weso.shex.MaxInclusive) {
			double maxexcl = 0;
			es.weso.shex.MaxInclusive mfacet = ((es.weso.shex.MaxInclusive) facet);
			if (mfacet.n() instanceof es.weso.shex.NumericDecimal)
				 maxexcl = ((es.weso.shex.NumericDecimal) mfacet.n()).n().toDouble();
			if (mfacet.n() instanceof es.weso.shex.NumericInt)
				 maxexcl = ((es.weso.shex.NumericInt) mfacet.n()).n();
			if (mfacet.n() instanceof es.weso.shex.NumericDouble)
				 maxexcl = ((es.weso.shex.NumericDouble) mfacet.n()).n();
			num.setMaxincl(new BigDecimal(maxexcl));
		}
		if (facet instanceof es.weso.shex.MinInclusive) {
			double maxexcl = 0;
			es.weso.shex.MinInclusive mfacet = ((es.weso.shex.MinInclusive) facet);
			if (mfacet.n() instanceof es.weso.shex.NumericDecimal)
				 maxexcl = ((es.weso.shex.NumericDecimal) mfacet.n()).n().toDouble();
			if (mfacet.n() instanceof es.weso.shex.NumericInt)
				 maxexcl = ((es.weso.shex.NumericInt) mfacet.n()).n();
			if (mfacet.n() instanceof es.weso.shex.NumericDouble)
				 maxexcl = ((es.weso.shex.NumericDouble) mfacet.n()).n();
			num.setMinincl(new BigDecimal(maxexcl));
		}
		if (facet instanceof es.weso.shex.TotalDigits) {
			num.setTotalDigits(((es.weso.shex.TotalDigits) facet).n());
		}
		if (facet instanceof es.weso.shex.FractionDigits) {
			num.setFractionDigits(((es.weso.shex.FractionDigits) facet).n());
		}
	}
	
	
	// stringFacet = (length|minlength|maxlength):INTEGER | pattern:STRING flags:STRING?
	private  void updateStringFacet (StringFacetSetOfNodes string, es.weso.shex.StringFacet facet) {	
		if (facet instanceof  es.weso.shex.Length) {
			string.setLength(((es.weso.shex.Length) facet).v());
		}
		if (facet instanceof  es.weso.shex.MinLength) {
			string.setMinLength(((es.weso.shex.MinLength) facet).v());
		}
		if (facet instanceof  es.weso.shex.MaxLength) {
			string.setMaxLength(((es.weso.shex.MaxLength) facet).v());
		}
		if (facet instanceof  es.weso.shex.Pattern) {
			string.setPattern(((es.weso.shex.Pattern) facet).p());
			if (((es.weso.shex.Pattern) facet).flags().nonEmpty())
				string.setFlags(((es.weso.shex.Pattern) facet).flags().get());
		}
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
	
	
	//--------------------------------------
	// Id functions
	//--------------------------------------
	
//	private static ShapeExprLabel createShapeLabel (String string,boolean generated) {
//		if (isIriString(string))
//			return new ShapeExprLabel(createIri(string),generated);
//		else 
//			return 
//	}
//		
//	private void setShapeId (ShapeExpr shapeRes, String id) {
//		shapeRes.setId(createShapeLabel(id, false));
//	}
	
	private ShapeExprLabel getShapeLabel (es.weso.shex.ShapeLabel label) {
		if (label instanceof es.weso.shex.IRILabel) {
			String iri = ((es.weso.shex.IRILabel) label).iri().getLexicalForm();
			return new ShapeExprLabel(RDF_FACTORY.createBNode(iri),false);
		}
		if (label instanceof es.weso.shex.BNodeLabel) {
			String bnode = ((es.weso.shex.BNodeLabel) label).bnode().getLexicalForm();
			return new ShapeExprLabel(RDF_FACTORY.createBNode(bnode),false);
		}
		return null;
	}
	

	private void setShapeId (ShapeExpr shapeRes, es.weso.shex.ShapeExpr shape) {
		if (shape.id().nonEmpty()) {
			shapeRes.setId(getShapeLabel(shape.id().get()));
	
		}
	}
		

	private TripleExprLabel getTripleLabel(es.weso.shex.ShapeLabel label) {
		if (label instanceof es.weso.shex.IRILabel) {
			String iri = ((es.weso.shex.IRILabel) label).iri().getLexicalForm();
			return new TripleExprLabel(createIri(iri),false);
		}
		if (label instanceof es.weso.shex.BNodeLabel) {
			String bnode = ((es.weso.shex.BNodeLabel) label).bnode().getLexicalForm();
			return new TripleExprLabel(RDF_FACTORY.createBNode(bnode),false);
		}
		return null;
	}
	
	private void setTripleId (TripleExpr tripleRes,  es.weso.shex.TripleExpr triple) {
		if (triple instanceof es.weso.shex.EachOf) {
			es.weso.shex.EachOf  trExp = (es.weso.shex.EachOf) triple;
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(getTripleLabel(trExp.id().get()));
			}	
		}
		if (triple instanceof es.weso.shex.OneOf) {
			es.weso.shex.OneOf  trExp = (es.weso.shex.OneOf) triple;
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(getTripleLabel(trExp.id().get()));
			}	
		}
		if (triple instanceof es.weso.shex.TripleConstraint) {
			es.weso.shex.TripleConstraint  trExp = (es.weso.shex.TripleConstraint) triple;
			System.err.println(trExp.id());
			if (trExp.id().nonEmpty()) {
				tripleRes.setId(getTripleLabel(trExp.id().get()));
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
