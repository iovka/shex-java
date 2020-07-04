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

import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

/**
 * @author Iovka Boneva
 */
public class TestMatchingsIterator {

    @Test
    public void testTwoTriples () {
        Map<Triple, List<TripleConstraint>> preMatching = new HashMap<>();
        Triple A = mock(Triple.class);
        Triple B = mock(Triple.class);
        TripleConstraint a1 = mock(TripleConstraint.class);
        TripleConstraint a2 = mock(TripleConstraint.class);
        TripleConstraint a3 = mock(TripleConstraint.class);
        TripleConstraint b1 = mock(TripleConstraint.class);
        TripleConstraint b2 = mock(TripleConstraint.class);

        preMatching.put(A, Arrays.asList(a1, a2, a3));
        preMatching.put(B,Arrays.asList(b1, b2));

        MatchingsIterator<TripleConstraint> it = new MatchingsIterator(preMatching);
        Set<Matching> allElements = new HashSet<>();
        while (it.hasNext()) {
            allElements.add(it.next());
        }

        Set<Map<Triple, TripleConstraint>> expectedElements = new HashSet<>();
        Matching m;
        m = new Matching(); m.put(A, a1); m.put(B, b1); expectedElements.add(m);
        m = new Matching(); m.put(A, a1); m.put(B, b2); expectedElements.add(m);
        m = new Matching(); m.put(A, a2); m.put(B, b1); expectedElements.add(m);
        m = new Matching(); m.put(A, a2); m.put(B, b2); expectedElements.add(m);
        m = new Matching(); m.put(A, a3); m.put(B, b1); expectedElements.add(m);
        m = new Matching(); m.put(A, a3); m.put(B, b2); expectedElements.add(m);

        assertEquals(expectedElements, allElements);
    }

    @Test
    public void testNoTriples () {

        Map<Triple, List<TripleConstraint>> preMatching = new HashMap<>();
        MatchingsIterator it = new MatchingsIterator(preMatching);
        assertEquals(Collections.emptyMap(), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testNoMatchingForSomeTriple () {
        Map<Triple, List<TripleConstraint>> preMatching = new HashMap<>();
        Triple A = mock(Triple.class);
        Triple B = mock(Triple.class);
        TripleConstraint a1 = mock(TripleConstraint.class);
        TripleConstraint a2 = mock(TripleConstraint.class);
        TripleConstraint a3 = mock(TripleConstraint.class);
        preMatching.put(A, Arrays.asList(a1, a2, a3));
        preMatching.put(B,Collections.emptyList());

        MatchingsIterator it = new MatchingsIterator(preMatching);
        assertFalse(it.hasNext());
    }

    @Test
    public void testRestrictedDomain () {
        Map<Triple, List<TripleConstraint>> preMatching = new HashMap<>();
        Triple A = mock(Triple.class);
        Triple B = mock(Triple.class);
        Triple C = mock(Triple.class);
        TripleConstraint a1 = mock(TripleConstraint.class);
        TripleConstraint a2 = mock(TripleConstraint.class);
        TripleConstraint a3 = mock(TripleConstraint.class);
        TripleConstraint b1 = mock(TripleConstraint.class);
        TripleConstraint b2 = mock(TripleConstraint.class);
        TripleConstraint c1 = mock(TripleConstraint.class);
        TripleConstraint c2 = mock(TripleConstraint.class);

        preMatching.put(A, Arrays.asList(a1, a2, a3));
        preMatching.put(B, Arrays.asList(b1, b2));
        preMatching.put(C, Arrays.asList(c1, c2));

        MatchingsIterator it = new MatchingsIterator(preMatching, Arrays.asList(A,C));
        Set<Matching> allElements = new HashSet<>();
        while (it.hasNext()) {
            allElements.add(it.next());
        }

        Set<Map<Triple, TripleConstraint>> expectedElements = new HashSet<>();
        Matching m;
        m = new Matching(); m.put(A, a1); m.put(C, c1); expectedElements.add(m);
        m = new Matching(); m.put(A, a1); m.put(C, c2); expectedElements.add(m);
        m = new Matching(); m.put(A, a2); m.put(C, c1); expectedElements.add(m);
        m = new Matching(); m.put(A, a2); m.put(C, c2); expectedElements.add(m);
        m = new Matching(); m.put(A, a3); m.put(C, c1); expectedElements.add(m);
        m = new Matching(); m.put(A, a3); m.put(C, c2); expectedElements.add(m);

        assertEquals(expectedElements, allElements);
    }

}
