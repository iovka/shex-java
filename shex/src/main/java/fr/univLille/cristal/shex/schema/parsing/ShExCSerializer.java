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
package fr.univLille.cristal.shex.schema.parsing;

import java.util.Map;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.NodeConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeAnd;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeNot;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeOr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;


public class ShExCSerializer {

	public Object ToJson(Map<Label,ShapeExpr> rules) {
		return null;
	}
	
	//--------------------------------------------------
	// Shape conversion
	//--------------------------------------------------

	private Object convertShapeExpr(ShapeExpr shape) {
		if (shape instanceof ShapeAnd)
			return convertShapeAnd((ShapeAnd) shape);
		if (shape instanceof ShapeOr)
			return convertShapeOr((ShapeOr) shape);
		if (shape instanceof ShapeNot)
			return convertShapeNot((ShapeNot) shape);
		if (shape instanceof ShapeExprRef)
			return convertShapeExprRef((ShapeExprRef) shape);
		if (shape instanceof Shape)
			return convertShape((Shape) shape);
		if (shape instanceof NodeConstraint)
			return convertNodeConstraint((NodeConstraint) shape);
		return null;
	}
	
	private Object convertShapeAnd(ShapeAnd shape) {
		return null;
	}
	
	private Object convertShapeOr(ShapeOr shape) {
		return null;
	}
	
	private Object convertShapeNot(ShapeNot shape) {
		return null;
	}
	
	private Object convertShapeExprRef(ShapeExprRef shape) {
		return null;
	}
	
	private Object convertShape(Shape shape) {
		return null;
	}

	private Object convertNodeConstraint(NodeConstraint shape) {
		return null;
	}
	
	//--------------------------------------------------
	// Constraint conversion
	//--------------------------------------------------
	
	
	
	//--------------------------------------------------
	// Triple conversion
	//--------------------------------------------------

	private Object convertTripleExpr(TripleExpr triple) {
		if (triple instanceof EachOf)
			return convertEachOf((EachOf) triple);
		if (triple instanceof OneOf)
			return convertOneOf((OneOf) triple);
		if (triple instanceof TripleExprRef)
			return convertTripleExprRef((TripleExprRef) triple);
		if (triple instanceof RepeatedTripleExpression)
			return convertRepeatedTripleExpression((RepeatedTripleExpression) triple);
		if (triple instanceof TripleConstraint)
			return convertTripleConstraint((TripleConstraint) triple);
		return null;
	}
	
	private Object convertEachOf(EachOf triple) {
		return null;
	}

	private Object convertOneOf(OneOf triple) {
		return null;
	}

	private Object convertTripleExprRef(TripleExprRef triple) {
		return null;
	}
	
	private Object convertRepeatedTripleExpression(RepeatedTripleExpression triple) {
		return null;
	}
	
	private Object convertTripleConstraint(TripleConstraint triple) {
		return null;
	}
	
	//--------------------------------------------------
	// Divers conversion
	//--------------------------------------------------

	
}