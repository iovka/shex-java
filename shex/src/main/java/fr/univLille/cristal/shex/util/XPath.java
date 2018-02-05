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

package fr.univLille.cristal.shex.util;

import net.sf.saxon.expr.EarlyEvaluationContext;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.functions.Matches;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.linked.TextImpl;
import net.sf.saxon.value.AtomicValue;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class XPath {
	public static XPathContext context = new EarlyEvaluationContext((new Processor(false)).getUnderlyingConfiguration());
	public static Matches matcher = new Matches();
	
	
	/** Respecting syntaxe in 3.1
	 * 
	 */
	public static boolean matches(String input, String regex, String flags) {
		AtomicValue inputAV = new XdmAtomicValue(input).getUnderlyingValue();
		AtomicValue regexAV = new XdmAtomicValue(regex).getUnderlyingValue();
		if (flags == null) flags = "";
		try {
			return matcher.evalMatches(inputAV, regexAV, flags, context);
		} catch (XPathException e) {
			//e.printStackTrace();
			return false;
		}
	}

	
}
