package fr.inria.lille.shexjava.shapeMap.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.schema.LabelUserDefined;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.simple.Types;
import org.apache.commons.text.StringEscapeUtils;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSelector;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSelectorFilterObject;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSelectorFilterSubject;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSeletorRDFTerm;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelector;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelectorLabel;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelectorStart;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.BlankNodeContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.BooleanLiteralContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.IriContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.LangStringContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.LiteralContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.NodeSpecContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.NumericLiteralContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ObjectTermContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.PredicateContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.PrefixedNameContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.RdfLiteralContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeAssociationContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeMapContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeSpecContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.StringContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.SubjectTermContext;
import fr.inria.lille.shexjava.util.XPath;

public class ShapeMapParsing extends ShapeMapBaseVisitor<Object> {
	private RDF rdfFactory;
	private Map<String,String> prefixes;
	

	public BaseShapeMap parse( InputStream is) throws IOException {
		rdfFactory = GlobalFactory.RDFFactory;
		Reader isr = new InputStreamReader(is,Charset.defaultCharset().name());
		CharStream inputStream = CharStreams.fromReader(isr);
		ShapeMapLexer lexer = new ShapeMapLexer(inputStream);
		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
		ShapeMapParser parser = new ShapeMapParser(commonTokenStream);   

		return this.visitShapeMap(parser.shapeMap());

	}
	
	@Override
	public BaseShapeMap visitShapeMap(ShapeMapContext ctx) {
		return new BaseShapeMap(ctx.shapeAssociation().stream().map(c -> this.visitShapeAssociation(c)).collect(Collectors.toList()));
	}

	@Override
	public ShapeAssociation visitShapeAssociation(ShapeAssociationContext ctx) {
		return new ShapeAssociation(this.visitNodeSpec(ctx.nodeSpec()), this.visitShapeSpec(ctx.shapeSpec()));
	}

	@Override
	public ShapeSelector visitShapeSpec(ShapeSpecContext ctx) {
		if (ctx.iri()!=null)
			return new ShapeSelectorLabel(new LabelUserDefined(this.visitIri(ctx.iri())));
		return new ShapeSelectorStart();
	}

	@Override
	public NodeSelector visitNodeSpec(NodeSpecContext ctx) {
		if (ctx.triplePattern()!=null)
			return (NodeSelector) ctx.triplePattern().accept(this);
		if (ctx.objectTerm()!=null)
			return new NodeSeletorRDFTerm(this.visitObjectTerm(ctx.objectTerm()));
		return null;
	}
	
	
	@Override
	public NodeSelectorFilterSubject visitTriplePatternObject(ShapeMapParser.TriplePatternObjectContext ctx) {
		if (ctx.objectTerm()!=null)
			return new NodeSelectorFilterSubject(this.visitPredicate(ctx.predicate()), this.visitObjectTerm(ctx.objectTerm()));
		return new NodeSelectorFilterSubject(this.visitPredicate(ctx.predicate()));

	}
	
	@Override
	public NodeSelectorFilterObject visitTriplePatternSubject(ShapeMapParser.TriplePatternSubjectContext ctx) {
		if (ctx.subjectTerm()!=null)
			return new NodeSelectorFilterObject(this.visitSubjectTerm(ctx.subjectTerm()), this.visitPredicate(ctx.predicate()));
		return new NodeSelectorFilterObject(this.visitPredicate(ctx.predicate()));
	}
	

	@Override
	public RDFTerm visitObjectTerm(ObjectTermContext ctx) {
		if (ctx.literal()!=null)
			return visitLiteral(ctx.literal());
		if (ctx.subjectTerm()!=null)
			return visitSubjectTerm(ctx.subjectTerm());
		return null;
	}
	
	@Override
	public BlankNodeOrIRI visitSubjectTerm(SubjectTermContext ctx) {
		if (ctx.iri()!=null)
			return visitIri(ctx.iri());
		if (ctx.blankNode()!=null)
			return visitBlankNode(ctx.blankNode());
		return null;
	}


	@Override
	public Literal visitLiteral(LiteralContext ctx) {
		if (ctx.booleanLiteral()!=null)
			return visitBooleanLiteral(ctx.booleanLiteral());
		if (ctx.numericLiteral()!=null)
			return visitNumericLiteral(ctx.numericLiteral());
		if (ctx.rdfLiteral()!=null)
			return visitRdfLiteral(ctx.rdfLiteral());
		return null;
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
		if (ctx.iri() != null)
			return rdfFactory.createLiteral(value, (IRI) ctx.iri().accept(this));
		return this.visitLangString(ctx.langString());
	}

	@Override
	public Literal visitBooleanLiteral(BooleanLiteralContext ctx) {
		if (ctx.KW_FALSE()!=null)
			return rdfFactory.createLiteral("false",Types.XSD_BOOLEAN);
		return rdfFactory.createLiteral("true",Types.XSD_BOOLEAN);
	}

	@Override
	public String visitString(StringContext ctx) {
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
	public Literal visitLangString(LangStringContext ctx) {
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
		result = XPath.unescapeJavaString(result);
		return rdfFactory.createLiteral(result,ctx.LANGTAG().getText());
	}

	@Override
	public IRI visitPredicate(PredicateContext ctx) {
		return visitIri(ctx.iri());
	}


	@Override
	public IRI visitIri(IriContext ctx) {
		if (ctx.RDF_TYPE()!=null)
			return rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"); 
		if (ctx.IRIREF() != null) {
			String iris = ctx.IRIREF().getText();
			iris = iris.substring(1,iris.length()-1);
			return rdfFactory.createIRI(StringEscapeUtils.unescapeJava(iris));
		}
		return visitPrefixedName(ctx.prefixedName()); 
	}

	@Override
	public IRI visitPrefixedName(PrefixedNameContext ctx) {
		if (ctx.PNAME_NS()!=null)
			return rdfFactory.createIRI(prefixes.get(ctx.PNAME_NS().getText()));

		String prefix = ctx.PNAME_LN().getText().split(":")[0]+":";
		if (!prefixes.containsKey(prefix))
			throw new ParseCancellationException("Unknown prefix: "+prefix);
		String value = (ctx.PNAME_LN().getText().replaceAll(prefix, prefixes.get(prefix)));
		return rdfFactory.createIRI(value); 
	}

	@Override
	public BlankNode visitBlankNode(BlankNodeContext ctx) {
		return rdfFactory.createBlankNode(ctx.BLANK_NODE_LABEL().getText().substring(2));
	}

}
