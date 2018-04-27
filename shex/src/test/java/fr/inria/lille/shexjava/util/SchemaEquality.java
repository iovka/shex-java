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
package fr.inria.lille.shexjava.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.Value;

import fr.inria.lille.shexjava.graph.TCProperty;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.EachOf;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyShape;
import fr.inria.lille.shexjava.schema.abstrsynt.EmptyTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.NodeConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.OneOf;
import fr.inria.lille.shexjava.schema.abstrsynt.RepeatedTripleExpression;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeAnd;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExprRef;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeNot;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeOr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleConstraint;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExpr;
import fr.inria.lille.shexjava.schema.abstrsynt.TripleExprRef;
import fr.inria.lille.shexjava.schema.concrsynt.Constraint;
import fr.inria.lille.shexjava.schema.concrsynt.StemRangeConstraint;
import fr.inria.lille.shexjava.schema.concrsynt.ValueSetValueConstraint;



/** Test if two schemas are equal.
 * @author Jérémie Dusart
 *
 */
public class SchemaEquality {

	public static boolean areEquals(ShexSchema schema1, ShexSchema schema2) {
		if (schema1.getRules().size()!=schema2.getRules().size())
			return false;
		
		for (Label label:schema1.getRules().keySet())
			if (!schema2.getRules().containsKey(label))
				return false;
			else
				if (! areEqualsShapeExpr(schema1.getRules().get(label), schema2.getRules().get(label)))
					return false;
		
		return true;
	}
	
	//-------------------------------------------------
	// Shape Equality
	//-------------------------------------------------
	
	private static boolean areEqualsShapeExpr(ShapeExpr shape1,ShapeExpr shape2) {
		if ((!shape1.getId().isGenerated()) | (!shape2.getId().isGenerated())) {
			if (shape1.getId().isGenerated() != shape2.getId().isGenerated())
				return false;
			if (!shape1.getId().equals(shape2.getId()))
				return false;
		}

		if ((shape1 instanceof ShapeAnd) && (shape2 instanceof ShapeAnd))
			return areEqualsListShapeExpr(((ShapeAnd) shape1).getSubExpressions(),
											  ((ShapeAnd) shape2).getSubExpressions());
		
		if ((shape1 instanceof ShapeOr) && (shape2 instanceof ShapeOr))
			return areEqualsListShapeExpr(((ShapeOr) shape1).getSubExpressions(),
											  ((ShapeOr) shape2).getSubExpressions());
		
		if ((shape1 instanceof ShapeNot) && (shape2 instanceof ShapeNot))
			return areEqualsShapeExpr(((ShapeNot) shape1).getSubExpression(), 
										  ((ShapeNot) shape2).getSubExpression());
		
		if ((shape1 instanceof Shape) && (shape2 instanceof Shape))
			return areEqualsShape((Shape) shape1, (Shape) shape2);
		
		if ((shape1 instanceof NodeConstraint) && (shape2 instanceof NodeConstraint))
			return areEqualsListConstraint(((NodeConstraint) shape1).getConstraints(), 
											   ((NodeConstraint) shape2).getConstraints());
		
		if ((shape1 instanceof ShapeExprRef) && (shape2 instanceof ShapeExprRef))
			return areEqualsShapeExprRef((ShapeExprRef) shape1, (ShapeExprRef) shape2);
		
		if ((shape1 instanceof EmptyShape) && (shape2 instanceof EmptyShape))
			return true;
		
		return false;
	}
	
	private static boolean areEqualsListShapeExpr(List<ShapeExpr> list1,List<ShapeExpr> list2) {
		if (list1.isEmpty() & list2.isEmpty())
			return true;
		
		for (ShapeExpr sh2:list2) {
			List<ShapeExpr> tmp = new ArrayList<ShapeExpr>(list2);
			tmp.remove(sh2);
			if (areEqualsShapeExpr(list1.get(0),sh2) && areEqualsListShapeExpr(list1.subList(1, list1.size()),tmp)){
				return true;				
			}
		}
		
		return false;
	}
	
	private static boolean areEqualsShapeExprRef(ShapeExprRef shape1,ShapeExprRef shape2) {
		return shape1.getLabel().equals(shape2.getLabel());
	}
	
	private static boolean areEqualsShape(Shape shape1,Shape shape2) {
		if (shape1.isClosed()!=shape2.isClosed())
			return false;
		if (shape1.getExtraProperties().size()!=shape2.getExtraProperties().size())
			return false;
		for (TCProperty tcp:shape1.getExtraProperties())
			if (! shape2.getExtraProperties().contains(tcp))
				return false;
		return areEqualsTripleExpr(shape1.getTripleExpression(), shape2.getTripleExpression());
	}
	
	//-------------------------------------------------
	// NodeConstraint Equality
	//-------------------------------------------------

	private static boolean areEqualsListConstraint(List<Constraint> list1, List<Constraint> list2) {
		if (list1.isEmpty() & list2.isEmpty())
			return true;

		for (Constraint sh2:list2) {
			List<Constraint> tmp = new ArrayList<Constraint>(list2);
			tmp.remove(sh2);
			if (areEqualsConstraint(list1.get(0),sh2) && areEqualsListConstraint(list1.subList(1, list1.size()),tmp)){
				return true;				
			}
		}
		
		return false;
	}

	private static boolean areEqualsConstraint(Constraint ct1,Constraint ct2) {
		// Allow to deal with nodekinnd, datatype constraint, facet, stem, Language. Need to deal with StemRange and ValueSetValue
		if (ct1.equals(ct2)) 
			return true;
		

		
		if (ct1 instanceof ValueSetValueConstraint & ct2 instanceof ValueSetValueConstraint) {
			ValueSetValueConstraint vst1 = (ValueSetValueConstraint) ct1;
			ValueSetValueConstraint vst2 = (ValueSetValueConstraint) ct2;
			
			if (vst1.getExplicitValues().size()!=vst2.getExplicitValues().size())
				return false;
			for (Value v:vst1.getExplicitValues())
				if (!vst2.getExplicitValues().contains(v))
					return false;
			
			List<Constraint> lct1 = new ArrayList<Constraint>(vst1.getConstraintsValue());
			List<Constraint> lct2 = new ArrayList<Constraint>(vst2.getConstraintsValue());
			return areEqualsListConstraint(lct1,lct2);
		}
		
		if (ct1 instanceof StemRangeConstraint) {
			StemRangeConstraint str1 = (StemRangeConstraint) ct1;
			StemRangeConstraint str2 = (StemRangeConstraint) ct2;
			
			if (! str1.getStem().equals(str2.getStem()))
				return false;
			return areEqualsConstraint(str1.getExclusions(),str2.getExclusions());
		}
			
		return false;
	}
	
	
	
	//-------------------------------------------------
	// Triple Equality
	//-------------------------------------------------

	private static boolean areEqualsTripleExpr(TripleExpr triple1,TripleExpr triple2) {
		if ((!triple1.getId().isGenerated()) | (!triple2.getId().isGenerated())) {
			if (triple1.getId().isGenerated() != triple2.getId().isGenerated())
				return false;
			if (!triple1.getId().equals(triple2.getId()))
				return false;
		}

		if ((triple1 instanceof EachOf) & (triple2 instanceof EachOf))
			return areEqualsListTripleExpr(((EachOf) triple1).getSubExpressions(),((EachOf) triple2).getSubExpressions());
		if ((triple1 instanceof OneOf) & (triple2 instanceof OneOf))
			return areEqualsListTripleExpr(((OneOf) triple1).getSubExpressions(),((OneOf) triple2).getSubExpressions());
		if ((triple1 instanceof RepeatedTripleExpression) & (triple2 instanceof RepeatedTripleExpression))
			return areEqualsRepeatedTripleExpression((RepeatedTripleExpression) triple1,(RepeatedTripleExpression) triple2);
		if ((triple1 instanceof TripleConstraint) & (triple2 instanceof TripleConstraint))
			return areEqualsTripleConstraint((TripleConstraint) triple1,(TripleConstraint) triple2);
		if ((triple1 instanceof TripleExprRef) & (triple2 instanceof TripleExprRef))
			return areEqualsTripleExprRef((TripleExprRef) triple1,(TripleExprRef) triple2);
		if (triple1 instanceof EmptyTripleExpression & triple2 instanceof EmptyTripleExpression)
			return true;
				
		return false;
	}

	private static boolean areEqualsListTripleExpr(List<TripleExpr> list1,List<TripleExpr> list2) {
		if (list1.isEmpty() & list2.isEmpty())
			return true;
		
		for (TripleExpr triple:list2) {
			List<TripleExpr> tmp = new ArrayList<TripleExpr>(list2);
			tmp.remove(triple);
			if (areEqualsTripleExpr(list1.get(0),triple) & areEqualsListTripleExpr(list1.subList(1, list1.size()),tmp)){
				return true;				
			}
		}
		
		return false;
	}
	
	private static boolean areEqualsRepeatedTripleExpression(RepeatedTripleExpression triple1,RepeatedTripleExpression triple2) {
		if (!triple1.getCardinality().equals(triple2.getCardinality()))
			return false;
		return areEqualsTripleExpr(triple1.getSubExpression(),triple2.getSubExpression());	
	}
	
	private static boolean areEqualsTripleExprRef(TripleExprRef triple1,TripleExprRef triple2) {
		return triple1.getLabel().equals(triple2.getLabel());
	}
	
	private static boolean areEqualsTripleConstraint(TripleConstraint triple1,TripleConstraint triple2) {
		if (!triple1.getProperty().equals(triple2.getProperty()))
			return false;
		return areEqualsShapeExpr(triple1.getShapeExpr(), triple2.getShapeExpr());
	}
	

	
}
