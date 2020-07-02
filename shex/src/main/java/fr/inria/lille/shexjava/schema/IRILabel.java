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
import org.apache.commons.rdf.api.IRI;

import java.util.Map;
import java.util.Objects;

/**
 * @author Iovka Boneva
 */
public class IRILabel extends Label {

	private final IRI iri;
	private final boolean generated;

	public IRILabel (IRI iri, boolean generated) {
	    this.iri = iri;
	    this.generated = generated;
    }

    public IRILabel (IRI iri) {
	    this(iri, false);
    }

    public boolean isGenerated() { return generated; }

    public String stringValue () { return iri.getIRIString(); }

    public String toPrettyString(Map<String,String> prefixes) {
        return RDFPrintUtils.toPrettyString(iri, prefixes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRILabel iriLabel = (IRILabel) o;
        return generated == iriLabel.generated &&
                iri.equals(iriLabel.iri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iri, generated);
    }
}
