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
