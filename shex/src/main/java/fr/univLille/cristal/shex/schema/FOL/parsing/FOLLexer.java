// Generated from fr/univLille/cristal/shex/schema/FOL/parsing/FOL.g4 by ANTLR 4.5
package fr.univLille.cristal.shex.schema.FOL.parsing;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, FORALL=5, EXISTS=6, IMPLICATION=7, AND=8, 
		OR=9, NOT=10, EQUAL=11, EQUALINF=12, EQUALSUP=13, INF=14, SUP=15, DIFF=16, 
		PASS=17, IRIREF=18, PNAME_NS=19, PNAME_LN=20, BLANK_NODE_LABEL=21, VARIABLE=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "FORALL", "EXISTS", "IMPLICATION", "AND", 
		"OR", "NOT", "EQUAL", "EQUALINF", "EQUALSUP", "INF", "SUP", "DIFF", "PASS", 
		"IRIREF", "PNAME_NS", "PNAME_LN", "BLANK_NODE_LABEL", "VARIABLE", "UCHAR", 
		"PN_CHARS_BASE", "PN_CHARS_U", "PN_CHARS", "PN_PREFIX", "PN_LOCAL", "PLX", 
		"PERCENT", "HEX", "PN_LOCAL_ESC", "A", "B", "C", "D", "E", "F", "G", "H", 
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", 
		"W", "X", "Y", "Z"
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


	public FOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\30\u015d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3"+
		"\22\6\22\u00ac\n\22\r\22\16\22\u00ad\3\22\3\22\3\23\3\23\3\23\7\23\u00b5"+
		"\n\23\f\23\16\23\u00b8\13\23\3\23\3\23\3\24\5\24\u00bd\n\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\5\26\u00c9\n\26\3\26\3\26\7\26"+
		"\u00cd\n\26\f\26\16\26\u00d0\13\26\3\26\5\26\u00d3\n\26\3\27\3\27\7\27"+
		"\u00d7\n\27\f\27\16\27\u00da\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30"+
		"\u00f0\n\30\3\31\5\31\u00f3\n\31\3\32\3\32\5\32\u00f7\n\32\3\33\3\33\5"+
		"\33\u00fb\n\33\3\34\3\34\3\34\7\34\u0100\n\34\f\34\16\34\u0103\13\34\3"+
		"\34\5\34\u0106\n\34\3\35\3\35\3\35\5\35\u010b\n\35\3\35\3\35\3\35\7\35"+
		"\u0110\n\35\f\35\16\35\u0113\13\35\3\35\3\35\3\35\5\35\u0118\n\35\5\35"+
		"\u011a\n\35\3\36\3\36\5\36\u011e\n\36\3\37\3\37\3\37\3\37\3 \5 \u0125"+
		"\n \3!\3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3"+
		"*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63"+
		"\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\2\2<"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2"+
		"A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2"+
		"o\2q\2s\2u\2\3\2&\5\2\13\f\17\17\"\"\t\2\2\"$$>@^^``bb}\177\3\2\62;\4"+
		"\2C\\c|\5\2\62;C\\c|\17\2C\\c|\u00c2\u00d8\u00da\u00f8\u00fa\u0301\u0372"+
		"\u037f\u0381\u2001\u200e\u200f\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902"+
		"\ufdd1\ufdf2\uffff\7\2//\62;\u00b9\u00b9\u0302\u0371\u2041\u2042\4\2\60"+
		"\60<<\5\2\62;CHch\t\2##%\61==??ABaa\u0080\u0080\4\2CCcc\4\2DDdd\4\2EE"+
		"ee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2"+
		"NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4"+
		"\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\u0150\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33"+
		"\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2"+
		"\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3w\3\2\2\2\5y\3\2\2\2\7{\3"+
		"\2\2\2\t}\3\2\2\2\13\177\3\2\2\2\r\u0086\3\2\2\2\17\u008d\3\2\2\2\21\u0090"+
		"\3\2\2\2\23\u0094\3\2\2\2\25\u0097\3\2\2\2\27\u009b\3\2\2\2\31\u009d\3"+
		"\2\2\2\33\u00a0\3\2\2\2\35\u00a3\3\2\2\2\37\u00a5\3\2\2\2!\u00a7\3\2\2"+
		"\2#\u00ab\3\2\2\2%\u00b1\3\2\2\2\'\u00bc\3\2\2\2)\u00c0\3\2\2\2+\u00c3"+
		"\3\2\2\2-\u00d4\3\2\2\2/\u00ef\3\2\2\2\61\u00f2\3\2\2\2\63\u00f6\3\2\2"+
		"\2\65\u00fa\3\2\2\2\67\u00fc\3\2\2\29\u010a\3\2\2\2;\u011d\3\2\2\2=\u011f"+
		"\3\2\2\2?\u0124\3\2\2\2A\u0126\3\2\2\2C\u0129\3\2\2\2E\u012b\3\2\2\2G"+
		"\u012d\3\2\2\2I\u012f\3\2\2\2K\u0131\3\2\2\2M\u0133\3\2\2\2O\u0135\3\2"+
		"\2\2Q\u0137\3\2\2\2S\u0139\3\2\2\2U\u013b\3\2\2\2W\u013d\3\2\2\2Y\u013f"+
		"\3\2\2\2[\u0141\3\2\2\2]\u0143\3\2\2\2_\u0145\3\2\2\2a\u0147\3\2\2\2c"+
		"\u0149\3\2\2\2e\u014b\3\2\2\2g\u014d\3\2\2\2i\u014f\3\2\2\2k\u0151\3\2"+
		"\2\2m\u0153\3\2\2\2o\u0155\3\2\2\2q\u0157\3\2\2\2s\u0159\3\2\2\2u\u015b"+
		"\3\2\2\2wx\7\f\2\2x\4\3\2\2\2yz\7*\2\2z\6\3\2\2\2{|\7.\2\2|\b\3\2\2\2"+
		"}~\7+\2\2~\n\3\2\2\2\177\u0080\5M\'\2\u0080\u0081\5_\60\2\u0081\u0082"+
		"\5e\63\2\u0082\u0083\5C\"\2\u0083\u0084\5Y-\2\u0084\u0085\5Y-\2\u0085"+
		"\f\3\2\2\2\u0086\u0087\5K&\2\u0087\u0088\5q9\2\u0088\u0089\5S*\2\u0089"+
		"\u008a\5g\64\2\u008a\u008b\5i\65\2\u008b\u008c\5g\64\2\u008c\16\3\2\2"+
		"\2\u008d\u008e\7/\2\2\u008e\u008f\7@\2\2\u008f\20\3\2\2\2\u0090\u0091"+
		"\5C\"\2\u0091\u0092\5]/\2\u0092\u0093\5I%\2\u0093\22\3\2\2\2\u0094\u0095"+
		"\5_\60\2\u0095\u0096\5e\63\2\u0096\24\3\2\2\2\u0097\u0098\5]/\2\u0098"+
		"\u0099\5_\60\2\u0099\u009a\5i\65\2\u009a\26\3\2\2\2\u009b\u009c\7?\2\2"+
		"\u009c\30\3\2\2\2\u009d\u009e\7>\2\2\u009e\u009f\7?\2\2\u009f\32\3\2\2"+
		"\2\u00a0\u00a1\7@\2\2\u00a1\u00a2\7?\2\2\u00a2\34\3\2\2\2\u00a3\u00a4"+
		"\7>\2\2\u00a4\36\3\2\2\2\u00a5\u00a6\7@\2\2\u00a6 \3\2\2\2\u00a7\u00a8"+
		"\7#\2\2\u00a8\u00a9\7?\2\2\u00a9\"\3\2\2\2\u00aa\u00ac\t\2\2\2\u00ab\u00aa"+
		"\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\u00af\3\2\2\2\u00af\u00b0\b\22\2\2\u00b0$\3\2\2\2\u00b1\u00b6\7>\2\2"+
		"\u00b2\u00b5\n\3\2\2\u00b3\u00b5\5/\30\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3"+
		"\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7"+
		"\u00b9\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7@\2\2\u00ba&\3\2\2\2\u00bb"+
		"\u00bd\5\67\34\2\u00bc\u00bb\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\3"+
		"\2\2\2\u00be\u00bf\7<\2\2\u00bf(\3\2\2\2\u00c0\u00c1\5\'\24\2\u00c1\u00c2"+
		"\59\35\2\u00c2*\3\2\2\2\u00c3\u00c4\7a\2\2\u00c4\u00c5\7<\2\2\u00c5\u00c8"+
		"\3\2\2\2\u00c6\u00c9\5\63\32\2\u00c7\u00c9\t\4\2\2\u00c8\u00c6\3\2\2\2"+
		"\u00c8\u00c7\3\2\2\2\u00c9\u00d2\3\2\2\2\u00ca\u00cd\5\65\33\2\u00cb\u00cd"+
		"\7\60\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cb\3\2\2\2\u00cd\u00d0\3\2\2\2"+
		"\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d1\3\2\2\2\u00d0\u00ce"+
		"\3\2\2\2\u00d1\u00d3\5\65\33\2\u00d2\u00ce\3\2\2\2\u00d2\u00d3\3\2\2\2"+
		"\u00d3,\3\2\2\2\u00d4\u00d8\t\5\2\2\u00d5\u00d7\t\6\2\2\u00d6\u00d5\3"+
		"\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		".\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\u00dc\7^\2\2\u00dc\u00dd\7w\2\2\u00dd"+
		"\u00de\3\2\2\2\u00de\u00df\5? \2\u00df\u00e0\5? \2\u00e0\u00e1\5? \2\u00e1"+
		"\u00e2\5? \2\u00e2\u00f0\3\2\2\2\u00e3\u00e4\7^\2\2\u00e4\u00e5\7W\2\2"+
		"\u00e5\u00e6\3\2\2\2\u00e6\u00e7\5? \2\u00e7\u00e8\5? \2\u00e8\u00e9\5"+
		"? \2\u00e9\u00ea\5? \2\u00ea\u00eb\5? \2\u00eb\u00ec\5? \2\u00ec\u00ed"+
		"\5? \2\u00ed\u00ee\5? \2\u00ee\u00f0\3\2\2\2\u00ef\u00db\3\2\2\2\u00ef"+
		"\u00e3\3\2\2\2\u00f0\60\3\2\2\2\u00f1\u00f3\t\7\2\2\u00f2\u00f1\3\2\2"+
		"\2\u00f3\62\3\2\2\2\u00f4\u00f7\5\61\31\2\u00f5\u00f7\7a\2\2\u00f6\u00f4"+
		"\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\64\3\2\2\2\u00f8\u00fb\5\63\32\2\u00f9"+
		"\u00fb\t\b\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fb\66\3\2\2"+
		"\2\u00fc\u0105\5\61\31\2\u00fd\u0100\5\65\33\2\u00fe\u0100\7\60\2\2\u00ff"+
		"\u00fd\3\2\2\2\u00ff\u00fe\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u00ff\3\2"+
		"\2\2\u0101\u0102\3\2\2\2\u0102\u0104\3\2\2\2\u0103\u0101\3\2\2\2\u0104"+
		"\u0106\5\65\33\2\u0105\u0101\3\2\2\2\u0105\u0106\3\2\2\2\u01068\3\2\2"+
		"\2\u0107\u010b\5\63\32\2\u0108\u010b\4\62<\2\u0109\u010b\5;\36\2\u010a"+
		"\u0107\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u0109\3\2\2\2\u010b\u0119\3\2"+
		"\2\2\u010c\u0110\5\65\33\2\u010d\u0110\t\t\2\2\u010e\u0110\5;\36\2\u010f"+
		"\u010c\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u010e\3\2\2\2\u0110\u0113\3\2"+
		"\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0117\3\2\2\2\u0113"+
		"\u0111\3\2\2\2\u0114\u0118\5\65\33\2\u0115\u0118\7<\2\2\u0116\u0118\5"+
		";\36\2\u0117\u0114\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0116\3\2\2\2\u0118"+
		"\u011a\3\2\2\2\u0119\u0111\3\2\2\2\u0119\u011a\3\2\2\2\u011a:\3\2\2\2"+
		"\u011b\u011e\5=\37\2\u011c\u011e\5A!\2\u011d\u011b\3\2\2\2\u011d\u011c"+
		"\3\2\2\2\u011e<\3\2\2\2\u011f\u0120\7\'\2\2\u0120\u0121\5? \2\u0121\u0122"+
		"\5? \2\u0122>\3\2\2\2\u0123\u0125\t\n\2\2\u0124\u0123\3\2\2\2\u0125@\3"+
		"\2\2\2\u0126\u0127\7^\2\2\u0127\u0128\t\13\2\2\u0128B\3\2\2\2\u0129\u012a"+
		"\t\f\2\2\u012aD\3\2\2\2\u012b\u012c\t\r\2\2\u012cF\3\2\2\2\u012d\u012e"+
		"\t\16\2\2\u012eH\3\2\2\2\u012f\u0130\t\17\2\2\u0130J\3\2\2\2\u0131\u0132"+
		"\t\20\2\2\u0132L\3\2\2\2\u0133\u0134\t\21\2\2\u0134N\3\2\2\2\u0135\u0136"+
		"\t\22\2\2\u0136P\3\2\2\2\u0137\u0138\t\23\2\2\u0138R\3\2\2\2\u0139\u013a"+
		"\t\24\2\2\u013aT\3\2\2\2\u013b\u013c\t\25\2\2\u013cV\3\2\2\2\u013d\u013e"+
		"\t\26\2\2\u013eX\3\2\2\2\u013f\u0140\t\27\2\2\u0140Z\3\2\2\2\u0141\u0142"+
		"\t\30\2\2\u0142\\\3\2\2\2\u0143\u0144\t\31\2\2\u0144^\3\2\2\2\u0145\u0146"+
		"\t\32\2\2\u0146`\3\2\2\2\u0147\u0148\t\33\2\2\u0148b\3\2\2\2\u0149\u014a"+
		"\t\34\2\2\u014ad\3\2\2\2\u014b\u014c\t\35\2\2\u014cf\3\2\2\2\u014d\u014e"+
		"\t\36\2\2\u014eh\3\2\2\2\u014f\u0150\t\37\2\2\u0150j\3\2\2\2\u0151\u0152"+
		"\t \2\2\u0152l\3\2\2\2\u0153\u0154\t!\2\2\u0154n\3\2\2\2\u0155\u0156\t"+
		"\"\2\2\u0156p\3\2\2\2\u0157\u0158\t#\2\2\u0158r\3\2\2\2\u0159\u015a\t"+
		"$\2\2\u015at\3\2\2\2\u015b\u015c\t%\2\2\u015cv\3\2\2\2\32\2\u00ad\u00b4"+
		"\u00b6\u00bc\u00c8\u00cc\u00ce\u00d2\u00d8\u00ef\u00f2\u00f6\u00fa\u00ff"+
		"\u0101\u0105\u010a\u010f\u0111\u0117\u0119\u011d\u0124\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}