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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.NeighbourhoodConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAndExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeDefinition;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpression;
import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNotExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOrExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeRef;
import fr.univLille.cristal.shex.schema.abstrsynt.SomeOfTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpression;
import fr.univLille.cristal.shex.schema.concrsynt.DatatypeSetOfNodes;
import fr.univLille.cristal.shex.schema.concrsynt.SetOfNodes;
import fr.univLille.cristal.shex.util.Interval;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class SimpleSchemaConstructor {
	public static ShapeLabel slAll = newShapeLabel("SL_ALL"); 
	public static ShapeLabel slInt = newShapeLabel("SL_INT");
	public static ShapeLabel slString = newShapeLabel("SL_STRING");
	public final static String PREFIX = "http://a.ex#";

	private Map<ShapeLabel, ShapeDefinition> rulesMap = new LinkedHashMap<>();
	
	public void clearRuleMaps () {
		rulesMap.clear();
	}
	
	public void addRule(String label, ShapeExpression vexpr) {
		rulesMap.put(newShapeLabel(label), new ShapeDefinition(vexpr));
	}
	
	public void addSlallRule () {
		if (! rulesMap.containsKey(slAll))
			rulesMap.put(slAll, new ShapeDefinition(new NodeConstraint(SetOfNodes.AllNodes)));
	}
	public void addSlintRule () {
		if (! rulesMap.containsKey(slInt))
			rulesMap.put(slInt, new ShapeDefinition(new NodeConstraint(new DatatypeSetOfNodes(XMLSchema.INT))));
	}
	public void addSlstringRule () {
		if (! rulesMap.containsKey(slString))
			rulesMap.put(slString, new ShapeDefinition(new NodeConstraint(new DatatypeSetOfNodes(XMLSchema.STRING))));
	}
	
	public Map<ShapeLabel, ShapeDefinition> getRulesMap() {
		return rulesMap;
	}
	
	// Parses a possibly repeated triple expression
	public static TripleExpression tc (String s) {
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
		ShapeRef ref;
		
		switch(s[1]) {
		case "."   : ref = new ShapeRef(slAll); break;
		case "int" : ref = new ShapeRef(slInt); break;
		case "string": ref = new ShapeRef(slString); break;
		default    : ref = new ShapeRef(newShapeLabel(s[1]));  
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

	public static SomeOfTripleExpression someof (Object... arg) {
		if (arg.length == 1 && arg[0] instanceof String)
			return someofParse((String) arg[0]);
		else
			return new SomeOfTripleExpression(getTeExpressions(arg));
	}
	
	public static EachOfTripleExpression eachof (Object... arg) {
		if (arg.length == 1 && arg[0] instanceof String)
			return eachofParse((String) arg[0]);
		else
			return new EachOfTripleExpression(getTeExpressions(arg));
	}

	private static List<TripleExpression> getTeExpressions (Object... arg) {
		List<TripleExpression> list = new ArrayList<>();
		for (Object a : arg) {
			asrt((a instanceof TripleExpression), "Not a triple expression " + a.toString());
			list.add((TripleExpression)a);
		}
		return list;
	}
	
	// Parses a some-of expression which sub-expressions are all triple constraints with possible cardinalities
	private static SomeOfTripleExpression someofParse(String s) {
		return new SomeOfTripleExpression(nary(s.split("\\|")));
	}
	
	// Parses a each-of expression which sub-expressions are all triple constraints with possible cardinalities
	private static EachOfTripleExpression eachofParse(String s) {
		return new EachOfTripleExpression(nary(s.split(";")));
	}
	
	private static List<TripleExpression> nary(String[] x) {
		List<TripleExpression> l = new ArrayList<>();
		for (String y: x) {
			l.add(tc(y));
		}
		return l;
	}

	public static ShapeExpression se (Object o) {
		if (o instanceof TripleExpression)
			return new NeighbourhoodConstraint((TripleExpression)o);
		else if (o instanceof ShapeExpression)
			return (ShapeExpression) o;
		else 
			throw new UnsupportedOperationException("unknown expr type " + o.getClass());
	}
	
	
	private static List<ShapeExpression> getSeExpressions (Object ... exprs) {
		ArrayList<ShapeExpression> subExpr = new ArrayList<>();
		for (Object o : exprs) {
			subExpr.add(se(o));
		}
		return subExpr;
	}
	
	
	public static ShapeNotExpression not (Object o) {
		return new ShapeNotExpression(se(o));
	}

	public static ShapeAndExpression shapeAnd (Object ... exprs) {
		return new ShapeAndExpression(getSeExpressions(exprs));
	}
	
	public static ShapeOrExpression shapeOr (Object ... exprs) {
		return new ShapeOrExpression(getSeExpressions(exprs));
	}

	
	private static void asrt(boolean b, String message) {
		if (!b) throw new RuntimeException(message);
	}

	private static ShapeLabel newShapeLabel (String label){
		return new ShapeLabel(label);
	}
	
}