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
	
	public void removeReport(RDFTerm node, Label label) {
		failureReports.remove(new Pair<RDFTerm, Label>(node,label));	
	}
	
	public boolean hasReport(RDFTerm node, Label label) {
		return failureReports.containsKey(new Pair<RDFTerm, Label>(node,label));	
	}

	public abstract void addFailureReportNoTCFound(RDFTerm node, Shape shape, TypingForValidation typing, Triple neighbour) ;
	
	public abstract void addFailureReportNoMatchingFound(RDFTerm node, Shape shape, TypingForValidation typing, ArrayList<Triple> neighbourhood);
	
}
