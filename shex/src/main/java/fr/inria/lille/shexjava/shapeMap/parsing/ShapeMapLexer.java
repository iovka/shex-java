// Generated from fr/inria/lille/shexjava/shapeMap/parsing/ShapeMap.g4 by ANTLR 4.7.1
package fr.inria.lille.shexjava.shapeMap.parsing;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShapeMapLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		KW_TRUE=10, KW_FALSE=11, PNAME_LN=12, PNAME_NS=13, IRIREF=14, BLANK_NODE_LABEL=15, 
		RDF_TYPE=16, LANGTAG=17, INTEGER=18, DECIMAL=19, DOUBLE=20, EXPONENT=21, 
		STRING_LITERAL1=22, STRING_LITERAL2=23, STRING_LITERAL_LONG1=24, STRING_LITERAL_LONG2=25, 
		UCHAR=26, ECHAR=27, PN_CHARS_BASE=28, PN_CHARS_U=29, PN_CHARS=30, PN_PREFIX=31, 
		PN_LOCAL=32, PLX=33, PERCENT=34, HEX=35, PN_LOCAL_ESC=36, PASS=37;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"KW_TRUE", "KW_FALSE", "PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", 
		"RDF_TYPE", "LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "UCHAR", 
		"ECHAR", "PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", 
		"PLX", "PERCENT", "HEX", "PN_LOCAL_ESC", "PASS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'@START'", "'@'", "'START'", "'{'", "'FOCUS'", "'_'", "'}'", 
		"'^^'", "'true'", "'false'", null, null, null, null, "'a'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "KW_TRUE", 
		"KW_FALSE", "PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", "RDF_TYPE", 
		"LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "UCHAR", 
		"ECHAR", "PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", 
		"PLX", "PERCENT", "HEX", "PN_LOCAL_ESC", "PASS"
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


	public ShapeMapLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ShapeMap.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\'\u018e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\16\5\16}\n\16\3\16\3\16\3\17\3\17\3\17\7\17\u0084\n"+
		"\17\f\17\16\17\u0087\13\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\5\20\u0090"+
		"\n\20\3\20\3\20\7\20\u0094\n\20\f\20\16\20\u0097\13\20\3\20\5\20\u009a"+
		"\n\20\3\21\3\21\3\22\3\22\6\22\u00a0\n\22\r\22\16\22\u00a1\3\22\3\22\6"+
		"\22\u00a6\n\22\r\22\16\22\u00a7\7\22\u00aa\n\22\f\22\16\22\u00ad\13\22"+
		"\3\23\5\23\u00b0\n\23\3\23\6\23\u00b3\n\23\r\23\16\23\u00b4\3\24\5\24"+
		"\u00b8\n\24\3\24\7\24\u00bb\n\24\f\24\16\24\u00be\13\24\3\24\3\24\6\24"+
		"\u00c2\n\24\r\24\16\24\u00c3\3\25\5\25\u00c7\n\25\3\25\6\25\u00ca\n\25"+
		"\r\25\16\25\u00cb\3\25\3\25\7\25\u00d0\n\25\f\25\16\25\u00d3\13\25\3\25"+
		"\3\25\5\25\u00d7\n\25\3\25\6\25\u00da\n\25\r\25\16\25\u00db\3\25\5\25"+
		"\u00df\n\25\3\26\3\26\5\26\u00e3\n\26\3\26\6\26\u00e6\n\26\r\26\16\26"+
		"\u00e7\3\27\3\27\3\27\3\27\7\27\u00ee\n\27\f\27\16\27\u00f1\13\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\30\7\30\u00f9\n\30\f\30\16\30\u00fc\13\30\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0107\n\31\3\31\3\31\3\31"+
		"\5\31\u010c\n\31\7\31\u010e\n\31\f\31\16\31\u0111\13\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u011e\n\32\3\32\3\32\3\32"+
		"\5\32\u0123\n\32\7\32\u0125\n\32\f\32\16\32\u0128\13\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0142\n\33\3\34\3\34\3\34\3\35"+
		"\5\35\u0148\n\35\3\36\3\36\5\36\u014c\n\36\3\37\3\37\5\37\u0150\n\37\3"+
		" \3 \3 \7 \u0155\n \f \16 \u0158\13 \3 \5 \u015b\n \3!\3!\3!\5!\u0160"+
		"\n!\3!\3!\3!\7!\u0165\n!\f!\16!\u0168\13!\3!\3!\3!\5!\u016d\n!\5!\u016f"+
		"\n!\3\"\3\"\5\"\u0173\n\"\3#\3#\3#\3#\3$\5$\u017a\n$\3%\3%\3%\3&\6&\u0180"+
		"\n&\r&\16&\u0181\3&\3&\7&\u0186\n&\f&\16&\u0189\13&\5&\u018b\n&\3&\3&"+
		"\2\2\'\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\35"+
		"9\36;\37= ?!A\"C#E$G%I&K\'\3\2\23\t\2\2\"$$>@^^``bb}\177\3\2\62;\4\2C"+
		"\\c|\5\2\62;C\\c|\4\2--//\4\2GGgg\6\2\f\f\17\17))^^\6\2\f\f\17\17$$^^"+
		"\4\2))^^\4\2$$^^\n\2$$))^^ddhhppttvv\7\2//\62;\u00b9\u00b9\u0302\u0371"+
		"\u2041\u2042\4\2\60\60<<\5\2\62;CHch\t\2##%\61==??ABaa\u0080\u0080\5\2"+
		"\13\f\17\17\"\"\5\2\f\f\17\17``\3\20\2C\2\\\2c\2|\2\u00c2\2\u00d8\2\u00da"+
		"\2\u00f8\2\u00fa\2\u0301\2\u0372\2\u037f\2\u0381\2\u2001\2\u200e\2\u200f"+
		"\2\u2072\2\u2191\2\u2c02\2\u2ff1\2\u3003\2\ud801\2\uf902\2\ufdd1\2\ufdf2"+
		"\2\uffff\2\2\3\uffff\20\u01c6\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5O\3"+
		"\2\2\2\7V\3\2\2\2\tX\3\2\2\2\13^\3\2\2\2\r`\3\2\2\2\17f\3\2\2\2\21h\3"+
		"\2\2\2\23j\3\2\2\2\25m\3\2\2\2\27r\3\2\2\2\31x\3\2\2\2\33|\3\2\2\2\35"+
		"\u0080\3\2\2\2\37\u008a\3\2\2\2!\u009b\3\2\2\2#\u009d\3\2\2\2%\u00af\3"+
		"\2\2\2\'\u00b7\3\2\2\2)\u00c6\3\2\2\2+\u00e0\3\2\2\2-\u00e9\3\2\2\2/\u00f4"+
		"\3\2\2\2\61\u00ff\3\2\2\2\63\u0116\3\2\2\2\65\u0141\3\2\2\2\67\u0143\3"+
		"\2\2\29\u0147\3\2\2\2;\u014b\3\2\2\2=\u014f\3\2\2\2?\u0151\3\2\2\2A\u015f"+
		"\3\2\2\2C\u0172\3\2\2\2E\u0174\3\2\2\2G\u0179\3\2\2\2I\u017b\3\2\2\2K"+
		"\u018a\3\2\2\2MN\7.\2\2N\4\3\2\2\2OP\7B\2\2PQ\7U\2\2QR\7V\2\2RS\7C\2\2"+
		"ST\7T\2\2TU\7V\2\2U\6\3\2\2\2VW\7B\2\2W\b\3\2\2\2XY\7U\2\2YZ\7V\2\2Z["+
		"\7C\2\2[\\\7T\2\2\\]\7V\2\2]\n\3\2\2\2^_\7}\2\2_\f\3\2\2\2`a\7H\2\2ab"+
		"\7Q\2\2bc\7E\2\2cd\7W\2\2de\7U\2\2e\16\3\2\2\2fg\7a\2\2g\20\3\2\2\2hi"+
		"\7\177\2\2i\22\3\2\2\2jk\7`\2\2kl\7`\2\2l\24\3\2\2\2mn\7v\2\2no\7t\2\2"+
		"op\7w\2\2pq\7g\2\2q\26\3\2\2\2rs\7h\2\2st\7c\2\2tu\7n\2\2uv\7u\2\2vw\7"+
		"g\2\2w\30\3\2\2\2xy\5\33\16\2yz\5A!\2z\32\3\2\2\2{}\5? \2|{\3\2\2\2|}"+
		"\3\2\2\2}~\3\2\2\2~\177\7<\2\2\177\34\3\2\2\2\u0080\u0085\7>\2\2\u0081"+
		"\u0084\n\2\2\2\u0082\u0084\5\65\33\2\u0083\u0081\3\2\2\2\u0083\u0082\3"+
		"\2\2\2\u0084\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086"+
		"\u0088\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u0089\7@\2\2\u0089\36\3\2\2\2"+
		"\u008a\u008b\7a\2\2\u008b\u008c\7<\2\2\u008c\u008f\3\2\2\2\u008d\u0090"+
		"\5;\36\2\u008e\u0090\t\3\2\2\u008f\u008d\3\2\2\2\u008f\u008e\3\2\2\2\u0090"+
		"\u0099\3\2\2\2\u0091\u0094\5=\37\2\u0092\u0094\7\60\2\2\u0093\u0091\3"+
		"\2\2\2\u0093\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095"+
		"\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u009a\5="+
		"\37\2\u0099\u0095\3\2\2\2\u0099\u009a\3\2\2\2\u009a \3\2\2\2\u009b\u009c"+
		"\7c\2\2\u009c\"\3\2\2\2\u009d\u009f\7B\2\2\u009e\u00a0\t\4\2\2\u009f\u009e"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00ab\3\2\2\2\u00a3\u00a5\7/\2\2\u00a4\u00a6\t\5\2\2\u00a5\u00a4\3\2"+
		"\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00aa\3\2\2\2\u00a9\u00a3\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ab\u00ac\3\2\2\2\u00ac$\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0"+
		"\t\6\2\2\u00af\u00ae\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\3\2\2\2\u00b1"+
		"\u00b3\t\3\2\2\u00b2\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b4\u00b5\3\2\2\2\u00b5&\3\2\2\2\u00b6\u00b8\t\6\2\2\u00b7\u00b6"+
		"\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00bc\3\2\2\2\u00b9\u00bb\t\3\2\2\u00ba"+
		"\u00b9\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\u00bf\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c1\7\60\2\2\u00c0"+
		"\u00c2\t\3\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4(\3\2\2\2\u00c5\u00c7\t\6\2\2\u00c6\u00c5"+
		"\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00de\3\2\2\2\u00c8\u00ca\t\3\2\2\u00c9"+
		"\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2"+
		"\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00d1\7\60\2\2\u00ce\u00d0\t\3\2\2\u00cf"+
		"\u00ce\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2"+
		"\2\2\u00d2\u00d4\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00df\5+\26\2\u00d5"+
		"\u00d7\7\60\2\2\u00d6\u00d5\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3"+
		"\2\2\2\u00d8\u00da\t\3\2\2\u00d9\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00df\5+"+
		"\26\2\u00de\u00c9\3\2\2\2\u00de\u00d6\3\2\2\2\u00df*\3\2\2\2\u00e0\u00e2"+
		"\t\7\2\2\u00e1\u00e3\t\6\2\2\u00e2\u00e1\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3"+
		"\u00e5\3\2\2\2\u00e4\u00e6\t\3\2\2\u00e5\u00e4\3\2\2\2\u00e6\u00e7\3\2"+
		"\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8,\3\2\2\2\u00e9\u00ef"+
		"\7)\2\2\u00ea\u00ee\n\b\2\2\u00eb\u00ee\5\67\34\2\u00ec\u00ee\5\65\33"+
		"\2\u00ed\u00ea\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1"+
		"\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f2\u00f3\7)\2\2\u00f3.\3\2\2\2\u00f4\u00fa\7$\2\2\u00f5"+
		"\u00f9\n\t\2\2\u00f6\u00f9\5\67\34\2\u00f7\u00f9\5\65\33\2\u00f8\u00f5"+
		"\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9\u00fc\3\2\2\2\u00fa"+
		"\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd\3\2\2\2\u00fc\u00fa\3\2"+
		"\2\2\u00fd\u00fe\7$\2\2\u00fe\60\3\2\2\2\u00ff\u0100\7)\2\2\u0100\u0101"+
		"\7)\2\2\u0101\u0102\7)\2\2\u0102\u010f\3\2\2\2\u0103\u0107\7)\2\2\u0104"+
		"\u0105\7)\2\2\u0105\u0107\7)\2\2\u0106\u0103\3\2\2\2\u0106\u0104\3\2\2"+
		"\2\u0106\u0107\3\2\2\2\u0107\u010b\3\2\2\2\u0108\u010c\n\n\2\2\u0109\u010c"+
		"\5\67\34\2\u010a\u010c\5\65\33\2\u010b\u0108\3\2\2\2\u010b\u0109\3\2\2"+
		"\2\u010b\u010a\3\2\2\2\u010c\u010e\3\2\2\2\u010d\u0106\3\2\2\2\u010e\u0111"+
		"\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111"+
		"\u010f\3\2\2\2\u0112\u0113\7)\2\2\u0113\u0114\7)\2\2\u0114\u0115\7)\2"+
		"\2\u0115\62\3\2\2\2\u0116\u0117\7$\2\2\u0117\u0118\7$\2\2\u0118\u0119"+
		"\7$\2\2\u0119\u0126\3\2\2\2\u011a\u011e\7$\2\2\u011b\u011c\7$\2\2\u011c"+
		"\u011e\7$\2\2\u011d\u011a\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2"+
		"\2\2\u011e\u0122\3\2\2\2\u011f\u0123\n\13\2\2\u0120\u0123\5\67\34\2\u0121"+
		"\u0123\5\65\33\2\u0122\u011f\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0121\3"+
		"\2\2\2\u0123\u0125\3\2\2\2\u0124\u011d\3\2\2\2\u0125\u0128\3\2\2\2\u0126"+
		"\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129\3\2\2\2\u0128\u0126\3\2"+
		"\2\2\u0129\u012a\7$\2\2\u012a\u012b\7$\2\2\u012b\u012c\7$\2\2\u012c\64"+
		"\3\2\2\2\u012d\u012e\7^\2\2\u012e\u012f\7w\2\2\u012f\u0130\3\2\2\2\u0130"+
		"\u0131\5G$\2\u0131\u0132\5G$\2\u0132\u0133\5G$\2\u0133\u0134\5G$\2\u0134"+
		"\u0142\3\2\2\2\u0135\u0136\7^\2\2\u0136\u0137\7W\2\2\u0137\u0138\3\2\2"+
		"\2\u0138\u0139\5G$\2\u0139\u013a\5G$\2\u013a\u013b\5G$\2\u013b\u013c\5"+
		"G$\2\u013c\u013d\5G$\2\u013d\u013e\5G$\2\u013e\u013f\5G$\2\u013f\u0140"+
		"\5G$\2\u0140\u0142\3\2\2\2\u0141\u012d\3\2\2\2\u0141\u0135\3\2\2\2\u0142"+
		"\66\3\2\2\2\u0143\u0144\7^\2\2\u0144\u0145\t\f\2\2\u01458\3\2\2\2\u0146"+
		"\u0148\t\23\2\2\u0147\u0146\3\2\2\2\u0148:\3\2\2\2\u0149\u014c\59\35\2"+
		"\u014a\u014c\7a\2\2\u014b\u0149\3\2\2\2\u014b\u014a\3\2\2\2\u014c<\3\2"+
		"\2\2\u014d\u0150\5;\36\2\u014e\u0150\t\r\2\2\u014f\u014d\3\2\2\2\u014f"+
		"\u014e\3\2\2\2\u0150>\3\2\2\2\u0151\u015a\59\35\2\u0152\u0155\5=\37\2"+
		"\u0153\u0155\7\60\2\2\u0154\u0152\3\2\2\2\u0154\u0153\3\2\2\2\u0155\u0158"+
		"\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157\u0159\3\2\2\2\u0158"+
		"\u0156\3\2\2\2\u0159\u015b\5=\37\2\u015a\u0156\3\2\2\2\u015a\u015b\3\2"+
		"\2\2\u015b@\3\2\2\2\u015c\u0160\5;\36\2\u015d\u0160\4\62<\2\u015e\u0160"+
		"\5C\"\2\u015f\u015c\3\2\2\2\u015f\u015d\3\2\2\2\u015f\u015e\3\2\2\2\u0160"+
		"\u016e\3\2\2\2\u0161\u0165\5=\37\2\u0162\u0165\t\16\2\2\u0163\u0165\5"+
		"C\"\2\u0164\u0161\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0163\3\2\2\2\u0165"+
		"\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u016c\3\2"+
		"\2\2\u0168\u0166\3\2\2\2\u0169\u016d\5=\37\2\u016a\u016d\7<\2\2\u016b"+
		"\u016d\5C\"\2\u016c\u0169\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016b\3\2"+
		"\2\2\u016d\u016f\3\2\2\2\u016e\u0166\3\2\2\2\u016e\u016f\3\2\2\2\u016f"+
		"B\3\2\2\2\u0170\u0173\5E#\2\u0171\u0173\5I%\2\u0172\u0170\3\2\2\2\u0172"+
		"\u0171\3\2\2\2\u0173D\3\2\2\2\u0174\u0175\7\'\2\2\u0175\u0176\5G$\2\u0176"+
		"\u0177\5G$\2\u0177F\3\2\2\2\u0178\u017a\t\17\2\2\u0179\u0178\3\2\2\2\u017a"+
		"H\3\2\2\2\u017b\u017c\7^\2\2\u017c\u017d\t\20\2\2\u017dJ\3\2\2\2\u017e"+
		"\u0180\t\21\2\2\u017f\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181\u017f\3"+
		"\2\2\2\u0181\u0182\3\2\2\2\u0182\u018b\3\2\2\2\u0183\u0187\7%\2\2\u0184"+
		"\u0186\t\22\2\2\u0185\u0184\3\2\2\2\u0186\u0189\3\2\2\2\u0187\u0185\3"+
		"\2\2\2\u0187\u0188\3\2\2\2\u0188\u018b\3\2\2\2\u0189\u0187\3\2\2\2\u018a"+
		"\u017f\3\2\2\2\u018a\u0183\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018d\b&"+
		"\2\2\u018dL\3\2\2\2\65\2|\u0083\u0085\u008f\u0093\u0095\u0099\u00a1\u00a7"+
		"\u00ab\u00af\u00b4\u00b7\u00bc\u00c3\u00c6\u00cb\u00d1\u00d6\u00db\u00de"+
		"\u00e2\u00e7\u00ed\u00ef\u00f8\u00fa\u0106\u010b\u010f\u011d\u0122\u0126"+
		"\u0141\u0147\u014b\u014f\u0154\u0156\u015a\u015f\u0164\u0166\u016c\u016e"+
		"\u0172\u0179\u0181\u0187\u018a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}