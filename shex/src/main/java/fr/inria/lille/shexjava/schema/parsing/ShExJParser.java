/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.inria.lille.shexjava.schema.parsing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;

import com.github.jsonldjava.utils.JsonUtils;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Annotation;
import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyShape;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.OneOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.concrsynt.Constraint;
import fr.inria.lille.shexjava.schema.concrsynt.DatatypeConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.FacetNumericConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.FacetStringConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.IRIStemConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.IRIStemRangeConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.LanguageConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.LanguageStemConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.LanguageStemRangeConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.LiteralStemConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.LiteralStemRangeConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.NodeKindConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.ValueSetValueConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.WildcardConstraint;
import fr.inria.lille.shexjava.util.Interval;

/** Parses a {@link ShexSchema} from its jsonld representation. 
 * 
 * This implementation does not support: external definitions, semantic actions and anonymous "start" shapes.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
@SuppressWarnings("rawtypes")
public class ShExJParser implements Parser{
	private RDF rdfFactory;
	private List<String> imports;
	private Path path;

	// --------------------------------------------------------------------
	// 	PARSING
	// --------------------------------------------------------------------

	// Schema 	{ 	startActs:[SemAct]? start: shapeExpr? shapes:[shapeExpr+]? }
	public Map<Label,ShapeExpr> getRules(RDF rdfFactory, Path path) throws Exception  {
		InputStream inputStream = new FileInputStream(path.toFile());
		return getRules(rdfFactory,inputStream);
	}
		
	public Map<Label,ShapeExpr> getRules(RDF rdfFactory, InputStream is) throws Exception{
		this.rdfFactory = rdfFactory;
		imports = new ArrayList<>();

		Object schemaObject = JsonUtils.fromInputStream(is,Charset.defaultCharset().name());
		
		Map map = (Map) schemaObject;

		if (! "Schema".equals(getType(map))) {
			throw new ParseException("The type of a schema should be a schema.",-1);	
		}

		if (map.containsKey("startActs")) {
			System.err.println("startActs not supported.");
		}

		if (map.containsKey("start")) {
			throw new UnsupportedOperationException("Start action not supported.");
		}
		
		if (map.containsKey("imports")){
			if (map.get("imports") instanceof String) {
				imports.add((String) map.get("imports"));
			}else {
				imports.addAll((List<String>) map.get("imports"));
			}
		}
		List shapes = (List) (map.get("shapes"));

		Map<Label,ShapeExpr> rules = new HashMap<Label,ShapeExpr>();

		for (Object shapeObj : shapes) {
			Map shape = (Map) shapeObj;
			ShapeExpr shexpr = parseShapeExpression(shape);
			if (rules.containsKey(shexpr.getId()))
				throw new IllegalArgumentException("Label "+shexpr.getId()+" allready used.");
			rules.put(shexpr.getId(), shexpr);
		}

		return rules;
	}

	public List<String> getImports(){
		return imports;
	}

	
	
	//-------------------------------------------
	// Parsing shape
	//-------------------------------------------

	// shapeExpr 	= 	ShapeOr | ShapeAnd | ShapeNot | NodeConstraint | Shape | ShapeExternal | shapeExprRef
	// shapeExprRef = Label
	// Label = IRI | BNode
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

	// ShapeOr 	{ 	id:Label? shapeExprs:[shapeExpr] }
	protected ShapeExpr parseShapeOr (Map map) {
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpr> subExpressions = parseListOfShapeExprs(shapeExprs);
		ShapeExpr res = new ShapeOr(subExpressions);
		setShapeId(res, map);
		return res;
	}

	// ShapeAnd 	{ 	id:Label? shapeExprs:[shapeExpr] }
	protected ShapeExpr parseShapeAnd (Map map) {
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpr> subExpressions = parseListOfShapeExprs(shapeExprs);
		ShapeExpr res = new ShapeAnd(subExpressions);
		setShapeId(res, map);
		return res;
	}

	// ShapeNot 	{ 	id:Label? shapeExpr:shapeExpr }
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

	// Shape 	{ 	id:Label? closed:BOOL? extra:[IRI]? expression:tripleExpr? semActs:[SemAct]? }
	protected ShapeExpr parseShape(Map map) {
		// TODO not used and not supported
		Object semActs = getSemActs(map);
			
		Boolean closed = (Boolean) (map.get("closed"));
		if (closed == null)
			closed = false;

		List<IRI> extra;
		List extraList = (List) (map.get("extra"));
		if (extraList != null) 
			extra = parseListOfIri(extraList);
		else
			extra = Collections.emptyList();

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
		
		List<Annotation> annotations = getAnnotations(map);
		
		Shape res = new Shape(texpr, extraProps, closed, annotations);

		setShapeId(res, map);

		return res;
	}
	
	
	
	//-------------------------------------------
	// Parsing node constraint
	//-------------------------------------------

	// NodeConstraint 	{ 	id:Label? nodeKind:("iri" | "bnode" | "nonliteral" | "literal")? datatype:IRI? xsFacet* values:[valueSetValue]? }
	// xsFacet = stringFacet | numericFacet
	protected NodeConstraint parseNodeConstraint(Map map) {
		List<Constraint> constraints = new ArrayList<>();

		String nodeKind = (String) (map.get("nodeKind"));
		if (nodeKind != null)
			switch (nodeKind) {
			case "iri"  : constraints.add(NodeKindConstraint.AllIRI); break;
			case "bnode": constraints.add(NodeKindConstraint.Blank); break;
			case "literal": constraints.add(NodeKindConstraint.AllLiteral); break;
			case "nonliteral": constraints.add(NodeKindConstraint.AllNonLiteral); break;
			}

		String datatype = (String) (map.get("datatype"));
		if (datatype != null)
			constraints.add(new DatatypeConstraint(rdfFactory.createIRI(datatype)));

		Constraint num = getNumericFacet(map);
		if (num != null) 
			constraints.add(num);

		Constraint str = getStringFacet(map);
		if (str != null) 
			constraints.add(str);

		List values = (List) (map.get("values"));
		if (values != null) {
			constraints.add(parseValueSetValue(values));
		}

		NodeConstraint res = new NodeConstraint(constraints); 

		setShapeId(res, map);

		return res;
	}

	// List of
	// valueSetValue 	= 	objectValue | IriStem | IriStemRange | LiteralStem | LiteralStemRange | Language | LanguageStem | LanguageStemRange ;
	// objectValue 	= 	IRI | ObjectLiteral ;
	// Language 	{ 	langTag:ObjectLiteral }
	// _Stem_ contains stem as key
	private Constraint parseValueSetValue (List values) {
		Set<RDFTerm> explicitValues = new HashSet<>();
		Set<Constraint> nodeConstraints = new HashSet<>();

		for (Object o : values) {
			if (o instanceof String) {
				explicitValues.add(rdfFactory.createIRI((String)o));
			} else {
				Map m = (Map) o;
				String type = (String) m.get("type");
				if (type!=null) {
					switch (type) {
					case "Language" : 
						nodeConstraints.add(parseLanguage(m));
						break;
					case "IriStem":
						nodeConstraints.add(parseIRIStem(m));
						break;
					case "IriStemRange":
						nodeConstraints.add(parseIRIStemRange(m));
						break;
					case "LiteralStem":
						nodeConstraints.add(parseLiteralStem(m));
						break;
					case "LiteralStemRange":
						nodeConstraints.add(parseLiteralStemRange(m));
						break;
					case "LanguageStem":
						nodeConstraints.add(parseLanguageStem(m));
						break;
					case "LanguageStemRange":
						nodeConstraints.add(parseLanguageStemRange(m));
						break;
					default:
						if (m.containsKey("value")) {
							explicitValues.add(parseObjectLiteral(m));
						}else {
							System.err.println("Node constraint not recognize:"+m);
						}
					}
				} else {
					if (m.containsKey("value")) {
						explicitValues.add(parseObjectLiteral(m));
					}else {
						System.err.println("Node constraint not recognize:"+m);
					}			
				}
			}
		}
		
		return new ValueSetValueConstraint(explicitValues,nodeConstraints);
	}

	// ObjectLiteral 	{ 	value:STRING language:STRING? type: STRING? }
	private RDFTerm parseObjectLiteral (Map m) {
		String value = (String) m.get("value");
		if (m.get("type") == null & m.get("language")==null)
			return rdfFactory.createLiteral(value);

		if (m.get("language") != null) {
			String lang = (String) m.get("language");
			return rdfFactory.createLiteral(value, lang);
		}

		IRI type = rdfFactory.createIRI((String) m.get("type"));
		return rdfFactory.createLiteral(value,type);
	}

	//IriStem { stem:IRI }
	protected Constraint parseIRIStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new IRIStemConstraint(stem);
	}

	//IriStemRange 	{ 	stem:(IRI | Wildcard) exclusions:[ objectValue|IriStem +]? }
	protected Constraint parseIRIStemRange (Map m) {
		//TODO: error if stem not present
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<RDFTerm> forbidenValue = new HashSet<RDFTerm>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					forbidenValue.add(rdfFactory.createIRI((String) o));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null & type.equals("IriStem"))
						exclusions.add(parseIRIStem((Map) o));
					else 
						forbidenValue.add(parseObjectLiteral((Map) o));
				}
			}
		}

		Constraint stem;
		if (m.get("stem") instanceof String) {
			stem = parseIRIStem(m);
		} else {
			stem = new WildcardConstraint();
		}

		return new IRIStemRangeConstraint(stem,forbidenValue,exclusions);
	}


	//LiteralStem 	{ 	stem:ObjectLiteral }
	protected Constraint parseLiteralStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new LiteralStemConstraint(stem);
	}

	//LiteralStemRange 	{ 	stem:(Literal | Wildcard) exclusions:[ objectValue|LiteralStem +]? }
	protected Constraint parseLiteralStemRange (Map m) {
		//TODO: error if stem not present
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<RDFTerm> forbidenValue = new HashSet<RDFTerm>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					forbidenValue.add(rdfFactory.createLiteral((String) o));
					//exclusions.add(new ObjectLiteral((String) o, null,null));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null & type.equals("LiteralStem"))
						exclusions.add(parseLiteralStem((Map) o));
					else 
						forbidenValue.add(parseObjectLiteral((Map) o));
				}
			}
		}

		Constraint stem;
		if (m.get("stem") instanceof String) {
			stem = parseLiteralStem(m);
		} else {
			stem = new WildcardConstraint();
		}
		return new LiteralStemRangeConstraint(stem,forbidenValue,exclusions);
	}

	//Language 	{ 	langTag:ObjectLiteral }
	protected Constraint parseLanguage (Map m) {
		if (m.containsKey("languageTag"))
			return new LanguageConstraint((String) m.get("languageTag"));
		if (m.containsKey("langTag"))
			return new LanguageConstraint((String) m.get("langTag"));
		throw new UnsupportedOperationException("Langtag not found");
	}

	protected Constraint parseLanguageStem (Map m) {
		//TODO: error if stem not present
		String stem = (String) m.get("stem");
		return new LanguageStemConstraint(stem);
	}

	protected Constraint parseLanguageStemRange (Map m) {
		//TODO: error if stem not present
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<RDFTerm> forbidenValue = new HashSet<RDFTerm>();
		if (m.containsKey("exclusions")) {
			List<Object> exclu = (List<Object>) m.get("exclusions");
			for (Object o:exclu) {
				if (o instanceof String) {
					String tmp = (String) o;
					if (isIriString(tmp))
						forbidenValue.add(rdfFactory.createIRI((String) o));
					else
						exclusions.add(new LanguageConstraint(tmp));
				}else {
					String type = (String) ((Map) o).get("type");
					if (type != null ) {
						if (type.equals("LanguageStem"))
							exclusions.add(parseLanguageStem((Map) o));
						if (type.equals("Language"))
							exclusions.add(parseLanguage((Map) o));
					}else 
						forbidenValue.add(parseObjectLiteral((Map) o));
				}
			}
		}

		Constraint stem;
		if (m.get("stem") instanceof String) {
			stem = parseLanguageStem(m);
		} else {
			stem = new WildcardConstraint();
		}

		return new LanguageStemRangeConstraint(stem,forbidenValue,exclusions);
	}

	// numericFacet = (mininclusive|minexclusive|maxinclusive|maxeclusive):numericLiteral | (totaldigits|fractiondigits):INTEGER
	private static Constraint getNumericFacet (Map map) {
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
			FacetNumericConstraint facet = new FacetNumericConstraint();
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
	private static Constraint getStringFacet (Map map) {		
		Integer length = (Integer) (map.get("length"));
		Integer minlength = (Integer) (map.get("minlength"));
		Integer maxlength = (Integer) (map.get("maxlength"));
		String patternString = (String) (map.get("pattern"));
		String flags = (String) (map.get("flags"));

		if (length != null || minlength != null || maxlength != null || patternString != null) {
			FacetStringConstraint facet = new FacetStringConstraint();
			facet.setLength(length);
			facet.setMinLength(minlength);
			facet.setMaxLength(maxlength);
			facet.setPattern(patternString);
			facet.setFlags(flags);
			return facet;
		} 
		else return null;
	}


	//-------------------------------------------
	// Parsing triple expression
	//-------------------------------------------

	private List<TripleExpr> parseListOfTripleExprs (List list) {
		ArrayList<TripleExpr> resultList = new ArrayList<>();
		for(Object o : list){
			TripleExpr tripleExpression;
			tripleExpression = parseTripleExpression(o);
			resultList.add(tripleExpression);
		}
		return resultList;
	}

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

	// EachOf 	{ 	id:Label? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	protected TripleExpr parseEachOf (Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		// 
		List<Annotation> annotations = getAnnotations(map);

		List list = (List) (map.get("expressions"));
		List<TripleExpr> subExpressions = parseListOfTripleExprs(list);

		Interval card = getCardinality(map);

		TripleExpr res = new EachOf(subExpressions,annotations);
		if (card != null)
			res = new RepeatedTripleExpression(res, card);
		setTripleId(res, map);

		return res;
	}

	// OneOf { 	id:Label? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }	
	protected TripleExpr parseOneOf (Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		//

		List<Annotation> annotations = getAnnotations(map);

		List list = (List) (map.get("expressions"));
		List<TripleExpr> subExpressions = parseListOfTripleExprs(list);

		Interval card = getCardinality(map);

		TripleExpr res = new OneOf(subExpressions, annotations);
		if (card != null)
			res = new RepeatedTripleExpression(res, card);	

		setTripleId(res, map);

		return res;
	}

	// TripleConstraint 	{ 	id:Label? inverse:BOOL? predicate:IRI valueExpr: shapeExpr? min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	protected TripleExpr parseTripleConstraint(Map map) {
		// TODO not used or not supported
		Object semActs = getSemActs(map);
		//
		List<Annotation> annotations = getAnnotations(map);

		Boolean inv = (Boolean) (map.get("inverse"));
		if (inv == null)
			inv = false;
		IRI predicate = rdfFactory.createIRI((String) (map.get("predicate")));
		TCProperty property = createTCProperty(predicate, !inv);

		ShapeExpr shexpr = null;
		Object valueExpr = map.get("valueExpr");
		if (valueExpr != null)
			shexpr = parseShapeExpression(valueExpr);
		else {
			shexpr = new EmptyShape();
		}

		Interval card = getCardinality(map);

		TripleExpr res = new TripleConstraint(property, shexpr, annotations);
		if (card != null) 
			res = new RepeatedTripleExpression(res, card); 
		setTripleId(res, map);

		return res;
	}


	// --------------------------------------------------------------------
	// 	PARSING PART OF JSON OBJECT
	// --------------------------------------------------------------------

	protected List<IRI> parseListOfIri (List list) {
		List<IRI> res = new ArrayList<>(list.size());
		for (Object o: list) {
			res.add(rdfFactory.createIRI((String)o));
		}
		return res;
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

	private List<Annotation> getAnnotations (Map map) {
		Object annotations = map.get("annotations");
		if (annotations != null) {
			List<Annotation> result = new ArrayList<Annotation>();
			List<Object> lannot = (List<Object>) annotations;
			for (Object annot:lannot) {
				Map<String,Object> mannot = (Map<String,Object>) annot;
				IRI pred = rdfFactory.createIRI((String) mannot.get("predicate"));
				RDFTerm obj = null;
				if (mannot.get("object") instanceof String)
					obj = rdfFactory.createIRI((String) mannot.get("object"));
				else
					obj = parseObjectLiteral((Map) mannot.get("object"));
				result.add(new Annotation(pred,obj));
			}
			return result;
		}
		return null;
	}

	// ----------------------------------------------------------------------
	// FACTORY METHODS
	// ----------------------------------------------------------------------

	private Label createShapeLabel (String string,boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else {
			if (string.startsWith("_:"))
				string = string.substring(2);
			return new Label(rdfFactory.createBlankNode(string),generated);
		}
	}

	private Label createTripleLabel (String string,boolean generated) {
		if (isIriString(string))
			return new Label(rdfFactory.createIRI(string),generated);
		else {
			if (string.startsWith("_:"))
				string = string.substring(2);
			return new Label(rdfFactory.createBlankNode(string),generated);
		}
	}

	private static TCProperty createTCProperty(IRI iri, boolean isFwd){
		if(isFwd){
			return TCProperty.createFwProperty(iri);
		} else {
			return TCProperty.createInvProperty(iri);
		}
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
		if (s.indexOf(':') < 0 | s.startsWith("_:")) {
			return false;
		}
		return true;
	}

}
