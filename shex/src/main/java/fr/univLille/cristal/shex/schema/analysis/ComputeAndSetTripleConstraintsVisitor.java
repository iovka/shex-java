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

import java.util.ArrayList;
import java.util.List;

import fr.univLille.cristal.shex.schema.abstrsynt.EachOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.SomeOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.util.SubList;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ComputeAndSetTripleConstraintsVisitor extends TripleExpressionVisitor<Void> {
	
	private List<TripleConstraint> theList = new ArrayList<>();
	private Object propertyKey;
	
	public ComputeAndSetTripleConstraintsVisitor(Object propertyKey) {
		this.propertyKey = propertyKey;
	}

	@Override
	public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
		int begin = theList.size();
		theList.add(tc);
		int end = theList.size();
		tc.getAttributes().setAttribute(propertyKey, new SubList(theList, begin, end));
		//TODO remove tc.setTripleConstraints();
	}

	@Override
	public void visitEmpty(EmptyTripleExpression emptyTripleExpression, Object[] arguments) {
		int beginEnd = theList.size();
		emptyTripleExpression.getAttributes().setAttribute(propertyKey, new SubList(theList, beginEnd, beginEnd));
	}		
	
	@Override
	public Void getResult() {
		return null;
	}
	
	@Override
	public void visitEachOf(EachOfTripleExpression expr, Object... arguments) {
		int begin = theList.size();
		super.visitEachOf(expr, arguments);
		int end = theList.size();
		expr.getAttributes().setAttribute(propertyKey, new SubList(theList, begin, end));
		//TODO remove expr.setTripleConstraints(new SubList(theList, begin, end));
	}

	@Override
	public void visitSomeOf(SomeOfTripleExpression expr, Object... arguments) {
		int begin = theList.size();
		super.visitSomeOf(expr, arguments);
		int end = theList.size();
		expr.getAttributes().setAttribute(propertyKey, new SubList(theList, begin, end));
		//TODO removeexpr.setTripleConstraints(new SubList(theList, begin, end));
	}

	@Override
	public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
		int begin = theList.size();
		super.visitRepeated(expr, arguments);
		int end = theList.size();
		expr.getAttributes().setAttribute(propertyKey, new SubList(theList, begin, end));
		//TODO remove expr.setTripleConstraints(new SubList(theList, begin, end));
	}
	
}