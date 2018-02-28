package fr.univLille.cristal.shex.schema.parsing.ShExC;

import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ShExCErrorListener extends ConsoleErrorListener {

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

}
