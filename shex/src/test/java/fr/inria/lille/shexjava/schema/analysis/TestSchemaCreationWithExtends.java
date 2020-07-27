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

import fr.inria.lille.shexjava.exception.CyclicReferencesException;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collections;

/**
 * @author Iovka Boneva
 */
public class TestSchemaCreationWithExtends {

    @Test(expected = CyclicReferencesException.class)
    public void testCyclicExtendsReferencesThrowsException () throws Exception {
        String schemaString =
                "BASE <http://a.example/>\n" +
                "<A> EXTENDS @<B> { <p> . }\n" +
                "<B> EXTENDS @<A> { <p> . }";
        Util.parseShexC(schemaString);
    }

    @Test
    public void testComputeConcreteSuperShapes () throws Exception {
        String schemaString =
                "BASE <http://a.example/>\n" +
                        "ABSTRACT <A> EXTENDS @<B> EXTENDS @<C> { <p> . }\n" +
                        "<B> EXTENDS @<C> { <p> . }\n" +
                        "ABSTRACT <C> { <p> . }";
        ShexSchema schema = Util.parseShexC(schemaString);
        Shape aShape = (Shape) Util.getShapeExpr(schema, "http://a.example/A");
        Shape bShape = (Shape) Util.getShapeExpr(schema, "http://a.example/B");
        Shape cShape = (Shape) Util.getShapeExpr(schema, "http://a.example/C");

        assertEquals(Collections.singleton(bShape), schema.getConcreteSubShapes(cShape));
        assertEquals(Collections.emptySet(), schema.getConcreteSubShapes(bShape));
        assertEquals(Collections.emptySet(), schema.getConcreteSubShapes(aShape));
    }

}
