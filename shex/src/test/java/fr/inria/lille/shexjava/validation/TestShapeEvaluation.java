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

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.IRILabel;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.schema.analysis.TestCollectTripleConstraints;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class TestShapeEvaluation {

    @Test
    public void testMatchingSubExpressions () {
        TripleConstraint tc_p1 = TestCollectTripleConstraints.buildTripleConstraint("p");
        TripleConstraint tc_p2 = TestCollectTripleConstraints.buildTripleConstraint("p");
        TripleConstraint tc_p3 = TestCollectTripleConstraints.buildTripleConstraint("p");
        TripleConstraint tc_p4 = TestCollectTripleConstraints.buildTripleConstraint("p");

        TripleConstraint tc_q1 = TestCollectTripleConstraints.buildTripleConstraint("q");
        TripleConstraint tc_q3 = TestCollectTripleConstraints.buildTripleConstraint("q");
        TripleConstraint tc_q4 = TestCollectTripleConstraints.buildTripleConstraint("q");

        TripleConstraint tc_r2 = TestCollectTripleConstraints.buildTripleConstraint("r");
        TripleConstraint tc_r3 = TestCollectTripleConstraints.buildTripleConstraint("r");

        TripleExpr te1 = new EachOf(Arrays.asList(tc_p1, tc_q1));
        TripleExpr te2 = new EachOf(Arrays.asList(tc_p2, tc_r2));
        TripleExpr te3 = new EachOf(Arrays.asList(tc_p3, tc_q3, tc_r3));
        TripleExpr te4 = new EachOf(Arrays.asList(tc_p4, tc_q4));

        Shape shape1 = new Shape(te1, Collections.emptyList(), Collections.emptySet(), false);
        Shape shape2 = new Shape(te2, Collections.emptyList(), Collections.emptySet(), false);

        ShapeExprRef shape1AndShape2Ref = new ShapeExprRef(new IRILabel(TestCollectTripleConstraints.buildIRI("Shape1AndShape2Ref")));
        shape1AndShape2Ref.setShapeDefinition(new ShapeAnd(Arrays.asList( shape1, shape2)));

        Shape shape3 = new Shape(te3, Collections.emptyList(), Collections.emptySet(), false);
        ShapeExprRef shape3Ref = new ShapeExprRef(new IRILabel(TestCollectTripleConstraints.buildIRI("shape2ref")));
        shape3Ref.setShapeDefinition(shape3);

        Shape shape4 = new Shape(te4, Arrays.asList(shape1AndShape2Ref, shape3Ref), Collections.emptySet(),false) ;
        shape4.setId(new IRILabel(TestCollectTripleConstraints.buildIRI("shape4")));


        Triple tp = TestCollectTripleConstraints.buildTriple("n1", "p", "n2");
        Triple tq = TestCollectTripleConstraints.buildTriple("n1", "q", "n2");
        Triple tr = TestCollectTripleConstraints.buildTriple("n1", "r", "n2");

        Map<Triple, List<TripleConstraint>> matchingTripleConstraints = new HashMap<>();
        matchingTripleConstraints.put(tp, Arrays.asList(tc_p1, tc_p2, tc_p3, tc_p4));
        matchingTripleConstraints.put(tq, Arrays.asList(tc_q1, tc_q3, tc_q4));
        matchingTripleConstraints.put(tr, Arrays.asList(tc_r2, tc_r3));

        DynamicCollectorOfTripleConstraints collectorTC = new DynamicCollectorOfTripleConstraints();

        MyShapeEvaluation eval = new MyShapeEvaluation(null, null, null, shape4, null, collectorTC);

        collectorTC.getTCs(shape4);
        Map<Triple, Set<Object>> matchingSubExpressions = eval.matchingSubExpressionsOfShape(
                Arrays.asList(tp, tq, tr), matchingTripleConstraints, shape4);

        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref, te4)), matchingSubExpressions.get(tp));
        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref, te4)), matchingSubExpressions.get(tq));
        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref)), matchingSubExpressions.get(tr));

        collectorTC.getTCs(shape2);
        matchingSubExpressions = eval.matchingSubExpressionsOfShape(
                Arrays.asList(tp, tq, tr), matchingTripleConstraints, shape2);
        assertEquals(new HashSet<>(Arrays.asList(te2)), matchingSubExpressions.get(tp));
        assertEquals(new HashSet<>(Arrays.asList(te2)), matchingSubExpressions.get(tr));
        assertEquals(Collections.emptySet(), matchingSubExpressions.get(tq));
    }

}
