package fr.inria.lille.shexjava.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.Shape;
import fr.inria.lille.shexjava.util.Pair;

public abstract class FailureAnalyzer {
	protected DynamicCollectorOfTripleConstraint collectorTC;
	protected Map<Pair<RDFTerm, Label>,FailureReport> failureReports;

	public FailureAnalyzer() {
		failureReports = new HashMap<>();
		this.collectorTC = new DynamicCollectorOfTripleConstraint();
	}
	
	public void setReport(FailureReport report) {
		failureReports.put(new Pair<RDFTerm, Label>(report.getNode(),report.getLabel()), report);	
	}
	
	public FailureReport getReport(RDFTerm node, Label label) {
		return failureReports.get(new Pair<RDFTerm, Label>(node,label));		
	}

	public abstract void addFailureReportNoTCFound(RDFTerm node, Shape shape, ShapeMap typing, Triple neighbour) ;
	
	public abstract void addFailureReportNoMatchingFound(RDFTerm node, Shape shape, ShapeMap typing, ArrayList<Triple> neighbourhood);
	
}
