package fr.inria.lille.shexjava.schema.parsing;

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

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.simple.Types;
import org.apache.commons.text.StringEscapeUtils;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.Annotation;
import fr.inria.lille.shexjava.schema.abstrsynt.AnnotedObject;
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
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExternal;
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
import fr.inria.lille.shexjava.schema.concrsynt.ValueConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.ValueSetValueConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.WildcardConstraint;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExCErrorListener;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExCErrorStrategy;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocBaseVisitor;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocLexer;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.BooleanLiteralContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.CodeDeclContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.DatatypeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.EncapsulatedShapeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.GroupShapeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.IncludeSetContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeAndContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeAtomAnyContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeAtomNodeConstraintContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeAtomShapeExpressionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeAtomShapeOrRefContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeExpressionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeNotContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.InlineShapeOrRefContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.IriExclusionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.IriRangeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.LanguageExclusionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.LanguageRangeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.LiteralContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.LiteralExclusionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.LiteralRangeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.MultiElementGroupContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.MultiElementOneOfContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.NegationContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.NodeConstraintFacetContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.NumericLengthContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.NumericLiteralContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.NumericRangeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.OneOfShapeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.QualifierContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.RdfLiteralContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.RdfTypeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.RepeatCardinalityContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.SemanticActionsContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.SenseFlagsContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeAndContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeAtomAnyContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeAtomNodeConstraintContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeAtomShapeExpressionContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeAtomShapeOrRefContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ShapeNotContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.SingleElementGroupContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.StringLengthContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.UnaryShapeContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.ValueSetValueContext;
import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser.XsFacetContext;
import fr.inria.lille.shexjava.util.DatatypeUtil;
import fr.inria.lille.shexjava.util.Interval;
import fr.inria.lille.shexjava.util.XPath;

public class ShExCParser extends ShExDocBaseVisitor<Object> implements Parser{
	private RDF rdfFactory;
	private Map<Label,ShapeExpr> rules;
	private Map<String,String> prefixes;
	private List<String> imports;
	private String base;
	private Path filename;
	private ShapeExpr start;

	public Map<Label,ShapeExpr> getRules(Path path) throws Exception{
		return getRules(GlobalFactory.RDFFactory,path);
	}


	public Map<Label,ShapeExpr> getRules(RDF rdfFactory, Path path) throws Exception{
		this.filename=path;
		InputStream is = new FileInputStream(path.toFile());
		return getRules(rdfFactory,is);		
	}


	public Map<Label,ShapeExpr> getRules(InputStream is) throws Exception{
		return getRules(GlobalFactory.RDFFactory,is);
	}


	public Map<Label,ShapeExpr> getRules(RDF rdfFactory, InputStream is) throws Exception{
		this.rdfFactory = rdfFactory;
		this.start = null;
		rules = new HashMap<Label,ShapeExpr>();
		prefixes = new HashMap<String,String>();
		imports = new ArrayList<String>();
		base = null;
		
		Reader isr = new InputStreamReader(is,Charset.defaultCharset().name());
		CharStream inputStream = CharStreams.fromReader(isr);
		ShExDocLexer ShExDocLexer = new ShExDocLexer(inputStream);
		ShExDocLexer.removeErrorListeners();
		ShExDocLexer.addErrorListener(new ShExCErrorListener());
		CommonTokenStream commonTokenStream = new CommonTokenStream(ShExDocLexer);
		ShExDocParser ShExDocParser = new ShExDocParser(commonTokenStream);   

		ShExDocParser.setErrorHandler(new ShExCErrorStrategy());
		ShExDocParser.removeErrorListeners();
		ShExDocParser.addErrorListener(new ShExCErrorListener());

		ShExDocParser.ShExDocContext context = ShExDocParser.shExDoc();      

		this.visit(context);
		if (start!=null)
			rules.put(start.getId(),start);
		return rules;
	}

	public List<String> getImports(){
		return imports;
	}

	public Map<String,String> getPrefixes(){
		return prefixes;
	}
	
	public ShapeExpr getStart() {
		return start;
	}
	//--------------------------------------------
	// General
	//--------------------------------------------

	@Override
	public Object visitShExDoc(ShExDocParser.ShExDocContext ctx) {
		return visitChildren(ctx); 
	}

	
	@Override
	public Object visitDirective(ShExDocParser.DirectiveContext ctx) {
		return visitChildren(ctx); 
	}

	@Override 
	public Object visitBaseDecl(ShExDocParser.BaseDeclContext ctx) { 
		this.base = ctx.IRIREF().getText();
		base = base.substring(1, base.length()-1);
		return null; 
	}

	@Override 
	public Object visitPrefixDecl(ShExDocParser.PrefixDeclContext ctx) { 
		String values = ctx.IRIREF().getText();
		values = values.substring(1, values.length()-1);
		prefixes.put(ctx.PNAME_NS().getText(), values);
		return null;
	}
	
	@Override 
	public Object visitImportDecl(ShExDocParser.ImportDeclContext ctx) { 
		String imp = (String) ctx.IRIREF().getText();
		imp = imp.substring(1, imp.length()-1);
		imports.add(imp);
		return null;
	}

	@Override 
	public Object visitNotStartAction(ShExDocParser.NotStartActionContext ctx) { 
		return visitChildren(ctx); 
	}

	@Override 
	public Object visitStart(ShExDocParser.StartContext ctx) {
		if (ctx.shapeExpression()!=null)
			start = visitShapeExpression(ctx.shapeExpression());
		return null; 
	}

	@Override 
	public Object visitStartActions(ShExDocParser.StartActionsContext ctx) { 
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStatement(ShExDocParser.StatementContext ctx) { 
		return visitChildren(ctx);
	}


	//--------------------------------------------
	// Shape structure
	//--------------------------------------------

	@Override 
	public ShapeExpr visitShapeExprDecl(ShExDocParser.ShapeExprDeclContext ctx) {
		Label label = (Label) visitShapeExprLabel(ctx.shapeExprLabel());
		ShapeExpr expr ;
		if (ctx.KW_EXTERNAL()!=null) {
			expr = new ShapeExternal();
		}else {
			expr = (ShapeExpr) visitShapeExpression(ctx.shapeExpression());
		}
		expr.setId(label);
		if (rules.containsKey(label))
			throw new IllegalArgumentException("Label "+label+" allready used.");
		rules.put(label,expr);
		return expr; 
	}

	@Override 
	public ShapeExpr visitShapeExpression(ShExDocParser.ShapeExpressionContext ctx) { 
		return visitShapeOr(ctx.shapeOr()); 
	}

	@Override 
	public ShapeExpr visitShapeOr(ShExDocParser.ShapeOrContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ShapeAndContext child:ctx.shapeAnd())
				children.add(visitShapeAnd(child));
		if (children.size()==1)
			return children.get(0);
		return new ShapeOr(children); 
	}
	
	@Override 
	public ShapeExpr visitShapeAnd(ShExDocParser.ShapeAndContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (ShapeNotContext child:ctx.shapeNot())
			children.add(visitShapeNot(child));
		if (children.size()==1)
			return children.get(0);
		return new ShapeAnd(children); 
	}

	@Override 
	public ShapeExpr visitShapeNot(ShExDocParser.ShapeNotContext ctx) {
		if (ctx.negation()==null)
			return (ShapeExpr) ctx.shapeAtom().accept(this);
		ShapeExpr result = new ShapeNot((ShapeExpr) ctx.shapeAtom().accept(this)); 
		return result;
	}

	// Not Used
	@Override
	public Object visitNegation(NegationContext ctx) {
		return null;
	}

	@Override
	public ShapeExpr visitInlineShapeExpression(InlineShapeExpressionContext ctx) {
		return visitInlineShapeOr(ctx.inlineShapeOr());
	}

	@Override 
	public ShapeExpr visitInlineShapeOr(ShExDocParser.InlineShapeOrContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (InlineShapeAndContext child:ctx.inlineShapeAnd())
			children.add(visitInlineShapeAnd(child));
		if (children.size()==1)
			return children.get(0);
		return new ShapeOr(children); 
	}

	@Override 
	public ShapeExpr visitInlineShapeAnd(ShExDocParser.InlineShapeAndContext ctx) { 
		List<ShapeExpr> children = new ArrayList<ShapeExpr>();
		for (InlineShapeNotContext child:ctx.inlineShapeNot())
			children.add(visitInlineShapeNot(child));
		if (children.size()==1)
			return children.get(0);
		return new ShapeAnd(children); 
	}

	@Override 
	public ShapeExpr visitInlineShapeNot(ShExDocParser.InlineShapeNotContext ctx) { 
		if (ctx.negation()==null) {
			return (ShapeExpr) ctx.inlineShapeAtom().accept(this);
		}
		return new ShapeNot((ShapeExpr) ctx.inlineShapeAtom().accept(this)); 
	}

	@Override 
	public Shape visitInlineShapeDefinition(ShExDocParser.InlineShapeDefinitionContext ctx) {
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
		if (ctx.oneOfShape()!=null) {
			triple = visitOneOfShape(ctx.oneOfShape());
		} else {
			triple = new EmptyTripleExpression();
		}				
		return new Shape(triple, extra, closed);
	}

	@Override
	public Shape visitShapeDefinition(ShExDocParser.ShapeDefinitionContext ctx) {
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
		if (ctx.oneOfShape()!=null) {
			triple = visitOneOfShape(ctx.oneOfShape());
		} else {
			triple = new EmptyTripleExpression();
		}				
		List<Annotation> annotations = null;
		if (ctx.annotation()!=null) {
			annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
		}
		
		//SemActs not used
		
		Shape result = new Shape(triple, extra, closed);
		result.setAnnotations(annotations);
		return result;
	}

	//Not used
	@Override
	public Object visitQualifier(QualifierContext ctx) {
		return null;
	}

	@Override 
	public Set<TCProperty> visitExtraPropertySet(ShExDocParser.ExtraPropertySetContext ctx) {
		Set<TCProperty> extra = new HashSet<TCProperty>();
		for (ShExDocParser.PredicateContext child : ctx.predicate())
			extra.add(TCProperty.createFwProperty((IRI) child.accept(this)));
		return extra; 
	}

	@Override
	public TripleExpr visitOneOfShape(OneOfShapeContext ctx) {
		if (ctx.groupShape()!=null)
			return visitGroupShape(ctx.groupShape());
		return visitMultiElementOneOf(ctx.multiElementOneOf());
	}

	@Override
	public TripleExpr visitMultiElementOneOf(MultiElementOneOfContext ctx) {
		ArrayList<TripleExpr> children = new ArrayList<>();
		for (GroupShapeContext child:ctx.groupShape())
			children.add(visitGroupShape(child));
		return new OneOf(children);
	}


	@Override
	public TripleExpr visitGroupShape(GroupShapeContext ctx) {
		if (ctx.multiElementGroup()!=null)
			return visitMultiElementGroup(ctx.multiElementGroup());
		return visitSingleElementGroup(ctx.singleElementGroup());
	}

	@Override
	public TripleExpr visitSingleElementGroup(SingleElementGroupContext ctx) {
		return visitUnaryShape(ctx.unaryShape());
	}

	@Override
	public TripleExpr visitMultiElementGroup(MultiElementGroupContext ctx) {
		ArrayList<TripleExpr> children = new ArrayList<>();
		for (UnaryShapeContext child:ctx.unaryShape())
			children.add(visitUnaryShape(child));
		return new EachOf(children);
	}

	@Override
	public TripleExpr visitUnaryShape(UnaryShapeContext ctx) {
		if (ctx.include()!=null)
			return visitInclude(ctx.include());
		TripleExpr res;
		if (ctx.tripleConstraint()!=null)
			res = visitTripleConstraint(ctx.tripleConstraint());
		else
			res = visitEncapsulatedShape(ctx.encapsulatedShape());
		if (ctx.tripleExprLabel()!=null)
			res.setId(visitTripleExprLabel(ctx.tripleExprLabel()));
		return res;
	}

	@Override
	public TripleExpr visitEncapsulatedShape(EncapsulatedShapeContext ctx) {
		TripleExpr result = visitOneOfShape(ctx.oneOfShape());//(ctx.innerShape());
		
		if (ctx.annotation()!=null && !ctx.annotation().isEmpty()) {
			List<Annotation> annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
			((AnnotedObject) result).setAnnotations(annotations);
		}
				
		if (ctx.cardinality()!=null){
			Interval card = (Interval) ctx.cardinality().accept(this);
			result = new RepeatedTripleExpression(result, card);
		}
		
		//SemActs not used
		
		return result; 
	}

	@Override
	public ShapeExpr visitShapeAtomNodeConstraint(ShapeAtomNodeConstraintContext ctx) {
		ShapeExpr result = (ShapeExpr) ctx.nodeConstraint().accept(this);
		if (ctx.shapeOrRef()!=null) {
			List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
			expressions.add(result);
			expressions.add(visitShapeOrRef(ctx.shapeOrRef()));
			result = new ShapeAnd(expressions);
		}
		return result;
	}

	@Override
	public ShapeExpr visitShapeAtomShapeOrRef(ShapeAtomShapeOrRefContext ctx) {
		return visitShapeOrRef(ctx.shapeOrRef());
	}

	@Override
	public ShapeExpr visitShapeAtomShapeExpression(ShapeAtomShapeExpressionContext ctx) {
		return visitShapeExpression(ctx.shapeExpression());
	}

	@Override
	public EmptyShape visitShapeAtomAny(ShapeAtomAnyContext ctx) {
		return  new EmptyShape();
	}

	@Override
	public Object visitInlineShapeAtomNodeConstraint(InlineShapeAtomNodeConstraintContext ctx) {
		ShapeExpr result = (ShapeExpr) ctx.nodeConstraint().accept(this);
		if (ctx.inlineShapeOrRef()!=null) {
			List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
			expressions.add(result);
			expressions.add(visitInlineShapeOrRef(ctx.inlineShapeOrRef()));
			result = new ShapeAnd(expressions);
		}
		return result;
	}

	@Override
	public Object visitInlineShapeAtomShapeOrRef(InlineShapeAtomShapeOrRefContext ctx) {
		ShapeExpr result = visitInlineShapeOrRef(ctx.inlineShapeOrRef());
		if (ctx.nodeConstraint()!=null) {
			List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
			expressions.add(result);
			expressions.add((ShapeExpr) ctx.nodeConstraint().accept(this));
			result = new ShapeAnd(expressions);
		}
		return result;
	}

	@Override
	public Object visitInlineShapeAtomShapeExpression(InlineShapeAtomShapeExpressionContext ctx) {
		return visitShapeExpression(ctx.shapeExpression());
	}

	@Override
	public Object visitInlineShapeAtomAny(InlineShapeAtomAnyContext ctx) {
		return  new EmptyShape();
	}

	@Override 
	public NodeConstraint visitNodeConstraintLiteral(ShExDocParser.NodeConstraintLiteralContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add(NodeKindConstraint.LiteralKind);
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add(visitXsFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
	}

	@Override 
	public NodeConstraint visitNodeConstraintNonLiteral(ShExDocParser.NodeConstraintNonLiteralContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add((Constraint) ctx.nonLiteralKind().accept(this));
		for (ShExDocParser.StringFacetContext facet : ctx.stringFacet()) {
			constraints.add(visitStringFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
	}
	
	@Override 
	public NodeConstraint visitNodeConstraintDatatype(ShExDocParser.NodeConstraintDatatypeContext ctx) { 
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add(new DatatypeConstraint((IRI) ctx.datatype().accept(this)));
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add(visitXsFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
	}

	@Override 
	public NodeConstraint visitNodeConstraintValueSet(ShExDocParser.NodeConstraintValueSetContext ctx) { 
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		constraints.add((Constraint) ctx.valueSet().accept(this));
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add(visitXsFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
	}

	@Override
	public Object visitNodeConstraintFacet(NodeConstraintFacetContext ctx) {
		List<Constraint> constraints = new ArrayList<Constraint>() ;
		for (XsFacetContext facet : ctx.xsFacet()) {
			constraints.add(visitXsFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
	}

	@Override 
	public NodeKindConstraint visitNonLiteralKind(ShExDocParser.NonLiteralKindContext ctx) {
		if (ctx.KW_NONLITERAL()!=null)
			return NodeKindConstraint.NonLiteralKind;
		else if (ctx.KW_BNODE()!=null)
			return NodeKindConstraint.BNodeKind;
		else 
			return NodeKindConstraint.IRIKind;
	}

	@Override
	public Constraint visitXsFacet(XsFacetContext ctx) {
		if (ctx.stringFacet()!=null)
			return visitStringFacet(ctx.stringFacet());
		return visitNumericFacet(ctx.numericFacet());
	}

	@Override 
	public FacetStringConstraint visitStringFacet(ShExDocParser.StringFacetContext ctx) {
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

	//not used
	@Override
	public Constraint visitStringLength(StringLengthContext ctx) {
		return null;
	}

	@Override
	public FacetNumericConstraint visitNumericFacet(ShExDocParser.NumericFacetContext ctx) {
		FacetNumericConstraint result = new FacetNumericConstraint();
		if (ctx.numericLength()!=null) {
			int val = Integer.parseInt(ctx.INTEGER().getText());
			if (ctx.numericLength().KW_FRACTIONDIGITS()!=null)
				result.setFractionDigits(val);
			if (ctx.numericLength().KW_TOTALDIGITS()!=null)
				result.setTotalDigits(val);
		}else {
			Literal lnode = visitNumericLiteral(ctx.numericLiteral());
			try {
				BigDecimal value = DatatypeUtil.getDecimalValue(lnode);
				if (ctx.numericRange().KW_MAXEXCLUSIVE()!=null)
					result.setMaxexcl(value);
				if (ctx.numericRange().KW_MAXINCLUSIVE()!=null)
					result.setMaxincl(value);
				if (ctx.numericRange().KW_MINEXCLUSIVE()!=null)
					result.setMinexcl(value);
				if (ctx.numericRange().KW_MININCLUSIVE()!=null)
					result.setMinincl(value);
			}catch (NumberFormatException e) {
				throw new NumberFormatException(lnode.toString());
			}
				
		}
		return result; 
	}

	//not used
	@Override
	public Object visitNumericRange(NumericRangeContext ctx) {
		return null;
	}
	
	//not used
	@Override
	public Object visitNumericLength(NumericLengthContext ctx) {
		return null;
	}

	@Override 
	public TripleExpr visitTripleConstraint(ShExDocParser.TripleConstraintContext ctx) { 
		IRI predicate = visitPredicate(ctx.predicate());
		
		TCProperty tcp;
		if (ctx.senseFlags()==null)
			tcp = TCProperty.createFwProperty(predicate);
		else
			tcp = TCProperty.createInvProperty(predicate);
		
		ShapeExpr expr = visitInlineShapeExpression(ctx.inlineShapeExpression());

		TripleExpr res = new TripleConstraint(tcp, expr);
	
		if (ctx.annotation()!=null) {
			List<Annotation> annotations = new ArrayList<Annotation>();
			for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
				annotations.add((Annotation) annotContext.accept(this));
			((AnnotedObject) res).setAnnotations(annotations);
		}
		
		// SemAct not used
		if (ctx.cardinality()!=null) 
			res = new RepeatedTripleExpression(res,(Interval) ctx.cardinality().accept(this));
		
		return res;
	}

	//Not used
	@Override
	public Object visitSenseFlags(SenseFlagsContext ctx) {
		return null;
	}

	@Override
	public ValueSetValueConstraint visitValueSet(ShExDocParser.ValueSetContext ctx) {
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		Set<ValueConstraint> constraint = new HashSet<ValueConstraint>();
		for (ParseTree child:ctx.children) {
			if (child instanceof ShExDocParser.ValueSetValueContext) {
				Object res = child.accept(this);
				if (res instanceof RDFTerm)
					explicitValues.add((RDFTerm) res);
				else
					constraint.add((ValueConstraint) res);
			}
		}
		return new ValueSetValueConstraint(explicitValues, constraint); 
	}

   // Return either an explicit value (in the first three return it can) or a Constraint?
	@Override
	public Object visitValueSetValue(ValueSetValueContext ctx) {
		if (ctx.iriRange()!=null) 
			return visitIriRange(ctx.iriRange());
		if (ctx.literalRange()!=null) 
			return visitLiteralRange(ctx.literalRange());
		if (ctx.languageRange()!=null) 
			return visitLanguageRange(ctx.languageRange());
		// We are in the last case
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		Set<ValueConstraint> exclusions = new HashSet<ValueConstraint>();
		for (ParseTree child:ctx.children){
			if (! (child instanceof TerminalNodeImpl)) {
				Object res = child.accept(this);
				if (res instanceof RDFTerm)
					explicitValues.add((RDFTerm) res);
				else
					exclusions.add((ValueConstraint) res);
			}
		}
		if (ctx.iriExclusion()!=null)
			return new IRIStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);
		if (ctx.literalExclusion()!=null)
			return new LiteralStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);
		if (ctx.languageExclusion()!=null)
			return new LanguageStemRangeConstraint(new WildcardConstraint(), explicitValues, exclusions);

		return null;
	}

	@Override
	public Object visitIriRange(IriRangeContext ctx) {
		if (ctx.STEM_MARK()==null)
			return visitIri(ctx.iri());
		
		IRIStemConstraint stem = new IRIStemConstraint(visitIri(ctx.iri()).getIRIString());
		if (ctx.iriExclusion()==null)
			return stem;
		
		Set<ValueConstraint> exclusions = new HashSet<ValueConstraint>();
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		for(IriExclusionContext exclu:ctx.iriExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof IRI)
				explicitValues.add((IRI) res);
			else
				exclusions.add((ValueConstraint) res);
		}
		
		return new IRIStemRangeConstraint(stem, explicitValues, exclusions); 
	}

	@Override
	public Object visitIriExclusion(IriExclusionContext ctx) {
		if (ctx.STEM_MARK()!=null)
			return new IRIStemConstraint(visitIri(ctx.iri()).getIRIString());
		return visitIri(ctx.iri());
	}

	@Override
	public Object visitLiteralRange(LiteralRangeContext ctx) {
		Literal val = visitLiteral(ctx.literal());
		if (ctx.STEM_MARK()==null) {
			return val;
		}
		LiteralStemConstraint stem = new LiteralStemConstraint(val.getLexicalForm());
		if (ctx.literalExclusion()==null)
			return stem;
		
		Set<ValueConstraint> exclusions = new HashSet<ValueConstraint>();
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		for(ShExDocParser.LiteralExclusionContext exclu:ctx.literalExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof RDFTerm)
				explicitValues.add((Literal) res);
			else
				exclusions.add((ValueConstraint) res);
		}
		
		return new LiteralStemRangeConstraint(stem, explicitValues, exclusions); 
	}

	@Override
	public Object visitLiteralExclusion(LiteralExclusionContext ctx) {
		Literal val = visitLiteral(ctx.literal());
		if (ctx.STEM_MARK()!=null)
			return new LiteralStemConstraint(val.getLexicalForm());
		return val;
	}

	@Override
	public Object visitLanguageRange(LanguageRangeContext ctx) {
		String langtag="";
		if (ctx.LANGTAG()!=null) {
			langtag = ctx.LANGTAG().getText().substring(1);
			if (ctx.STEM_MARK()==null)
				return new LanguageConstraint(langtag);
		}
		
		LanguageStemConstraint stem = new LanguageStemConstraint(langtag);
		if (ctx.languageExclusion()==null)
			return stem;
		
		Set<ValueConstraint> exclusions = new HashSet<ValueConstraint>();
		Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
		for(ShExDocParser.LanguageExclusionContext exclu:ctx.languageExclusion()) {
			Object res = exclu.accept(this);
			if (res instanceof RDFTerm)
				explicitValues.add((Literal) res);
			else
				exclusions.add((ValueConstraint) res);
		}
		return new LanguageStemRangeConstraint(stem, explicitValues, exclusions); 
	}

	@Override
	public Object visitLanguageExclusion(LanguageExclusionContext ctx) {
		String langtag = ctx.LANGTAG().getText().substring(1);
		if (ctx.STEM_MARK()==null)
			return new LanguageConstraint(langtag);
		return  new LanguageStemConstraint(langtag);
	}

	@Override
	public Literal visitLiteral(LiteralContext ctx) {
		if (ctx.rdfLiteral()!=null) 
			return visitRdfLiteral(ctx.rdfLiteral());
		if (ctx.numericLiteral()!=null)
			return visitNumericLiteral(ctx.numericLiteral());
		if (ctx.booleanLiteral()!=null)
			return visitBooleanLiteral(ctx.booleanLiteral());
		return null;
	}

	@Override 
	public ShapeExpr visitShapeOrRef(ShExDocParser.ShapeOrRefContext ctx) {
		if (ctx.shapeDefinition()!=null)
			return visitShapeDefinition(ctx.shapeDefinition());
		return visitShapeRef(ctx.shapeRef());
	}

	@Override
	public ShapeExpr visitInlineShapeOrRef(InlineShapeOrRefContext ctx) {
		if (ctx.inlineShapeDefinition()!=null)
			return visitInlineShapeDefinition(ctx.inlineShapeDefinition());
		return visitShapeRef(ctx.shapeRef());
	}

	@Override 
	public ShapeExprRef visitShapeRef(ShExDocParser.ShapeRefContext ctx) { 
		if (ctx.ATPNAME_LN()!=null) {
			String ref = ctx.getText().substring(1);
			String prefix = ref.split(":")[0]+":";
			String name = ref.split(":")[1];
			return new ShapeExprRef(new Label(rdfFactory.createIRI(prefixes.get(prefix)+name))); 
		}
		if (ctx.ATPNAME_NS()!=null) {
			String prefix = ctx.getText().substring(1);
			return new ShapeExprRef(new Label(rdfFactory.createIRI(prefixes.get(prefix)))); 
		}
		return new ShapeExprRef((Label) ctx.shapeExprLabel().accept(this)); 
	}

	@Override 
	public TripleExprRef visitInclude(ShExDocParser.IncludeContext ctx) { 
		return new TripleExprRef((Label) ctx.tripleExprLabel().accept(this)); 
	}

	@Override
	public Object visitSemanticActions(SemanticActionsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Annotation visitAnnotation(ShExDocParser.AnnotationContext ctx) {
		IRI predicate = (IRI) ctx.predicate().accept(this);
		RDFTerm obj;
		if (ctx.iri() != null)
			obj = (RDFTerm) ctx.iri().accept(this);
		else
			obj = (RDFTerm) ctx.literal().accept(this);
		return new Annotation(predicate,obj); 
	}

	public IRI visitPredicate(ShExDocParser.PredicateContext ctx) { 
		if (ctx.iri() != null)
			return visitIri(ctx.iri());
		return visitRdfType(ctx.rdfType());
	}

	@Override
	public IRI visitRdfType(RdfTypeContext ctx) {
		return rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"); 
	}

	@Override
	public IRI visitDatatype(DatatypeContext ctx) {
		return visitIri(ctx.iri());
	}

	@Override 
	public Interval visitStarCardinality(ShExDocParser.StarCardinalityContext ctx) { 
		return Interval.STAR; 
	}

	@Override 
	public Interval visitPlusCardinality(ShExDocParser.PlusCardinalityContext ctx) { 
		return Interval.PLUS; 
	}
	
	@Override 
	public Interval visitOptionalCardinality(ShExDocParser.OptionalCardinalityContext ctx) { 
		return Interval.OPT; 
	}

	@Override
	public Interval visitRepeatCardinality(RepeatCardinalityContext ctx) {
		return (Interval) ctx.repeatRange().accept(this);
	}

	@Override 
	public Interval visitExactRange(ShExDocParser.ExactRangeContext ctx) { 
		int min = Integer.parseInt(ctx.INTEGER().getText());
		return new Interval(min,min);
	}

	@Override
	public Interval visitMinMaxRange(ShExDocParser.MinMaxRangeContext ctx) { 
		int min = Integer.parseInt(ctx.INTEGER(0).getText());
		if (ctx.INTEGER().size()>1)
			return  new Interval(min,Integer.parseInt(ctx.INTEGER(1).getText()));
		return new Interval(min,Interval.UNBOUND);
	}

	@Override
	public Label visitShapeExprLabel(ShExDocParser.ShapeExprLabelContext ctx) {
		Object result = visitChildren(ctx);
		if (result instanceof IRI) 
			return new Label((IRI) result);
		else 
			return new Label((BlankNode) result);
	}
	
	@Override
	public Label visitTripleExprLabel(ShExDocParser.TripleExprLabelContext ctx) {
		Object result = visitChildren(ctx);
		if (result instanceof IRI) 
			return new Label((IRI) result);
		else 
			return new Label((BlankNode) result);
	}

	@Override
	public Literal visitNumericLiteral(NumericLiteralContext ctx) {
		if (ctx.INTEGER()!=null)
			return  rdfFactory.createLiteral(ctx.INTEGER().getText(),Types.XSD_INTEGER);
		if (ctx.DOUBLE()!=null)
			return  rdfFactory.createLiteral(ctx.DOUBLE().getText(),Types.XSD_DOUBLE);
		return rdfFactory.createLiteral(ctx.DECIMAL().getText(),Types.XSD_DECIMAL);
	}

	@Override
	public Literal visitRdfLiteral(RdfLiteralContext ctx) {
		String value = (String) ctx.string().accept(this);
		if (ctx.datatype() != null)
			return rdfFactory.createLiteral(value, (IRI) ctx.datatype().accept(this));
		if (ctx.LANGTAG()!=null)
			return rdfFactory.createLiteral(value, ctx.LANGTAG().getText().substring(1));
		return rdfFactory.createLiteral(value);
	}

	@Override
	public Literal visitBooleanLiteral(BooleanLiteralContext ctx) {
		if (ctx.KW_FALSE()!=null)
			return rdfFactory.createLiteral("false",Types.XSD_BOOLEAN);
		return rdfFactory.createLiteral("true",Types.XSD_BOOLEAN);
	}

	@Override
	public String visitString(ShExDocParser.StringContext ctx) { 
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

	@Override
	public IRI visitIri(ShExDocParser.IriContext ctx) {
		TerminalNode iri = ctx.IRIREF();
		if (iri != null) {
			String iris = iri.getText();
			iris = iris.substring(1,iris.length()-1);
			if (base!=null)
				return rdfFactory.createIRI(base+iris);
			return rdfFactory.createIRI(StringEscapeUtils.unescapeJava(iris));
		}
		return visitPrefixedName(ctx.prefixedName()); 
	}

	@Override
	public IRI visitPrefixedName(ShExDocParser.PrefixedNameContext ctx) {
		if (ctx.PNAME_NS()!=null)
			return rdfFactory.createIRI(prefixes.get(ctx.PNAME_NS().getText()));

		String prefix = ctx.PNAME_LN().getText().split(":")[0]+":";
		if (!prefixes.containsKey(prefix))
			throw new ParseCancellationException("Unknown prefix: "+prefix);
		String value = (ctx.PNAME_LN().getText().replaceAll(prefix, prefixes.get(prefix)));
		return rdfFactory.createIRI(value); 
	}

	@Override
	public BlankNode visitBlankNode(ShExDocParser.BlankNodeContext ctx) {
		return rdfFactory.createBlankNode(ctx.BLANK_NODE_LABEL().getText().substring(2));
	}


	@Override
	public Object visitCodeDecl(CodeDeclContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIncludeSet(IncludeSetContext ctx) {
		// TODO Auto-generated method stub
		return null;
	} 

	
	//--------------------------------------------
	// Utils
	//--------------------------------------------

	public List<Constraint> cleanConstraint(List<Constraint> constraints) {
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
					FacetNumericConstraint tmp = ((FacetNumericConstraint) ct);
					if (tmp.getFractionDigits()!=null) fn.setFractionDigits(tmp.getFractionDigits());
					if (tmp.getTotalDigits()!=null) fn.setTotalDigits(tmp.getTotalDigits());
					if (tmp.getMaxexcl()!=null) fn.setMaxexcl(tmp.getMaxexcl());
					if (tmp.getMaxincl()!=null) fn.setMaxincl(tmp.getMaxincl());
					if (tmp.getMinexcl()!=null) fn.setMinexcl(tmp.getMinexcl());
					if (tmp.getMinincl()!=null) fn.setMinincl(tmp.getMinincl());
				}
			}
			if (ct instanceof FacetStringConstraint) {
				dealt=true;
				if (sn==null) {
					sn = (FacetStringConstraint) ct;
				}else{
					FacetStringConstraint tmp = ((FacetStringConstraint) ct);
					if (tmp.getFlags()!=null) sn.setFlags(tmp.getFlags());
					if (tmp.getPatternString()!=null) sn.setPattern(tmp.getPatternString());
					if (tmp.getLength()!=null) sn.setLength(tmp.getLength());
					if (tmp.getMaxlength()!=null) sn.setMaxlength(tmp.getMaxlength());
					if (tmp.getMinlength()!=null) sn.setMinLength(tmp.getMinlength());
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
}
