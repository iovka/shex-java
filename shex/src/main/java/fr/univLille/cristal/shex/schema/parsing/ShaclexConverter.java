package fr.univLille.cristal.shex.schema.parsing;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import es.weso.shex.Schema;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import scala.collection.Iterator;
import scala.collection.immutable.List;

public class ShaclexConverter {
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
	
	private ShexSchema shexSchema;
	private Schema scalaSchema;
	
	
	public ShaclexConverter(Schema sch) {
		this.scalaSchema=sch;
		this.shexSchema = new ShexSchema();
	}
	
	public ShexSchema convert() {
		List<es.weso.shex.ShapeLabel> listLabels = this.scalaSchema.labels();
		Iterator<es.weso.shex.ShapeLabel> llite = listLabels.iterator();
		while (llite.hasNext()) {
			es.weso.shex.ShapeLabel label = llite.next();
			es.weso.shex.ShapeExpr shapeEx = this.scalaSchema.getShape(label).get();
			
			ShapeExpr newShape = this.convertShapeExpr(shapeEx);
			ShapeExprLabel newLabel = newShape.getId();
			System.out.println(newLabel+" : "+newShape);
			this.shexSchema.put(newLabel, newShape);
		}
		return this.shexSchema;
	}
	
	private ShapeExpr convertShapeExpr(es.weso.shex.ShapeExpr shapeEx) {
		if (shapeEx instanceof es.weso.shex.Shape) {
			return convertShape((es.weso.shex.Shape) shapeEx);
		}

		
		
		return null;
	}
	
	private ShapeExpr convertShape(es.weso.shex.Shape shape) {
//		if (shape.id().nonEmpty()) {
//			createShapeLabel(label.toString(), false);
//		}
//		
		//es.weso.shex.Shape shape = (Shape) shapeEx;
		//System.out.println("Closed:"+shapeEx.closed().get());
		return null;
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

	private void setShapeId (ShapeExpr shape, Map map) {
		if (map.containsKey("id")) {
			shape.setId(createShapeLabel(getId(map),false));
		}else {
			shape.setId(createShapeLabel(String.format("%s_%04d", SHAPE_LABEL_PREFIX,shapeLabelNb),true));
			shapeLabelNb++;
		}
	}
	
	private void setTripleId (TripleExpr triple, Map map) {
		if (map.containsKey("id")) {
			triple.setId(createTripleLabel(getId(map),false));
		}else {
			triple.setId(createTripleLabel(String.format("%s_%04d", TRIPLE_LABEL_PREFIX,tripleLabelNb),true));
			tripleLabelNb++;
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
