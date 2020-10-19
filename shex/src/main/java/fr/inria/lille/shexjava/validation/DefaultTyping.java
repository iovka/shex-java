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

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** A default implementation of a typing that stores explicitly the status for node-label pairs.
 * Missing status is considered {@link Status#NOTCOMPUTED}.
 * @author Iovka Boneva
 */
public class DefaultTyping implements Typing {

    private Map<Pair<RDFTerm, Label>, Status> statusMap = new HashMap<>();

    @Override
    public Status getStatus(RDFTerm node, Label label) {
        return statusMap.computeIfAbsent(new Pair<>(node, label), k -> Status.NOTCOMPUTED);
    }

    protected void setStatus (RDFTerm node, Label label, Status status) {
        statusMap.put(new Pair<>(node, label), status);
    }

    @Override
    public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
        return Collections.unmodifiableMap(statusMap);
    }
}
