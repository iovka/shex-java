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
package fr.inria.lille.shexjava.validation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

/**
 * @author Jérémie Dusart
 *
 */
public class RecursiveTyping implements Typing {
	private Set<Pair<RDFTerm, Label>> typing;

	public RecursiveTyping() {
		typing = new HashSet<Pair<RDFTerm, Label>>();
	}

	@Override
	public boolean contains(RDFTerm node, Label label) {
		return typing.contains(new Pair<RDFTerm, Label>(node,label));
	}

	@Override
	public Set<Pair<RDFTerm, Label>> asSet() {
		return typing;
	}
	
	public void addHypothesis(RDFTerm node, Label label) {
		typing.add(new Pair<RDFTerm, Label>(node,label));
	}
	
	public void addHypothesis(Set<Pair<RDFTerm,Label>> hypothesis) {
		typing.addAll(hypothesis);
	}
	
	public void removeHypothesis(RDFTerm node, Label label) {
		typing.remove(new Pair<RDFTerm, Label>(node,label));
	}
	
	public void removeHypothesis(Set<Pair<RDFTerm,Label>> hypothesis) {
		typing.removeAll(hypothesis);
	}	

}
