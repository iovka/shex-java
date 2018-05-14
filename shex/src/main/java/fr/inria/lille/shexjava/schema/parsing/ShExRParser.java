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
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
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
import fr.inria.lille.shexjava.util.DatatypeUtil;
import fr.inria.lille.shexjava.util.Interval;


/** Parses a {@link ShexSchema} from its rdf representation. 
 * 
 * This implementation does not support: external definitions, semantic actions and anonymous "start" shapes.
 * The base IRI for shex object is http://www.w3.org/ns/shex#.
 * 
 * @author Jérémie Dusart
 */
public class ShExRParser implements Parser {
	public static final List<RDFFormat> RDFFormats = Arrays.asList(new RDFFormat[] {
			RDFFormat.BINARY,
			RDFFormat.JSONLD,
			RDFFormat.N3,
			RDFFormat.NQUADS,
			RDFFormat.NTRIPLES,
			RDFFormat.RDFA,
			RDFFormat.RDFJSON,
			RDFFormat.RDFXML,
			RDFFormat.TRIG,
			RDFFormat.TRIX,
			RDFFormat.TURTLE
	});

	private static final RDF GlobalRDFFactory = new SimpleRDF();
	private static IRI TYPE_IRI = GlobalRDFFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	
	private Graph graph;
	private List<String> imports;
	private Set<RDFTerm> shapeSeen;
	private Set<RDFTerm> tripleSeen;
	private RDF localRDFFactory;
	
	/** Used the first format that contains an extension that ends the provided path in the list of RDFFormats.
	 * @see fr.inria.lille.shexjava.schema.parsing.Parser#getRules(java.nio.file.Path)
	 */
	public Map<Label, ShapeExpr> getRules(RDF rdfFactory, Path path) throws Exception {
		RDFFormat foundformat = null;
		for (RDFFormat format:ShExRParser.RDFFormats) {
			for (String ext:format.getFileExtensions()) {
				if (path.toString().endsWith(ext)) {
					foundformat = format;	
				}
			}
		}	
		InputStream is = new FileInputStream(path.toFile());
		return getRules(rdfFactory,is,foundformat);
	}
	
	/** Used null as format.
	 * @see fr.inria.lille.shexjava.schema.parsing.Parser#getRules(java.nio.file.Path)
	 */
	public Map<Label, ShapeExpr> getRules(RDF rdfFactory, InputStream is) throws Exception {
		return getRules(rdfFactory,is,null);
	}
	
	private static IRI SCHEMA = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#Schema");
	private static IRI SHAPES = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#shapes");
	private static String BASE_IRI = "http://base.shex.fr/shex/";
	public Map<Label, ShapeExpr> getRules(RDF rdfFactory, InputStream is, RDFFormat format) throws Exception {
		localRDFFactory = rdfFactory;
		Reader isr = new InputStreamReader(is,Charset.defaultCharset().name());
		
		Model model = Rio.parse(isr, BASE_IRI, RDFFormat.TURTLE);
		RDF4J tmp = new RDF4J();
		graph = tmp.asGraph(model);
			
		Map<Label,ShapeExpr> rules = new HashMap<Label,ShapeExpr>();
		shapeSeen = new HashSet<RDFTerm>();
		tripleSeen = new HashSet<RDFTerm>();
		
		BlankNodeOrIRI root = graph.stream(null,TYPE_IRI,SCHEMA).map(x-> x.getSubject()).collect(Collectors.toList()).get(0);
		
		List<Triple> triples = getTriples(root,SHAPES);
		for (Triple triple: triples) {
			ShapeExpr shape = parseShapeExpr(triple.getObject());
			if (!(shape instanceof ShapeExprRef)) {
				if (rules.containsKey(shape.getId()))
					throw new IllegalArgumentException("Label "+shape.getId()+" allready used.");
				rules.put(shape.getId(), shape);
			}		
		}
		parseImports(root);
		
		return rules;
	}

	@Override
	public List<String> getImports() {
		return imports;
	}
	
	
	
	//---------------------------------------------------------
	// Schema
	//---------------------------------------------------------

	private static IRI IMPORTS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#imports");
	private void parseImports(RDFTerm value) {
		imports = new ArrayList<String>();
		List<Triple> triples = getTriples(value,IMPORTS);
		if (triples.size()==0)
			return ;
		
		for (Triple triple:triples) {
			if (triple.getObject() instanceof IRI)
				imports.add(((IRI) triple.getObject()).getIRIString().substring(BASE_IRI.length())); 
		}
	}
	
	
	
	//----------------------------------------------------------
	// Shape
	//----------------------------------------------------------
	
	
	private static IRI SHAPE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#Shape");
	private static IRI SHAPE_AND = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#ShapeAnd");
	private static IRI SHAPE_OR = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#ShapeOr");
	private static IRI SHAPE_NOT = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#ShapeNot");
	private static IRI NODE_CONSTRAINT = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#NodeConstraint");
	private ShapeExpr parseShapeExpr(RDFTerm value) {
		List<Triple> triples = getTriples(value,null);
		if (shapeSeen.contains(value) |  triples.size()==0 )
			return new ShapeExprRef(createLabel(value));
		
		triples = getTriples(value,TYPE_IRI);
		RDFTerm type = (RDFTerm) triples.get(0).getObject();
		
		shapeSeen.add(value);
		if (type.equals(SHAPE_AND))
			return parseShapeAnd(value);
		if (type.equals(SHAPE_OR))
			return parseShapeOr(value);
		if (type.equals(SHAPE))
			return parseShape(value);
		if (type.equals(SHAPE_NOT))
			return parseShapeNot(value);
		if (type.equals(NODE_CONSTRAINT))
			return parseNodeConstraint(value);
		
		System.err.println("Unknown shape type: "+type);
		return null;
	}
	
	
	private static IRI SHAPE_EXPRS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#shapeExprs");
	private ShapeExpr parseShapeAnd(RDFTerm value) {
		RDFTerm val = getObjects(value,SHAPE_EXPRS).get(0);

		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((RDFTerm) obj));
		
		ShapeExpr res = new ShapeAnd(subExpr);
		setLabel(res,value);		
		return res;
	}
	
	
	private ShapeExpr parseShapeOr(RDFTerm value) {
		RDFTerm val = getObjects(value,SHAPE_EXPRS).get(0);
		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((RDFTerm) obj));
		
		ShapeExpr res = new ShapeOr(subExpr);
		setLabel(res,value);	
		return res;
	}
	
	
	private static IRI SHAPE_EXPR = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#shapeExpr");
	private ShapeExpr parseShapeNot(RDFTerm value) {
		RDFTerm val = getObjects(value,SHAPE_EXPR).get(0);
		
		ShapeExpr res = new ShapeNot(parseShapeExpr(val));
		setLabel(res,value);
		return res;
	}
	
	
	private static IRI TRIPLE_EXPRESSION = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#expression");
	private static IRI CLOSED = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#closed");
	private static IRI EXTRA = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#extra");
	private ShapeExpr parseShape(RDFTerm value) {
		List<Annotation> annotations = parseAnnotations(value);
		List<Triple> triples = getTriples(value,TRIPLE_EXPRESSION);
		
		if (triples.size()==0) {
			ShapeExpr shtmp = new Shape(new EmptyTripleExpression(),Collections.emptySet(),false);
			setLabel(shtmp,value);
			return shtmp;
		}
		
		boolean closed = false;
		if (testTriples(value,CLOSED)) {
			Literal val = (Literal) getObjects(value,CLOSED).get(0);
			closed = DatatypeUtil.getBooleanValue(val);
		}
		
		Set<TCProperty> extras = new HashSet<TCProperty>();
		if (testTriples(value,EXTRA)) {
			for (Triple triple:getTriples(value,EXTRA)) {
				extras.add(TCProperty.createFwProperty((IRI) triple.getObject()));
			}
		}	
		RDFTerm val = (RDFTerm) getObjects(value,TRIPLE_EXPRESSION).get(0);
		
		Shape res = new Shape(parseTripleExpr(val),extras,closed,annotations);
		setLabel(res,value);	
		return res;
	}
	
	
	private static IRI DATATYPE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#datatype");
	private ShapeExpr parseNodeConstraint(RDFTerm value) {
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		Constraint constraint = parseNodeKind(value);
		if (constraint != null)
			constraints.add(constraint);
		
		if (testTriples(value,DATATYPE)) {
			RDFTerm val = (RDFTerm) getObjects(value,DATATYPE).get(0);
			constraints.add(new DatatypeConstraint((IRI) val));
		}
		
		constraint = parseStringFacet(value);
		if (constraint!=null)
			constraints.add(constraint);
		
		constraint = parseNumericFacet(value);
		if (constraint!=null)
			constraints.add(constraint);
				
		constraint = parseValues(value);
		if (constraint!=null)
			constraints.add(constraint);
				
		ShapeExpr res = new NodeConstraint(constraints);
		setLabel(res,value);
		return res;
	}
	
	
	private static IRI VALUES = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#values");
	private Constraint parseValues(RDFTerm value) {
		if (!testTriples(value,VALUES))
			return null;
		
		RDFTerm val = (RDFTerm) getObjects(value,VALUES).get(0);
		
		List<Object> values_tmp = computeListOfObject(val);
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		Set<Constraint> constraints = new HashSet<Constraint>();
		for (Object obj:values_tmp) {
			if (obj instanceof Literal | obj instanceof IRI) {
				explicitValues.add((RDFTerm) obj);
				continue;
			} else {
				Constraint tmp = parseLanguage((RDFTerm) obj);
				if (tmp!=null)
					constraints.add(tmp);
				tmp = parseLiteralStem((RDFTerm) obj);
				if (tmp!=null)
					constraints.add(tmp);
				tmp = parseIRIStem((RDFTerm) obj);
				if (tmp!=null)
					constraints.add(tmp);
			}
		}
				
		return new ValueSetValueConstraint(explicitValues, constraints);
	}
	
	
	private static IRI IRI_STEM = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#IriStem");
	private static IRI IRI_STEM_RANGE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#IriStemRange");
	private Constraint parseIRIStem(RDFTerm value) {
		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);
		if (type.equals(IRI_STEM)) {
			Literal tmp = (Literal) getObjects(value, STEM).get(0);
			return new IRIStemConstraint(tmp.getLexicalForm());
		}
		if (type.equals(IRI_STEM_RANGE)) {
			Constraint stem;
			if (testTriples(value, STEM)) {
				RDFTerm tmp = (RDFTerm) getObjects(value, STEM).get(0);
				if (tmp instanceof Literal)
					stem = new IRIStemConstraint(((Literal) tmp).getLexicalForm());
				else
					stem = new WildcardConstraint();
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			RDFTerm exclu = (RDFTerm) getObjects(value, EXCLUSION).get(0);
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof IRI) {
					explicitValues.add((IRI) excl);
				} else {
					Literal tmp = (Literal) getObjects(excl, STEM).get(0);
					constraints.add(new IRIStemConstraint(tmp.stringValue()));
				}
			}

			return new IRIStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private static IRI LITERAL_STEM = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#LiteralStem");
	private static IRI LITERAL_STEM_RANGE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#LiteralStemRange");
	private Constraint parseLiteralStem(RDFTerm value) {
		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);
		if (type.equals(LITERAL_STEM)) {
			Literal tmp = (Literal) model.filter((Resource) value, STEM, null).objects().toArray()[0];
			return new LiteralStemConstraint(tmp.stringValue());
		}
		if (type.equals(LITERAL_STEM_RANGE)) {
			Constraint stem;
			if (model.filter((Resource) value, STEM, null).size()>0) {
				Literal tmp = (Literal) model.filter((Resource) value, STEM, null).objects().toArray()[0];
				stem = new LiteralStemConstraint(tmp.stringValue());
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			RDFTerm exclu = (RDFTerm) model.filter((Resource) value, EXCLUSION, null).objects().toArray()[0];
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof Literal) {
					explicitValues.add((Literal) excl);
					//constraints.add(new LanguageSetOfNodes(((Literal)excl).stringValue()));
				} else {
					Literal tmp = (Literal) model.filter((Resource) excl, STEM, null).objects().toArray()[0];
					constraints.add(new LiteralStemConstraint(tmp.stringValue()));
				}
			}
			return new LiteralStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private static IRI STEM = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#stem");
	private static IRI EXCLUSION = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#exclusion");
	private static IRI LANGUAGE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#Language");
	private static IRI LANGUAGE_TAG = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#languageTag");
	private static IRI LANGUAGE_STEM = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#LanguageStem");
	private static IRI LANGUAGE_STEM_RANGE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#LanguageStemRange");
	private Constraint parseLanguage(RDFTerm value) {
		//System.err.println(model.filter((Resource) value,null,null));
		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);
		if (type.equals(LANGUAGE)) {
			Literal tmp = (Literal) model.filter((Resource) value, LANGUAGE_TAG, null).objects().toArray()[0];
			return new LanguageConstraint(tmp.stringValue());
		}
		if (type.equals(LANGUAGE_STEM)) {
			Literal tmp = (Literal) model.filter((Resource) value, STEM, null).objects().toArray()[0];
			return new LanguageStemConstraint(tmp.stringValue());
		}
		if (type.equals(LANGUAGE_STEM_RANGE)) {
			Constraint stem ;
			if (model.filter((Resource) value, STEM, null).size()>0) {
				Literal tmp = (Literal) model.filter((Resource) value, STEM, null).objects().toArray()[0];
				stem = new LanguageStemConstraint(tmp.stringValue());
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			Value exclu = (Value) model.filter((Resource) value, EXCLUSION, null).objects().toArray()[0];
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof Literal) {
					constraints.add(new LanguageConstraint(((Literal)excl).stringValue()));
				} else {
					Literal tmp = (Literal) model.filter((Resource) excl, STEM, null).objects().toArray()[0];
					constraints.add(new LanguageStemConstraint(tmp.stringValue()));
				}
			}
			return new LanguageStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private static IRI NODEKIND = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#nodeKind");	
	private static IRI BNODE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#bnode");
	private static IRI IRI = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#iri");
	private static IRI LITERAL = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#literal");
	private static IRI NONLITERAL = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#nonliteral");
	private Constraint parseNodeKind(RDFTerm value) {
		if (model.filter((Resource) value,NODEKIND,null).size()==0)
			return null;
		
		RDFTerm val = (RDFTerm) model.filter((Resource) value, NODEKIND, null).objects().toArray()[0];
		if (val.equals(BNODE))
			return NodeKindConstraint.Blank;
		if (val.equals(IRI))
			return NodeKindConstraint.AllIRI;
		if (val.equals(LITERAL))
			return NodeKindConstraint.AllLiteral;
		if (val.equals(NONLITERAL))
			return NodeKindConstraint.AllNonLiteral;
		System.err.println("Unknown nodekind: "+val);
		return null;
	}
	
	
	private static IRI LENGTH = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#length");
	private static IRI MINLENGTH = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#minlength");
	private static IRI MAXLENGTH = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#maxlength");
	private static IRI PATTERN = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#pattern");
	private static IRI FLAGS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#flags");
	
	private Constraint parseStringFacet(RDFTerm value) {
		FacetStringConstraint facet = new FacetStringConstraint();
		boolean changed = false;
		
		if (testTriples(value, LENGTH)) {
			Literal val = (Literal) model.filter((Resource) value, LENGTH, null).objects().toArray()[0];
			facet.setLength(val.intValue());
			changed=true;
		}
		if (testTriples(value, MINLENGTH)) {
			Literal val = (Literal) model.filter((Resource) value, MINLENGTH, null).objects().toArray()[0];
			facet.setMinLength(val.intValue());
			changed=true;
		}
		if (testTriples(value, MAXLENGTH)) {
			Literal val = (Literal) model.filter((Resource) value, MAXLENGTH, null).objects().toArray()[0];
			facet.setMaxLength(val.intValue());
			changed=true;
		}
		if (testTriples(value, PATTERN)) {
			Literal val = (Literal) model.filter((Resource) value, PATTERN, null).objects().toArray()[0];
			facet.setPattern(val.stringValue());
			changed=true;
		}
		if (testTriples(value, FLAGS)) {
			Literal val = (Literal) model.filter((Resource) value, FLAGS, null).objects().toArray()[0];
			facet.setFlags(val.stringValue());
			changed=true;
		}
		
		if (changed)
			return facet;
		return null;
	}
	
	
	private static IRI MININCLUSIVE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#mininclusive");
	private static IRI MINEXCLUSIVE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#minexclusive");
	private static IRI MAXINCLUSIVE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#maxinclusive");
	private static IRI MAXEXCLUSIVE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#maxexclusive");
	private static IRI FRACTIONDIGITS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#fractiondigits");
	private static IRI TOTALDIGITS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#totaldigits");
	private Constraint parseNumericFacet(RDFTerm value) {
		FacetNumericConstraint facet = new FacetNumericConstraint();
		boolean changed = false;
				
		if (testTriples(value, MININCLUSIVE)) {
			Literal val = (Literal) model.filter((Resource) value, MININCLUSIVE, null).objects().toArray()[0];
			facet.setMinincl(val.decimalValue());
			changed=true;
		}
		
		if (testTriples(value, MINEXCLUSIVE)) {
			Literal val = (Literal) model.filter((Resource) value, MINEXCLUSIVE, null).objects().toArray()[0];
			facet.setMinexcl(val.decimalValue());
			changed=true;
		}
		
		if (testTriples(value, MAXINCLUSIVE)) {
			Literal val = (Literal) model.filter((Resource) value, MAXINCLUSIVE, null).objects().toArray()[0];
			facet.setMaxincl(val.decimalValue());
			changed=true;
		}
		
		if (testTriples(value, MAXEXCLUSIVE)) {
			Literal val = (Literal) model.filter((Resource) value, MAXEXCLUSIVE, null).objects().toArray()[0];
			facet.setMaxexcl(val.decimalValue());
			changed=true;
		}
		
		if (testTriples(value, FRACTIONDIGITS)) {
			Literal val = (Literal) model.filter((Resource) value, FRACTIONDIGITS, null).objects().toArray()[0];
			facet.setFractionDigits(val.intValue());;
			changed=true;
		}
		
		if (testTriples(value, TOTALDIGITS)) {
			Literal val = (Literal) model.filter((Resource) value, TOTALDIGITS, null).objects().toArray()[0];
			facet.setTotalDigits(val.intValue());;
			changed=true;
		}
		
		if (changed)
			return facet;
		return null;
	}
	
	
	
	//--------------------------------------------------------
	// Triple
	//--------------------------------------------------------

	private static IRI TRIPLE_CONSTRAINT = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#TripleConstraint");
	private static IRI EACH_OF = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#EachOf");
	private static IRI ONE_OF = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#OneOf");
	
	private TripleExpr parseTripleExpr(RDFTerm value) {
		if (tripleSeen.contains(value) | model.filter((Resource) value,null,null).size()==0)
			return new TripleExprRef(createLabel(value));
		
		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);

		tripleSeen.add(value);
		if (type.equals(ONE_OF))
			return parseOneOf(value);
		if (type.equals(EACH_OF))
			return parseEachOf(value);
		if (type.equals(TRIPLE_CONSTRAINT))
			return parseTripleConstraint(value);
		System.err.println("Unknown triple type: "+type);
		return null;
	}
	
	
	private static IRI EXPRESSIONS = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#expressions");
	
	private TripleExpr parseEachOf(RDFTerm value) {
		List<Annotation> annotations = parseAnnotations(value);

		RDFTerm val = (RDFTerm) model.filter((Resource) value, EXPRESSIONS, null).objects().toArray()[0];
		List<TripleExpr> subExpr = new ArrayList<TripleExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseTripleExpr((RDFTerm) obj));
		
		TripleExpr res = new EachOf(subExpr,annotations);
		setLabel(res,value);

		Interval card = getInterval(value);
		if (card!=null)
			res = new RepeatedTripleExpression(res, card);
		
		return res;
	}
	
	private TripleExpr parseOneOf(RDFTerm value) {
		List<Annotation> annotations = parseAnnotations(value);

		RDFTerm val = (RDFTerm) model.filter((Resource) value, EXPRESSIONS, null).objects().toArray()[0];
		List<TripleExpr> subExpr = new ArrayList<TripleExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseTripleExpr((RDFTerm) obj));
		
		TripleExpr res = new OneOf(subExpr,annotations);
		setLabel(res,value);

		Interval card = getInterval(value);
		if (card!=null)
			res = new RepeatedTripleExpression(res, card);
		
		return res;
	}
	
	
	private static IRI PREDICATE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#predicate");
	private static IRI VALUE_EXPR = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#valueExpr");
	private static IRI INVERSE = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#inverse");
	private TripleExpr parseTripleConstraint(RDFTerm value) {
		List<Annotation> annotations = parseAnnotations(value);
		
		boolean inverse = false;
		if (testTriples(value, INVERSE)) {
			Literal inv = (Literal) model.filter((Resource) value, INVERSE, null).objects().toArray()[0];
			inverse = inv.booleanValue();
		}
		
		RDFTerm pred = (RDFTerm) model.filter((Resource) value, PREDICATE, null).objects().toArray()[0];
		TCProperty predicate;
		if (inverse)
			predicate = TCProperty.createInvProperty((IRI) pred);
		else
			predicate = TCProperty.createFwProperty((IRI) pred);
		
		ShapeExpr valueExpr;
		if (testTriples(value,VALUE_EXPR)) {
			RDFTerm val = (RDFTerm) model.filter((Resource) value, VALUE_EXPR, null).objects().toArray()[0];
			valueExpr = parseShapeExpr(val);
		} else {
			valueExpr = new EmptyShape();
		}
		
		TripleExpr res = new TripleConstraint(predicate,valueExpr,annotations);
		setLabel(res,value);
		
		Interval card = getInterval(value);
		if (card!=null)
			res = new RepeatedTripleExpression(res, card);
		
		return res;
	}
	
	
	private static IRI ANNOTATION = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#annotation");
	private static IRI OBJECT = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#object");
	private List<Annotation> parseAnnotations(RDFTerm value){
		List<Annotation> annotations = null;
		if (model.filter((Resource) value, ANNOTATION, null).size()>0) {
			RDFTerm ann = (RDFTerm) model.filter((Resource) value, ANNOTATION, null).objects().toArray()[0];
			List<Object> lannot = computeListOfObject(ann);
			annotations = new ArrayList<Annotation>();
			for (Object obj:lannot) {
				IRI predicate = (IRI) model.filter((Resource) obj, PREDICATE, null).objects().toArray()[0];
				RDFTerm object = (RDFTerm) model.filter((Resource) obj, OBJECT, null).objects().toArray()[0];
				annotations.add(new Annotation(predicate,object));
			}
		}
		return annotations;
		
	}
	
	
	private static IRI MIN = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#min");
	private static IRI MAX = GlobalRDFFactory.createIRI("http://www.w3.org/ns/shex#max");
	private Interval getInterval(RDFTerm value) {
		Integer  min=null,max=null;
		if (testTriples(value,MIN))
			min =  ((Literal) model.filter((Resource) value, MIN, null).objects().toArray()[0]).intValue();
		if (testTriples(value,MAX))
			max =  ((Literal) model.filter((Resource) value, MAX, null).objects().toArray()[0]).intValue();
		
		if (min==null & max==null)
			return null;
		
		if (min==0 & max==1)
			return Interval.OPT;
		if (min==0 & max==-1)
			return Interval.STAR;
		if (min==1 & max==-1)
			return Interval.PLUS;
		if (max ==-1)
			max = Interval.UNBOUND;
		return new Interval(min,max);
	}
	
	
	
	//--------------------------------------------------------
	// Utils
	//--------------------------------------------------------
	
	private static IRI FIRST = GlobalRDFFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#first");
	private static IRI REST = GlobalRDFFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest");	
	private static IRI NIL = GlobalRDFFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil");
	private List<Object> computeListOfObject(RDFTerm value) {
		RDFTerm model_first = getObjects(value, FIRST).get(0);
		RDFTerm model_rest = getObjects(value, REST).get(0);

		List<Object> result = new ArrayList<Object>();
		result.add(model_first);
		
		if (!model_rest.equals(NIL))
			result.addAll(computeListOfObject(model_rest));
		return result;
	}
	
	
	private Label createLabel(RDFTerm value) {
		if (value instanceof IRI)
			return new Label((IRI) value);
		if (value instanceof BlankNode)
			return new Label((BlankNode) value);
		return null;
	}
	
	
	private void setLabel(ShapeExpr shape,RDFTerm value) {
		if (value instanceof IRI)
			shape.setId(new Label((IRI) value));
		if (value instanceof BlankNode & ! value.ntriplesString().startsWith("gen-id"))
			shape.setId(new  Label((BlankNode) value));
	}
	
	
	private void setLabel(TripleExpr triple,RDFTerm value) {
		if (value instanceof IRI)
			triple.setId(new Label((IRI) value));
		if (value instanceof BlankNode & !value.ntriplesString().startsWith("gen-id"))
			triple.setId(new  Label((BlankNode) value));
	}
	
	private boolean testTriples(RDFTerm node, IRI prop) {
		 return graph.stream((BlankNodeOrIRI) node,prop,null).collect(Collectors.toList()).size()>0;
	}
	
	private List<RDFTerm> getObjects(RDFTerm node, IRI prop) {
		 return graph.stream((BlankNodeOrIRI) node,prop,null).map(x -> x.getObject()).collect(Collectors.toList());
	}
	
	private List<Triple> getTriples(RDFTerm node, IRI prop) {
		 return graph.stream((BlankNodeOrIRI) node,prop,null).collect(Collectors.toList());
	}
	
}
