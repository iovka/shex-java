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
import org.apache.commons.rdf.api.BlankNodeOrIRI;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/** A user defined label is a blank node or IRI.
 * @author Iovka Boneva
 */
public class LabelUserDefined implements Label {
    private final BlankNodeOrIRI ib;

    public LabelUserDefined(BlankNodeOrIRI ib) {
        this.ib = ib;
    }
    public BlankNodeOrIRI getLabel () {
        return ib;
    }

    @Override
    public boolean isUserDefined() {
        return true;
    }

    @Override
    public String toPrettyString(Map<String, String> prefixes) {
        return RDFPrintUtils.toPrettyString(ib, prefixes);
    }
    @Override
    public String toString() {
        return toPrettyString(Collections.emptyMap());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelUserDefined that = (LabelUserDefined) o;
        return ib.equals(that.ib);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ib);
    }
}
