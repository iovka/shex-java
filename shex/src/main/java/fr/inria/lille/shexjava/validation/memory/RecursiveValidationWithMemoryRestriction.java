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
package fr.inria.lille.shexjava.validation.memory;

import java.util.stream.Stream;

import org.apache.commons.rdf.api.Graph;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.validation.RecursiveValidation;

/**
 * 
 * @author Iovka Boneva
 *
 */
public class RecursiveValidationWithMemoryRestriction extends RecursiveValidation {

	// TODO: il faut changer le typingForValidation dans RecursiveValidation
	
	public RecursiveValidationWithMemoryRestriction(ShexSchema schema, Graph graph) {
		super(schema, graph);
	}
	
	
	
}
