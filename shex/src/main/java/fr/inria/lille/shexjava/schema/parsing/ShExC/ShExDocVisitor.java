// Generated from fr\inria\lille\shexjava\schema\parsing\ShExC\ShExDoc.g4 by ANTLR 4.10.1
package fr.inria.lille.shexjava.schema.parsing.ShExC;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ShExDocParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ShExDocVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shExDoc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShExDoc(ShExDocParser.ShExDocContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirective(ShExDocParser.DirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#baseDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseDecl(ShExDocParser.BaseDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#prefixDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixDecl(ShExDocParser.PrefixDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#importDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDecl(ShExDocParser.ImportDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#notStartAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotStartAction(ShExDocParser.NotStartActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ShExDocParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#startActions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartActions(ShExDocParser.StartActionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ShExDocParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeExprDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeExprDecl(ShExDocParser.ShapeExprDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeExpression(ShExDocParser.ShapeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeExpression(ShExDocParser.InlineShapeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeOr(ShExDocParser.ShapeOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeOr(ShExDocParser.InlineShapeOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAnd(ShExDocParser.ShapeAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAnd(ShExDocParser.InlineShapeAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeNot}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeNot(ShExDocParser.ShapeNotContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeNot}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeNot(ShExDocParser.InlineShapeNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapeAtomNonLitNodeConstraint}
	 * labeled alternative in {@link ShExDocParser#shapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAtomNonLitNodeConstraint(ShExDocParser.ShapeAtomNonLitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapeAtomLitNodeConstraint}
	 * labeled alternative in {@link ShExDocParser#shapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAtomLitNodeConstraint(ShExDocParser.ShapeAtomLitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapeAtomShapeOrRef}
	 * labeled alternative in {@link ShExDocParser#shapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAtomShapeOrRef(ShExDocParser.ShapeAtomShapeOrRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapeAtomShapeExpression}
	 * labeled alternative in {@link ShExDocParser#shapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAtomShapeExpression(ShExDocParser.ShapeAtomShapeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shapeAtomAny}
	 * labeled alternative in {@link ShExDocParser#shapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeAtomAny(ShExDocParser.ShapeAtomAnyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineShapeAtomNonLitNodeConstraint}
	 * labeled alternative in {@link ShExDocParser#inlineShapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAtomNonLitNodeConstraint(ShExDocParser.InlineShapeAtomNonLitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineShapeAtomLitNodeConstraint}
	 * labeled alternative in {@link ShExDocParser#inlineShapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAtomLitNodeConstraint(ShExDocParser.InlineShapeAtomLitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineShapeAtomShapeOrRef}
	 * labeled alternative in {@link ShExDocParser#inlineShapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAtomShapeOrRef(ShExDocParser.InlineShapeAtomShapeOrRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineShapeAtomShapeExpression}
	 * labeled alternative in {@link ShExDocParser#inlineShapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAtomShapeExpression(ShExDocParser.InlineShapeAtomShapeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineShapeAtomAny}
	 * labeled alternative in {@link ShExDocParser#inlineShapeAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeAtomAny(ShExDocParser.InlineShapeAtomAnyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeOrRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeOrRef(ShExDocParser.ShapeOrRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeOrRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeOrRef(ShExDocParser.InlineShapeOrRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeRef(ShExDocParser.ShapeRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nodeConstraintLiteral}
	 * labeled alternative in {@link ShExDocParser#inlineLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeConstraintLiteral(ShExDocParser.NodeConstraintLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nodeConstraintNonLiteral}
	 * labeled alternative in {@link ShExDocParser#inlineLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeConstraintNonLiteral(ShExDocParser.NodeConstraintNonLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nodeConstraintDatatype}
	 * labeled alternative in {@link ShExDocParser#inlineLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeConstraintDatatype(ShExDocParser.NodeConstraintDatatypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nodeConstraintValueSet}
	 * labeled alternative in {@link ShExDocParser#inlineLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeConstraintValueSet(ShExDocParser.NodeConstraintValueSetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nodeConstraintNumericFacet}
	 * labeled alternative in {@link ShExDocParser#inlineLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeConstraintNumericFacet(ShExDocParser.NodeConstraintNumericFacetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#litNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitNodeConstraint(ShExDocParser.LitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code litNodeConstraintLiteral}
	 * labeled alternative in {@link ShExDocParser#inlineNonLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitNodeConstraintLiteral(ShExDocParser.LitNodeConstraintLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code litNodeConstraintStringFacet}
	 * labeled alternative in {@link ShExDocParser#inlineNonLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitNodeConstraintStringFacet(ShExDocParser.LitNodeConstraintStringFacetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#nonLitNodeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonLitNodeConstraint(ShExDocParser.NonLitNodeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#nonLiteralKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonLiteralKind(ShExDocParser.NonLiteralKindContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#xsFacet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXsFacet(ShExDocParser.XsFacetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#stringFacet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringFacet(ShExDocParser.StringFacetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#stringLength}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLength(ShExDocParser.StringLengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#numericFacet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericFacet(ShExDocParser.NumericFacetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#numericRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericRange(ShExDocParser.NumericRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#numericLength}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLength(ShExDocParser.NumericLengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#rawNumeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRawNumeric(ShExDocParser.RawNumericContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeDefinition(ShExDocParser.ShapeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#inlineShapeDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineShapeDefinition(ShExDocParser.InlineShapeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifier(ShExDocParser.QualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#extraPropertySet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtraPropertySet(ShExDocParser.ExtraPropertySetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#tripleExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTripleExpression(ShExDocParser.TripleExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#oneOfTripleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneOfTripleExpr(ShExDocParser.OneOfTripleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#multiElementOneOf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiElementOneOf(ShExDocParser.MultiElementOneOfContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#groupTripleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupTripleExpr(ShExDocParser.GroupTripleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#singleElementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementGroup(ShExDocParser.SingleElementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#multiElementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiElementGroup(ShExDocParser.MultiElementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#unaryTripleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryTripleExpr(ShExDocParser.UnaryTripleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#bracketedTripleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedTripleExpr(ShExDocParser.BracketedTripleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#tripleConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTripleConstraint(ShExDocParser.TripleConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code starCardinality}
	 * labeled alternative in {@link ShExDocParser#cardinality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStarCardinality(ShExDocParser.StarCardinalityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusCardinality}
	 * labeled alternative in {@link ShExDocParser#cardinality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusCardinality(ShExDocParser.PlusCardinalityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code optionalCardinality}
	 * labeled alternative in {@link ShExDocParser#cardinality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionalCardinality(ShExDocParser.OptionalCardinalityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code repeatCardinality}
	 * labeled alternative in {@link ShExDocParser#cardinality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatCardinality(ShExDocParser.RepeatCardinalityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exactRange}
	 * labeled alternative in {@link ShExDocParser#repeatRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExactRange(ShExDocParser.ExactRangeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minMaxRange}
	 * labeled alternative in {@link ShExDocParser#repeatRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinMaxRange(ShExDocParser.MinMaxRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#senseFlags}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSenseFlags(ShExDocParser.SenseFlagsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#valueSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueSet(ShExDocParser.ValueSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#valueSetValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueSetValue(ShExDocParser.ValueSetValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#iriRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIriRange(ShExDocParser.IriRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#iriExclusion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIriExclusion(ShExDocParser.IriExclusionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#literalRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralRange(ShExDocParser.LiteralRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#literalExclusion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExclusion(ShExDocParser.LiteralExclusionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code languageRangeFull}
	 * labeled alternative in {@link ShExDocParser#languageRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguageRangeFull(ShExDocParser.LanguageRangeFullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code languageRangeAt}
	 * labeled alternative in {@link ShExDocParser#languageRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguageRangeAt(ShExDocParser.LanguageRangeAtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#languageExclusion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguageExclusion(ShExDocParser.LanguageExclusionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#include}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclude(ShExDocParser.IncludeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(ShExDocParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#semanticAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemanticAction(ShExDocParser.SemanticActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ShExDocParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(ShExDocParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#rdfType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfType(ShExDocParser.RdfTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#datatype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatatype(ShExDocParser.DatatypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#shapeExprLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeExprLabel(ShExDocParser.ShapeExprLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#tripleExprLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTripleExprLabel(ShExDocParser.TripleExprLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(ShExDocParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#rdfLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfLiteral(ShExDocParser.RdfLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(ShExDocParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#rdfString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRdfString(ShExDocParser.RdfStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#iri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIri(ShExDocParser.IriContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#prefixedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixedName(ShExDocParser.PrefixedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#blankNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankNode(ShExDocParser.BlankNodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#extension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtension(ShExDocParser.ExtensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ShExDocParser#restriction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRestriction(ShExDocParser.RestrictionContext ctx);
}