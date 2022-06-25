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
package fr.inria.lille.shexjava.schema.abstrsynt;

import fr.inria.lille.shexjava.schema.Label;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/** The parent class of all expressions from the abstract syntax.
 * @author Iovka Boneva
 */
public abstract class Expression {

    protected Label id = null;
    public Label getId () {
		return this.id;
	}
	public void setId(Label id) {
		if (this.id != null)
			throw new IllegalStateException("ID can be set only once ( is:"+this.id+", to:"+id+").");
		this.id = id;
	}
    public abstract String toPrettyString(Map<String,String> prefixes);

    // TODO: used ? Can be removed ?
    public String toPrettyString() {
        return toPrettyString(Collections.emptyMap());
    }

    @Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}

	@Override
	/** Reference equality. */
	public final boolean equals(Object o) {
    	return this == o;
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}
}
