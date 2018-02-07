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

package fr.univLille.cristal.shex.schema.parsing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.utils.JsonUtils;

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
import fr.univLille.cristal.shex.schema.concrsynt.LanguageSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.LanguageStemSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.LiteralStemSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.NumericFacetSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.ObjectLiteral;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StemRangeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StringFacetSetOfNodes;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.util.RDFFactory;

/** Parses a {@link ShexSchema} from its jsonld representation. 
 * 
 * This implementation does not support: external definitions, anonymous "start" shapes, inclusion, and stems.
 * It only supports XSD datatypes.
 * 
 * This implementation does not handle syntax errors. 
 * It would raise an exception if the jsonld file does not follow the shex schema format.
 * 
 * @author Iovka Boneva
 *
 */
@SuppressWarnings("rawtypes")
public class JsonldParser{
	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();


	// --------------------------------------------------------------------
	// 	PARSING
	// --------------------------------------------------------------------

	// Schema 	{ 	startActs:[SemAct]? start: shapeExpr? shapes:[shapeExpr+]? }
	public ShexSchema parseSchema(Path path) throws IOException, JsonLdError, ParseException, UndefinedReferenceException, CyclicReferencesException, NotStratifiedException  {
		InputStream inputStream = new FileInputStream(path.toFile());
		Object schemaObject = JsonUtils.fromInputStream(inputStream);
		
		Map map = (Map) schemaObject;

		if (! "Schema".equals(getType(map))) {
			throw new ParseException("The type of a schema should be a schema",-1);	
		}

		if (map.containsKey("startActs")) {
			System.err.println("startActs not supported.");
		}

		if (map.containsKey("start")) {
			throw new UnsupportedOperationException(path + ": Start action not supported");
		}

		List shapes = (List) (map.get("shapes"));

		Map<ShapeExprLabel,ShapeExpr> rules = new HashMap<ShapeExprLabel,ShapeExpr>();

		for (Object shapeObj : shapes) {
			Map shape = (Map) shapeObj;
			ShapeExpr shexpr = parseShapeExpression(shape);
			rules.put(shexpr.getId(), shexpr);
		}

		ShexSchema schema = new ShexSchema(rules);
		return schema;
	}


	//-------------------------------------------
	// Parsing shape
	//-------------------------------------------

	// shapeExpr 	= 	ShapeOr | ShapeAnd | ShapeNot | NodeConstraint | Shape | ShapeExternal | shapeExprRef
	// shapeExprRef = shapeExprLabel
	// shapeExprLabel = IRI | BNode
	protected ShapeExpr parseShapeExpression(Object exprObj) {

		// TODO does not support shapeExprRef
		// It is only supported when the shape expression comes from a triple constraint
		// Then it is supported within the triple constraint
		// TODO this method should evolve when the abstract syntax of shape expressions is adapted so that it can be a reference
		ShapeExpr resultExpr = null;
		if (exprObj instanceof String) {
			resultExpr = new ShapeExprRef(createShapeLabel(((String)exprObj),false));
			setShapeId(resultExpr, Collections.EMPTY_MAP);
			return resultExpr;
		}

		Map exprMap = (Map) exprObj;

		String type = getType(exprMap);		

		switch(type){
		case "ShapeOr":
			resultExpr = parseShapeOr(exprMap);
			break;

		case "ShapeAnd":
			resultExpr = parseShapeAnd(exprMap);
			break;

		case "ShapeNot":
			resultExpr = parseShapeNot(exprMap);
			break;

		case "NodeConstraint":
			resultExpr = parseNodeConstraint(exprMap);
			break;		

		case "Shape":
			resultExpr = parseShape(exprMap);
			break;

		case "ShapeExternal":
			throw new UnsupportedOperationException(String.format("Type %s not supported.", "ShapeExternal"));
		}

		return resultExpr;
	}

	// ShapeOr 	{ 	id:shapeExprLabel? shapeExprs:[shapeExpr] }
	protected ShapeExpr parseShapeOr (Map map) {
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpr> subExpressions = parseListOfShapeExprs(shapeExprs);
		ShapeExpr res = new ShapeOr(subExpressions);
		setShapeId(res, map);
		return res;
	}

	// ShapeAnd 	{ 	id:shapeExprLabel? shapeExprs:[shapeExpr] }
	protected ShapeExpr parseShapeAnd (Map map) {
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpr> subExpressions = parseListOfShapeExprs(shapeExprs);
		ShapeExpr res = new ShapeAnd(subExpressions);
		setShapeId(res, map);
		return res;
	}

	// ShapeNot 	{ 	id:shapeExprLabel? shapeExpr:shapeExpr }
	protected ShapeExpr parseShapeNot (Map map) {
		Map shapeExpr = (Map) map.get("shapeExpr");
		ShapeExpr subExpr = parseShapeExpression(shapeExpr);
		ShapeExpr res = new ShapeNot(subExpr);
		setShapeId(res, map);
		return res;
	}

	protected List<ShapeExpr> parseListOfShapeExprs (List expressionsList) {
		ArrayList<ShapeExpr> list = new ArrayList<>();
		for(Object exprObj : expressionsList){
			ShapeExpr shapeExpression = parseShapeExpression(exprObj);
			list.add(shapeExpression);
		}
		return list;
	}

	// Shape 	{ 	id:shapeExprLabel? closed:BOOL? extra:[IRI]? expression:tripleExpr? semActs:[SemAct]? }
	protected Shape parseShape(Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		//

		Boolean closed = (Boolean) (map.get("closed"));
		if (closed == null)
			closed = false;

		List<IRI> extra;
		List extraList = (List) (map.get("extra"));
		if (extraList != null) 
			extra = parseListOfIri(extraList);
		else
			extra = Collections.EMPTY_LIST;

		Set<TCProperty> extraProps = new HashSet<>();
		for (IRI iri: extra) {
			extraProps.add(TCProperty.createFwProperty(iri));
		}

		TripleExpr texpr;

		Object texprObj = map.get("expression");
		if (texprObj == null) {
			texpr = new EmptyTripleExpression();
			setTripleId(texpr, Collections.EMPTY_MAP);
		}else {
			texpr = parseTripleExpression(texprObj);
		}

		Shape res = createNeighbourhoodConstraint(texpr, closed, extraProps);

		setShapeId(res, map);

		return res;
	}

	// NodeConstraint 	{ 	id:shapeExprLabel? nodeKind:("iri" | "bnode" | "nonliteral" | "literal")? datatype:IRI? xsFacet* values:[valueSetValue]? }
	// xsFacet = stringFacet | numericFacet

	protected NodeConstraint parseNodeConstraint(Map map) {
		List<SetOfNodes> constraints = new ArrayList<>();

		String nodeKind = (String) (map.get("nodeKind"));
		if (nodeKind != null)
			switch (nodeKind) {
			case "iri"  : constraints.add(SetOfNodes.AllIRI); break;
			case "bnode": constraints.add(SetOfNodes.Blank); break;
			case "literal": constraints.add(SetOfNodes.AllLiteral); break;
			case "nonliteral": constraints.add(SetOfNodes.complement(SetOfNodes.AllLiteral)); break;
			}

		String datatype = (String) (map.get("datatype"));
		if (datatype != null)
			constraints.add(new DatatypeSetOfNodes(RDF_FACTORY.createIRI(datatype)));

		SetOfNodes num = getNumericFacet(map);
		if (num != null) 
			constraints.add(num);

		SetOfNodes str = getStringFacet(map);
		if (str != null) 
			constraints.add(str);

		List values = (List) (map.get("values"));
		if (values != null) {
			constraints.addAll(parseValueSetValueList(values));
		}

		NodeConstraint res;
		if (constraints.size() == 1)
			res = new NodeConstraint(constraints.get(0));
		else 
			res = new NodeConstraint(new ConjunctiveSetOfNodes(constraints)); 

		setShapeId(res, map);

		return res;
	}

	
	//-------------------------------------------
	// Parsing triple expression
	//-------------------------------------------

	// tripleExpr 	= 	EachOf | OneOf | TripleConstraint | tripleExprRef ;
	protected TripleExpr parseTripleExpression (Object obj) {
		TripleExpr resultExpr = null;

		if (obj instanceof String) {
			resultExpr = new TripleExprRef(createTripleLabel((String) obj,false));
			setTripleId(resultExpr, Collections.EMPTY_MAP);
			return resultExpr;
		}

		Map map = (Map) obj;

		String type = getType(map);

		switch (type) {
		case "EachOf" : 
			resultExpr = parseEachOf(map); 
			break;

		case "OneOf" :
			resultExpr = parseOneOf(map);
			break;

		case "TripleConstraint" :
			resultExpr = parseTripleConstraint(map);
			break;
		}
		return resultExpr;
	}

	// EachOf 	{ 	id:tripleExprLabel? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	protected TripleExpr parseEachOf (Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		// 

		List list = (List) (map.get("expressions"));
		List<TripleExpr> subExpressions = parseListOfTripleExprs(list);

		Interval card = getCardinality(map);

		TripleExpr res;
		if (card == null)
			res = new EachOf(subExpressions);
		else 
			res = new RepeatedTripleExpression(new EachOf(subExpressions), card);

		setTripleId(res, map);

		return res;
	}

	// OneOf { 	id:tripleExprLabel? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }	
	@SuppressWarnings("rawtypes")
	protected TripleExpr parseOneOf (Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		//


		List list = (List) (map.get("expressions"));
		List<TripleExpr> subExpressions = parseListOfTripleExprs(list);

		Interval card = getCardinality(map);

		TripleExpr res;
		if (card == null)
			res = new EachOf(subExpressions);
		else 
			res = new RepeatedTripleExpression(new OneOf(subExpressions), card);	

		setTripleId(res, map);

		return res;
	}

	// TripleConstraint 	{ 	id:tripleExprLabel? inverse:BOOL? predicate:IRI valueExpr: shapeExpr? min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	protected TripleExpr parseTripleConstraint(Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		//

		Boolean inv = (Boolean) (map.get("inverse"));
		if (inv == null)
			inv = false;
		IRI predicate = RDF_FACTORY.createIRI((String) (map.get("predicate")));
		TCProperty property = createTCProperty(predicate, !inv);

		ShapeExpr shexpr = null;
		Object valueExpr = map.get("valueExpr");
		if (valueExpr != null)
			shexpr = parseShapeExpression(valueExpr);
		else {
			TripleExpr tmp = new EmptyTripleExpression();
			setTripleId(tmp,Collections.EMPTY_MAP);
			shexpr = new Shape(tmp, Collections.EMPTY_SET, false);
			setShapeId(shexpr, Collections.EMPTY_MAP);
		}

		Interval card = getCardinality(map);	
		TripleExpr res ;
		if (card == null)
			res = TripleConstraint.newSingleton(property, shexpr);
		else 
			res = new RepeatedTripleExpression(TripleConstraint.newSingleton(property, shexpr), card); 
		setTripleId(res, map);

		return res;
	}


	// --------------------------------------------------------------------
	// 	PARSING PART OF JSON OBJECT
	// --------------------------------------------------------------------

	// List of
	// valueSetValue 	= 	objectValue | IriStem | IriStemRange | LiteralStem | LiteralStemRange | Language | LanguageStem | LanguageStemRange ;
	// objectValue 	= 	IRI | ObjectLiteral ;
	// Language 	{ 	langTag:ObjectLiteral }
	// _Stem_ contains stem as key
	private List<SetOfNodes> parseValueSetValueList (List values) {
		List<Value> explicitValuesList = new ArrayList<>();
		List<SetOfNodes> nodeConstraintsList = new ArrayList<>();

		for (Object o : values) {
			if (o instanceof String) {
				explicitValuesList.add(RDF_FACTORY.createIRI((String)o));
			} else {
				Map m = (Map) o;
				String type = (String) m.get("type");
				if (type!=null) {
					switch (type) {
					case "Language" : 
						nodeConstraintsList.add(parseLanguage(m));
						break;
					case "IriStem":
						nodeConstraintsList.add(parseIRIStem(m));
						break;
					case "IriStemRange":
						nodeConstraintsList.add(parseIRIStemRange(m));
						break;
					case "LiteralStem":
						nodeConstraintsList.add(parseLiteralStem(m));
						break;
					case "LiteralStemRange":
						nodeConstraintsList.add(parseLiteralStemRange(m));
						break;
					case "LanguageStem":
						nodeConstraintsList.add(parseLanguageStem(m));
						break;
					case "LanguageStemRange":
						nodeConstraintsList.add(parseLanguageStemRange(m));
						break;
					default:
						if (m.containsKey("value")) {
							nodeConstraintsList.add(parseObjectLiteral(m));
						}else {
							System.err.println("Node constraint not recognize:"+m);
						}
					}
				} else {
					if (m.containsKey("value")) {
						nodeConstraintsList.add(parseObjectLiteral(m));
					}else {
						System.err.println("Node constraint not recognize:"+m);
					}			
				}
			}
		}
		if (! explicitValuesList.isEmpty())
			nodeConstraintsList.add(new ExplictValuesSetOfNodes(explicitValuesList));

		return nodeConstraintsList;
	}
	
	// ObjectLiteral 	{ 	value:STRING language:STRING? type: STRING? }
	private SetOfNodes parseObjectLiteral (Map m) {
		IRI type = null;
		String typeString = (String) m.get("type");
		if (typeString != null)
			type = RDF_FACTORY.createIRI(typeString);

		return new ObjectLiteral((String) m.get("value"), (String) m.get("language"), type);
	}

	//IriStem { stem:IRI }
	protected SetOfNodes parseIRIStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new IRIStemSetOfNodes(stem);
	}

	//IriStemRange 	{ 	stem:(IRI | Wildcard) exclusions:[ objectValue|IriStem +]? }
	protected SetOfNodes parseIRIStemRange (Map m) {
		//TODO: error if stem not present
		Set<SetOfNodes> exclusions = new HashSet<SetOfNodes>();
		Set<Value> forbidenValue = new HashSet<Value>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					forbidenValue.add(RDF_FACTORY.createIRI((String) o));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null & type.equals("IriStem")) {
						exclusions.add(parseIRIStem((Map) o));
					}else {
						exclusions.add(parseObjectLiteral((Map) o));
					}

				}
			}
		}

		SetOfNodes stem = null;
		if (m.get("stem") instanceof String) {
			stem = parseIRIStem(m);
		}
		
		if (! forbidenValue.isEmpty())
			exclusions.add(new ExplictValuesSetOfNodes(forbidenValue));

		return new StemRangeSetOfNodes(stem,exclusions);
	}


	//LiteralStem 	{ 	stem:ObjectLiteral }
	protected SetOfNodes parseLiteralStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new LiteralStemSetOfNodes(stem);
	}

	//IriStemRange 	{ 	stem:(IRI | Wildcard) exclusions:[ objectValue|IriStem +]? }
	protected SetOfNodes parseLiteralStemRange (Map m) {
		//TODO: error if stem not present
		Set<SetOfNodes> exclusions = new HashSet<SetOfNodes>();
		Set<Value> forbidenValue = new HashSet<Value>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					forbidenValue.add(RDF_FACTORY.createIRI((String) o));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null & type.equals("LiteralStem")) {
						exclusions.add(parseLiteralStem((Map) o));
					}else {
						exclusions.add(parseObjectLiteral((Map) o));
					}

				}
			}
		}

		SetOfNodes stem = null;
		if (m.get("stem") instanceof String) {
			stem = parseLiteralStem(m);
		}
		
		if (! forbidenValue.isEmpty())
			exclusions.add(new ExplictValuesSetOfNodes(forbidenValue));

		return new StemRangeSetOfNodes(stem,exclusions);
	}

	//Language 	{ 	langTag:ObjectLiteral }
	protected SetOfNodes parseLanguageStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new LanguageStemSetOfNodes(stem);
	}
	
	protected SetOfNodes parseLanguageStemRange (Map m) {
		//TODO: error if stem not present
		Set<SetOfNodes> exclusions = new HashSet<SetOfNodes>();
		Set<Value> forbidenValue = new HashSet<Value>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					String tmp = (String) o;
					if (isIriString(tmp))
						forbidenValue.add(RDF_FACTORY.createIRI((String) o));
					else
						exclusions.add(new LanguageSetOfNodes(tmp));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null & type.equals("LanguageStem")) {
						exclusions.add(parseLanguageStem((Map) o));
					}else {
						exclusions.add(parseObjectLiteral((Map) o));
					}

				}
			}
		}

		SetOfNodes stem = null;
		if (m.get("stem") instanceof String) {
			stem = parseLanguageStem(m);
		}
		
		if (! forbidenValue.isEmpty())
			exclusions.add(new ExplictValuesSetOfNodes(forbidenValue));
		
		return new StemRangeSetOfNodes(stem,exclusions);
	}

	protected SetOfNodes parseStem (Map m) {
		throw new UnsupportedOperationException("stems not supported");
	}

	//Language 	{ 	langTag:ObjectLiteral }
	protected SetOfNodes parseLanguage (Map m) {
		if (m.containsKey("languageTag"))
			return new LanguageSetOfNodes((String) m.get("languageTag"));
		if (m.containsKey("langTag"))
			return new LanguageSetOfNodes((String) m.get("langTag"));
		throw new UnsupportedOperationException("Langtag not found");
	}

	// numericFacet = (mininclusive|minexclusive|maxinclusive|maxeclusive):numericLiteral | (totaldigits|fractiondigits):INTEGER
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

		if (length != null || minlength != null || maxlength != null || patternString != null) {
			StringFacetSetOfNodes facet = new StringFacetSetOfNodes();
			facet.setLength(length);
			facet.setMinLength(minlength);
			facet.setMaxLength(maxlength);
			facet.setPattern(patternString);
			facet.setFlags(flags);
			return facet;
		} 
		else return null;
	}

	
	protected List<IRI> parseListOfIri (List list) {
		List<IRI> res = new ArrayList<>(list.size());
		for (Object o: list) {
			res.add(RDF_FACTORY.createIRI((String)o));
		}
		return res;
	}

	
	private List<TripleExpr> parseListOfTripleExprs (List list) {
		ArrayList<TripleExpr> resultList = new ArrayList<>();
		for(Object o : list){
			TripleExpr tripleExpression;
			tripleExpression = parseTripleExpression(o);
			resultList.add(tripleExpression);
		}
		return resultList;
	}


	private Interval getCardinality (Map map)  {	
		Integer min = (Integer) (map.get("min"));
		Integer max = (Integer) (map.get("max"));

		if (min == null && max == null)
			return null;
		if (min == null)
			min = 0;
		if (max == null || max == -1)
			max = Interval.UNBOUND;

		return new Interval(min, max);
	}


	private Object getSemActs (Map map) {
		Object semActs = map.get("semActs");
		if (semActs != null)
			System.err.println("Semantic actions not supported.");
		return semActs;
	}

	private Object getAnnotations (Map map) {
		Object annotations = map.get("annotations");
		if (annotations != null)
			System.err.println("Annotations not supported.");
		return annotations;
	}

	// ----------------------------------------------------------------------
	// FACTORY METHODS
	// ----------------------------------------------------------------------

	private static ShapeExprLabel createShapeLabel (String string,boolean generated) {
		if (isIriString(string))
			return new ShapeExprLabel(RDF_FACTORY.createIRI(string),generated);
		else 
			return new ShapeExprLabel(RDF_FACTORY.createBNode(string),generated);
	}

	private static TripleExprLabel createTripleLabel (String string,boolean generated) {
		if (isIriString(string))
			return new TripleExprLabel(RDF_FACTORY.createIRI(string),generated);
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

	private Shape createNeighbourhoodConstraint (TripleExpr expr, boolean isClosed, Set<TCProperty> extraProps) {
		return new Shape(expr, extraProps, isClosed);	
	}

	// ----------------------------------------------------------------------
	// UTILITY METHODS
	// ----------------------------------------------------------------------
	private String getType (Map map) {
		return (String) (map.get("type"));
	}

	private String getId (Map map) {
		return (String) (map.get("id"));
	}

	private void setShapeId (ShapeExpr shape, Map map) {
		if (map.containsKey("id")) {
			shape.setId(createShapeLabel(getId(map),false));
		}
	}

	private void setTripleId (TripleExpr triple, Map map) {
		if (map.containsKey("id")) {
			triple.setId(createTripleLabel(getId(map),false));
		}
	}

	private static boolean isIriString (String s) {
		if (s.indexOf(':') < 0) {
			return false;
		}
		return true;
	}

}
