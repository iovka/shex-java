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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class MatchingCollector {
	private Map<Pair<RDFTerm, Label>, LocalMatching> matchings;

	public MatchingCollector() {
		matchings = new HashMap<>();
	}
	
	public void updateMatching (RDFTerm node, Label label, LocalMatching matching) {
		Pair<RDFTerm, Label> key = new Pair<>(node, label);
		if (matching == null && matchings.containsKey(key))
			matchings.remove(key);
		else if (matching != null)
			matchings.put(key, matching);
	}
	
	public LocalMatching getMatching (RDFTerm node, Label label) {
		return matchings.get(new Pair<>(node, label));
	}

	public void startValidation() {
		// Nothing to do
	}

	public void validationComplete() {
		// Nothing to do 
	}
	
	
	
	/*
	public void setMatch(RDFTerm node, Label label, List<Pair<Triple, Label>> match) {
		matchings.put(new Pair<RDFTerm, Label>(node,label), match);	
	}
	
	public List<Pair<Triple, Label>> getMatch(RDFTerm node, Label label) {
		return matchings.get(new Pair<RDFTerm, Label>(node,label));		
	}
	
	public void removeMatch(RDFTerm node, Label label) {
		matchings.remove(new Pair<RDFTerm, Label>(node,label));	
	}
	*/
	
}
