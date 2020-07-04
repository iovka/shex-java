/* ******************************************************************************
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import org.apache.commons.rdf.api.*;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.util.CommonGraph;

/** Contains static methods useful in the different validation alogorithms.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 * 3 août 2018
 */
public class ValidationUtils {
	
	public static Matcher getPredicateOnlyMatcher () {
		return predicateOnlyMatcher;
	}
	
	public static Matcher getPredicateAndValueMatcher () {
		return predicateAndValueMatcher;
	}
	
	/** Select the neighborhood that are relevant for matching a shape.
	 * @param graph the graph in which the neighborhood is to be found
	 * @param tripleConstraints all the triple constraints of the shape
	 * @param shapeIsClosed whether the shape is closed
	 * @return A list that contains all the forward neighborhood if the shape is closed, or otherwise only the forward neighborhood with predicates that appear in {@param tripleConstraints}. The incoming neighborhood is always restricted by the predicates of the inverse triple constraints.
	 */
	public static List<Triple> getMatchableNeighbourhood(
			Graph graph, RDFTerm node, List<TripleConstraint> tripleConstraints, boolean shapeIsClosed) {
		Set<IRI> inversePredicate = new HashSet<>();
		Set<IRI> forwardPredicate = new HashSet<>();
		for (TripleConstraint tc : tripleConstraints)
			if (tc.getProperty().isForward())
				forwardPredicate.add(tc.getProperty().getIri());
			else
				inversePredicate.add(tc.getProperty().getIri());

		ArrayList<Triple> neighbourhood =
				new ArrayList<>(CommonGraph.getInNeighboursWithPredicate(graph, node, inversePredicate));
		if (shapeIsClosed)
			neighbourhood.addAll(CommonGraph.getOutNeighbours(graph, node));
		else
			neighbourhood.addAll(CommonGraph.getOutNeighboursWithPredicate(graph, node,forwardPredicate));
		return neighbourhood;
	}

	
	public static PreMatching computePreMatching(RDFTerm focusNode, List<Triple> neighbourhood,
												 List<TripleConstraint> tripleConstraints, Set<IRI> extraProperties,
												 Matcher matcher, BiPredicate<RDFTerm, ShapeExpr> valueMatcher) {
		
		LinkedHashMap<Triple,List<TripleConstraint>> matchingTriplesMap = new LinkedHashMap<>(neighbourhood.size());
		ArrayList<Triple> matchedToExtraTriples = new ArrayList<>();
		ArrayList<Triple> unmatchedTriples = new ArrayList<>();

		for (Triple triple: neighbourhood) {
			ArrayList<TripleConstraint> matching = new ArrayList<>();
			for (TripleConstraint tc: tripleConstraints) {
				if (matcher.apply(focusNode, triple, tc, valueMatcher)) {
					matching.add(tc);
				}
			} 
			if (! matching.isEmpty()) 
				matchingTriplesMap.put(triple, matching);   // TODO why the empty list is not added ?
			else
				if (extraProperties.contains(triple.getPredicate())) 
					matchedToExtraTriples.add(triple);
				else
					unmatchedTriples.add(triple);
		}
		
		return new PreMatching(matchingTriplesMap, matchedToExtraTriples, unmatchedTriples);
	}

	public static <T> Map<T, List<Triple>> invertMatching (Matching<T> matching) {
		Map<T, List<Triple>> result = new HashMap<>();
		for (Map.Entry<Triple, T> entry : matching.entrySet()) {
			List<Triple> triples = result.computeIfAbsent(entry.getValue(), k -> new ArrayList<>());
			triples.add(entry.getKey());
		}
		return result;
	}

	// TODO never used
	/** Produces a map that with every triple constraint in the given list of triple constarints associates all the triples matched with this triple constraint in the given pre-matching. */
	public static Map<TripleConstraint, List<Triple>> inversePreMatching (Map<Triple, List<TripleConstraint>> preMatching, List<TripleConstraint> tripleConstraints) {
		Map<TripleConstraint, List<Triple>> result = new HashMap<>();
		for (TripleConstraint tc : tripleConstraints)
			result.put(tc, new ArrayList<>());
		for (Map.Entry<Triple, List<TripleConstraint>> e : preMatching.entrySet())
			for (TripleConstraint tc: e.getValue())
				result.get(tc).add(e.getKey());
		return result;
	}
	
	// TODO never used
	/** Produces a map that with every triple constraint from the given matching associates the list of triples that are matched with that triple constraint in the matching.*/
	public static Map<Label, List<Triple>> inverseMatching (Map<Triple, Label> matching, List<TripleConstraint> tripleConstraints) {
		Map<Label, List<Triple>> result = new HashMap<>();
		for (TripleConstraint tc : tripleConstraints)
			result.put(tc.getId(), new ArrayList<>());
		for (Triple p : matching.keySet()) 
			result.get(matching.get(p)).add(p);
		return result;
	}

	private static Matcher predicateOnlyMatcher = (focusNode, triple, tc, valueMatcher) -> {

		if (! tc.getProperty().getIri().equals(triple.getPredicate()))
			return false;

		RDFTerm focus;
		if (tc.getProperty().isForward()) {
			focus = triple.getSubject();
		} else {
			focus = triple.getObject();
		}
		return focusNode instanceof IRI || focusNode.ntriplesString().equals(focus.ntriplesString());
	};

	private static Matcher predicateAndValueMatcher = (focusNode, triple, tc, valueMatcher) -> {

		if (! tc.getProperty().getIri().equals(triple.getPredicate()))
			return false;

		RDFTerm focus;
		RDFTerm opposite;
		if (tc.getProperty().isForward()) {
			focus = triple.getSubject();
			opposite = triple.getObject();
		} else {
			focus = triple.getObject();
			opposite = triple.getSubject();
		}
		if (focusNode instanceof BlankNode && ! focusNode.ntriplesString().equals(focus.ntriplesString()))
			return false;
		return valueMatcher.test(opposite, tc.getShapeExpr());
	};

	private ValidationUtils () {}
}
