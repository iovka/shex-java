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
package fr.univLille.cristal.shex.schema.concrsynt;

import java.util.Set;

import org.eclipse.rdf4j.model.Value;

public abstract class StemRangeConstraint implements Constraint {
	private Constraint stem;
	private ValueSetValueConstraint exclusions;
	
	public StemRangeConstraint(Constraint stem,ValueSetValueConstraint exclusions) {
		this.stem = stem;
		this.exclusions = exclusions;
	}

	@Override
	public boolean contains(Value node) {
		if (stem!=null)
			if (!stem.contains(node))
			 return false;
		
		if (exclusions.contains(node))
			return false;
		
		return true;
	}
	
	public String toString() {
		return "StemRange=("+stem+" exclusions="+exclusions+")";
	}

}
