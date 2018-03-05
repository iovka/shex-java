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

/**
 * @author Jérémie Dusart
 *
 */
public class ValueSetValueConstraint implements Constraint {
	private Set<Value> explicitValues;
	private Set<Constraint> constraintsValue;

	public ValueSetValueConstraint(Set<Value> explicitValues, Set<Constraint> constraintsValue) {
		this.explicitValues = explicitValues;
		this.constraintsValue = constraintsValue;
	}
	
	public Set<Value> getExplicitValues() {
		return explicitValues;
	}

	public Set<Constraint> getConstraintsValue() {
		return constraintsValue;
	}

	@Override
	public boolean contains(Value node) {
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
	
}
