// Generated from fr/inria/lille/shexjava/schema/parsing/ShExC/ShExDoc.g4 by ANTLR 4.5
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
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, KW_BASE=23, KW_EXTERNAL=24, 
		KW_IMPORT=25, KW_PREFIX=26, KW_START=27, KW_VIRTUAL=28, KW_CLOSED=29, 
		KW_EXTRA=30, KW_LITERAL=31, KW_IRI=32, KW_NONLITERAL=33, KW_BNODE=34, 
		KW_AND=35, KW_OR=36, KW_MININCLUSIVE=37, KW_MINEXCLUSIVE=38, KW_MAXINCLUSIVE=39, 
		KW_MAXEXCLUSIVE=40, KW_LENGTH=41, KW_MINLENGTH=42, KW_MAXLENGTH=43, KW_TOTALDIGITS=44, 
		KW_FRACTIONDIGITS=45, KW_NOT=46, KW_TRUE=47, KW_FALSE=48, PASS=49, COMMENT=50, 
		CODE=51, RDF_TYPE=52, IRIREF=53, PNAME_NS=54, PNAME_LN=55, ATPNAME_NS=56, 
		ATPNAME_LN=57, REGEXP=58, REGEXP_FLAGS=59, BLANK_NODE_LABEL=60, LANGTAG=61, 
		INTEGER=62, DECIMAL=63, DOUBLE=64, STEM_MARK=65, UNBOUNDED=66, STRING_LITERAL1=67, 
		STRING_LITERAL2=68, STRING_LITERAL_LONG1=69, STRING_LITERAL_LONG2=70;
	public static final int
		RULE_shExDoc = 0, RULE_directive = 1, RULE_baseDecl = 2, RULE_prefixDecl = 3, 
		RULE_importDecl = 4, RULE_notStartAction = 5, RULE_start = 6, RULE_startActions = 7, 
		RULE_statement = 8, RULE_shapeExprDecl = 9, RULE_shapeExpression = 10, 
		RULE_shapeOr = 11, RULE_shapeAnd = 12, RULE_shapeNot = 13, RULE_negation = 14, 
		RULE_inlineShapeExpression = 15, RULE_inlineShapeOr = 16, RULE_inlineShapeAnd = 17, 
		RULE_inlineShapeNot = 18, RULE_inlineShapeDefinition = 19, RULE_shapeDefinition = 20, 
		RULE_qualifier = 21, RULE_extraPropertySet = 22, RULE_oneOfShape = 23, 
		RULE_multiElementOneOf = 24, RULE_innerShape = 25, RULE_groupShape = 26, 
		RULE_singleElementGroup = 27, RULE_multiElementGroup = 28, RULE_unaryShape = 29, 
		RULE_encapsulatedShape = 30, RULE_shapeAtom = 31, RULE_inlineShapeAtom = 32, 
		RULE_nodeConstraint = 33, RULE_nonLiteralKind = 34, RULE_xsFacet = 35, 
		RULE_stringFacet = 36, RULE_stringLength = 37, RULE_numericFacet = 38, 
		RULE_numericRange = 39, RULE_numericLength = 40, RULE_tripleConstraint = 41, 
		RULE_senseFlags = 42, RULE_valueSet = 43, RULE_valueSetValue = 44, RULE_iriRange = 45, 
		RULE_iriExclusion = 46, RULE_literalRange = 47, RULE_literalExclusion = 48, 
		RULE_languageRange = 49, RULE_languageExclusion = 50, RULE_literal = 51, 
		RULE_shapeOrRef = 52, RULE_inlineShapeOrRef = 53, RULE_shapeRef = 54, 
		RULE_include = 55, RULE_semanticActions = 56, RULE_annotation = 57, RULE_predicate = 58, 
		RULE_rdfType = 59, RULE_datatype = 60, RULE_cardinality = 61, RULE_repeatRange = 62, 
		RULE_shapeExprLabel = 63, RULE_tripleExprLabel = 64, RULE_numericLiteral = 65, 
		RULE_rdfLiteral = 66, RULE_booleanLiteral = 67, RULE_string = 68, RULE_iri = 69, 
		RULE_prefixedName = 70, RULE_blankNode = 71, RULE_codeDecl = 72, RULE_includeSet = 73;
	public static final String[] ruleNames = {
		"shExDoc", "directive", "baseDecl", "prefixDecl", "importDecl", "notStartAction", 
		"start", "startActions", "statement", "shapeExprDecl", "shapeExpression", 
		"shapeOr", "shapeAnd", "shapeNot", "negation", "inlineShapeExpression", 
		"inlineShapeOr", "inlineShapeAnd", "inlineShapeNot", "inlineShapeDefinition", 
		"shapeDefinition", "qualifier", "extraPropertySet", "oneOfShape", "multiElementOneOf", 
		"innerShape", "groupShape", "singleElementGroup", "multiElementGroup", 
		"unaryShape", "encapsulatedShape", "shapeAtom", "inlineShapeAtom", "nodeConstraint", 
		"nonLiteralKind", "xsFacet", "stringFacet", "stringLength", "numericFacet", 
		"numericRange", "numericLength", "tripleConstraint", "senseFlags", "valueSet", 
		"valueSetValue", "iriRange", "iriExclusion", "literalRange", "literalExclusion", 
		"languageRange", "languageExclusion", "literal", "shapeOrRef", "inlineShapeOrRef", 
		"shapeRef", "include", "semanticActions", "annotation", "predicate", "rdfType", 
		"datatype", "cardinality", "repeatRange", "shapeExprLabel", "tripleExprLabel", 
		"numericLiteral", "rdfLiteral", "booleanLiteral", "string", "iri", "prefixedName", 
		"blankNode", "codeDecl", "includeSet"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'!'", "'{'", "'}'", "'|'", "'('", "')'", "';'", "'$'", "'.'", 
		"'^'", "'['", "']'", "'-'", "'@'", "'&'", "'//'", "'+'", "'?'", "','", 
		"'^^'", "'%'", null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "'true'", "'false'", null, null, null, "'a'", null, null, 
		null, null, null, null, null, null, null, null, null, null, "'~'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "KW_BASE", 
		"KW_EXTERNAL", "KW_IMPORT", "KW_PREFIX", "KW_START", "KW_VIRTUAL", "KW_CLOSED", 
		"KW_EXTRA", "KW_LITERAL", "KW_IRI", "KW_NONLITERAL", "KW_BNODE", "KW_AND", 
		"KW_OR", "KW_MININCLUSIVE", "KW_MINEXCLUSIVE", "KW_MAXINCLUSIVE", "KW_MAXEXCLUSIVE", 
		"KW_LENGTH", "KW_MINLENGTH", "KW_MAXLENGTH", "KW_TOTALDIGITS", "KW_FRACTIONDIGITS", 
		"KW_NOT", "KW_TRUE", "KW_FALSE", "PASS", "COMMENT", "CODE", "RDF_TYPE", 
		"IRIREF", "PNAME_NS", "PNAME_LN", "ATPNAME_NS", "ATPNAME_LN", "REGEXP", 
		"REGEXP_FLAGS", "BLANK_NODE_LABEL", "LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", 
		"STEM_MARK", "UNBOUNDED", "STRING_LITERAL1", "STRING_LITERAL2", "STRING_LITERAL_LONG1", 
		"STRING_LITERAL_LONG2"
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
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX))) != 0)) {
				{
				{
				setState(148);
				directive();
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(164);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
				{
				setState(156);
				switch (_input.LA(1)) {
				case KW_START:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
				case BLANK_NODE_LABEL:
					{
					setState(154);
					notStartAction();
					}
					break;
				case T__21:
					{
					setState(155);
					startActions();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
					{
					{
					setState(158);
					statement();
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(166);
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
			setState(171);
			switch (_input.LA(1)) {
			case KW_BASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				baseDecl();
				}
				break;
			case KW_PREFIX:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				prefixDecl();
				}
				break;
			case KW_IMPORT:
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
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
			setState(173);
			match(KW_BASE);
			setState(174);
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
			setState(176);
			match(KW_PREFIX);
			setState(177);
			match(PNAME_NS);
			setState(178);
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
			setState(180);
			match(KW_IMPORT);
			setState(181);
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
			setState(185);
			switch (_input.LA(1)) {
			case KW_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(183);
				start();
				}
				break;
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
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
			setState(187);
			match(KW_START);
			setState(188);
			match(T__0);
			setState(189);
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
		public List<CodeDeclContext> codeDecl() {
			return getRuleContexts(CodeDeclContext.class);
		}
		public CodeDeclContext codeDecl(int i) {
			return getRuleContext(CodeDeclContext.class,i);
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
			setState(192); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(191);
				codeDecl();
				}
				}
				setState(194); 
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
			setState(198);
			switch (_input.LA(1)) {
			case KW_BASE:
			case KW_IMPORT:
			case KW_PREFIX:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				directive();
				}
				break;
			case KW_START:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
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
		public ShapeExprLabelContext shapeExprLabel() {
			return getRuleContext(ShapeExprLabelContext.class,0);
		}
		public ShapeExpressionContext shapeExpression() {
			return getRuleContext(ShapeExpressionContext.class,0);
		}
		public TerminalNode KW_EXTERNAL() { return getToken(ShExDocParser.KW_EXTERNAL, 0); }
		public ShapeExprDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeExprDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeExprDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShapeExprDeclContext shapeExprDecl() throws RecognitionException {
		ShapeExprDeclContext _localctx = new ShapeExprDeclContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_shapeExprDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			shapeExprLabel();
			setState(203);
			switch (_input.LA(1)) {
			case T__1:
			case T__2:
			case T__5:
			case T__9:
			case T__11:
			case T__14:
			case T__15:
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
				setState(201);
				shapeExpression();
				}
				break;
			case KW_EXTERNAL:
				{
				setState(202);
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
			setState(205);
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
			setState(207);
			shapeAnd();
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_OR) {
				{
				{
				setState(208);
				match(KW_OR);
				setState(209);
				shapeAnd();
				}
				}
				setState(214);
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
			setState(215);
			shapeNot();
			setState(220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(216);
				match(KW_AND);
				setState(217);
				shapeNot();
				}
				}
				setState(222);
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
			setState(224);
			_la = _input.LA(1);
			if (_la==T__1 || _la==KW_NOT) {
				{
				setState(223);
				negation();
				}
			}

			setState(226);
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
			setState(228);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==KW_NOT) ) {
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
			setState(230);
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
			setState(232);
			inlineShapeAnd();
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_OR) {
				{
				{
				setState(233);
				match(KW_OR);
				setState(234);
				inlineShapeAnd();
				}
				}
				setState(239);
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
			setState(240);
			inlineShapeNot();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(241);
				match(KW_AND);
				setState(242);
				inlineShapeNot();
				}
				}
				setState(247);
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
			setState(249);
			_la = _input.LA(1);
			if (_la==T__1 || _la==KW_NOT) {
				{
				setState(248);
				negation();
				}
			}

			setState(251);
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
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(253);
				qualifier();
				}
				}
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(259);
			match(T__2);
			setState(261);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__5) | (1L << T__8) | (1L << T__10) | (1L << T__15) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(260);
				oneOfShape();
				}
			}

			setState(263);
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
			setState(268);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(265);
				qualifier();
				}
				}
				setState(270);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(271);
			match(T__2);
			setState(273);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__5) | (1L << T__8) | (1L << T__10) | (1L << T__15) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(272);
				oneOfShape();
				}
			}

			setState(275);
			match(T__3);
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(276);
				annotation();
				}
				}
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(282);
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
		public IncludeSetContext includeSet() {
			return getRuleContext(IncludeSetContext.class,0);
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
			setState(287);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(284);
				includeSet();
				}
				break;
			case KW_EXTRA:
				enterOuterAlt(_localctx, 2);
				{
				setState(285);
				extraPropertySet();
				}
				break;
			case KW_CLOSED:
				enterOuterAlt(_localctx, 3);
				{
				setState(286);
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
			setState(289);
			match(KW_EXTRA);
			setState(291); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(290);
				predicate();
				}
				}
				setState(293); 
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
			setState(297);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(295);
				groupShape();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(296);
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
			setState(299);
			groupShape();
			setState(302); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(300);
				match(T__4);
				setState(301);
				groupShape();
				}
				}
				setState(304); 
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

	public static class InnerShapeContext extends ParserRuleContext {
		public MultiElementGroupContext multiElementGroup() {
			return getRuleContext(MultiElementGroupContext.class,0);
		}
		public MultiElementOneOfContext multiElementOneOf() {
			return getRuleContext(MultiElementOneOfContext.class,0);
		}
		public InnerShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_innerShape; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInnerShape(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InnerShapeContext innerShape() throws RecognitionException {
		InnerShapeContext _localctx = new InnerShapeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_innerShape);
		try {
			setState(308);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				multiElementGroup();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
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

	public static class GroupShapeContext extends ParserRuleContext {
		public GroupShapeContext groupShape() {
			return getRuleContext(GroupShapeContext.class,0);
		}
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
		enterRule(_localctx, 52, RULE_groupShape);
		try {
			setState(316);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(310);
				match(T__5);
				setState(311);
				groupShape();
				setState(312);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(314);
				singleElementGroup();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(315);
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
		enterRule(_localctx, 54, RULE_singleElementGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			unaryShape();
			setState(320);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(319);
				match(T__7);
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
		enterRule(_localctx, 56, RULE_multiElementGroup);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			unaryShape();
			setState(325); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(323);
					match(T__7);
					setState(324);
					unaryShape();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(327); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(330);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(329);
				match(T__7);
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
		enterRule(_localctx, 58, RULE_unaryShape);
		int _la;
		try {
			setState(341);
			switch (_input.LA(1)) {
			case T__1:
			case T__5:
			case T__8:
			case T__10:
			case RDF_TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(334);
				_la = _input.LA(1);
				if (_la==T__8) {
					{
					setState(332);
					match(T__8);
					setState(333);
					tripleExprLabel();
					}
				}

				setState(338);
				switch (_input.LA(1)) {
				case T__1:
				case T__10:
				case RDF_TYPE:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
					{
					setState(336);
					tripleConstraint();
					}
					break;
				case T__5:
					{
					setState(337);
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
				setState(340);
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
		public InnerShapeContext innerShape() {
			return getRuleContext(InnerShapeContext.class,0);
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
		enterRule(_localctx, 60, RULE_encapsulatedShape);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(T__5);
			setState(344);
			innerShape();
			setState(345);
			match(T__6);
			setState(347);
			_la = _input.LA(1);
			if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (T__2 - 3)) | (1L << (T__17 - 3)) | (1L << (T__18 - 3)) | (1L << (UNBOUNDED - 3)))) != 0)) {
				{
				setState(346);
				cardinality();
				}
			}

			setState(352);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(349);
				annotation();
				}
				}
				setState(354);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(355);
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
		enterRule(_localctx, 62, RULE_shapeAtom);
		int _la;
		try {
			setState(367);
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
				setState(357);
				nodeConstraint();
				setState(359);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__15) | (1L << KW_CLOSED) | (1L << KW_EXTRA) | (1L << ATPNAME_NS) | (1L << ATPNAME_LN))) != 0)) {
					{
					setState(358);
					shapeOrRef();
					}
				}

				}
				break;
			case T__2:
			case T__14:
			case T__15:
			case KW_CLOSED:
			case KW_EXTRA:
			case ATPNAME_NS:
			case ATPNAME_LN:
				_localctx = new ShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(361);
				shapeOrRef();
				}
				break;
			case T__5:
				_localctx = new ShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(362);
				match(T__5);
				setState(363);
				shapeExpression();
				setState(364);
				match(T__6);
				}
				break;
			case T__9:
				_localctx = new ShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(366);
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
		enterRule(_localctx, 64, RULE_inlineShapeAtom);
		int _la;
		try {
			setState(382);
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
				setState(369);
				nodeConstraint();
				setState(371);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(370);
					inlineShapeOrRef();
					}
					break;
				}
				}
				break;
			case T__2:
			case T__14:
			case T__15:
			case KW_CLOSED:
			case KW_EXTRA:
			case ATPNAME_NS:
			case ATPNAME_LN:
				_localctx = new InlineShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(373);
				inlineShapeOrRef();
				setState(375);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << KW_LITERAL) | (1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE) | (1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << REGEXP))) != 0)) {
					{
					setState(374);
					nodeConstraint();
					}
				}

				}
				break;
			case T__5:
				_localctx = new InlineShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(377);
				match(T__5);
				setState(378);
				shapeExpression();
				setState(379);
				match(T__6);
				}
				break;
			case T__9:
				_localctx = new InlineShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(381);
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
		enterRule(_localctx, 66, RULE_nodeConstraint);
		int _la;
		try {
			setState(417);
			switch (_input.LA(1)) {
			case KW_LITERAL:
				_localctx = new NodeConstraintLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(384);
				match(KW_LITERAL);
				setState(388);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(385);
					xsFacet();
					}
					}
					setState(390);
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
				setState(391);
				nonLiteralKind();
				setState(395);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(392);
					stringFacet();
					}
					}
					setState(397);
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
				setState(398);
				datatype();
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
			case T__11:
				_localctx = new NodeConstraintValueSetContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(405);
				valueSet();
				setState(409);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(406);
					xsFacet();
					}
					}
					setState(411);
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
				setState(413); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(412);
					xsFacet();
					}
					}
					setState(415); 
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
		enterRule(_localctx, 68, RULE_nonLiteralKind);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE))) != 0)) ) {
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
		enterRule(_localctx, 70, RULE_xsFacet);
		try {
			setState(423);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case REGEXP:
				enterOuterAlt(_localctx, 1);
				{
				setState(421);
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
				setState(422);
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
		enterRule(_localctx, 72, RULE_stringFacet);
		int _la;
		try {
			setState(432);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
				enterOuterAlt(_localctx, 1);
				{
				setState(425);
				stringLength();
				setState(426);
				match(INTEGER);
				}
				break;
			case REGEXP:
				enterOuterAlt(_localctx, 2);
				{
				setState(428);
				match(REGEXP);
				setState(430);
				_la = _input.LA(1);
				if (_la==REGEXP_FLAGS) {
					{
					setState(429);
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
		enterRule(_localctx, 74, RULE_stringLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH))) != 0)) ) {
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
		enterRule(_localctx, 76, RULE_numericFacet);
		try {
			setState(442);
			switch (_input.LA(1)) {
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
				enterOuterAlt(_localctx, 1);
				{
				setState(436);
				numericRange();
				setState(437);
				numericLiteral();
				}
				break;
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
				enterOuterAlt(_localctx, 2);
				{
				setState(439);
				numericLength();
				setState(440);
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
		enterRule(_localctx, 78, RULE_numericRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE))) != 0)) ) {
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
		enterRule(_localctx, 80, RULE_numericLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(446);
			_la = _input.LA(1);
			if ( !(_la==KW_TOTALDIGITS || _la==KW_FRACTIONDIGITS) ) {
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
		enterRule(_localctx, 82, RULE_tripleConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
			_la = _input.LA(1);
			if (_la==T__1 || _la==T__10) {
				{
				setState(448);
				senseFlags();
				}
			}

			setState(451);
			predicate();
			setState(452);
			inlineShapeExpression();
			setState(454);
			_la = _input.LA(1);
			if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (T__2 - 3)) | (1L << (T__17 - 3)) | (1L << (T__18 - 3)) | (1L << (UNBOUNDED - 3)))) != 0)) {
				{
				setState(453);
				cardinality();
				}
			}

			setState(459);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(456);
				annotation();
				}
				}
				setState(461);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(462);
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
		enterRule(_localctx, 84, RULE_senseFlags);
		int _la;
		try {
			setState(472);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(464);
				match(T__1);
				setState(466);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(465);
					match(T__10);
					}
				}

				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(468);
				match(T__10);
				setState(470);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(469);
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
		enterRule(_localctx, 86, RULE_valueSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(474);
			match(T__11);
			setState(478);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 10)) & ~0x3f) == 0 && ((1L << (_la - 10)) & ((1L << (T__9 - 10)) | (1L << (T__14 - 10)) | (1L << (KW_TRUE - 10)) | (1L << (KW_FALSE - 10)) | (1L << (IRIREF - 10)) | (1L << (PNAME_NS - 10)) | (1L << (PNAME_LN - 10)) | (1L << (LANGTAG - 10)) | (1L << (INTEGER - 10)) | (1L << (DECIMAL - 10)) | (1L << (DOUBLE - 10)) | (1L << (STRING_LITERAL1 - 10)) | (1L << (STRING_LITERAL2 - 10)) | (1L << (STRING_LITERAL_LONG1 - 10)) | (1L << (STRING_LITERAL_LONG2 - 10)))) != 0)) {
				{
				{
				setState(475);
				valueSetValue();
				}
				}
				setState(480);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(481);
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
		enterRule(_localctx, 88, RULE_valueSetValue);
		int _la;
		try {
			setState(504);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(483);
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
				setState(484);
				literalRange();
				}
				break;
			case T__14:
			case LANGTAG:
				enterOuterAlt(_localctx, 3);
				{
				setState(485);
				languageRange();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 4);
				{
				setState(486);
				match(T__9);
				setState(502);
				switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
				case 1:
					{
					setState(488); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(487);
						iriExclusion();
						}
						}
						setState(490); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__13 );
					}
					break;
				case 2:
					{
					setState(493); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(492);
						literalExclusion();
						}
						}
						setState(495); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__13 );
					}
					break;
				case 3:
					{
					setState(498); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(497);
						languageExclusion();
						}
						}
						setState(500); 
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
		enterRule(_localctx, 90, RULE_iriRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(506);
			iri();
			setState(514);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(507);
				match(STEM_MARK);
				setState(511);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(508);
					iriExclusion();
					}
					}
					setState(513);
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
		enterRule(_localctx, 92, RULE_iriExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(516);
			match(T__13);
			setState(517);
			iri();
			setState(519);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(518);
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
		enterRule(_localctx, 94, RULE_literalRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(521);
			literal();
			setState(529);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(522);
				match(STEM_MARK);
				setState(526);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(523);
					literalExclusion();
					}
					}
					setState(528);
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
		enterRule(_localctx, 96, RULE_literalExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			match(T__13);
			setState(532);
			literal();
			setState(534);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(533);
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
		enterRule(_localctx, 98, RULE_languageRange);
		int _la;
		try {
			setState(554);
			switch (_input.LA(1)) {
			case LANGTAG:
				enterOuterAlt(_localctx, 1);
				{
				setState(536);
				match(LANGTAG);
				setState(544);
				_la = _input.LA(1);
				if (_la==STEM_MARK) {
					{
					setState(537);
					match(STEM_MARK);
					setState(541);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__13) {
						{
						{
						setState(538);
						languageExclusion();
						}
						}
						setState(543);
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
				setState(546);
				match(T__14);
				setState(547);
				match(STEM_MARK);
				setState(551);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(548);
					languageExclusion();
					}
					}
					setState(553);
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
		enterRule(_localctx, 100, RULE_languageExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			match(T__13);
			setState(557);
			match(LANGTAG);
			setState(559);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(558);
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
		enterRule(_localctx, 102, RULE_literal);
		try {
			setState(564);
			switch (_input.LA(1)) {
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 1);
				{
				setState(561);
				rdfLiteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(562);
				numericLiteral();
				}
				break;
			case KW_TRUE:
			case KW_FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(563);
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
		enterRule(_localctx, 104, RULE_shapeOrRef);
		try {
			setState(568);
			switch (_input.LA(1)) {
			case T__2:
			case T__15:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(566);
				shapeDefinition();
				}
				break;
			case T__14:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(567);
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
		enterRule(_localctx, 106, RULE_inlineShapeOrRef);
		try {
			setState(572);
			switch (_input.LA(1)) {
			case T__2:
			case T__15:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(570);
				inlineShapeDefinition();
				}
				break;
			case T__14:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(571);
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
		enterRule(_localctx, 108, RULE_shapeRef);
		try {
			setState(578);
			switch (_input.LA(1)) {
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(574);
				match(ATPNAME_LN);
				}
				break;
			case ATPNAME_NS:
				enterOuterAlt(_localctx, 2);
				{
				setState(575);
				match(ATPNAME_NS);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(576);
				match(T__14);
				setState(577);
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
		enterRule(_localctx, 110, RULE_include);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(580);
			match(T__15);
			setState(581);
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
		public List<CodeDeclContext> codeDecl() {
			return getRuleContexts(CodeDeclContext.class);
		}
		public CodeDeclContext codeDecl(int i) {
			return getRuleContext(CodeDeclContext.class,i);
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
		enterRule(_localctx, 112, RULE_semanticActions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(583);
				codeDecl();
				}
				}
				setState(588);
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
		enterRule(_localctx, 114, RULE_annotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589);
			match(T__16);
			setState(590);
			predicate();
			setState(593);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				{
				setState(591);
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
				setState(592);
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
		enterRule(_localctx, 116, RULE_predicate);
		try {
			setState(597);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(595);
				iri();
				}
				break;
			case RDF_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(596);
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
		enterRule(_localctx, 118, RULE_rdfType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(599);
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
		enterRule(_localctx, 120, RULE_datatype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
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
		enterRule(_localctx, 122, RULE_cardinality);
		try {
			setState(607);
			switch (_input.LA(1)) {
			case UNBOUNDED:
				_localctx = new StarCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(603);
				match(UNBOUNDED);
				}
				break;
			case T__17:
				_localctx = new PlusCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(604);
				match(T__17);
				}
				break;
			case T__18:
				_localctx = new OptionalCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(605);
				match(T__18);
				}
				break;
			case T__2:
				_localctx = new RepeatCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(606);
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
		enterRule(_localctx, 124, RULE_repeatRange);
		int _la;
		try {
			setState(619);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				_localctx = new ExactRangeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(609);
				match(T__2);
				setState(610);
				match(INTEGER);
				setState(611);
				match(T__3);
				}
				break;
			case 2:
				_localctx = new MinMaxRangeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(612);
				match(T__2);
				setState(613);
				match(INTEGER);
				setState(614);
				match(T__19);
				setState(616);
				_la = _input.LA(1);
				if (_la==INTEGER || _la==UNBOUNDED) {
					{
					setState(615);
					_la = _input.LA(1);
					if ( !(_la==INTEGER || _la==UNBOUNDED) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
				}

				setState(618);
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
		enterRule(_localctx, 126, RULE_shapeExprLabel);
		try {
			setState(623);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(621);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(622);
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
		enterRule(_localctx, 128, RULE_tripleExprLabel);
		try {
			setState(627);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
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
		enterRule(_localctx, 130, RULE_numericLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(629);
			_la = _input.LA(1);
			if ( !(((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (INTEGER - 62)) | (1L << (DECIMAL - 62)) | (1L << (DOUBLE - 62)))) != 0)) ) {
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
		enterRule(_localctx, 132, RULE_rdfLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(631);
			string();
			setState(635);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				{
				setState(632);
				match(LANGTAG);
				}
				break;
			case 2:
				{
				setState(633);
				match(T__20);
				setState(634);
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
		enterRule(_localctx, 134, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(637);
			_la = _input.LA(1);
			if ( !(_la==KW_TRUE || _la==KW_FALSE) ) {
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
		enterRule(_localctx, 136, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(639);
			_la = _input.LA(1);
			if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (STRING_LITERAL1 - 67)) | (1L << (STRING_LITERAL2 - 67)) | (1L << (STRING_LITERAL_LONG1 - 67)) | (1L << (STRING_LITERAL_LONG2 - 67)))) != 0)) ) {
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
		enterRule(_localctx, 138, RULE_iri);
		try {
			setState(643);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(641);
				match(IRIREF);
				}
				break;
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(642);
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
		enterRule(_localctx, 140, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(645);
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
		enterRule(_localctx, 142, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
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

	public static class CodeDeclContext extends ParserRuleContext {
		public IriContext iri() {
			return getRuleContext(IriContext.class,0);
		}
		public TerminalNode CODE() { return getToken(ShExDocParser.CODE, 0); }
		public CodeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codeDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitCodeDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeDeclContext codeDecl() throws RecognitionException {
		CodeDeclContext _localctx = new CodeDeclContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_codeDecl);
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

	public static class IncludeSetContext extends ParserRuleContext {
		public List<TripleExprLabelContext> tripleExprLabel() {
			return getRuleContexts(TripleExprLabelContext.class);
		}
		public TripleExprLabelContext tripleExprLabel(int i) {
			return getRuleContext(TripleExprLabelContext.class,i);
		}
		public IncludeSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includeSet; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitIncludeSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncludeSetContext includeSet() throws RecognitionException {
		IncludeSetContext _localctx = new IncludeSetContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_includeSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(653);
			match(T__15);
			setState(655); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(654);
				tripleExprLabel();
				}
				}
				setState(657); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0) );
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3H\u0296\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\3\2\7\2\u0098\n\2\f\2\16\2\u009b\13\2\3\2\3\2\5\2\u009f"+
		"\n\2\3\2\7\2\u00a2\n\2\f\2\16\2\u00a5\13\2\5\2\u00a7\n\2\3\2\3\2\3\3\3"+
		"\3\3\3\5\3\u00ae\n\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\5"+
		"\7\u00bc\n\7\3\b\3\b\3\b\3\b\3\t\6\t\u00c3\n\t\r\t\16\t\u00c4\3\n\3\n"+
		"\5\n\u00c9\n\n\3\13\3\13\3\13\5\13\u00ce\n\13\3\f\3\f\3\r\3\r\3\r\7\r"+
		"\u00d5\n\r\f\r\16\r\u00d8\13\r\3\16\3\16\3\16\7\16\u00dd\n\16\f\16\16"+
		"\16\u00e0\13\16\3\17\5\17\u00e3\n\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22"+
		"\3\22\3\22\7\22\u00ee\n\22\f\22\16\22\u00f1\13\22\3\23\3\23\3\23\7\23"+
		"\u00f6\n\23\f\23\16\23\u00f9\13\23\3\24\5\24\u00fc\n\24\3\24\3\24\3\25"+
		"\7\25\u0101\n\25\f\25\16\25\u0104\13\25\3\25\3\25\5\25\u0108\n\25\3\25"+
		"\3\25\3\26\7\26\u010d\n\26\f\26\16\26\u0110\13\26\3\26\3\26\5\26\u0114"+
		"\n\26\3\26\3\26\7\26\u0118\n\26\f\26\16\26\u011b\13\26\3\26\3\26\3\27"+
		"\3\27\3\27\5\27\u0122\n\27\3\30\3\30\6\30\u0126\n\30\r\30\16\30\u0127"+
		"\3\31\3\31\5\31\u012c\n\31\3\32\3\32\3\32\6\32\u0131\n\32\r\32\16\32\u0132"+
		"\3\33\3\33\5\33\u0137\n\33\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u013f\n"+
		"\34\3\35\3\35\5\35\u0143\n\35\3\36\3\36\3\36\6\36\u0148\n\36\r\36\16\36"+
		"\u0149\3\36\5\36\u014d\n\36\3\37\3\37\5\37\u0151\n\37\3\37\3\37\5\37\u0155"+
		"\n\37\3\37\5\37\u0158\n\37\3 \3 \3 \3 \5 \u015e\n \3 \7 \u0161\n \f \16"+
		" \u0164\13 \3 \3 \3!\3!\5!\u016a\n!\3!\3!\3!\3!\3!\3!\5!\u0172\n!\3\""+
		"\3\"\5\"\u0176\n\"\3\"\3\"\5\"\u017a\n\"\3\"\3\"\3\"\3\"\3\"\5\"\u0181"+
		"\n\"\3#\3#\7#\u0185\n#\f#\16#\u0188\13#\3#\3#\7#\u018c\n#\f#\16#\u018f"+
		"\13#\3#\3#\7#\u0193\n#\f#\16#\u0196\13#\3#\3#\7#\u019a\n#\f#\16#\u019d"+
		"\13#\3#\6#\u01a0\n#\r#\16#\u01a1\5#\u01a4\n#\3$\3$\3%\3%\5%\u01aa\n%\3"+
		"&\3&\3&\3&\3&\5&\u01b1\n&\5&\u01b3\n&\3\'\3\'\3(\3(\3(\3(\3(\3(\5(\u01bd"+
		"\n(\3)\3)\3*\3*\3+\5+\u01c4\n+\3+\3+\3+\5+\u01c9\n+\3+\7+\u01cc\n+\f+"+
		"\16+\u01cf\13+\3+\3+\3,\3,\5,\u01d5\n,\3,\3,\5,\u01d9\n,\5,\u01db\n,\3"+
		"-\3-\7-\u01df\n-\f-\16-\u01e2\13-\3-\3-\3.\3.\3.\3.\3.\6.\u01eb\n.\r."+
		"\16.\u01ec\3.\6.\u01f0\n.\r.\16.\u01f1\3.\6.\u01f5\n.\r.\16.\u01f6\5."+
		"\u01f9\n.\5.\u01fb\n.\3/\3/\3/\7/\u0200\n/\f/\16/\u0203\13/\5/\u0205\n"+
		"/\3\60\3\60\3\60\5\60\u020a\n\60\3\61\3\61\3\61\7\61\u020f\n\61\f\61\16"+
		"\61\u0212\13\61\5\61\u0214\n\61\3\62\3\62\3\62\5\62\u0219\n\62\3\63\3"+
		"\63\3\63\7\63\u021e\n\63\f\63\16\63\u0221\13\63\5\63\u0223\n\63\3\63\3"+
		"\63\3\63\7\63\u0228\n\63\f\63\16\63\u022b\13\63\5\63\u022d\n\63\3\64\3"+
		"\64\3\64\5\64\u0232\n\64\3\65\3\65\3\65\5\65\u0237\n\65\3\66\3\66\5\66"+
		"\u023b\n\66\3\67\3\67\5\67\u023f\n\67\38\38\38\38\58\u0245\n8\39\39\3"+
		"9\3:\7:\u024b\n:\f:\16:\u024e\13:\3;\3;\3;\3;\5;\u0254\n;\3<\3<\5<\u0258"+
		"\n<\3=\3=\3>\3>\3?\3?\3?\3?\5?\u0262\n?\3@\3@\3@\3@\3@\3@\3@\5@\u026b"+
		"\n@\3@\5@\u026e\n@\3A\3A\5A\u0272\nA\3B\3B\5B\u0276\nB\3C\3C\3D\3D\3D"+
		"\3D\5D\u027e\nD\3E\3E\3F\3F\3G\3G\5G\u0286\nG\3H\3H\3I\3I\3J\3J\3J\3J"+
		"\3K\3K\6K\u0292\nK\rK\16K\u0293\3K\2\2L\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080"+
		"\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\2\r\4\2\4"+
		"\4\60\60\3\2\"$\3\2+-\3\2\'*\3\2./\4\2@@DD\3\2@B\3\2\61\62\3\2EH\3\28"+
		"9\4\2\30\30\65\65\u02b4\2\u0099\3\2\2\2\4\u00ad\3\2\2\2\6\u00af\3\2\2"+
		"\2\b\u00b2\3\2\2\2\n\u00b6\3\2\2\2\f\u00bb\3\2\2\2\16\u00bd\3\2\2\2\20"+
		"\u00c2\3\2\2\2\22\u00c8\3\2\2\2\24\u00ca\3\2\2\2\26\u00cf\3\2\2\2\30\u00d1"+
		"\3\2\2\2\32\u00d9\3\2\2\2\34\u00e2\3\2\2\2\36\u00e6\3\2\2\2 \u00e8\3\2"+
		"\2\2\"\u00ea\3\2\2\2$\u00f2\3\2\2\2&\u00fb\3\2\2\2(\u0102\3\2\2\2*\u010e"+
		"\3\2\2\2,\u0121\3\2\2\2.\u0123\3\2\2\2\60\u012b\3\2\2\2\62\u012d\3\2\2"+
		"\2\64\u0136\3\2\2\2\66\u013e\3\2\2\28\u0140\3\2\2\2:\u0144\3\2\2\2<\u0157"+
		"\3\2\2\2>\u0159\3\2\2\2@\u0171\3\2\2\2B\u0180\3\2\2\2D\u01a3\3\2\2\2F"+
		"\u01a5\3\2\2\2H\u01a9\3\2\2\2J\u01b2\3\2\2\2L\u01b4\3\2\2\2N\u01bc\3\2"+
		"\2\2P\u01be\3\2\2\2R\u01c0\3\2\2\2T\u01c3\3\2\2\2V\u01da\3\2\2\2X\u01dc"+
		"\3\2\2\2Z\u01fa\3\2\2\2\\\u01fc\3\2\2\2^\u0206\3\2\2\2`\u020b\3\2\2\2"+
		"b\u0215\3\2\2\2d\u022c\3\2\2\2f\u022e\3\2\2\2h\u0236\3\2\2\2j\u023a\3"+
		"\2\2\2l\u023e\3\2\2\2n\u0244\3\2\2\2p\u0246\3\2\2\2r\u024c\3\2\2\2t\u024f"+
		"\3\2\2\2v\u0257\3\2\2\2x\u0259\3\2\2\2z\u025b\3\2\2\2|\u0261\3\2\2\2~"+
		"\u026d\3\2\2\2\u0080\u0271\3\2\2\2\u0082\u0275\3\2\2\2\u0084\u0277\3\2"+
		"\2\2\u0086\u0279\3\2\2\2\u0088\u027f\3\2\2\2\u008a\u0281\3\2\2\2\u008c"+
		"\u0285\3\2\2\2\u008e\u0287\3\2\2\2\u0090\u0289\3\2\2\2\u0092\u028b\3\2"+
		"\2\2\u0094\u028f\3\2\2\2\u0096\u0098\5\4\3\2\u0097\u0096\3\2\2\2\u0098"+
		"\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u00a6\3\2"+
		"\2\2\u009b\u0099\3\2\2\2\u009c\u009f\5\f\7\2\u009d\u009f\5\20\t\2\u009e"+
		"\u009c\3\2\2\2\u009e\u009d\3\2\2\2\u009f\u00a3\3\2\2\2\u00a0\u00a2\5\22"+
		"\n\2\u00a1\u00a0\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u009e\3\2"+
		"\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\7\2\2\3\u00a9"+
		"\3\3\2\2\2\u00aa\u00ae\5\6\4\2\u00ab\u00ae\5\b\5\2\u00ac\u00ae\5\n\6\2"+
		"\u00ad\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae\5\3"+
		"\2\2\2\u00af\u00b0\7\31\2\2\u00b0\u00b1\7\67\2\2\u00b1\7\3\2\2\2\u00b2"+
		"\u00b3\7\34\2\2\u00b3\u00b4\78\2\2\u00b4\u00b5\7\67\2\2\u00b5\t\3\2\2"+
		"\2\u00b6\u00b7\7\33\2\2\u00b7\u00b8\7\67\2\2\u00b8\13\3\2\2\2\u00b9\u00bc"+
		"\5\16\b\2\u00ba\u00bc\5\24\13\2\u00bb\u00b9\3\2\2\2\u00bb\u00ba\3\2\2"+
		"\2\u00bc\r\3\2\2\2\u00bd\u00be\7\35\2\2\u00be\u00bf\7\3\2\2\u00bf\u00c0"+
		"\5\26\f\2\u00c0\17\3\2\2\2\u00c1\u00c3\5\u0092J\2\u00c2\u00c1\3\2\2\2"+
		"\u00c3\u00c4\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\21"+
		"\3\2\2\2\u00c6\u00c9\5\4\3\2\u00c7\u00c9\5\f\7\2\u00c8\u00c6\3\2\2\2\u00c8"+
		"\u00c7\3\2\2\2\u00c9\23\3\2\2\2\u00ca\u00cd\5\u0080A\2\u00cb\u00ce\5\26"+
		"\f\2\u00cc\u00ce\7\32\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce"+
		"\25\3\2\2\2\u00cf\u00d0\5\30\r\2\u00d0\27\3\2\2\2\u00d1\u00d6\5\32\16"+
		"\2\u00d2\u00d3\7&\2\2\u00d3\u00d5\5\32\16\2\u00d4\u00d2\3\2\2\2\u00d5"+
		"\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\31\3\2\2"+
		"\2\u00d8\u00d6\3\2\2\2\u00d9\u00de\5\34\17\2\u00da\u00db\7%\2\2\u00db"+
		"\u00dd\5\34\17\2\u00dc\u00da\3\2\2\2\u00dd\u00e0\3\2\2\2\u00de\u00dc\3"+
		"\2\2\2\u00de\u00df\3\2\2\2\u00df\33\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1"+
		"\u00e3\5\36\20\2\u00e2\u00e1\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\3"+
		"\2\2\2\u00e4\u00e5\5@!\2\u00e5\35\3\2\2\2\u00e6\u00e7\t\2\2\2\u00e7\37"+
		"\3\2\2\2\u00e8\u00e9\5\"\22\2\u00e9!\3\2\2\2\u00ea\u00ef\5$\23\2\u00eb"+
		"\u00ec\7&\2\2\u00ec\u00ee\5$\23\2\u00ed\u00eb\3\2\2\2\u00ee\u00f1\3\2"+
		"\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0#\3\2\2\2\u00f1\u00ef"+
		"\3\2\2\2\u00f2\u00f7\5&\24\2\u00f3\u00f4\7%\2\2\u00f4\u00f6\5&\24\2\u00f5"+
		"\u00f3\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2"+
		"\2\2\u00f8%\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fc\5\36\20\2\u00fb\u00fa"+
		"\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\5B\"\2\u00fe"+
		"\'\3\2\2\2\u00ff\u0101\5,\27\2\u0100\u00ff\3\2\2\2\u0101\u0104\3\2\2\2"+
		"\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u0102"+
		"\3\2\2\2\u0105\u0107\7\5\2\2\u0106\u0108\5\60\31\2\u0107\u0106\3\2\2\2"+
		"\u0107\u0108\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010a\7\6\2\2\u010a)\3"+
		"\2\2\2\u010b\u010d\5,\27\2\u010c\u010b\3\2\2\2\u010d\u0110\3\2\2\2\u010e"+
		"\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0111\3\2\2\2\u0110\u010e\3\2"+
		"\2\2\u0111\u0113\7\5\2\2\u0112\u0114\5\60\31\2\u0113\u0112\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0119\7\6\2\2\u0116\u0118\5t"+
		";\2\u0117\u0116\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\u011c\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d\5r"+
		":\2\u011d+\3\2\2\2\u011e\u0122\5\u0094K\2\u011f\u0122\5.\30\2\u0120\u0122"+
		"\7\37\2\2\u0121\u011e\3\2\2\2\u0121\u011f\3\2\2\2\u0121\u0120\3\2\2\2"+
		"\u0122-\3\2\2\2\u0123\u0125\7 \2\2\u0124\u0126\5v<\2\u0125\u0124\3\2\2"+
		"\2\u0126\u0127\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128/"+
		"\3\2\2\2\u0129\u012c\5\66\34\2\u012a\u012c\5\62\32\2\u012b\u0129\3\2\2"+
		"\2\u012b\u012a\3\2\2\2\u012c\61\3\2\2\2\u012d\u0130\5\66\34\2\u012e\u012f"+
		"\7\7\2\2\u012f\u0131\5\66\34\2\u0130\u012e\3\2\2\2\u0131\u0132\3\2\2\2"+
		"\u0132\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133\63\3\2\2\2\u0134\u0137"+
		"\5:\36\2\u0135\u0137\5\62\32\2\u0136\u0134\3\2\2\2\u0136\u0135\3\2\2\2"+
		"\u0137\65\3\2\2\2\u0138\u0139\7\b\2\2\u0139\u013a\5\66\34\2\u013a\u013b"+
		"\7\t\2\2\u013b\u013f\3\2\2\2\u013c\u013f\58\35\2\u013d\u013f\5:\36\2\u013e"+
		"\u0138\3\2\2\2\u013e\u013c\3\2\2\2\u013e\u013d\3\2\2\2\u013f\67\3\2\2"+
		"\2\u0140\u0142\5<\37\2\u0141\u0143\7\n\2\2\u0142\u0141\3\2\2\2\u0142\u0143"+
		"\3\2\2\2\u01439\3\2\2\2\u0144\u0147\5<\37\2\u0145\u0146\7\n\2\2\u0146"+
		"\u0148\5<\37\2\u0147\u0145\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u0147\3\2"+
		"\2\2\u0149\u014a\3\2\2\2\u014a\u014c\3\2\2\2\u014b\u014d\7\n\2\2\u014c"+
		"\u014b\3\2\2\2\u014c\u014d\3\2\2\2\u014d;\3\2\2\2\u014e\u014f\7\13\2\2"+
		"\u014f\u0151\5\u0082B\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151"+
		"\u0154\3\2\2\2\u0152\u0155\5T+\2\u0153\u0155\5> \2\u0154\u0152\3\2\2\2"+
		"\u0154\u0153\3\2\2\2\u0155\u0158\3\2\2\2\u0156\u0158\5p9\2\u0157\u0150"+
		"\3\2\2\2\u0157\u0156\3\2\2\2\u0158=\3\2\2\2\u0159\u015a\7\b\2\2\u015a"+
		"\u015b\5\64\33\2\u015b\u015d\7\t\2\2\u015c\u015e\5|?\2\u015d\u015c\3\2"+
		"\2\2\u015d\u015e\3\2\2\2\u015e\u0162\3\2\2\2\u015f\u0161\5t;\2\u0160\u015f"+
		"\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0166\5r:\2\u0166?\3\2\2\2\u0167"+
		"\u0169\5D#\2\u0168\u016a\5j\66\2\u0169\u0168\3\2\2\2\u0169\u016a\3\2\2"+
		"\2\u016a\u0172\3\2\2\2\u016b\u0172\5j\66\2\u016c\u016d\7\b\2\2\u016d\u016e"+
		"\5\26\f\2\u016e\u016f\7\t\2\2\u016f\u0172\3\2\2\2\u0170\u0172\7\f\2\2"+
		"\u0171\u0167\3\2\2\2\u0171\u016b\3\2\2\2\u0171\u016c\3\2\2\2\u0171\u0170"+
		"\3\2\2\2\u0172A\3\2\2\2\u0173\u0175\5D#\2\u0174\u0176\5l\67\2\u0175\u0174"+
		"\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u0181\3\2\2\2\u0177\u0179\5l\67\2\u0178"+
		"\u017a\5D#\2\u0179\u0178\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u0181\3\2\2"+
		"\2\u017b\u017c\7\b\2\2\u017c\u017d\5\26\f\2\u017d\u017e\7\t\2\2\u017e"+
		"\u0181\3\2\2\2\u017f\u0181\7\f\2\2\u0180\u0173\3\2\2\2\u0180\u0177\3\2"+
		"\2\2\u0180\u017b\3\2\2\2\u0180\u017f\3\2\2\2\u0181C\3\2\2\2\u0182\u0186"+
		"\7!\2\2\u0183\u0185\5H%\2\u0184\u0183\3\2\2\2\u0185\u0188\3\2\2\2\u0186"+
		"\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u01a4\3\2\2\2\u0188\u0186\3\2"+
		"\2\2\u0189\u018d\5F$\2\u018a\u018c\5J&\2\u018b\u018a\3\2\2\2\u018c\u018f"+
		"\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u01a4\3\2\2\2\u018f"+
		"\u018d\3\2\2\2\u0190\u0194\5z>\2\u0191\u0193\5H%\2\u0192\u0191\3\2\2\2"+
		"\u0193\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u01a4"+
		"\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u019b\5X-\2\u0198\u019a\5H%\2\u0199"+
		"\u0198\3\2\2\2\u019a\u019d\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2"+
		"\2\2\u019c\u01a4\3\2\2\2\u019d\u019b\3\2\2\2\u019e\u01a0\5H%\2\u019f\u019e"+
		"\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2"+
		"\u01a4\3\2\2\2\u01a3\u0182\3\2\2\2\u01a3\u0189\3\2\2\2\u01a3\u0190\3\2"+
		"\2\2\u01a3\u0197\3\2\2\2\u01a3\u019f\3\2\2\2\u01a4E\3\2\2\2\u01a5\u01a6"+
		"\t\3\2\2\u01a6G\3\2\2\2\u01a7\u01aa\5J&\2\u01a8\u01aa\5N(\2\u01a9\u01a7"+
		"\3\2\2\2\u01a9\u01a8\3\2\2\2\u01aaI\3\2\2\2\u01ab\u01ac\5L\'\2\u01ac\u01ad"+
		"\7@\2\2\u01ad\u01b3\3\2\2\2\u01ae\u01b0\7<\2\2\u01af\u01b1\7=\2\2\u01b0"+
		"\u01af\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b3\3\2\2\2\u01b2\u01ab\3\2"+
		"\2\2\u01b2\u01ae\3\2\2\2\u01b3K\3\2\2\2\u01b4\u01b5\t\4\2\2\u01b5M\3\2"+
		"\2\2\u01b6\u01b7\5P)\2\u01b7\u01b8\5\u0084C\2\u01b8\u01bd\3\2\2\2\u01b9"+
		"\u01ba\5R*\2\u01ba\u01bb\7@\2\2\u01bb\u01bd\3\2\2\2\u01bc\u01b6\3\2\2"+
		"\2\u01bc\u01b9\3\2\2\2\u01bdO\3\2\2\2\u01be\u01bf\t\5\2\2\u01bfQ\3\2\2"+
		"\2\u01c0\u01c1\t\6\2\2\u01c1S\3\2\2\2\u01c2\u01c4\5V,\2\u01c3\u01c2\3"+
		"\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01c6\5v<\2\u01c6"+
		"\u01c8\5 \21\2\u01c7\u01c9\5|?\2\u01c8\u01c7\3\2\2\2\u01c8\u01c9\3\2\2"+
		"\2\u01c9\u01cd\3\2\2\2\u01ca\u01cc\5t;\2\u01cb\u01ca\3\2\2\2\u01cc\u01cf"+
		"\3\2\2\2\u01cd\u01cb\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01d0\3\2\2\2\u01cf"+
		"\u01cd\3\2\2\2\u01d0\u01d1\5r:\2\u01d1U\3\2\2\2\u01d2\u01d4\7\4\2\2\u01d3"+
		"\u01d5\7\r\2\2\u01d4\u01d3\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01db\3\2"+
		"\2\2\u01d6\u01d8\7\r\2\2\u01d7\u01d9\7\4\2\2\u01d8\u01d7\3\2\2\2\u01d8"+
		"\u01d9\3\2\2\2\u01d9\u01db\3\2\2\2\u01da\u01d2\3\2\2\2\u01da\u01d6\3\2"+
		"\2\2\u01dbW\3\2\2\2\u01dc\u01e0\7\16\2\2\u01dd\u01df\5Z.\2\u01de\u01dd"+
		"\3\2\2\2\u01df\u01e2\3\2\2\2\u01e0\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1"+
		"\u01e3\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e3\u01e4\7\17\2\2\u01e4Y\3\2\2\2"+
		"\u01e5\u01fb\5\\/\2\u01e6\u01fb\5`\61\2\u01e7\u01fb\5d\63\2\u01e8\u01f8"+
		"\7\f\2\2\u01e9\u01eb\5^\60\2\u01ea\u01e9\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec"+
		"\u01ea\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01f9\3\2\2\2\u01ee\u01f0\5b"+
		"\62\2\u01ef\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01ef\3\2\2\2\u01f1"+
		"\u01f2\3\2\2\2\u01f2\u01f9\3\2\2\2\u01f3\u01f5\5f\64\2\u01f4\u01f3\3\2"+
		"\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f4\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7"+
		"\u01f9\3\2\2\2\u01f8\u01ea\3\2\2\2\u01f8\u01ef\3\2\2\2\u01f8\u01f4\3\2"+
		"\2\2\u01f9\u01fb\3\2\2\2\u01fa\u01e5\3\2\2\2\u01fa\u01e6\3\2\2\2\u01fa"+
		"\u01e7\3\2\2\2\u01fa\u01e8\3\2\2\2\u01fb[\3\2\2\2\u01fc\u0204\5\u008c"+
		"G\2\u01fd\u0201\7C\2\2\u01fe\u0200\5^\60\2\u01ff\u01fe\3\2\2\2\u0200\u0203"+
		"\3\2\2\2\u0201\u01ff\3\2\2\2\u0201\u0202\3\2\2\2\u0202\u0205\3\2\2\2\u0203"+
		"\u0201\3\2\2\2\u0204\u01fd\3\2\2\2\u0204\u0205\3\2\2\2\u0205]\3\2\2\2"+
		"\u0206\u0207\7\20\2\2\u0207\u0209\5\u008cG\2\u0208\u020a\7C\2\2\u0209"+
		"\u0208\3\2\2\2\u0209\u020a\3\2\2\2\u020a_\3\2\2\2\u020b\u0213\5h\65\2"+
		"\u020c\u0210\7C\2\2\u020d\u020f\5b\62\2\u020e\u020d\3\2\2\2\u020f\u0212"+
		"\3\2\2\2\u0210\u020e\3\2\2\2\u0210\u0211\3\2\2\2\u0211\u0214\3\2\2\2\u0212"+
		"\u0210\3\2\2\2\u0213\u020c\3\2\2\2\u0213\u0214\3\2\2\2\u0214a\3\2\2\2"+
		"\u0215\u0216\7\20\2\2\u0216\u0218\5h\65\2\u0217\u0219\7C\2\2\u0218\u0217"+
		"\3\2\2\2\u0218\u0219\3\2\2\2\u0219c\3\2\2\2\u021a\u0222\7?\2\2\u021b\u021f"+
		"\7C\2\2\u021c\u021e\5f\64\2\u021d\u021c\3\2\2\2\u021e\u0221\3\2\2\2\u021f"+
		"\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0223\3\2\2\2\u0221\u021f\3\2"+
		"\2\2\u0222\u021b\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u022d\3\2\2\2\u0224"+
		"\u0225\7\21\2\2\u0225\u0229\7C\2\2\u0226\u0228\5f\64\2\u0227\u0226\3\2"+
		"\2\2\u0228\u022b\3\2\2\2\u0229\u0227\3\2\2\2\u0229\u022a\3\2\2\2\u022a"+
		"\u022d\3\2\2\2\u022b\u0229\3\2\2\2\u022c\u021a\3\2\2\2\u022c\u0224\3\2"+
		"\2\2\u022de\3\2\2\2\u022e\u022f\7\20\2\2\u022f\u0231\7?\2\2\u0230\u0232"+
		"\7C\2\2\u0231\u0230\3\2\2\2\u0231\u0232\3\2\2\2\u0232g\3\2\2\2\u0233\u0237"+
		"\5\u0086D\2\u0234\u0237\5\u0084C\2\u0235\u0237\5\u0088E\2\u0236\u0233"+
		"\3\2\2\2\u0236\u0234\3\2\2\2\u0236\u0235\3\2\2\2\u0237i\3\2\2\2\u0238"+
		"\u023b\5*\26\2\u0239\u023b\5n8\2\u023a\u0238\3\2\2\2\u023a\u0239\3\2\2"+
		"\2\u023bk\3\2\2\2\u023c\u023f\5(\25\2\u023d\u023f\5n8\2\u023e\u023c\3"+
		"\2\2\2\u023e\u023d\3\2\2\2\u023fm\3\2\2\2\u0240\u0245\7;\2\2\u0241\u0245"+
		"\7:\2\2\u0242\u0243\7\21\2\2\u0243\u0245\5\u0080A\2\u0244\u0240\3\2\2"+
		"\2\u0244\u0241\3\2\2\2\u0244\u0242\3\2\2\2\u0245o\3\2\2\2\u0246\u0247"+
		"\7\22\2\2\u0247\u0248\5\u0082B\2\u0248q\3\2\2\2\u0249\u024b\5\u0092J\2"+
		"\u024a\u0249\3\2\2\2\u024b\u024e\3\2\2\2\u024c\u024a\3\2\2\2\u024c\u024d"+
		"\3\2\2\2\u024ds\3\2\2\2\u024e\u024c\3\2\2\2\u024f\u0250\7\23\2\2\u0250"+
		"\u0253\5v<\2\u0251\u0254\5\u008cG\2\u0252\u0254\5h\65\2\u0253\u0251\3"+
		"\2\2\2\u0253\u0252\3\2\2\2\u0254u\3\2\2\2\u0255\u0258\5\u008cG\2\u0256"+
		"\u0258\5x=\2\u0257\u0255\3\2\2\2\u0257\u0256\3\2\2\2\u0258w\3\2\2\2\u0259"+
		"\u025a\7\66\2\2\u025ay\3\2\2\2\u025b\u025c\5\u008cG\2\u025c{\3\2\2\2\u025d"+
		"\u0262\7D\2\2\u025e\u0262\7\24\2\2\u025f\u0262\7\25\2\2\u0260\u0262\5"+
		"~@\2\u0261\u025d\3\2\2\2\u0261\u025e\3\2\2\2\u0261\u025f\3\2\2\2\u0261"+
		"\u0260\3\2\2\2\u0262}\3\2\2\2\u0263\u0264\7\5\2\2\u0264\u0265\7@\2\2\u0265"+
		"\u026e\7\6\2\2\u0266\u0267\7\5\2\2\u0267\u0268\7@\2\2\u0268\u026a\7\26"+
		"\2\2\u0269\u026b\t\7\2\2\u026a\u0269\3\2\2\2\u026a\u026b\3\2\2\2\u026b"+
		"\u026c\3\2\2\2\u026c\u026e\7\6\2\2\u026d\u0263\3\2\2\2\u026d\u0266\3\2"+
		"\2\2\u026e\177\3\2\2\2\u026f\u0272\5\u008cG\2\u0270\u0272\5\u0090I\2\u0271"+
		"\u026f\3\2\2\2\u0271\u0270\3\2\2\2\u0272\u0081\3\2\2\2\u0273\u0276\5\u008c"+
		"G\2\u0274\u0276\5\u0090I\2\u0275\u0273\3\2\2\2\u0275\u0274\3\2\2\2\u0276"+
		"\u0083\3\2\2\2\u0277\u0278\t\b\2\2\u0278\u0085\3\2\2\2\u0279\u027d\5\u008a"+
		"F\2\u027a\u027e\7?\2\2\u027b\u027c\7\27\2\2\u027c\u027e\5z>\2\u027d\u027a"+
		"\3\2\2\2\u027d\u027b\3\2\2\2\u027d\u027e\3\2\2\2\u027e\u0087\3\2\2\2\u027f"+
		"\u0280\t\t\2\2\u0280\u0089\3\2\2\2\u0281\u0282\t\n\2\2\u0282\u008b\3\2"+
		"\2\2\u0283\u0286\7\67\2\2\u0284\u0286\5\u008eH\2\u0285\u0283\3\2\2\2\u0285"+
		"\u0284\3\2\2\2\u0286\u008d\3\2\2\2\u0287\u0288\t\13\2\2\u0288\u008f\3"+
		"\2\2\2\u0289\u028a\7>\2\2\u028a\u0091\3\2\2\2\u028b\u028c\7\30\2\2\u028c"+
		"\u028d\5\u008cG\2\u028d\u028e\t\f\2\2\u028e\u0093\3\2\2\2\u028f\u0291"+
		"\7\22\2\2\u0290\u0292\5\u0082B\2\u0291\u0290\3\2\2\2\u0292\u0293\3\2\2"+
		"\2\u0293\u0291\3\2\2\2\u0293\u0294\3\2\2\2\u0294\u0095\3\2\2\2Y\u0099"+
		"\u009e\u00a3\u00a6\u00ad\u00bb\u00c4\u00c8\u00cd\u00d6\u00de\u00e2\u00ef"+
		"\u00f7\u00fb\u0102\u0107\u010e\u0113\u0119\u0121\u0127\u012b\u0132\u0136"+
		"\u013e\u0142\u0149\u014c\u0150\u0154\u0157\u015d\u0162\u0169\u0171\u0175"+
		"\u0179\u0180\u0186\u018d\u0194\u019b\u01a1\u01a3\u01a9\u01b0\u01b2\u01bc"+
		"\u01c3\u01c8\u01cd\u01d4\u01d8\u01da\u01e0\u01ec\u01f1\u01f6\u01f8\u01fa"+
		"\u0201\u0204\u0209\u0210\u0213\u0218\u021f\u0222\u0229\u022c\u0231\u0236"+
		"\u023a\u023e\u0244\u024c\u0253\u0257\u0261\u026a\u026d\u0271\u0275\u027d"+
		"\u0285\u0293";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}