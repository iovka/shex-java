package fr.univLille.cristal.shex.schema.parsing.ShExC;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ShExCErrorStrategy extends DefaultErrorStrategy{

	@Override
	public void recover(Parser recognizer, RecognitionException e) {
		for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
			context.exception = e;
		}
		
		int line = recognizer.getCurrentToken().getLine();
		int charPositionInLine = recognizer.getCurrentToken().getCharPositionInLine();
		String msg = recognizer.getCurrentToken().getText();
		String message = ("line " + line + ":" + charPositionInLine + " " + msg);

		throw new ParseCancellationException(message,e);
	}

	/** Make sure we don't attempt to recover inline; if the parser
	 *  successfully recovers, it won't throw an exception.
	 */
	@Override
	public Token recoverInline(Parser recognizer)
			throws RecognitionException
	{
		InputMismatchException e = new InputMismatchException(recognizer);
		for (ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent()) {
			context.exception = e;
		}
		
		int line = recognizer.getCurrentToken().getLine();
		int charPositionInLine = recognizer.getCurrentToken().getCharPositionInLine();
		String msg = recognizer.getCurrentToken().getText();
		String message = ("line " + line + ":" + charPositionInLine + " " + msg);

		throw new ParseCancellationException(message,e);
	}

	/** Make sure we don't attempt to recover from problems in subrules. */
	@Override
	public void sync(Parser recognizer) { }

}
