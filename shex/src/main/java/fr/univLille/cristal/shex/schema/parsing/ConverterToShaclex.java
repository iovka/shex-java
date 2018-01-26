package fr.univLille.cristal.shex.schema.parsing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import es.weso.shex.Schema;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
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

public class ConverterToShaclex {
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
	
	private Schema scalaSchema;
	
	
	public ConverterToShaclex(Schema sch) {
		this.scalaSchema=sch;
	}
	
	public ShexSchema convert() throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		
		return null;
	}
	
	

}
