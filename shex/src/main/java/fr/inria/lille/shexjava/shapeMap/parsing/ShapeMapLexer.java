// Generated from fr/inria/lille/shexjava/shapeMap/parsing/ShapeMap.g4 by ANTLR 4.7.1
package fr.inria.lille.shexjava.shapeMap.parsing;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShapeMapLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, KW_TRUE=9, 
		KW_FALSE=10, PNAME_LN=11, PNAME_NS=12, IRIREF=13, BLANK_NODE_LABEL=14, 
		RDF_TYPE=15, AT_START=16, LANGTAG=17, INTEGER=18, DECIMAL=19, DOUBLE=20, 
		EXPONENT=21, STRING_LITERAL1=22, STRING_LITERAL2=23, STRING_LITERAL_LONG1=24, 
		STRING_LITERAL_LONG2=25, LANG_STRING_LITERAL1=26, LANG_STRING_LITERAL2=27, 
		LANG_STRING_LITERAL_LONG1=28, LANG_STRING_LITERAL_LONG2=29, UCHAR=30, 
		ECHAR=31, PN_CHARS_BASE=32, PN_CHARS_U=33, PN_CHARS=34, PN_PREFIX=35, 
		PN_LOCAL=36, PLX=37, PERCENT=38, HEX=39, PN_LOCAL_ESC=40, PASS=41;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "KW_TRUE", 
		"KW_FALSE", "PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", "RDF_TYPE", 
		"AT_START", "LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "LANG_STRING_LITERAL1", 
		"LANG_STRING_LITERAL2", "LANG_STRING_LITERAL_LONG1", "LANG_STRING_LITERAL_LONG2", 
		"UCHAR", "ECHAR", "PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", 
		"PN_LOCAL", "PLX", "PERCENT", "HEX", "PN_LOCAL_ESC", "PASS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'{'", "'FOCUS'", "'_'", "'}'", "'@'", "'START'", "'^^'", 
		"'true'", "'false'", null, null, null, null, "'a'", "'@START'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "KW_TRUE", "KW_FALSE", 
		"PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", "RDF_TYPE", "AT_START", 
		"LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "LANG_STRING_LITERAL1", 
		"LANG_STRING_LITERAL2", "LANG_STRING_LITERAL_LONG1", "LANG_STRING_LITERAL_LONG2", 
		"UCHAR", "ECHAR", "PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", 
		"PN_LOCAL", "PLX", "PERCENT", "HEX", "PN_LOCAL_ESC", "PASS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u01a2\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\r\5\r~\n\r\3\r\3\r\3\16\3\16\3\16\7\16\u0085\n\16\f"+
		"\16\16\16\u0088\13\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\5\17\u0091\n"+
		"\17\3\17\3\17\7\17\u0095\n\17\f\17\16\17\u0098\13\17\3\17\5\17\u009b\n"+
		"\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\6\22\u00a8"+
		"\n\22\r\22\16\22\u00a9\3\22\3\22\6\22\u00ae\n\22\r\22\16\22\u00af\7\22"+
		"\u00b2\n\22\f\22\16\22\u00b5\13\22\3\23\5\23\u00b8\n\23\3\23\6\23\u00bb"+
		"\n\23\r\23\16\23\u00bc\3\24\5\24\u00c0\n\24\3\24\7\24\u00c3\n\24\f\24"+
		"\16\24\u00c6\13\24\3\24\3\24\6\24\u00ca\n\24\r\24\16\24\u00cb\3\25\5\25"+
		"\u00cf\n\25\3\25\6\25\u00d2\n\25\r\25\16\25\u00d3\3\25\3\25\7\25\u00d8"+
		"\n\25\f\25\16\25\u00db\13\25\3\25\3\25\5\25\u00df\n\25\3\25\6\25\u00e2"+
		"\n\25\r\25\16\25\u00e3\3\25\5\25\u00e7\n\25\3\26\3\26\5\26\u00eb\n\26"+
		"\3\26\6\26\u00ee\n\26\r\26\16\26\u00ef\3\27\3\27\3\27\3\27\7\27\u00f6"+
		"\n\27\f\27\16\27\u00f9\13\27\3\27\3\27\3\30\3\30\3\30\3\30\7\30\u0101"+
		"\n\30\f\30\16\30\u0104\13\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\5\31\u010f\n\31\3\31\3\31\3\31\5\31\u0114\n\31\7\31\u0116\n\31\f\31"+
		"\16\31\u0119\13\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\5\32\u0126\n\32\3\32\3\32\3\32\5\32\u012b\n\32\7\32\u012d\n\32\f\32"+
		"\16\32\u0130\13\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u0156"+
		"\n\37\3 \3 \3 \3!\5!\u015c\n!\3\"\3\"\5\"\u0160\n\"\3#\3#\5#\u0164\n#"+
		"\3$\3$\3$\7$\u0169\n$\f$\16$\u016c\13$\3$\5$\u016f\n$\3%\3%\3%\5%\u0174"+
		"\n%\3%\3%\3%\7%\u0179\n%\f%\16%\u017c\13%\3%\3%\3%\5%\u0181\n%\5%\u0183"+
		"\n%\3&\3&\5&\u0187\n&\3\'\3\'\3\'\3\'\3(\5(\u018e\n(\3)\3)\3)\3*\6*\u0194"+
		"\n*\r*\16*\u0195\3*\3*\7*\u019a\n*\f*\16*\u019d\13*\5*\u019f\n*\3*\3*"+
		"\2\2+\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+\3\2\23\t\2\2\"$$>@^^``bb}\177\3\2\62;\4"+
		"\2C\\c|\5\2\62;C\\c|\4\2--//\4\2GGgg\6\2\f\f\17\17))^^\6\2\f\f\17\17$"+
		"$^^\4\2))^^\4\2$$^^\n\2$$))^^ddhhppttvv\7\2//\62;\u00b9\u00b9\u0302\u0371"+
		"\u2041\u2042\4\2\60\60<<\5\2\62;CHch\t\2##%\61==??ABaa\u0080\u0080\5\2"+
		"\13\f\17\17\"\"\5\2\f\f\17\17``\3\20\2C\2\\\2c\2|\2\u00c2\2\u00d8\2\u00da"+
		"\2\u00f8\2\u00fa\2\u0301\2\u0372\2\u037f\2\u0381\2\u2001\2\u200e\2\u200f"+
		"\2\u2072\2\u2191\2\u2c02\2\u2ff1\2\u3003\2\ud801\2\uf902\2\ufdd1\2\ufdf2"+
		"\2\uffff\2\2\3\uffff\20\u01da\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\3U\3\2\2\2\5W\3\2\2\2\7Y\3\2\2\2\t_\3\2\2"+
		"\2\13a\3\2\2\2\rc\3\2\2\2\17e\3\2\2\2\21k\3\2\2\2\23n\3\2\2\2\25s\3\2"+
		"\2\2\27y\3\2\2\2\31}\3\2\2\2\33\u0081\3\2\2\2\35\u008b\3\2\2\2\37\u009c"+
		"\3\2\2\2!\u009e\3\2\2\2#\u00a5\3\2\2\2%\u00b7\3\2\2\2\'\u00bf\3\2\2\2"+
		")\u00ce\3\2\2\2+\u00e8\3\2\2\2-\u00f1\3\2\2\2/\u00fc\3\2\2\2\61\u0107"+
		"\3\2\2\2\63\u011e\3\2\2\2\65\u0135\3\2\2\2\67\u0138\3\2\2\29\u013b\3\2"+
		"\2\2;\u013e\3\2\2\2=\u0155\3\2\2\2?\u0157\3\2\2\2A\u015b\3\2\2\2C\u015f"+
		"\3\2\2\2E\u0163\3\2\2\2G\u0165\3\2\2\2I\u0173\3\2\2\2K\u0186\3\2\2\2M"+
		"\u0188\3\2\2\2O\u018d\3\2\2\2Q\u018f\3\2\2\2S\u019e\3\2\2\2UV\7.\2\2V"+
		"\4\3\2\2\2WX\7}\2\2X\6\3\2\2\2YZ\7H\2\2Z[\7Q\2\2[\\\7E\2\2\\]\7W\2\2]"+
		"^\7U\2\2^\b\3\2\2\2_`\7a\2\2`\n\3\2\2\2ab\7\177\2\2b\f\3\2\2\2cd\7B\2"+
		"\2d\16\3\2\2\2ef\7U\2\2fg\7V\2\2gh\7C\2\2hi\7T\2\2ij\7V\2\2j\20\3\2\2"+
		"\2kl\7`\2\2lm\7`\2\2m\22\3\2\2\2no\7v\2\2op\7t\2\2pq\7w\2\2qr\7g\2\2r"+
		"\24\3\2\2\2st\7h\2\2tu\7c\2\2uv\7n\2\2vw\7u\2\2wx\7g\2\2x\26\3\2\2\2y"+
		"z\5\31\r\2z{\5I%\2{\30\3\2\2\2|~\5G$\2}|\3\2\2\2}~\3\2\2\2~\177\3\2\2"+
		"\2\177\u0080\7<\2\2\u0080\32\3\2\2\2\u0081\u0086\7>\2\2\u0082\u0085\n"+
		"\2\2\2\u0083\u0085\5=\37\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085"+
		"\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0089\3\2"+
		"\2\2\u0088\u0086\3\2\2\2\u0089\u008a\7@\2\2\u008a\34\3\2\2\2\u008b\u008c"+
		"\7a\2\2\u008c\u008d\7<\2\2\u008d\u0090\3\2\2\2\u008e\u0091\5C\"\2\u008f"+
		"\u0091\t\3\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\u009a\3\2"+
		"\2\2\u0092\u0095\5E#\2\u0093\u0095\7\60\2\2\u0094\u0092\3\2\2\2\u0094"+
		"\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2"+
		"\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009b\5E#\2\u009a\u0096"+
		"\3\2\2\2\u009a\u009b\3\2\2\2\u009b\36\3\2\2\2\u009c\u009d\7c\2\2\u009d"+
		" \3\2\2\2\u009e\u009f\7B\2\2\u009f\u00a0\7U\2\2\u00a0\u00a1\7V\2\2\u00a1"+
		"\u00a2\7C\2\2\u00a2\u00a3\7T\2\2\u00a3\u00a4\7V\2\2\u00a4\"\3\2\2\2\u00a5"+
		"\u00a7\7B\2\2\u00a6\u00a8\t\4\2\2\u00a7\u00a6\3\2\2\2\u00a8\u00a9\3\2"+
		"\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00b3\3\2\2\2\u00ab"+
		"\u00ad\7/\2\2\u00ac\u00ae\t\5\2\2\u00ad\u00ac\3\2\2\2\u00ae\u00af\3\2"+
		"\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\3\2\2\2\u00b1"+
		"\u00ab\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2"+
		"\2\2\u00b4$\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b8\t\6\2\2\u00b7\u00b6"+
		"\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00ba\3\2\2\2\u00b9\u00bb\t\3\2\2\u00ba"+
		"\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd&\3\2\2\2\u00be\u00c0\t\6\2\2\u00bf\u00be\3\2\2\2\u00bf\u00c0"+
		"\3\2\2\2\u00c0\u00c4\3\2\2\2\u00c1\u00c3\t\3\2\2\u00c2\u00c1\3\2\2\2\u00c3"+
		"\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7\3\2"+
		"\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c9\7\60\2\2\u00c8\u00ca\t\3\2\2\u00c9"+
		"\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2"+
		"\2\2\u00cc(\3\2\2\2\u00cd\u00cf\t\6\2\2\u00ce\u00cd\3\2\2\2\u00ce\u00cf"+
		"\3\2\2\2\u00cf\u00e6\3\2\2\2\u00d0\u00d2\t\3\2\2\u00d1\u00d0\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\3\2"+
		"\2\2\u00d5\u00d9\7\60\2\2\u00d6\u00d8\t\3\2\2\u00d7\u00d6\3\2\2\2\u00d8"+
		"\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dc\3\2"+
		"\2\2\u00db\u00d9\3\2\2\2\u00dc\u00e7\5+\26\2\u00dd\u00df\7\60\2\2\u00de"+
		"\u00dd\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e1\3\2\2\2\u00e0\u00e2\t\3"+
		"\2\2\u00e1\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3"+
		"\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\5+\26\2\u00e6\u00d1\3\2"+
		"\2\2\u00e6\u00de\3\2\2\2\u00e7*\3\2\2\2\u00e8\u00ea\t\7\2\2\u00e9\u00eb"+
		"\t\6\2\2\u00ea\u00e9\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec"+
		"\u00ee\t\3\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2"+
		"\2\2\u00ef\u00f0\3\2\2\2\u00f0,\3\2\2\2\u00f1\u00f7\7)\2\2\u00f2\u00f6"+
		"\n\b\2\2\u00f3\u00f6\5? \2\u00f4\u00f6\5=\37\2\u00f5\u00f2\3\2\2\2\u00f5"+
		"\u00f3\3\2\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2"+
		"\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa"+
		"\u00fb\7)\2\2\u00fb.\3\2\2\2\u00fc\u0102\7$\2\2\u00fd\u0101\n\t\2\2\u00fe"+
		"\u0101\5? \2\u00ff\u0101\5=\37\2\u0100\u00fd\3\2\2\2\u0100\u00fe\3\2\2"+
		"\2\u0100\u00ff\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103"+
		"\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0106\7$\2\2\u0106"+
		"\60\3\2\2\2\u0107\u0108\7)\2\2\u0108\u0109\7)\2\2\u0109\u010a\7)\2\2\u010a"+
		"\u0117\3\2\2\2\u010b\u010f\7)\2\2\u010c\u010d\7)\2\2\u010d\u010f\7)\2"+
		"\2\u010e\u010b\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0113"+
		"\3\2\2\2\u0110\u0114\n\n\2\2\u0111\u0114\5? \2\u0112\u0114\5=\37\2\u0113"+
		"\u0110\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0112\3\2\2\2\u0114\u0116\3\2"+
		"\2\2\u0115\u010e\3\2\2\2\u0116\u0119\3\2\2\2\u0117\u0115\3\2\2\2\u0117"+
		"\u0118\3\2\2\2\u0118\u011a\3\2\2\2\u0119\u0117\3\2\2\2\u011a\u011b\7)"+
		"\2\2\u011b\u011c\7)\2\2\u011c\u011d\7)\2\2\u011d\62\3\2\2\2\u011e\u011f"+
		"\7$\2\2\u011f\u0120\7$\2\2\u0120\u0121\7$\2\2\u0121\u012e\3\2\2\2\u0122"+
		"\u0126\7$\2\2\u0123\u0124\7$\2\2\u0124\u0126\7$\2\2\u0125\u0122\3\2\2"+
		"\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u012a\3\2\2\2\u0127\u012b"+
		"\n\13\2\2\u0128\u012b\5? \2\u0129\u012b\5=\37\2\u012a\u0127\3\2\2\2\u012a"+
		"\u0128\3\2\2\2\u012a\u0129\3\2\2\2\u012b\u012d\3\2\2\2\u012c\u0125\3\2"+
		"\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f"+
		"\u0131\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u0132\7$\2\2\u0132\u0133\7$\2"+
		"\2\u0133\u0134\7$\2\2\u0134\64\3\2\2\2\u0135\u0136\5-\27\2\u0136\u0137"+
		"\5#\22\2\u0137\66\3\2\2\2\u0138\u0139\5/\30\2\u0139\u013a\5#\22\2\u013a"+
		"8\3\2\2\2\u013b\u013c\5\61\31\2\u013c\u013d\5#\22\2\u013d:\3\2\2\2\u013e"+
		"\u013f\5\63\32\2\u013f\u0140\5#\22\2\u0140<\3\2\2\2\u0141\u0142\7^\2\2"+
		"\u0142\u0143\7w\2\2\u0143\u0144\3\2\2\2\u0144\u0145\5O(\2\u0145\u0146"+
		"\5O(\2\u0146\u0147\5O(\2\u0147\u0148\5O(\2\u0148\u0156\3\2\2\2\u0149\u014a"+
		"\7^\2\2\u014a\u014b\7W\2\2\u014b\u014c\3\2\2\2\u014c\u014d\5O(\2\u014d"+
		"\u014e\5O(\2\u014e\u014f\5O(\2\u014f\u0150\5O(\2\u0150\u0151\5O(\2\u0151"+
		"\u0152\5O(\2\u0152\u0153\5O(\2\u0153\u0154\5O(\2\u0154\u0156\3\2\2\2\u0155"+
		"\u0141\3\2\2\2\u0155\u0149\3\2\2\2\u0156>\3\2\2\2\u0157\u0158\7^\2\2\u0158"+
		"\u0159\t\f\2\2\u0159@\3\2\2\2\u015a\u015c\t\23\2\2\u015b\u015a\3\2\2\2"+
		"\u015cB\3\2\2\2\u015d\u0160\5A!\2\u015e\u0160\7a\2\2\u015f\u015d\3\2\2"+
		"\2\u015f\u015e\3\2\2\2\u0160D\3\2\2\2\u0161\u0164\5C\"\2\u0162\u0164\t"+
		"\r\2\2\u0163\u0161\3\2\2\2\u0163\u0162\3\2\2\2\u0164F\3\2\2\2\u0165\u016e"+
		"\5A!\2\u0166\u0169\5E#\2\u0167\u0169\7\60\2\2\u0168\u0166\3\2\2\2\u0168"+
		"\u0167\3\2\2\2\u0169\u016c\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2"+
		"\2\2\u016b\u016d\3\2\2\2\u016c\u016a\3\2\2\2\u016d\u016f\5E#\2\u016e\u016a"+
		"\3\2\2\2\u016e\u016f\3\2\2\2\u016fH\3\2\2\2\u0170\u0174\5C\"\2\u0171\u0174"+
		"\4\62<\2\u0172\u0174\5K&\2\u0173\u0170\3\2\2\2\u0173\u0171\3\2\2\2\u0173"+
		"\u0172\3\2\2\2\u0174\u0182\3\2\2\2\u0175\u0179\5E#\2\u0176\u0179\t\16"+
		"\2\2\u0177\u0179\5K&\2\u0178\u0175\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0177"+
		"\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b"+
		"\u0180\3\2\2\2\u017c\u017a\3\2\2\2\u017d\u0181\5E#\2\u017e\u0181\7<\2"+
		"\2\u017f\u0181\5K&\2\u0180\u017d\3\2\2\2\u0180\u017e\3\2\2\2\u0180\u017f"+
		"\3\2\2\2\u0181\u0183\3\2\2\2\u0182\u017a\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"J\3\2\2\2\u0184\u0187\5M\'\2\u0185\u0187\5Q)\2\u0186\u0184\3\2\2\2\u0186"+
		"\u0185\3\2\2\2\u0187L\3\2\2\2\u0188\u0189\7\'\2\2\u0189\u018a\5O(\2\u018a"+
		"\u018b\5O(\2\u018bN\3\2\2\2\u018c\u018e\t\17\2\2\u018d\u018c\3\2\2\2\u018e"+
		"P\3\2\2\2\u018f\u0190\7^\2\2\u0190\u0191\t\20\2\2\u0191R\3\2\2\2\u0192"+
		"\u0194\t\21\2\2\u0193\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0193\3"+
		"\2\2\2\u0195\u0196\3\2\2\2\u0196\u019f\3\2\2\2\u0197\u019b\7%\2\2\u0198"+
		"\u019a\t\22\2\2\u0199\u0198\3\2\2\2\u019a\u019d\3\2\2\2\u019b\u0199\3"+
		"\2\2\2\u019b\u019c\3\2\2\2\u019c\u019f\3\2\2\2\u019d\u019b\3\2\2\2\u019e"+
		"\u0193\3\2\2\2\u019e\u0197\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a1\b*"+
		"\2\2\u01a1T\3\2\2\2\65\2}\u0084\u0086\u0090\u0094\u0096\u009a\u00a9\u00af"+
		"\u00b3\u00b7\u00bc\u00bf\u00c4\u00cb\u00ce\u00d3\u00d9\u00de\u00e3\u00e6"+
		"\u00ea\u00ef\u00f5\u00f7\u0100\u0102\u010e\u0113\u0117\u0125\u012a\u012e"+
		"\u0155\u015b\u015f\u0163\u0168\u016a\u016e\u0173\u0178\u017a\u0180\u0182"+
		"\u0186\u018d\u0195\u019b\u019e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}