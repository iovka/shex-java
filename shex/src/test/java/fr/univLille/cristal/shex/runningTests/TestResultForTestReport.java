/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

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