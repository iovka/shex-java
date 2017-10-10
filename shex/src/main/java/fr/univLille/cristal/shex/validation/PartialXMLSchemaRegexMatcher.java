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

package fr.univLille.cristal.shex.validation;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class PartialXMLSchemaRegexMatcher implements XMLSchemaRegexMatcher {

	@Override
	public boolean matches(String string, String pattern) {
		pattern = pattern.replace("^", "\\^");
		pattern = pattern.replace("$", "\\$");
		//System.out.println("Warning: regex pattern matching might not be correct w.r.t. the XMLSchema Regular expressions definition.");
		return string.matches(pattern);
	}
	

}
