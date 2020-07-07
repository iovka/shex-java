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

import java.util.Collections;
import java.util.Map;

/** A generated label. Acts also as factory for such labels.
 *
 * @author Iovka Boneva
 */
public class LabelGenerated implements Label {

    public static LabelGenerated getNew () {
        return new LabelGenerated(next++);
    }

    private final int id;
    private static int next = 0;

    private LabelGenerated (int id) {
        this.id = id;
    }

    @Override
    public boolean isUserDefined() {
        return false;
    }

    @Override
    public String toPrettyString(Map<String,String> prefixes) {
        return "Label:"+id;
    }

    @Override
    public String toString() {
        return toPrettyString(Collections.emptyMap());
    }

    @Override
    public final boolean equals(Object o) {
        return this == o;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
}
