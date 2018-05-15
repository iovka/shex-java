// Generated from fr/univLille/cristal/shex/schema/FOL/parsing/FOL.g4 by ANTLR 4.5
package fr.univLille.cristal.shex.schema.FOL.parsing;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FOLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FOLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FOLParser#formulas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormulas(FOLParser.FormulasContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(FOLParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#quantifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifiers(FOLParser.QuantifiersContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forallQuantifier}
	 * labeled alternative in {@link FOLParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForallQuantifier(FOLParser.ForallQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code existsQuantifier}
	 * labeled alternative in {@link FOLParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistsQuantifier(FOLParser.ExistsQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceImplication}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceImplication(FOLParser.SentenceImplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceAnd}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceAnd(FOLParser.SentenceAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceOr}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceOr(FOLParser.SentenceOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceNot}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceNot(FOLParser.SentenceNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceParenthesis}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceParenthesis(FOLParser.SentenceParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceOperator}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceOperator(FOLParser.SentenceOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sentenceAtom}
	 * labeled alternative in {@link FOLParser#sentence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentenceAtom(FOLParser.SentenceAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorEqual}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorEqual(FOLParser.OperatorEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorEqualInf}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorEqualInf(FOLParser.OperatorEqualInfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorEqualSup}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorEqualSup(FOLParser.OperatorEqualSupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorInf}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorInf(FOLParser.OperatorInfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorSup}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorSup(FOLParser.OperatorSupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operatorDiff}
	 * labeled alternative in {@link FOLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorDiff(FOLParser.OperatorDiffContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapePredicate}
	 * labeled alternative in {@link FOLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapePredicate(FOLParser.ShapePredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code triplePredicate}
	 * labeled alternative in {@link FOLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriplePredicate(FOLParser.TriplePredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(FOLParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#iri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIri(FOLParser.IriContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#prefixedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixedName(FOLParser.PrefixedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOLParser#blankNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNode(FOLParser.BlankNodeContext ctx);
}