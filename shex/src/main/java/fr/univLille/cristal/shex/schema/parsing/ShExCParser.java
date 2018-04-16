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
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.jena.sparql.lang.sparql_10.ParseException;
import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.Annotation;
import fr.univLille.cristal.shex.schema.abstrsynt.AnnotedObject;
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
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExCErrorListener;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocBaseVisitor;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocLexer;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocParser;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocParser.IriExclusionContext;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocParser.QualifierContext;
import fr.univLille.cristal.shex.schema.parsing.ShExC.ShExDocParser.XsFacetContext;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.util.XPath;


/** Parses a {@link ShexSchema} from its shexc representation. 
 * 
 * This implementation does not support: external definitions, semantic actions and anonymous "start" shapes.
 * @author Jérémie Dusart
 *
 */
public class ShExCParser extends ShExDocBaseVisitor<Object> implements Parser  {
	private final static ValueFactory rdfFactory = SimpleValueFactory.getInstance();
	private Map<Label,ShapeExpr> rules;
	private Map<String,String> prefixes;
	private List<String> imports;
	private String base;
	private Path filename;
	
	public Map<Label,ShapeExpr> getRules(Path path) throws Exception{
		this.filename=path;
		InputStream is = new FileInputStream(path.toFile());
		return getRules(is);		
	}
	
	public Map<Label,ShapeExpr> getRules(InputStream is) throws Exception{
		Reader isr = new InputStreamReader(is,Charset.defaultCharset().name());
		ANTLRInputStream inputStream = new ANTLRInputStream(isr);
        ShExDocLexer ShExDocLexer = new ShExDocLexer(inputStream);
        ShExDocLexer.removeErrorListeners();
        ShExDocLexer.addErrorListener(new ShExCErrorListener());
        CommonTokenStream commonTokenStream = new CommonTokenStream(ShExDocLexer);
        ShExDocParser ShExDocParser = new ShExDocParser(commonTokenStream);   
        
        ShExDocParser.setErrorHandler(new BailErrorStrategy());
        ShExDocParser.removeErrorListeners();
        ShExDocParser.addErrorListener(new ShExCErrorListener());
        
        ShExDocParser.ShExDocContext context = ShExDocParser.shExDoc();      
        rules = new HashMap<Label,ShapeExpr>();
        prefixes = new HashMap<String,String>();
        imports = new ArrayList<String>();
        base = null;
        this.visit(context);
        return rules;
	}
	
	public List<String> getImports(){
		return imports;
	}
	
	//--------------------------------------------
	// General
	//--------------------------------------------
	public Object visitDirective(ShExDocParser.DirectiveContext ctx) { return visitChildren(ctx); 	}
	
	public Object visitBaseDecl(ShExDocParser.BaseDeclContext ctx) { 
		this.base = ctx.IRIREF().getText();
		base = base.substring(1, base.length()-1);
		return null; 
	}
	
	public Object visitPrefixDecl(ShExDocParser.PrefixDeclContext ctx) { 
		String values = ctx.IRIREF().getText();
		values = values.substring(1, values.length()-1);
		prefixes.put(ctx.PNAME_NS().getText(), values);
		return null;
	}
	
	public Object visitImportDecl(ShExDocParser.ImportDeclContext ctx) { 
		String imp = (String) ctx.iri().getText();
		imp = imp.substring(1, imp.length()-1);
		imports.add(imp);
		return null;
	}
	
	@Override 
	public Object visitNotStartAction(ShExDocParser.NotStartActionContext ctx) { return visitChildren(ctx); }

	@Override 
	public Object visitStart(ShExDocParser.StartContext ctx) { return visitChildren(ctx); }

	@Override 
	public Object visitStartActions(ShExDocParser.StartActionsContext ctx) { return visitChildren(ctx); }

	@Override 
	public Object visitStatement(ShExDocParser.StatementContext ctx) { return visitChildren(ctx); }
	
	
	//--------------------------------------------
	// Shape structure
	//--------------------------------------------
	
	@Override 
	public Object visitShapeExprDecl(ShExDocParser.ShapeExprDeclContext ctx) {
		Label label = (Label) visitShapeExprLabel(ctx.shapeExprLabel());
		ShapeExpr expr = (ShapeExpr) visitShapeExpression(ctx.shapeExpression());
		expr.setId(label);
		if (rules.containsKey(label))
			throw new IllegalArgumentException("Label "+label+" allready used.");
		rules.put(label,expr);
		return visitChildren(ctx); 
	}
	
	public Object visitShapeExpression(ShExDocParser.ShapeExpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	
	public Object visitShapeOr(ShExDocParser.ShapeOrContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.ShapeAndContext)
				children.add((ShapeExpr) child.accept(this));
		}
		if (children.size()==1)
			return children.get(0);
		return new ShapeOr(children); 
	}
	
	public Object visitShapeAnd(ShExDocParser.ShapeAndContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.ShapeNotContext)
				children.add((ShapeExpr) child.accept(this));
		}
		if (children.size()==1)
			return children.get(0);
		return new ShapeAnd(children); 
	}
	
	public Object visitShapeNot(ShExDocParser.ShapeNotContext ctx) {
		if (ctx.negation()==null) {
			return ctx.shapeAtom().accept(this);
		}
		ShapeExpr result = new ShapeNot((ShapeExpr) ctx.shapeAtom().accept(this)); 
		return result;
	}

	@Override 
	public Object visitInlineShapeOr(ShExDocParser.InlineShapeOrContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.InlineShapeAndContext)
				children.add((ShapeExpr) child.accept(this));
		}
		if (children.size()==1)
			return children.get(0);
		return new ShapeOr(children); 
	}

	@Override 
	public Object visitInlineShapeAnd(ShExDocParser.InlineShapeAndContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.InlineShapeNotContext)
				children.add((ShapeExpr) child.accept(this));
		}
		if (children.size()==1)
			return children.get(0);
		return new ShapeAnd(children); 
	}


	@Override 
	public Object visitInlineShapeNot(ShExDocParser.InlineShapeNotContext ctx) { 
		if (ctx.negation()==null) {
			return ctx.inlineShapeAtom().accept(this);
		}
		return new ShapeNot((ShapeExpr) ctx.inlineShapeAtom().accept(this)); 
	}
	
	@Override 
	public Object visitInlineShapeDefinition(ShExDocParser.InlineShapeDefinitionContext ctx) {
		Set<TCProperty> extra = new HashSet<TCProperty>();
		boolean closed = false;
		for (QualifierContext qua:ctx.qualifier()) {
			if (qua.KW_CLOSED()!=null)
				closed= true;
			else if (qua.extraPropertySet()!=null){
				extra.addAll((Set<TCProperty>) qua.extraPropertySet().accept(this));
			} else {
				System.err.println("Qualifier: "+qua.getText());
				System.err.println(this.filename);

			}
		}
		TripleExpr triple = (TripleExpr) ctx.tripleExpression().accept(this);
		return new Shape(triple, extra, closed);
	}

	
	@Override
	public Object visitShapeDefinition(ShExDocParser.ShapeDefinitionContext ctx) {
		//TODO semact
		List<Annotation> annotations = null;
		if (ctx.annotation()!=null) {
			annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
		}
				
		Set<TCProperty> extra = new HashSet<TCProperty>();
		boolean closed = false;
		for (QualifierContext qua:ctx.qualifier()) {
			if (qua.KW_CLOSED()!=null)
				closed= true;
			else if (qua.extraPropertySet()!=null){
				extra.addAll((Set<TCProperty>) qua.extraPropertySet().accept(this));
			} else {
				System.err.println("Qualifier: "+qua.getText());
				System.err.println(this.filename);
			}
		}
		
		TripleExpr triple;
		if (ctx.tripleExpression()!=null)
			triple = (TripleExpr) ctx.tripleExpression().accept(this);
		else
			triple = new EmptyTripleExpression();
		
		return new Shape(triple, extra, closed,annotations);
	}
	
	@Override 
	public Object visitExtraPropertySet(ShExDocParser.ExtraPropertySetContext ctx) {
		Set<TCProperty> extra = new HashSet<TCProperty>();
		for (ShExDocParser.PredicateContext child : ctx.predicate()) {
			extra.add(TCProperty.createFwProperty((IRI) child.accept(this)));
		}
		return extra; 
	}

	@Override 
	public Object visitShapeAtomShapeOrRef(ShExDocParser.ShapeAtomShapeOrRefContext ctx) {
		return ctx.shapeOrRef().accept(this); 
	}
	
	@Override 
	public Object visitShapeAtomShapeExpression(ShExDocParser.ShapeAtomShapeExpressionContext ctx) { 
		return ctx.shapeExpression().accept(this); 
	}
	
	@Override 
	public Object visitShapeAtomAny(ShExDocParser.ShapeAtomAnyContext ctx) { 
		return new EmptyShape(); 
	}
	
	@Override 
	public Object visitInlineShapeAtomShapeOrRef(ShExDocParser.InlineShapeAtomShapeOrRefContext ctx) {
		ShapeExpr res = (ShapeExpr) ctx.inlineShapeOrRef().accept(this);
		if (ctx.nodeConstraint()!=null) {
			List<ShapeExpr> tmp = new ArrayList<ShapeExpr>();
			tmp.add(res);
			tmp.add(new NodeConstraint((List<Constraint>) ctx.nodeConstraint().accept(this)));
			return new ShapeAnd(tmp) ;
		}
		return res;
	}
	
	@Override 
	public Object visitShapeOrRef(ShExDocParser.ShapeOrRefContext ctx) {
		if (ctx.shapeDefinition()!=null)
			return ctx.shapeDefinition().accept(this);
		return ctx.shapeRef().accept(this);
	}
	
	@Override 
	public Object visitInlineShapeAtomShapeExpression(ShExDocParser.InlineShapeAtomShapeExpressionContext ctx) { 
		return ctx.shapeExpression().accept(this); 
	}
	
	@Override 
	public Object visitInlineShapeAtomAny(ShExDocParser.InlineShapeAtomAnyContext ctx) { 
		return new EmptyShape();
	}
	
	@Override 
	public Object visitShapeRef(ShExDocParser.ShapeRefContext ctx) { 
		if (ctx.ATPNAME_LN()!=null) {
			String ref = ctx.getText().substring(1);
			String prefix = ref.split(":")[0]+":";
			String name = ref.split(":")[1];
			return new ShapeExprRef(new Label(rdfFactory.createIRI(prefixes.get(prefix)+name))); 
		}
		if (ctx.ATPNAME_NS()!=null) {
			String prefix = ctx.getText().substring(1);
			return new ShapeExprRef(new Label(rdfFactory.createIRI(prefixes.get(prefix)))); 		}
		
		return new ShapeExprRef((Label) ctx.shapeExprLabel().accept(this)); 
	}
	
	
	
	//--------------------------------------------
	// Node constraints
	//--------------------------------------------

	@Override 
	public Object visitShapeAtomNodeConstraint(ShExDocParser.ShapeAtomNodeConstraintContext ctx) { 
		NodeConstraint res = new NodeConstraint((List<Constraint>) ctx.nodeConstraint().accept(this));
		if (ctx.shapeOrRef()!=null) {
			List<ShapeExpr> tmp = new ArrayList<ShapeExpr>();
			tmp.add(res);
			tmp.add((ShapeExpr) ctx.shapeOrRef().accept(this));
			return new ShapeAnd(tmp) ;
		}
		return res;
	}
	
	@Override 
	public Object visitInlineShapeAtomNodeConstraint(ShExDocParser.InlineShapeAtomNodeConstraintContext ctx) {
		NodeConstraint res = new NodeConstraint((List<Constraint>) ctx.nodeConstraint().accept(this));
		if (ctx.inlineShapeOrRef()!=null) {
			List<ShapeExpr> tmp = new ArrayList<ShapeExpr>();
			tmp.add(res);
			tmp.add((ShapeExpr) ctx.inlineShapeOrRef().accept(this));
			return new ShapeAnd(tmp) ;
		}
		return res;
	}
		
	@Override 
	public Object visitNodeConstraintLiteral(ShExDocParser.NodeConstraintLiteralContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add(NodeKindConstraint.AllLiteral);
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add((Constraint) facet.accept(this));
		}
		return cleanConstraint(constraints);
	}
	
	@Override 
	public Object visitNodeConstraintNonLiteral(ShExDocParser.NodeConstraintNonLiteralContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add((Constraint) ctx.nonLiteralKind().accept(this));
		for (ShExDocParser.StringFacetContext facet : ctx.stringFacet()) {
			constraints.add((Constraint) facet.accept(this));
		}
		return cleanConstraint(constraints);
	}
	
	@Override 
	public Object visitNodeConstraintDatatype(ShExDocParser.NodeConstraintDatatypeContext ctx) { 
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add(new DatatypeConstraint((IRI) ctx.datatype().accept(this)));
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add((Constraint) facet.accept(this));
		}
		return cleanConstraint(constraints);
	}
	
	@Override 
	public Object visitNodeConstraintValueSet(ShExDocParser.NodeConstraintValueSetContext ctx) { 
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add((Constraint) ctx.valueSet().accept(this));
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add((Constraint) facet.accept(this));
		}
		return cleanConstraint(constraints);
	}
	
	@Override 
	public Object visitNodeConstraintFacet(ShExDocParser.NodeConstraintFacetContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add((Constraint) facet.accept(this));
		}
		return cleanConstraint(constraints);
	}
	
	@Override 
	public Object visitNonLiteralKind(ShExDocParser.NonLiteralKindContext ctx) {
		if (ctx.KW_NONLITERAL()!=null)
			return NodeKindConstraint.AllNonLiteral;
		else if (ctx.KW_BNODE()!=null)
			return NodeKindConstraint.Blank;
		else 
			return NodeKindConstraint.AllIRI;
	}
	
	@Override 
	public Object visitXsFacet(ShExDocParser.XsFacetContext ctx) {
		if (ctx.stringFacet()!=null)
			return ctx.stringFacet().accept(this);
		return ctx.numericFacet().accept(this);
	}
	
	@Override 
	public Object visitStringFacet(ShExDocParser.StringFacetContext ctx) {
		FacetStringConstraint result = new FacetStringConstraint();
		if (ctx.REGEXP()!=null) {
			if (ctx.REGEXP_FLAGS()!=null)
				result.setFlags(ctx.REGEXP_FLAGS().getText());
			String pattern = ctx.REGEXP().getText();
			pattern = pattern.substring(1,pattern.length()-1);
			pattern = XPath.normalizeRegex(pattern);
			result.setPattern(pattern);
		}else {
			int val = Integer.parseInt(ctx.INTEGER().getText());
			if (ctx.stringLength().KW_LENGTH()!=null) 
				result.setLength(val);
			else if (ctx.stringLength().KW_MINLENGTH()!=null) 
				result.setMinLength(val);
			else if (ctx.stringLength().KW_MAXLENGTH()!=null) 
				result.setMaxLength(val);
		}
		return result;
	}
	
	@Override 
	public Object visitNumericFacet(ShExDocParser.NumericFacetContext ctx) {
		FacetNumericConstraint result = new FacetNumericConstraint();
		if (ctx.numericLength()!=null) {
			if (ctx.numericLength().KW_FRACTIONDIGITS()!=null)
				result.setFractionDigits(Integer.parseInt(ctx.INTEGER().getText()));
			if (ctx.numericLength().KW_TOTALDIGITS()!=null)
				result.setTotalDigits(Integer.parseInt(ctx.INTEGER().getText()));
		}else {
			Literal lnode;
			if (ctx.numericLiteral()!=null) {
				lnode = (Literal) ctx.numericLiteral().accept(this);
			} else {
				lnode = rdfFactory.createLiteral((String) ctx.string().accept(this),
						(IRI) ctx.datatype().accept(this));
			}
			BigDecimal value = lnode.decimalValue();
			if (ctx.numericRange().KW_MAXEXCLUSIVE()!=null)
				result.setMaxexcl(value);
			if (ctx.numericRange().KW_MAXINCLUSIVE()!=null)
				result.setMaxincl(value);
			if (ctx.numericRange().KW_MINEXCLUSIVE()!=null)
				result.setMinexcl(value);
			if (ctx.numericRange().KW_MININCLUSIVE()!=null)
				result.setMinincl(value);
				
		}
		return result; 
	}
	
	// must return a setOfNode
	@Override
	public Object visitValueSet(ShExDocParser.ValueSetContext ctx) {
		Set<Value> explicitValues = new HashSet<Value>();
		Set<Constraint> constraint = new HashSet<Constraint>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.ValueSetValueContext) {
				Object res = child.accept(this);
				if (res instanceof Value)
					explicitValues.add((Value) res);
				else
					constraint.add((Constraint) res);
			}
		}
		
		return new ValueSetValueConstraint(explicitValues, constraint); 
	}

	@Override 
	public Object visitValueSetValue(ShExDocParser.ValueSetValueContext ctx) {
		if(ctx.children.get(0).getText().equals(".")) {
			Set<Value> explicitValues = new HashSet<Value>();
			Set<Constraint> exclusions = new HashSet<Constraint>();
			for (ParseTree child:ctx.children){
				if (! (child instanceof TerminalNodeImpl)) {
					Object res = child.accept(this);
					if (res instanceof IRI)
						explicitValues.add((Value) res);
					else
						exclusions.add((Constraint) res);
				}
			}
			if (ctx.iriExclusion()!=null)
				return new IRIStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);
			if (ctx.literalExclusion()!=null)
				return new LiteralStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);
			if (ctx.languageExclusion()!=null)
				return new LanguageStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);
		}
		return visitChildren(ctx);
	}
		
	@Override
	public Object visitIriRange(ShExDocParser.IriRangeContext ctx) {
		if (ctx.STEM_MARK()==null)
			return (IRI) ctx.iri().accept(this);
		IRIStemConstraint stem = new IRIStemConstraint(((IRI) ctx.iri().accept(this)).stringValue());
		if (ctx.iriExclusion()==null)
			return stem;
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<Value> explicitValues = new HashSet<Value>();
		for(IriExclusionContext exclu:ctx.iriExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof IRI)
				explicitValues.add((IRI) res);
			else
				exclusions.add((Constraint) res);
		}
		if (exclusions.size()==0 && explicitValues.size()==0)
			return stem;
		return new IRIStemRangeConstraint(stem, explicitValues, exclusions); 
	}
	
	@Override 
	public Object visitIriExclusion(ShExDocParser.IriExclusionContext ctx) {
		if (ctx.STEM_MARK()!=null)
			return new IRIStemConstraint(((IRI) ctx.iri().accept(this)).stringValue());
		return (IRI) ctx.iri().accept(this);
	}
	
	@Override 
	public Object visitLiteralRange(ShExDocParser.LiteralRangeContext ctx) {
		Literal val = (Literal) ctx.literal().accept(this);
		if (ctx.STEM_MARK()==null) {
			return val;
		}
		LiteralStemConstraint stem = new LiteralStemConstraint(val.stringValue());
		if (ctx.literalExclusion()==null)
			return stem;
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<Value> explicitValues = new HashSet<Value>();
		for(ShExDocParser.LiteralExclusionContext exclu:ctx.literalExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof Value)
				explicitValues.add((Literal) res);
			else
				exclusions.add((Constraint) res);
		}
		if (exclusions.size()==0 && explicitValues.size()==0)
			return stem;
		return new LiteralStemRangeConstraint(stem, explicitValues, exclusions); 
	}
	
	@Override 
	public Object visitLiteralExclusion(ShExDocParser.LiteralExclusionContext ctx) {
		Literal val = (Literal) ctx.literal().accept(this);
		if (ctx.STEM_MARK()!=null)
			return new LiteralStemConstraint(val.stringValue());
		return val;
	}
	
	@Override
	public Object visitLanguageRange(ShExDocParser.LanguageRangeContext ctx) {
		String langtag = ctx.LANGTAG().getText().substring(1);
		if (ctx.STEM_MARK()==null)
			return new LanguageConstraint(langtag);
		LanguageStemConstraint stem = new LanguageStemConstraint(langtag);
		if (ctx.languageExclusion()==null)
			return stem;
		Set<Constraint> exclusions = new HashSet<Constraint>();
		Set<Value> explicitValues = new HashSet<Value>();
		for(ShExDocParser.LanguageExclusionContext exclu:ctx.languageExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof Value)
				explicitValues.add((Literal) res);
			else
				exclusions.add((Constraint) res);
		}
		if (exclusions.size()==0 && explicitValues.size()==0)
			return stem;
		return new LanguageStemRangeConstraint(stem, explicitValues, exclusions); 
	}
	
	@Override 
	public Object visitLanguageExclusion(ShExDocParser.LanguageExclusionContext ctx) { 
		String langtag = ctx.LANGTAG().getText().substring(1);
		if (ctx.STEM_MARK()==null)
			return new LanguageConstraint(langtag);
		return  new LanguageStemConstraint(langtag);
	}
	
	//--------------------------------------------
	// Triple structure
	//--------------------------------------------
	@Override 
	public Object visitTripleExpression(ShExDocParser.TripleExpressionContext ctx) { 
		return ctx.oneOfTripleExpr().accept(this); 
	}

	@Override 
	public Object visitOneOfTripleExpr(ShExDocParser.OneOfTripleExprContext ctx) {
		return visitChildren(ctx);
	}

	@Override 
	public Object visitMultiElementOneOf(ShExDocParser.MultiElementOneOfContext ctx) { 
		List<TripleExpr> exprs = new ArrayList<TripleExpr>();
		for(ParseTree child:ctx.children){
			if (child instanceof ShExDocParser.GroupTripleExprContext)
				exprs.add((TripleExpr) child.accept(this));
		}
		if (exprs.size()==1)
			return exprs.get(0);
		return new OneOf(exprs); 
	}

	@Override 
	public Object visitInnerTripleExpr(ShExDocParser.InnerTripleExprContext ctx) { 
		return visitChildren(ctx); 
	}

	@Override 
	public Object visitGroupTripleExpr(ShExDocParser.GroupTripleExprContext ctx) {
		if (ctx.singleElementGroup()!=null)
			return ctx.singleElementGroup().accept(this);
		return ctx.multiElementGroup().accept(this);
	}

	@Override 
	public Object visitSingleElementGroup(ShExDocParser.SingleElementGroupContext ctx) { 
		Object res = ctx.unaryTripleExpr().accept(this); 
		return res;
	}

	@Override 
	public Object visitMultiElementGroup(ShExDocParser.MultiElementGroupContext ctx) { 
		List<TripleExpr> exprs = new ArrayList<TripleExpr>();
		for(ParseTree child:ctx.children){
			if (child instanceof ShExDocParser.UnaryTripleExprContext)
				exprs.add((TripleExpr) child.accept(this));
		}
		return new EachOf(exprs); 
	}

	@Override 
	public Object visitUnaryTripleExpr(ShExDocParser.UnaryTripleExprContext ctx) { 
		TripleExpr result;
		if (ctx.tripleConstraint()!=null) {
			result = (TripleExpr) ctx.tripleConstraint().accept(this);
		} else if (ctx.bracketedTripleExpr()!=null){
			result = (TripleExpr) ctx.bracketedTripleExpr().accept(this);
		} else {
			result = (TripleExpr) ctx.include().accept(this);
		}
		if (ctx.tripleExprLabel() != null) {
			result.setId((Label) ctx.tripleExprLabel().accept(this));
		}
		return result; 
	}

	@Override 
	public Object visitBracketedTripleExpr(ShExDocParser.BracketedTripleExprContext ctx) {
		TripleExpr result = (TripleExpr) ctx.innerTripleExpr().accept(this);
		
		List<Annotation> annotations = null;
		if (ctx.annotation()!=null) {
			annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
		}
		
		((AnnotedObject) result).setAnnotations(annotations);
		
		if (ctx.cardinality()!=null){
			Interval card = (Interval) ctx.cardinality().accept(this);
			result = new RepeatedTripleExpression(result, card);
		}
		return result; 
	}
	
	@Override 
	public Object visitStarCardinality(ShExDocParser.StarCardinalityContext ctx) { 
		return Interval.STAR; 
	}

	@Override 
	public Object visitPlusCardinality(ShExDocParser.PlusCardinalityContext ctx) { 
		return Interval.PLUS; 
	}
	
	@Override 
	public Object visitOptionalCardinality(ShExDocParser.OptionalCardinalityContext ctx) { 
		return Interval.OPT; 
	}
	
	@Override 
	public Object visitRepeatCardinality(ShExDocParser.RepeatCardinalityContext ctx) {
		int min = Integer.parseInt(ctx.repeatRange().min_range().getText());
		if (ctx.repeatRange().max_range() != null) {
			if (ctx.repeatRange().max_range().INTEGER()!=null) 
				return new Interval(min,Integer.parseInt(ctx.repeatRange().max_range().getText())); 
			return new Interval(min,Interval.UNBOUND);
		}
		return new Interval(min,min);
	}


	@Override 
	public Object visitTripleConstraint(ShExDocParser.TripleConstraintContext ctx) { 
		IRI predicate = (IRI) ctx.predicate().accept(this);
		TCProperty tcp;
		if (ctx.senseFlags()==null)
			tcp = TCProperty.createFwProperty(predicate);
		else
			tcp = TCProperty.createInvProperty(predicate);
		ShapeExpr expr = (ShapeExpr) ctx.inlineShapeExpression().accept(this);
		if (expr==null) {
			expr = new EmptyShape();
		}
		
		List<Annotation> annotations = null;
		if (ctx.annotation()!=null) {
			annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
		}
		
		TripleExpr res = new TripleConstraint(tcp, expr,annotations);
		// TODO semact annotations
		if (ctx.cardinality()!=null) {
			Interval card = (Interval) ctx.cardinality().accept(this);
			res = new RepeatedTripleExpression(res,card);
		}
		return res;
	}
	
	@Override 
	public Object visitInclude(ShExDocParser.IncludeContext ctx) { 
		return new TripleExprRef((Label) ctx.tripleExprLabel().accept(this)); 
	}

	//--------------------------------------------
	// Utils
	//--------------------------------------------

	public Object cleanConstraint(List<Constraint> constraints) {
		List<Constraint> result = new ArrayList<Constraint>();
		
		FacetNumericConstraint fn=null;
		FacetStringConstraint sn=null;
		NodeKindConstraint nk=null;
		for (Constraint ct:constraints) {
			boolean dealt = false;
			if (ct instanceof FacetNumericConstraint) {
				dealt=true;
				if (fn==null) {
					fn = (FacetNumericConstraint) ct;
				}else{
					fn.setFractionDigits(((FacetNumericConstraint) ct).getFractionDigits());
					fn.setTotalDigits(((FacetNumericConstraint) ct).getTotalDigits());
					fn.setMaxexcl(((FacetNumericConstraint) ct).getMaxexcl());
					fn.setMaxincl(((FacetNumericConstraint) ct).getMaxincl());
					fn.setMinexcl(((FacetNumericConstraint) ct).getMinexcl());
					fn.setMinincl(((FacetNumericConstraint) ct).getMinincl());
				}
			}
			if (ct instanceof FacetStringConstraint) {
				dealt=true;
				if (sn==null) {
					sn = (FacetStringConstraint) ct;
				}else{
					sn.setFlags(((FacetStringConstraint) ct).getFlags());
					sn.setPattern(((FacetStringConstraint) ct).getPatternString());
					sn.setLength(((FacetStringConstraint) ct).getLength());
					sn.setMaxlength(((FacetStringConstraint) ct).getMaxlength());
					sn.setMinLength(((FacetStringConstraint) ct).getMinlength());
				}
			}
			if (ct instanceof NodeKindConstraint) {
				dealt=true;
				if (nk == null) {
					nk = (NodeKindConstraint) ct;
				} else {
					throw new ParseCancellationException("Multiple nodekind constraint found.");
				}
			}
			if (!dealt)
				result.add(ct);
			
		}
		if (fn!=null) result.add(fn);
		if (sn!=null) result.add(sn);
		if (nk!=null) result.add(nk);
		
		return result;
	}
	
	public Object visitPredicate(ShExDocParser.PredicateContext ctx) { 
		if (ctx.iri() != null)
			return ctx.iri().accept(this);
		return rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"); 
	}
	
	public Object visitAnnotation(ShExDocParser.AnnotationContext ctx) {
		IRI predicate = (IRI) ctx.predicate().accept(this);
		Value obj;
		if (ctx.iri() != null)
			obj = (Value) ctx.iri().accept(this);
		else
			obj = (Value) ctx.literal().accept(this);
		return new Annotation(predicate,obj); 
	}
	
	
	public Object visitShapeExprLabel(ShExDocParser.ShapeExprLabelContext ctx) {
		Object result = visitChildren(ctx);
		if (result instanceof IRI) 
			return new Label((IRI) result);
		else 
			return new Label((BNode) result);
	}
	
	public Object visitTripleExprLabel(ShExDocParser.TripleExprLabelContext ctx) {
		Object result = visitChildren(ctx);
		if (result instanceof IRI) 
			return new Label((IRI) result);
		else 
			return new Label((BNode) result);
	}
		
	public Object visitIri(ShExDocParser.IriContext ctx) {
		TerminalNode iri = ctx.IRIREF();
		if (iri != null) {
			String iris = iri.getText();
			iris = iris.substring(1,iris.length()-1);
			if (base!=null)
				return rdfFactory.createIRI(base,iris);
			return rdfFactory.createIRI(iris);
		}
		return ctx.prefixedName().accept(this); 
	}
	
	@Override 
	public Object visitPrefixedName(ShExDocParser.PrefixedNameContext ctx) {
		if (ctx.PNAME_NS()!=null)
			return rdfFactory.createIRI(prefixes.get(ctx.PNAME_NS().getText()));

		String prefix = ctx.PNAME_LN().getText().split(":")[0]+":";
		String value = (ctx.PNAME_LN().getText().replaceAll(prefix, prefixes.get(prefix)));
		return rdfFactory.createIRI(value); 
	}

	
	@Override 
	public Object visitNumericLiteral(@NotNull ShExDocParser.NumericLiteralContext ctx) { 
		if (ctx.INTEGER()!=null)
			return  rdfFactory.createLiteral(ctx.INTEGER().getText(),XMLSchema.INTEGER);
		if (ctx.DOUBLE()!=null)
			return  rdfFactory.createLiteral(ctx.DOUBLE().getText(),XMLSchema.DOUBLE);
		return rdfFactory.createLiteral(ctx.DECIMAL().getText(),XMLSchema.DECIMAL);
	}
	
	@Override 
	public Object visitRdfLiteral(@NotNull ShExDocParser.RdfLiteralContext ctx) {
		String value = (String) ctx.string().accept(this);
		if (ctx.datatype() != null)
			return rdfFactory.createLiteral(value, (IRI) ctx.datatype().accept(this));
		if (ctx.LANGTAG()!=null)
			return rdfFactory.createLiteral(value, ctx.LANGTAG().getText().substring(1));
		return rdfFactory.createLiteral(value);
	}

	@Override 
	public Object visitBooleanLiteral(@NotNull ShExDocParser.BooleanLiteralContext ctx) {
		if (ctx.KW_FALSE()!=null)
			return rdfFactory.createLiteral(false);
		return rdfFactory.createLiteral(true);
	}

	
	@Override 
	public Object visitString(ShExDocParser.StringContext ctx) { 
		String result = "";
		
		if (ctx.STRING_LITERAL1()!=null) {
			result = new String(ctx.STRING_LITERAL1().getText());
		}
		if (ctx.STRING_LITERAL2()!=null) {
			result = ctx.STRING_LITERAL2().toString();
		}
		if (ctx.STRING_LITERAL_LONG1()!=null) {
			result = ctx.STRING_LITERAL_LONG1().toString();
		}
		if (ctx.STRING_LITERAL_LONG2()!=null) {
			result = ctx.STRING_LITERAL_LONG2().toString();
		}
		result = result.substring(1, result.length()-1);
		return XPath.unescapeJavaString(result);
	}
	
	public Object visitBlankNode(ShExDocParser.BlankNodeContext ctx) { 
		return rdfFactory.createBNode(ctx.BLANK_NODE_LABEL().getText().substring(2));
	}
}
