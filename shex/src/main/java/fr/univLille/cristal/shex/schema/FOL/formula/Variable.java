package fr.univLille.cristal.shex.schema.FOL.formula;

import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class Variable  implements Sentence{
	protected String name;

	public Variable(String name){
		this.name = name;
	}

	@Override
	public boolean evaluate(Map<Variable,Value> affectations,
							Set<Pair<Value, Label>> shapes,
							Set<Pair<Pair<Value,Value>, Label>> triples) {
		// TODO Auto-generated method stub
		return false;
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
