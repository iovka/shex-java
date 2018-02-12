package sandbox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;



public class TestRegularExpressions {
	
	@Test
	public void testDollar() {
		
		assertTrue  ( "ab".matches("ab$")  );
		assertFalse ( "xab".matches("ab$") );
		
		Pattern pattern = Pattern.compile("ab$");
		assertTrue ( pattern.matcher("ab").find() );
		assertTrue ( pattern.matcher("xab").find());

	}

	@Test
	public void testDollarInString() {
		assertFalse  (  "ab$".matches("ab$")  );
		assertTrue   (  "ab$".matches("ab\\$"));
		
		assertFalse  (  "^ab".matches("^ab")  );
		assertTrue   (  "^ab".matches("\\^ab"));
	}
	
}
