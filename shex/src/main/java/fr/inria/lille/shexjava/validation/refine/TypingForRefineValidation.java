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
package fr.inria.lille.shexjava.validation.refine;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.Typing;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.Map;

/** This typing explicitly stores only the conformant node - label associations.
 * All other associations are considered non conformant.
 * @author Iovka Boneva
 */
public class TypingForRefineValidation implements Typing {



    @Override
    public Status getStatus(RDFTerm node, Label label) {
        return null;
    }

    @Override
    public Map<Pair<RDFTerm, Label>, Status> getStatusMap() {
        return null;
    }

    @Override
    public boolean isConformant(RDFTerm node, Label label) {
        return false;
    }
}
