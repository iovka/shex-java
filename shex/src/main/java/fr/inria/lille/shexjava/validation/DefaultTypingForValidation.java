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

import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.HashSet;
import java.util.LinkedHashSet;

/** A default implementation of {@see MyTypingForValidation} as a set of pairs.
 * @author Iovka Boneva
 */
public class DefaultTypingForValidation extends LinkedHashSet<Pair<RDFTerm, ShapeExpr>> implements MyTypingForValidation {

    @Override
    public boolean contains(RDFTerm node, ShapeExpr shapeExpr) {
        return super.contains(new Pair<>(node, shapeExpr));
    }

    @Override
    public void add(RDFTerm node, ShapeExpr shapeExpr) {
        super.add(new Pair<>(node, shapeExpr));
    }

    @Override
    public void remove(RDFTerm node, ShapeExpr shapeExpr) {
        super.remove(new Pair<>(node, shapeExpr));
    }
}
