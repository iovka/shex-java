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

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.graph.RDFGraph;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;

/** <p>Allows to validate a graph against a ShEx schema.</p>
 * 
 * <p>A {@link ValidationAlgorithm} must be initialized with an {@link RDFGraph} and a {@link ShexSchema}.
 * Then the {@link #validate(Value, Label)} method allows to construct a typing of the graph by the schema.
 * The {@link #getTyping()} method allows to retrieve the typing constructed by {@link #validate(Value, Label)}.</p>
 * 
 * <p>The method {@link #validate(Value, Label)} can take as parameter a node and a shape label, which both can be <code>null</code>.</p>
 * 
 * 
  * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface ValidationAlgorithm {

	/** Constructs a typing that allows to validate a focus node against a type.
	 * return true is focusNode has shape label, false otherwise.
	 * The constructed typing can be retrieved using {@link #getTyping()}
	 * 
	 * @param focusNode The focus node for which the typing is to be complete. If null, then the typing will be complete for all nodes. The node might not belong to the graph, in which case it does not have a neighborhood.
	 * @param label The label for which the typing is to be complete. If null, then the typing will be complete for all labels.
	 */
	public boolean validate(Value focusNode, Label label)  throws Exception;
	
	/** Retrieves the typing constructed by a previous call of {@link #validate(Value, Label)}.
	 * 
	 */
	public Typing getTyping();
}
