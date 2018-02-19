package fr.univLille.cristal.shex.runningTests;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
class TestResultForTestReport {
	String name;
	boolean passed; // should be "passed" or "failed"
	String description;
	String testType;
	
	public TestResultForTestReport(String name, boolean passed, String description, String testType) {
		this.name = name;
		this.passed = passed;
		this.description = description;
		this.testType = testType;
	}
}