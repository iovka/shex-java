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
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.LabelUserDefined;
import fr.inria.lille.shexjava.schema.abstrsynt.*;
import fr.inria.lille.shexjava.util.Pair;
import fr.inria.lille.shexjava.util.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class TestShapeEvaluation {

    @Test
    public void testMatchingSubExpressions () throws Exception {
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

        Shape shape1 = new Shape(te1, Collections.emptyList(), Collections.emptySet(), false, null);
        Shape shape2 = new Shape(te2, Collections.emptyList(), Collections.emptySet(), false, null);

        ShapeExprRef shape1AndShape2Ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("Shape1AndShape2Ref")));
        shape1AndShape2Ref.setShapeDefinition(new ShapeAnd(Arrays.asList( shape1, shape2)));

        Shape shape3 = new Shape(te3, Collections.emptyList(), Collections.emptySet(), false, null);
        ShapeExprRef shape3Ref = new ShapeExprRef(new LabelUserDefined(RDF.buildIRI("shape2ref")));
        shape3Ref.setShapeDefinition(shape3);

        Shape shape4 = new Shape(te4, Arrays.asList(shape1AndShape2Ref, shape3Ref), Collections.emptySet(),false, null) ;
        shape4.setId(new LabelUserDefined(RDF.buildIRI("shape4")));

        Triple tp = RDF.buildTriple("n1", "p", "n2");
        Triple tq = RDF.buildTriple("n1", "q", "n2");
        Triple tr = RDF.buildTriple("n1", "r", "n2");

        List<Triple> neighbourhood = Arrays.asList(tp, tq, tr);

        Map<Triple, List<TripleConstraint>> matchingTripleConstraints = new HashMap<>();
        matchingTripleConstraints.put(tp, Arrays.asList(tc_p1, tc_p2, tc_p3, tc_p4));
        matchingTripleConstraints.put(tq, Arrays.asList(tc_q1, tc_q3, tc_q4));
        matchingTripleConstraints.put(tr, Arrays.asList(tc_r2, tc_r3));

        DynamicCollectorOfTripleConstraints collectorTC = new DynamicCollectorOfTripleConstraints();
        Map<Label, ShapeExpr> rules = new HashMap<>();
        rules.put(shape4.getId(), shape4);
        SORBEGenerator sorbeGenerator = new SORBEGenerator();

        Typing allConformantTyping = new Typing() {
            @Override
            public Status getStatus(RDFTerm node, Label label) {
                return Status.CONFORMANT;
            }

            @Override
            public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
                throw new UnsupportedOperationException();
            }
        };
/*
        MyShapeEvaluation eval = new MyShapeEvaluation(RDF.buildIRI("n1"), shape4, neighbourhood, allConformantTyping, collectorTC, sorbeGenerator);
        eval.init();

        Map<Triple, List<Object>> matchingSubExpressions = eval.matchingSubExpressionsOfShape(neighbourhood, shape4);

        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref, te4)), new HashSet<>(matchingSubExpressions.get(tp)));
        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref, te4)), new HashSet<>(matchingSubExpressions.get(tq)));
        assertEquals(new HashSet<>(Arrays.asList(shape1AndShape2Ref, shape3Ref)), new HashSet<>(matchingSubExpressions.get(tr)));
       
 */
    }

}
