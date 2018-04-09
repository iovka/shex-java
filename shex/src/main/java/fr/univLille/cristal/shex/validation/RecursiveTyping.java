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
package fr.univLille.cristal.shex.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.graph.NeighborTriple;
import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

/**
 * @author Jérémie Dusart
 *
 */
public class RecursiveTyping implements Typing {
	private Set<Pair<Value, Label>> typing;
	private Map<Pair<Value, Label>,List<Pair<NeighborTriple,Label>>> matching;

	public RecursiveTyping() {
		typing = new HashSet<Pair<Value, Label>>();
		matching = new HashMap<Pair<Value, Label>,List<Pair<NeighborTriple,Label>>>();
	}

	@Override
	public boolean contains(Value node, Label label) {
		return typing.contains(new Pair<Value, Label>(node,label));
	}

	@Override
	public Set<Pair<Value, Label>> asSet() {
		return typing;
	}
	
	public void addHypothesis(Value node, Label label) {
		typing.add(new Pair<Value, Label>(node,label));
	}
	
	public void addHypothesis(Set<Pair<Value,Label>> hypothesis) {
		typing.addAll(hypothesis);
	}
	
	public void removeHypothesis(Value node, Label label) {
		Pair<Value, Label> hyp = new Pair<Value, Label>(node,label);
		typing.remove(hyp);
		if (matching.containsKey(hyp))
			matching.remove(hyp);
	}
	
	
	public void removeHypothesis(Set<Pair<Value,Label>> hypothesis) {
		typing.removeAll(hypothesis);
		for (Pair<Value,Label> key:hypothesis)
			if (matching.containsKey(key))
				matching.remove(key);
	}	

	@Override
	public List<Pair<NeighborTriple, Label>> getMatch(Value node, Label label) {
		if (matching.containsKey(new Pair<Value, Label>(node,label)))
			return matching.get(new Pair<Value, Label>(node,label));		
		return null;
	}

	@Override
	public void setMatch(Value node, Label label, List<Pair<NeighborTriple, Label>> match) {
		matching.put(new Pair<Value, Label>(node,label), match);	
	}

	@Override
	public void removeMatch(Value node, Label label) {
		matching.remove(new Pair<Value, Label>(node,label));	
	}
}
