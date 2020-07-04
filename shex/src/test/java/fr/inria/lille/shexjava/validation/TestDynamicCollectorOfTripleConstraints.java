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
import fr.inria.lille.shexjava.util.RDF;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Iovka Boneva
 */
public class TestDynamicCollectorOfTripleConstraints {

    @Test
    public void testParents () {
        TripleConstraint tc_p1 = RDF.buildTripleConstraint("p");
        TripleConstraint tc_p2 = RDF.buildTripleConstraint("p");
        TripleConstraint tc_p3 = RDF.buildTripleConstraint("p");
        TripleConstraint tc_p4 = RDF.buildTripleConstraint("p");

        TripleConstraint tc_q1 = RDF.buildTripleConstraint("q");
        TripleConstraint tc_q3 = RDF.buildTripleConstraint("q");
        TripleConstraint tc_q4 = RDF.buildTripleConstraint("q");

        TripleConstraint tc_r2 = RDF.buildTripleConstraint("r");
        TripleConstraint tc_r3 = RDF.buildTripleConstraint("r");

        TripleExpr te1 = new EachOf(Arrays.asList(tc_p1, tc_q1));
        TripleExpr te2 = new EachOf(Arrays.asList(tc_p2, tc_r2));
        TripleExpr te3 = new EachOf(Arrays.asList(tc_p3, tc_q3, tc_r3));
        TripleExpr te4 = new EachOf(Arrays.asList(tc_p4, tc_q4));

        Shape shape1 = new Shape(te1, Collections.emptyList(), Collections.emptySet(), false);
        Shape shape2 = new Shape(te2, Collections.emptyList(), Collections.emptySet(), false);

        ShapeExprRef shape1AndShape2Ref = new ShapeExprRef(new IRILabel(RDF.buildIRI("Shape1AndShape2Ref")));
        shape1AndShape2Ref.setShapeDefinition(new ShapeAnd(Arrays.asList( shape1, shape2)));

        Shape shape3 = new Shape(te3, Collections.emptyList(), Collections.emptySet(), false);
        ShapeExprRef shape3Ref = new ShapeExprRef(new IRILabel(RDF.buildIRI("shape2ref")));
        shape3Ref.setShapeDefinition(shape3);

        Shape shape4 = new Shape(te4, Arrays.asList(shape1AndShape2Ref, shape3Ref), Collections.emptySet(),false) ;

        DynamicCollectorOfTripleConstraints collectorTC = new DynamicCollectorOfTripleConstraints();
        collectorTC.getTCs(shape4);

        assertEquals(te1, collectorTC.getParentInShape(tc_p1, shape1));
        assertEquals(te1, collectorTC.getParentInShape(tc_q1, shape1));
        assertEquals(shape1AndShape2Ref, collectorTC.getParentInShape(tc_p1, shape4));
        assertEquals(shape3Ref, collectorTC.getParentInShape(tc_p3, shape4));

        assertNull(collectorTC.getParentInShape(tc_p1, shape2));
    }
}
