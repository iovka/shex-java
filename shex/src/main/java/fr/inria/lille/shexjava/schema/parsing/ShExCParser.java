package fr.inria.lille.shexjava.schema.parsing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;

import fr.inria.lille.shexjava.schema.BNodeLabel;
import fr.inria.lille.shexjava.schema.IRILabel;
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
import fr.inria.lille.shexjava.util.Interval;
import fr.inria.lille.shexjava.util.XPath;

public class ShExCParser  extends ShExDocBaseVisitor<Object> implements Parser{


    // -------------------------------------------------------------------------
    // FROM PARSER
    // -------------------------------------------------------------------------

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
        base = "http://shexjava.fr/DefaultBase#";

        Reader isr = new InputStreamReader(is, Charset.defaultCharset().name());
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

    // -------------------------------------------------------------------------
    // FROM ShexDocBaseVisitor
    // -------------------------------------------------------------------------

    @Override
    public Object visitShExDoc(ShExDocParser.ShExDocContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Object visitDirective(ShExDocParser.DirectiveContext ctx) {
        // Nothing else to do
        return super.visitDirective(ctx);
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
        // TODO does it work if there are several start actions ?
    	return visitChildren(ctx);
    }


    @Override
    public Object visitStatement(ShExDocParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ShapeExpr visitShapeExprDecl(ShExDocParser.ShapeExprDeclContext ctx) {
        Label label = (Label) visitShapeExprLabel(ctx.shapeExprLabel());
        boolean isAbstract = false;
        ShapeExpr expr ;
        if (ctx.KW_EXTERNAL()!=null) {
            expr = new ShapeExternal();
        }else {
            expr = (ShapeExpr) visitShapeExpression(ctx.shapeExpression());
        }
        if (ctx.KW_ABSTRACT()!=null)
            isAbstract = true;
        if (rules.containsKey(label))
            throw new IllegalArgumentException("Label "+label+" already used.");

        expr.setId(label);
        expr.setAbstract(isAbstract);
        rules.put(label,expr);
        return expr;
    }

    @Override
    public ShapeExpr visitShapeExpression(ShExDocParser.ShapeExpressionContext ctx) {
        return visitShapeOr(ctx.shapeOr());
    }

    @Override
    public ShapeExpr visitInlineShapeExpression(ShExDocParser.InlineShapeExpressionContext ctx) {
        return visitInlineShapeOr(ctx.inlineShapeOr());
    }

    @Override
    public ShapeExpr visitShapeOr(ShExDocParser.ShapeOrContext ctx) {
        List<ShapeExpr> children = new ArrayList<ShapeExpr>();
        for (ShExDocParser.ShapeAndContext child:ctx.shapeAnd())
            children.add(visitShapeAnd(child));
        if (children.size()==1)
            return children.get(0);
        return new ShapeOr(children);
    }

    @Override
    public ShapeExpr visitInlineShapeOr(ShExDocParser.InlineShapeOrContext ctx) {
        List<ShapeExpr> children = new ArrayList<ShapeExpr>();
        for (ShExDocParser.InlineShapeAndContext child:ctx.inlineShapeAnd())
            children.add(visitInlineShapeAnd(child));
        if (children.size()==1)
            return children.get(0);
        return new ShapeOr(children);
    }

    @Override
    public ShapeExpr visitShapeAnd(ShExDocParser.ShapeAndContext ctx) {
        List<ShapeExpr> children = new ArrayList<ShapeExpr>();
        for (ShExDocParser.ShapeNotContext child:ctx.shapeNot())
            children.add(visitShapeNot(child));
        if (children.size()==1)
            return children.get(0);
        return new ShapeAnd(children);
    }

    @Override
    public ShapeExpr visitInlineShapeAnd(ShExDocParser.InlineShapeAndContext ctx) {
        List<ShapeExpr> children = new ArrayList<ShapeExpr>();
        for (ShExDocParser.InlineShapeNotContext child:ctx.inlineShapeNot())
            children.add(visitInlineShapeNot(child));
        if (children.size()==1)
            return children.get(0);
        return new ShapeAnd(children);
    }

    @Override
    public ShapeExpr visitShapeNot(ShExDocParser.ShapeNotContext ctx) {
        if (ctx.KW_NOT()==null)
            return (ShapeExpr) ctx.shapeAtom().accept(this);
        ShapeExpr result = new ShapeNot((ShapeExpr) ctx.shapeAtom().accept(this));
        return result;
    }

    @Override
    public ShapeExpr visitInlineShapeNot(ShExDocParser.InlineShapeNotContext ctx) {
        if (ctx.KW_NOT()==null) {
            return (ShapeExpr) ctx.inlineShapeAtom().accept(this);
        }
        return new ShapeNot((ShapeExpr) ctx.inlineShapeAtom().accept(this));
    }

    @Override
    public ShapeExpr visitShapeAtomNonLitNodeConstraint(ShExDocParser.ShapeAtomNonLitNodeConstraintContext ctx) {
        ShapeExpr result = (ShapeExpr) ctx.nonLitNodeConstraint().accept(this);
        if (ctx.shapeOrRef()!=null) {
            List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
            expressions.add(result);
            expressions.add(visitShapeOrRef(ctx.shapeOrRef()));
            result = new ShapeAnd(expressions);
        }
        return result;

    }
	@Override
	public ShapeExpr visitShapeAtomLitNodeConstraint(ShExDocParser.ShapeAtomLitNodeConstraintContext ctx) {
		return visitLitNodeConstraint(ctx.litNodeConstraint());
	}

    @Override
    public ShapeExpr visitShapeAtomShapeOrRef(ShExDocParser.ShapeAtomShapeOrRefContext ctx) {
        ShapeExpr result = visitShapeOrRef(ctx.shapeOrRef());
        if (ctx.nonLitNodeConstraint()!=null) {
            List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
            expressions.add(result);
            expressions.add((ShapeExpr) ctx.nonLitNodeConstraint().accept(this));
            result = new ShapeAnd(expressions);
        }
        return result;
    }

    @Override
    public Object visitShapeAtomShapeExpression(ShExDocParser.ShapeAtomShapeExpressionContext ctx) {
        return visitShapeExpression(ctx.shapeExpression());
    }

    @Override
    public Object visitShapeAtomAny(ShExDocParser.ShapeAtomAnyContext ctx) {
        return  new EmptyShape();
    }
    @Override
    public Object visitInlineShapeAtomNonLitNodeConstraint(ShExDocParser.InlineShapeAtomNonLitNodeConstraintContext ctx) {
        ShapeExpr result = (ShapeExpr) ctx.inlineNonLitNodeConstraint().accept(this);
        if (ctx.inlineShapeOrRef()!=null) {
            List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
            expressions.add(result);
            expressions.add(visitInlineShapeOrRef(ctx.inlineShapeOrRef()));
            result = new ShapeAnd(expressions);
        }
        return result;
    }

    @Override
    public Object visitInlineShapeAtomLitNodeConstraint(ShExDocParser.InlineShapeAtomLitNodeConstraintContext ctx) {
        return (ShapeExpr) ctx.inlineLitNodeConstraint().accept(this);
    }

    @Override
    public Object visitInlineShapeAtomShapeOrRef(ShExDocParser.InlineShapeAtomShapeOrRefContext ctx) {
        ShapeExpr result = visitInlineShapeOrRef(ctx.inlineShapeOrRef());
        if (ctx.inlineNonLitNodeConstraint()!=null) {
            List<ShapeExpr> expressions = new ArrayList<ShapeExpr>();
            expressions.add(result);
            expressions.add((ShapeExpr) ctx.inlineNonLitNodeConstraint().accept(this));
            result = new ShapeAnd(expressions);
        }
        return result;
    }


    @Override
    public Object visitInlineShapeAtomShapeExpression(ShExDocParser.InlineShapeAtomShapeExpressionContext ctx) {
        return visitShapeExpression(ctx.shapeExpression());
    }

    @Override
    public Object visitInlineShapeAtomAny(ShExDocParser.InlineShapeAtomAnyContext ctx) {
        return  new EmptyShape();
    }

    @Override
    public ShapeExpr visitShapeOrRef(ShExDocParser.ShapeOrRefContext ctx) {
        if (ctx.shapeDefinition()!=null)
            return visitShapeDefinition(ctx.shapeDefinition());
        return visitShapeRef(ctx.shapeRef());
    }

    @Override
    public ShapeExpr visitInlineShapeOrRef(ShExDocParser.InlineShapeOrRefContext ctx) {
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
            return new ShapeExprRef(new IRILabel(rdfFactory.createIRI(prefixes.get(prefix)+name)));
        }
        if (ctx.ATPNAME_NS()!=null) {
            String prefix = ctx.getText().substring(1);
            return new ShapeExprRef(new IRILabel(rdfFactory.createIRI(prefixes.get(prefix))));
        }
        return new ShapeExprRef((Label) ctx.shapeExprLabel().accept(this));
    }

    @Override
    public Object visitNodeConstraintLiteral(ShExDocParser.NodeConstraintLiteralContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        constraints.add(NodeKindConstraint.LiteralKind);
        for (ShExDocParser.XsFacetContext facet : ctx.xsFacet()) {
            constraints.add(visitXsFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitNodeConstraintNonLiteral(ShExDocParser.NodeConstraintNonLiteralContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        constraints.add((Constraint) ctx.nonLiteralKind().accept(this));
        for (ShExDocParser.StringFacetContext facet : ctx.stringFacet()) {
            constraints.add(visitStringFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitNodeConstraintDatatype(ShExDocParser.NodeConstraintDatatypeContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        constraints.add(new DatatypeConstraint((IRI) ctx.datatype().accept(this)));
        for (ShExDocParser.XsFacetContext facet : ctx.xsFacet()) {
            constraints.add(visitXsFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitNodeConstraintValueSet(ShExDocParser.NodeConstraintValueSetContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        constraints.add((Constraint) ctx.valueSet().accept(this));
        for (ShExDocParser.XsFacetContext facet : ctx.xsFacet()) {
            constraints.add(visitXsFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitNodeConstraintNumericFacet(ShExDocParser.NodeConstraintNumericFacetContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        for (ShExDocParser.NumericFacetContext facet : ctx.numericFacet()) {
            constraints.add(visitNumericFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public ShapeExpr visitLitNodeConstraint(ShExDocParser.LitNodeConstraintContext ctx) {
        NodeConstraint result = (NodeConstraint) ctx.inlineLitNodeConstraint().accept(this);
        if (ctx.annotation()!=null) {
            List<Annotation> annotations = new ArrayList<Annotation>();
            for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
                annotations.add((Annotation) annotContext.accept(this));
            result.setAnnotations(annotations);
        }

        // SemAct not used
        return result;
    }

    @Override
    public Object visitLitNodeConstraintLiteral(ShExDocParser.LitNodeConstraintLiteralContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
        constraints.add((Constraint) ctx.nonLiteralKind().accept(this));
        for (ShExDocParser.StringFacetContext facet : ctx.stringFacet()) {
            constraints.add(visitStringFacet(facet));
        }
        return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitLitNodeConstraintStringFacet(ShExDocParser.LitNodeConstraintStringFacetContext ctx) {
        List<Constraint> constraints = new ArrayList<Constraint>() ;
		for (ShExDocParser.StringFacetContext facet : ctx.stringFacet()) {
			constraints.add(visitStringFacet(facet));
		}
		return new NodeConstraint(cleanConstraint(constraints));
    }

    @Override
    public Object visitNonLitNodeConstraint(ShExDocParser.NonLitNodeConstraintContext ctx) {
        NodeConstraint result = (NodeConstraint) ctx.inlineNonLitNodeConstraint().accept(this);
        if (ctx.annotation()!=null) {
            List<Annotation> annotations = new ArrayList<Annotation>();
            for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
                annotations.add((Annotation) annotContext.accept(this));
            result.setAnnotations(annotations);
        }
        // SemAct not used
        return result;
    }

    @Override
    public Object visitNonLiteralKind(ShExDocParser.NonLiteralKindContext ctx) {
        if (ctx.KW_NONLITERAL()!=null)
            return NodeKindConstraint.NonLiteralKind;
        else if (ctx.KW_BNODE()!=null)
            return NodeKindConstraint.BNodeKind;
        else
            return NodeKindConstraint.IRIKind;
    }

    @Override
    public Constraint visitXsFacet(ShExDocParser.XsFacetContext ctx) {
        if (ctx.stringFacet()!=null)
            return visitStringFacet(ctx.stringFacet());
        return visitNumericFacet(ctx.numericFacet());
    }

    @Override
    public Constraint visitStringFacet(ShExDocParser.StringFacetContext ctx) {
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
    public Object visitStringLength(ShExDocParser.StringLengthContext ctx) {
        // TODO this is supposedely not used. Why ?
        throw new UnsupportedOperationException("Maybe a bug. Contact the developers of shex-java.");
    }

    @Override
    public Constraint visitNumericFacet(ShExDocParser.NumericFacetContext ctx) {
        FacetNumericConstraint result = new FacetNumericConstraint();
        if (ctx.numericLength()!=null) {
            int val = Integer.parseInt(ctx.INTEGER().getText());
            if (ctx.numericLength().KW_FRACTIONDIGITS()!=null)
                result.setFractionDigits(val);
            if (ctx.numericLength().KW_TOTALDIGITS()!=null)
                result.setTotalDigits(val);
        }else {
            BigDecimal value = visitRawNumeric(ctx.rawNumeric());
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

    @Override
    public Object visitNumericRange(ShExDocParser.NumericRangeContext ctx) {
        // TODO this is supposedely not used. Why ?
        throw new UnsupportedOperationException("Maybe a bug. Contact the developers of shex-java.");
    }

    @Override
    public Object visitNumericLength(ShExDocParser.NumericLengthContext ctx) {
        // TODO this is supposedely not used. Why ?
        throw new UnsupportedOperationException("Maybe a bug. Contact the developers of shex-java.");
    }

    @Override
    public BigDecimal visitRawNumeric(ShExDocParser.RawNumericContext ctx) {
        if (ctx.INTEGER()!=null)
            return new BigDecimal(ctx.INTEGER().getText());
        if (ctx.DOUBLE()!=null)
            return new BigDecimal(ctx.DOUBLE().getText());
        return new BigDecimal(ctx.DECIMAL().getText());
    }

    @Override
    public ShapeExpr visitShapeDefinition(ShExDocParser.ShapeDefinitionContext ctx) {

        TripleExpr triple;
        List<ShapeExprRef> extended = new ArrayList<>();
        Set<TCProperty> extra = new HashSet<TCProperty>();
        boolean closed = false;
        List<Annotation> annotations = null;

        for (ShExDocParser.QualifierContext qua:ctx.qualifier()) {
            if (qua.KW_CLOSED()!=null){
                closed= true;
            } else if (qua.extension()!=null){
                extended.add(new ShapeExprRef((Label) qua.extension().accept(this)));
            } else if (qua.extraPropertySet()!=null){
                extra.addAll((Set<TCProperty>) qua.extraPropertySet().accept(this));
            } else {
                // TODO ParseException
                System.err.println("Qualifier: "+qua.getText());
                System.err.println(this.filename);
            }
        }

        if (ctx.tripleExpression()!=null) {
            triple = visitTripleExpression(ctx.tripleExpression());
        } else {
            triple = new EmptyTripleExpression();
        }
        if (ctx.annotation()!=null) {
            annotations = new ArrayList<>();
            for (ShExDocParser.AnnotationContext annotContext:ctx.annotation())
                annotations.add((Annotation) annotContext.accept(this));
        }

        return new Shape(triple, extended, extra, closed, annotations);
        //SemActs not used
    }

    @Override
    public ShapeExpr visitInlineShapeDefinition(ShExDocParser.InlineShapeDefinitionContext ctx) {
        TripleExpr triple;
        List<ShapeExprRef> extended = new ArrayList<>();
        Set<TCProperty> extra = new HashSet<TCProperty>();
        boolean closed = false;
        List<Annotation> annotations = null;

        for (ShExDocParser.QualifierContext qua:ctx.qualifier()) {
            if (qua.KW_CLOSED()!=null){
                closed= true;
            } else if (qua.extension()!=null){
                extended.add(new ShapeExprRef((Label) qua.extension().accept(this)));
            } else if (qua.extraPropertySet()!=null){
                extra.addAll((Set<TCProperty>) qua.extraPropertySet().accept(this));
            } else {
                // TODO ParseException
                System.err.println("Qualifier: "+qua.getText());
                System.err.println(this.filename);
            }
        }

        if (ctx.tripleExpression()!=null) {
            triple = visitTripleExpression(ctx.tripleExpression());
        } else {
            triple = new EmptyTripleExpression();
        }
        return new Shape(triple, extended, extra, closed, annotations);
    }

    @Override
    public Object visitQualifier(ShExDocParser.QualifierContext ctx) {
        // TODO this is supposedely not used. Why ?
        throw new UnsupportedOperationException("Maybe a bug. Contact the developers of shex-java.");
    }

    @Override
    public Object visitExtraPropertySet(ShExDocParser.ExtraPropertySetContext ctx) {
        Set<TCProperty> extra = new HashSet<TCProperty>();
        for (ShExDocParser.PredicateContext child : ctx.predicate())
            extra.add(TCProperty.createFwProperty((IRI) child.accept(this)));
        return extra;
    }

    @Override
    public TripleExpr visitTripleExpression(ShExDocParser.TripleExpressionContext ctx) {
        return visitOneOfTripleExpr(ctx.oneOfTripleExpr());
    }

    @Override
    public TripleExpr visitOneOfTripleExpr(ShExDocParser.OneOfTripleExprContext ctx) {
        if (ctx.groupTripleExpr()!=null)
            return visitGroupTripleExpr(ctx.groupTripleExpr());
        return visitMultiElementOneOf(ctx.multiElementOneOf());
    }

    @Override
    public TripleExpr visitMultiElementOneOf(ShExDocParser.MultiElementOneOfContext ctx) {
        ArrayList<TripleExpr> children = new ArrayList<>();
        for (ShExDocParser.GroupTripleExprContext child:ctx.groupTripleExpr())
            children.add(visitGroupTripleExpr(child));
        return new OneOf(children);
    }

    @Override
    public TripleExpr visitGroupTripleExpr(ShExDocParser.GroupTripleExprContext ctx) {
        if (ctx.multiElementGroup()!=null)
            return visitMultiElementGroup(ctx.multiElementGroup());
        return visitSingleElementGroup(ctx.singleElementGroup());
    }

    @Override
    public TripleExpr visitSingleElementGroup(ShExDocParser.SingleElementGroupContext ctx) {
        return visitUnaryTripleExpr(ctx.unaryTripleExpr());
    }

    @Override
    public TripleExpr visitMultiElementGroup(ShExDocParser.MultiElementGroupContext ctx) {
		ArrayList<TripleExpr> children = new ArrayList<>();
		for (ShExDocParser.UnaryTripleExprContext child:ctx.unaryTripleExpr())
			children.add(visitUnaryTripleExpr(child));
		return new EachOf(children);
    }

    @Override
    public TripleExpr visitUnaryTripleExpr(ShExDocParser.UnaryTripleExprContext ctx) {
        if (ctx.include()!=null)
            return visitInclude(ctx.include());
        TripleExpr res;
        if (ctx.tripleConstraint()!=null)
            res = visitTripleConstraint(ctx.tripleConstraint());
        else
            res = visitBracketedTripleExpr(ctx.bracketedTripleExpr());
        if (ctx.tripleExprLabel()!=null)
            res.setId(visitTripleExprLabel(ctx.tripleExprLabel()));
        return res;
    }

    @Override
    public TripleExpr visitBracketedTripleExpr(ShExDocParser.BracketedTripleExprContext ctx) {
        TripleExpr result = visitTripleExpression(ctx.tripleExpression());//(ctx.innerShape());

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
        return (Interval) ctx.repeatRange().accept(this);
    }

    @Override
    public Object visitExactRange(ShExDocParser.ExactRangeContext ctx) {
        int min = Integer.parseInt(ctx.INTEGER().getText());
        return new Interval(min,min);
    }

    @Override
    public Object visitMinMaxRange(ShExDocParser.MinMaxRangeContext ctx) {
        int min = Integer.parseInt(ctx.INTEGER(0).getText());
        if (ctx.INTEGER().size()>1)
            return  new Interval(min,Integer.parseInt(ctx.INTEGER(1).getText()));
        return new Interval(min,Interval.UNBOUND);
    }

    @Override
    public Object visitSenseFlags(ShExDocParser.SenseFlagsContext ctx) {
        // TODO this is supposedely not used. Why ?
        throw new UnsupportedOperationException("Maybe a bug. Contact the developers of shex-java.");
    }

    @Override
    public Object visitValueSet(ShExDocParser.ValueSetContext ctx) {
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

    @Override
    public Object visitValueSetValue(ShExDocParser.ValueSetValueContext ctx) {
        if (ctx.iriRange()!=null)
            return visitIriRange(ctx.iriRange());
        if (ctx.literalRange()!=null)
            return visitLiteralRange(ctx.literalRange());
        if (ctx.languageRange()!=null)
            return ctx.languageRange().accept(this);
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
    public Object visitIriRange(ShExDocParser.IriRangeContext ctx) {
        if (ctx.STEM_MARK()==null)
            return visitIri(ctx.iri());

        IRIStemConstraint stem = new IRIStemConstraint(visitIri(ctx.iri()).getIRIString());
        if (ctx.iriExclusion()==null)
            return stem;

        Set<ValueConstraint> exclusions = new HashSet<ValueConstraint>();
        Set<RDFTerm> explicitValues = new HashSet<RDFTerm>();
        for(ShExDocParser.IriExclusionContext exclu:ctx.iriExclusion()) {
            Object res = exclu.accept(this);
            if (res instanceof IRI)
                explicitValues.add((IRI) res);
            else
                exclusions.add((ValueConstraint) res);
        }

        return new IRIStemRangeConstraint(stem, explicitValues, exclusions);
    }

    @Override
    public Object visitIriExclusion(ShExDocParser.IriExclusionContext ctx) {
        if (ctx.STEM_MARK()!=null)
            return new IRIStemConstraint(visitIri(ctx.iri()).getIRIString());
        return visitIri(ctx.iri());
    }

    @Override
    public Object visitLiteralRange(ShExDocParser.LiteralRangeContext ctx) {
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
    public Object visitLiteralExclusion(ShExDocParser.LiteralExclusionContext ctx) {
        Literal val = visitLiteral(ctx.literal());
        if (ctx.STEM_MARK()!=null)
            return new LiteralStemConstraint(val.getLexicalForm());
        return val;
    }

    @Override
    public Object visitLanguageRangeFull(ShExDocParser.LanguageRangeFullContext ctx) {
        String langtag="";
        langtag = ctx.LANGTAG().getText().substring(1);
        if (ctx.STEM_MARK()==null)
            return new LanguageConstraint(langtag);

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
    public Object visitLanguageRangeAt(ShExDocParser.LanguageRangeAtContext ctx) {
        String langtag="";

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
    public Object visitLanguageExclusion(ShExDocParser.LanguageExclusionContext ctx) {
        String langtag = ctx.LANGTAG().getText().substring(1);
        if (ctx.STEM_MARK()==null)
            return new LanguageConstraint(langtag);
        return  new LanguageStemConstraint(langtag);
    }

    // TODO why sometimes the accept is used, and other times directly the visit of this class is used ?
    @Override
    public TripleExprRef visitInclude(ShExDocParser.IncludeContext ctx) {
        return new TripleExprRef((Label) ctx.tripleExprLabel().accept(this));
    }

    @Override
    public Object visitAnnotation(ShExDocParser.AnnotationContext ctx) {
        IRI predicate = (IRI) ctx.predicate().accept(this);
        RDFTerm obj;
        if (ctx.iri() != null)
            obj = (RDFTerm) ctx.iri().accept(this);
        else
            obj = (RDFTerm) ctx.literal().accept(this);
        return new Annotation(predicate,obj);
    }

    @Override
    public Object visitSemanticAction(ShExDocParser.SemanticActionContext ctx) {
        // TODO change to some parse exception ?
        throw new UnsupportedOperationException("semantic actions not supported");
    }

    @Override
    public Literal visitLiteral(ShExDocParser.LiteralContext ctx) {
        if (ctx.rdfLiteral()!=null)
            return visitRdfLiteral(ctx.rdfLiteral());
        if (ctx.numericLiteral()!=null)
            return visitNumericLiteral(ctx.numericLiteral());
        if (ctx.booleanLiteral()!=null)
            return visitBooleanLiteral(ctx.booleanLiteral());
        return null;
    }

    @Override
    public IRI visitPredicate(ShExDocParser.PredicateContext ctx) {
        if (ctx.iri() != null)
            return visitIri(ctx.iri());
        return visitRdfType(ctx.rdfType());
    }

    @Override
    public IRI visitRdfType(ShExDocParser.RdfTypeContext ctx) {
        // TODO isn't this a predifined constant ?
        return rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    }

    @Override
    public Object visitDatatype(ShExDocParser.DatatypeContext ctx) {
        return visitIri(ctx.iri());
    }

    @Override
    public Label visitShapeExprLabel(ShExDocParser.ShapeExprLabelContext ctx) {
        Object result = visitChildren(ctx);
        if (result instanceof IRI)
            return new IRILabel((IRI) result);
        else
            return new BNodeLabel((BlankNode) result);
    }

    @Override
    public Label visitTripleExprLabel(ShExDocParser.TripleExprLabelContext ctx) {
        Object result = visitChildren(ctx);
        if (result instanceof IRI)
            return new IRILabel((IRI) result);
        else
            return new BNodeLabel((BlankNode) result);
    }

    @Override
    public Literal visitNumericLiteral(ShExDocParser.NumericLiteralContext ctx) {
        if (ctx.INTEGER()!=null)
            return  rdfFactory.createLiteral(ctx.INTEGER().getText(),Types.XSD_INTEGER);
        if (ctx.DOUBLE()!=null)
            return  rdfFactory.createLiteral(ctx.DOUBLE().getText(),Types.XSD_DOUBLE);
        return rdfFactory.createLiteral(ctx.DECIMAL().getText(),Types.XSD_DECIMAL);
    }

    @Override
    public Literal visitRdfLiteral(ShExDocParser.RdfLiteralContext ctx) {
        String value = (String) ctx.rdfString().accept(this);
        if (ctx.datatype() != null)
            return rdfFactory.createLiteral(value, (IRI) ctx.datatype().accept(this));
        if (ctx.LANGTAG()!=null)
            return rdfFactory.createLiteral(value, ctx.LANGTAG().getText().substring(1));
        return rdfFactory.createLiteral(value);
    }

    @Override
    public Literal visitBooleanLiteral(ShExDocParser.BooleanLiteralContext ctx) {
        if (ctx.KW_FALSE()!=null)
            return rdfFactory.createLiteral("false",Types.XSD_BOOLEAN);
        return rdfFactory.createLiteral("true",Types.XSD_BOOLEAN);
    }

    @Override
    public Object visitRdfString(ShExDocParser.RdfStringContext ctx) {
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
            if (base!=null && !ctx.getText().contains(":"))
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
    public Object visitBlankNode(ShExDocParser.BlankNodeContext ctx) {
        return rdfFactory.createBlankNode(ctx.BLANK_NODE_LABEL().getText().substring(2));
    }

    @Override
    public Label visitExtension(ShExDocParser.ExtensionContext ctx) {
        return visitShapeExprLabel(ctx.shapeExprLabel());
    }

    @Override
    public Object visitRestriction(ShExDocParser.RestrictionContext ctx) {
        return super.visitRestriction(ctx);
    }

    private RDF rdfFactory;
	private Map<String,String> prefixes;
	// TODO should be changed : base resolution
    private String base = "http://shexjava.fr/DefaultBase#";
	private List<String> imports;
    private ShapeExpr start;
    private Map<Label,ShapeExpr> rules;
    private Path filename;



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
					throw new ParseCancellationException("Multiple node-kind constraints found.");
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
