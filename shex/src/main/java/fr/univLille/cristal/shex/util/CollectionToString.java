package fr.univLille.cristal.shex.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionToString {

	public static String collectionToString (Collection<?> list, String separator, String prefix, String suffix) {
		StringBuilder s = new StringBuilder();
		s.append(prefix);
		Iterator<?> it = list.iterator();
		if (it.hasNext()) s.append(it.next());
		while (it.hasNext()) {
			s.append(separator);
			s.append(it.next());
		}
		s.append(suffix);
		return s.toString();
	}
	
}
