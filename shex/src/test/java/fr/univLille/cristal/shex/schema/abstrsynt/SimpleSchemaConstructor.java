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


package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedLabelException;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SimpleSchemaConstructor {
	public static ShapeExprLabel slAll = newShapeLabel("SL_ALL"); 
	public static ShapeExprLabel slInt = newShapeLabel("SL_INT");
	public static ShapeExprLabel slString = newShapeLabel("SL_STRING");
	public final static String PREFIX = "http://a.ex#";

	private ShexSchema schema = new ShexSchema();
	
	public ShexSchema getSchema () throws UndefinedLabelException, CyclicReferencesException, NotStratifiedException {
		schema.finalize();
		return this.schema;
	}
	
	
	public void addRule(String label, ShapeExpr vexpr) {
		schema.put(newShapeLabel(label), vexpr);
	}
	
	public void addSlallRule () {
		if (! schema.containsKey(slAll))
			schema.put(slAll, new NodeConstraint(SetOfNodes.AllNodes));
	}
	public void addSlintRule () {
		if (! schema.containsKey(slInt))
			schema.put(slInt, new NodeConstraint(new DatatypeSetOfNodes(XMLSchema.INT)));
	}
	public void addSlstringRule () {
		if (! schema.containsKey(slString))
			schema.put(slString, new NodeConstraint(new DatatypeSetOfNodes(XMLSchema.STRING)));
	}

	
	// Parses a possibly repeated triple expression
	public static TripleExpr tc (String s) {
		if (s.contains("#"))
			return reptc(s);
		else
			return simpletc(s);
	}
	
	
	private static TripleConstraint simpletc (String tc) {
		String[] s = tc.split("::");
		s[0] = s[0].trim();
		s[1] = s[1].trim();
		
		TCProperty prop;
		ShapeExprRef ref;
		
		switch(s[1]) {
		case "."   : ref = new ShapeExprRef(slAll); break;
		case "int" : ref = new ShapeExprRef(slInt); break;
		case "string": ref = new ShapeExprRef(slString); break;
		default    : ref = new ShapeExprRef(newShapeLabel(s[1]));  
		}
		
		String propString = s[0];
		prop = TCProperty.createFwProperty(SimpleValueFactory.getInstance().createIRI(PREFIX+propString));
		
		return TripleConstraint.newSingleton(prop, ref);
	}
	
	static RepeatedTripleExpression reptc (String x) {
		String[] s = x.split("#");
		
		asrt(s.length == 2, "");
		s[0] = s[0].trim();
		s[1] = s[1].trim();
		
		String[] t = s[1].split(" ");
		t[0] = t[0].trim();
		t[1] = t[1].trim();
		
		int min = Integer.parseInt(t[0]);
		int max;
		if (t[1].equals("*"))
			max = Interval.UNBOUND;
		else
			max = Integer.parseInt(t[1]);
		
		TripleConstraint tc = simpletc(s[0]);
		return new RepeatedTripleExpression(tc, new Interval(min, max));
	}

	public static OneOf someof (Object... arg) {
		if (arg.length == 1 && arg[0] instanceof String)
			return someofParse((String) arg[0]);
		else
			return new OneOf(getTeExpressions(arg));
	}
	
	public static EachOf eachof (Object... arg) {
		if (arg.length == 1 && arg[0] instanceof String)
			return eachofParse((String) arg[0]);
		else
			return new EachOf(getTeExpressions(arg));
	}

	private static List<TripleExpr> getTeExpressions (Object... arg) {
		List<TripleExpr> list = new ArrayList<>();
		for (Object a : arg) {
			asrt((a instanceof TripleExpr), "Not a triple expression " + a.toString());
			list.add((TripleExpr)a);
		}
		return list;
	}
	
	// Parses a some-of expression which sub-expressions are all triple constraints with possible cardinalities
	private static OneOf someofParse(String s) {
		return new OneOf(nary(s.split("\\|")));
	}
	
	// Parses a each-of expression which sub-expressions are all triple constraints with possible cardinalities
	private static EachOf eachofParse(String s) {
		return new EachOf(nary(s.split(";")));
	}
	
	private static List<TripleExpr> nary(String[] x) {
		List<TripleExpr> l = new ArrayList<>();
		for (String y: x) {
			l.add(tc(y));
		}
		return l;
	}

	public static ShapeExpr se (Object o) {
		if (o instanceof TripleExpr)
			return new Shape((TripleExpr)o);
		else if (o instanceof ShapeExpr)
			return (ShapeExpr) o;
		else 
			throw new UnsupportedOperationException("unknown expr type " + o.getClass());
	}
	
	
	private static List<ShapeExpr> getSeExpressions (Object ... exprs) {
		ArrayList<ShapeExpr> subExpr = new ArrayList<>();
		for (Object o : exprs) {
			subExpr.add(se(o));
		}
		return subExpr;
	}
	
	
	public static ShapeNot not (Object o) {
		return new ShapeNot(se(o));
	}

	public static ShapeAnd shapeAnd (Object ... exprs) {
		return new ShapeAnd(getSeExpressions(exprs));
	}
	
	public static ShapeOr shapeOr (Object ... exprs) {
		return new ShapeOr(getSeExpressions(exprs));
	}

	
	private static void asrt(boolean b, String message) {
		if (!b) throw new RuntimeException(message);
	}

	public static ShapeExprLabel newShapeLabel (String label){
		return new ShapeExprLabel(SimpleValueFactory.getInstance().createIRI(PREFIX + label));
	}
	
}
