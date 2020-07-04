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
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyShape;
import fr.inria.lille.shexjava.schema.abstrsynt.TCProperty;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * @author Iovka Boneva
 */
public class RDF {

    public static final String base = "http://x.x/";

    public static TripleConstraint buildTripleConstraint(String predicate) {
        return new TripleConstraint(TCProperty.createFwProperty(buildIRI(predicate)),
                new EmptyShape());
    }

    public static Triple buildTriple(String subject, String predicate, String object) {
        return GlobalFactory.RDFFactory.createTriple(
                buildIRI(subject),
                buildIRI(predicate),
                buildIRI(object));
    }

    public static IRI buildIRI (String i) {
        return GlobalFactory.RDFFactory.createIRI(base + i);
    }
}
