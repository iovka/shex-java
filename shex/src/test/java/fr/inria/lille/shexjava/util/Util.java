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
package fr.inria.lille.shexjava.util;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.LabelUserDefined;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.parsing.ShExCParser;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Iovka Boneva
 */
public class Util {

    public static ShexSchema parseShexC (String schemaString) throws Exception {
        ShExCParser parser = new ShExCParser();
        InputStream is = new ByteArrayInputStream(schemaString.getBytes());
        return new ShexSchema(parser.getRules(is));
    }

    public static ShapeExpr getShapeExpr (ShexSchema schema, String labelIriString) {
        return schema.getShapeExprsMap().get(new LabelUserDefined(GlobalFactory.RDFFactory.createIRI(labelIriString)));
    }

}
