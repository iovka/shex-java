package fr.inria.lille.shexjava.schema.FOL.formula;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.datatypes.XMLDatatypeUtil;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.util.Pair;

public class OperatorRestricted extends Operator {

	public OperatorRestricted(Variable v1, Variable v2) {
		super(v1, v2);
	}
	
	public int evaluate(Map<Variable,Value> affectations,
			Set<Pair<Value, Label>> shapes,
			Set<Pair<Pair<Value,Value>, Label>> triples) throws Exception {
		if (affectations.containsKey(v1) && !isCorrectlyDefined(affectations.get(v1)))
			throw new Exception("Incorrect value for variable: "+v1.name);
		if (affectations.containsKey(v2) && !isCorrectlyDefined(affectations.get(v2)))
			throw new Exception("Incorrect value for variable: "+v2);
		if (!affectations.containsKey(v1) || !affectations.containsKey(v2))
			return 2;
		return -1;
	}
	public static final Set<IRI> validateDatatype = new HashSet<>(Arrays.asList(new IRI[] {
			XMLSchema.INTEGER,
			XMLSchema.DECIMAL,
			XMLSchema.FLOAT,
			XMLSchema.DOUBLE,
			XMLSchema.STRING,
			XMLSchema.DATETIME,
			XMLSchema.NON_POSITIVE_INTEGER,
			XMLSchema.NEGATIVE_INTEGER,
			XMLSchema.LONG,
			XMLSchema.INT,
			XMLSchema.SHORT,
			XMLSchema.BYTE,
			XMLSchema.NON_NEGATIVE_INTEGER,
			XMLSchema.UNSIGNED_LONG,
			XMLSchema.UNSIGNED_INT,
			XMLSchema.UNSIGNED_SHORT,
			XMLSchema.UNSIGNED_BYTE,
			XMLSchema.POSITIVE_INTEGER
	}));
	
	public static boolean isCorrectlyDefined(Value v1) {
		return (v1 instanceof Literal) && validateDatatype.contains(((Literal) v1).getDatatype());
	}
	
	public static boolean isStrictlyInferior(Value v1,Value v2) {
		Literal lv1 = (Literal) v1;
		Literal lv2 = (Literal) v2;
		if (lv1.getDatatype().equals(XMLSchema.STRING))
			return lv1.stringValue().compareTo(lv2.stringValue()) < 0;
		return XMLDatatypeUtil.compare(lv1.stringValue(), lv2.stringValue(), lv1.getDatatype()) < 0;
	}
	
	public static boolean isEqual(Value v1,Value v2) {
		Literal lv1 = (Literal) v1;
		Literal lv2 = (Literal) v2;
		if (lv1.getDatatype().equals(XMLSchema.STRING))
			return lv1.stringValue().compareTo(lv2.stringValue()) == 0;
		return XMLDatatypeUtil.compare(lv1.stringValue(), lv2.stringValue(), lv1.getDatatype()) == 0;
	}
}
