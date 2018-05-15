package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class Variable  implements Sentence{
	protected String name;

	public Variable(String name){
		this.name = name;
	}

	@Override
	public int evaluate(Map<Variable, RDFTerm> affectations,
							Set<Pair<RDFTerm, Label>> shapes,
							Set<Pair<Pair<RDFTerm, RDFTerm>, Label>> triples) throws Exception {
		throw new Exception("Trying to evaluate variable: "+name);
	}
	
	
	public String getName() {
		return name;
	}

	@Override 
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Variable))
			return false;
		return name.equals(((Variable) o).getName());
	}
	
	@Override
	public String toString() {
		return name;
	}

}
