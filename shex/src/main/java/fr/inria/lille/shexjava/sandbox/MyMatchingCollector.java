/*******************************************************************************
 * Copyright (C) 2019 Université de Lille - Inria
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

package fr.inria.lille.shexjava.sandbox;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.validation.LocalMatching;
import fr.inria.lille.shexjava.validation.MatchingCollector;
import fr.inria.lille.shexjava.util.Pair;

/**
 * @author Iovka Boneva
 *
 */
public class MyMatchingCollector extends MatchingCollector {
	
	protected Set<Pair<RDFTerm, Label>> unsuccessfulMatchings = new HashSet<>();

	@Override
	public void updateMatching (RDFTerm node, Label label, LocalMatching matching) {
		Pair<RDFTerm, Label> key = new Pair<>(node, label);
		if (matching == null && matchings.containsKey(key))
			matchings.remove(key);
		else if (matching != null)
			matchings.put(key, matching);
		else 
			unsuccessfulMatchings.add(key);
	}
	
	public Set<Pair<RDFTerm, Label>> getUnsuccessfulMatchings () {
		return unsuccessfulMatchings;
	}
}
