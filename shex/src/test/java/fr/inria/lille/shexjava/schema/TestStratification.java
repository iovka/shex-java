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
package fr.inria.lille.shexjava.schema;

import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.util.Util;
import net.bytebuddy.pool.TypePool;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.util.Map;

/**
 * @author Iovka Boneva
 */
public class TestStratification {

    @Test
    public void testDependencyGraphWithExtends () throws Exception {
        String s = "BASE <http://a.example/>\n" +
                "<X> EXTENDS @<Y> {<r> @<S3>}\n" +
                "<Y> @<S1> AND @<S2> AND {<r> @<S4>}\n" +
                "<Z> {<r> @<Y>}\n" +
                "<S1> { <q1> @<S5>}\n" +
                "<S2> { <q2> @<S6>}\n" +
                "<S3> { <p3> .}\n" +
                "<S4> { <p4> .}\n" +
                "<S5> { <p5> .}\n" +
                "<S6> { <p6> .}\n";

        ShexSchema schema = Util.parseShexC(s);
        DefaultDirectedWeightedGraph<Label, DefaultWeightedEdge> depGraph = ShexSchema.computeGraphOfDependences(schema.getShapeExprsMap(), schema.getConcreteSubShapesMap());

        for (Map.Entry<Label, ShapeExpr> e : schema.getShapeExprsMap().entrySet()) {
            System.out.println(e.getKey() + "->" + e.getValue() +
                    ((e.getValue() instanceof Shape) ? " (Shape)" : ""));
        }

        System.out.println("");
        for (DefaultWeightedEdge e : depGraph.edgeSet()) {
            System.out.println(depGraph.getEdgeSource(e) + " -> " + depGraph.getEdgeTarget(e));
        }


    }
}
