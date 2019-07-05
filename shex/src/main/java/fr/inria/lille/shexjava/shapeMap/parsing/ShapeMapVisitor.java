// Generated from fr/inria/lille/shexjava/shapeMap/parsing/ShapeMap.g4 by ANTLR 4.7.1
package fr.inria.lille.shexjava.shapeMap.parsing;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ShapeMapParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ShapeMapVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#shapeMap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeMap(ShapeMapParser.ShapeMapContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#shapeAssociation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAssociation(ShapeMapParser.ShapeAssociationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#nodeSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeSpec(ShapeMapParser.NodeSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#subjectTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubjectTerm(ShapeMapParser.SubjectTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#objectTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectTerm(ShapeMapParser.ObjectTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#triplePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplePattern(ShapeMapParser.TriplePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#shapeSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeSpec(ShapeMapParser.ShapeSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ShapeMapParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(ShapeMapParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#rdfLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfLiteral(ShapeMapParser.RdfLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(ShapeMapParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(ShapeMapParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#langString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLangString(ShapeMapParser.LangStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(ShapeMapParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#rdfType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfType(ShapeMapParser.RdfTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#iri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIri(ShapeMapParser.IriContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#prefixedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixedName(ShapeMapParser.PrefixedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShapeMapParser#blankNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNode(ShapeMapParser.BlankNodeContext ctx);
}