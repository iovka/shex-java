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
		T__17=18, T__18=19, T__19=20, T__20=21, KW_ABSTRACT=22, KW_BASE=23, KW_EXTENDS=24, 
		KW_IMPORT=25, KW_RESTRICTS=26, KW_EXTERNAL=27, KW_PREFIX=28, KW_START=29, 
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
		RULE_inlineShapeExpression = 11, RULE_shapeOr = 12, RULE_inlineShapeOr = 13, 
		RULE_shapeAnd = 14, RULE_inlineShapeAnd = 15, RULE_shapeNot = 16, RULE_inlineShapeNot = 17, 
		RULE_shapeAtom = 18, RULE_inlineShapeAtom = 19, RULE_shapeOrRef = 20, 
		RULE_inlineShapeOrRef = 21, RULE_shapeRef = 22, RULE_inlineLitNodeConstraint = 23, 
		RULE_litNodeConstraint = 24, RULE_inlineNonLitNodeConstraint = 25, RULE_nonLitNodeConstraint = 26, 
		RULE_nonLiteralKind = 27, RULE_xsFacet = 28, RULE_stringFacet = 29, RULE_stringLength = 30, 
		RULE_numericFacet = 31, RULE_numericRange = 32, RULE_numericLength = 33, 
		RULE_rawNumeric = 34, RULE_shapeDefinition = 35, RULE_inlineShapeDefinition = 36, 
		RULE_qualifier = 37, RULE_extraPropertySet = 38, RULE_tripleExpression = 39, 
		RULE_oneOfTripleExpr = 40, RULE_multiElementOneOf = 41, RULE_groupTripleExpr = 42, 
		RULE_singleElementGroup = 43, RULE_multiElementGroup = 44, RULE_unaryTripleExpr = 45, 
		RULE_bracketedTripleExpr = 46, RULE_tripleConstraint = 47, RULE_cardinality = 48, 
		RULE_repeatRange = 49, RULE_senseFlags = 50, RULE_valueSet = 51, RULE_valueSetValue = 52, 
		RULE_iriRange = 53, RULE_iriExclusion = 54, RULE_literalRange = 55, RULE_literalExclusion = 56, 
		RULE_languageRange = 57, RULE_languageExclusion = 58, RULE_include = 59, 
		RULE_annotation = 60, RULE_semanticAction = 61, RULE_literal = 62, RULE_predicate = 63, 
		RULE_rdfType = 64, RULE_datatype = 65, RULE_shapeExprLabel = 66, RULE_tripleExprLabel = 67, 
		RULE_numericLiteral = 68, RULE_rdfLiteral = 69, RULE_booleanLiteral = 70, 
		RULE_rdfString = 71, RULE_iri = 72, RULE_prefixedName = 73, RULE_blankNode = 74, 
		RULE_extension = 75, RULE_restriction = 76;
	public static final String[] ruleNames = {
		"shExDoc", "directive", "baseDecl", "prefixDecl", "importDecl", "notStartAction", 
		"start", "startActions", "statement", "shapeExprDecl", "shapeExpression", 
		"inlineShapeExpression", "shapeOr", "inlineShapeOr", "shapeAnd", "inlineShapeAnd", 
		"shapeNot", "inlineShapeNot", "shapeAtom", "inlineShapeAtom", "shapeOrRef", 
		"inlineShapeOrRef", "shapeRef", "inlineLitNodeConstraint", "litNodeConstraint", 
		"inlineNonLitNodeConstraint", "nonLitNodeConstraint", "nonLiteralKind", 
		"xsFacet", "stringFacet", "stringLength", "numericFacet", "numericRange", 
		"numericLength", "rawNumeric", "shapeDefinition", "inlineShapeDefinition", 
		"qualifier", "extraPropertySet", "tripleExpression", "oneOfTripleExpr", 
		"multiElementOneOf", "groupTripleExpr", "singleElementGroup", "multiElementGroup", 
		"unaryTripleExpr", "bracketedTripleExpr", "tripleConstraint", "cardinality", 
		"repeatRange", "senseFlags", "valueSet", "valueSetValue", "iriRange", 
		"iriExclusion", "literalRange", "literalExclusion", "languageRange", "languageExclusion", 
		"include", "annotation", "semanticAction", "literal", "predicate", "rdfType", 
		"datatype", "shapeExprLabel", "tripleExprLabel", "numericLiteral", "rdfLiteral", 
		"booleanLiteral", "rdfString", "iri", "prefixedName", "blankNode", "extension", 
		"restriction"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'('", "')'", "'.'", "'@'", "'{'", "'}'", "'|'", "';'", "'$'", 
		"'+'", "'?'", "','", "'^'", "'['", "']'", "'-'", "'&'", "'//'", "'%'", 
		"'^^'", null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "'true'", "'false'", null, null, null, "'a'", 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"'~'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "KW_ABSTRACT", 
		"KW_BASE", "KW_EXTENDS", "KW_IMPORT", "KW_RESTRICTS", "KW_EXTERNAL", "KW_PREFIX", 
		"KW_START", "KW_VIRTUAL", "KW_CLOSED", "KW_EXTRA", "KW_LITERAL", "KW_IRI", 
		"KW_NONLITERAL", "KW_BNODE", "KW_AND", "KW_OR", "KW_MININCLUSIVE", "KW_MINEXCLUSIVE", 
		"KW_MAXINCLUSIVE", "KW_MAXEXCLUSIVE", "KW_LENGTH", "KW_MINLENGTH", "KW_MAXLENGTH", 
		"KW_TOTALDIGITS", "KW_FRACTIONDIGITS", "KW_NOT", "KW_TRUE", "KW_FALSE", 
		"PASS", "COMMENT", "CODE", "RDF_TYPE", "IRIREF", "PNAME_NS", "PNAME_LN", 
		"ATPNAME_NS", "ATPNAME_LN", "REGEXP", "REGEXP_FLAGS", "BLANK_NODE_LABEL", 
		"LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "STEM_MARK", "UNBOUNDED", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2"
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
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX))) != 0)) {
				{
				{
				setState(154);
				directive();
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << KW_ABSTRACT) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
				{
				setState(162);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case KW_ABSTRACT:
				case KW_START:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
				case BLANK_NODE_LABEL:
					{
					setState(160);
					notStartAction();
					}
					break;
				case T__19:
					{
					setState(161);
					startActions();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_ABSTRACT) | (1L << KW_BASE) | (1L << KW_IMPORT) | (1L << KW_PREFIX) | (1L << KW_START) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << BLANK_NODE_LABEL))) != 0)) {
					{
					{
					setState(164);
					statement();
					}
					}
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(172);
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
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_BASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				baseDecl();
				}
				break;
			case KW_PREFIX:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				prefixDecl();
				}
				break;
			case KW_IMPORT:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
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
			setState(179);
			match(KW_BASE);
			setState(180);
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
			setState(182);
			match(KW_PREFIX);
			setState(183);
			match(PNAME_NS);
			setState(184);
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
			setState(186);
			match(KW_IMPORT);
			setState(187);
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
			setState(191);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
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
				setState(190);
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
			setState(193);
			match(KW_START);
			setState(194);
			match(T__0);
			setState(195);
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
			setState(198); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(197);
				semanticAction();
				}
				}
				setState(200); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__19 );
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
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_BASE:
			case KW_IMPORT:
			case KW_PREFIX:
				enterOuterAlt(_localctx, 1);
				{
				setState(202);
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
				setState(203);
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
		public TerminalNode KW_ABSTRACT() { return getToken(ShExDocParser.KW_ABSTRACT, 0); }
		public List<RestrictionContext> restriction() {
			return getRuleContexts(RestrictionContext.class);
		}
		public RestrictionContext restriction(int i) {
			return getRuleContext(RestrictionContext.class,i);
		}
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KW_ABSTRACT) {
				{
				setState(206);
				match(KW_ABSTRACT);
				}
			}

			setState(209);
			shapeExprLabel();
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16 || _la==KW_RESTRICTS) {
				{
				{
				setState(210);
				restriction();
				}
				}
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(218);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__3:
			case T__4:
			case T__5:
			case T__14:
			case T__17:
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
				setState(216);
				shapeExpression();
				}
				break;
			case KW_EXTERNAL:
				{
				setState(217);
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
			setState(220);
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
		enterRule(_localctx, 22, RULE_inlineShapeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
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
		enterRule(_localctx, 24, RULE_shapeOr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			shapeAnd();
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_OR) {
				{
				{
				setState(225);
				match(KW_OR);
				setState(226);
				shapeAnd();
				}
				}
				setState(231);
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
		enterRule(_localctx, 26, RULE_inlineShapeOr);
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
		enterRule(_localctx, 28, RULE_shapeAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			shapeNot();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(241);
				match(KW_AND);
				setState(242);
				shapeNot();
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
		enterRule(_localctx, 30, RULE_inlineShapeAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			inlineShapeNot();
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW_AND) {
				{
				{
				setState(249);
				match(KW_AND);
				setState(250);
				inlineShapeNot();
				}
				}
				setState(255);
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
		public TerminalNode KW_NOT() { return getToken(ShExDocParser.KW_NOT, 0); }
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
		enterRule(_localctx, 32, RULE_shapeNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KW_NOT) {
				{
				setState(256);
				match(KW_NOT);
				}
			}

			setState(259);
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

	public static class InlineShapeNotContext extends ParserRuleContext {
		public InlineShapeAtomContext inlineShapeAtom() {
			return getRuleContext(InlineShapeAtomContext.class,0);
		}
		public TerminalNode KW_NOT() { return getToken(ShExDocParser.KW_NOT, 0); }
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
		enterRule(_localctx, 34, RULE_inlineShapeNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KW_NOT) {
				{
				setState(261);
				match(KW_NOT);
				}
			}

			setState(264);
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
	public static class ShapeAtomShapeOrRefContext extends ShapeAtomContext {
		public ShapeOrRefContext shapeOrRef() {
			return getRuleContext(ShapeOrRefContext.class,0);
		}
		public NonLitNodeConstraintContext nonLitNodeConstraint() {
			return getRuleContext(NonLitNodeConstraintContext.class,0);
		}
		public ShapeAtomShapeOrRefContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomShapeOrRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShapeAtomNonLitNodeConstraintContext extends ShapeAtomContext {
		public NonLitNodeConstraintContext nonLitNodeConstraint() {
			return getRuleContext(NonLitNodeConstraintContext.class,0);
		}
		public ShapeOrRefContext shapeOrRef() {
			return getRuleContext(ShapeOrRefContext.class,0);
		}
		public ShapeAtomNonLitNodeConstraintContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomNonLitNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShapeAtomLitNodeConstraintContext extends ShapeAtomContext {
		public LitNodeConstraintContext litNodeConstraint() {
			return getRuleContext(LitNodeConstraintContext.class,0);
		}
		public ShapeAtomLitNodeConstraintContext(ShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitShapeAtomLitNodeConstraint(this);
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
		enterRule(_localctx, 36, RULE_shapeAtom);
		int _la;
		try {
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new ShapeAtomNonLitNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(266);
				nonLitNodeConstraint();
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__17) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA) | (1L << ATPNAME_NS) | (1L << ATPNAME_LN))) != 0)) {
					{
					setState(267);
					shapeOrRef();
					}
				}

				}
				break;
			case 2:
				_localctx = new ShapeAtomLitNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				litNodeConstraint();
				}
				break;
			case 3:
				_localctx = new ShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(271);
				shapeOrRef();
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					setState(272);
					nonLitNodeConstraint();
					}
				}

				}
				break;
			case 4:
				_localctx = new ShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(275);
				match(T__1);
				setState(276);
				shapeExpression();
				setState(277);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new ShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(279);
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
	public static class InlineShapeAtomLitNodeConstraintContext extends InlineShapeAtomContext {
		public InlineLitNodeConstraintContext inlineLitNodeConstraint() {
			return getRuleContext(InlineLitNodeConstraintContext.class,0);
		}
		public InlineShapeAtomLitNodeConstraintContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomLitNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InlineShapeAtomShapeOrRefContext extends InlineShapeAtomContext {
		public InlineShapeOrRefContext inlineShapeOrRef() {
			return getRuleContext(InlineShapeOrRefContext.class,0);
		}
		public InlineNonLitNodeConstraintContext inlineNonLitNodeConstraint() {
			return getRuleContext(InlineNonLitNodeConstraintContext.class,0);
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
	public static class InlineShapeAtomNonLitNodeConstraintContext extends InlineShapeAtomContext {
		public InlineNonLitNodeConstraintContext inlineNonLitNodeConstraint() {
			return getRuleContext(InlineNonLitNodeConstraintContext.class,0);
		}
		public InlineShapeOrRefContext inlineShapeOrRef() {
			return getRuleContext(InlineShapeOrRefContext.class,0);
		}
		public InlineShapeAtomNonLitNodeConstraintContext(InlineShapeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitInlineShapeAtomNonLitNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineShapeAtomContext inlineShapeAtom() throws RecognitionException {
		InlineShapeAtomContext _localctx = new InlineShapeAtomContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_inlineShapeAtom);
		int _la;
		try {
			setState(296);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new InlineShapeAtomNonLitNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				inlineNonLitNodeConstraint();
				setState(284);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(283);
					inlineShapeOrRef();
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new InlineShapeAtomLitNodeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(286);
				inlineLitNodeConstraint();
				}
				break;
			case 3:
				_localctx = new InlineShapeAtomShapeOrRefContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(287);
				inlineShapeOrRef();
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_IRI) | (1L << KW_NONLITERAL) | (1L << KW_BNODE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					setState(288);
					inlineNonLitNodeConstraint();
					}
				}

				}
				break;
			case 4:
				_localctx = new InlineShapeAtomShapeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(291);
				match(T__1);
				setState(292);
				shapeExpression();
				setState(293);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new InlineShapeAtomAnyContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(295);
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
		enterRule(_localctx, 40, RULE_shapeOrRef);
		try {
			setState(300);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__17:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(298);
				shapeDefinition();
				}
				break;
			case T__4:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(299);
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
		enterRule(_localctx, 42, RULE_inlineShapeOrRef);
		try {
			setState(304);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__17:
			case KW_EXTENDS:
			case KW_CLOSED:
			case KW_EXTRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				inlineShapeDefinition();
				}
				break;
			case T__4:
			case ATPNAME_NS:
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(303);
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
		enterRule(_localctx, 44, RULE_shapeRef);
		try {
			setState(310);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATPNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				match(ATPNAME_LN);
				}
				break;
			case ATPNAME_NS:
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
				match(ATPNAME_NS);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(308);
				match(T__4);
				setState(309);
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

	public static class InlineLitNodeConstraintContext extends ParserRuleContext {
		public InlineLitNodeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineLitNodeConstraint; }
	 
		public InlineLitNodeConstraintContext() { }
		public void copyFrom(InlineLitNodeConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NodeConstraintNumericFacetContext extends InlineLitNodeConstraintContext {
		public List<NumericFacetContext> numericFacet() {
			return getRuleContexts(NumericFacetContext.class);
		}
		public NumericFacetContext numericFacet(int i) {
			return getRuleContext(NumericFacetContext.class,i);
		}
		public NodeConstraintNumericFacetContext(InlineLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintNumericFacet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintLiteralContext extends InlineLitNodeConstraintContext {
		public TerminalNode KW_LITERAL() { return getToken(ShExDocParser.KW_LITERAL, 0); }
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintLiteralContext(InlineLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintNonLiteralContext extends InlineLitNodeConstraintContext {
		public NonLiteralKindContext nonLiteralKind() {
			return getRuleContext(NonLiteralKindContext.class,0);
		}
		public List<StringFacetContext> stringFacet() {
			return getRuleContexts(StringFacetContext.class);
		}
		public StringFacetContext stringFacet(int i) {
			return getRuleContext(StringFacetContext.class,i);
		}
		public NodeConstraintNonLiteralContext(InlineLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintNonLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintDatatypeContext extends InlineLitNodeConstraintContext {
		public DatatypeContext datatype() {
			return getRuleContext(DatatypeContext.class,0);
		}
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintDatatypeContext(InlineLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintDatatype(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NodeConstraintValueSetContext extends InlineLitNodeConstraintContext {
		public ValueSetContext valueSet() {
			return getRuleContext(ValueSetContext.class,0);
		}
		public List<XsFacetContext> xsFacet() {
			return getRuleContexts(XsFacetContext.class);
		}
		public XsFacetContext xsFacet(int i) {
			return getRuleContext(XsFacetContext.class,i);
		}
		public NodeConstraintValueSetContext(InlineLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNodeConstraintValueSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineLitNodeConstraintContext inlineLitNodeConstraint() throws RecognitionException {
		InlineLitNodeConstraintContext _localctx = new InlineLitNodeConstraintContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_inlineLitNodeConstraint);
		int _la;
		try {
			setState(345);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LITERAL:
				_localctx = new NodeConstraintLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(312);
				match(KW_LITERAL);
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(313);
					xsFacet();
					}
					}
					setState(318);
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
				setState(319);
				nonLiteralKind();
				setState(323);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(320);
					stringFacet();
					}
					}
					setState(325);
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
				setState(326);
				datatype();
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(327);
					xsFacet();
					}
					}
					setState(332);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__14:
				_localctx = new NodeConstraintValueSetContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(333);
				valueSet();
				setState(337);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(334);
					xsFacet();
					}
					}
					setState(339);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
				_localctx = new NodeConstraintNumericFacetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(341); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(340);
					numericFacet();
					}
					}
					setState(343); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_MININCLUSIVE) | (1L << KW_MINEXCLUSIVE) | (1L << KW_MAXINCLUSIVE) | (1L << KW_MAXEXCLUSIVE) | (1L << KW_TOTALDIGITS) | (1L << KW_FRACTIONDIGITS))) != 0) );
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

	public static class LitNodeConstraintContext extends ParserRuleContext {
		public InlineLitNodeConstraintContext inlineLitNodeConstraint() {
			return getRuleContext(InlineLitNodeConstraintContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
		}
		public LitNodeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_litNodeConstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLitNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LitNodeConstraintContext litNodeConstraint() throws RecognitionException {
		LitNodeConstraintContext _localctx = new LitNodeConstraintContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_litNodeConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			inlineLitNodeConstraint();
			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(348);
				annotation();
				}
				}
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(357);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(354);
				semanticAction();
				}
				}
				setState(359);
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

	public static class InlineNonLitNodeConstraintContext extends ParserRuleContext {
		public InlineNonLitNodeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineNonLitNodeConstraint; }
	 
		public InlineNonLitNodeConstraintContext() { }
		public void copyFrom(InlineNonLitNodeConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LitNodeConstraintStringFacetContext extends InlineNonLitNodeConstraintContext {
		public List<StringFacetContext> stringFacet() {
			return getRuleContexts(StringFacetContext.class);
		}
		public StringFacetContext stringFacet(int i) {
			return getRuleContext(StringFacetContext.class,i);
		}
		public LitNodeConstraintStringFacetContext(InlineNonLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLitNodeConstraintStringFacet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LitNodeConstraintLiteralContext extends InlineNonLitNodeConstraintContext {
		public NonLiteralKindContext nonLiteralKind() {
			return getRuleContext(NonLiteralKindContext.class,0);
		}
		public List<StringFacetContext> stringFacet() {
			return getRuleContexts(StringFacetContext.class);
		}
		public StringFacetContext stringFacet(int i) {
			return getRuleContext(StringFacetContext.class,i);
		}
		public LitNodeConstraintLiteralContext(InlineNonLitNodeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLitNodeConstraintLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineNonLitNodeConstraintContext inlineNonLitNodeConstraint() throws RecognitionException {
		InlineNonLitNodeConstraintContext _localctx = new InlineNonLitNodeConstraintContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inlineNonLitNodeConstraint);
		int _la;
		try {
			setState(372);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_IRI:
			case KW_NONLITERAL:
			case KW_BNODE:
				_localctx = new LitNodeConstraintLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(360);
				nonLiteralKind();
				setState(364);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0)) {
					{
					{
					setState(361);
					stringFacet();
					}
					}
					setState(366);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case REGEXP:
				_localctx = new LitNodeConstraintStringFacetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(368); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(367);
					stringFacet();
					}
					}
					setState(370); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KW_LENGTH) | (1L << KW_MINLENGTH) | (1L << KW_MAXLENGTH) | (1L << REGEXP))) != 0) );
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

	public static class NonLitNodeConstraintContext extends ParserRuleContext {
		public InlineNonLitNodeConstraintContext inlineNonLitNodeConstraint() {
			return getRuleContext(InlineNonLitNodeConstraintContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
		}
		public NonLitNodeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonLitNodeConstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitNonLitNodeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonLitNodeConstraintContext nonLitNodeConstraint() throws RecognitionException {
		NonLitNodeConstraintContext _localctx = new NonLitNodeConstraintContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_nonLitNodeConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			inlineNonLitNodeConstraint();
			setState(378);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(375);
				annotation();
				}
				}
				setState(380);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(384);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(381);
				semanticAction();
				}
				}
				setState(386);
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
		enterRule(_localctx, 54, RULE_nonLiteralKind);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
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
		enterRule(_localctx, 56, RULE_xsFacet);
		try {
			setState(391);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
			case REGEXP:
				enterOuterAlt(_localctx, 1);
				{
				setState(389);
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
				setState(390);
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
		enterRule(_localctx, 58, RULE_stringFacet);
		int _la;
		try {
			setState(400);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_LENGTH:
			case KW_MINLENGTH:
			case KW_MAXLENGTH:
				enterOuterAlt(_localctx, 1);
				{
				setState(393);
				stringLength();
				setState(394);
				match(INTEGER);
				}
				break;
			case REGEXP:
				enterOuterAlt(_localctx, 2);
				{
				setState(396);
				match(REGEXP);
				setState(398);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==REGEXP_FLAGS) {
					{
					setState(397);
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
		enterRule(_localctx, 60, RULE_stringLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
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
		public RawNumericContext rawNumeric() {
			return getRuleContext(RawNumericContext.class,0);
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
		enterRule(_localctx, 62, RULE_numericFacet);
		try {
			setState(410);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_MININCLUSIVE:
			case KW_MINEXCLUSIVE:
			case KW_MAXINCLUSIVE:
			case KW_MAXEXCLUSIVE:
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				numericRange();
				setState(405);
				rawNumeric();
				}
				break;
			case KW_TOTALDIGITS:
			case KW_FRACTIONDIGITS:
				enterOuterAlt(_localctx, 2);
				{
				setState(407);
				numericLength();
				setState(408);
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
		enterRule(_localctx, 64, RULE_numericRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
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
		enterRule(_localctx, 66, RULE_numericLength);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
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

	public static class RawNumericContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ShExDocParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(ShExDocParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(ShExDocParser.DOUBLE, 0); }
		public RawNumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rawNumeric; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRawNumeric(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RawNumericContext rawNumeric() throws RecognitionException {
		RawNumericContext _localctx = new RawNumericContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_rawNumeric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(416);
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

	public static class ShapeDefinitionContext extends ParserRuleContext {
		public List<QualifierContext> qualifier() {
			return getRuleContexts(QualifierContext.class);
		}
		public QualifierContext qualifier(int i) {
			return getRuleContext(QualifierContext.class,i);
		}
		public TripleExpressionContext tripleExpression() {
			return getRuleContext(TripleExpressionContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
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
		enterRule(_localctx, 70, RULE_shapeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(418);
				qualifier();
				}
				}
				setState(423);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(424);
			match(T__5);
			setState(426);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__9) | (1L << T__13) | (1L << T__17) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(425);
				tripleExpression();
				}
			}

			setState(428);
			match(T__6);
			setState(432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(429);
				annotation();
				}
				}
				setState(434);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(438);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(435);
				semanticAction();
				}
				}
				setState(440);
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

	public static class InlineShapeDefinitionContext extends ParserRuleContext {
		public List<QualifierContext> qualifier() {
			return getRuleContexts(QualifierContext.class);
		}
		public QualifierContext qualifier(int i) {
			return getRuleContext(QualifierContext.class,i);
		}
		public TripleExpressionContext tripleExpression() {
			return getRuleContext(TripleExpressionContext.class,0);
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
		enterRule(_localctx, 72, RULE_inlineShapeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << KW_EXTENDS) | (1L << KW_CLOSED) | (1L << KW_EXTRA))) != 0)) {
				{
				{
				setState(441);
				qualifier();
				}
				}
				setState(446);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(447);
			match(T__5);
			setState(449);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__9) | (1L << T__13) | (1L << T__17) | (1L << RDF_TYPE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN))) != 0)) {
				{
				setState(448);
				tripleExpression();
				}
			}

			setState(451);
			match(T__6);
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
		enterRule(_localctx, 74, RULE_qualifier);
		try {
			setState(456);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__17:
			case KW_EXTENDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(453);
				extension();
				}
				break;
			case KW_EXTRA:
				enterOuterAlt(_localctx, 2);
				{
				setState(454);
				extraPropertySet();
				}
				break;
			case KW_CLOSED:
				enterOuterAlt(_localctx, 3);
				{
				setState(455);
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
		enterRule(_localctx, 76, RULE_extraPropertySet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(458);
			match(KW_EXTRA);
			setState(460); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(459);
				predicate();
				}
				}
				setState(462); 
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

	public static class TripleExpressionContext extends ParserRuleContext {
		public OneOfTripleExprContext oneOfTripleExpr() {
			return getRuleContext(OneOfTripleExprContext.class,0);
		}
		public TripleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tripleExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitTripleExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TripleExpressionContext tripleExpression() throws RecognitionException {
		TripleExpressionContext _localctx = new TripleExpressionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_tripleExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(464);
			oneOfTripleExpr();
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

	public static class OneOfTripleExprContext extends ParserRuleContext {
		public GroupTripleExprContext groupTripleExpr() {
			return getRuleContext(GroupTripleExprContext.class,0);
		}
		public MultiElementOneOfContext multiElementOneOf() {
			return getRuleContext(MultiElementOneOfContext.class,0);
		}
		public OneOfTripleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oneOfTripleExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitOneOfTripleExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OneOfTripleExprContext oneOfTripleExpr() throws RecognitionException {
		OneOfTripleExprContext _localctx = new OneOfTripleExprContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_oneOfTripleExpr);
		try {
			setState(468);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(466);
				groupTripleExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(467);
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
		public List<GroupTripleExprContext> groupTripleExpr() {
			return getRuleContexts(GroupTripleExprContext.class);
		}
		public GroupTripleExprContext groupTripleExpr(int i) {
			return getRuleContext(GroupTripleExprContext.class,i);
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
		enterRule(_localctx, 82, RULE_multiElementOneOf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			groupTripleExpr();
			setState(473); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(471);
				match(T__7);
				setState(472);
				groupTripleExpr();
				}
				}
				setState(475); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 );
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

	public static class GroupTripleExprContext extends ParserRuleContext {
		public SingleElementGroupContext singleElementGroup() {
			return getRuleContext(SingleElementGroupContext.class,0);
		}
		public MultiElementGroupContext multiElementGroup() {
			return getRuleContext(MultiElementGroupContext.class,0);
		}
		public GroupTripleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupTripleExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitGroupTripleExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupTripleExprContext groupTripleExpr() throws RecognitionException {
		GroupTripleExprContext _localctx = new GroupTripleExprContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_groupTripleExpr);
		try {
			setState(479);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				singleElementGroup();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(478);
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
		public UnaryTripleExprContext unaryTripleExpr() {
			return getRuleContext(UnaryTripleExprContext.class,0);
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
		enterRule(_localctx, 86, RULE_singleElementGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			unaryTripleExpr();
			setState(483);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(482);
				match(T__8);
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
		public List<UnaryTripleExprContext> unaryTripleExpr() {
			return getRuleContexts(UnaryTripleExprContext.class);
		}
		public UnaryTripleExprContext unaryTripleExpr(int i) {
			return getRuleContext(UnaryTripleExprContext.class,i);
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
		enterRule(_localctx, 88, RULE_multiElementGroup);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			unaryTripleExpr();
			setState(488); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(486);
					match(T__8);
					setState(487);
					unaryTripleExpr();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(490); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(493);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(492);
				match(T__8);
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

	public static class UnaryTripleExprContext extends ParserRuleContext {
		public TripleConstraintContext tripleConstraint() {
			return getRuleContext(TripleConstraintContext.class,0);
		}
		public BracketedTripleExprContext bracketedTripleExpr() {
			return getRuleContext(BracketedTripleExprContext.class,0);
		}
		public TripleExprLabelContext tripleExprLabel() {
			return getRuleContext(TripleExprLabelContext.class,0);
		}
		public IncludeContext include() {
			return getRuleContext(IncludeContext.class,0);
		}
		public UnaryTripleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryTripleExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitUnaryTripleExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryTripleExprContext unaryTripleExpr() throws RecognitionException {
		UnaryTripleExprContext _localctx = new UnaryTripleExprContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_unaryTripleExpr);
		int _la;
		try {
			setState(504);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__9:
			case T__13:
			case RDF_TYPE:
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(497);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(495);
					match(T__9);
					setState(496);
					tripleExprLabel();
					}
				}

				setState(501);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__13:
				case RDF_TYPE:
				case IRIREF:
				case PNAME_NS:
				case PNAME_LN:
					{
					setState(499);
					tripleConstraint();
					}
					break;
				case T__1:
					{
					setState(500);
					bracketedTripleExpr();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(503);
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

	public static class BracketedTripleExprContext extends ParserRuleContext {
		public TripleExpressionContext tripleExpression() {
			return getRuleContext(TripleExpressionContext.class,0);
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
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
		}
		public BracketedTripleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketedTripleExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitBracketedTripleExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketedTripleExprContext bracketedTripleExpr() throws RecognitionException {
		BracketedTripleExprContext _localctx = new BracketedTripleExprContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_bracketedTripleExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(506);
			match(T__1);
			setState(507);
			tripleExpression();
			setState(508);
			match(T__2);
			setState(510);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 6)) & ~0x3f) == 0 && ((1L << (_la - 6)) & ((1L << (T__5 - 6)) | (1L << (T__10 - 6)) | (1L << (T__11 - 6)) | (1L << (UNBOUNDED - 6)))) != 0)) {
				{
				setState(509);
				cardinality();
				}
			}

			setState(515);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(512);
				annotation();
				}
				}
				setState(517);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(521);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(518);
				semanticAction();
				}
				}
				setState(523);
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

	public static class TripleConstraintContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public InlineShapeExpressionContext inlineShapeExpression() {
			return getRuleContext(InlineShapeExpressionContext.class,0);
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
		public List<SemanticActionContext> semanticAction() {
			return getRuleContexts(SemanticActionContext.class);
		}
		public SemanticActionContext semanticAction(int i) {
			return getRuleContext(SemanticActionContext.class,i);
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
		enterRule(_localctx, 94, RULE_tripleConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(525);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(524);
				senseFlags();
				}
			}

			setState(527);
			predicate();
			setState(528);
			inlineShapeExpression();
			setState(530);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 6)) & ~0x3f) == 0 && ((1L << (_la - 6)) & ((1L << (T__5 - 6)) | (1L << (T__10 - 6)) | (1L << (T__11 - 6)) | (1L << (UNBOUNDED - 6)))) != 0)) {
				{
				setState(529);
				cardinality();
				}
			}

			setState(535);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(532);
				annotation();
				}
				}
				setState(537);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(538);
				semanticAction();
				}
				}
				setState(543);
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
		enterRule(_localctx, 96, RULE_cardinality);
		try {
			setState(548);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNBOUNDED:
				_localctx = new StarCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(544);
				match(UNBOUNDED);
				}
				break;
			case T__10:
				_localctx = new PlusCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(545);
				match(T__10);
				}
				break;
			case T__11:
				_localctx = new OptionalCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(546);
				match(T__11);
				}
				break;
			case T__5:
				_localctx = new RepeatCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(547);
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
		enterRule(_localctx, 98, RULE_repeatRange);
		int _la;
		try {
			setState(560);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				_localctx = new ExactRangeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(550);
				match(T__5);
				setState(551);
				match(INTEGER);
				setState(552);
				match(T__6);
				}
				break;
			case 2:
				_localctx = new MinMaxRangeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(553);
				match(T__5);
				setState(554);
				match(INTEGER);
				setState(555);
				match(T__12);
				setState(557);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INTEGER || _la==UNBOUNDED) {
					{
					setState(556);
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

				setState(559);
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
		enterRule(_localctx, 100, RULE_senseFlags);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			match(T__13);
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
		enterRule(_localctx, 102, RULE_valueSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(564);
			match(T__14);
			setState(568);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << KW_TRUE) | (1L << KW_FALSE) | (1L << IRIREF) | (1L << PNAME_NS) | (1L << PNAME_LN) | (1L << LANGTAG))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (INTEGER - 64)) | (1L << (DECIMAL - 64)) | (1L << (DOUBLE - 64)) | (1L << (STRING_LITERAL1 - 64)) | (1L << (STRING_LITERAL2 - 64)) | (1L << (STRING_LITERAL_LONG1 - 64)) | (1L << (STRING_LITERAL_LONG2 - 64)))) != 0)) {
				{
				{
				setState(565);
				valueSetValue();
				}
				}
				setState(570);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(571);
			match(T__15);
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
		enterRule(_localctx, 104, RULE_valueSetValue);
		int _la;
		try {
			setState(594);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(573);
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
				setState(574);
				literalRange();
				}
				break;
			case T__4:
			case LANGTAG:
				enterOuterAlt(_localctx, 3);
				{
				setState(575);
				languageRange();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(576);
				match(T__3);
				setState(592);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
				case 1:
					{
					setState(578); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(577);
						iriExclusion();
						}
						}
						setState(580); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__16 );
					}
					break;
				case 2:
					{
					setState(583); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(582);
						literalExclusion();
						}
						}
						setState(585); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__16 );
					}
					break;
				case 3:
					{
					setState(588); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(587);
						languageExclusion();
						}
						}
						setState(590); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__16 );
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
		enterRule(_localctx, 106, RULE_iriRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(596);
			iri();
			setState(604);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(597);
				match(STEM_MARK);
				setState(601);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16) {
					{
					{
					setState(598);
					iriExclusion();
					}
					}
					setState(603);
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
		enterRule(_localctx, 108, RULE_iriExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(606);
			match(T__16);
			setState(607);
			iri();
			setState(609);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(608);
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
		enterRule(_localctx, 110, RULE_literalRange);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(611);
			literal();
			setState(619);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(612);
				match(STEM_MARK);
				setState(616);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16) {
					{
					{
					setState(613);
					literalExclusion();
					}
					}
					setState(618);
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
		enterRule(_localctx, 112, RULE_literalExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(621);
			match(T__16);
			setState(622);
			literal();
			setState(624);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(623);
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
		public LanguageRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_languageRange; }
	 
		public LanguageRangeContext() { }
		public void copyFrom(LanguageRangeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LanguageRangeFullContext extends LanguageRangeContext {
		public TerminalNode LANGTAG() { return getToken(ShExDocParser.LANGTAG, 0); }
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public List<LanguageExclusionContext> languageExclusion() {
			return getRuleContexts(LanguageExclusionContext.class);
		}
		public LanguageExclusionContext languageExclusion(int i) {
			return getRuleContext(LanguageExclusionContext.class,i);
		}
		public LanguageRangeFullContext(LanguageRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLanguageRangeFull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LanguageRangeAtContext extends LanguageRangeContext {
		public TerminalNode STEM_MARK() { return getToken(ShExDocParser.STEM_MARK, 0); }
		public List<LanguageExclusionContext> languageExclusion() {
			return getRuleContexts(LanguageExclusionContext.class);
		}
		public LanguageExclusionContext languageExclusion(int i) {
			return getRuleContext(LanguageExclusionContext.class,i);
		}
		public LanguageRangeAtContext(LanguageRangeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitLanguageRangeAt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LanguageRangeContext languageRange() throws RecognitionException {
		LanguageRangeContext _localctx = new LanguageRangeContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_languageRange);
		int _la;
		try {
			setState(644);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LANGTAG:
				_localctx = new LanguageRangeFullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(626);
				match(LANGTAG);
				setState(634);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STEM_MARK) {
					{
					setState(627);
					match(STEM_MARK);
					setState(631);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__16) {
						{
						{
						setState(628);
						languageExclusion();
						}
						}
						setState(633);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			case T__4:
				_localctx = new LanguageRangeAtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(636);
				match(T__4);
				setState(637);
				match(STEM_MARK);
				setState(641);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16) {
					{
					{
					setState(638);
					languageExclusion();
					}
					}
					setState(643);
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
		enterRule(_localctx, 116, RULE_languageExclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(646);
			match(T__16);
			setState(647);
			match(LANGTAG);
			setState(649);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STEM_MARK) {
				{
				setState(648);
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
		enterRule(_localctx, 118, RULE_include);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(651);
			match(T__17);
			setState(652);
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
		enterRule(_localctx, 120, RULE_annotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(654);
			match(T__18);
			setState(655);
			predicate();
			setState(658);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				{
				setState(656);
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
				setState(657);
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
		enterRule(_localctx, 122, RULE_semanticAction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(660);
			match(T__19);
			setState(661);
			iri();
			setState(662);
			_la = _input.LA(1);
			if ( !(_la==T__19 || _la==CODE) ) {
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
		enterRule(_localctx, 124, RULE_literal);
		try {
			setState(667);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LITERAL1:
			case STRING_LITERAL2:
			case STRING_LITERAL_LONG1:
			case STRING_LITERAL_LONG2:
				enterOuterAlt(_localctx, 1);
				{
				setState(664);
				rdfLiteral();
				}
				break;
			case INTEGER:
			case DECIMAL:
			case DOUBLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(665);
				numericLiteral();
				}
				break;
			case KW_TRUE:
			case KW_FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(666);
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
		enterRule(_localctx, 126, RULE_predicate);
		try {
			setState(671);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(669);
				iri();
				}
				break;
			case RDF_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(670);
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
		enterRule(_localctx, 128, RULE_rdfType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(673);
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
		enterRule(_localctx, 130, RULE_datatype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(675);
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
		enterRule(_localctx, 132, RULE_shapeExprLabel);
		try {
			setState(679);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(677);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(678);
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
		enterRule(_localctx, 134, RULE_tripleExprLabel);
		try {
			setState(683);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 1);
				{
				setState(681);
				iri();
				}
				break;
			case BLANK_NODE_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(682);
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
		enterRule(_localctx, 136, RULE_numericLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(685);
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
		public RdfStringContext rdfString() {
			return getRuleContext(RdfStringContext.class,0);
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
		enterRule(_localctx, 138, RULE_rdfLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(687);
			rdfString();
			setState(691);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
			case 1:
				{
				setState(688);
				match(LANGTAG);
				}
				break;
			case 2:
				{
				setState(689);
				match(T__20);
				setState(690);
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
		enterRule(_localctx, 140, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(693);
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

	public static class RdfStringContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL_LONG1() { return getToken(ShExDocParser.STRING_LITERAL_LONG1, 0); }
		public TerminalNode STRING_LITERAL_LONG2() { return getToken(ShExDocParser.STRING_LITERAL_LONG2, 0); }
		public TerminalNode STRING_LITERAL1() { return getToken(ShExDocParser.STRING_LITERAL1, 0); }
		public TerminalNode STRING_LITERAL2() { return getToken(ShExDocParser.STRING_LITERAL2, 0); }
		public RdfStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rdfString; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRdfString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RdfStringContext rdfString() throws RecognitionException {
		RdfStringContext _localctx = new RdfStringContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_rdfString);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(695);
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
		enterRule(_localctx, 144, RULE_iri);
		try {
			setState(699);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IRIREF:
				enterOuterAlt(_localctx, 1);
				{
				setState(697);
				match(IRIREF);
				}
				break;
			case PNAME_NS:
			case PNAME_LN:
				enterOuterAlt(_localctx, 2);
				{
				setState(698);
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
		enterRule(_localctx, 146, RULE_prefixedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(701);
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
		enterRule(_localctx, 148, RULE_blankNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
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
		enterRule(_localctx, 150, RULE_extension);
		try {
			setState(710);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_EXTENDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(705);
				match(KW_EXTENDS);
				setState(706);
				match(T__4);
				setState(707);
				shapeExprLabel();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(708);
				match(T__17);
				setState(709);
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

	public static class RestrictionContext extends ParserRuleContext {
		public TerminalNode KW_RESTRICTS() { return getToken(ShExDocParser.KW_RESTRICTS, 0); }
		public ShapeExprLabelContext shapeExprLabel() {
			return getRuleContext(ShapeExprLabelContext.class,0);
		}
		public RestrictionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_restriction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShExDocVisitor ) return ((ShExDocVisitor<? extends T>)visitor).visitRestriction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RestrictionContext restriction() throws RecognitionException {
		RestrictionContext _localctx = new RestrictionContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_restriction);
		try {
			setState(716);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_RESTRICTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(712);
				match(KW_RESTRICTS);
				setState(713);
				shapeExprLabel();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(714);
				match(T__16);
				setState(715);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3J\u02d1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\3\2\7\2\u009e\n\2\f\2\16\2\u00a1\13"+
		"\2\3\2\3\2\5\2\u00a5\n\2\3\2\7\2\u00a8\n\2\f\2\16\2\u00ab\13\2\5\2\u00ad"+
		"\n\2\3\2\3\2\3\3\3\3\3\3\5\3\u00b4\n\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\7\3\7\5\7\u00c2\n\7\3\b\3\b\3\b\3\b\3\t\6\t\u00c9\n\t\r\t\16"+
		"\t\u00ca\3\n\3\n\5\n\u00cf\n\n\3\13\5\13\u00d2\n\13\3\13\3\13\7\13\u00d6"+
		"\n\13\f\13\16\13\u00d9\13\13\3\13\3\13\5\13\u00dd\n\13\3\f\3\f\3\r\3\r"+
		"\3\16\3\16\3\16\7\16\u00e6\n\16\f\16\16\16\u00e9\13\16\3\17\3\17\3\17"+
		"\7\17\u00ee\n\17\f\17\16\17\u00f1\13\17\3\20\3\20\3\20\7\20\u00f6\n\20"+
		"\f\20\16\20\u00f9\13\20\3\21\3\21\3\21\7\21\u00fe\n\21\f\21\16\21\u0101"+
		"\13\21\3\22\5\22\u0104\n\22\3\22\3\22\3\23\5\23\u0109\n\23\3\23\3\23\3"+
		"\24\3\24\5\24\u010f\n\24\3\24\3\24\3\24\5\24\u0114\n\24\3\24\3\24\3\24"+
		"\3\24\3\24\5\24\u011b\n\24\3\25\3\25\5\25\u011f\n\25\3\25\3\25\3\25\5"+
		"\25\u0124\n\25\3\25\3\25\3\25\3\25\3\25\5\25\u012b\n\25\3\26\3\26\5\26"+
		"\u012f\n\26\3\27\3\27\5\27\u0133\n\27\3\30\3\30\3\30\3\30\5\30\u0139\n"+
		"\30\3\31\3\31\7\31\u013d\n\31\f\31\16\31\u0140\13\31\3\31\3\31\7\31\u0144"+
		"\n\31\f\31\16\31\u0147\13\31\3\31\3\31\7\31\u014b\n\31\f\31\16\31\u014e"+
		"\13\31\3\31\3\31\7\31\u0152\n\31\f\31\16\31\u0155\13\31\3\31\6\31\u0158"+
		"\n\31\r\31\16\31\u0159\5\31\u015c\n\31\3\32\3\32\7\32\u0160\n\32\f\32"+
		"\16\32\u0163\13\32\3\32\7\32\u0166\n\32\f\32\16\32\u0169\13\32\3\33\3"+
		"\33\7\33\u016d\n\33\f\33\16\33\u0170\13\33\3\33\6\33\u0173\n\33\r\33\16"+
		"\33\u0174\5\33\u0177\n\33\3\34\3\34\7\34\u017b\n\34\f\34\16\34\u017e\13"+
		"\34\3\34\7\34\u0181\n\34\f\34\16\34\u0184\13\34\3\35\3\35\3\36\3\36\5"+
		"\36\u018a\n\36\3\37\3\37\3\37\3\37\3\37\5\37\u0191\n\37\5\37\u0193\n\37"+
		"\3 \3 \3!\3!\3!\3!\3!\3!\5!\u019d\n!\3\"\3\"\3#\3#\3$\3$\3%\7%\u01a6\n"+
		"%\f%\16%\u01a9\13%\3%\3%\5%\u01ad\n%\3%\3%\7%\u01b1\n%\f%\16%\u01b4\13"+
		"%\3%\7%\u01b7\n%\f%\16%\u01ba\13%\3&\7&\u01bd\n&\f&\16&\u01c0\13&\3&\3"+
		"&\5&\u01c4\n&\3&\3&\3\'\3\'\3\'\5\'\u01cb\n\'\3(\3(\6(\u01cf\n(\r(\16"+
		"(\u01d0\3)\3)\3*\3*\5*\u01d7\n*\3+\3+\3+\6+\u01dc\n+\r+\16+\u01dd\3,\3"+
		",\5,\u01e2\n,\3-\3-\5-\u01e6\n-\3.\3.\3.\6.\u01eb\n.\r.\16.\u01ec\3.\5"+
		".\u01f0\n.\3/\3/\5/\u01f4\n/\3/\3/\5/\u01f8\n/\3/\5/\u01fb\n/\3\60\3\60"+
		"\3\60\3\60\5\60\u0201\n\60\3\60\7\60\u0204\n\60\f\60\16\60\u0207\13\60"+
		"\3\60\7\60\u020a\n\60\f\60\16\60\u020d\13\60\3\61\5\61\u0210\n\61\3\61"+
		"\3\61\3\61\5\61\u0215\n\61\3\61\7\61\u0218\n\61\f\61\16\61\u021b\13\61"+
		"\3\61\7\61\u021e\n\61\f\61\16\61\u0221\13\61\3\62\3\62\3\62\3\62\5\62"+
		"\u0227\n\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\5\63\u0230\n\63\3\63\5"+
		"\63\u0233\n\63\3\64\3\64\3\65\3\65\7\65\u0239\n\65\f\65\16\65\u023c\13"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\6\66\u0245\n\66\r\66\16\66\u0246"+
		"\3\66\6\66\u024a\n\66\r\66\16\66\u024b\3\66\6\66\u024f\n\66\r\66\16\66"+
		"\u0250\5\66\u0253\n\66\5\66\u0255\n\66\3\67\3\67\3\67\7\67\u025a\n\67"+
		"\f\67\16\67\u025d\13\67\5\67\u025f\n\67\38\38\38\58\u0264\n8\39\39\39"+
		"\79\u0269\n9\f9\169\u026c\139\59\u026e\n9\3:\3:\3:\5:\u0273\n:\3;\3;\3"+
		";\7;\u0278\n;\f;\16;\u027b\13;\5;\u027d\n;\3;\3;\3;\7;\u0282\n;\f;\16"+
		";\u0285\13;\5;\u0287\n;\3<\3<\3<\5<\u028c\n<\3=\3=\3=\3>\3>\3>\3>\5>\u0295"+
		"\n>\3?\3?\3?\3?\3@\3@\3@\5@\u029e\n@\3A\3A\5A\u02a2\nA\3B\3B\3C\3C\3D"+
		"\3D\5D\u02aa\nD\3E\3E\5E\u02ae\nE\3F\3F\3G\3G\3G\3G\5G\u02b6\nG\3H\3H"+
		"\3I\3I\3J\3J\5J\u02be\nJ\3K\3K\3L\3L\3M\3M\3M\3M\3M\5M\u02c9\nM\3N\3N"+
		"\3N\3N\5N\u02cf\nN\3N\2\2O\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$"+
		"&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\2\f"+
		"\3\2$&\3\2-/\3\2),\3\2\60\61\3\2BD\4\2BBFF\4\2\26\26\67\67\3\2\63\64\3"+
		"\2GJ\3\2:;\2\u02f6\2\u009f\3\2\2\2\4\u00b3\3\2\2\2\6\u00b5\3\2\2\2\b\u00b8"+
		"\3\2\2\2\n\u00bc\3\2\2\2\f\u00c1\3\2\2\2\16\u00c3\3\2\2\2\20\u00c8\3\2"+
		"\2\2\22\u00ce\3\2\2\2\24\u00d1\3\2\2\2\26\u00de\3\2\2\2\30\u00e0\3\2\2"+
		"\2\32\u00e2\3\2\2\2\34\u00ea\3\2\2\2\36\u00f2\3\2\2\2 \u00fa\3\2\2\2\""+
		"\u0103\3\2\2\2$\u0108\3\2\2\2&\u011a\3\2\2\2(\u012a\3\2\2\2*\u012e\3\2"+
		"\2\2,\u0132\3\2\2\2.\u0138\3\2\2\2\60\u015b\3\2\2\2\62\u015d\3\2\2\2\64"+
		"\u0176\3\2\2\2\66\u0178\3\2\2\28\u0185\3\2\2\2:\u0189\3\2\2\2<\u0192\3"+
		"\2\2\2>\u0194\3\2\2\2@\u019c\3\2\2\2B\u019e\3\2\2\2D\u01a0\3\2\2\2F\u01a2"+
		"\3\2\2\2H\u01a7\3\2\2\2J\u01be\3\2\2\2L\u01ca\3\2\2\2N\u01cc\3\2\2\2P"+
		"\u01d2\3\2\2\2R\u01d6\3\2\2\2T\u01d8\3\2\2\2V\u01e1\3\2\2\2X\u01e3\3\2"+
		"\2\2Z\u01e7\3\2\2\2\\\u01fa\3\2\2\2^\u01fc\3\2\2\2`\u020f\3\2\2\2b\u0226"+
		"\3\2\2\2d\u0232\3\2\2\2f\u0234\3\2\2\2h\u0236\3\2\2\2j\u0254\3\2\2\2l"+
		"\u0256\3\2\2\2n\u0260\3\2\2\2p\u0265\3\2\2\2r\u026f\3\2\2\2t\u0286\3\2"+
		"\2\2v\u0288\3\2\2\2x\u028d\3\2\2\2z\u0290\3\2\2\2|\u0296\3\2\2\2~\u029d"+
		"\3\2\2\2\u0080\u02a1\3\2\2\2\u0082\u02a3\3\2\2\2\u0084\u02a5\3\2\2\2\u0086"+
		"\u02a9\3\2\2\2\u0088\u02ad\3\2\2\2\u008a\u02af\3\2\2\2\u008c\u02b1\3\2"+
		"\2\2\u008e\u02b7\3\2\2\2\u0090\u02b9\3\2\2\2\u0092\u02bd\3\2\2\2\u0094"+
		"\u02bf\3\2\2\2\u0096\u02c1\3\2\2\2\u0098\u02c8\3\2\2\2\u009a\u02ce\3\2"+
		"\2\2\u009c\u009e\5\4\3\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00ac\3\2\2\2\u00a1\u009f\3\2"+
		"\2\2\u00a2\u00a5\5\f\7\2\u00a3\u00a5\5\20\t\2\u00a4\u00a2\3\2\2\2\u00a4"+
		"\u00a3\3\2\2\2\u00a5\u00a9\3\2\2\2\u00a6\u00a8\5\22\n\2\u00a7\u00a6\3"+
		"\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa"+
		"\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00a4\3\2\2\2\u00ac\u00ad\3\2"+
		"\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\7\2\2\3\u00af\3\3\2\2\2\u00b0\u00b4"+
		"\5\6\4\2\u00b1\u00b4\5\b\5\2\u00b2\u00b4\5\n\6\2\u00b3\u00b0\3\2\2\2\u00b3"+
		"\u00b1\3\2\2\2\u00b3\u00b2\3\2\2\2\u00b4\5\3\2\2\2\u00b5\u00b6\7\31\2"+
		"\2\u00b6\u00b7\79\2\2\u00b7\7\3\2\2\2\u00b8\u00b9\7\36\2\2\u00b9\u00ba"+
		"\7:\2\2\u00ba\u00bb\79\2\2\u00bb\t\3\2\2\2\u00bc\u00bd\7\33\2\2\u00bd"+
		"\u00be\79\2\2\u00be\13\3\2\2\2\u00bf\u00c2\5\16\b\2\u00c0\u00c2\5\24\13"+
		"\2\u00c1\u00bf\3\2\2\2\u00c1\u00c0\3\2\2\2\u00c2\r\3\2\2\2\u00c3\u00c4"+
		"\7\37\2\2\u00c4\u00c5\7\3\2\2\u00c5\u00c6\5\26\f\2\u00c6\17\3\2\2\2\u00c7"+
		"\u00c9\5|?\2\u00c8\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00c8\3\2\2"+
		"\2\u00ca\u00cb\3\2\2\2\u00cb\21\3\2\2\2\u00cc\u00cf\5\4\3\2\u00cd\u00cf"+
		"\5\f\7\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\23\3\2\2\2\u00d0"+
		"\u00d2\7\30\2\2\u00d1\u00d0\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\3"+
		"\2\2\2\u00d3\u00d7\5\u0086D\2\u00d4\u00d6\5\u009aN\2\u00d5\u00d4\3\2\2"+
		"\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00dc"+
		"\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00dd\5\26\f\2\u00db\u00dd\7\35\2\2"+
		"\u00dc\u00da\3\2\2\2\u00dc\u00db\3\2\2\2\u00dd\25\3\2\2\2\u00de\u00df"+
		"\5\32\16\2\u00df\27\3\2\2\2\u00e0\u00e1\5\34\17\2\u00e1\31\3\2\2\2\u00e2"+
		"\u00e7\5\36\20\2\u00e3\u00e4\7(\2\2\u00e4\u00e6\5\36\20\2\u00e5\u00e3"+
		"\3\2\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"\33\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00ef\5 \21\2\u00eb\u00ec\7(\2\2"+
		"\u00ec\u00ee\5 \21\2\u00ed\u00eb\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed"+
		"\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\35\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2"+
		"\u00f7\5\"\22\2\u00f3\u00f4\7\'\2\2\u00f4\u00f6\5\"\22\2\u00f5\u00f3\3"+
		"\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"\37\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00ff\5$\23\2\u00fb\u00fc\7\'\2"+
		"\2\u00fc\u00fe\5$\23\2\u00fd\u00fb\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd"+
		"\3\2\2\2\u00ff\u0100\3\2\2\2\u0100!\3\2\2\2\u0101\u00ff\3\2\2\2\u0102"+
		"\u0104\7\62\2\2\u0103\u0102\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\3"+
		"\2\2\2\u0105\u0106\5&\24\2\u0106#\3\2\2\2\u0107\u0109\7\62\2\2\u0108\u0107"+
		"\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\5(\25\2\u010b"+
		"%\3\2\2\2\u010c\u010e\5\66\34\2\u010d\u010f\5*\26\2\u010e\u010d\3\2\2"+
		"\2\u010e\u010f\3\2\2\2\u010f\u011b\3\2\2\2\u0110\u011b\5\62\32\2\u0111"+
		"\u0113\5*\26\2\u0112\u0114\5\66\34\2\u0113\u0112\3\2\2\2\u0113\u0114\3"+
		"\2\2\2\u0114\u011b\3\2\2\2\u0115\u0116\7\4\2\2\u0116\u0117\5\26\f\2\u0117"+
		"\u0118\7\5\2\2\u0118\u011b\3\2\2\2\u0119\u011b\7\6\2\2\u011a\u010c\3\2"+
		"\2\2\u011a\u0110\3\2\2\2\u011a\u0111\3\2\2\2\u011a\u0115\3\2\2\2\u011a"+
		"\u0119\3\2\2\2\u011b\'\3\2\2\2\u011c\u011e\5\64\33\2\u011d\u011f\5,\27"+
		"\2\u011e\u011d\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u012b\3\2\2\2\u0120\u012b"+
		"\5\60\31\2\u0121\u0123\5,\27\2\u0122\u0124\5\64\33\2\u0123\u0122\3\2\2"+
		"\2\u0123\u0124\3\2\2\2\u0124\u012b\3\2\2\2\u0125\u0126\7\4\2\2\u0126\u0127"+
		"\5\26\f\2\u0127\u0128\7\5\2\2\u0128\u012b\3\2\2\2\u0129\u012b\7\6\2\2"+
		"\u012a\u011c\3\2\2\2\u012a\u0120\3\2\2\2\u012a\u0121\3\2\2\2\u012a\u0125"+
		"\3\2\2\2\u012a\u0129\3\2\2\2\u012b)\3\2\2\2\u012c\u012f\5H%\2\u012d\u012f"+
		"\5.\30\2\u012e\u012c\3\2\2\2\u012e\u012d\3\2\2\2\u012f+\3\2\2\2\u0130"+
		"\u0133\5J&\2\u0131\u0133\5.\30\2\u0132\u0130\3\2\2\2\u0132\u0131\3\2\2"+
		"\2\u0133-\3\2\2\2\u0134\u0139\7=\2\2\u0135\u0139\7<\2\2\u0136\u0137\7"+
		"\7\2\2\u0137\u0139\5\u0086D\2\u0138\u0134\3\2\2\2\u0138\u0135\3\2\2\2"+
		"\u0138\u0136\3\2\2\2\u0139/\3\2\2\2\u013a\u013e\7#\2\2\u013b\u013d\5:"+
		"\36\2\u013c\u013b\3\2\2\2\u013d\u0140\3\2\2\2\u013e\u013c\3\2\2\2\u013e"+
		"\u013f\3\2\2\2\u013f\u015c\3\2\2\2\u0140\u013e\3\2\2\2\u0141\u0145\58"+
		"\35\2\u0142\u0144\5<\37\2\u0143\u0142\3\2\2\2\u0144\u0147\3\2\2\2\u0145"+
		"\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u015c\3\2\2\2\u0147\u0145\3\2"+
		"\2\2\u0148\u014c\5\u0084C\2\u0149\u014b\5:\36\2\u014a\u0149\3\2\2\2\u014b"+
		"\u014e\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u015c\3\2"+
		"\2\2\u014e\u014c\3\2\2\2\u014f\u0153\5h\65\2\u0150\u0152\5:\36\2\u0151"+
		"\u0150\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154\3\2"+
		"\2\2\u0154\u015c\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u0158\5@!\2\u0157\u0156"+
		"\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a"+
		"\u015c\3\2\2\2\u015b\u013a\3\2\2\2\u015b\u0141\3\2\2\2\u015b\u0148\3\2"+
		"\2\2\u015b\u014f\3\2\2\2\u015b\u0157\3\2\2\2\u015c\61\3\2\2\2\u015d\u0161"+
		"\5\60\31\2\u015e\u0160\5z>\2\u015f\u015e\3\2\2\2\u0160\u0163\3\2\2\2\u0161"+
		"\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0167\3\2\2\2\u0163\u0161\3\2"+
		"\2\2\u0164\u0166\5|?\2\u0165\u0164\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0165"+
		"\3\2\2\2\u0167\u0168\3\2\2\2\u0168\63\3\2\2\2\u0169\u0167\3\2\2\2\u016a"+
		"\u016e\58\35\2\u016b\u016d\5<\37\2\u016c\u016b\3\2\2\2\u016d\u0170\3\2"+
		"\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u0177\3\2\2\2\u0170"+
		"\u016e\3\2\2\2\u0171\u0173\5<\37\2\u0172\u0171\3\2\2\2\u0173\u0174\3\2"+
		"\2\2\u0174\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0177\3\2\2\2\u0176"+
		"\u016a\3\2\2\2\u0176\u0172\3\2\2\2\u0177\65\3\2\2\2\u0178\u017c\5\64\33"+
		"\2\u0179\u017b\5z>\2\u017a\u0179\3\2\2\2\u017b\u017e\3\2\2\2\u017c\u017a"+
		"\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u0182\3\2\2\2\u017e\u017c\3\2\2\2\u017f"+
		"\u0181\5|?\2\u0180\u017f\3\2\2\2\u0181\u0184\3\2\2\2\u0182\u0180\3\2\2"+
		"\2\u0182\u0183\3\2\2\2\u0183\67\3\2\2\2\u0184\u0182\3\2\2\2\u0185\u0186"+
		"\t\2\2\2\u01869\3\2\2\2\u0187\u018a\5<\37\2\u0188\u018a\5@!\2\u0189\u0187"+
		"\3\2\2\2\u0189\u0188\3\2\2\2\u018a;\3\2\2\2\u018b\u018c\5> \2\u018c\u018d"+
		"\7B\2\2\u018d\u0193\3\2\2\2\u018e\u0190\7>\2\2\u018f\u0191\7?\2\2\u0190"+
		"\u018f\3\2\2\2\u0190\u0191\3\2\2\2\u0191\u0193\3\2\2\2\u0192\u018b\3\2"+
		"\2\2\u0192\u018e\3\2\2\2\u0193=\3\2\2\2\u0194\u0195\t\3\2\2\u0195?\3\2"+
		"\2\2\u0196\u0197\5B\"\2\u0197\u0198\5F$\2\u0198\u019d\3\2\2\2\u0199\u019a"+
		"\5D#\2\u019a\u019b\7B\2\2\u019b\u019d\3\2\2\2\u019c\u0196\3\2\2\2\u019c"+
		"\u0199\3\2\2\2\u019dA\3\2\2\2\u019e\u019f\t\4\2\2\u019fC\3\2\2\2\u01a0"+
		"\u01a1\t\5\2\2\u01a1E\3\2\2\2\u01a2\u01a3\t\6\2\2\u01a3G\3\2\2\2\u01a4"+
		"\u01a6\5L\'\2\u01a5\u01a4\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2"+
		"\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01aa\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aa"+
		"\u01ac\7\b\2\2\u01ab\u01ad\5P)\2\u01ac\u01ab\3\2\2\2\u01ac\u01ad\3\2\2"+
		"\2\u01ad\u01ae\3\2\2\2\u01ae\u01b2\7\t\2\2\u01af\u01b1\5z>\2\u01b0\u01af"+
		"\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3"+
		"\u01b8\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01b7\5|?\2\u01b6\u01b5\3\2\2"+
		"\2\u01b7\u01ba\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3\2\2\2\u01b9I"+
		"\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bd\5L\'\2\u01bc\u01bb\3\2\2\2\u01bd"+
		"\u01c0\3\2\2\2\u01be\u01bc\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c1\3\2"+
		"\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c3\7\b\2\2\u01c2\u01c4\5P)\2\u01c3\u01c2"+
		"\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01c6\7\t\2\2\u01c6"+
		"K\3\2\2\2\u01c7\u01cb\5\u0098M\2\u01c8\u01cb\5N(\2\u01c9\u01cb\7!\2\2"+
		"\u01ca\u01c7\3\2\2\2\u01ca\u01c8\3\2\2\2\u01ca\u01c9\3\2\2\2\u01cbM\3"+
		"\2\2\2\u01cc\u01ce\7\"\2\2\u01cd\u01cf\5\u0080A\2\u01ce\u01cd\3\2\2\2"+
		"\u01cf\u01d0\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1O\3"+
		"\2\2\2\u01d2\u01d3\5R*\2\u01d3Q\3\2\2\2\u01d4\u01d7\5V,\2\u01d5\u01d7"+
		"\5T+\2\u01d6\u01d4\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7S\3\2\2\2\u01d8\u01db"+
		"\5V,\2\u01d9\u01da\7\n\2\2\u01da\u01dc\5V,\2\u01db\u01d9\3\2\2\2\u01dc"+
		"\u01dd\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01deU\3\2\2\2"+
		"\u01df\u01e2\5X-\2\u01e0\u01e2\5Z.\2\u01e1\u01df\3\2\2\2\u01e1\u01e0\3"+
		"\2\2\2\u01e2W\3\2\2\2\u01e3\u01e5\5\\/\2\u01e4\u01e6\7\13\2\2\u01e5\u01e4"+
		"\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6Y\3\2\2\2\u01e7\u01ea\5\\/\2\u01e8\u01e9"+
		"\7\13\2\2\u01e9\u01eb\5\\/\2\u01ea\u01e8\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec"+
		"\u01ea\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01ef\3\2\2\2\u01ee\u01f0\7\13"+
		"\2\2\u01ef\u01ee\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0[\3\2\2\2\u01f1\u01f2"+
		"\7\f\2\2\u01f2\u01f4\5\u0088E\2\u01f3\u01f1\3\2\2\2\u01f3\u01f4\3\2\2"+
		"\2\u01f4\u01f7\3\2\2\2\u01f5\u01f8\5`\61\2\u01f6\u01f8\5^\60\2\u01f7\u01f5"+
		"\3\2\2\2\u01f7\u01f6\3\2\2\2\u01f8\u01fb\3\2\2\2\u01f9\u01fb\5x=\2\u01fa"+
		"\u01f3\3\2\2\2\u01fa\u01f9\3\2\2\2\u01fb]\3\2\2\2\u01fc\u01fd\7\4\2\2"+
		"\u01fd\u01fe\5P)\2\u01fe\u0200\7\5\2\2\u01ff\u0201\5b\62\2\u0200\u01ff"+
		"\3\2\2\2\u0200\u0201\3\2\2\2\u0201\u0205\3\2\2\2\u0202\u0204\5z>\2\u0203"+
		"\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0203\3\2\2\2\u0205\u0206\3\2"+
		"\2\2\u0206\u020b\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u020a\5|?\2\u0209\u0208"+
		"\3\2\2\2\u020a\u020d\3\2\2\2\u020b\u0209\3\2\2\2\u020b\u020c\3\2\2\2\u020c"+
		"_\3\2\2\2\u020d\u020b\3\2\2\2\u020e\u0210\5f\64\2\u020f\u020e\3\2\2\2"+
		"\u020f\u0210\3\2\2\2\u0210\u0211\3\2\2\2\u0211\u0212\5\u0080A\2\u0212"+
		"\u0214\5\30\r\2\u0213\u0215\5b\62\2\u0214\u0213\3\2\2\2\u0214\u0215\3"+
		"\2\2\2\u0215\u0219\3\2\2\2\u0216\u0218\5z>\2\u0217\u0216\3\2\2\2\u0218"+
		"\u021b\3\2\2\2\u0219\u0217\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u021f\3\2"+
		"\2\2\u021b\u0219\3\2\2\2\u021c\u021e\5|?\2\u021d\u021c\3\2\2\2\u021e\u0221"+
		"\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220a\3\2\2\2\u0221"+
		"\u021f\3\2\2\2\u0222\u0227\7F\2\2\u0223\u0227\7\r\2\2\u0224\u0227\7\16"+
		"\2\2\u0225\u0227\5d\63\2\u0226\u0222\3\2\2\2\u0226\u0223\3\2\2\2\u0226"+
		"\u0224\3\2\2\2\u0226\u0225\3\2\2\2\u0227c\3\2\2\2\u0228\u0229\7\b\2\2"+
		"\u0229\u022a\7B\2\2\u022a\u0233\7\t\2\2\u022b\u022c\7\b\2\2\u022c\u022d"+
		"\7B\2\2\u022d\u022f\7\17\2\2\u022e\u0230\t\7\2\2\u022f\u022e\3\2\2\2\u022f"+
		"\u0230\3\2\2\2\u0230\u0231\3\2\2\2\u0231\u0233\7\t\2\2\u0232\u0228\3\2"+
		"\2\2\u0232\u022b\3\2\2\2\u0233e\3\2\2\2\u0234\u0235\7\20\2\2\u0235g\3"+
		"\2\2\2\u0236\u023a\7\21\2\2\u0237\u0239\5j\66\2\u0238\u0237\3\2\2\2\u0239"+
		"\u023c\3\2\2\2\u023a\u0238\3\2\2\2\u023a\u023b\3\2\2\2\u023b\u023d\3\2"+
		"\2\2\u023c\u023a\3\2\2\2\u023d\u023e\7\22\2\2\u023ei\3\2\2\2\u023f\u0255"+
		"\5l\67\2\u0240\u0255\5p9\2\u0241\u0255\5t;\2\u0242\u0252\7\6\2\2\u0243"+
		"\u0245\5n8\2\u0244\u0243\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0244\3\2\2"+
		"\2\u0246\u0247\3\2\2\2\u0247\u0253\3\2\2\2\u0248\u024a\5r:\2\u0249\u0248"+
		"\3\2\2\2\u024a\u024b\3\2\2\2\u024b\u0249\3\2\2\2\u024b\u024c\3\2\2\2\u024c"+
		"\u0253\3\2\2\2\u024d\u024f\5v<\2\u024e\u024d\3\2\2\2\u024f\u0250\3\2\2"+
		"\2\u0250\u024e\3\2\2\2\u0250\u0251\3\2\2\2\u0251\u0253\3\2\2\2\u0252\u0244"+
		"\3\2\2\2\u0252\u0249\3\2\2\2\u0252\u024e\3\2\2\2\u0253\u0255\3\2\2\2\u0254"+
		"\u023f\3\2\2\2\u0254\u0240\3\2\2\2\u0254\u0241\3\2\2\2\u0254\u0242\3\2"+
		"\2\2\u0255k\3\2\2\2\u0256\u025e\5\u0092J\2\u0257\u025b\7E\2\2\u0258\u025a"+
		"\5n8\2\u0259\u0258\3\2\2\2\u025a\u025d\3\2\2\2\u025b\u0259\3\2\2\2\u025b"+
		"\u025c\3\2\2\2\u025c\u025f\3\2\2\2\u025d\u025b\3\2\2\2\u025e\u0257\3\2"+
		"\2\2\u025e\u025f\3\2\2\2\u025fm\3\2\2\2\u0260\u0261\7\23\2\2\u0261\u0263"+
		"\5\u0092J\2\u0262\u0264\7E\2\2\u0263\u0262\3\2\2\2\u0263\u0264\3\2\2\2"+
		"\u0264o\3\2\2\2\u0265\u026d\5~@\2\u0266\u026a\7E\2\2\u0267\u0269\5r:\2"+
		"\u0268\u0267\3\2\2\2\u0269\u026c\3\2\2\2\u026a\u0268\3\2\2\2\u026a\u026b"+
		"\3\2\2\2\u026b\u026e\3\2\2\2\u026c\u026a\3\2\2\2\u026d\u0266\3\2\2\2\u026d"+
		"\u026e\3\2\2\2\u026eq\3\2\2\2\u026f\u0270\7\23\2\2\u0270\u0272\5~@\2\u0271"+
		"\u0273\7E\2\2\u0272\u0271\3\2\2\2\u0272\u0273\3\2\2\2\u0273s\3\2\2\2\u0274"+
		"\u027c\7A\2\2\u0275\u0279\7E\2\2\u0276\u0278\5v<\2\u0277\u0276\3\2\2\2"+
		"\u0278\u027b\3\2\2\2\u0279\u0277\3\2\2\2\u0279\u027a\3\2\2\2\u027a\u027d"+
		"\3\2\2\2\u027b\u0279\3\2\2\2\u027c\u0275\3\2\2\2\u027c\u027d\3\2\2\2\u027d"+
		"\u0287\3\2\2\2\u027e\u027f\7\7\2\2\u027f\u0283\7E\2\2\u0280\u0282\5v<"+
		"\2\u0281\u0280\3\2\2\2\u0282\u0285\3\2\2\2\u0283\u0281\3\2\2\2\u0283\u0284"+
		"\3\2\2\2\u0284\u0287\3\2\2\2\u0285\u0283\3\2\2\2\u0286\u0274\3\2\2\2\u0286"+
		"\u027e\3\2\2\2\u0287u\3\2\2\2\u0288\u0289\7\23\2\2\u0289\u028b\7A\2\2"+
		"\u028a\u028c\7E\2\2\u028b\u028a\3\2\2\2\u028b\u028c\3\2\2\2\u028cw\3\2"+
		"\2\2\u028d\u028e\7\24\2\2\u028e\u028f\5\u0088E\2\u028fy\3\2\2\2\u0290"+
		"\u0291\7\25\2\2\u0291\u0294\5\u0080A\2\u0292\u0295\5\u0092J\2\u0293\u0295"+
		"\5~@\2\u0294\u0292\3\2\2\2\u0294\u0293\3\2\2\2\u0295{\3\2\2\2\u0296\u0297"+
		"\7\26\2\2\u0297\u0298\5\u0092J\2\u0298\u0299\t\b\2\2\u0299}\3\2\2\2\u029a"+
		"\u029e\5\u008cG\2\u029b\u029e\5\u008aF\2\u029c\u029e\5\u008eH\2\u029d"+
		"\u029a\3\2\2\2\u029d\u029b\3\2\2\2\u029d\u029c\3\2\2\2\u029e\177\3\2\2"+
		"\2\u029f\u02a2\5\u0092J\2\u02a0\u02a2\5\u0082B\2\u02a1\u029f\3\2\2\2\u02a1"+
		"\u02a0\3\2\2\2\u02a2\u0081\3\2\2\2\u02a3\u02a4\78\2\2\u02a4\u0083\3\2"+
		"\2\2\u02a5\u02a6\5\u0092J\2\u02a6\u0085\3\2\2\2\u02a7\u02aa\5\u0092J\2"+
		"\u02a8\u02aa\5\u0096L\2\u02a9\u02a7\3\2\2\2\u02a9\u02a8\3\2\2\2\u02aa"+
		"\u0087\3\2\2\2\u02ab\u02ae\5\u0092J\2\u02ac\u02ae\5\u0096L\2\u02ad\u02ab"+
		"\3\2\2\2\u02ad\u02ac\3\2\2\2\u02ae\u0089\3\2\2\2\u02af\u02b0\t\6\2\2\u02b0"+
		"\u008b\3\2\2\2\u02b1\u02b5\5\u0090I\2\u02b2\u02b6\7A\2\2\u02b3\u02b4\7"+
		"\27\2\2\u02b4\u02b6\5\u0084C\2\u02b5\u02b2\3\2\2\2\u02b5\u02b3\3\2\2\2"+
		"\u02b5\u02b6\3\2\2\2\u02b6\u008d\3\2\2\2\u02b7\u02b8\t\t\2\2\u02b8\u008f"+
		"\3\2\2\2\u02b9\u02ba\t\n\2\2\u02ba\u0091\3\2\2\2\u02bb\u02be\79\2\2\u02bc"+
		"\u02be\5\u0094K\2\u02bd\u02bb\3\2\2\2\u02bd\u02bc\3\2\2\2\u02be\u0093"+
		"\3\2\2\2\u02bf\u02c0\t\13\2\2\u02c0\u0095\3\2\2\2\u02c1\u02c2\7@\2\2\u02c2"+
		"\u0097\3\2\2\2\u02c3\u02c4\7\32\2\2\u02c4\u02c5\7\7\2\2\u02c5\u02c9\5"+
		"\u0086D\2\u02c6\u02c7\7\24\2\2\u02c7\u02c9\5\u0086D\2\u02c8\u02c3\3\2"+
		"\2\2\u02c8\u02c6\3\2\2\2\u02c9\u0099\3\2\2\2\u02ca\u02cb\7\34\2\2\u02cb"+
		"\u02cf\5\u0086D\2\u02cc\u02cd\7\23\2\2\u02cd\u02cf\5\u0086D\2\u02ce\u02ca"+
		"\3\2\2\2\u02ce\u02cc\3\2\2\2\u02cf\u009b\3\2\2\2b\u009f\u00a4\u00a9\u00ac"+
		"\u00b3\u00c1\u00ca\u00ce\u00d1\u00d7\u00dc\u00e7\u00ef\u00f7\u00ff\u0103"+
		"\u0108\u010e\u0113\u011a\u011e\u0123\u012a\u012e\u0132\u0138\u013e\u0145"+
		"\u014c\u0153\u0159\u015b\u0161\u0167\u016e\u0174\u0176\u017c\u0182\u0189"+
		"\u0190\u0192\u019c\u01a7\u01ac\u01b2\u01b8\u01be\u01c3\u01ca\u01d0\u01d6"+
		"\u01dd\u01e1\u01e5\u01ec\u01ef\u01f3\u01f7\u01fa\u0200\u0205\u020b\u020f"+
		"\u0214\u0219\u021f\u0226\u022f\u0232\u023a\u0246\u024b\u0250\u0252\u0254"+
		"\u025b\u025e\u0263\u026a\u026d\u0272\u0279\u027c\u0283\u0286\u028b\u0294"+
		"\u029d\u02a1\u02a9\u02ad\u02b5\u02bd\u02c8\u02ce";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}