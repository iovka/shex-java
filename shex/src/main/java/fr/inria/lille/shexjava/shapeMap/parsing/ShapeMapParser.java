// Generated from fr/inria/lille/shexjava/shapeMap/parsing/ShapeMap.g4 by ANTLR 4.7.1
package fr.inria.lille.shexjava.shapeMap.parsing;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShapeMapParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, KW_TRUE=9, 
		KW_FALSE=10, PNAME_LN=11, PNAME_NS=12, IRIREF=13, BLANK_NODE_LABEL=14, 
		RDF_TYPE=15, LANGTAG=16, INTEGER=17, DECIMAL=18, DOUBLE=19, EXPONENT=20, 
		STRING_LITERAL1=21, STRING_LITERAL2=22, STRING_LITERAL_LONG1=23, STRING_LITERAL_LONG2=24, 
		UCHAR=25, ECHAR=26, PN_CHARS_BASE=27, PN_CHARS_U=28, PN_CHARS=29, PN_PREFIX=30, 
		PN_LOCAL=31, PLX=32, PERCENT=33, HEX=34, PN_LOCAL_ESC=35, PASS=36;
	public static final int
		RULE_shapeMap = 0, RULE_shapeAssociation = 1, RULE_shapeSpec = 2, RULE_nodeSpec = 3, 
		RULE_triplePattern = 4, RULE_objectTerm = 5, RULE_subjectTerm = 6, RULE_literal = 7, 
		RULE_numericLiteral = 8, RULE_rdfLiteral = 9, RULE_booleanLiteral = 10, 
		RULE_string = 11, RULE_langString = 12, RULE_predicate = 13, RULE_rdfType = 14, 
		RULE_iri = 15, RULE_prefixedName = 16, RULE_blankNode = 17;
	public static final String[] ruleNames = {
		"shapeMap", "shapeAssociation", "shapeSpec", "nodeSpec", "triplePattern", 
		"objectTerm", "subjectTerm", "literal", "numericLiteral", "rdfLiteral", 
		"booleanLiteral", "string", "langString", "predicate", "rdfType", "iri", 
		"prefixedName", "blankNode"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'@'", "'START'", "'{'", "'FOCUS'", "'_'", "'}'", "'^^'", 
		"'true'", "'false'", null, null, null, null, "'a'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "KW_TRUE", "KW_FALSE", 
		"PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", "RDF_TYPE", "LANGTAG", 
		"INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", "STRING_LITERAL2", 
		"STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "UCHAR", "ECHAR", "PN_CHARS_BASE", 
		"PN_CHARS_U", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", "PLX", "PERCENT", "HEX", 
		"PN_LOCAL_ESC", "PASS"
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
	public String getGrammarFileName() { return "ShapeMap.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ShapeMapParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ShapeMapContext extends ParserRuleContext {
		public List<ShapeAssociationContext> shapeAssociation() {
			return getRuleContexts(ShapeAssociationContext.class);
		}
		public ShapeAssociationContext shapeAssociation(int i) {
			return getRuleContext(ShapeAssociationContext.class,i);
		}
		public ShapeMapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeMap; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitShapeMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeMapContext shapeMap() throws RecognitionException {
		ShapeMapContext _localctx = new ShapeMapContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_shapeMap);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			shapeAssociation();
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(37);
				match(T__0);
				setState(38);
				shapeAssociation();
				}
				}
				setState(43);
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

	public static class ShapeAssociationContext extends ParserRuleContext {
		public NodeSpecContext nodeSpec() {
			return getRuleContext(NodeSpecContext.class,0);
		}
		public ShapeSpecContext shapeSpec() {
			return getRuleContext(ShapeSpecContext.class,0);
		}
		public ShapeAssociationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeAssociation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitShapeAssociation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeAssociationContext shapeAssociation() throws RecognitionException {
		ShapeAssociationContext _localctx = new ShapeAssociationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_shapeAssociation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			nodeSpec();
			setState(45);
			shapeSpec();
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

	public static class ShapeSpecContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public ShapeSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitShapeSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeSpecContext shapeSpec() throws RecognitionException {
		ShapeSpecContext _localctx = new ShapeSpecContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_shapeSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__1);
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PNAME_LN:
			case PNAME_NS:
			case IRIREF:
				{
				setState(48);
				iri();
				}
				break;
			case T__2:
				{
				setState(49);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class NodeSpecContext extends ParserRuleContext {
		public ObjectTermContext objectTerm() {
			return getRuleContext(ObjectTermContext.class,0);
		}
		public TriplePatternContext triplePattern() {
			return getRuleContext(TriplePatternContext.class,0);
		}
		public NodeSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitNodeSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeSpecContext nodeSpec() throws RecognitionException {
		NodeSpecContext _localctx = new NodeSpecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_nodeSpec);
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_TRUE:
			case KW_FALSE:
			case PNAME_LN:
			case PNAME_NS:
			case IRIREF:
			case BLANK_NODE_LABEL:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				objectTerm();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				triplePattern();
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

	public static class TriplePatternContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public ObjectTermContext objectTerm() {
			return getRuleContext(ObjectTermContext.class,0);
		}
		public SubjectTermContext subjectTerm() {
			return getRuleContext(SubjectTermContext.class,0);
		}
		public TriplePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triplePattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitTriplePattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriplePatternContext triplePattern() throws RecognitionException {
		TriplePatternContext _localctx = new TriplePatternContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_triplePattern);
		try {
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(T__3);
				setState(57);
				match(T__4);
				setState(58);
				predicate();
				setState(61);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case KW_TRUE:
				case KW_FALSE:
				case PNAME_LN:
				case PNAME_NS:
				case IRIREF:
				case BLANK_NODE_LABEL:
				case INTEGER:
				case DECIMAL:
				case DOUBLE:
				case STRING_LITERAL1:
				case STRING_LITERAL2:
				case STRING_LITERAL_LONG1:
				case STRING_LITERAL_LONG2:
					{
					setState(59);
					objectTerm();
					}
					break;
				case T__5:
					{
					setState(60);
					match(T__5);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(63);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				match(T__3);
				setState(68);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PNAME_LN:
				case PNAME_NS:
				case IRIREF:
				case BLANK_NODE_LABEL:
					{
					setState(66);
					subjectTerm();
					}
					break;
				case T__5:
					{
					setState(67);
					match(T__5);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(70);
				predicate();
				setState(71);
				match(T__4);
				setState(72);
				match(T__6);
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

	public static class ObjectTermContext extends ParserRuleContext {
		public SubjectTermContext subjectTerm() {
			return getRuleContext(SubjectTermContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ObjectTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitObjectTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectTermContext objectTerm() throws RecognitionException {
		ObjectTermContext _localctx = new ObjectTermContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_objectTerm);
		try {
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PNAME_LN:
			case PNAME_NS:
			case IRIREF:
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				subjectTerm();
				}
				break;
			case KW_TRUE:
			case KW_FALSE:
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				literal();
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

	public static class SubjectTermContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public SubjectTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subjectTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitSubjectTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubjectTermContext subjectTerm() throws RecognitionException {
		SubjectTermContext _localctx = new SubjectTermContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_subjectTerm);
		try {
			setState(82);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PNAME_LN:
			case PNAME_NS:
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
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

	public static class LiteralContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public RdfLiteralContext rdfLiteral() {
			return getRuleContext(RdfLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_literal);
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				numericLiteral();
				}
				break;
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				rdfLiteral();
				}
				break;
			case KW_TRUE:
			case KW_FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				booleanLiteral();
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

	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ShapeMapParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(ShapeMapParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(ShapeMapParser.DOUBLE, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_numericLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTEGER) | (1L << DECIMAL) | (1L << DOUBLE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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

	public static class RdfLiteralContext extends ParserRuleContext {
		public LangStringContext langString() {
			return getRuleContext(LangStringContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public RdfLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitRdfLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfLiteralContext rdfLiteral() throws RecognitionException {
		RdfLiteralContext _localctx = new RdfLiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_rdfLiteral);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				langString();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				string();
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(93);
					match(T__7);
					setState(94);
					iri();
					}
				}

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

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode KW_TRUE() { return getToken(ShapeMapParser.KW_TRUE, 0); }
		public TerminalNode KW_FALSE() { return getToken(ShapeMapParser.KW_FALSE, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_la = _input.LA(1);
			if ( !(_la==KW_TRUE || _la==KW_FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL_LONG1() { return getToken(ShapeMapParser.STRING_LITERAL_LONG1, 0); }
		public TerminalNode STRING_LITERAL_LONG2() { return getToken(ShapeMapParser.STRING_LITERAL_LONG2, 0); }
		public TerminalNode STRING_LITERAL1() { return getToken(ShapeMapParser.STRING_LITERAL1, 0); }
		public TerminalNode STRING_LITERAL2() { return getToken(ShapeMapParser.STRING_LITERAL2, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING_LITERAL1) | (1L << STRING_LITERAL2) | (1L << STRING_LITERAL_LONG1) | (1L << STRING_LITERAL_LONG2))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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

	public static class LangStringContext extends ParserRuleContext {
		public TerminalNode LANGTAG() { return getToken(ShapeMapParser.LANGTAG, 0); }
		public TerminalNode STRING_LITERAL_LONG1() { return getToken(ShapeMapParser.STRING_LITERAL_LONG1, 0); }
		public TerminalNode STRING_LITERAL_LONG2() { return getToken(ShapeMapParser.STRING_LITERAL_LONG2, 0); }
		public TerminalNode STRING_LITERAL1() { return getToken(ShapeMapParser.STRING_LITERAL1, 0); }
		public TerminalNode STRING_LITERAL2() { return getToken(ShapeMapParser.STRING_LITERAL2, 0); }
		public LangStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_langString; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitLangString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LangStringContext langString() throws RecognitionException {
		LangStringContext _localctx = new LangStringContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_langString);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING_LITERAL1) | (1L << STRING_LITERAL2) | (1L << STRING_LITERAL_LONG1) | (1L << STRING_LITERAL_LONG2))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(104);
			match(LANGTAG);
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

	public static class PredicateContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public RdfTypeContext rdfType() {
			return getRuleContext(RdfTypeContext.class,0);
		}
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_predicate);
		try {
			setState(108);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PNAME_LN:
			case PNAME_NS:
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				iri();
				}
				break;
			case RDF_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				rdfType();
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

	public static class RdfTypeContext extends ParserRuleContext {
		public TerminalNode RDF_TYPE() { return getToken(ShapeMapParser.RDF_TYPE, 0); }
		public RdfTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitRdfType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfTypeContext rdfType() throws RecognitionException {
		RdfTypeContext _localctx = new RdfTypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_rdfType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(RDF_TYPE);
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
		public TerminalNode IRIREF() { return getToken(ShapeMapParser.IRIREF, 0); }
		public PrefixedNameContext prefixedName() {
			return getRuleContext(PrefixedNameContext.class,0);
		}
		public IriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iri; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriContext iri() throws RecognitionException {
		IriContext _localctx = new IriContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_iri);
		try {
			setState(114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				match(IRIREF);
				}
				break;
			case PNAME_LN:
			case PNAME_NS:
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
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
		public TerminalNode PNAME_LN() { return getToken(ShapeMapParser.PNAME_LN, 0); }
		public TerminalNode PNAME_NS() { return getToken(ShapeMapParser.PNAME_NS, 0); }
		public PrefixedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitPrefixedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixedNameContext prefixedName() throws RecognitionException {
		PrefixedNameContext _localctx = new PrefixedNameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			_la = _input.LA(1);
			if ( !(_la==PNAME_LN || _la==PNAME_NS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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
		public TerminalNode BLANK_NODE_LABEL() { return getToken(ShapeMapParser.BLANK_NODE_LABEL, 0); }
		public BlankNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShapeMapVisitor ) return ((ShapeMapVisitor<? extends T>)visitor).visitBlankNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodeContext blankNode() throws RecognitionException {
		BlankNodeContext _localctx = new BlankNodeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&{\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23\t"+
		"\23\3\2\3\2\3\2\7\2*\n\2\f\2\16\2-\13\2\3\3\3\3\3\3\3\4\3\4\3\4\5\4\65"+
		"\n\4\3\5\3\5\5\59\n\5\3\6\3\6\3\6\3\6\3\6\5\6@\n\6\3\6\3\6\3\6\3\6\3\6"+
		"\5\6G\n\6\3\6\3\6\3\6\3\6\5\6M\n\6\3\7\3\7\5\7Q\n\7\3\b\3\b\5\bU\n\b\3"+
		"\t\3\t\3\t\5\tZ\n\t\3\n\3\n\3\13\3\13\3\13\3\13\5\13b\n\13\5\13d\n\13"+
		"\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\5\17o\n\17\3\20\3\20\3\21\3"+
		"\21\5\21u\n\21\3\22\3\22\3\23\3\23\3\23\2\2\24\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$\2\6\3\2\23\25\3\2\13\f\3\2\27\32\3\2\r\16\2v\2&\3"+
		"\2\2\2\4.\3\2\2\2\6\61\3\2\2\2\b8\3\2\2\2\nL\3\2\2\2\fP\3\2\2\2\16T\3"+
		"\2\2\2\20Y\3\2\2\2\22[\3\2\2\2\24c\3\2\2\2\26e\3\2\2\2\30g\3\2\2\2\32"+
		"i\3\2\2\2\34n\3\2\2\2\36p\3\2\2\2 t\3\2\2\2\"v\3\2\2\2$x\3\2\2\2&+\5\4"+
		"\3\2\'(\7\3\2\2(*\5\4\3\2)\'\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\3"+
		"\3\2\2\2-+\3\2\2\2./\5\b\5\2/\60\5\6\4\2\60\5\3\2\2\2\61\64\7\4\2\2\62"+
		"\65\5 \21\2\63\65\7\5\2\2\64\62\3\2\2\2\64\63\3\2\2\2\65\7\3\2\2\2\66"+
		"9\5\f\7\2\679\5\n\6\28\66\3\2\2\28\67\3\2\2\29\t\3\2\2\2:;\7\6\2\2;<\7"+
		"\7\2\2<?\5\34\17\2=@\5\f\7\2>@\7\b\2\2?=\3\2\2\2?>\3\2\2\2@A\3\2\2\2A"+
		"B\7\t\2\2BM\3\2\2\2CF\7\6\2\2DG\5\16\b\2EG\7\b\2\2FD\3\2\2\2FE\3\2\2\2"+
		"GH\3\2\2\2HI\5\34\17\2IJ\7\7\2\2JK\7\t\2\2KM\3\2\2\2L:\3\2\2\2LC\3\2\2"+
		"\2M\13\3\2\2\2NQ\5\16\b\2OQ\5\20\t\2PN\3\2\2\2PO\3\2\2\2Q\r\3\2\2\2RU"+
		"\5 \21\2SU\5$\23\2TR\3\2\2\2TS\3\2\2\2U\17\3\2\2\2VZ\5\22\n\2WZ\5\24\13"+
		"\2XZ\5\26\f\2YV\3\2\2\2YW\3\2\2\2YX\3\2\2\2Z\21\3\2\2\2[\\\t\2\2\2\\\23"+
		"\3\2\2\2]d\5\32\16\2^a\5\30\r\2_`\7\n\2\2`b\5 \21\2a_\3\2\2\2ab\3\2\2"+
		"\2bd\3\2\2\2c]\3\2\2\2c^\3\2\2\2d\25\3\2\2\2ef\t\3\2\2f\27\3\2\2\2gh\t"+
		"\4\2\2h\31\3\2\2\2ij\t\4\2\2jk\7\22\2\2k\33\3\2\2\2lo\5 \21\2mo\5\36\20"+
		"\2nl\3\2\2\2nm\3\2\2\2o\35\3\2\2\2pq\7\21\2\2q\37\3\2\2\2ru\7\17\2\2s"+
		"u\5\"\22\2tr\3\2\2\2ts\3\2\2\2u!\3\2\2\2vw\t\5\2\2w#\3\2\2\2xy\7\20\2"+
		"\2y%\3\2\2\2\17+\648?FLPTYacnt";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}