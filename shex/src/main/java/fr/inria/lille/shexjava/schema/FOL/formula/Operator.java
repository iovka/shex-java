package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.ArrayList;
import java.util.List;


public abstract class Operator implements Sentence{
	protected Variable v1;
	protected Variable v2;
	
	public Operator(Variable v1,Variable v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	
	public List<Variable> getVariables() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(v1);
		res.add(v2);
		return res;
	}

	
}