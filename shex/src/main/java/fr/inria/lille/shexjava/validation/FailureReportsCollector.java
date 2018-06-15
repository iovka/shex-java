package fr.inria.lille.shexjava.validation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class FailureReportsCollector {
	protected Map<Pair<RDFTerm, Label>,FailureReport> failureReports;

	public FailureReportsCollector() {
		failureReports = new HashMap<>();
	}
	
	public void setReport(RDFTerm node, Label label, FailureReport report) {
		failureReports.put(new Pair<RDFTerm, Label>(node,label), report);	
	}
	
	public FailureReport getReport(RDFTerm node, Label label) {
		return failureReports.get(new Pair<RDFTerm, Label>(node,label));		
	}

	
	
}
