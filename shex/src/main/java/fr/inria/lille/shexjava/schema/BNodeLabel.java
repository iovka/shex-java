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

import fr.inria.lille.shexjava.util.RDFPrintUtils;
import org.apache.commons.rdf.api.BlankNode;

import java.util.Map;
import java.util.Objects;

/**
 * @author Iovka Boneva
 */
public class BNodeLabel extends Label {

    private final BlankNode bnode;
    private final boolean generated;

    public BNodeLabel (BlankNode bnode, boolean generated) {
        this.bnode = bnode;
        this.generated = generated;
    }

    public BNodeLabel (BlankNode bnode) {
        this(bnode, false);
    }

    public boolean isIri () { return false; }
    public boolean isBlankNode () { return true; }
    public boolean isGenerated() { return generated; }

    public String stringValue() { return bnode.ntriplesString(); }

    public String toPrettyString(Map<String,String> prefixes) {
        return RDFPrintUtils.toPrettyString(bnode, prefixes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BNodeLabel that = (BNodeLabel) o;
        return generated == that.generated &&
                bnode.equals(that.bnode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bnode, generated);
    }
}
