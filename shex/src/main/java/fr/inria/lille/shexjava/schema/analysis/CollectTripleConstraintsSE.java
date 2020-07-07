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
package fr.inria.lille.shexjava.schema.analysis;

import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.util.Pair;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class CollectTripleConstraintsSE extends
        ShapeExpressionVisitor<Pair<List<TripleConstraint>, Map<TripleConstraint, Deque<Object>>>> {

    public CollectTripleConstraintsSE(){
        setResult(new Pair<>(new ArrayList<>(), new HashMap<>()));
    }

    @Override
    public void visitShape(Shape expr, Object... arguments) {
        Deque<Object> parents = null;
        if (arguments.length > 0) parents = (Deque) arguments[0];

        if (parents != null) parents.push(expr);

        for (ShapeExpr se : expr.getExtended()) {
            if (parents != null) parents.push(se);
            se.accept(this, arguments);
            if (parents != null) parents.pop();
        }
        CollectTripleConstraintsTE tecollector = new CollectTripleConstraintsTE();
        TripleExpr tripleExpr = expr.getTripleExpression();

        if (parents != null) parents.push(tripleExpr);
        tripleExpr.accept(tecollector, arguments);
        if (parents != null) parents.pop();

        getResult().one.addAll(tecollector.getResult().one);
        getResult().two.putAll(tecollector.getResult().two);

        if (parents != null) parents.pop();
    }

    @Override
    public void visitNodeConstraint(NodeConstraint expr, Object... arguments) {}

    @Override
    public void visitShapeExprRef(ShapeExprRef shapeRef, Object... arguments) {
        shapeRef.getShapeDefinition().accept(this, arguments);
    }
}
