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
package fr.inria.lille.shexjava.schema.abstrsynt.visitors;

import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iovka Boneva
 */
public class CollectTripleConstraintsSE extends ShapeExpressionVisitor<List<TripleConstraint>> {

    public CollectTripleConstraintsSE(){
        setResult(new ArrayList<>());
    }

    @Override
    public void visitShape(Shape expr, Object... arguments) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
