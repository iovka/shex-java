package fr.univLille.cristal.shex.util;

/** An immutable pair.
 * 
 * The equals and hashCode methods compare the two components of the pair.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 * @param <First>
 * @param <Second>
 */
public class Pair<First, Second> {

	public final First one;
	public final Second two;

	public Pair(First one, Second two) {
		super();
		this.one = one;
		this.two = two;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((one == null) ? 0 : one.hashCode());
		result = prime * result + ((two == null) ? 0 : two.hashCode());
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
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (one == null) {
			if (other.one != null)
				return false;
		} else if (!one.equals(other.one))
			return false;
		if (two == null) {
			if (other.two != null)
				return false;
		} else if (!two.equals(other.two))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", one, two);
	}
	
}
