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

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.inria.lille.shexjava.GlobalFactory;
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

	private RDF rdfFactory;
	private Graph graph;
	private List<String> imports;
	private Set<RDFTerm> shapeSeen;
	private Set<RDFTerm> tripleSeen;
	
	public Map<Label,ShapeExpr> getRules(Path path) throws Exception{
		return getRules(GlobalFactory.RDFFactory,path);
	}
	
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
	public Map<Label,ShapeExpr> getRules(InputStream is) throws Exception{
		return getRules(GlobalFactory.RDFFactory,is);
	}
	
	
	public Map<Label, ShapeExpr> getRules(RDF rdfFactory, InputStream is) throws Exception {
		return getRules(rdfFactory,is,null);
	}
	
	
	private static String BASE_IRI = "http://base.shex.fr/shex/";
	public Map<Label, ShapeExpr> getRules(RDF rdfFactory, InputStream is, RDFFormat format) throws Exception {
		this.rdfFactory = rdfFactory;
		IRI SCHEMA = rdfFactory.createIRI("http://www.w3.org/ns/shex#Schema");
		IRI SHAPES = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapes");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		
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

	private void parseImports(RDFTerm value) {
		IRI IMPORTS = rdfFactory.createIRI("http://www.w3.org/ns/shex#imports");
		imports = new ArrayList<String>();
		List<Triple> triples = getTriples(value,IMPORTS);
		if (triples.size()==0)
			return ;
		
		for (Triple triple:triples) {
			List<Object> tmp = computeListOfObject(triple.getObject());
			for (Object obj:tmp)
				if (obj instanceof IRI)
					imports.add(((IRI) obj).getIRIString().substring(BASE_IRI.length())); 
		}
	}
	
	
	
	//----------------------------------------------------------
	// Shape
	//----------------------------------------------------------
	
	
	
	private ShapeExpr parseShapeExpr(RDFTerm value) {
		IRI SHAPE = rdfFactory.createIRI("http://www.w3.org/ns/shex#Shape");
		IRI SHAPE_AND = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeAnd");
		IRI SHAPE_OR = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeOr");
		IRI SHAPE_NOT = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeNot");
		IRI NODE_CONSTRAINT = rdfFactory.createIRI("http://www.w3.org/ns/shex#NodeConstraint");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

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
	
	
	private ShapeExpr parseShapeAnd(RDFTerm value) {
		IRI SHAPE_EXPRS = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapeExprs");
		RDFTerm val = getObjects(value,SHAPE_EXPRS).get(0);

		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((RDFTerm) obj));
		
		ShapeExpr res = new ShapeAnd(subExpr);
		setLabel(res,value);		
		return res;
	}
	
	
	private ShapeExpr parseShapeOr(RDFTerm value) {
		IRI SHAPE_EXPRS = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapeExprs");
		RDFTerm val = getObjects(value,SHAPE_EXPRS).get(0);
		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((RDFTerm) obj));
		
		ShapeExpr res = new ShapeOr(subExpr);
		setLabel(res,value);	
		return res;
	}
	
	
	private ShapeExpr parseShapeNot(RDFTerm value) {
		IRI SHAPE_EXPR = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapeExpr");
		RDFTerm val = getObjects(value,SHAPE_EXPR).get(0);
		
		ShapeExpr res = new ShapeNot(parseShapeExpr(val));
		setLabel(res,value);
		return res;
	}
	
	
	
	private ShapeExpr parseShape(RDFTerm value) {
		IRI TRIPLE_EXPRESSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#expression");
		IRI CLOSED = rdfFactory.createIRI("http://www.w3.org/ns/shex#closed");
		IRI EXTRA = rdfFactory.createIRI("http://www.w3.org/ns/shex#extra");
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
	
	
	private ShapeExpr parseNodeConstraint(RDFTerm value) {
		IRI DATATYPE = rdfFactory.createIRI("http://www.w3.org/ns/shex#datatype");
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
	
	
	private Constraint parseValues(RDFTerm value) {
		IRI VALUES = rdfFactory.createIRI("http://www.w3.org/ns/shex#values");
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
	
	
	
	private Constraint parseIRIStem(RDFTerm value) {
		IRI EXCLUSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#exclusion");
		IRI STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#stem");
		IRI IRI_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#IriStem");
		IRI IRI_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#IriStemRange");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

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
					Literal tmp = (Literal) getObjects((RDFTerm) excl, STEM).get(0);
					constraints.add(new IRIStemConstraint(tmp.getLexicalForm()));
				}
			}

			return new IRIStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private Constraint parseLiteralStem(RDFTerm value) {
		IRI EXCLUSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#exclusion");
		IRI STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#stem");
		IRI LITERAL_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#LiteralStem");
		IRI LITERAL_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#LiteralStemRange");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);
		if (type.equals(LITERAL_STEM)) {
			Literal tmp = (Literal) getObjects(value, STEM).get(0);
			return new LiteralStemConstraint(tmp.getLexicalForm());
		}
		if (type.equals(LITERAL_STEM_RANGE)) {
			Constraint stem;
			if (testTriples(value, STEM)) {
				Literal tmp = (Literal) getObjects(value, STEM).get(0);
				stem = new LiteralStemConstraint(tmp.getLexicalForm());
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			RDFTerm exclu = (RDFTerm) getObjects(value, EXCLUSION).get(0);
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof Literal) {
					explicitValues.add((Literal) excl);
					//constraints.add(new LanguageSetOfNodes(((Literal)excl).stringValue()));
				} else {
					Literal tmp = (Literal) getObjects((RDFTerm) excl, STEM).get(0);
					constraints.add(new LiteralStemConstraint(tmp.getLexicalForm()));
				}
			}
			return new LiteralStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	
	private Constraint parseLanguage(RDFTerm value) {
		IRI STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#stem");
		IRI EXCLUSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#exclusion");
		IRI LANGUAGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#Language");
		IRI LANGUAGE_TAG = rdfFactory.createIRI("http://www.w3.org/ns/shex#languageTag");
		IRI LANGUAGE_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#LanguageStem");
		IRI LANGUAGE_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#LanguageStemRange");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

		RDFTerm type = (RDFTerm) getObjects(value,TYPE_IRI).get(0);
		if (type.equals(LANGUAGE)) {
			Literal tmp = (Literal)  getObjects(value, LANGUAGE_TAG).get(0);
			return new LanguageConstraint(tmp.getLexicalForm());
		}
		if (type.equals(LANGUAGE_STEM)) {
			Literal tmp = (Literal)  getObjects(value, STEM).get(0);
			return new LanguageStemConstraint(tmp.getLexicalForm());
		}
		if (type.equals(LANGUAGE_STEM_RANGE)) {
			Constraint stem ;
			if (testTriples(value, STEM)) {
				Literal tmp = (Literal)  getObjects(value, STEM).get(0);
				stem = new LanguageStemConstraint(tmp.getLexicalForm());
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			RDFTerm exclu = (RDFTerm)  getObjects(value, EXCLUSION).get(0);
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof Literal) {
					constraints.add(new LanguageConstraint(((Literal)excl).getLexicalForm()));
				} else {
					Literal tmp = (Literal)  getObjects((RDFTerm) excl, STEM).get(0);
					constraints.add(new LanguageStemConstraint(tmp.getLexicalForm()));
				}
			}
			return new LanguageStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private Constraint parseNodeKind(RDFTerm value) {
		IRI NODEKIND = rdfFactory.createIRI("http://www.w3.org/ns/shex#nodeKind");	
		IRI BNODE = rdfFactory.createIRI("http://www.w3.org/ns/shex#bnode");
		IRI IRI = rdfFactory.createIRI("http://www.w3.org/ns/shex#iri");
		IRI LITERAL = rdfFactory.createIRI("http://www.w3.org/ns/shex#literal");
		IRI NONLITERAL = rdfFactory.createIRI("http://www.w3.org/ns/shex#nonliteral");
		
		if (!testTriples(value,NODEKIND))
			return null;
		
		RDFTerm val = (RDFTerm) getObjects(value, NODEKIND).get(0);
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
	
	
	
	private Constraint parseStringFacet(RDFTerm value) {
		IRI LENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#length");
		IRI MINLENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#minlength");
		IRI MAXLENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxlength");
		IRI PATTERN = rdfFactory.createIRI("http://www.w3.org/ns/shex#pattern");
		IRI FLAGS = rdfFactory.createIRI("http://www.w3.org/ns/shex#flags");

		FacetStringConstraint facet = new FacetStringConstraint();
		boolean changed = false;
		
		if (testTriples(value, LENGTH)) {
			Literal val = (Literal) getObjects(value, LENGTH).get(0);
			facet.setLength(DatatypeUtil.getIntegerValue(val));
			changed=true;
		}
		if (testTriples(value, MINLENGTH)) {
			Literal val = (Literal) getObjects(value, MINLENGTH).get(0);
			facet.setMinLength(DatatypeUtil.getIntegerValue(val));
			changed=true;
		}
		if (testTriples(value, MAXLENGTH)) {
			Literal val = (Literal) getObjects(value, MAXLENGTH).get(0);
			facet.setMaxLength(DatatypeUtil.getIntegerValue(val));
			changed=true;
		}
		if (testTriples(value, PATTERN)) {
			Literal val = (Literal) getObjects(value, PATTERN).get(0);
			facet.setPattern(DatatypeUtil.getStringValue(val));
			changed=true;
		}
		if (testTriples(value, FLAGS)) {
			Literal val = (Literal) getObjects(value, FLAGS).get(0);
			facet.setFlags(DatatypeUtil.getStringValue(val));
			changed=true;
		}
		
		if (changed)
			return facet;
		return null;
	}
	
	
	private Constraint parseNumericFacet(RDFTerm value) {
		IRI MININCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#mininclusive");
		IRI MINEXCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#minexclusive");
		IRI MAXINCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxinclusive");
		IRI MAXEXCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxexclusive");
		IRI FRACTIONDIGITS = rdfFactory.createIRI("http://www.w3.org/ns/shex#fractiondigits");
		IRI TOTALDIGITS = rdfFactory.createIRI("http://www.w3.org/ns/shex#totaldigits");

		FacetNumericConstraint facet = new FacetNumericConstraint();
		boolean changed = false;
				
		if (testTriples(value, MININCLUSIVE)) {
			Literal val = (Literal) getObjects(value, MININCLUSIVE).get(0);
			facet.setMinincl(DatatypeUtil.getDecimalValue(val));
			changed=true;
		}
		
		if (testTriples(value, MINEXCLUSIVE)) {
			Literal val = (Literal) getObjects(value, MINEXCLUSIVE).get(0);
			facet.setMinexcl(DatatypeUtil.getDecimalValue(val));
			changed=true;
		}
		
		if (testTriples(value, MAXINCLUSIVE)) {
			Literal val = (Literal) getObjects(value, MAXINCLUSIVE).get(0);
			facet.setMaxincl(DatatypeUtil.getDecimalValue(val));
			changed=true;
		}
		
		if (testTriples(value, MAXEXCLUSIVE)) {
			Literal val = (Literal) getObjects(value, MAXEXCLUSIVE).get(0);
			facet.setMaxexcl(DatatypeUtil.getDecimalValue(val));
			changed=true;
		}
		
		if (testTriples(value, FRACTIONDIGITS)) {
			Literal val = (Literal) getObjects(value, FRACTIONDIGITS).get(0);
			facet.setFractionDigits(DatatypeUtil.getIntegerValue(val));
			changed=true;
		}
		
		if (testTriples(value, TOTALDIGITS)) {
			Literal val = (Literal) getObjects(value, TOTALDIGITS).get(0);
			facet.setTotalDigits(DatatypeUtil.getIntegerValue(val));;
			changed=true;
		}
		
		if (changed)
			return facet;
		return null;
	}
	
	
	
	//--------------------------------------------------------
	// Triple
	//--------------------------------------------------------

		
	private TripleExpr parseTripleExpr(RDFTerm value) {
		IRI TRIPLE_CONSTRAINT = rdfFactory.createIRI("http://www.w3.org/ns/shex#TripleConstraint");
		IRI EACH_OF = rdfFactory.createIRI("http://www.w3.org/ns/shex#EachOf");
		IRI ONE_OF = rdfFactory.createIRI("http://www.w3.org/ns/shex#OneOf");
		IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		
		if (tripleSeen.contains(value) | !testTriples(value,null))
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
	
	
	private TripleExpr parseEachOf(RDFTerm value) {
		IRI EXPRESSIONS = rdfFactory.createIRI("http://www.w3.org/ns/shex#expressions");

		List<Annotation> annotations = parseAnnotations(value);

		RDFTerm val = (RDFTerm) getObjects(value, EXPRESSIONS).get(0);
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
		IRI EXPRESSIONS = rdfFactory.createIRI("http://www.w3.org/ns/shex#expressions");
		
		List<Annotation> annotations = parseAnnotations(value);

		RDFTerm val = (RDFTerm) getObjects(value, EXPRESSIONS).get(0);
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
	
	
	private TripleExpr parseTripleConstraint(RDFTerm value) {
		IRI PREDICATE = rdfFactory.createIRI("http://www.w3.org/ns/shex#predicate");
		IRI VALUE_EXPR = rdfFactory.createIRI("http://www.w3.org/ns/shex#valueExpr");
		IRI INVERSE = rdfFactory.createIRI("http://www.w3.org/ns/shex#inverse");

		List<Annotation> annotations = parseAnnotations(value);
		
		boolean inverse = false;
		if (testTriples(value, INVERSE)) {
			Literal inv = (Literal) getObjects(value, INVERSE).get(0);
			inverse = DatatypeUtil.getBooleanValue(inv);
		}
		
		RDFTerm pred = (RDFTerm) getObjects(value, PREDICATE).get(0);
		TCProperty predicate;
		if (inverse)
			predicate = TCProperty.createInvProperty((IRI) pred);
		else
			predicate = TCProperty.createFwProperty((IRI) pred);
		
		ShapeExpr valueExpr;
		if (testTriples(value,VALUE_EXPR)) {
			RDFTerm val = (RDFTerm) getObjects(value,VALUE_EXPR).get(0);
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
	
	
	private List<Annotation> parseAnnotations(RDFTerm value){
		IRI PREDICATE = rdfFactory.createIRI("http://www.w3.org/ns/shex#predicate");
		IRI ANNOTATION = rdfFactory.createIRI("http://www.w3.org/ns/shex#annotation");
		IRI OBJECT = rdfFactory.createIRI("http://www.w3.org/ns/shex#object");
		
		List<Annotation> annotations = null;
		
		if (testTriples(value, ANNOTATION)) {
			RDFTerm ann = (RDFTerm) getObjects(value, ANNOTATION).get(0);
			List<Object> lannot = computeListOfObject(ann);
			annotations = new ArrayList<Annotation>();
			for (Object obj:lannot) {
				IRI predicate = (IRI) getObjects((RDFTerm) obj, PREDICATE).get(0);
				RDFTerm object = (RDFTerm) getObjects((RDFTerm) obj, OBJECT).get(0);
				annotations.add(new Annotation(predicate,object));
			}
		}
		return annotations;
		
	}
	
	
	
	private Interval getInterval(RDFTerm value) {
		IRI MIN = rdfFactory.createIRI("http://www.w3.org/ns/shex#min");
		IRI MAX = rdfFactory.createIRI("http://www.w3.org/ns/shex#max");
		
		Integer  min=null,max=null;
		if (testTriples(value,MIN))
			min =  DatatypeUtil.getIntegerValue(((Literal) getObjects(value, MIN).get(0)));
		if (testTriples(value,MAX))
			max =  DatatypeUtil.getIntegerValue(((Literal) getObjects(value, MAX).get(0)));
		
		if (min==null & max==null)
			return null;
		
		if (min==0 & max==1)
			return Interval.OPT;
		if (min==0 & max==-1)
			return Interval.STAR;
		if (min==1 & max==-1)
			return Interval.PLUS;
		if (min==2 & max==1)
			return Interval.EMPTY;
		if (max ==-1)
			max = Interval.UNBOUND;
		return new Interval(min,max);
	}
	
	
	
	//--------------------------------------------------------
	// Utils
	//--------------------------------------------------------
	
	private List<Object> computeListOfObject(RDFTerm value) {
		IRI FIRST = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#first");
		IRI REST = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest");	
		IRI NIL = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil");

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
