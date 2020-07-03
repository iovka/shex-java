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

import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;
import fr.inria.lille.shexjava.util.Pair;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class CollectTripleConstraintsTE
        extends TripleExpressionVisitor<Pair<List<TripleConstraint>, Map<TripleConstraint, Deque<Object>>>> {

    public CollectTripleConstraintsTE(){
        setResult(new Pair<>(new ArrayList<>(), new HashMap<>()));
    }
    @Override
    public void visitRepeated(RepeatedTripleExpression expr, Object... arguments) {
        expr.getSubExpression().accept(this, arguments);
    }

    @Override
    public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
        Deque<Object> parents = null;
        if (arguments.length > 0) parents = (Deque) arguments[0];
        getResult().one.add(tc);
        if (parents != null) getResult().two.put(tc, new ArrayDeque<>(parents));
    }

    @Override
    public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
        expr.getTripleExp().accept(this, arguments);
    }

    @Override
    public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {}
}