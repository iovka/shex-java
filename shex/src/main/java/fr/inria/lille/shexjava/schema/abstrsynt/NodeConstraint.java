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

import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.analysis.ShapeExpressionVisitor;
import fr.inria.lille.shexjava.schema.concrsynt.Constraint;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 * @author Jérémie Dusart
 */
public class NodeConstraint extends ShapeExpr implements AnnotedObject{
	private List<Annotation> annotations;
	private List<Constraint> constraints;
	
	public NodeConstraint (List<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	public boolean contains(RDFTerm node) {
		for (Constraint s : constraints)
			if (! s.contains(node))
				return false;
		return true;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	@Override
	public <ResultType> void accept(ShapeExpressionVisitor<ResultType> visitor, Object... arguments) {
		visitor.visitNodeConstraint(this, arguments);
	}
	
	@Override
	public String toString() {
		return "NodeConstraint : "+constraints;
	}

	@Override
	public String toPrettyString(Map<String,String> prefixes) {
		String result = "[";
		for (Constraint cst:constraints)
			result+=" "+cst.toPrettyString();
		result +=" ];";
		return " "+result;
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	
	public void setAnnotations (List<Annotation> annotations) {
		if (this.annotations == null)
			this.annotations = annotations;
		else throw new IllegalStateException("Annotations already set");
	}	

}
