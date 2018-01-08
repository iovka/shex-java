package fr.univLille.cristal.shex.schema.analysis;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.output.ThresholdingOutputStream;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;

public class InstrumentationMentionedPropertiesOnShapes extends Instrumentation<Shape, Set<TCProperty>> {

	
	private static InstrumentationMentionedPropertiesOnShapes theInstance = new InstrumentationMentionedPropertiesOnShapes();

	public static InstrumentationMentionedPropertiesOnShapes getInstance() {
		return theInstance;
	}
	
	protected InstrumentationMentionedPropertiesOnShapes() {
		super("MENTIONED_PROPERTIES");
	}

	@Override
	public Set<TCProperty> compute(Shape shape, Object... context) {	
		Set<TripleConstraint> set = SchemaRulesStaticAnalysis.collectTripleConstraints(shape.getTripleExpression());
		return set.stream().map(tc -> tc.getProperty()).collect(Collectors.toSet());
	}

}
