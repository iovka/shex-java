// Generated from fr/inria/lille/shexjava/schema/parsing/ShExC/ShExDoc.g4 by ANTLR 4.7.1
package fr.inria.lille.shexjava.schema.parsing.ShExC;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShExDocParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, KW_ABSTRACT=23, KW_BASE=24, 
		KW_EXTERNAL=25, KW_EXTENDS=26, KW_IMPORT=27, KW_PREFIX=28, KW_START=29, 
		KW_VIRTUAL=30, KW_CLOSED=31, KW_EXTRA=32, KW_LITERAL=33, KW_IRI=34, KW_NONLITERAL=35, 
		KW_BNODE=36, KW_AND=37, KW_OR=38, KW_MININCLUSIVE=39, KW_MINEXCLUSIVE=40, 
		KW_MAXINCLUSIVE=41, KW_MAXEXCLUSIVE=42, KW_LENGTH=43, KW_MINLENGTH=44, 
		KW_MAXLENGTH=45, KW_TOTALDIGITS=46, KW_FRACTIONDIGITS=47, KW_NOT=48, KW_TRUE=49, 
		KW_FALSE=50, PASS=51, COMMENT=52, CODE=53, RDF_TYPE=54, IRIREF=55, PNAME_NS=56, 
		PNAME_LN=57, ATPNAME_NS=58, ATPNAME_LN=59, REGEXP=60, REGEXP_FLAGS=61, 
		BLANK_NODE_LABEL=62, LANGTAG=63, INTEGER=64, DECIMAL=65, DOUBLE=66, STEM_MARK=67, 
		UNBOUNDED=68, STRING_LITERAL1=69, STRING_LITERAL2=70, STRING_LITERAL_LONG1=71, 
		STRING_LITERAL_LONG2=72;
	public static final int
		RULE_shExDoc = 0, RULE_directive = 1, RULE_baseDecl = 2, RULE_prefixDecl = 3, 
		RULE_importDecl = 4, RULE_notStartAction = 5, RULE_start = 6, RULE_startActions = 7, 
		RULE_statement = 8, RULE_shapeExprDecl = 9, RULE_shapeExpression = 10, 
		RULE_shapeOr = 11, RULE_shapeAnd = 12, RULE_shapeNot = 13, RULE_negation = 14, 
		RULE_inlineShapeExpression = 15, RULE_inlineShapeOr = 16, RULE_inlineShapeAnd = 17, 
		RULE_inlineShapeNot = 18, RULE_inlineShapeDefinition = 19, RULE_shapeDefinition = 20, 
		RULE_qualifier = 21, RULE_extraPropertySet = 22, RULE_oneOfShape = 23, 
		RULE_multiElementOneOf = 24, RULE_groupShape = 25, RULE_singleElementGroup = 26, 
		RULE_multiElementGroup = 27, RULE_unaryShape = 28, RULE_encapsulatedShape = 29, 
		RULE_shapeAtom = 30, RULE_inlineShapeAtom = 31, RULE_nodeConstraint = 32, 
		RULE_nonLiteralKind = 33, RULE_xsFacet = 34, RULE_stringFacet = 35, RULE_stringLength = 36, 
		RULE_numericFacet = 37, RULE_numericRange = 38, RULE_numericLength = 39, 
		RULE_tripleConstraint = 40, RULE_senseFlags = 41, RULE_valueSet = 42, 
		RULE_valueSetValue = 43, RULE_iriRange = 44, RULE_iriExclusion = 45, RULE_literalRange = 46, 
		RULE_literalExclusion = 47, RULE_languageRange = 48, RULE_languageExclusion = 49, 
		RULE_literal = 50, RULE_shapeOrRef = 51, RULE_inlineShapeOrRef = 52, RULE_shapeRef = 53, 
		RULE_include = 54, RULE_semanticActions = 55, RULE_annotation = 56, RULE_predicate = 57, 
		RULE_rdfType = 58, RULE_datatype = 59, RULE_cardinality = 60, RULE_repeatRange = 61, 
		RULE_shapeExprLabel = 62, RULE_tripleExprLabel = 63, RULE_numericLiteral = 64, 
		RULE_rdfLiteral = 65, RULE_booleanLiteral = 66, RULE_string = 67, RULE_iri = 68, 
		RULE_prefixedName = 69, RULE_blankNode = 70, RULE_extension = 71, RULE_semanticAction = 72;
	public static final String[] ruleNames = {
		"shExDoc", "directive", "baseDecl", "prefixDecl", "importDecl", "notStartAction", 
		"start", "startActions", "statement", "shapeExprDecl", "shapeExpression", 
		"shapeOr", "shapeAnd", "shapeNot", "negation", "inlineShapeExpression", 
		"inlineShapeOr", "inlineShapeAnd", "inlineShapeNot", "inlineShapeDefinition", 
		"shapeDefinition", "qualifier", "extraPropertySet", "oneOfShape", "multiElementOneOf", 
		"groupShape", "singleElementGroup", "multiElementGroup", "unaryShape", 
		"encapsulatedShape", "shapeAtom", "inlineShapeAtom", "nodeConstraint", 
		"nonLiteralKind", "xsFacet", "stringFacet", "stringLength", "numericFacet", 
		"numericRange", "numericLength", "tripleConstraint", "senseFlags", "valueSet", 
		"valueSetValue", "iriRange", "iriExclusion", "literalRange", "literalExclusion", 
		"languageRange", "languageExclusion", "literal", "shapeOrRef", "inlineShapeOrRef", 
		"shapeRef", "include", "semanticActions", "annotation", "predicate", "rdfType", 
		"datatype", "cardinality", "repeatRange", "shapeExprLabel", "tripleExprLabel", 
		"numericLiteral", "rdfLiteral", "booleanLiteral", "string", "iri", "prefixedName", 
		"blankNode", "extension", "semanticAction"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'!'", "'{'", "'}'", "'|'", "';'", "'$'", "'('", "')'", "'.'", 
		"'^'", "'['", "']'", "'-'", "'@'", "'&'", "'//'", "'+'", "'?'", "','", 
		"'^^'", "'%'", null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "'true'", "'false'", null, null, null, "'a'", 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"'~'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "KW_ABSTRACT", 
		"KW_BASE", "KW_EXTERNAL", "KW_EXTENDS", "KW_IMPORT", "KW_PREFIX", "KW_START", 
		"KW_VIRTUAL", "KW_CLOSED", "KW_EXTRA", "KW_LITERAL", "KW_IRI", "KW_NONLITERAL", 
		"KW_BNODE", "KW_AND", "KW_OR", "KW_MININCLUSIVE", "KW_MINEXCLUSIVE", "KW_MAXINCLUSIVE", 
		"KW_MAXEXCLUSIVE", "KW_LENGTH", "KW_MINLENGTH", "KW_MAXLENGTH", "KW_TOTALDIGITS", 
		"KW_FRACTIONDIGITS", "KW_NOT", "KW_TRUE", "KW_FALSE", "PASS", "COMMENT", 
		"CODE", "RDF_TYPE", "IRIREF", "PNAME_NS", "PNAME_LN", "ATPNAME_NS", "ATPNAME_LN", 
		"REGEXP", "REGEXP_FLAGS", "BLANK_NODE_LABEL", "LANGTAG", "INTEGER", "DECIMAL", 
		"DOUBLE", "STEM_MARK", "UNBOUNDED", "STRING_LITERAL1", "STRING_LITERAL2", 
		"STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2"
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
	public String getGrammarFileName() { return "ShExDoc.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ShExDocParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ShExDocContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ShExDocParser.EOF, 0); }
		public List<DirectiveContext> directive() {
			return getRuleContexts(DirectiveContext.class);
		}
		public DirectiveContext directive(int i) {
			return getRuleContext(DirectiveContext.class,i);
		}
		public NotStartActionContext notStartAction() {
			return getRuleContext(NotStartActionContext.class,0);
		}
		public StartActionsContext startActions() {
			return getRuleContext(StartActionsContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ShExDocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shExDoc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShExDoc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShExDocContext shExDoc() throws RecognitionException {
		ShExDocContext _localctx = new ShExDocContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_shExDoc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX))) != 0)) {
				{
				{
				setState(146);
				directive();
				}
				}
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << KW_ABSTRACT) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
				{
				setState(154);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case KW_ABSTRACT:
				case KW_START:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
				case BLANK_NODE_LABEL:
					{
					setState(152);
					notStartAction();
					}
					break;
				case T__21:
					{
					setState(153);
					startActions();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_ABSTRACT) | (1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
					{
					{
					setState(156);
					statement();
					}
					}
					setState(161);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(164);
			match(EOF);
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

	public static class DirectiveContext extends ParserRuleContext {
		public BaseDeclContext baseDecl() {
			return getRuleContext(BaseDeclContext.class,0);
		}
		public PrefixDeclContext prefixDecl() {
			return getRuleContext(PrefixDeclContext.class,0);
		}
		public ImportDeclContext importDecl() {
			return getRuleContext(ImportDeclContext.class,0);
		}
		public DirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitDirective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DirectiveContext directive() throws RecognitionException {
		DirectiveContext _localctx = new DirectiveContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_directive);
		try {
			setState(169);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_BASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				baseDecl();
				}
				break;
			case KW_PREFIX:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				prefixDecl();
				}
				break;
			case KW_IMPORT:
				enterOuterAlt(_localctx, 3);
				{
				setState(168);
				importDecl();
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

	public static class BaseDeclContext extends ParserRuleContext {
		public TerminalNode KW_BASE() { return getToken(ShExDocParser.KW_BASE, 0); }
		public TerminalNode IRIREF() { return getToken(ShExDocParser.IRIREF, 0); }
		public BaseDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitBaseDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseDeclContext baseDecl() throws RecognitionException {
		BaseDeclContext _localctx = new BaseDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_baseDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(KW_BASE);
			setState(172);
			match(IRIREF);
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

	public static class PrefixDeclContext extends ParserRuleContext {
		public TerminalNode KW_PREFIX() { return getToken(ShExDocParser.KW_PREFIX, 0); }
		public TerminalNode PNAME_NS() { return getToken(ShExDocParser.PNAME_NS, 0); }
		public TerminalNode IRIREF() { return getToken(ShExDocParser.IRIREF, 0); }
		public PrefixDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitPrefixDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixDeclContext prefixDecl() throws RecognitionException {
		PrefixDeclContext _localctx = new PrefixDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_prefixDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(KW_PREFIX);
			setState(175);
			match(PNAME_NS);
			setState(176);
			match(IRIREF);
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

	public static class ImportDeclContext extends ParserRuleContext {
		public TerminalNode KW_IMPORT() { return getToken(ShExDocParser.KW_IMPORT, 0); }
		public TerminalNode IRIREF() { return getToken(ShExDocParser.IRIREF, 0); }
		public ImportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitImportDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclContext importDecl() throws RecognitionException {
		ImportDeclContext _localctx = new ImportDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_importDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(KW_IMPORT);
			setState(179);
			match(IRIREF);
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

	public static class NotStartActionContext extends ParserRuleContext {
		public StartContext start() {
			return getRuleContext(StartContext.class,0);
		}
		public ShapeExprDeclContext shapeExprDecl() {
			return getRuleContext(ShapeExprDeclContext.class,0);
		}
		public NotStartActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notStartAction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNotStartAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotStartActionContext notStartAction() throws RecognitionException {
		NotStartActionContext _localctx = new NotStartActionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_notStartAction);
		try {
			setState(183);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(181);
				start();
				}
				break;
			case KW_ABSTRACT:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(182);
				shapeExprDecl();
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

	public static class StartContext extends ParserRuleContext {
		public TerminalNode KW_START() { return getToken(ShExDocParser.KW_START, 0); }
		public ShapeExpressionContext shapeExpression() {
			return getRuleContext(ShapeExpressionContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(KW_START);
			setState(186);
			match(T__0);
			setState(187);
			shapeExpression();
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

	public static class StartActionsContext extends ParserRuleContext {
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
		}
		public StartActionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startActions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStartActions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartActionsContext startActions() throws RecognitionException {
		StartActionsContext _localctx = new StartActionsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_startActions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(189);
				semanticAction();
				}
				}
				setState(192); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__21 );
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

	public static class StatementContext extends ParserRuleContext {
		public DirectiveContext directive() {
			return getRuleContext(DirectiveContext.class,0);
		}
		public NotStartActionContext notStartAction() {
			return getRuleContext(NotStartActionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		try {
			setState(196);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_BASE:
			case KW_IMPORT:
			case KW_PREFIX:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				directive();
				}
				break;
			case KW_ABSTRACT:
			case KW_START:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				notStartAction();
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

	public static class ShapeExprDeclContext extends ParserRuleContext {
		public ShapeExprDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeExprDecl; }
	 
		public ShapeExprDeclContext() { }
		public void copyFrom(ShapeExprDeclContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BaseShapeExpressionContext extends ShapeExprDeclContext {
		public ShapeExprLabelContext shapeExprLabel() {
			return getRuleContext(ShapeExprLabelContext.class,0);
		}
		public ShapeExpressionContext shapeExpression() {
			return getRuleContext(ShapeExpressionContext.class,0);
		}
		public TerminalNode KW_EXTERNAL() { return getToken(ShExDocParser.KW_EXTERNAL, 0); }
		public TerminalNode KW_ABSTRACT() { return getToken(ShExDocParser.KW_ABSTRACT, 0); }
		public BaseShapeExpressionContext(ShapeExprDeclContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitBaseShapeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeExprDeclContext shapeExprDecl() throws RecognitionException {
		ShapeExprDeclContext _localctx = new ShapeExprDeclContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_shapeExprDecl);
		int _la;
		try {
			_localctx = new BaseShapeExpressionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KW_ABSTRACT) {
				{
				setState(198);
				match(KW_ABSTRACT);
				}
			}

			setState(201);
			shapeExprLabel();
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__2:
			case T__7:
			case T__9:
			case T__11:
			case T__14:
			case T__15:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
			case KW_LITERAL:
			case KW_IRI:
			case KW_NONLITERAL:
			case KW_BNODE:
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
			case KW_NOT:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case ATPNAME_NS:
			case ATPNAME_LN:
			case REGEXP:
				{
				setState(202);
				shapeExpression();
				}
				break;
			case KW_EXTERNAL:
				{
				setState(203);
				match(KW_EXTERNAL);
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

	public static class ShapeExpressionContext extends ParserRuleContext {
		public ShapeOrContext shapeOr() {
			return getRuleContext(ShapeOrContext.class,0);
		}
		public ShapeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeExpressionContext shapeExpression() throws RecognitionException {
		ShapeExpressionContext _localctx = new ShapeExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_shapeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			shapeOr();
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

	public static class ShapeOrContext extends ParserRuleContext {
		public List<ShapeAndContext> shapeAnd() {
			return getRuleContexts(ShapeAndContext.class);
		}
		public ShapeAndContext shapeAnd(int i) {
			return getRuleContext(ShapeAndContext.class,i);
		}
		public List<TerminalNode> KW_OR() { return getTokens(ShExDocParser.KW_OR); }
		public TerminalNode KW_OR(int i) {
			return getToken(ShExDocParser.KW_OR, i);
		}
		public ShapeOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeOr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeOrContext shapeOr() throws RecognitionException {
		ShapeOrContext _localctx = new ShapeOrContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_shapeOr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			shapeAnd();
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_OR) {
				{
				{
				setState(209);
				match(KW_OR);
				setState(210);
				shapeAnd();
				}
				}
				setState(215);
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

	public static class ShapeAndContext extends ParserRuleContext {
		public List<ShapeNotContext> shapeNot() {
			return getRuleContexts(ShapeNotContext.class);
		}
		public ShapeNotContext shapeNot(int i) {
			return getRuleContext(ShapeNotContext.class,i);
		}
		public List<TerminalNode> KW_AND() { return getTokens(ShExDocParser.KW_AND); }
		public TerminalNode KW_AND(int i) {
			return getToken(ShExDocParser.KW_AND, i);
		}
		public ShapeAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeAnd; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeAndContext shapeAnd() throws RecognitionException {
		ShapeAndContext _localctx = new ShapeAndContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_shapeAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			shapeNot();
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(217);
				match(KW_AND);
				setState(218);
				shapeNot();
				}
				}
				setState(223);
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

	public static class ShapeNotContext extends ParserRuleContext {
		public ShapeAtomContext shapeAtom() {
			return getRuleContext(ShapeAtomContext.class,0);
		}
		public NegationContext negation() {
			return getRuleContext(NegationContext.class,0);
		}
		public ShapeNotContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeNot; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeNot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeNotContext shapeNot() throws RecognitionException {
		ShapeNotContext _localctx = new ShapeNotContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_shapeNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1 || _la==KW_NOT) {
				{
				setState(224);
				negation();
				}
			}

			setState(227);
			shapeAtom();
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

	public static class NegationContext extends ParserRuleContext {
		public TerminalNode KW_NOT() { return getToken(ShExDocParser.KW_NOT, 0); }
		public NegationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNegation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegationContext negation() throws RecognitionException {
		NegationContext _localctx = new NegationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_negation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==KW_NOT) ) {
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

	public static class InlineShapeExpressionContext extends ParserRuleContext {
		public InlineShapeOrContext inlineShapeOr() {
			return getRuleContext(InlineShapeOrContext.class,0);
		}
		public InlineShapeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeExpressionContext inlineShapeExpression() throws RecognitionException {
		InlineShapeExpressionContext _localctx = new InlineShapeExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_inlineShapeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			inlineShapeOr();
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

	public static class InlineShapeOrContext extends ParserRuleContext {
		public List<InlineShapeAndContext> inlineShapeAnd() {
			return getRuleContexts(InlineShapeAndContext.class);
		}
		public InlineShapeAndContext inlineShapeAnd(int i) {
			return getRuleContext(InlineShapeAndContext.class,i);
		}
		public List<TerminalNode> KW_OR() { return getTokens(ShExDocParser.KW_OR); }
		public TerminalNode KW_OR(int i) {
			return getToken(ShExDocParser.KW_OR, i);
		}
		public InlineShapeOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeOr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeOrContext inlineShapeOr() throws RecognitionException {
		InlineShapeOrContext _localctx = new InlineShapeOrContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_inlineShapeOr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			inlineShapeAnd();
			setState(238);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_OR) {
				{
				{
				setState(234);
				match(KW_OR);
				setState(235);
				inlineShapeAnd();
				}
				}
				setState(240);
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

	public static class InlineShapeAndContext extends ParserRuleContext {
		public List<InlineShapeNotContext> inlineShapeNot() {
			return getRuleContexts(InlineShapeNotContext.class);
		}
		public InlineShapeNotContext inlineShapeNot(int i) {
			return getRuleContext(InlineShapeNotContext.class,i);
		}
		public List<TerminalNode> KW_AND() { return getTokens(ShExDocParser.KW_AND); }
		public TerminalNode KW_AND(int i) {
			return getToken(ShExDocParser.KW_AND, i);
		}
		public InlineShapeAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeAnd; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeAndContext inlineShapeAnd() throws RecognitionException {
		InlineShapeAndContext _localctx = new InlineShapeAndContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_inlineShapeAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			inlineShapeNot();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(242);
				match(KW_AND);
				setState(243);
				inlineShapeNot();
				}
				}
				setState(248);
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

	public static class InlineShapeNotContext extends ParserRuleContext {
		public InlineShapeAtomContext inlineShapeAtom() {
			return getRuleContext(InlineShapeAtomContext.class,0);
		}
		public NegationContext negation() {
			return getRuleContext(NegationContext.class,0);
		}
		public InlineShapeNotContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeNot; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeNot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeNotContext inlineShapeNot() throws RecognitionException {
		InlineShapeNotContext _localctx = new InlineShapeNotContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_inlineShapeNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1 || _la==KW_NOT) {
				{
				setState(249);
				negation();
				}
			}

			setState(252);
			inlineShapeAtom();
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

	public static class InlineShapeDefinitionContext extends ParserRuleContext {
		public List<QualifierContext> qualifier() {
			return getRuleContexts(QualifierContext.class);
		}
		public QualifierContext qualifier(int i) {
			return getRuleContext(QualifierContext.class,i);
		}
		public OneOfShapeContext oneOfShape() {
			return getRuleContext(OneOfShapeContext.class,0);
		}
		public InlineShapeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeDefinitionContext inlineShapeDefinition() throws RecognitionException {
		InlineShapeDefinitionContext _localctx = new InlineShapeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_inlineShapeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(254);
				qualifier();
				}
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(260);
			match(T__2);
			setState(262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__6) | (1L << T__7) | (1L << T__10) | (1L << T__15) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(261);
				oneOfShape();
				}
			}

			setState(264);
			match(T__3);
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

	public static class ShapeDefinitionContext extends ParserRuleContext {
		public SemanticActionsContext semanticActions() {
			return getRuleContext(SemanticActionsContext.class,0);
		}
		public List<QualifierContext> qualifier() {
			return getRuleContexts(QualifierContext.class);
		}
		public QualifierContext qualifier(int i) {
			return getRuleContext(QualifierContext.class,i);
		}
		public OneOfShapeContext oneOfShape() {
			return getRuleContext(OneOfShapeContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ShapeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeDefinitionContext shapeDefinition() throws RecognitionException {
		ShapeDefinitionContext _localctx = new ShapeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_shapeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(266);
				qualifier();
				}
				}
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(272);
			match(T__2);
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__6) | (1L << T__7) | (1L << T__10) | (1L << T__15) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(273);
				oneOfShape();
				}
			}

			setState(276);
			match(T__3);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(277);
				annotation();
				}
				}
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(283);
			semanticActions();
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

	public static class QualifierContext extends ParserRuleContext {
		public ExtensionContext extension() {
			return getRuleContext(ExtensionContext.class,0);
		}
		public ExtraPropertySetContext extraPropertySet() {
			return getRuleContext(ExtraPropertySetContext.class,0);
		}
		public TerminalNode KW_CLOSED() { return getToken(ShExDocParser.KW_CLOSED, 0); }
		public QualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitQualifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifierContext qualifier() throws RecognitionException {
		QualifierContext _localctx = new QualifierContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_qualifier);
		try {
			setState(288);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
			case KW_EXTENDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(285);
				extension();
				}
				break;
			case KW_EXTRA:
				enterOuterAlt(_localctx, 2);
				{
				setState(286);
				extraPropertySet();
				}
				break;
			case KW_CLOSED:
				enterOuterAlt(_localctx, 3);
				{
				setState(287);
				match(KW_CLOSED);
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

	public static class ExtraPropertySetContext extends ParserRuleContext {
		public TerminalNode KW_EXTRA() { return getToken(ShExDocParser.KW_EXTRA, 0); }
		public List<PredicateContext> predicate() {
			return getRuleContexts(PredicateContext.class);
		}
		public PredicateContext predicate(int i) {
			return getRuleContext(PredicateContext.class,i);
		}
		public ExtraPropertySetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extraPropertySet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitExtraPropertySet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExtraPropertySetContext extraPropertySet() throws RecognitionException {
		ExtraPropertySetContext _localctx = new ExtraPropertySetContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_extraPropertySet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(KW_EXTRA);
			setState(292); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(291);
				predicate();
				}
				}
				setState(294); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0) );
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

	public static class OneOfShapeContext extends ParserRuleContext {
		public GroupShapeContext groupShape() {
			return getRuleContext(GroupShapeContext.class,0);
		}
		public MultiElementOneOfContext multiElementOneOf() {
			return getRuleContext(MultiElementOneOfContext.class,0);
		}
		public OneOfShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oneOfShape; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitOneOfShape(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OneOfShapeContext oneOfShape() throws RecognitionException {
		OneOfShapeContext _localctx = new OneOfShapeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_oneOfShape);
		try {
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				groupShape();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				multiElementOneOf();
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

	public static class MultiElementOneOfContext extends ParserRuleContext {
		public List<GroupShapeContext> groupShape() {
			return getRuleContexts(GroupShapeContext.class);
		}
		public GroupShapeContext groupShape(int i) {
			return getRuleContext(GroupShapeContext.class,i);
		}
		public MultiElementOneOfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiElementOneOf; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitMultiElementOneOf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiElementOneOfContext multiElementOneOf() throws RecognitionException {
		MultiElementOneOfContext _localctx = new MultiElementOneOfContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_multiElementOneOf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			groupShape();
			setState(303); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(301);
				match(T__4);
				setState(302);
				groupShape();
				}
				}
				setState(305); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 );
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

	public static class GroupShapeContext extends ParserRuleContext {
		public SingleElementGroupContext singleElementGroup() {
			return getRuleContext(SingleElementGroupContext.class,0);
		}
		public MultiElementGroupContext multiElementGroup() {
			return getRuleContext(MultiElementGroupContext.class,0);
		}
		public GroupShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupShape; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitGroupShape(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupShapeContext groupShape() throws RecognitionException {
		GroupShapeContext _localctx = new GroupShapeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_groupShape);
		try {
			setState(309);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(307);
				singleElementGroup();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(308);
				multiElementGroup();
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

	public static class SingleElementGroupContext extends ParserRuleContext {
		public UnaryShapeContext unaryShape() {
			return getRuleContext(UnaryShapeContext.class,0);
		}
		public SingleElementGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleElementGroup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitSingleElementGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleElementGroupContext singleElementGroup() throws RecognitionException {
		SingleElementGroupContext _localctx = new SingleElementGroupContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_singleElementGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			unaryShape();
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(312);
				match(T__5);
				}
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

	public static class MultiElementGroupContext extends ParserRuleContext {
		public List<UnaryShapeContext> unaryShape() {
			return getRuleContexts(UnaryShapeContext.class);
		}
		public UnaryShapeContext unaryShape(int i) {
			return getRuleContext(UnaryShapeContext.class,i);
		}
		public MultiElementGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiElementGroup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitMultiElementGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiElementGroupContext multiElementGroup() throws RecognitionException {
		MultiElementGroupContext _localctx = new MultiElementGroupContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_multiElementGroup);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			unaryShape();
			setState(318); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(316);
					match(T__5);
					setState(317);
					unaryShape();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(320); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(323);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(322);
				match(T__5);
				}
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

	public static class UnaryShapeContext extends ParserRuleContext {
		public TripleConstraintContext tripleConstraint() {
			return getRuleContext(TripleConstraintContext.class,0);
		}
		public EncapsulatedShapeContext encapsulatedShape() {
			return getRuleContext(EncapsulatedShapeContext.class,0);
		}
		public TripleExprLabelContext tripleExprLabel() {
			return getRuleContext(TripleExprLabelContext.class,0);
		}
		public IncludeContext include() {
			return getRuleContext(IncludeContext.class,0);
		}
		public UnaryShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryShape; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitUnaryShape(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryShapeContext unaryShape() throws RecognitionException {
		UnaryShapeContext _localctx = new UnaryShapeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_unaryShape);
		int _la;
		try {
			setState(334);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__6:
			case T__7:
			case T__10:
			case RDF_TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(325);
					match(T__6);
					setState(326);
					tripleExprLabel();
					}
				}

				setState(331);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__1:
				case T__10:
				case RDF_TYPE:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
					{
					setState(329);
					tripleConstraint();
					}
					break;
				case T__7:
					{
					setState(330);
					encapsulatedShape();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(333);
				include();
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

	public static class EncapsulatedShapeContext extends ParserRuleContext {
		public OneOfShapeContext oneOfShape() {
			return getRuleContext(OneOfShapeContext.class,0);
		}
		public SemanticActionsContext semanticActions() {
			return getRuleContext(SemanticActionsContext.class,0);
		}
		public CardinalityContext cardinality() {
			return getRuleContext(CardinalityContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public EncapsulatedShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_encapsulatedShape; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitEncapsulatedShape(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EncapsulatedShapeContext encapsulatedShape() throws RecognitionException {
		EncapsulatedShapeContext _localctx = new EncapsulatedShapeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_encapsulatedShape);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(T__7);
			setState(337);
			oneOfShape();
			setState(338);
			match(T__8);
			setState(340);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__17) | (1L << T__18))) != 0) || _la==UNBOUNDED) {
				{
				setState(339);
				cardinality();
				}
			}

			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(342);
				annotation();
				}
				}
				setState(347);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(348);
			semanticActions();
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

	public static class ShapeAtomContext extends ParserRuleContext {
		public ShapeAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeAtom; }
	 
		public ShapeAtomContext() { }
		public void copyFrom(ShapeAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ShapeAtomNodeConstraintContext extends ShapeAtomContext {
		public NodeConstraintContext nodeConstraint() {
			return getRuleContext(NodeConstraintContext.class,0);
		}
		public ShapeOrRefContext shapeOrRef() {
			return getRuleContext(ShapeOrRefContext.class,0);
		}
		public ShapeAtomNodeConstraintContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShapeAtomShapeOrRefContext extends ShapeAtomContext {
		public ShapeOrRefContext shapeOrRef() {
			return getRuleContext(ShapeOrRefContext.class,0);
		}
		public ShapeAtomShapeOrRefContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomShapeOrRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShapeAtomShapeExpressionContext extends ShapeAtomContext {
		public ShapeExpressionContext shapeExpression() {
			return getRuleContext(ShapeExpressionContext.class,0);
		}
		public ShapeAtomShapeExpressionContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomShapeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShapeAtomAnyContext extends ShapeAtomContext {
		public ShapeAtomAnyContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomAny(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeAtomContext shapeAtom() throws RecognitionException {
		ShapeAtomContext _localctx = new ShapeAtomContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_shapeAtom);
		int _la;
		try {
			setState(360);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case KW_LITERAL:
			case KW_IRI:
			case KW_NONLITERAL:
			case KW_BNODE:
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case REGEXP:
				_localctx = new ShapeAtomNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(350);
				nodeConstraint();
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__15) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA) | (1L << ATPNAME_NS) | (1L << ATPNAME_LN))) != 0)) {
					{
					setState(351);
					shapeOrRef();
					}
				}

				}
				break;
			case T__2:
			case T__14:
			case T__15:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
			case ATPNAME_NS:
			case ATPNAME_LN:
				_localctx = new ShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				shapeOrRef();
				}
				break;
			case T__7:
				_localctx = new ShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(355);
				match(T__7);
				setState(356);
				shapeExpression();
				setState(357);
				match(T__8);
				}
				break;
			case T__9:
				_localctx = new ShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(359);
				match(T__9);
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

	public static class InlineShapeAtomContext extends ParserRuleContext {
		public InlineShapeAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeAtom; }
	 
		public InlineShapeAtomContext() { }
		public void copyFrom(InlineShapeAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class InlineShapeAtomNodeConstraintContext extends InlineShapeAtomContext {
		public NodeConstraintContext nodeConstraint() {
			return getRuleContext(NodeConstraintContext.class,0);
		}
		public InlineShapeOrRefContext inlineShapeOrRef() {
			return getRuleContext(InlineShapeOrRefContext.class,0);
		}
		public InlineShapeAtomNodeConstraintContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineShapeAtomShapeExpressionContext extends InlineShapeAtomContext {
		public ShapeExpressionContext shapeExpression() {
			return getRuleContext(ShapeExpressionContext.class,0);
		}
		public InlineShapeAtomShapeExpressionContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomShapeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineShapeAtomShapeOrRefContext extends InlineShapeAtomContext {
		public InlineShapeOrRefContext inlineShapeOrRef() {
			return getRuleContext(InlineShapeOrRefContext.class,0);
		}
		public NodeConstraintContext nodeConstraint() {
			return getRuleContext(NodeConstraintContext.class,0);
		}
		public InlineShapeAtomShapeOrRefContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomShapeOrRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineShapeAtomAnyContext extends InlineShapeAtomContext {
		public InlineShapeAtomAnyContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomAny(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeAtomContext inlineShapeAtom() throws RecognitionException {
		InlineShapeAtomContext _localctx = new InlineShapeAtomContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_inlineShapeAtom);
		int _la;
		try {
			setState(375);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case KW_LITERAL:
			case KW_IRI:
			case KW_NONLITERAL:
			case KW_BNODE:
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case REGEXP:
				_localctx = new InlineShapeAtomNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(362);
				nodeConstraint();
				setState(364);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(363);
					inlineShapeOrRef();
					}
					break;
				}
				}
				break;
			case T__2:
			case T__14:
			case T__15:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
			case ATPNAME_NS:
			case ATPNAME_LN:
				_localctx = new InlineShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				inlineShapeOrRef();
				setState(368);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << KW_LITERAL) | (1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE) | (1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << REGEXP))) != 0)) {
					{
					setState(367);
					nodeConstraint();
					}
				}

				}
				break;
			case T__7:
				_localctx = new InlineShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(370);
				match(T__7);
				setState(371);
				shapeExpression();
				setState(372);
				match(T__8);
				}
				break;
			case T__9:
				_localctx = new InlineShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(374);
				match(T__9);
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

	public static class NodeConstraintContext extends ParserRuleContext {
		public NodeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeConstraint; }
	 
		public NodeConstraintContext() { }
		public void copyFrom(NodeConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NodeConstraintLiteralContext extends NodeConstraintContext {
		public TerminalNode KW_LITERAL() { return getToken(ShExDocParser.KW_LITERAL, 0); }
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintLiteralContext(NodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintNonLiteralContext extends NodeConstraintContext {
		public NonLiteralKindContext nonLiteralKind() {
			return getRuleContext(NonLiteralKindContext.class,0);
		}
		public List<StringFacetContext> stringFacet() {
			return getRuleContexts(StringFacetContext.class);
		}
		public StringFacetContext stringFacet(int i) {
			return getRuleContext(StringFacetContext.class,i);
		}
		public NodeConstraintNonLiteralContext(NodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintNonLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintDatatypeContext extends NodeConstraintContext {
		public DatatypeContext datatype() {
			return getRuleContext(DatatypeContext.class,0);
		}
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintDatatypeContext(NodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintDatatype(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintFacetContext extends NodeConstraintContext {
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintFacetContext(NodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintFacet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintValueSetContext extends NodeConstraintContext {
		public ValueSetContext valueSet() {
			return getRuleContext(ValueSetContext.class,0);
		}
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintValueSetContext(NodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintValueSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeConstraintContext nodeConstraint() throws RecognitionException {
		NodeConstraintContext _localctx = new NodeConstraintContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_nodeConstraint);
		int _la;
		try {
			setState(410);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LITERAL:
				_localctx = new NodeConstraintLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(377);
				match(KW_LITERAL);
				setState(381);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(378);
					xsFacet();
					}
					}
					setState(383);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case KW_IRI:
			case KW_NONLITERAL:
			case KW_BNODE:
				_localctx = new NodeConstraintNonLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(384);
				nonLiteralKind();
				setState(388);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(385);
					stringFacet();
					}
					}
					setState(390);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				_localctx = new NodeConstraintDatatypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(391);
				datatype();
				setState(395);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(392);
					xsFacet();
					}
					}
					setState(397);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__11:
				_localctx = new NodeConstraintValueSetContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(398);
				valueSet();
				setState(402);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(399);
					xsFacet();
					}
					}
					setState(404);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
			case REGEXP:
				_localctx = new NodeConstraintFacetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(406); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(405);
					xsFacet();
					}
					}
					setState(408); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0) );
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

	public static class NonLiteralKindContext extends ParserRuleContext {
		public TerminalNode KW_IRI() { return getToken(ShExDocParser.KW_IRI, 0); }
		public TerminalNode KW_BNODE() { return getToken(ShExDocParser.KW_BNODE, 0); }
		public TerminalNode KW_NONLITERAL() { return getToken(ShExDocParser.KW_NONLITERAL, 0); }
		public NonLiteralKindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonLiteralKind; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNonLiteralKind(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonLiteralKindContext nonLiteralKind() throws RecognitionException {
		NonLiteralKindContext _localctx = new NonLiteralKindContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_nonLiteralKind);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE))) != 0)) ) {
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

	public static class XsFacetContext extends ParserRuleContext {
		public StringFacetContext stringFacet() {
			return getRuleContext(StringFacetContext.class,0);
		}
		public NumericFacetContext numericFacet() {
			return getRuleContext(NumericFacetContext.class,0);
		}
		public XsFacetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xsFacet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitXsFacet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XsFacetContext xsFacet() throws RecognitionException {
		XsFacetContext _localctx = new XsFacetContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_xsFacet);
		try {
			setState(416);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case REGEXP:
				enterOuterAlt(_localctx, 1);
				{
				setState(414);
				stringFacet();
				}
				break;
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				numericFacet();
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

	public static class StringFacetContext extends ParserRuleContext {
		public StringLengthContext stringLength() {
			return getRuleContext(StringLengthContext.class,0);
		}
		public TerminalNode INTEGER() { return getToken(ShExDocParser.INTEGER, 0); }
		public TerminalNode REGEXP() { return getToken(ShExDocParser.REGEXP, 0); }
		public TerminalNode REGEXP_FLAGS() { return getToken(ShExDocParser.REGEXP_FLAGS, 0); }
		public StringFacetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringFacet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStringFacet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringFacetContext stringFacet() throws RecognitionException {
		StringFacetContext _localctx = new StringFacetContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_stringFacet);
		int _la;
		try {
			setState(425);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
				enterOuterAlt(_localctx, 1);
				{
				setState(418);
				stringLength();
				setState(419);
				match(INTEGER);
				}
				break;
			case REGEXP:
				enterOuterAlt(_localctx, 2);
				{
				setState(421);
				match(REGEXP);
				setState(423);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==REGEXP_FLAGS) {
					{
					setState(422);
					match(REGEXP_FLAGS);
					}
				}

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

	public static class StringLengthContext extends ParserRuleContext {
		public TerminalNode KW_LENGTH() { return getToken(ShExDocParser.KW_LENGTH, 0); }
		public TerminalNode KW_MINLENGTH() { return getToken(ShExDocParser.KW_MINLENGTH, 0); }
		public TerminalNode KW_MAXLENGTH() { return getToken(ShExDocParser.KW_MAXLENGTH, 0); }
		public StringLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLength; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStringLength(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLengthContext stringLength() throws RecognitionException {
		StringLengthContext _localctx = new StringLengthContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_stringLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(427);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH))) != 0)) ) {
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

	public static class NumericFacetContext extends ParserRuleContext {
		public NumericRangeContext numericRange() {
			return getRuleContext(NumericRangeContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public NumericLengthContext numericLength() {
			return getRuleContext(NumericLengthContext.class,0);
		}
		public TerminalNode INTEGER() { return getToken(ShExDocParser.INTEGER, 0); }
		public NumericFacetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericFacet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNumericFacet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericFacetContext numericFacet() throws RecognitionException {
		NumericFacetContext _localctx = new NumericFacetContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_numericFacet);
		try {
			setState(435);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
				enterOuterAlt(_localctx, 1);
				{
				setState(429);
				numericRange();
				setState(430);
				numericLiteral();
				}
				break;
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
				enterOuterAlt(_localctx, 2);
				{
				setState(432);
				numericLength();
				setState(433);
				match(INTEGER);
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

	public static class NumericRangeContext extends ParserRuleContext {
		public TerminalNode KW_MININCLUSIVE() { return getToken(ShExDocParser.KW_MININCLUSIVE, 0); }
		public TerminalNode KW_MINEXCLUSIVE() { return getToken(ShExDocParser.KW_MINEXCLUSIVE, 0); }
		public TerminalNode KW_MAXINCLUSIVE() { return getToken(ShExDocParser.KW_MAXINCLUSIVE, 0); }
		public TerminalNode KW_MAXEXCLUSIVE() { return getToken(ShExDocParser.KW_MAXEXCLUSIVE, 0); }
		public NumericRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericRange; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNumericRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericRangeContext numericRange() throws RecognitionException {
		NumericRangeContext _localctx = new NumericRangeContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_numericRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(437);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE))) != 0)) ) {
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

	public static class NumericLengthContext extends ParserRuleContext {
		public TerminalNode KW_TOTALDIGITS() { return getToken(ShExDocParser.KW_TOTALDIGITS, 0); }
		public TerminalNode KW_FRACTIONDIGITS() { return getToken(ShExDocParser.KW_FRACTIONDIGITS, 0); }
		public NumericLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLength; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNumericLength(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLengthContext numericLength() throws RecognitionException {
		NumericLengthContext _localctx = new NumericLengthContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_numericLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(439);
			_la = _input.LA(1);
			if ( !(_la==KW_TOTALDIGITS || _la==KW_FRACTIONDIGITS) ) {
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

	public static class TripleConstraintContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public InlineShapeExpressionContext inlineShapeExpression() {
			return getRuleContext(InlineShapeExpressionContext.class,0);
		}
		public SemanticActionsContext semanticActions() {
			return getRuleContext(SemanticActionsContext.class,0);
		}
		public SenseFlagsContext senseFlags() {
			return getRuleContext(SenseFlagsContext.class,0);
		}
		public CardinalityContext cardinality() {
			return getRuleContext(CardinalityContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TripleConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tripleConstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitTripleConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TripleConstraintContext tripleConstraint() throws RecognitionException {
		TripleConstraintContext _localctx = new TripleConstraintContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_tripleConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(442);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1 || _la==T__10) {
				{
				setState(441);
				senseFlags();
				}
			}

			setState(444);
			predicate();
			setState(445);
			inlineShapeExpression();
			setState(447);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__17) | (1L << T__18))) != 0) || _la==UNBOUNDED) {
				{
				setState(446);
				cardinality();
				}
			}

			setState(452);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(449);
				annotation();
				}
				}
				setState(454);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(455);
			semanticActions();
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

	public static class SenseFlagsContext extends ParserRuleContext {
		public SenseFlagsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_senseFlags; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitSenseFlags(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SenseFlagsContext senseFlags() throws RecognitionException {
		SenseFlagsContext _localctx = new SenseFlagsContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_senseFlags);
		int _la;
		try {
			setState(465);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(457);
				match(T__1);
				setState(459);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(458);
					match(T__10);
					}
				}

				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(461);
				match(T__10);
				setState(463);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(462);
					match(T__1);
					}
				}

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

	public static class ValueSetContext extends ParserRuleContext {
		public List<ValueSetValueContext> valueSetValue() {
			return getRuleContexts(ValueSetValueContext.class);
		}
		public ValueSetValueContext valueSetValue(int i) {
			return getRuleContext(ValueSetValueContext.class,i);
		}
		public ValueSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueSet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitValueSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueSetContext valueSet() throws RecognitionException {
		ValueSetContext _localctx = new ValueSetContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_valueSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(467);
			match(T__11);
			setState(471);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 10)) & ~0x3f) == 0 && ((1L << (_la - 10)) & ((1L << (T__9 - 10)) | (1L << (T__14 - 10)) | (1L << (KW_TRUE - 10)) | (1L << (KW_FALSE - 10)) | (1L << (IRIREF - 10)) | (1L << (PNAME_NS - 10)) | (1L << (PNAME_LN - 10)) | (1L << (LANGTAG - 10)) | (1L << (INTEGER - 10)) | (1L << (DECIMAL - 10)) | (1L << (DOUBLE - 10)) | (1L << (STRING_LITERAL1 - 10)) | (1L << (STRING_LITERAL2 - 10)) | (1L << (STRING_LITERAL_LONG1 - 10)) | (1L << (STRING_LITERAL_LONG2 - 10)))) != 0)) {
				{
				{
				setState(468);
				valueSetValue();
				}
				}
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(474);
			match(T__12);
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

	public static class ValueSetValueContext extends ParserRuleContext {
		public IriRangeContext iriRange() {
			return getRuleContext(IriRangeContext.class,0);
		}
		public LiteralRangeContext literalRange() {
			return getRuleContext(LiteralRangeContext.class,0);
		}
		public LanguageRangeContext languageRange() {
			return getRuleContext(LanguageRangeContext.class,0);
		}
		public List<IriExclusionContext> iriExclusion() {
			return getRuleContexts(IriExclusionContext.class);
		}
		public IriExclusionContext iriExclusion(int i) {
			return getRuleContext(IriExclusionContext.class,i);
		}
		public List<LiteralExclusionContext> literalExclusion() {
			return getRuleContexts(LiteralExclusionContext.class);
		}
		public LiteralExclusionContext literalExclusion(int i) {
			return getRuleContext(LiteralExclusionContext.class,i);
		}
		public List<LanguageExclusionContext> languageExclusion() {
			return getRuleContexts(LanguageExclusionContext.class);
		}
		public LanguageExclusionContext languageExclusion(int i) {
			return getRuleContext(LanguageExclusionContext.class,i);
		}
		public ValueSetValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueSetValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitValueSetValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueSetValueContext valueSetValue() throws RecognitionException {
		ValueSetValueContext _localctx = new ValueSetValueContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_valueSetValue);
		int _la;
		try {
			setState(497);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(476);
				iriRange();
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
				setState(477);
				literalRange();
				}
				break;
			case T__14:
			case LANGTAG:
				enterOuterAlt(_localctx, 3);
				{
				setState(478);
				languageRange();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 4);
				{
				setState(479);
				match(T__9);
				setState(495);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
				case 1:
					{
					setState(481); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(480);
						iriExclusion();
						}
						}
						setState(483); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__13 );
					}
					break;
				case 2:
					{
					setState(486); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(485);
						literalExclusion();
						}
						}
						setState(488); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__13 );
					}
					break;
				case 3:
					{
					setState(491); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(490);
						languageExclusion();
						}
						}
						setState(493); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__13 );
					}
					break;
				}
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

	public static class IriRangeContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public List<IriExclusionContext> iriExclusion() {
			return getRuleContexts(IriExclusionContext.class);
		}
		public IriExclusionContext iriExclusion(int i) {
			return getRuleContext(IriExclusionContext.class,i);
		}
		public IriRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iriRange; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitIriRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriRangeContext iriRange() throws RecognitionException {
		IriRangeContext _localctx = new IriRangeContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_iriRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			iri();
			setState(507);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(500);
				match(STEM_MARK);
				setState(504);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(501);
					iriExclusion();
					}
					}
					setState(506);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
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

	public static class IriExclusionContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public IriExclusionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iriExclusion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitIriExclusion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriExclusionContext iriExclusion() throws RecognitionException {
		IriExclusionContext _localctx = new IriExclusionContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_iriExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(509);
			match(T__13);
			setState(510);
			iri();
			setState(512);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(511);
				match(STEM_MARK);
				}
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

	public static class LiteralRangeContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public List<LiteralExclusionContext> literalExclusion() {
			return getRuleContexts(LiteralExclusionContext.class);
		}
		public LiteralExclusionContext literalExclusion(int i) {
			return getRuleContext(LiteralExclusionContext.class,i);
		}
		public LiteralRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalRange; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLiteralRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralRangeContext literalRange() throws RecognitionException {
		LiteralRangeContext _localctx = new LiteralRangeContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_literalRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(514);
			literal();
			setState(522);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(515);
				match(STEM_MARK);
				setState(519);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(516);
					literalExclusion();
					}
					}
					setState(521);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
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

	public static class LiteralExclusionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public LiteralExclusionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalExclusion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLiteralExclusion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralExclusionContext literalExclusion() throws RecognitionException {
		LiteralExclusionContext _localctx = new LiteralExclusionContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_literalExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(524);
			match(T__13);
			setState(525);
			literal();
			setState(527);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(526);
				match(STEM_MARK);
				}
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

	public static class LanguageRangeContext extends ParserRuleContext {
		public TerminalNode LANGTAG() { return getToken(ShExDocParser.LANGTAG, 0); }
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public List<LanguageExclusionContext> languageExclusion() {
			return getRuleContexts(LanguageExclusionContext.class);
		}
		public LanguageExclusionContext languageExclusion(int i) {
			return getRuleContext(LanguageExclusionContext.class,i);
		}
		public LanguageRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_languageRange; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLanguageRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LanguageRangeContext languageRange() throws RecognitionException {
		LanguageRangeContext _localctx = new LanguageRangeContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_languageRange);
		int _la;
		try {
			setState(547);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LANGTAG:
				enterOuterAlt(_localctx, 1);
				{
				setState(529);
				match(LANGTAG);
				setState(537);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STEM_MARK) {
					{
					setState(530);
					match(STEM_MARK);
					setState(534);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__13) {
						{
						{
						setState(531);
						languageExclusion();
						}
						}
						setState(536);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(539);
				match(T__14);
				setState(540);
				match(STEM_MARK);
				setState(544);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(541);
					languageExclusion();
					}
					}
					setState(546);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public static class LanguageExclusionContext extends ParserRuleContext {
		public TerminalNode LANGTAG() { return getToken(ShExDocParser.LANGTAG, 0); }
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public LanguageExclusionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_languageExclusion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLanguageExclusion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LanguageExclusionContext languageExclusion() throws RecognitionException {
		LanguageExclusionContext _localctx = new LanguageExclusionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_languageExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(549);
			match(T__13);
			setState(550);
			match(LANGTAG);
			setState(552);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(551);
				match(STEM_MARK);
				}
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

	public static class LiteralContext extends ParserRuleContext {
		public RdfLiteralContext rdfLiteral() {
			return getRuleContext(RdfLiteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
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
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_literal);
		try {
			setState(557);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 1);
				{
				setState(554);
				rdfLiteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(555);
				numericLiteral();
				}
				break;
			case KW_TRUE:
			case KW_FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(556);
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

	public static class ShapeOrRefContext extends ParserRuleContext {
		public ShapeDefinitionContext shapeDefinition() {
			return getRuleContext(ShapeDefinitionContext.class,0);
		}
		public ShapeRefContext shapeRef() {
			return getRuleContext(ShapeRefContext.class,0);
		}
		public ShapeOrRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeOrRef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeOrRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeOrRefContext shapeOrRef() throws RecognitionException {
		ShapeOrRefContext _localctx = new ShapeOrRefContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_shapeOrRef);
		try {
			setState(561);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__15:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(559);
				shapeDefinition();
				}
				break;
			case T__14:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(560);
				shapeRef();
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

	public static class InlineShapeOrRefContext extends ParserRuleContext {
		public InlineShapeDefinitionContext inlineShapeDefinition() {
			return getRuleContext(InlineShapeDefinitionContext.class,0);
		}
		public ShapeRefContext shapeRef() {
			return getRuleContext(ShapeRefContext.class,0);
		}
		public InlineShapeOrRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineShapeOrRef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeOrRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeOrRefContext inlineShapeOrRef() throws RecognitionException {
		InlineShapeOrRefContext _localctx = new InlineShapeOrRefContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_inlineShapeOrRef);
		try {
			setState(565);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__15:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(563);
				inlineShapeDefinition();
				}
				break;
			case T__14:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(564);
				shapeRef();
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

	public static class ShapeRefContext extends ParserRuleContext {
		public TerminalNode ATPNAME_LN() { return getToken(ShExDocParser.ATPNAME_LN, 0); }
		public TerminalNode ATPNAME_NS() { return getToken(ShExDocParser.ATPNAME_NS, 0); }
		public ShapeExprLabelContext shapeExprLabel() {
			return getRuleContext(ShapeExprLabelContext.class,0);
		}
		public ShapeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeRef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeRefContext shapeRef() throws RecognitionException {
		ShapeRefContext _localctx = new ShapeRefContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_shapeRef);
		try {
			setState(571);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(567);
				match(ATPNAME_LN);
				}
				break;
			case ATPNAME_NS:
				enterOuterAlt(_localctx, 2);
				{
				setState(568);
				match(ATPNAME_NS);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(569);
				match(T__14);
				setState(570);
				shapeExprLabel();
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

	public static class IncludeContext extends ParserRuleContext {
		public TripleExprLabelContext tripleExprLabel() {
			return getRuleContext(TripleExprLabelContext.class,0);
		}
		public IncludeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_include; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInclude(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncludeContext include() throws RecognitionException {
		IncludeContext _localctx = new IncludeContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_include);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			match(T__15);
			setState(574);
			tripleExprLabel();
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

	public static class SemanticActionsContext extends ParserRuleContext {
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
		}
		public SemanticActionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semanticActions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitSemanticActions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SemanticActionsContext semanticActions() throws RecognitionException {
		SemanticActionsContext _localctx = new SemanticActionsContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_semanticActions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(579);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(576);
				semanticAction();
				}
				}
				setState(581);
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

	public static class AnnotationContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_annotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			match(T__16);
			setState(583);
			predicate();
			setState(586);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				{
				setState(584);
				iri();
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
				{
				setState(585);
				literal();
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
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_predicate);
		try {
			setState(590);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(588);
				iri();
				}
				break;
			case RDF_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(589);
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
		public TerminalNode RDF_TYPE() { return getToken(ShExDocParser.RDF_TYPE, 0); }
		public RdfTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRdfType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfTypeContext rdfType() throws RecognitionException {
		RdfTypeContext _localctx = new RdfTypeContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_rdfType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(592);
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

	public static class DatatypeContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public DatatypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_datatype; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitDatatype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatatypeContext datatype() throws RecognitionException {
		DatatypeContext _localctx = new DatatypeContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_datatype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			iri();
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

	public static class CardinalityContext extends ParserRuleContext {
		public CardinalityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cardinality; }
	 
		public CardinalityContext() { }
		public void copyFrom(CardinalityContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StarCardinalityContext extends CardinalityContext {
		public StarCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitStarCardinality(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RepeatCardinalityContext extends CardinalityContext {
		public RepeatRangeContext repeatRange() {
			return getRuleContext(RepeatRangeContext.class,0);
		}
		public RepeatCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRepeatCardinality(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusCardinalityContext extends CardinalityContext {
		public PlusCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitPlusCardinality(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OptionalCardinalityContext extends CardinalityContext {
		public OptionalCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitOptionalCardinality(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CardinalityContext cardinality() throws RecognitionException {
		CardinalityContext _localctx = new CardinalityContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_cardinality);
		try {
			setState(600);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNBOUNDED:
				_localctx = new StarCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(596);
				match(UNBOUNDED);
				}
				break;
			case T__17:
				_localctx = new PlusCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(597);
				match(T__17);
				}
				break;
			case T__18:
				_localctx = new OptionalCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(598);
				match(T__18);
				}
				break;
			case T__2:
				_localctx = new RepeatCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(599);
				repeatRange();
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

	public static class RepeatRangeContext extends ParserRuleContext {
		public RepeatRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repeatRange; }
	 
		public RepeatRangeContext() { }
		public void copyFrom(RepeatRangeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExactRangeContext extends RepeatRangeContext {
		public TerminalNode INTEGER() { return getToken(ShExDocParser.INTEGER, 0); }
		public ExactRangeContext(RepeatRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitExactRange(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinMaxRangeContext extends RepeatRangeContext {
		public List<TerminalNode> INTEGER() { return getTokens(ShExDocParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(ShExDocParser.INTEGER, i);
		}
		public TerminalNode UNBOUNDED() { return getToken(ShExDocParser.UNBOUNDED, 0); }
		public MinMaxRangeContext(RepeatRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitMinMaxRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RepeatRangeContext repeatRange() throws RecognitionException {
		RepeatRangeContext _localctx = new RepeatRangeContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_repeatRange);
		int _la;
		try {
			setState(612);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				_localctx = new ExactRangeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(602);
				match(T__2);
				setState(603);
				match(INTEGER);
				setState(604);
				match(T__3);
				}
				break;
			case 2:
				_localctx = new MinMaxRangeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(605);
				match(T__2);
				setState(606);
				match(INTEGER);
				setState(607);
				match(T__19);
				setState(609);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INTEGER || _la==UNBOUNDED) {
					{
					setState(608);
					_la = _input.LA(1);
					if ( !(_la==INTEGER || _la==UNBOUNDED) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(611);
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

	public static class ShapeExprLabelContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public ShapeExprLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeExprLabel; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeExprLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeExprLabelContext shapeExprLabel() throws RecognitionException {
		ShapeExprLabelContext _localctx = new ShapeExprLabelContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_shapeExprLabel);
		try {
			setState(616);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(614);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(615);
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

	public static class TripleExprLabelContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public BlankNodeContext blankNode() {
			return getRuleContext(BlankNodeContext.class,0);
		}
		public TripleExprLabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tripleExprLabel; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitTripleExprLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TripleExprLabelContext tripleExprLabel() throws RecognitionException {
		TripleExprLabelContext _localctx = new TripleExprLabelContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_tripleExprLabel);
		try {
			setState(620);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(618);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(619);
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

	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ShExDocParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(ShExDocParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(ShExDocParser.DOUBLE, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_numericLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(622);
			_la = _input.LA(1);
			if ( !(((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (INTEGER - 64)) | (1L << (DECIMAL - 64)) | (1L << (DOUBLE - 64)))) != 0)) ) {
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
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode LANGTAG() { return getToken(ShExDocParser.LANGTAG, 0); }
		public DatatypeContext datatype() {
			return getRuleContext(DatatypeContext.class,0);
		}
		public RdfLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRdfLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfLiteralContext rdfLiteral() throws RecognitionException {
		RdfLiteralContext _localctx = new RdfLiteralContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_rdfLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(624);
			string();
			setState(628);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				{
				setState(625);
				match(LANGTAG);
				}
				break;
			case 2:
				{
				setState(626);
				match(T__20);
				setState(627);
				datatype();
				}
				break;
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

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode KW_TRUE() { return getToken(ShExDocParser.KW_TRUE, 0); }
		public TerminalNode KW_FALSE() { return getToken(ShExDocParser.KW_FALSE, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(630);
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
		public TerminalNode STRING_LITERAL_LONG1() { return getToken(ShExDocParser.STRING_LITERAL_LONG1, 0); }
		public TerminalNode STRING_LITERAL_LONG2() { return getToken(ShExDocParser.STRING_LITERAL_LONG2, 0); }
		public TerminalNode STRING_LITERAL1() { return getToken(ShExDocParser.STRING_LITERAL1, 0); }
		public TerminalNode STRING_LITERAL2() { return getToken(ShExDocParser.STRING_LITERAL2, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(632);
			_la = _input.LA(1);
			if ( !(((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (STRING_LITERAL1 - 69)) | (1L << (STRING_LITERAL2 - 69)) | (1L << (STRING_LITERAL_LONG1 - 69)) | (1L << (STRING_LITERAL_LONG2 - 69)))) != 0)) ) {
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

	public static class IriContext extends ParserRuleContext {
		public TerminalNode IRIREF() { return getToken(ShExDocParser.IRIREF, 0); }
		public PrefixedNameContext prefixedName() {
			return getRuleContext(PrefixedNameContext.class,0);
		}
		public IriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iri; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitIri(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IriContext iri() throws RecognitionException {
		IriContext _localctx = new IriContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_iri);
		try {
			setState(636);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(634);
				match(IRIREF);
				}
				break;
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(635);
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
		public TerminalNode PNAME_LN() { return getToken(ShExDocParser.PNAME_LN, 0); }
		public TerminalNode PNAME_NS() { return getToken(ShExDocParser.PNAME_NS, 0); }
		public PrefixedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitPrefixedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixedNameContext prefixedName() throws RecognitionException {
		PrefixedNameContext _localctx = new PrefixedNameContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(638);
			_la = _input.LA(1);
			if ( !(_la==PNAME_NS || _la==PNAME_LN) ) {
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
		public TerminalNode BLANK_NODE_LABEL() { return getToken(ShExDocParser.BLANK_NODE_LABEL, 0); }
		public BlankNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankNode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitBlankNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankNodeContext blankNode() throws RecognitionException {
		BlankNodeContext _localctx = new BlankNodeContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(640);
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

	public static class ExtensionContext extends ParserRuleContext {
		public TerminalNode KW_EXTENDS() { return getToken(ShExDocParser.KW_EXTENDS, 0); }
		public ShapeExprLabelContext shapeExprLabel() {
			return getRuleContext(ShapeExprLabelContext.class,0);
		}
		public ExtensionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitExtension(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExtensionContext extension() throws RecognitionException {
		ExtensionContext _localctx = new ExtensionContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_extension);
		try {
			setState(647);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_EXTENDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(642);
				match(KW_EXTENDS);
				setState(643);
				match(T__14);
				setState(644);
				shapeExprLabel();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(645);
				match(T__15);
				setState(646);
				shapeExprLabel();
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

	public static class SemanticActionContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode CODE() { return getToken(ShExDocParser.CODE, 0); }
		public SemanticActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semanticAction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitSemanticAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SemanticActionContext semanticAction() throws RecognitionException {
		SemanticActionContext _localctx = new SemanticActionContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_semanticAction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(649);
			match(T__21);
			setState(650);
			iri();
			setState(651);
			_la = _input.LA(1);
			if ( !(_la==T__21 || _la==CODE) ) {
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3J\u0290\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\3\2\7\2\u0096\n\2\f\2\16\2\u0099\13\2\3\2\3\2\5\2\u009d\n\2"+
		"\3\2\7\2\u00a0\n\2\f\2\16\2\u00a3\13\2\5\2\u00a5\n\2\3\2\3\2\3\3\3\3\3"+
		"\3\5\3\u00ac\n\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\5\7\u00ba"+
		"\n\7\3\b\3\b\3\b\3\b\3\t\6\t\u00c1\n\t\r\t\16\t\u00c2\3\n\3\n\5\n\u00c7"+
		"\n\n\3\13\5\13\u00ca\n\13\3\13\3\13\3\13\5\13\u00cf\n\13\3\f\3\f\3\r\3"+
		"\r\3\r\7\r\u00d6\n\r\f\r\16\r\u00d9\13\r\3\16\3\16\3\16\7\16\u00de\n\16"+
		"\f\16\16\16\u00e1\13\16\3\17\5\17\u00e4\n\17\3\17\3\17\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\22\7\22\u00ef\n\22\f\22\16\22\u00f2\13\22\3\23\3\23"+
		"\3\23\7\23\u00f7\n\23\f\23\16\23\u00fa\13\23\3\24\5\24\u00fd\n\24\3\24"+
		"\3\24\3\25\7\25\u0102\n\25\f\25\16\25\u0105\13\25\3\25\3\25\5\25\u0109"+
		"\n\25\3\25\3\25\3\26\7\26\u010e\n\26\f\26\16\26\u0111\13\26\3\26\3\26"+
		"\5\26\u0115\n\26\3\26\3\26\7\26\u0119\n\26\f\26\16\26\u011c\13\26\3\26"+
		"\3\26\3\27\3\27\3\27\5\27\u0123\n\27\3\30\3\30\6\30\u0127\n\30\r\30\16"+
		"\30\u0128\3\31\3\31\5\31\u012d\n\31\3\32\3\32\3\32\6\32\u0132\n\32\r\32"+
		"\16\32\u0133\3\33\3\33\5\33\u0138\n\33\3\34\3\34\5\34\u013c\n\34\3\35"+
		"\3\35\3\35\6\35\u0141\n\35\r\35\16\35\u0142\3\35\5\35\u0146\n\35\3\36"+
		"\3\36\5\36\u014a\n\36\3\36\3\36\5\36\u014e\n\36\3\36\5\36\u0151\n\36\3"+
		"\37\3\37\3\37\3\37\5\37\u0157\n\37\3\37\7\37\u015a\n\37\f\37\16\37\u015d"+
		"\13\37\3\37\3\37\3 \3 \5 \u0163\n \3 \3 \3 \3 \3 \3 \5 \u016b\n \3!\3"+
		"!\5!\u016f\n!\3!\3!\5!\u0173\n!\3!\3!\3!\3!\3!\5!\u017a\n!\3\"\3\"\7\""+
		"\u017e\n\"\f\"\16\"\u0181\13\"\3\"\3\"\7\"\u0185\n\"\f\"\16\"\u0188\13"+
		"\"\3\"\3\"\7\"\u018c\n\"\f\"\16\"\u018f\13\"\3\"\3\"\7\"\u0193\n\"\f\""+
		"\16\"\u0196\13\"\3\"\6\"\u0199\n\"\r\"\16\"\u019a\5\"\u019d\n\"\3#\3#"+
		"\3$\3$\5$\u01a3\n$\3%\3%\3%\3%\3%\5%\u01aa\n%\5%\u01ac\n%\3&\3&\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\5\'\u01b6\n\'\3(\3(\3)\3)\3*\5*\u01bd\n*\3*\3*\3*\5"+
		"*\u01c2\n*\3*\7*\u01c5\n*\f*\16*\u01c8\13*\3*\3*\3+\3+\5+\u01ce\n+\3+"+
		"\3+\5+\u01d2\n+\5+\u01d4\n+\3,\3,\7,\u01d8\n,\f,\16,\u01db\13,\3,\3,\3"+
		"-\3-\3-\3-\3-\6-\u01e4\n-\r-\16-\u01e5\3-\6-\u01e9\n-\r-\16-\u01ea\3-"+
		"\6-\u01ee\n-\r-\16-\u01ef\5-\u01f2\n-\5-\u01f4\n-\3.\3.\3.\7.\u01f9\n"+
		".\f.\16.\u01fc\13.\5.\u01fe\n.\3/\3/\3/\5/\u0203\n/\3\60\3\60\3\60\7\60"+
		"\u0208\n\60\f\60\16\60\u020b\13\60\5\60\u020d\n\60\3\61\3\61\3\61\5\61"+
		"\u0212\n\61\3\62\3\62\3\62\7\62\u0217\n\62\f\62\16\62\u021a\13\62\5\62"+
		"\u021c\n\62\3\62\3\62\3\62\7\62\u0221\n\62\f\62\16\62\u0224\13\62\5\62"+
		"\u0226\n\62\3\63\3\63\3\63\5\63\u022b\n\63\3\64\3\64\3\64\5\64\u0230\n"+
		"\64\3\65\3\65\5\65\u0234\n\65\3\66\3\66\5\66\u0238\n\66\3\67\3\67\3\67"+
		"\3\67\5\67\u023e\n\67\38\38\38\39\79\u0244\n9\f9\169\u0247\139\3:\3:\3"+
		":\3:\5:\u024d\n:\3;\3;\5;\u0251\n;\3<\3<\3=\3=\3>\3>\3>\3>\5>\u025b\n"+
		">\3?\3?\3?\3?\3?\3?\3?\5?\u0264\n?\3?\5?\u0267\n?\3@\3@\5@\u026b\n@\3"+
		"A\3A\5A\u026f\nA\3B\3B\3C\3C\3C\3C\5C\u0277\nC\3D\3D\3E\3E\3F\3F\5F\u027f"+
		"\nF\3G\3G\3H\3H\3I\3I\3I\3I\3I\5I\u028a\nI\3J\3J\3J\3J\3J\2\2K\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX"+
		"Z\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090"+
		"\u0092\2\r\4\2\4\4\62\62\3\2$&\3\2-/\3\2),\3\2\60\61\4\2BBFF\3\2BD\3\2"+
		"\63\64\3\2GJ\3\2:;\4\2\30\30\67\67\2\u02ae\2\u0097\3\2\2\2\4\u00ab\3\2"+
		"\2\2\6\u00ad\3\2\2\2\b\u00b0\3\2\2\2\n\u00b4\3\2\2\2\f\u00b9\3\2\2\2\16"+
		"\u00bb\3\2\2\2\20\u00c0\3\2\2\2\22\u00c6\3\2\2\2\24\u00c9\3\2\2\2\26\u00d0"+
		"\3\2\2\2\30\u00d2\3\2\2\2\32\u00da\3\2\2\2\34\u00e3\3\2\2\2\36\u00e7\3"+
		"\2\2\2 \u00e9\3\2\2\2\"\u00eb\3\2\2\2$\u00f3\3\2\2\2&\u00fc\3\2\2\2(\u0103"+
		"\3\2\2\2*\u010f\3\2\2\2,\u0122\3\2\2\2.\u0124\3\2\2\2\60\u012c\3\2\2\2"+
		"\62\u012e\3\2\2\2\64\u0137\3\2\2\2\66\u0139\3\2\2\28\u013d\3\2\2\2:\u0150"+
		"\3\2\2\2<\u0152\3\2\2\2>\u016a\3\2\2\2@\u0179\3\2\2\2B\u019c\3\2\2\2D"+
		"\u019e\3\2\2\2F\u01a2\3\2\2\2H\u01ab\3\2\2\2J\u01ad\3\2\2\2L\u01b5\3\2"+
		"\2\2N\u01b7\3\2\2\2P\u01b9\3\2\2\2R\u01bc\3\2\2\2T\u01d3\3\2\2\2V\u01d5"+
		"\3\2\2\2X\u01f3\3\2\2\2Z\u01f5\3\2\2\2\\\u01ff\3\2\2\2^\u0204\3\2\2\2"+
		"`\u020e\3\2\2\2b\u0225\3\2\2\2d\u0227\3\2\2\2f\u022f\3\2\2\2h\u0233\3"+
		"\2\2\2j\u0237\3\2\2\2l\u023d\3\2\2\2n\u023f\3\2\2\2p\u0245\3\2\2\2r\u0248"+
		"\3\2\2\2t\u0250\3\2\2\2v\u0252\3\2\2\2x\u0254\3\2\2\2z\u025a\3\2\2\2|"+
		"\u0266\3\2\2\2~\u026a\3\2\2\2\u0080\u026e\3\2\2\2\u0082\u0270\3\2\2\2"+
		"\u0084\u0272\3\2\2\2\u0086\u0278\3\2\2\2\u0088\u027a\3\2\2\2\u008a\u027e"+
		"\3\2\2\2\u008c\u0280\3\2\2\2\u008e\u0282\3\2\2\2\u0090\u0289\3\2\2\2\u0092"+
		"\u028b\3\2\2\2\u0094\u0096\5\4\3\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2"+
		"\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u00a4\3\2\2\2\u0099"+
		"\u0097\3\2\2\2\u009a\u009d\5\f\7\2\u009b\u009d\5\20\t\2\u009c\u009a\3"+
		"\2\2\2\u009c\u009b\3\2\2\2\u009d\u00a1\3\2\2\2\u009e\u00a0\5\22\n\2\u009f"+
		"\u009e\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u009c\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\7\2\2\3\u00a7\3\3\2\2\2"+
		"\u00a8\u00ac\5\6\4\2\u00a9\u00ac\5\b\5\2\u00aa\u00ac\5\n\6\2\u00ab\u00a8"+
		"\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\5\3\2\2\2\u00ad"+
		"\u00ae\7\32\2\2\u00ae\u00af\79\2\2\u00af\7\3\2\2\2\u00b0\u00b1\7\36\2"+
		"\2\u00b1\u00b2\7:\2\2\u00b2\u00b3\79\2\2\u00b3\t\3\2\2\2\u00b4\u00b5\7"+
		"\35\2\2\u00b5\u00b6\79\2\2\u00b6\13\3\2\2\2\u00b7\u00ba\5\16\b\2\u00b8"+
		"\u00ba\5\24\13\2\u00b9\u00b7\3\2\2\2\u00b9\u00b8\3\2\2\2\u00ba\r\3\2\2"+
		"\2\u00bb\u00bc\7\37\2\2\u00bc\u00bd\7\3\2\2\u00bd\u00be\5\26\f\2\u00be"+
		"\17\3\2\2\2\u00bf\u00c1\5\u0092J\2\u00c0\u00bf\3\2\2\2\u00c1\u00c2\3\2"+
		"\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\21\3\2\2\2\u00c4\u00c7"+
		"\5\4\3\2\u00c5\u00c7\5\f\7\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7"+
		"\23\3\2\2\2\u00c8\u00ca\7\31\2\2\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3\2\2"+
		"\2\u00ca\u00cb\3\2\2\2\u00cb\u00ce\5~@\2\u00cc\u00cf\5\26\f\2\u00cd\u00cf"+
		"\7\33\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\25\3\2\2\2\u00d0"+
		"\u00d1\5\30\r\2\u00d1\27\3\2\2\2\u00d2\u00d7\5\32\16\2\u00d3\u00d4\7("+
		"\2\2\u00d4\u00d6\5\32\16\2\u00d5\u00d3\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7"+
		"\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\31\3\2\2\2\u00d9\u00d7\3\2\2"+
		"\2\u00da\u00df\5\34\17\2\u00db\u00dc\7\'\2\2\u00dc\u00de\5\34\17\2\u00dd"+
		"\u00db\3\2\2\2\u00de\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2"+
		"\2\2\u00e0\33\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e4\5\36\20\2\u00e3"+
		"\u00e2\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\5>"+
		" \2\u00e6\35\3\2\2\2\u00e7\u00e8\t\2\2\2\u00e8\37\3\2\2\2\u00e9\u00ea"+
		"\5\"\22\2\u00ea!\3\2\2\2\u00eb\u00f0\5$\23\2\u00ec\u00ed\7(\2\2\u00ed"+
		"\u00ef\5$\23\2\u00ee\u00ec\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee\3\2"+
		"\2\2\u00f0\u00f1\3\2\2\2\u00f1#\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u00f8"+
		"\5&\24\2\u00f4\u00f5\7\'\2\2\u00f5\u00f7\5&\24\2\u00f6\u00f4\3\2\2\2\u00f7"+
		"\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9%\3\2\2\2"+
		"\u00fa\u00f8\3\2\2\2\u00fb\u00fd\5\36\20\2\u00fc\u00fb\3\2\2\2\u00fc\u00fd"+
		"\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\5@!\2\u00ff\'\3\2\2\2\u0100\u0102"+
		"\5,\27\2\u0101\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103"+
		"\u0104\3\2\2\2\u0104\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0108\7\5"+
		"\2\2\u0107\u0109\5\60\31\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109"+
		"\u010a\3\2\2\2\u010a\u010b\7\6\2\2\u010b)\3\2\2\2\u010c\u010e\5,\27\2"+
		"\u010d\u010c\3\2\2\2\u010e\u0111\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110"+
		"\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u010f\3\2\2\2\u0112\u0114\7\5\2\2\u0113"+
		"\u0115\5\60\31\2\u0114\u0113\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\3"+
		"\2\2\2\u0116\u011a\7\6\2\2\u0117\u0119\5r:\2\u0118\u0117\3\2\2\2\u0119"+
		"\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011d\3\2"+
		"\2\2\u011c\u011a\3\2\2\2\u011d\u011e\5p9\2\u011e+\3\2\2\2\u011f\u0123"+
		"\5\u0090I\2\u0120\u0123\5.\30\2\u0121\u0123\7!\2\2\u0122\u011f\3\2\2\2"+
		"\u0122\u0120\3\2\2\2\u0122\u0121\3\2\2\2\u0123-\3\2\2\2\u0124\u0126\7"+
		"\"\2\2\u0125\u0127\5t;\2\u0126\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128"+
		"\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129/\3\2\2\2\u012a\u012d\5\64\33"+
		"\2\u012b\u012d\5\62\32\2\u012c\u012a\3\2\2\2\u012c\u012b\3\2\2\2\u012d"+
		"\61\3\2\2\2\u012e\u0131\5\64\33\2\u012f\u0130\7\7\2\2\u0130\u0132\5\64"+
		"\33\2\u0131\u012f\3\2\2\2\u0132\u0133\3\2\2\2\u0133\u0131\3\2\2\2\u0133"+
		"\u0134\3\2\2\2\u0134\63\3\2\2\2\u0135\u0138\5\66\34\2\u0136\u0138\58\35"+
		"\2\u0137\u0135\3\2\2\2\u0137\u0136\3\2\2\2\u0138\65\3\2\2\2\u0139\u013b"+
		"\5:\36\2\u013a\u013c\7\b\2\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\67\3\2\2\2\u013d\u0140\5:\36\2\u013e\u013f\7\b\2\2\u013f\u0141\5:\36"+
		"\2\u0140\u013e\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143"+
		"\3\2\2\2\u0143\u0145\3\2\2\2\u0144\u0146\7\b\2\2\u0145\u0144\3\2\2\2\u0145"+
		"\u0146\3\2\2\2\u01469\3\2\2\2\u0147\u0148\7\t\2\2\u0148\u014a\5\u0080"+
		"A\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014d\3\2\2\2\u014b"+
		"\u014e\5R*\2\u014c\u014e\5<\37\2\u014d\u014b\3\2\2\2\u014d\u014c\3\2\2"+
		"\2\u014e\u0151\3\2\2\2\u014f\u0151\5n8\2\u0150\u0149\3\2\2\2\u0150\u014f"+
		"\3\2\2\2\u0151;\3\2\2\2\u0152\u0153\7\n\2\2\u0153\u0154\5\60\31\2\u0154"+
		"\u0156\7\13\2\2\u0155\u0157\5z>\2\u0156\u0155\3\2\2\2\u0156\u0157\3\2"+
		"\2\2\u0157\u015b\3\2\2\2\u0158\u015a\5r:\2\u0159\u0158\3\2\2\2\u015a\u015d"+
		"\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015e\3\2\2\2\u015d"+
		"\u015b\3\2\2\2\u015e\u015f\5p9\2\u015f=\3\2\2\2\u0160\u0162\5B\"\2\u0161"+
		"\u0163\5h\65\2\u0162\u0161\3\2\2\2\u0162\u0163\3\2\2\2\u0163\u016b\3\2"+
		"\2\2\u0164\u016b\5h\65\2\u0165\u0166\7\n\2\2\u0166\u0167\5\26\f\2\u0167"+
		"\u0168\7\13\2\2\u0168\u016b\3\2\2\2\u0169\u016b\7\f\2\2\u016a\u0160\3"+
		"\2\2\2\u016a\u0164\3\2\2\2\u016a\u0165\3\2\2\2\u016a\u0169\3\2\2\2\u016b"+
		"?\3\2\2\2\u016c\u016e\5B\"\2\u016d\u016f\5j\66\2\u016e\u016d\3\2\2\2\u016e"+
		"\u016f\3\2\2\2\u016f\u017a\3\2\2\2\u0170\u0172\5j\66\2\u0171\u0173\5B"+
		"\"\2\u0172\u0171\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u017a\3\2\2\2\u0174"+
		"\u0175\7\n\2\2\u0175\u0176\5\26\f\2\u0176\u0177\7\13\2\2\u0177\u017a\3"+
		"\2\2\2\u0178\u017a\7\f\2\2\u0179\u016c\3\2\2\2\u0179\u0170\3\2\2\2\u0179"+
		"\u0174\3\2\2\2\u0179\u0178\3\2\2\2\u017aA\3\2\2\2\u017b\u017f\7#\2\2\u017c"+
		"\u017e\5F$\2\u017d\u017c\3\2\2\2\u017e\u0181\3\2\2\2\u017f\u017d\3\2\2"+
		"\2\u017f\u0180\3\2\2\2\u0180\u019d\3\2\2\2\u0181\u017f\3\2\2\2\u0182\u0186"+
		"\5D#\2\u0183\u0185\5H%\2\u0184\u0183\3\2\2\2\u0185\u0188\3\2\2\2\u0186"+
		"\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u019d\3\2\2\2\u0188\u0186\3\2"+
		"\2\2\u0189\u018d\5x=\2\u018a\u018c\5F$\2\u018b\u018a\3\2\2\2\u018c\u018f"+
		"\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u019d\3\2\2\2\u018f"+
		"\u018d\3\2\2\2\u0190\u0194\5V,\2\u0191\u0193\5F$\2\u0192\u0191\3\2\2\2"+
		"\u0193\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u019d"+
		"\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u0199\5F$\2\u0198\u0197\3\2\2\2\u0199"+
		"\u019a\3\2\2\2\u019a\u0198\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u019d\3\2"+
		"\2\2\u019c\u017b\3\2\2\2\u019c\u0182\3\2\2\2\u019c\u0189\3\2\2\2\u019c"+
		"\u0190\3\2\2\2\u019c\u0198\3\2\2\2\u019dC\3\2\2\2\u019e\u019f\t\3\2\2"+
		"\u019fE\3\2\2\2\u01a0\u01a3\5H%\2\u01a1\u01a3\5L\'\2\u01a2\u01a0\3\2\2"+
		"\2\u01a2\u01a1\3\2\2\2\u01a3G\3\2\2\2\u01a4\u01a5\5J&\2\u01a5\u01a6\7"+
		"B\2\2\u01a6\u01ac\3\2\2\2\u01a7\u01a9\7>\2\2\u01a8\u01aa\7?\2\2\u01a9"+
		"\u01a8\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ac\3\2\2\2\u01ab\u01a4\3\2"+
		"\2\2\u01ab\u01a7\3\2\2\2\u01acI\3\2\2\2\u01ad\u01ae\t\4\2\2\u01aeK\3\2"+
		"\2\2\u01af\u01b0\5N(\2\u01b0\u01b1\5\u0082B\2\u01b1\u01b6\3\2\2\2\u01b2"+
		"\u01b3\5P)\2\u01b3\u01b4\7B\2\2\u01b4\u01b6\3\2\2\2\u01b5\u01af\3\2\2"+
		"\2\u01b5\u01b2\3\2\2\2\u01b6M\3\2\2\2\u01b7\u01b8\t\5\2\2\u01b8O\3\2\2"+
		"\2\u01b9\u01ba\t\6\2\2\u01baQ\3\2\2\2\u01bb\u01bd\5T+\2\u01bc\u01bb\3"+
		"\2\2\2\u01bc\u01bd\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u01bf\5t;\2\u01bf"+
		"\u01c1\5 \21\2\u01c0\u01c2\5z>\2\u01c1\u01c0\3\2\2\2\u01c1\u01c2\3\2\2"+
		"\2\u01c2\u01c6\3\2\2\2\u01c3\u01c5\5r:\2\u01c4\u01c3\3\2\2\2\u01c5\u01c8"+
		"\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8"+
		"\u01c6\3\2\2\2\u01c9\u01ca\5p9\2\u01caS\3\2\2\2\u01cb\u01cd\7\4\2\2\u01cc"+
		"\u01ce\7\r\2\2\u01cd\u01cc\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01d4\3\2"+
		"\2\2\u01cf\u01d1\7\r\2\2\u01d0\u01d2\7\4\2\2\u01d1\u01d0\3\2\2\2\u01d1"+
		"\u01d2\3\2\2\2\u01d2\u01d4\3\2\2\2\u01d3\u01cb\3\2\2\2\u01d3\u01cf\3\2"+
		"\2\2\u01d4U\3\2\2\2\u01d5\u01d9\7\16\2\2\u01d6\u01d8\5X-\2\u01d7\u01d6"+
		"\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u01dc\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc\u01dd\7\17\2\2\u01ddW\3\2\2\2"+
		"\u01de\u01f4\5Z.\2\u01df\u01f4\5^\60\2\u01e0\u01f4\5b\62\2\u01e1\u01f1"+
		"\7\f\2\2\u01e2\u01e4\5\\/\2\u01e3\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5"+
		"\u01e3\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01f2\3\2\2\2\u01e7\u01e9\5`"+
		"\61\2\u01e8\u01e7\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01e8\3\2\2\2\u01ea"+
		"\u01eb\3\2\2\2\u01eb\u01f2\3\2\2\2\u01ec\u01ee\5d\63\2\u01ed\u01ec\3\2"+
		"\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0"+
		"\u01f2\3\2\2\2\u01f1\u01e3\3\2\2\2\u01f1\u01e8\3\2\2\2\u01f1\u01ed\3\2"+
		"\2\2\u01f2\u01f4\3\2\2\2\u01f3\u01de\3\2\2\2\u01f3\u01df\3\2\2\2\u01f3"+
		"\u01e0\3\2\2\2\u01f3\u01e1\3\2\2\2\u01f4Y\3\2\2\2\u01f5\u01fd\5\u008a"+
		"F\2\u01f6\u01fa\7E\2\2\u01f7\u01f9\5\\/\2\u01f8\u01f7\3\2\2\2\u01f9\u01fc"+
		"\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fe\3\2\2\2\u01fc"+
		"\u01fa\3\2\2\2\u01fd\u01f6\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe[\3\2\2\2"+
		"\u01ff\u0200\7\20\2\2\u0200\u0202\5\u008aF\2\u0201\u0203\7E\2\2\u0202"+
		"\u0201\3\2\2\2\u0202\u0203\3\2\2\2\u0203]\3\2\2\2\u0204\u020c\5f\64\2"+
		"\u0205\u0209\7E\2\2\u0206\u0208\5`\61\2\u0207\u0206\3\2\2\2\u0208\u020b"+
		"\3\2\2\2\u0209\u0207\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020d\3\2\2\2\u020b"+
		"\u0209\3\2\2\2\u020c\u0205\3\2\2\2\u020c\u020d\3\2\2\2\u020d_\3\2\2\2"+
		"\u020e\u020f\7\20\2\2\u020f\u0211\5f\64\2\u0210\u0212\7E\2\2\u0211\u0210"+
		"\3\2\2\2\u0211\u0212\3\2\2\2\u0212a\3\2\2\2\u0213\u021b\7A\2\2\u0214\u0218"+
		"\7E\2\2\u0215\u0217\5d\63\2\u0216\u0215\3\2\2\2\u0217\u021a\3\2\2\2\u0218"+
		"\u0216\3\2\2\2\u0218\u0219\3\2\2\2\u0219\u021c\3\2\2\2\u021a\u0218\3\2"+
		"\2\2\u021b\u0214\3\2\2\2\u021b\u021c\3\2\2\2\u021c\u0226\3\2\2\2\u021d"+
		"\u021e\7\21\2\2\u021e\u0222\7E\2\2\u021f\u0221\5d\63\2\u0220\u021f\3\2"+
		"\2\2\u0221\u0224\3\2\2\2\u0222\u0220\3\2\2\2\u0222\u0223\3\2\2\2\u0223"+
		"\u0226\3\2\2\2\u0224\u0222\3\2\2\2\u0225\u0213\3\2\2\2\u0225\u021d\3\2"+
		"\2\2\u0226c\3\2\2\2\u0227\u0228\7\20\2\2\u0228\u022a\7A\2\2\u0229\u022b"+
		"\7E\2\2\u022a\u0229\3\2\2\2\u022a\u022b\3\2\2\2\u022be\3\2\2\2\u022c\u0230"+
		"\5\u0084C\2\u022d\u0230\5\u0082B\2\u022e\u0230\5\u0086D\2\u022f\u022c"+
		"\3\2\2\2\u022f\u022d\3\2\2\2\u022f\u022e\3\2\2\2\u0230g\3\2\2\2\u0231"+
		"\u0234\5*\26\2\u0232\u0234\5l\67\2\u0233\u0231\3\2\2\2\u0233\u0232\3\2"+
		"\2\2\u0234i\3\2\2\2\u0235\u0238\5(\25\2\u0236\u0238\5l\67\2\u0237\u0235"+
		"\3\2\2\2\u0237\u0236\3\2\2\2\u0238k\3\2\2\2\u0239\u023e\7=\2\2\u023a\u023e"+
		"\7<\2\2\u023b\u023c\7\21\2\2\u023c\u023e\5~@\2\u023d\u0239\3\2\2\2\u023d"+
		"\u023a\3\2\2\2\u023d\u023b\3\2\2\2\u023em\3\2\2\2\u023f\u0240\7\22\2\2"+
		"\u0240\u0241\5\u0080A\2\u0241o\3\2\2\2\u0242\u0244\5\u0092J\2\u0243\u0242"+
		"\3\2\2\2\u0244\u0247\3\2\2\2\u0245\u0243\3\2\2\2\u0245\u0246\3\2\2\2\u0246"+
		"q\3\2\2\2\u0247\u0245\3\2\2\2\u0248\u0249\7\23\2\2\u0249\u024c\5t;\2\u024a"+
		"\u024d\5\u008aF\2\u024b\u024d\5f\64\2\u024c\u024a\3\2\2\2\u024c\u024b"+
		"\3\2\2\2\u024ds\3\2\2\2\u024e\u0251\5\u008aF\2\u024f\u0251\5v<\2\u0250"+
		"\u024e\3\2\2\2\u0250\u024f\3\2\2\2\u0251u\3\2\2\2\u0252\u0253\78\2\2\u0253"+
		"w\3\2\2\2\u0254\u0255\5\u008aF\2\u0255y\3\2\2\2\u0256\u025b\7F\2\2\u0257"+
		"\u025b\7\24\2\2\u0258\u025b\7\25\2\2\u0259\u025b\5|?\2\u025a\u0256\3\2"+
		"\2\2\u025a\u0257\3\2\2\2\u025a\u0258\3\2\2\2\u025a\u0259\3\2\2\2\u025b"+
		"{\3\2\2\2\u025c\u025d\7\5\2\2\u025d\u025e\7B\2\2\u025e\u0267\7\6\2\2\u025f"+
		"\u0260\7\5\2\2\u0260\u0261\7B\2\2\u0261\u0263\7\26\2\2\u0262\u0264\t\7"+
		"\2\2\u0263\u0262\3\2\2\2\u0263\u0264\3\2\2\2\u0264\u0265\3\2\2\2\u0265"+
		"\u0267\7\6\2\2\u0266\u025c\3\2\2\2\u0266\u025f\3\2\2\2\u0267}\3\2\2\2"+
		"\u0268\u026b\5\u008aF\2\u0269\u026b\5\u008eH\2\u026a\u0268\3\2\2\2\u026a"+
		"\u0269\3\2\2\2\u026b\177\3\2\2\2\u026c\u026f\5\u008aF\2\u026d\u026f\5"+
		"\u008eH\2\u026e\u026c\3\2\2\2\u026e\u026d\3\2\2\2\u026f\u0081\3\2\2\2"+
		"\u0270\u0271\t\b\2\2\u0271\u0083\3\2\2\2\u0272\u0276\5\u0088E\2\u0273"+
		"\u0277\7A\2\2\u0274\u0275\7\27\2\2\u0275\u0277\5x=\2\u0276\u0273\3\2\2"+
		"\2\u0276\u0274\3\2\2\2\u0276\u0277\3\2\2\2\u0277\u0085\3\2\2\2\u0278\u0279"+
		"\t\t\2\2\u0279\u0087\3\2\2\2\u027a\u027b\t\n\2\2\u027b\u0089\3\2\2\2\u027c"+
		"\u027f\79\2\2\u027d\u027f\5\u008cG\2\u027e\u027c\3\2\2\2\u027e\u027d\3"+
		"\2\2\2\u027f\u008b\3\2\2\2\u0280\u0281\t\13\2\2\u0281\u008d\3\2\2\2\u0282"+
		"\u0283\7@\2\2\u0283\u008f\3\2\2\2\u0284\u0285\7\34\2\2\u0285\u0286\7\21"+
		"\2\2\u0286\u028a\5~@\2\u0287\u0288\7\22\2\2\u0288\u028a\5~@\2\u0289\u0284"+
		"\3\2\2\2\u0289\u0287\3\2\2\2\u028a\u0091\3\2\2\2\u028b\u028c\7\30\2\2"+
		"\u028c\u028d\5\u008aF\2\u028d\u028e\t\f\2\2\u028e\u0093\3\2\2\2Y\u0097"+
		"\u009c\u00a1\u00a4\u00ab\u00b9\u00c2\u00c6\u00c9\u00ce\u00d7\u00df\u00e3"+
		"\u00f0\u00f8\u00fc\u0103\u0108\u010f\u0114\u011a\u0122\u0128\u012c\u0133"+
		"\u0137\u013b\u0142\u0145\u0149\u014d\u0150\u0156\u015b\u0162\u016a\u016e"+
		"\u0172\u0179\u017f\u0186\u018d\u0194\u019a\u019c\u01a2\u01a9\u01ab\u01b5"+
		"\u01bc\u01c1\u01c6\u01cd\u01d1\u01d3\u01d9\u01e5\u01ea\u01ef\u01f1\u01f3"+
		"\u01fa\u01fd\u0202\u0209\u020c\u0211\u0218\u021b\u0222\u0225\u022a\u022f"+
		"\u0233\u0237\u023d\u0245\u024c\u0250\u025a\u0263\u0266\u026a\u026e\u0276"+
		"\u027e\u0289";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}