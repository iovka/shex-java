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
package fr.inria.lille.shexjava.schema.concrsynt;

import java.util.Set;

import org.apache.commons.rdf.api.RDFTerm;

/**
 * @author Jérémie Dusart
 *
 */
public class ValueSetValueConstraint implements Constraint {
	private Set<RDFTerm> explicitValues;
	private Set<Constraint> constraintsValue;

	public ValueSetValueConstraint(Set<RDFTerm> explicitValues, Set<Constraint> constraintsValue) {
		this.explicitValues = explicitValues;
		this.constraintsValue = constraintsValue;
	}
	
	public Set<RDFTerm> getExplicitValues() {
		return explicitValues;
	}

	public Set<Constraint> getConstraintsValue() {
		return constraintsValue;
	}

	@Override
	public boolean contains(RDFTerm node) {
		if (explicitValues.contains(node))
			return true;
		
		for (Constraint constraint:constraintsValue)
			if (constraint.contains(node))
				return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "values:"+explicitValues+ " U "+constraintsValue;
	}
	
	@Override
	public String toPrettyString() {
		String result = "";
		if (explicitValues.size()>0)
			result += " values:"+explicitValues+" ";
		if (constraintsValue.size()>0) {
			if (!result.equals(""))
				result+="U";
			result += " constraints:"+constraintsValue+" ";
		}
		return result;
	}
	
}
