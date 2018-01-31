package sandbox;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import fr.univLille.cristal.shex.graph.TCProperty;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExprRef;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.util.Interval;

public class CreateShapeExpression {
	
	public static void main(String[] args) {
		ValueFactory RDF_FACTORY = SimpleValueFactory.getInstance();
		
		ShapeExprRef ref = new ShapeExprRef(new ShapeExprLabel(RDF_FACTORY.createIRI("http://example.org/S")));
		TCProperty prop = TCProperty.createFwProperty(RDF_FACTORY.createIRI("http://example.org/p"));
		TripleConstraint tc =  TripleConstraint.newSingleton(prop, ref);
		RepeatedTripleExpression expr = new RepeatedTripleExpression(tc, Interval.PLUS);
		
		System.out.println(expr);
		
	}

}
