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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.utils.JsonUtils;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SchemaRules;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeDefinition;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.abstrsynt.SomeOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.analysis.CollectTripleConstraintsVisitor;
import fr.univLille.cristal.shex.schema.concrsynt.ConjunctiveSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.ExplictValuesSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.NumericFacetSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.ObjectLiteral;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.StringFacetSetOfNodes;
import fr.univLille.cristal.shex.util.Interval;

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
public class JsonldParser {
		
	private Object schemaObject; 
	private Path path;
	private boolean semActsWarning = false;
	private boolean annotationsWarning = false;
	private Map<ShapeLabel, ShapeExpression> rules = new HashMap<>();
	private ShapeLabel SL_ALL = createShapeLabel("SLall");
	private ShapeExpression ALL_NODES = new NodeConstraint(SetOfNodes.AllNodes);
	private final ValueFactory RDF_FACTORY = SimpleValueFactory.getInstance();

	
	public JsonldParser(Path path) throws IOException, JsonLdError {

		this.path = path;
		InputStream inputStream = new FileInputStream(path.toString());
		schemaObject = JsonUtils.fromInputStream(inputStream);
		
		/*
		this.path = path;
		try (BufferedReader in = Files.newBufferedReader(path)) {
			theJsonObject = Json.createReader(in).readObject();
		}
		
		XSDDatatype.loadXSDSimpleTypes(typeMapper);
		*/
	}
	
	// --------------------------------------------------------------------
	// 	PARSING
	// --------------------------------------------------------------------
	
	
	// Schema 	{ 	startActs:[SemAct]? start: shapeExpr? shapes:[shapeExpr+]? }
	
	public ShexSchema parseSchema()  {

		Map map = (Map) schemaObject;
		
		assert "Schema".equals(getType(map)) : "The type of a schema should be " + "Schema" ; 
		
		if (map.containsKey("startActs")) {
			warningSemanticActions();
		}
		
		if (map.containsKey("start")) {
			throw new UnsupportedOperationException(path + ": Start action not supported");
		}
		
		List shapes = (List) (map.get("shapes"));
		
		// TODO: what happens if neither start not shapes ?

		for (Object shapeObj : shapes) {
			
			Map shape = (Map) shapeObj;
			String label = getId(shape);
			ShapeExpression shexpr = parseShapeExpression(shape);
			rules.put(createShapeLabel(label), shexpr);
		}
				
		Map<ShapeLabel, ShapeDefinition> rulesMap = new HashMap<>();
		for (Map.Entry<ShapeLabel, ShapeExpression> e: rules.entrySet()) {
			rulesMap.put(e.getKey(), new ShapeDefinition(e.getValue()));
		}
		SchemaRules schemaRules = new SchemaRules(rulesMap);
		return new ShexSchema(schemaRules);
	}
	
	


	// shapeExpr 	= 	ShapeOr | ShapeAnd | ShapeNot | NodeConstraint | Shape | ShapeExternal | shapeExprRef
	
	protected ShapeExpression parseShapeExpression(Object exprObj) {

		// TODO does not support shapeExprRef
		// It is only supported when the shape expression comes from a triple constraint
		// Then it is supported within the triple constraint
		// TODO this method should evolve when the abstract syntax of shape expressions is adapted so that it can be a reference
		if (exprObj instanceof String)
			return new ShapeRef(new ShapeLabel((String) exprObj));
		
		Map exprMap = (Map) exprObj;

		String type = getType(exprMap);		
		ShapeExpression resultExpr = null;
		
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
			resultExpr = parseNeighbourhoodConstraint(exprMap);
			break;

		case "ShapeExternal":
			throw new UnsupportedOperationException(String.format("Type %s not supported.", "ShapeExternal"));
		}

		return resultExpr;
	}

	// ShapeOr 	{ 	id:shapeExprLabel? shapeExprs:[shapeExpr] }
	protected ShapeExpression parseShapeOr (Map map) {
		String id = getId(map);
		// TODO: the id is not used
		
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpression> subExpressions = parseListOfShapeExprs(shapeExprs);
		return new ShapeOrExpression(subExpressions);
		
	}
	
	// ShapeAnd 	{ 	id:shapeExprLabel? shapeExprs:[shapeExpr] }
	protected ShapeExpression parseShapeAnd (Map map) {
		String id = getId(map); 
		// TODO: the id is not used
		
		List shapeExprs = (List) (map.get("shapeExprs"));
		List<ShapeExpression> subExpressions = parseListOfShapeExprs(shapeExprs);
		return new ShapeAndExpression(subExpressions);
	}
	
	// ShapeNot 	{ 	id:shapeExprLabel? shapeExpr:shapeExpr }
	protected ShapeExpression parseShapeNot (Map map) {
		String id = getId(map); 
		// TODO: the id is not used

		Map shapeExpr = (Map) map.get("shapeExpr");
		ShapeExpression subExpr = parseShapeExpression(shapeExpr);
		return new ShapeNotExpression(subExpr);
	}
	
	// NodeConstraint 	{ 	id:shapeExprLabel? nodeKind:("iri" | "bnode" | "nonliteral" | "literal")? datatype:IRI? xsFacet* values:[valueSetValue]? }
	// xsFacet = stringFacet | numericFacet
	
	protected NodeConstraint parseNodeConstraint(Map map) {
		String id = getId(map);
		// TODO: the id is not used

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
			constraints.add(createDatatypeSet(datatype));

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
		
		if (constraints.size() == 1)
			return new NodeConstraint(constraints.get(0));
		else 
			return new NodeConstraint(new ConjunctiveSetOfNodes(constraints)); 

	}
	
	
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
				// IRI
				explicitValuesList.add(RDF_FACTORY.createIRI((String)o));
			} else {
				Map m = (Map) o;
				if (m.containsKey("stem"))
					nodeConstraintsList.add(parseStem(m));
				else if (m.containsKey("langTag")) {
					nodeConstraintsList.add(parseLanguage(m));
				}
				else if (m.containsKey("value")) {
					nodeConstraintsList.add(parseObjectLiteral(m));
				} else {

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
		
	
	/*if (m.containsKey("language") || m.containsKey("type"))
			throw new UnsupportedOperationException("language and tag not supported");
		
		String s = (String) m.get("value");
		if (isIriString(s)) {
			return RDF_FACTORY.createIRI(s);
		}
		if (isLangString(s)){
			String[] litLang = s.split("@");
			String valueString = litLang[0].substring(1, litLang[0].length()-1); 
			return RDF_FACTORY.createLiteral(valueString, litLang[1]);
		} else if (isDatatypeString(s)) {
			String[] valueType = s.split("\\^\\^");
			String valueString = valueType[0].substring(1, valueType[0].length()-1);
			String typeString = valueType[1];
			IRI type = RDF_FACTORY.createIRI(typeString);
			return RDF_FACTORY.createLiteral(valueString, type);
		} else {
			throw new UnsupportedOperationException("unsupported literal type");
		}
	}
	 */
	protected SetOfNodes parseStem (Map m) {
		throw new UnsupportedOperationException("stems not supported");
	}
	
	protected SetOfNodes parseLanguage (Map m) {
		throw new UnsupportedOperationException("language tag not yet implemented");
	}
	
	// Shape 	{ 	id:shapeExprLabel? closed:BOOL? extra:[IRI]? expression:tripleExpr? semActs:[SemAct]? }
	protected NeighbourhoodConstraint parseNeighbourhoodConstraint(Map map) {
		
		// TODO not used or not supported
		String id = getId(map);
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
		
			
		TripleExpression texpr;
		
		Object texprObj = map.get("expression");
		if (texprObj == null)
			texpr = new EmptyTripleExpression();
		else 
			texpr = parseTripleExpression((Map)texprObj);
				

		Set<TCProperty> extraProps = new HashSet<>();
		for (IRI iri: extra) {
			extraProps.add(TCProperty.createFwProperty(iri));
		}
		
		return createNeighbourhoodConstraint(texpr, closed, extraProps);
	}
		
	// tripleExpr 	= 	EachOf | OneOf | TripleConstraint | tripleExprRef ;

	protected TripleExpression parseTripleExpression (Map map) {
		String type = getType(map);
		
		TripleExpression resultExpr = null;
		
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
			
		// FIXME tripleExprRef
		}
		return resultExpr;
	}
	
	// EachOf 	{ 	id:tripleExprLabel? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	
	protected TripleExpression parseEachOf (Map map) {
		
		// TODO not used or not supported
		String id = getId(map);
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		// 
	
		
		List list = (List) (map.get("expressions"));
		List<TripleExpression> subExpressions = parseListOfTripleExprs(list);
	
		Interval card = getCardinality(map);

		if (card == null)
			return new EachOfTripleExpression(subExpressions);
		else 
			return new RepeatedTripleExpression(new EachOfTripleExpression(subExpressions), card);	
	}
	
	// OneOf { 	id:tripleExprLabel? expressions:[tripleExpr] min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }
	
	@SuppressWarnings("rawtypes")
	protected TripleExpression parseOneOf (Map map) {
		
		// TODO not used or not supported
		String id = getId(map);
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		//
		
		
		List list = (List) (map.get("expressions"));
		List<TripleExpression> subExpressions = parseListOfTripleExprs(list);
	
		Interval card = getCardinality(map);
		
		if (card == null)
			return new SomeOfTripleExpression(subExpressions);
		else 
			return new RepeatedTripleExpression(new SomeOfTripleExpression(subExpressions), card);	
	
	}
	
	// TripleConstraint 	{ 	id:tripleExprLabel? inverse:BOOL? predicate:IRI valueExpr: shapeExpr? min:INTEGER? max:INTEGER? semActs:[SemAct]? annotations:[Annotation]? }

	protected TripleExpression parseTripleConstraint(Map map) {
		
		// TODO not used or not supported
		String id = getId(map);
		Object semActs = getSemActs(map);
		Object annotations = getAnnotations(map);
		//
		
		
		Boolean inv = (Boolean) (map.get("inverse"));
		if (inv == null)
			inv = false;
		
		IRI predicate = createIri((String) (map.get("predicate")));
		
		Interval card = getCardinality(map);
		
		ShapeExpression shexpr = null;
		
		Object valueExpr = map.get("valueExpr");
		if (valueExpr != null)
			shexpr = parseShapeExpression(valueExpr);
				
		TCProperty property = createTCProperty(predicate, !inv);
		
		ShapeRef shapeRef = null;

		if (shexpr == null) {
			addAcceptsAllRule();
			shapeRef = new ShapeRef(SL_ALL);
		} 
		else {
			if (shexpr instanceof ShapeRef) {
				shapeRef = (ShapeRef) shexpr;
			}
			else {
				ShapeLabel shapeLabel = createFreshLabel();
				rules.put(shapeLabel, shexpr);
				shapeRef = new ShapeRef(shapeLabel);
			}
		}
		if (card == null)
			return TripleConstraint.newSingleton(property, shapeRef);
		else 
			return new RepeatedTripleExpression(TripleConstraint.newSingleton(property, shapeRef), card); 
			
	}
	
	
	// --------------------------------------------------------------------
	// 	PARSING PART OF JSON OBJECT
	// --------------------------------------------------------------------
	 
	// numericFacet = (mininclusive|minexclusive|maxinclusive|maxeclusive):numericLiteral | (totaldigits|fractiondigits):INTEGER

	private static SetOfNodes getNumericFacet (Map map) {

		/*
		Object omni = map.get("mininclusive");
		Object omne = map.get("minexclusive");
		Object omxi = map.get("maxinclusive");
		Object omxe = map.get("maxexclusive");
		*/
		
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
	
	protected List<IRI> parseListOfIri (List list) {
		List<IRI> res = new ArrayList<>(list.size());
		for (Object o: list) {
			res.add(createIri((String)o));
		}
		return res;
	}
	
	private List<TripleExpression> parseListOfTripleExprs (List list) {
		ArrayList<TripleExpression> resultList = new ArrayList<>();
		for(Object o : list){
			TripleExpression tripleExpression = parseTripleExpression((Map) o);
			resultList.add(tripleExpression);
		}
		return resultList;
	}
	
	private List<ShapeExpression> parseListOfShapeExprs (List expressionsList) {
		ArrayList<ShapeExpression> list = new ArrayList<>();
		for(Object exprObj : expressionsList){
			ShapeExpression shapeExpression = parseShapeExpression(exprObj);
			list.add(shapeExpression);
		}
		return list;
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
			warningSemanticActions();
		return semActs;
	}
	 
	private Object getAnnotations (Map map) {
		
		Object annotations = map.get("annotations");
		if (annotations != null)
			warningAnnotations();
		
		return annotations;
	}
	
	// ----------------------------------------------------------------------
	// FACTORY METHODS
	// ----------------------------------------------------------------------
	private static ShapeLabel createShapeLabel (String string) {
		return new ShapeLabel(string);
	}
	
	private static SetOfNodes createDatatypeSet (String datatypeIri) {
		return new DatatypeSetOfNodes(SimpleValueFactory.getInstance().createIRI(datatypeIri));
	}
	/*
	private static Property createProperty (String iri) {
		return ResourceFactory.createProperty(iri);
	}
	*/
	private static TCProperty createTCProperty(IRI iri, boolean isFwd){
		if(isFwd){
			return TCProperty.createFwProperty(iri);
		} else {
			return TCProperty.createInvProperty(iri);
		}
	}

	private NeighbourhoodConstraint createNeighbourhoodConstraint (TripleExpression expr, boolean isClosed, Set<TCProperty> extraProps) {
		List<TripleConstraint> tripleConstraints;
		CollectTripleConstraintsVisitor visitor = new CollectTripleConstraintsVisitor();
		expr.accept(visitor);
		tripleConstraints = visitor.getResult();
		
		List<TripleConstraint> additionalTripleConstraints = new ArrayList<>();
		
		Iterator<TCProperty> extraPropsIterator = extraProps.iterator();
		while (extraPropsIterator.hasNext()) {
			TCProperty extraProp = extraPropsIterator.next();
			List<ShapeExpression> refsWithThisPropNegated = 
					tripleConstraints.stream()
					.filter(tc -> tc.getPropertySet().getAsFiniteSet().iterator().next().equals(extraProp))
					.map(tc -> new ShapeNotExpression(tc.getShapeRef()))
					.collect(Collectors.toList());

			if (refsWithThisPropNegated.isEmpty() && !isClosed) {
				extraPropsIterator.remove();
			}
			else if (refsWithThisPropNegated.isEmpty() && isClosed) {
				addAcceptsAllRule();
				additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, new ShapeRef(SL_ALL))); 
			}
			else {
				ShapeLabel shapeLabel = createFreshExtraLabel();
			
				ShapeExpression shapeExpression;
				if (refsWithThisPropNegated.size() == 1)
					shapeExpression = refsWithThisPropNegated.get(0);
				else
					shapeExpression = new ShapeAndExpression(refsWithThisPropNegated);
				
				rules.put(shapeLabel, shapeExpression);
				additionalTripleConstraints.add(TripleConstraint.newSingleton(extraProp, new ShapeRef(shapeLabel)));
			}
		}
		
		// FIXME ShEx can close only the forward properties, so the open properties should always contain the complement
		// of all the mentioned inverses
		// best is to make two open triple constraints, one for the inverses and one for the forward

		Set<TCProperty> mentionnedProperties = new HashSet<>();
		mentionnedProperties.addAll(extraProps);
		mentionnedProperties.addAll(tripleConstraints.stream()
				.map(tc -> tc.getPropertySet().getAsFiniteSet().iterator().next())
				.collect(Collectors.toSet()));

		TripleConstraint fwdOpenTripleConstraint = null;
		if (! isClosed) {
			fwdOpenTripleConstraint = TripleConstraint.newForwardOpen(mentionnedProperties, new ShapeRef(SL_ALL));
			addAcceptsAllRule();
			additionalTripleConstraints.add(fwdOpenTripleConstraint);
		}
		addAcceptsAllRule();
		TripleConstraint inverseOpenTripleConstraint = TripleConstraint.newInverseOpen(mentionnedProperties, new ShapeRef(SL_ALL));
		additionalTripleConstraints.add(inverseOpenTripleConstraint);
		
		if (additionalTripleConstraints.isEmpty())
			return new NeighbourhoodConstraint(expr, expr, extraProps, fwdOpenTripleConstraint, inverseOpenTripleConstraint);
		else {
			List<TripleExpression> eachOfList = new ArrayList<>();
			eachOfList.add(expr);
			eachOfList.addAll(additionalTripleConstraints.stream().map(tc -> new RepeatedTripleExpression(tc, Interval.STAR)).collect(Collectors.toList()));
		
			TripleExpression enrichedTripleExpression = new EachOfTripleExpression(eachOfList);
			return new NeighbourhoodConstraint(enrichedTripleExpression, expr, extraProps, fwdOpenTripleConstraint, inverseOpenTripleConstraint);
		}
	}
	
	
	// TODO: would it be preferable to have blank node identifiers ?
	private int shapeLabelNb = 0;
	private static String SHAPE_LABEL_PREFIX = "SL";
	private static String EXTRA_SHAPE_LABEL_PREFIX = "SL_EXTRA";
	
	
	private ShapeLabel createFreshLabel () {
		return createShapeLabel(SHAPE_LABEL_PREFIX+(shapeLabelNb++));
	}
	
	private ShapeLabel createFreshExtraLabel () {
		return createShapeLabel(EXTRA_SHAPE_LABEL_PREFIX+(shapeLabelNb++));
	}
 
	
	// ----------------------------------------------------------------------
	// UTILITY METHODS
	// ----------------------------------------------------------------------
	private void error (String message) throws ParseException {
		throw new ParseException(String.format("%s: %s", path, message), -1);
	}
	
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
	
	private IRI createIri (String s) {
		return SimpleValueFactory.getInstance().createIRI(s);
	}
	
	
	private boolean isLangString (String s) {
		return LANG_STRING_PATTERN.matcher(s).matches();
	}
	private boolean isDatatypeString (String s) {
		return DATATYPE_STRING_PATTERN.matcher(s).matches();
	}
	private boolean isIriString (String s) {
		return IRI_PATTERN.matcher(s).matches();
	}
	/*
	private boolean isString (String s) {
		return STRING_PATTERN.matcher(s).matches();
	}
	*/
	private void addAcceptsAllRule () {
		if (! rules.containsKey(SL_ALL))
			rules.put(SL_ALL, ALL_NODES);
	}
	
	
	// ----------------------------------------------------------------------
	// CONSTANTS
	// ----------------------------------------------------------------------
	private final static Pattern LANG_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*(\")(@[a-zA-Z]+)(-[a-zA-Z0-9]+)*");
	private final static Pattern DATATYPE_STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*\"\\^\\^" + "([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");
	private final static Pattern IRI_PATTERN = Pattern.compile("([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");
	// TODO is this correct ?
	private static final Pattern STRING_PATTERN = Pattern.compile("\"([^\"]|\\\")*\"\\^\\^"); 
	
	
}
