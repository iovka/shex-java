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

import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.util.Pair;

/** A set of associations (resource, shape labels).
 * Is produced as result of a validation, see {@link ValidationAlgorithm}
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface Typing {

	/** Checks whether a node-label association belongs to the typing. 
	 * 
	 * @param node
	 * @param label
	 * @return
	 */
	public boolean contains(Value node, ShapeExprLabel label);
	
	/** Returns the typing as a set of pairs (node, label).
	 * 
	 * @return
	 */
	public Set<Pair<Value, ShapeExprLabel>> asSet(); // For testing purposes

}
