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

import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.analysis.TripleExpressionVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iovka Boneva
 */
public class CollectTripleConstraintsTE extends TripleExpressionVisitor<List<TripleConstraint>> {
    private List<TripleConstraint> list;

    public CollectTripleConstraintsTE(){
        this.list = new ArrayList<>();
    }

    @Override
    public List<TripleConstraint> getResult() {
        return list;
    }


    @Override
    public void visitRepeated(RepeatedTripleExpression expr, Object[] arguments) {
        expr.getSubExpression().accept(this, arguments);
    }

    @Override
    public void visitTripleConstraint(TripleConstraint tc, Object... arguments) {
        list.add(tc);
    }

    @Override
    public void visitTripleExprReference(TripleExprRef expr, Object... arguments) {
        expr.getTripleExp().accept(this, arguments);
    }

    @Override
    public void visitEmpty(EmptyTripleExpression expr, Object[] arguments) {}
}