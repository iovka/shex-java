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

/** Used to represent the status of node w.r.t. its validity for an expression in some schema.
 * @see Typing
 * 
 * @author Iovka Boneva
 * 2 août 2018
 */
public enum Status {
	/** Indicates that the node satisfies the expression. */
	CONFORMANT,
	/** The node does not satisfy the expression. */
	NONCONFORMANT,
	/** The status is unknown as it has not yet beet computed by the validation algorithm.
	 * In order to determine the status, one should run {@link ValidationAlgorithm#validate(org.apache.commons.rdf.api.RDFTerm, fr.inria.lille.shexjava.schema.Label)}
	 * with the corresponding node and expression (label). */
	NOTCOMPUTED;
}
