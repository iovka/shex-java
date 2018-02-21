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
package fr.univLille.cristal.shex.schema.concrsynt;

import java.math.BigDecimal;

import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class FacetNumericConstraint implements Constraint {
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
		
		if (!XMLDatatypeUtil.isValidDouble(lnode.stringValue()))
			return false;
		if (!XMLDatatypeUtil.isValidValue(lnode.stringValue(), lnode.getDatatype()))
			return false;
		BigDecimal dv = lnode.decimalValue().stripTrailingZeros();
		
		if (minincl != null && dv.compareTo(minincl) < 0)
			return false;
		if (minexcl != null && dv.compareTo(minexcl) <= 0)
			return false;
		if (maxincl != null && dv.compareTo(maxincl) > 0)
			return false;
		if (maxexcl != null && dv.compareTo(maxexcl) >= 0)
			return false;
		
		if (totalDigits==null & fractionDigits==null)
			return true;
		
		if (!XMLDatatypeUtil.isDecimalDatatype(lnode.getDatatype())){
			return false;
		}
		
		String normalizeValue = XMLDatatypeUtil.normalize(lnode.stringValue(), lnode.getDatatype());		
		if (totalDigits != null && totalDigits < computeTotalDigit(normalizeValue)) 
			return false;
		
		if (fractionDigits != null && fractionDigits < computeFractionDigit(normalizeValue)) 
			return false;
		
		return true;
	}
	
	private int computeTotalDigit(String value) {
		if (! value.contains("."))
			return (value.length());
		
		String entier = value.substring( 0, value.indexOf("."));	
	
		return String.valueOf(entier).length()+computeFractionDigit(value);
	}
	
	private int computeFractionDigit(String value) {
		if (! value.contains("."))
			return 0;
		String decim = value.substring( value.indexOf(".")+1,value.length());
		return String.valueOf(decim).length();
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

	public BigDecimal getMinincl() {
		return minincl;
	}

	public BigDecimal getMinexcl() {
		return minexcl;
	}

	public BigDecimal getMaxincl() {
		return maxincl;
	}

	public BigDecimal getMaxexcl() {
		return maxexcl;
	}

	public Integer getTotalDigits() {
		return totalDigits;
	}

	public Integer getFractionDigits() {
		return fractionDigits;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacetNumericConstraint other = (FacetNumericConstraint) obj;
		
		if (getFractionDigits()!=null) {
			if (other.getFractionDigits()==null)
				return false;
			if (getFractionDigits().compareTo(other.getFractionDigits()) != 0)
				return false;
		} else
			if (other.getFractionDigits()!=null)
				return false;
		
		if (getMaxexcl()!=null) {
			if (other.getMaxexcl()==null)
				return false;
			if (getMaxexcl().compareTo(other.getMaxexcl()) != 0)
				return false;
		} else
			if (other.getMaxexcl()!=null)
				return false;
	
		if (getMaxincl()!=null) {
			if (other.getMaxincl()==null)
				return false;
			if (getMaxincl().compareTo(other.getMaxincl()) != 0)
				return false;
		} else
			if (other.getMaxincl()!=null)
				return false;
	
		if (getMinexcl()!=null) {
			if (other.getMinexcl()==null)
				return false;
			if (getMinexcl().compareTo(other.getMinexcl()) != 0)
				return false;
		} else
			if (other.getMinexcl()!=null)
				return false;

		if (getMinincl()!=null) {
			if (other.getMinincl()==null)
				return false;
			if (getMinincl().compareTo(other.getMinincl()) != 0)
				return false;
		} else
			if (other.getMinincl()!=null)
				return false;

		if (getTotalDigits()!=null) {
			if (other.getTotalDigits()==null)
				return false;
			if (getTotalDigits().compareTo(other.getTotalDigits()) != 0)
				return false;
		} else
			if (other.getTotalDigits()!=null)
				return false;

		return true;
	}
}
