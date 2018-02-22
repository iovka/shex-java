/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.concrsynt.Constraint;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeConstraint;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SimpleSchemaConstructor {
	public static Label slAll = newShapeLabel("SL_ALL"); 
	public static Label slInt = newShapeLabel("SL_INT");
	public static Label slString = newShapeLabel("SL_STRING");
	public final static String PREFIX = "http://a.ex#";

	private Map<Label,ShapeExpr> rules = null;
	private ShexSchema schema;
	
	public SimpleSchemaConstructor() {
		this.rules = new HashMap<Label,ShapeExpr>();
	}
	
	public ShexSchema getSchema () throws UndefinedReferenceException, CyclicReferencesException, NotStratifiedException {
		this.schema = new ShexSchema(rules);
		return schema;
	}
	
	
	public void addRule(String label, ShapeExpr vexpr) {
		vexpr.setId(newShapeLabel(label));
		rules.put(vexpr.getId(), vexpr);
	}
	
	public void addSlallRule () {
		if (! rules.containsKey(slAll))
			rules.put(slAll, EmptyShape.Shape);
	}
	public void addSlintRule () {
		if (! rules.containsKey(slInt)) {
			List<Constraint> consts = new ArrayList<Constraint>();
			consts.add(new DatatypeConstraint(XMLSchema.INT));
			rules.put(slInt, new NodeConstraint(consts));
			}
	}
	public void addSlstringRule () {
		if (! rules.containsKey(slString)) {
			List<Constraint> consts = new ArrayList<Constraint>();
			consts.add(new DatatypeConstraint(XMLSchema.STRING));
			rules.put(slString, new NodeConstraint(consts));
		}
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

		return new TripleConstraint(prop, ref);
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
			return new Shape((TripleExpr)o, Collections.EMPTY_SET,true);
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

	public static Label newShapeLabel (String label){
		return new Label(SimpleValueFactory.getInstance().createIRI(PREFIX + label));
	}
	
}
