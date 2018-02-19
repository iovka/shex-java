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
package fr.univLille.cristal.shex.util;

public class Interval {
	
	/** These values determine a unique representation of an empty interval. */ 
	public static final int MIN_EMPTY = 2;
	public static final int MAX_EMPTY = 1;
	
	public static final int UNBOUND = Integer.MAX_VALUE;
	
	public static Interval STAR = new Interval(0, UNBOUND);
	public static Interval PLUS = new Interval(1, UNBOUND);
	public static Interval OPT = new Interval(0,1);
	public static Interval EMPTY = new Interval(MIN_EMPTY, MAX_EMPTY);
	public static Interval ONE = new Interval(1,1);
	public static Interval ZERO = new Interval(0,0);

	public int min, max;
	
	public Interval (int min, int max) {
		if (min < 0) throw new IllegalArgumentException("Interval negative min bound not allowed");
		if (max < 0) throw new IllegalArgumentException("Interval negative max bound not allowed");
		if (max < min) {
			this.min = MIN_EMPTY; this.max = MAX_EMPTY;
		} else {
			this.min = min; this.max = max;
		}
	}
	
	public boolean isUnbound () {
		return max == UNBOUND;
	}

	public boolean contains(int i) {
		return i >= min && i <= max;
	}
	
	
	@Override
	public String toString() {
		return String.format("[%d; %s]", min, max == UNBOUND ? "*" : max);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interval other = (Interval) obj;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		return true;
	}
	
}
