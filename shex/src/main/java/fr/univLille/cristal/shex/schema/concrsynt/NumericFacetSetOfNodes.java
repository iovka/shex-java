/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.schema.concrsynt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class NumericFacetSetOfNodes implements SetOfNodes {

	// FIXME: complete the xsd datatypes
	private static final Set<IRI> decimalTypes = new HashSet<>(Arrays.asList(new IRI[] {
			XMLSchema.DECIMAL, 	XMLSchema.INT, XMLSchema.INTEGER
	}));
	private static final Set<IRI> doubleTypes = new HashSet<>(Arrays.asList(new IRI[] {
			XMLSchema.DOUBLE, XMLSchema.FLOAT
	}));
	
	private BigDecimal minincl, minexcl, maxincl, maxexcl;
	private Integer totalDigits, fractionDigits;
	
	public void setMinincl(BigDecimal minincl) {
		if (this.minincl == null)
			this.minincl = minincl;
		else throw new IllegalStateException("mininclusive already set");
	}	
	
	public void setMinexcl(BigDecimal minexcl) {
		if (this.minexcl == null)
			this.minexcl = minexcl;
		else throw new IllegalStateException("minexclusive already set");
	}	

	public void setMaxincl(BigDecimal maxincl) {
		if (this.maxincl == null)
			this.maxincl = maxincl;
		else throw new IllegalStateException("mininclusive already set");
	}	
	
	public void setMaxexcl(BigDecimal maxexcl) {
		if (this.maxexcl == null)
			this.maxexcl = maxexcl;
		else throw new IllegalStateException("minexclusive already set");
	}	
	
	public void setTotalDigits(Integer totalDigits) {
		if (this.totalDigits == null)
			this.totalDigits = totalDigits;
		else throw new IllegalStateException("total digits already set");
	}
	
	public void setFractionDigits(Integer fractionDigits) {
		if (this.fractionDigits == null)
			this.fractionDigits = fractionDigits;
		else throw new IllegalStateException("fraction digits already set");
	}

	@Override
	public boolean contains(Value node) {
		if (! (node instanceof Literal)) return false;
		Literal lnode = (Literal) node;
		IRI datatype = lnode.getDatatype();
		if (decimalTypes.contains(datatype))
			return containsDecimal(lnode);	
		if (doubleTypes.contains(datatype))
			return containsDouble(lnode);

		return false;
	}
	
	private boolean containsDecimal (Literal lnode) {
		BigDecimal dv;
		try {
			dv = lnode.decimalValue();
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (! satisfiesMinMax(dv))
			return false;

		if (totalDigits != null && totalDigits <= dv.precision()) 
			return false;
		
		if (fractionDigits != null && fractionDigits < dv.stripTrailingZeros().scale()) 
			return false;
		
		return true;
	}
	
	private boolean containsDouble (Literal lnode) {
		double d;
		try {
			d = lnode.doubleValue();
		} catch (NumberFormatException e) {
			return false;
		}
		return satisfiesMinMax(BigDecimal.valueOf(d));
	}
	
	
	private boolean satisfiesMinMax (BigDecimal val) {
		if (minincl != null && val.compareTo(minincl) < 0)
			return false;
		if (minexcl != null && val.compareTo(minexcl) <= 0)
			return false;
		if (maxincl != null && val.compareTo(maxincl) > 0)
			return false;
		if (maxexcl != null && val.compareTo(maxexcl) >= 0)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String mini = minincl == null ? "" : " minincl: " + minincl.toString();
		String maxi = maxincl == null ? "" : " maxincl: " + maxincl.toString();
		String mine = minexcl == null ? "" : " minexcl: " + minexcl.toString();
		String maxe = maxexcl == null ? "" : " maxexcl: " + maxexcl.toString();
		String tot  = totalDigits == null ? "" : " totaldigits: " + totalDigits.toString();
		String frac = fractionDigits == null ? "" : "fracdigits: " + fractionDigits.toString();
		return mini + maxi + mine + maxe + tot + frac;
	}
	

}
