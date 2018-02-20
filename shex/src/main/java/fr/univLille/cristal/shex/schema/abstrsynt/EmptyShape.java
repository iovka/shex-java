package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.Collections;


public class EmptyShape extends NodeConstraint{
	public static final EmptyShape Shape = new EmptyShape();

	protected EmptyShape() {
		super(Collections.emptyList());
	}
	
	public String toString() {
		return ".";
	}

}
