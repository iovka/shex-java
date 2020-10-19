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

/** Represents a set of (node, shapeExpression) associations.
 * Is mutable, i.e. associations can be added and removed.
 * @author Iovka Boneva
 */
public interface MyTypingForValidation {

    /** Tests whether the {@param node} is currently associated with the given {@param shape}. */
    boolean contains (RDFTerm node, ShapeExpr shapeExpr);

    /** Adds the association. */
    void add (RDFTerm node, ShapeExpr shapeExpr);
    /** Removes the association. */
    void remove (RDFTerm node, ShapeExpr shapeExpr);

}
