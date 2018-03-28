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
package fr.univLille.cristal.shex.schema.parsing;

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

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.Annotation;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyShape;
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
import fr.univLille.cristal.shex.schema.concrsynt.Constraint;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.FacetNumericConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.FacetStringConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.IRIStemConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.IRIStemRangeConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.LanguageConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.LanguageStemConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.LanguageStemRangeConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.LiteralStemConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.LiteralStemRangeConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.NodeKindConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.ValueSetValueConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.WildcardConstraint;
import fr.univLille.cristal.shex.util.Interval;


/** Parses a {@link ShexSchema} from its rdf representation. 
 * 
 * This implementation does not support: external definitions, semantic actions and anonymous "start" shapes.
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

	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();
	private static IRI TYPE_IRI = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	
	private Model model;
	private List<String> imports;
	private Set<Value> shapeSeen;
	private Set<Value> tripleSeen;
	
	/** Used the first format that contains an extension that ends the provided path in the list of RDFFormats.
	 * @see fr.univLille.cristal.shex.schema.parsing.Parser#getRules(java.nio.file.Path)
	 */
	@Override
	public Map<Label, ShapeExpr> getRules(Path path) throws Exception {
		for (RDFFormat format:ShExRParser.RDFFormats) {
			for (String ext:format.getFileExtensions()) {
				if (path.toString().endsWith(ext)) {
					return getRules(path,format);	
				}
			}
		}	
		return getRules(path,null);
	}
	
	
	private static String BASE_IRI = "http://base.shex.fr/shex/";
	
	public Map<Label, ShapeExpr> getRules(Path path,RDFFormat format) throws Exception {
		InputStream is = new FileInputStream(path.toFile());
		Reader isr = new InputStreamReader(is,Charset.defaultCharset().name());
		
		model = Rio.parse(isr, BASE_IRI, RDFFormat.TURTLE, new ParserConfig(), rdfFactory, new ParseErrorLogger());
		
		Model roots = model.filter(null,TYPE_IRI,rdfFactory.createIRI("http://www.w3.org/ns/shex#Schema"));
		Resource root = (Resource) roots.subjects().toArray()[0];
		
		Map<Label,ShapeExpr> rules = new HashMap<Label,ShapeExpr>();
		shapeSeen = new HashSet<Value>();
		tripleSeen = new HashSet<Value>();
		
		for (Statement stat: model.filter(root, rdfFactory.createIRI("http://www.w3.org/ns/shex#shapes"), null)) {
			ShapeExpr shape = parseShapeExpr(stat.getObject());
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

	private static IRI IMPORTS = rdfFactory.createIRI("http://www.w3.org/ns/shex#imports");
	private void parseImports(Value value) {
		imports = new ArrayList<String>();
		if (model.filter((Resource) value,IMPORTS,null).size()==0)
			return ;
		
		Value val =  (Value) model.filter((Resource) value,IMPORTS,null).objects().toArray()[0];
		List<Object> list = computeListOfObject(val);

		for(Object obj:list) {
			imports.add(((String) obj.toString()).substring(BASE_IRI.length())); 
		}
	}
	
	
	
	//----------------------------------------------------------
	// Shape
	//----------------------------------------------------------
	
	
	private static IRI SHAPE = rdfFactory.createIRI("http://www.w3.org/ns/shex#Shape");
	private static IRI SHAPE_AND = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeAnd");
	private static IRI SHAPE_OR = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeOr");
	private static IRI SHAPE_NOT = rdfFactory.createIRI("http://www.w3.org/ns/shex#ShapeNot");
	private static IRI NODE_CONSTRAINT = rdfFactory.createIRI("http://www.w3.org/ns/shex#NodeConstraint");
	private ShapeExpr parseShapeExpr(Value value) {
		if (shapeSeen.contains(value) |  model.filter((Resource) value,null,null).size()==0 )
			return new ShapeExprRef(createLabel(value));
		
		Value type = (Value) model.filter((Resource) value,TYPE_IRI,null).objects().toArray()[0];
		
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
	
	
	private static IRI SHAPE_EXPRS = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapeExprs");
	private ShapeExpr parseShapeAnd(Value value) {
		Value val = (Value) model.filter((Resource) value, SHAPE_EXPRS, null).objects().toArray()[0];
		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((Value) obj));
		
		ShapeExpr res = new ShapeAnd(subExpr);
		setLabel(res,value);		
		return res;
	}
	
	
	private ShapeExpr parseShapeOr(Value value) {
		Value val = (Value) model.filter((Resource) value, SHAPE_EXPRS, null).objects().toArray()[0];
		List<ShapeExpr> subExpr = new ArrayList<ShapeExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseShapeExpr((Value) obj));
		
		ShapeExpr res = new ShapeOr(subExpr);
		setLabel(res,value);	
		return res;
	}
	
	
	private static IRI SHAPE_EXPR = rdfFactory.createIRI("http://www.w3.org/ns/shex#shapeExpr");
	private ShapeExpr parseShapeNot(Value value) {
		Value val = (Value) model.filter((Resource) value, SHAPE_EXPR, null).objects().toArray()[0];
		
		ShapeExpr res = new ShapeNot(parseShapeExpr(val));
		setLabel(res,value);
		return res;
	}
	
	
	private static IRI TRIPLE_EXPRESSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#expression");
	private static IRI CLOSED = rdfFactory.createIRI("http://www.w3.org/ns/shex#closed");
	private static IRI EXTRA = rdfFactory.createIRI("http://www.w3.org/ns/shex#extra");
	private ShapeExpr parseShape(Value value) {
		List<Annotation> annotations = parseAnnotations(value);

		if (model.filter((Resource) value, TRIPLE_EXPRESSION,null).size()==0) {
			ShapeExpr shtmp = new Shape(new EmptyTripleExpression(),Collections.emptySet(),false);
			setLabel(shtmp,value);
			return shtmp;
		}
		
		boolean closed = false;
		if (model.filter((Resource) value, CLOSED,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, CLOSED, null).objects().toArray()[0];
			closed = val.booleanValue();
		}
		
		Set<TCProperty> extras = new HashSet<TCProperty>();
		if (model.filter((Resource) value, EXTRA,null).size()>0) {
			for (Statement ext:model.filter((Resource) value, EXTRA,null)) {
				extras.add(TCProperty.createFwProperty((IRI) ext.getObject()));
			}
		}	
		Value val = (Value) model.filter((Resource) value, TRIPLE_EXPRESSION, null).objects().toArray()[0];
		
		Shape res = new Shape(parseTripleExpr(val),extras,closed,annotations);
		setLabel(res,value);	
		return res;
	}
	
	
	private static IRI DATATYPE = rdfFactory.createIRI("http://www.w3.org/ns/shex#datatype");
	private ShapeExpr parseNodeConstraint(Value value) {
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		Constraint constraint = parseNodeKind(value);
		if (constraint != null)
			constraints.add(constraint);
		
		if (model.filter((Resource) value,DATATYPE,null).size()>0) {
			Value val = (Value) model.filter((Resource) value, DATATYPE, null).objects().toArray()[0];
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
	
	
	private static IRI VALUES = rdfFactory.createIRI("http://www.w3.org/ns/shex#values");
	private Constraint parseValues(Value value) {
		if (model.filter((Resource) value,VALUES,null).size()==0)
			return null;
		
		Value val = (Value) model.filter((Resource) value, VALUES, null).objects().toArray()[0];
		
		List<Object> values_tmp = computeListOfObject(val);
		Set<Value> explicitValues = new HashSet<Value>();
		Set<Constraint> constraints = new HashSet<Constraint>();
		for (Object obj:values_tmp) {
			if (obj instanceof Literal | obj instanceof IRI) {
				explicitValues.add((Value) obj);
				continue;
			} else {
				Constraint tmp = parseLanguage((Value) obj);
				if (tmp!=null)
					constraints.add(tmp);
				tmp = parseLiteralStem((Value) obj);
				if (tmp!=null)
					constraints.add(tmp);
				tmp = parseIRIStem((Value) obj);
				if (tmp!=null)
					constraints.add(tmp);
			}
		}
				
		return new ValueSetValueConstraint(explicitValues, constraints);
	}
	
	
	private static IRI IRI_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#IriStem");
	private static IRI IRI_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#IriStemRange");
	private Constraint parseIRIStem(Value value) {
		Value type = (Value) model.filter((Resource) value,TYPE_IRI,null).objects().toArray()[0];
		if (type.equals(IRI_STEM)) {
			Literal tmp = (Literal) model.filter((Resource) value, STEM, null).objects().toArray()[0];
			return new IRIStemConstraint(tmp.stringValue());
		}
		if (type.equals(IRI_STEM_RANGE)) {
			Constraint stem;
			if (model.filter((Resource) value, STEM, null).size()>0) {
				Value tmp = (Value) model.filter((Resource) value, STEM, null).objects().toArray()[0];
				if (tmp instanceof Literal)
					stem = new IRIStemConstraint(tmp.stringValue());
				else
					stem = new WildcardConstraint();
			} else {
				stem = new WildcardConstraint();
			}
			
			Set<Value> explicitValues = new HashSet<Value>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			Value exclu = (Value) model.filter((Resource) value, EXCLUSION, null).objects().toArray()[0];
			List<Object> exclusions = computeListOfObject(exclu);
			for (Object excl:exclusions) {
				if (excl instanceof IRI) {
					explicitValues.add((IRI) excl);
				} else {
					Literal tmp = (Literal) model.filter((Resource) excl, STEM, null).objects().toArray()[0];
					constraints.add(new IRIStemConstraint(tmp.stringValue()));
				}
			}

			return new IRIStemRangeConstraint(stem, explicitValues, constraints);
		}
		return null;
	}
	
	
	private static IRI LITERAL_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#LiteralStem");
	private static IRI LITERAL_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#LiteralStemRange");
	private Constraint parseLiteralStem(Value value) {
		Value type = (Value) model.filter((Resource) value,TYPE_IRI,null).objects().toArray()[0];
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
			
			Set<Value> explicitValues = new HashSet<Value>();
			Set<Constraint> constraints = new HashSet<Constraint>();
			
			Value exclu = (Value) model.filter((Resource) value, EXCLUSION, null).objects().toArray()[0];
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
	
	
	private static IRI STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#stem");
	private static IRI EXCLUSION = rdfFactory.createIRI("http://www.w3.org/ns/shex#exclusion");
	private static IRI LANGUAGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#Language");
	private static IRI LANGUAGE_TAG = rdfFactory.createIRI("http://www.w3.org/ns/shex#languageTag");
	private static IRI LANGUAGE_STEM = rdfFactory.createIRI("http://www.w3.org/ns/shex#LanguageStem");
	private static IRI LANGUAGE_STEM_RANGE = rdfFactory.createIRI("http://www.w3.org/ns/shex#LanguageStemRange");
	private Constraint parseLanguage(Value value) {
		//System.err.println(model.filter((Resource) value,null,null));
		Value type = (Value) model.filter((Resource) value,TYPE_IRI,null).objects().toArray()[0];
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
			
			Set<Value> explicitValues = new HashSet<Value>();
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
	
	
	private static IRI NODEKIND = rdfFactory.createIRI("http://www.w3.org/ns/shex#nodeKind");	
	private static IRI BNODE = rdfFactory.createIRI("http://www.w3.org/ns/shex#bnode");
	private static IRI IRI = rdfFactory.createIRI("http://www.w3.org/ns/shex#iri");
	private static IRI LITERAL = rdfFactory.createIRI("http://www.w3.org/ns/shex#literal");
	private static IRI NONLITERAL = rdfFactory.createIRI("http://www.w3.org/ns/shex#nonliteral");
	private Constraint parseNodeKind(Value value) {
		if (model.filter((Resource) value,NODEKIND,null).size()==0)
			return null;
		
		Value val = (Value) model.filter((Resource) value, NODEKIND, null).objects().toArray()[0];
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
	
	
	private static IRI LENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#length");
	private static IRI MINLENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#minlength");
	private static IRI MAXLENGTH = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxlength");
	private static IRI PATTERN = rdfFactory.createIRI("http://www.w3.org/ns/shex#pattern");
	private static IRI FLAGS = rdfFactory.createIRI("http://www.w3.org/ns/shex#flags");
	
	private Constraint parseStringFacet(Value value) {
		FacetStringConstraint facet = new FacetStringConstraint();
		boolean changed = false;
		
		if (model.filter((Resource) value, LENGTH,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, LENGTH, null).objects().toArray()[0];
			facet.setLength(val.intValue());
			changed=true;
		}
		if (model.filter((Resource) value, MINLENGTH,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MINLENGTH, null).objects().toArray()[0];
			facet.setMinLength(val.intValue());
			changed=true;
		}
		if (model.filter((Resource) value, MAXLENGTH,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MAXLENGTH, null).objects().toArray()[0];
			facet.setMaxLength(val.intValue());
			changed=true;
		}
		if (model.filter((Resource) value, PATTERN,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, PATTERN, null).objects().toArray()[0];
			facet.setPattern(val.stringValue());
			changed=true;
		}
		if (model.filter((Resource) value, FLAGS, null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, FLAGS, null).objects().toArray()[0];
			facet.setFlags(val.stringValue());
			changed=true;
		}
		
		if (changed)
			return facet;
		return null;
	}
	
	
	private static IRI MININCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#mininclusive");
	private static IRI MINEXCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#minexclusive");
	private static IRI MAXINCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxinclusive");
	private static IRI MAXEXCLUSIVE = rdfFactory.createIRI("http://www.w3.org/ns/shex#maxexclusive");
	private static IRI FRACTIONDIGITS = rdfFactory.createIRI("http://www.w3.org/ns/shex#fractiondigits");
	private static IRI TOTALDIGITS = rdfFactory.createIRI("http://www.w3.org/ns/shex#totaldigits");
	private Constraint parseNumericFacet(Value value) {
		FacetNumericConstraint facet = new FacetNumericConstraint();
		boolean changed = false;
				
		if (model.filter((Resource) value, MININCLUSIVE,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MININCLUSIVE, null).objects().toArray()[0];
			facet.setMinincl(val.decimalValue());
			changed=true;
		}
		
		if (model.filter((Resource) value, MINEXCLUSIVE,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MINEXCLUSIVE, null).objects().toArray()[0];
			facet.setMinexcl(val.decimalValue());
			changed=true;
		}
		
		if (model.filter((Resource) value, MAXINCLUSIVE,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MAXINCLUSIVE, null).objects().toArray()[0];
			facet.setMaxincl(val.decimalValue());
			changed=true;
		}
		
		if (model.filter((Resource) value, MAXEXCLUSIVE,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, MAXEXCLUSIVE, null).objects().toArray()[0];
			facet.setMaxexcl(val.decimalValue());
			changed=true;
		}
		
		if (model.filter((Resource) value, FRACTIONDIGITS,null).size()>0) {
			Literal val = (Literal) model.filter((Resource) value, FRACTIONDIGITS, null).objects().toArray()[0];
			facet.setFractionDigits(val.intValue());;
			changed=true;
		}
		
		if (model.filter((Resource) value, TOTALDIGITS,null).size()>0) {
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

	private static IRI TRIPLE_CONSTRAINT = rdfFactory.createIRI("http://www.w3.org/ns/shex#TripleConstraint");
	private static IRI EACH_OF = rdfFactory.createIRI("http://www.w3.org/ns/shex#EachOf");
	private static IRI ONE_OF = rdfFactory.createIRI("http://www.w3.org/ns/shex#OneOf");
	
	private TripleExpr parseTripleExpr(Value value) {
		if (tripleSeen.contains(value) | model.filter((Resource) value,null,null).size()==0)
			return new TripleExprRef(createLabel(value));
		
		Value type = (Value) model.filter((Resource) value,TYPE_IRI,null).objects().toArray()[0];

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
	
	
	private static IRI EXPRESSIONS = rdfFactory.createIRI("http://www.w3.org/ns/shex#expressions");
	
	private TripleExpr parseEachOf(Value value) {
		List<Annotation> annotations = parseAnnotations(value);

		Value val = (Value) model.filter((Resource) value, EXPRESSIONS, null).objects().toArray()[0];
		List<TripleExpr> subExpr = new ArrayList<TripleExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseTripleExpr((Value) obj));
		
		TripleExpr res = new EachOf(subExpr,annotations);
		setLabel(res,value);

		Interval card = getInterval(value);
		if (card!=null)
			res = new RepeatedTripleExpression(res, card);
		
		return res;
	}
	
	private TripleExpr parseOneOf(Value value) {
		List<Annotation> annotations = parseAnnotations(value);

		Value val = (Value) model.filter((Resource) value, EXPRESSIONS, null).objects().toArray()[0];
		List<TripleExpr> subExpr = new ArrayList<TripleExpr>();
		for (Object obj:computeListOfObject(val))
			subExpr.add(parseTripleExpr((Value) obj));
		
		TripleExpr res = new OneOf(subExpr,annotations);
		setLabel(res,value);

		Interval card = getInterval(value);
		if (card!=null)
			res = new RepeatedTripleExpression(res, card);
		
		return res;
	}
	
	
	private static IRI PREDICATE = rdfFactory.createIRI("http://www.w3.org/ns/shex#predicate");
	private static IRI VALUE_EXPR = rdfFactory.createIRI("http://www.w3.org/ns/shex#valueExpr");
	private static IRI INVERSE = rdfFactory.createIRI("http://www.w3.org/ns/shex#inverse");
	private TripleExpr parseTripleConstraint(Value value) {
		List<Annotation> annotations = parseAnnotations(value);
		
		boolean inverse = false;
		if (model.filter((Resource) value, INVERSE, null).size()>0) {
			Literal inv = (Literal) model.filter((Resource) value, INVERSE, null).objects().toArray()[0];
			inverse = inv.booleanValue();
		}
		
		Value pred = (Value) model.filter((Resource) value, PREDICATE, null).objects().toArray()[0];
		TCProperty predicate;
		if (inverse)
			predicate = TCProperty.createInvProperty((IRI) pred);
		else
			predicate = TCProperty.createFwProperty((IRI) pred);
		
		ShapeExpr valueExpr;
		if (model.filter((Resource) value, VALUE_EXPR, null).size()>0) {
			Value val = (Value) model.filter((Resource) value, VALUE_EXPR, null).objects().toArray()[0];
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
	
	
	private static IRI ANNOTATION = rdfFactory.createIRI("http://www.w3.org/ns/shex#annotation");
	private static IRI OBJECT = rdfFactory.createIRI("http://www.w3.org/ns/shex#object");
	private List<Annotation> parseAnnotations(Value value){
		List<Annotation> annotations = null;
		if (model.filter((Resource) value, ANNOTATION, null).size()>0) {
			Value ann = (Value) model.filter((Resource) value, ANNOTATION, null).objects().toArray()[0];
			List<Object> lannot = computeListOfObject(ann);
			annotations = new ArrayList<Annotation>();
			for (Object obj:lannot) {
				IRI predicate = (IRI) model.filter((Resource) obj, PREDICATE, null).objects().toArray()[0];
				Value object = (Value) model.filter((Resource) obj, OBJECT, null).objects().toArray()[0];
				annotations.add(new Annotation(predicate,object));
			}
		}
		return annotations;
		
	}
	
	
	private static IRI MIN = rdfFactory.createIRI("http://www.w3.org/ns/shex#min");
	private static IRI MAX = rdfFactory.createIRI("http://www.w3.org/ns/shex#max");
	private Interval getInterval(Value value) {
		Integer  min=null,max=null;
		if (model.filter((Resource) value,MIN,null).size()>0)
			min =  ((Literal) model.filter((Resource) value, MIN, null).objects().toArray()[0]).intValue();
		if (model.filter((Resource) value,MAX,null).size()>0)
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
	
	private static IRI FIRST = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#first");
	private static IRI REST = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest");	
	private static IRI NIL = rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil");
	private List<Object> computeListOfObject(Value value) {
		Model model_first = model.filter((Resource) value, FIRST, null);
		Model model_rest = model.filter((Resource) value, REST, null);

		List<Object> result = new ArrayList<Object>();
		result.add(model_first.objects().toArray()[0]);
		
		Value rest = ((Value) model_rest.objects().toArray()[0]);
		if (!rest.equals(NIL))
			result.addAll(computeListOfObject(rest));
		return result;
	}
	
	
	private Label createLabel(Value value) {
		if (value instanceof IRI)
			return new Label((IRI) value);
		if (value instanceof BNode)
			return new Label((BNode) value);
		return null;
	}
	
	
	private void setLabel(ShapeExpr shape,Value value) {
		if (value instanceof IRI)
			shape.setId(new Label((IRI) value));
		if (value instanceof BNode & ! value.stringValue().startsWith("gen-id"))
			shape.setId(new  Label((BNode) value));
	}
	
	
	private void setLabel(TripleExpr triple,Value value) {
		if (value instanceof IRI)
			triple.setId(new Label((IRI) value));
		if (value instanceof BNode & ! value.stringValue().startsWith("gen-id"))
			triple.setId(new  Label((BNode) value));
	}
	
}
