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
package fr.univLille.cristal.shex.schema;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;

/**
 * 
 * @author Iovka Boneva
 * 11 oct. 2017
 */
public class ShapeExprLabel extends Label {

	public ShapeExprLabel(BNode bnode) {
		super(bnode);
	}

	public ShapeExprLabel(IRI iri) {
		super(iri);
	}

	public ShapeExprLabel(BNode bnode, boolean generated) {
		super(bnode, generated);
	}

	public ShapeExprLabel(IRI iri, boolean generated) {
		super(iri, generated);
	}
	
	
}
