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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, KW_TRUE=9, 
		KW_FALSE=10, PNAME_LN=11, PNAME_NS=12, IRIREF=13, BLANK_NODE_LABEL=14, 
		RDF_TYPE=15, LANGTAG=16, INTEGER=17, DECIMAL=18, DOUBLE=19, EXPONENT=20, 
		STRING_LITERAL1=21, STRING_LITERAL2=22, STRING_LITERAL_LONG1=23, STRING_LITERAL_LONG2=24, 
		UCHAR=25, ECHAR=26, PN_CHARS_BASE=27, PN_CHARS_U=28, PN_CHARS=29, PN_PREFIX=30, 
		PN_LOCAL=31, PLX=32, PERCENT=33, HEX=34, PN_LOCAL_ESC=35, PASS=36;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "KW_TRUE", 
		"KW_FALSE", "PNAME_LN", "PNAME_NS", "IRIREF", "BLANK_NODE_LABEL", "RDF_TYPE", 
		"LANGTAG", "INTEGER", "DECIMAL", "DOUBLE", "EXPONENT", "STRING_LITERAL1", 
		"STRING_LITERAL2", "STRING_LITERAL_LONG1", "STRING_LITERAL_LONG2", "UCHAR", 
		"ECHAR", "PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", 
		"PLX", "PERCENT", "HEX", "PN_LOCAL_ESC", "PASS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2&\u0185\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\5\rt\n\r\3\r"+
		"\3\r\3\16\3\16\3\16\7\16{\n\16\f\16\16\16~\13\16\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\17\5\17\u0087\n\17\3\17\3\17\7\17\u008b\n\17\f\17\16\17\u008e"+
		"\13\17\3\17\5\17\u0091\n\17\3\20\3\20\3\21\3\21\6\21\u0097\n\21\r\21\16"+
		"\21\u0098\3\21\3\21\6\21\u009d\n\21\r\21\16\21\u009e\7\21\u00a1\n\21\f"+
		"\21\16\21\u00a4\13\21\3\22\5\22\u00a7\n\22\3\22\6\22\u00aa\n\22\r\22\16"+
		"\22\u00ab\3\23\5\23\u00af\n\23\3\23\7\23\u00b2\n\23\f\23\16\23\u00b5\13"+
		"\23\3\23\3\23\6\23\u00b9\n\23\r\23\16\23\u00ba\3\24\5\24\u00be\n\24\3"+
		"\24\6\24\u00c1\n\24\r\24\16\24\u00c2\3\24\3\24\7\24\u00c7\n\24\f\24\16"+
		"\24\u00ca\13\24\3\24\3\24\5\24\u00ce\n\24\3\24\6\24\u00d1\n\24\r\24\16"+
		"\24\u00d2\3\24\5\24\u00d6\n\24\3\25\3\25\5\25\u00da\n\25\3\25\6\25\u00dd"+
		"\n\25\r\25\16\25\u00de\3\26\3\26\3\26\3\26\7\26\u00e5\n\26\f\26\16\26"+
		"\u00e8\13\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u00f0\n\27\f\27\16\27"+
		"\u00f3\13\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00fe\n"+
		"\30\3\30\3\30\3\30\5\30\u0103\n\30\7\30\u0105\n\30\f\30\16\30\u0108\13"+
		"\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0115"+
		"\n\31\3\31\3\31\3\31\5\31\u011a\n\31\7\31\u011c\n\31\f\31\16\31\u011f"+
		"\13\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0139\n\32"+
		"\3\33\3\33\3\33\3\34\5\34\u013f\n\34\3\35\3\35\5\35\u0143\n\35\3\36\3"+
		"\36\5\36\u0147\n\36\3\37\3\37\3\37\7\37\u014c\n\37\f\37\16\37\u014f\13"+
		"\37\3\37\5\37\u0152\n\37\3 \3 \3 \5 \u0157\n \3 \3 \3 \7 \u015c\n \f "+
		"\16 \u015f\13 \3 \3 \3 \5 \u0164\n \5 \u0166\n \3!\3!\5!\u016a\n!\3\""+
		"\3\"\3\"\3\"\3#\5#\u0171\n#\3$\3$\3$\3%\6%\u0177\n%\r%\16%\u0178\3%\3"+
		"%\7%\u017d\n%\f%\16%\u0180\13%\5%\u0182\n%\3%\3%\2\2&\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&\3"+
		"\2\23\t\2\2\"$$>@^^``bb}\177\3\2\62;\4\2C\\c|\5\2\62;C\\c|\4\2--//\4\2"+
		"GGgg\6\2\f\f\17\17))^^\6\2\f\f\17\17$$^^\4\2))^^\4\2$$^^\n\2$$))^^ddh"+
		"hppttvv\7\2//\62;\u00b9\u00b9\u0302\u0371\u2041\u2042\4\2\60\60<<\5\2"+
		"\62;CHch\t\2##%\61==??ABaa\u0080\u0080\5\2\13\f\17\17\"\"\5\2\f\f\17\17"+
		"``\3\20\2C\2\\\2c\2|\2\u00c2\2\u00d8\2\u00da\2\u00f8\2\u00fa\2\u0301\2"+
		"\u0372\2\u037f\2\u0381\2\u2001\2\u200e\2\u200f\2\u2072\2\u2191\2\u2c02"+
		"\2\u2ff1\2\u3003\2\ud801\2\uf902\2\ufdd1\2\ufdf2\2\uffff\2\2\3\uffff\20"+
		"\u01bd\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\3K\3\2\2\2\5M\3\2\2\2\7O\3\2\2\2\tU\3\2\2\2\13W\3"+
		"\2\2\2\r]\3\2\2\2\17_\3\2\2\2\21a\3\2\2\2\23d\3\2\2\2\25i\3\2\2\2\27o"+
		"\3\2\2\2\31s\3\2\2\2\33w\3\2\2\2\35\u0081\3\2\2\2\37\u0092\3\2\2\2!\u0094"+
		"\3\2\2\2#\u00a6\3\2\2\2%\u00ae\3\2\2\2\'\u00bd\3\2\2\2)\u00d7\3\2\2\2"+
		"+\u00e0\3\2\2\2-\u00eb\3\2\2\2/\u00f6\3\2\2\2\61\u010d\3\2\2\2\63\u0138"+
		"\3\2\2\2\65\u013a\3\2\2\2\67\u013e\3\2\2\29\u0142\3\2\2\2;\u0146\3\2\2"+
		"\2=\u0148\3\2\2\2?\u0156\3\2\2\2A\u0169\3\2\2\2C\u016b\3\2\2\2E\u0170"+
		"\3\2\2\2G\u0172\3\2\2\2I\u0181\3\2\2\2KL\7.\2\2L\4\3\2\2\2MN\7B\2\2N\6"+
		"\3\2\2\2OP\7U\2\2PQ\7V\2\2QR\7C\2\2RS\7T\2\2ST\7V\2\2T\b\3\2\2\2UV\7}"+
		"\2\2V\n\3\2\2\2WX\7H\2\2XY\7Q\2\2YZ\7E\2\2Z[\7W\2\2[\\\7U\2\2\\\f\3\2"+
		"\2\2]^\7a\2\2^\16\3\2\2\2_`\7\177\2\2`\20\3\2\2\2ab\7`\2\2bc\7`\2\2c\22"+
		"\3\2\2\2de\7v\2\2ef\7t\2\2fg\7w\2\2gh\7g\2\2h\24\3\2\2\2ij\7h\2\2jk\7"+
		"c\2\2kl\7n\2\2lm\7u\2\2mn\7g\2\2n\26\3\2\2\2op\5\31\r\2pq\5? \2q\30\3"+
		"\2\2\2rt\5=\37\2sr\3\2\2\2st\3\2\2\2tu\3\2\2\2uv\7<\2\2v\32\3\2\2\2w|"+
		"\7>\2\2x{\n\2\2\2y{\5\63\32\2zx\3\2\2\2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2"+
		"|}\3\2\2\2}\177\3\2\2\2~|\3\2\2\2\177\u0080\7@\2\2\u0080\34\3\2\2\2\u0081"+
		"\u0082\7a\2\2\u0082\u0083\7<\2\2\u0083\u0086\3\2\2\2\u0084\u0087\59\35"+
		"\2\u0085\u0087\t\3\2\2\u0086\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087\u0090"+
		"\3\2\2\2\u0088\u008b\5;\36\2\u0089\u008b\7\60\2\2\u008a\u0088\3\2\2\2"+
		"\u008a\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u008f\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0091\5;\36\2\u0090"+
		"\u008c\3\2\2\2\u0090\u0091\3\2\2\2\u0091\36\3\2\2\2\u0092\u0093\7c\2\2"+
		"\u0093 \3\2\2\2\u0094\u0096\7B\2\2\u0095\u0097\t\4\2\2\u0096\u0095\3\2"+
		"\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099"+
		"\u00a2\3\2\2\2\u009a\u009c\7/\2\2\u009b\u009d\t\5\2\2\u009c\u009b\3\2"+
		"\2\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f"+
		"\u00a1\3\2\2\2\u00a0\u009a\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3\2"+
		"\2\2\u00a2\u00a3\3\2\2\2\u00a3\"\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\u00a7"+
		"\t\6\2\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8"+
		"\u00aa\t\3\2\2\u00a9\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ab\u00ac\3\2\2\2\u00ac$\3\2\2\2\u00ad\u00af\t\6\2\2\u00ae\u00ad"+
		"\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b3\3\2\2\2\u00b0\u00b2\t\3\2\2\u00b1"+
		"\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2"+
		"\2\2\u00b4\u00b6\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b8\7\60\2\2\u00b7"+
		"\u00b9\t\3\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2"+
		"\2\2\u00ba\u00bb\3\2\2\2\u00bb&\3\2\2\2\u00bc\u00be\t\6\2\2\u00bd\u00bc"+
		"\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00d5\3\2\2\2\u00bf\u00c1\t\3\2\2\u00c0"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c8\7\60\2\2\u00c5\u00c7\t\3\2\2\u00c6"+
		"\u00c5\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2"+
		"\2\2\u00c9\u00cb\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00d6\5)\25\2\u00cc"+
		"\u00ce\7\60\2\2\u00cd\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3"+
		"\2\2\2\u00cf\u00d1\t\3\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2"+
		"\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\5)"+
		"\25\2\u00d5\u00c0\3\2\2\2\u00d5\u00cd\3\2\2\2\u00d6(\3\2\2\2\u00d7\u00d9"+
		"\t\7\2\2\u00d8\u00da\t\6\2\2\u00d9\u00d8\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00dc\3\2\2\2\u00db\u00dd\t\3\2\2\u00dc\u00db\3\2\2\2\u00dd\u00de\3\2"+
		"\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df*\3\2\2\2\u00e0\u00e6"+
		"\7)\2\2\u00e1\u00e5\n\b\2\2\u00e2\u00e5\5\65\33\2\u00e3\u00e5\5\63\32"+
		"\2\u00e4\u00e1\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e3\3\2\2\2\u00e5\u00e8"+
		"\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e9\3\2\2\2\u00e8"+
		"\u00e6\3\2\2\2\u00e9\u00ea\7)\2\2\u00ea,\3\2\2\2\u00eb\u00f1\7$\2\2\u00ec"+
		"\u00f0\n\t\2\2\u00ed\u00f0\5\65\33\2\u00ee\u00f0\5\63\32\2\u00ef\u00ec"+
		"\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1\3\2"+
		"\2\2\u00f4\u00f5\7$\2\2\u00f5.\3\2\2\2\u00f6\u00f7\7)\2\2\u00f7\u00f8"+
		"\7)\2\2\u00f8\u00f9\7)\2\2\u00f9\u0106\3\2\2\2\u00fa\u00fe\7)\2\2\u00fb"+
		"\u00fc\7)\2\2\u00fc\u00fe\7)\2\2\u00fd\u00fa\3\2\2\2\u00fd\u00fb\3\2\2"+
		"\2\u00fd\u00fe\3\2\2\2\u00fe\u0102\3\2\2\2\u00ff\u0103\n\n\2\2\u0100\u0103"+
		"\5\65\33\2\u0101\u0103\5\63\32\2\u0102\u00ff\3\2\2\2\u0102\u0100\3\2\2"+
		"\2\u0102\u0101\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u00fd\3\2\2\2\u0105\u0108"+
		"\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108"+
		"\u0106\3\2\2\2\u0109\u010a\7)\2\2\u010a\u010b\7)\2\2\u010b\u010c\7)\2"+
		"\2\u010c\60\3\2\2\2\u010d\u010e\7$\2\2\u010e\u010f\7$\2\2\u010f\u0110"+
		"\7$\2\2\u0110\u011d\3\2\2\2\u0111\u0115\7$\2\2\u0112\u0113\7$\2\2\u0113"+
		"\u0115\7$\2\2\u0114\u0111\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2"+
		"\2\2\u0115\u0119\3\2\2\2\u0116\u011a\n\13\2\2\u0117\u011a\5\65\33\2\u0118"+
		"\u011a\5\63\32\2\u0119\u0116\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u0118\3"+
		"\2\2\2\u011a\u011c\3\2\2\2\u011b\u0114\3\2\2\2\u011c\u011f\3\2\2\2\u011d"+
		"\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2"+
		"\2\2\u0120\u0121\7$\2\2\u0121\u0122\7$\2\2\u0122\u0123\7$\2\2\u0123\62"+
		"\3\2\2\2\u0124\u0125\7^\2\2\u0125\u0126\7w\2\2\u0126\u0127\3\2\2\2\u0127"+
		"\u0128\5E#\2\u0128\u0129\5E#\2\u0129\u012a\5E#\2\u012a\u012b\5E#\2\u012b"+
		"\u0139\3\2\2\2\u012c\u012d\7^\2\2\u012d\u012e\7W\2\2\u012e\u012f\3\2\2"+
		"\2\u012f\u0130\5E#\2\u0130\u0131\5E#\2\u0131\u0132\5E#\2\u0132\u0133\5"+
		"E#\2\u0133\u0134\5E#\2\u0134\u0135\5E#\2\u0135\u0136\5E#\2\u0136\u0137"+
		"\5E#\2\u0137\u0139\3\2\2\2\u0138\u0124\3\2\2\2\u0138\u012c\3\2\2\2\u0139"+
		"\64\3\2\2\2\u013a\u013b\7^\2\2\u013b\u013c\t\f\2\2\u013c\66\3\2\2\2\u013d"+
		"\u013f\t\23\2\2\u013e\u013d\3\2\2\2\u013f8\3\2\2\2\u0140\u0143\5\67\34"+
		"\2\u0141\u0143\7a\2\2\u0142\u0140\3\2\2\2\u0142\u0141\3\2\2\2\u0143:\3"+
		"\2\2\2\u0144\u0147\59\35\2\u0145\u0147\t\r\2\2\u0146\u0144\3\2\2\2\u0146"+
		"\u0145\3\2\2\2\u0147<\3\2\2\2\u0148\u0151\5\67\34\2\u0149\u014c\5;\36"+
		"\2\u014a\u014c\7\60\2\2\u014b\u0149\3\2\2\2\u014b\u014a\3\2\2\2\u014c"+
		"\u014f\3\2\2\2\u014d\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u0150\3\2"+
		"\2\2\u014f\u014d\3\2\2\2\u0150\u0152\5;\36\2\u0151\u014d\3\2\2\2\u0151"+
		"\u0152\3\2\2\2\u0152>\3\2\2\2\u0153\u0157\59\35\2\u0154\u0157\4\62<\2"+
		"\u0155\u0157\5A!\2\u0156\u0153\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0155"+
		"\3\2\2\2\u0157\u0165\3\2\2\2\u0158\u015c\5;\36\2\u0159\u015c\t\16\2\2"+
		"\u015a\u015c\5A!\2\u015b\u0158\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015a"+
		"\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e"+
		"\u0163\3\2\2\2\u015f\u015d\3\2\2\2\u0160\u0164\5;\36\2\u0161\u0164\7<"+
		"\2\2\u0162\u0164\5A!\2\u0163\u0160\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0162"+
		"\3\2\2\2\u0164\u0166\3\2\2\2\u0165\u015d\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"@\3\2\2\2\u0167\u016a\5C\"\2\u0168\u016a\5G$\2\u0169\u0167\3\2\2\2\u0169"+
		"\u0168\3\2\2\2\u016aB\3\2\2\2\u016b\u016c\7\'\2\2\u016c\u016d\5E#\2\u016d"+
		"\u016e\5E#\2\u016eD\3\2\2\2\u016f\u0171\t\17\2\2\u0170\u016f\3\2\2\2\u0171"+
		"F\3\2\2\2\u0172\u0173\7^\2\2\u0173\u0174\t\20\2\2\u0174H\3\2\2\2\u0175"+
		"\u0177\t\21\2\2\u0176\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u0176\3"+
		"\2\2\2\u0178\u0179\3\2\2\2\u0179\u0182\3\2\2\2\u017a\u017e\7%\2\2\u017b"+
		"\u017d\t\22\2\2\u017c\u017b\3\2\2\2\u017d\u0180\3\2\2\2\u017e\u017c\3"+
		"\2\2\2\u017e\u017f\3\2\2\2\u017f\u0182\3\2\2\2\u0180\u017e\3\2\2\2\u0181"+
		"\u0176\3\2\2\2\u0181\u017a\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184\b%"+
		"\2\2\u0184J\3\2\2\2\65\2sz|\u0086\u008a\u008c\u0090\u0098\u009e\u00a2"+
		"\u00a6\u00ab\u00ae\u00b3\u00ba\u00bd\u00c2\u00c8\u00cd\u00d2\u00d5\u00d9"+
		"\u00de\u00e4\u00e6\u00ef\u00f1\u00fd\u0102\u0106\u0114\u0119\u011d\u0138"+
		"\u013e\u0142\u0146\u014b\u014d\u0151\u0156\u015b\u015d\u0163\u0165\u0169"+
		"\u0170\u0178\u017e\u0181\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}