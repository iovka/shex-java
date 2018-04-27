// Generated from fr/univLille/cristal/shex/schema/FOL/parsing/FOL.g4 by ANTLR 4.5
package fr.inria.lille.shexjava.schema.FOL.parsing;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FOLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, FORALL=5, EXISTS=6, IMPLICATION=7, AND=8, 
		OR=9, NOT=10, EQUAL=11, EQUALINF=12, EQUALSUP=13, INF=14, SUP=15, DIFF=16, 
		PASS=17, IRIREF=18, PNAME_NS=19, PNAME_LN=20, BLANK_NODE_LABEL=21, VARIABLE=22;
	public static final int
		RULE_formulas = 0, RULE_formula = 1, RULE_quantifiers = 2, RULE_quantifier = 3, 
		RULE_sentence = 4, RULE_operator = 5, RULE_atom = 6, RULE_label = 7, RULE_iri = 8, 
		RULE_prefixedName = 9, RULE_blankNode = 10;
	public static final String[] ruleNames = {
		"formulas", "formula", "quantifiers", "quantifier", "sentence", "operator", 
		"atom", "label", "iri", "prefixedName", "blankNode"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\n'", "'('", "','", "')'", null, null, null, null, null, null, 
		"'='", null, null, "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "FORALL", "EXISTS", "IMPLICATION", "AND", 
		"OR", "NOT", "EQUAL", "EQUALINF", "EQUALSUP", "INF", "SUP", "DIFF", "PASS", 
		"IRIREF", "PNAME_NS", "PNAME_LN", "BLANK_NODE_LABEL", "VARIABLE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FOLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FormulasContext extends ParserRuleContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public FormulasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formulas; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitFormulas(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulasContext formulas() throws RecognitionException {
		FormulasContext _localctx = new FormulasContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_formulas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			formula();
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(23);
				match(T__0);
				setState(24);
				formula();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormulaContext extends ParserRuleContext {
		public QuantifiersContext quantifiers() {
			return getRuleContext(QuantifiersContext.class,0);
		}
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			quantifiers();
			setState(31);
			sentence();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantifiersContext extends ParserRuleContext {
		public List<QuantifierContext> quantifier() {
			return getRuleContexts(QuantifierContext.class);
		}
		public QuantifierContext quantifier(int i) {
			return getRuleContext(QuantifierContext.class,i);
		}
		public QuantifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifiers; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitQuantifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantifiersContext quantifiers() throws RecognitionException {
		QuantifiersContext _localctx = new QuantifiersContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_quantifiers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FORALL || _la==EXISTS) {
				{
				{
				setState(33);
				quantifier();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantifierContext extends ParserRuleContext {
		public QuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifier; }
	 
		public QuantifierContext() { }
		public void copyFrom(QuantifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExistsQuantifierContext extends QuantifierContext {
		public TerminalNode EXISTS() { return getToken(FOLParser.EXISTS, 0); }
		public TerminalNode VARIABLE() { return getToken(FOLParser.VARIABLE, 0); }
		public ExistsQuantifierContext(QuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitExistsQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForallQuantifierContext extends QuantifierContext {
		public TerminalNode FORALL() { return getToken(FOLParser.FORALL, 0); }
		public TerminalNode VARIABLE() { return getToken(FOLParser.VARIABLE, 0); }
		public ForallQuantifierContext(QuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitForallQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantifierContext quantifier() throws RecognitionException {
		QuantifierContext _localctx = new QuantifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_quantifier);
		try {
			setState(43);
			switch (_input.LA(1)) {
			case FORALL:
				_localctx = new ForallQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				match(FORALL);
				setState(40);
				match(VARIABLE);
				}
				break;
			case EXISTS:
				_localctx = new ExistsQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				match(EXISTS);
				setState(42);
				match(VARIABLE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SentenceContext extends ParserRuleContext {
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
	 
		public SentenceContext() { }
		public void copyFrom(SentenceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SentenceOrContext extends SentenceContext {
		public TerminalNode OR() { return getToken(FOLParser.OR, 0); }
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public SentenceOrContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceParenthesisContext extends SentenceContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public SentenceParenthesisContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceNotContext extends SentenceContext {
		public TerminalNode NOT() { return getToken(FOLParser.NOT, 0); }
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public SentenceNotContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceAndContext extends SentenceContext {
		public TerminalNode AND() { return getToken(FOLParser.AND, 0); }
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public SentenceAndContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceImplicationContext extends SentenceContext {
		public TerminalNode IMPLICATION() { return getToken(FOLParser.IMPLICATION, 0); }
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public SentenceImplicationContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceImplication(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceOperatorContext extends SentenceContext {
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public SentenceOperatorContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentenceAtomContext extends SentenceContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public SentenceAtomContext(SentenceContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitSentenceAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_sentence);
		int _la;
		try {
			setState(85);
			switch (_input.LA(1)) {
			case IMPLICATION:
				_localctx = new SentenceImplicationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				match(IMPLICATION);
				setState(46);
				match(T__1);
				setState(47);
				sentence();
				setState(48);
				match(T__2);
				setState(49);
				sentence();
				setState(50);
				match(T__3);
				}
				break;
			case AND:
				_localctx = new SentenceAndContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				match(AND);
				setState(53);
				match(T__1);
				setState(54);
				sentence();
				setState(57); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(55);
					match(T__2);
					setState(56);
					sentence();
					}
					}
					setState(59); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__2 );
				setState(61);
				match(T__3);
				}
				break;
			case OR:
				_localctx = new SentenceOrContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(63);
				match(OR);
				setState(64);
				match(T__1);
				setState(65);
				sentence();
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(66);
					match(T__2);
					setState(67);
					sentence();
					}
					}
					setState(70); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__2 );
				setState(72);
				match(T__3);
				}
				break;
			case NOT:
				_localctx = new SentenceNotContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(74);
				match(NOT);
				setState(75);
				match(T__1);
				setState(76);
				sentence();
				setState(77);
				match(T__3);
				}
				break;
			case T__1:
				_localctx = new SentenceParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(79);
				match(T__1);
				setState(80);
				sentence();
				setState(81);
				match(T__3);
				}
				break;
			case VARIABLE:
				_localctx = new SentenceOperatorContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(83);
				operator();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
				_localctx = new SentenceAtomContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(84);
				atom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorContext extends ParserRuleContext {
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
	 
		public OperatorContext() { }
		public void copyFrom(OperatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OperatorEqualSupContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode EQUALSUP() { return getToken(FOLParser.EQUALSUP, 0); }
		public OperatorEqualSupContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorEqualSup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorDiffContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode DIFF() { return getToken(FOLParser.DIFF, 0); }
		public OperatorDiffContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorDiff(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorSupContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode SUP() { return getToken(FOLParser.SUP, 0); }
		public OperatorSupContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorSup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorEqualContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode EQUAL() { return getToken(FOLParser.EQUAL, 0); }
		public OperatorEqualContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorEqualInfContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode EQUALINF() { return getToken(FOLParser.EQUALINF, 0); }
		public OperatorEqualInfContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorEqualInf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperatorInfContext extends OperatorContext {
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TerminalNode INF() { return getToken(FOLParser.INF, 0); }
		public OperatorInfContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitOperatorInf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_operator);
		try {
			setState(105);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new OperatorEqualContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				match(VARIABLE);
				setState(88);
				match(EQUAL);
				setState(89);
				match(VARIABLE);
				}
				break;
			case 2:
				_localctx = new OperatorEqualInfContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				match(VARIABLE);
				setState(91);
				match(EQUALINF);
				setState(92);
				match(VARIABLE);
				}
				break;
			case 3:
				_localctx = new OperatorEqualSupContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
				match(VARIABLE);
				setState(94);
				match(EQUALSUP);
				setState(95);
				match(VARIABLE);
				}
				break;
			case 4:
				_localctx = new OperatorInfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(96);
				match(VARIABLE);
				setState(97);
				match(INF);
				setState(98);
				match(VARIABLE);
				}
				break;
			case 5:
				_localctx = new OperatorSupContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(99);
				match(VARIABLE);
				setState(100);
				match(SUP);
				setState(101);
				match(VARIABLE);
				}
				break;
			case 6:
				_localctx = new OperatorDiffContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(102);
				match(VARIABLE);
				setState(103);
				match(DIFF);
				setState(104);
				match(VARIABLE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ShapePredicateContext extends AtomContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(FOLParser.VARIABLE, 0); }
		public ShapePredicateContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitShapePredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TriplePredicateContext extends AtomContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public List<TerminalNode> VARIABLE() { return getTokens(FOLParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(FOLParser.VARIABLE, i);
		}
		public TriplePredicateContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitTriplePredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_atom);
		try {
			setState(119);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new ShapePredicateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				label();
				setState(108);
				match(T__1);
				setState(109);
				match(VARIABLE);
				setState(110);
				match(T__3);
				}
				break;
			case 2:
				_localctx = new TriplePredicateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
				label();
				setState(113);
				match(T__1);
				setState(114);
				match(VARIABLE);
				setState(115);
				match(T__2);
				setState(116);
				match(VARIABLE);
				setState(117);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_label);
		try {
			setState(123);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				blankNode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IriContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(FOLParser.IRIREF, 0); }
		public PrefixedNameContext prefixedName() {
			return getRuleContext(PrefixedNameContext.class,0);
		}
		public IriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iri; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriContext iri() throws RecognitionException {
		IriContext _localctx = new IriContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_iri);
		try {
			setState(127);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				match(IRIREF);
				}
				break;
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				prefixedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixedNameContext extends ParserRuleContext {
		public TerminalNode PNAME_LN() { return getToken(FOLParser.PNAME_LN, 0); }
		public TerminalNode PNAME_NS() { return getToken(FOLParser.PNAME_NS, 0); }
		public PrefixedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitPrefixedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixedNameContext prefixedName() throws RecognitionException {
		PrefixedNameContext _localctx = new PrefixedNameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_la = _input.LA(1);
			if ( !(_la==PNAME_NS || _la==PNAME_LN) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlankNodeContext extends ParserRuleContext {
		public TerminalNode BLANK_NODE_LABEL() { return getToken(FOLParser.BLANK_NODE_LABEL, 0); }
		public BlankNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOLVisitor ) return ((FOLVisitor<? extends T>)visitor).visitBlankNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodeContext blankNode() throws RecognitionException {
		BlankNodeContext _localctx = new BlankNodeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(BLANK_NODE_LABEL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\30\u0088\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\3\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\3\3\3\4"+
		"\7\4%\n\4\f\4\16\4(\13\4\3\5\3\5\3\5\3\5\5\5.\n\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6<\n\6\r\6\16\6=\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\6\6G\n\6\r\6\16\6H\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6X\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\5\7l\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\5\bz\n\b\3\t\3\t\5\t~\n\t\3\n\3\n\5\n\u0082\n\n\3\13\3\13\3\f"+
		"\3\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\3\2\25\26\u008f\2\30\3\2"+
		"\2\2\4 \3\2\2\2\6&\3\2\2\2\b-\3\2\2\2\nW\3\2\2\2\fk\3\2\2\2\16y\3\2\2"+
		"\2\20}\3\2\2\2\22\u0081\3\2\2\2\24\u0083\3\2\2\2\26\u0085\3\2\2\2\30\35"+
		"\5\4\3\2\31\32\7\3\2\2\32\34\5\4\3\2\33\31\3\2\2\2\34\37\3\2\2\2\35\33"+
		"\3\2\2\2\35\36\3\2\2\2\36\3\3\2\2\2\37\35\3\2\2\2 !\5\6\4\2!\"\5\n\6\2"+
		"\"\5\3\2\2\2#%\5\b\5\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'\7\3"+
		"\2\2\2(&\3\2\2\2)*\7\7\2\2*.\7\30\2\2+,\7\b\2\2,.\7\30\2\2-)\3\2\2\2-"+
		"+\3\2\2\2.\t\3\2\2\2/\60\7\t\2\2\60\61\7\4\2\2\61\62\5\n\6\2\62\63\7\5"+
		"\2\2\63\64\5\n\6\2\64\65\7\6\2\2\65X\3\2\2\2\66\67\7\n\2\2\678\7\4\2\2"+
		"8;\5\n\6\29:\7\5\2\2:<\5\n\6\2;9\3\2\2\2<=\3\2\2\2=;\3\2\2\2=>\3\2\2\2"+
		">?\3\2\2\2?@\7\6\2\2@X\3\2\2\2AB\7\13\2\2BC\7\4\2\2CF\5\n\6\2DE\7\5\2"+
		"\2EG\5\n\6\2FD\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\7\6\2"+
		"\2KX\3\2\2\2LM\7\f\2\2MN\7\4\2\2NO\5\n\6\2OP\7\6\2\2PX\3\2\2\2QR\7\4\2"+
		"\2RS\5\n\6\2ST\7\6\2\2TX\3\2\2\2UX\5\f\7\2VX\5\16\b\2W/\3\2\2\2W\66\3"+
		"\2\2\2WA\3\2\2\2WL\3\2\2\2WQ\3\2\2\2WU\3\2\2\2WV\3\2\2\2X\13\3\2\2\2Y"+
		"Z\7\30\2\2Z[\7\r\2\2[l\7\30\2\2\\]\7\30\2\2]^\7\16\2\2^l\7\30\2\2_`\7"+
		"\30\2\2`a\7\17\2\2al\7\30\2\2bc\7\30\2\2cd\7\20\2\2dl\7\30\2\2ef\7\30"+
		"\2\2fg\7\21\2\2gl\7\30\2\2hi\7\30\2\2ij\7\22\2\2jl\7\30\2\2kY\3\2\2\2"+
		"k\\\3\2\2\2k_\3\2\2\2kb\3\2\2\2ke\3\2\2\2kh\3\2\2\2l\r\3\2\2\2mn\5\20"+
		"\t\2no\7\4\2\2op\7\30\2\2pq\7\6\2\2qz\3\2\2\2rs\5\20\t\2st\7\4\2\2tu\7"+
		"\30\2\2uv\7\5\2\2vw\7\30\2\2wx\7\6\2\2xz\3\2\2\2ym\3\2\2\2yr\3\2\2\2z"+
		"\17\3\2\2\2{~\5\22\n\2|~\5\26\f\2}{\3\2\2\2}|\3\2\2\2~\21\3\2\2\2\177"+
		"\u0082\7\24\2\2\u0080\u0082\5\24\13\2\u0081\177\3\2\2\2\u0081\u0080\3"+
		"\2\2\2\u0082\23\3\2\2\2\u0083\u0084\t\2\2\2\u0084\25\3\2\2\2\u0085\u0086"+
		"\7\27\2\2\u0086\27\3\2\2\2\f\35&-=HWky}\u0081";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}