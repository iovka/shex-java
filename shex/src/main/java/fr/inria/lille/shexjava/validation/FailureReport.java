package fr.inria.lille.shexjava.validation;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;

public class FailureReport {
	protected RDFTerm node;
	protected Label label;
	protected String message;

	public FailureReport(RDFTerm node, Label label, String message) {
		super();
		this.node = node;
		this.label = label;
		this.message = message;
	}

	public RDFTerm getNode() {
		return node;
	}

	public Label getLabel() {
		return label;
	}

	public String getMessage() {
		return message;
	}
	
}
