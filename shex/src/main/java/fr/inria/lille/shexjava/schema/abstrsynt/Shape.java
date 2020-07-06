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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.IRI;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.util.CollectionToString;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;

/**
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class Shape extends ShapeExpr implements AnnotedObject {
	private boolean closed;
	private Set<TCProperty> extra;
	private TripleExpr tripleExpr;
	private List<Annotation> annotations;
	private List<Label> baseLabels;
	private List<ShapeExpr> baseExprs;

	public Shape(TripleExpr tripleExpression, Set<TCProperty> extraProps, boolean closed) {
		this.tripleExpr = tripleExpression;
		this.extra = Collections.unmodifiableSet(new HashSet<>(extraProps));
		this.closed = closed;
		this.annotations = null;
		this.baseLabels = null;
		this.baseExprs = null;
	}
	
	public Shape(TripleExpr tripleExpression, Set<TCProperty> extraProps, boolean closed, List<Annotation> annotations) {
		this.tripleExpr = tripleExpression;
		this.extra = Collections.unmodifiableSet(new HashSet<>(extraProps));
		this.closed = closed;
		this.annotations = annotations;
		this.baseLabels = null;
		this.baseExprs = null;
	}
	
	public void setAnnotations (List<Annotation> annotations) {
		if (this.annotations == null)
			this.annotations = annotations;
		else throw new IllegalStateException("Annotations already set");
	}	

	public void setBaseLabels (List<Label> baseLabels) {
		if (this.baseLabels == null)
			this.baseLabels = baseLabels;
		else throw new IllegalStateException("BaseLabels already set");
	}

	public void resolveReferences (Map<Label,ShapeExpr> shexprsMap) throws UndefinedReferenceException {
		if (baseExprs != null)
			throw new IllegalStateException("References can be resolved at most once in Shape");
		if (baseLabels != null) {
			baseExprs = new ArrayList<ShapeExpr>();
			for (Label label : baseLabels)
				if (shexprsMap.containsKey(label))
					baseExprs.add(shexprsMap.get(label));
				else
					throw new UndefinedReferenceException("Undefined shape label for base: " + label);
		}
	}

	
	public TripleExpr getTripleExpression () {
		return tripleExpr;
	}
	
	public boolean isClosed () {
		return this.closed;
	}
	 
	public Set<IRI> getExtraProperties () {
		return this.extra.stream().map(tcp -> tcp.getIri()).collect(Collectors.toSet());
	}
		
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public List<Label> getBaseLabels() {
		return baseLabels;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitShape(this, arguments);
	}
	
	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		String closedstr = isClosed() ? "CLOSED " : "";
		String extendsStr = "";
		for (Label baseLabel:baseLabels)
			extendsStr += "EXTENDS " + baseLabel.toPrettyString(prefixes) + " ";
		String extraP = extra.isEmpty() ? "" : "EXTRA " + extra.toString();
		String annot = "";
		if (this.annotations!=null && this.annotations.isEmpty())
			annot = CollectionToString.collectionToString(annotations," ; ","// [", "]")+" ";
		return String.format("{%s%s%s%s%s}", closedstr, extendsStr, extraP, tripleExpr.toPrettyString(prefixes),annot);
	}

	
}
