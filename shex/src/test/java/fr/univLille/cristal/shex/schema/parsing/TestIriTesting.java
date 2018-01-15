package fr.univLille.cristal.shex.schema.parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class TestIriTesting {
	private final static Pattern IRI_PATTERN = Pattern.compile("([^#x[00-20]<>\"\\{\\}|^`\\\\]|.)*");

	@Test
	void test() {
		if (IRI_PATTERN.matcher("Test").matches()) {
			fail("Should fail...");		
		}
	}

}
