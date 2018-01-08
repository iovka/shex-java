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

package fr.univLille.cristal.shex.schema.analysis;

import java.util.Map;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.AbstractASTElement;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;

/** An instrumentation allows to add an attribute to some element of the abstract syntax; see {@link AbstractASTElement}, {@link ASTAttributes}.
 * For instance, to a {@link TripleExpr} one might want to associate the set of properties used in the {@link TripleExpr}, or a modified {@link TripleExpr} in which all repeated subexpressions are unfolded. 
 * @author Iovka Boneva
 * 12 oct. 2017
 * @param <AppliesOn> The type of element on which this instrumentation
 * @param <Type>
 */
public abstract class Instrumentation<AppliesOn extends ASTElement, Type> {


	private final Object key;

	/** The key under which this instrumentation puts its information in the map of attributes.
	 * 
	 * @return
	 */
	public final Object getKey() {
		return this.key;
	}

	
	protected Instrumentation(final String keyString) {
		this.key = new Object() {
			@Override
			public String toString() {
				return keyString;
			}
		};
	}
	
	/** Computes and adds the value for the instrumentation.
	 * 
	 * @param on The object on which the instrumentation is to be added
	 * @param context Additinal arguments whenever needed.
	 * @return The value for the instrumentation
	 */
	public final Type apply(AppliesOn on, Object ... context) {
		if (on.getDynamicAttributes().containsKey(getKey()))
			return (Type) (on.getDynamicAttributes().get(getKey()));
		Type value = compute(on, context);
		on.getDynamicAttributes().put(getKey(), value);
		return value;
	}
	
	/** Computes the value for the instrumentation without adding it.
	 * 
	 * @param on
	 * @param context
	 * @return
	 */
	public abstract Type compute (AppliesOn on, Object ... context);
	
	/** Applies the Instrumentation on all {@link AbstractASTElement}s that have the appropriate type AppliesOn, in all rules of the schema
	 * 
	 * @param rules
	 */
	public void apply (Map<ShapeExprLabel, ShapeExpr> rules) {
		
	}
		
}
