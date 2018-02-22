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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.util.Pair;

public class RecursiveTyping implements Typing {
	private Set<Pair<Value, Label>> typing;
	private Set<Pair<Value, Label>> lastSetOfHyp;

	public RecursiveTyping() {
		typing = new HashSet<Pair<Value, Label>>();
		lastSetOfHyp = new HashSet<Pair<Value, Label>>();
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
		lastSetOfHyp.add(new Pair<Value, Label>(node,label));
	}
	
	public void removeHypothesis(Value node, Label label) {
		typing.remove(new Pair<Value, Label>(node,label));
	}
	
	public void keepLastSessionOfHypothesis() {
		lastSetOfHyp = new HashSet<Pair<Value, Label>>();
	}
	
	public void removeLastSessionOfHypothesis() {
		for (Pair<Value, Label> hyp:lastSetOfHyp)
			typing.remove(hyp);		
		lastSetOfHyp = new HashSet<Pair<Value, Label>>();
	}

}
