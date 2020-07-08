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

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import org.apache.commons.rdf.api.RDFTerm;

/** Contains associations of nodes with shapes or shape expressions to be used during validation.
 * Such associations are not permanent, i.e. they might be made as hypotheses during validation and be removed later on.
 * The permanent associations, that are already known to be valid, can be set using {@link #setStatus(RDFTerm, Label, Status)} and retrieved using @link {@link #asTyping()}
 * @author Iovka Boneva
 */
public interface MyTypingForValidation {

    /** Gives a typing that is guaranteed to be valid. */
    Typing asTyping ();
    /** Tests whether the {@param node} is currently associated with the given {@param shape}. */
    boolean containsShape (RDFTerm node, Shape shape);
    /** Tests whether the {@param node} is currently associated with the given {@param shapeExpr}. */
    boolean containsShapeExpr (RDFTerm node, ShapeExpr shapeExpr);

    /** Adds the association. */
    void addShape (RDFTerm node, Shape shape);
    /** Removes the association. */
    boolean removeShape (RDFTerm node, Shape shape);

    /** Sets a permanent status for a node and a shape expression label. */
    void setStatus (RDFTerm node, Label shapeExprLabel, Status status);
}
