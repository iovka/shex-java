package fr.univLille.cristal.shex.runningTests;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;

public 	class TestCase {
	public final Resource testKind;
	public final String testName;
	public final String schemaFileName;
	public final String dataFileName;
	public final ShapeExprLabel shapeLabel;
	public final Value focusNode;
	public final String testComment;

	public TestCase(String testName, String schemaFileName, String dataFileName, ShapeExprLabel shapeLabel, Value focusNode, String testComment, Resource testKind) {
		super();
		this.testName = testName;
		this.schemaFileName = schemaFileName;
		this.dataFileName = dataFileName;
		this.shapeLabel = shapeLabel;
		this.focusNode = focusNode;
		this.testComment = testComment;
		this.testKind = testKind;
	}

	@Override
	public String toString() {
		String info = "";
		info += testName + "\n";
		info += testKind.toString() + "\n";
		info += "Comment    : " + testComment + "\n";
		info += "Schema file: " + schemaFileName + "\n";
		info += "Data file  : " + dataFileName + "\n";
		info += "Focus : " + focusNode + "\n";
		info += "Shape : " + shapeLabel + "\n";
		return info;
	}		

	boolean isWellDefined () {
		return schemaFileName != null && dataFileName != null && shapeLabel != null && focusNode != null;
	}
}
