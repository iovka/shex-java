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

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;

import fr.inria.lille.shexjava.util.RDFPrintUtils;

/** Label class for shex schema. A label is either an IRI or a BlankNode. This class is used to label triple expressions and shape expressions.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public abstract class Label {
	public abstract boolean isGenerated();

	public abstract String stringValue();

	@Override
	public String toString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public String toPrettyString() {
		return toPrettyString(Collections.emptyMap());
	}
	
	public abstract String toPrettyString(Map<String,String> prefixes) ;
}
