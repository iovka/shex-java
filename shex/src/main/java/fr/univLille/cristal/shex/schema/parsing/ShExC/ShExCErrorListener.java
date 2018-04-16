package fr.univLille.cristal.shex.schema.parsing.ShExC;

import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ShExCErrorListener implements ANTLRErrorListener {

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
			Object offendingSymbol,
			int line,
			int charPositionInLine,
			String msg,
			RecognitionException e) {
		String message = ("line " + line + ":" + charPositionInLine + " " + msg);
		throw new ParseCancellationException(message);
	}

	@Override
	public void reportAmbiguity(Parser recognizer,
								DFA dfa,
								int startIndex,
								int stopIndex,
								boolean exact,
								BitSet ambigAlts,
								ATNConfigSet configs){
	}

	@Override
	public void reportAttemptingFullContext(Parser recognizer,
											DFA dfa,
											int startIndex,
											int stopIndex,
											BitSet conflictingAlts,
											ATNConfigSet configs) {
	}

	@Override
	public void reportContextSensitivity(Parser recognizer,
										 DFA dfa,
										 int startIndex,
										 int stopIndex,
										 int prediction,
										 ATNConfigSet configs) {	
	}

}
