/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univLille.cristal.shex.schema.ShapeLabel;
import fr.univLille.cristal.shex.schema.analysis.SchemaRulesStaticAnalysis;


/** Represents the rules of a schema, together with different statically computed properties useful for validation.
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public class SchemaRules implements Iterable<ShapeLabel> {

	public enum Instrumentation {
		/** Unfolds repetitions on arbitrary expressions. */
		UNFOLDED_REPETITIONS;
		// TODO: how to do this ? Duplicating triple constraints ? How can triple constraints be used ?
	}
	
	private static final Map<Instrumentation, List<Instrumentation>> instrumentationsDependency = new HashMap<>();
	static {
		instrumentationsDependency.put(Instrumentation.UNFOLDED_REPETITIONS, new ArrayList<>());
	}

	private Map<ShapeLabel, ShapeDefinition> rules;

	public SchemaRules(Map<ShapeLabel, ShapeDefinition> rules) {
		// Check that all shape labels are defined
		Set<ShapeLabel> undefinedLabels = SchemaRulesStaticAnalysis.computeUndefinedShapeLabels(rules);
		if (! undefinedLabels.isEmpty())
			throw new IllegalArgumentException("Undefined shape labels: " + undefinedLabels);
		
		// Check that there are no cyclic shape ref dependencies
		List<List<ShapeLabel>> cyclicShapeRefDependencies = SchemaRulesStaticAnalysis.computeCyclicShapeRefDependencies(rules);
		if (! cyclicShapeRefDependencies.isEmpty())
			throw new IllegalArgumentException("Cyclic dependency of shape refences: " + cyclicShapeRefDependencies.get(0));
		
		// Enrich all shape references with the corresponding shape definition
		Set<ShapeRef> shapeRefs = SchemaRulesStaticAnalysis.collectAllShapeRefs(rules.values());
		for (ShapeRef ref : shapeRefs)
			ref.setShapeDefinition(rules.get(ref.getLabel()));
		
		List<Set<ShapeLabel>> stratification = SchemaRulesStaticAnalysis.computeStratification(rules);
		if (stratification == null)
			throw new IllegalArgumentException("The set of rules is not stratified.");
		else
			setStratification(stratification);
		this.rules = new HashMap<>(rules);
	}


	private Set<Instrumentation> appliedInstrumentations = new HashSet<>();

	public boolean isApplied (Instrumentation instr) {
		return appliedInstrumentations.contains(instr);
	}
	
	public ShapeDefinition getRule (ShapeLabel label) {
		return rules.get(label);
	}
	
	@Override
	public String toString() {
		return rules.toString();
	}
	
	@Override
	public Iterator<ShapeLabel> iterator() {
		return rules.keySet().iterator();
	}
	
	
	/*
	public void applyInstrumentationRec (Instrumentation instr) {
		if (appliedInstrumentations.contains(instr))
			return;
		List<Instrumentation> dependencies = instrumentationsDependency.get(instr);
		for (Instrumentation dep : dependencies) {
			if (! appliedInstrumentations.contains(dep)) {
				applyInstrumentationRec(dep);
			} 
		}
		applyInstrumentation(instr);
	}
	
	private void applyInstrumentation(Instrumentation instr) {
		if (appliedInstrumentations.contains(instr))
			return;
		switch (instr){
		case STRATIFICATION:
			List<Set<ShapeLabel>> str = computeStratification();
			if (str == null)
				throw new IllegalArgumentException("The set of rules is not stratified.");
			setStratification(str);
			break;
			
		default: 
			throw new UnsupportedOperationException("not yet implemented");
		}
		appliedInstrumentations.add(instr);
	}
	
    */
	
	
	// -------------------------------------------------------------------------
	// STRATIFICATION
	// -------------------------------------------------------------------------
	private List<Set<ShapeLabel>> stratification = null;
	
	public List<Set<ShapeLabel>> getStratification() {
		return this.stratification;
	}

	private void setStratification(List<Set<ShapeLabel>> stratification) {
		if (this.stratification != null)
			throw new IllegalStateException();
		
		List<Set<ShapeLabel>> tmp = new ArrayList<>();
		for (Set<ShapeLabel> strat : stratification) {
			tmp.add(Collections.unmodifiableSet(strat));
		}
		this.stratification = Collections.unmodifiableList(tmp);		
	}

}
