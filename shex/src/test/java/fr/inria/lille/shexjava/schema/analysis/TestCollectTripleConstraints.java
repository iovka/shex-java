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

import fr.inria.lille.shexjava.schema.LabelUserDefined;
import fr.inria.lille.shexjava.schema.abstrsynt.*;


import org.junit.Test;
import static org.junit.Assert.*;

import fr.inria.lille.shexjava.util.RDF;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class TestCollectTripleConstraints {

    @Test
    public void testExtendsNonEmptyTENonEmpty () {
        TripleConstraint tc1 = RDF.buildTripleConstraint("a");
        TripleConstraint tc2 = RDF.buildTripleConstraint("b");
        TripleConstraint tc3 = RDF.buildTripleConstraint("c");

        ShapeExpr extended = new ShapeAnd (Arrays.asList(
                new Shape(tc1, Collections.emptyList(), Collections.emptySet(), false),
                new Shape(tc2, Collections.emptyList(), Collections.emptySet(), false)));
        ShapeExprRef ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("i")));
        ref.setShapeDefinition(extended);
        Shape shape = new Shape(tc3, Arrays.asList(ref), Collections.emptySet(), false);

        CollectTripleConstraintsSE tcCollector = new CollectTripleConstraintsSE();
        shape.accept(tcCollector);
        assertEquals(new HashSet<>(Arrays.asList(tc1, tc2, tc3)), new HashSet<>(tcCollector.getResult().one));
    }

    @Test
    public void testCollectiWithParents () {
        TripleConstraint tc1 = RDF.buildTripleConstraint("a");
        TripleConstraint tc2 = RDF.buildTripleConstraint("b");
        TripleConstraint tc3 = RDF.buildTripleConstraint("c");
        TripleConstraint tc4 = RDF.buildTripleConstraint("d");
        TripleConstraint tc5 = RDF.buildTripleConstraint("e");
        TripleConstraint tc5bis = RDF.buildTripleConstraint("f");

        Shape shape1 = new Shape(tc1, Collections.emptyList(), Collections.emptySet(), false);
        Shape shape2 = new Shape(tc2, Collections.emptyList(), Collections.emptySet(), false);
        Shape shape3 = new Shape(tc3, Collections.emptyList(), Collections.emptySet(), false);

        ShapeAnd andS1S2 = new ShapeAnd(Arrays.asList(shape1, shape2));
        ShapeExprRef shape1AndShape2Ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("shape1AndShape2Ref")));
        shape1AndShape2Ref.setShapeDefinition(andS1S2);
        ShapeExprRef shape3ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("shape3ref")));
        shape3ref.setShapeDefinition(shape3);

        List<ShapeExprRef> shape4extended = Arrays.asList(shape1AndShape2Ref, shape3ref);
        Shape shape4 = new Shape(tc4, shape4extended, Collections.emptySet(), false);

        ShapeExprRef shape4ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("shape4ref")));
        shape4ref.setShapeDefinition(shape4);
        List<ShapeExprRef> shape5extended = Arrays.asList(shape4ref);
        TripleExpr tripleExpr5 = new EachOf(Arrays.asList(tc5, tc5bis));
        Shape shape5 = new Shape(tripleExpr5, shape5extended, Collections.emptySet(), false);

        Deque tc1parents = new ArrayDeque();
        tc1parents.push(shape5); tc1parents.push(shape4ref);
        tc1parents.push(shape4); tc1parents.push(shape1AndShape2Ref);
        tc1parents.push(shape1); tc1parents.push(tc1);

        Deque tc2parents = new ArrayDeque();
        tc2parents.push(shape5);  tc2parents.push(shape4ref);
        tc2parents.push(shape4); tc2parents.push(shape1AndShape2Ref);
        tc2parents.push(shape2); tc2parents.push(tc2);

        Deque tc3parents = new ArrayDeque();
        tc3parents.push(shape5);  tc3parents.push(shape4ref);
        tc3parents.push(shape4); tc3parents.push(shape3ref);
        tc3parents.push(shape3); tc3parents.push(tc3);

        Deque tc4parents = new ArrayDeque();
        tc4parents.push(shape5);  tc4parents.push(shape4ref);
        tc4parents.push(shape4); tc4parents.push(tc4);

        Deque tc5parents = new ArrayDeque();
        tc5parents.push(shape5); tc5parents.push(tripleExpr5);

        Map<TripleConstraint, Deque<Object>> expectedParentsMap = new HashMap<>();
        expectedParentsMap.put(tc1, tc1parents);
        expectedParentsMap.put(tc2, tc2parents);
        expectedParentsMap.put(tc3, tc3parents);
        expectedParentsMap.put(tc4, tc4parents);
        expectedParentsMap.put(tc5, tc5parents);
        expectedParentsMap.put(tc5bis, tc5parents);

        CollectTripleConstraintsSE collector = new CollectTripleConstraintsSE();
        shape5.accept(collector, new ArrayDeque<>());
        Map<TripleConstraint, Deque<Object>> computedParentsMap = collector.getResult().two;

        assertEquals(expectedParentsMap.keySet(), computedParentsMap.keySet());
        for (TripleConstraint tc : expectedParentsMap.keySet())
            assertArrayEquals(expectedParentsMap.get(tc).toArray(), computedParentsMap.get(tc).toArray());
    }
}
