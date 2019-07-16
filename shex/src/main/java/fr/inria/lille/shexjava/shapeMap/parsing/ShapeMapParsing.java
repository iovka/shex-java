package fr.inria.lille.shexjava.shapeMap.parsing;

import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.simple.Types;
import org.apache.commons.text.StringEscapeUtils;

import fr.inria.lille.shexjava.schema.Label;
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
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.RdfTypeContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeAssociationContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeMapContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.ShapeSpecContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.StringContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.SubjectTermContext;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParser.TriplePatternContext;
import fr.inria.lille.shexjava.util.XPath;

public class ShapeMapParsing extends ShapeMapBaseVisitor<Object> {
	private RDF rdfFactory;
	private Map<String,String> prefixes;
	

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
			return new ShapeSelectorLabel(new Label(this.visitIri(ctx.iri())));
		return new ShapeSelectorStart();
	}

	@Override
	public NodeSelector visitNodeSpec(NodeSpecContext ctx) {
		if (ctx.triplePattern()!=null)
			return this.visitTriplePattern(ctx.triplePattern());
		if (ctx.objectTerm()!=null)
			return new NodeSeletorRDFTerm(this.visitObjectTerm(ctx.objectTerm()));
		return null;
	}
	
	@Override
	public NodeSelector visitTriplePattern(TriplePatternContext ctx) {
		if (ctx.objectTerm()!=null)
			return new NodeSelectorFilterSubject(this.visitPredicate(ctx.predicate()), this.visitObjectTerm(ctx.objectTerm()));
		if (ctx.subjectTerm()!=null)
			return new NodeSelectorFilterObject(this.visitSubjectTerm(ctx.subjectTerm()), this.visitPredicate(ctx.predicate()));
		return null;
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
		if (ctx.iri() != null)
			return visitIri(ctx.iri());
		return visitRdfType(ctx.rdfType());
	}

	@Override
	public IRI visitRdfType(RdfTypeContext ctx) {
		return rdfFactory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"); 
	}

	@Override
	public IRI visitIri(IriContext ctx) {
		TerminalNode iri = ctx.IRIREF();
		if (iri != null) {
			String iris = iri.getText();
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
