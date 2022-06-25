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
package fr.inria.lille.shexjava.schema.abstrsynt;

import java.util.*;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.schema.Label;
import org.apache.commons.rdf.api.IRI;

import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.CollectionToString;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;

/**
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class  Shape extends ShapeExpr implements AnnotedObject {
	private boolean closed;
	private Set<TCProperty> extra;
	private TripleExpr tripleExpr;
	private List<Annotation> annotations;
	private List<ShapeExprRef> extended;

	public Shape(TripleExpr tripleExpr,
				 List<ShapeExpr> extended,
				 Set<TCProperty> extraProps,
				 boolean closed,
				 List<Annotation> annotations) {
		this.tripleExpr = tripleExpr;
		this.extra = Collections.unmodifiableSet(new HashSet<>(extraProps));
		this.extended = Collections.unmodifiableList(new ArrayList(extended));
		this.closed = closed;
		this.annotations = null;
		this.annotations = annotations;
	}

	/**
	 * @deprecated Use {@link #Shape(TripleExpr, List, Set, boolean, List) instead}
	 */
	@Deprecated
	public Shape(TripleExpr tripleExpr, List<ShapeExpr> extended, Set<TCProperty> extraProps, boolean closed) {
		this(tripleExpr, extended, extraProps, closed, null);
	}

	/**
	 * @deprecated Use {@link #Shape(TripleExpr, List, Set, boolean, List) instead}
	 */
	@Deprecated
	public Shape(TripleExpr tripleExpression, Set<TCProperty> extraProps, boolean closed) {
		this(tripleExpression, Collections.emptyList(), extraProps, closed, null);
	}

	/**
	 * @deprecated Use {@link #Shape(TripleExpr, List, Set, boolean, List) instead}
	 */
	@Deprecated
	public Shape(TripleExpr tripleExpression, Set<TCProperty> extraProps, boolean closed, List<Annotation> annotations) {
		this(tripleExpression, Collections.emptyList(), extraProps, closed, annotations);
	}
	
	public void setAnnotations (List<Annotation> annotations) {
		if (this.annotations == null)
			this.annotations = annotations;
		else throw new IllegalStateException("Annotations already set");
	}	

	public TripleExpr getTripleExpression () {
		return tripleExpr;
	}
	public List<ShapeExprRef> getExtended () { return extended; }
	
	public boolean isClosed () {
		return this.closed;
	}
	 
	public Set<IRI> getExtraProperties () {
		return this.extra.stream().map(TCProperty::getIri).collect(Collectors.toSet());
	}
		
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShape(this, arguments);
	}
	
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		String closedstr = isClosed() ? "CLOSED " : "";
		String extraP = extra.isEmpty() ? "" : "EXTRA " + extra.toString();
		String annot = "";
		if (this.annotations!=null && ! this.annotations.isEmpty())
			annot = CollectionToString.collectionToString(annotations," ; ","// [", "]")+" ";
		String extendedstr = "";
		if (! this.extended.isEmpty())
			extendedstr = CollectionToString.collectionToString(getExtended(), " ; ", " EXTENDS ", "") + " ";
		return String.format("%s%s%s{%s}%s", closedstr, extraP, extendedstr, tripleExpr.toPrettyString(prefixes),annot);
	}

	
}
