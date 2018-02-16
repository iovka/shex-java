package fr.univLille.cristal.shex.runningTests;

import java.nio.file.Paths;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.FOAF;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.util.Pair;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RefineValidation;
import fr.univLille.cristal.shex.validation.RefinementTyping;

public class OneTest {
	private static final RDFFactory RDF_FACTORY = RDFFactory.getInstance();
	
	public static void main(String[] args) throws Exception {
		Model model = new TreeModel();
		model.add(RDF_FACTORY.createBNode("JD"), FOAF.AGE, RDF_FACTORY.createLiteral(29));
		model.add(RDF_FACTORY.createBNode("JD"), FOAF.MBOX, RDF_FACTORY.createLiteral("test@test.org"));
		model.add(RDF_FACTORY.createBNode("JD"), FOAF.FIRST_NAME, RDF_FACTORY.createLiteral("Jérémie"));
		
		//model.add(RDF_FACTORY.createBNode("lol"), FOAF.MBOX, RDF_FACTORY.createLiteral("mdr@mdr.org"));
		//model.add(RDF_FACTORY.createBNode("lol"), FOAF.FIRST_NAME, RDF_FACTORY.createLiteral("MDR"));
		
		RDF4JGraph graph = new RDF4JGraph(model);
		
		ShexSchema schema = GenParser.parseSchema(Paths.get(".","User.json"));

		Value focusNode = RDF_FACTORY.createBNode("JD");
		ShapeExprLabel label = new ShapeExprLabel(RDF_FACTORY.createIRI("http://a.example/USER")); 
		
		RefineValidation valid = new RefineValidation(schema, graph);
		valid.validate(focusNode, label);
		RefinementTyping res = (RefinementTyping) valid.getTyping();
		
		for( Pair<Value, ShapeExprLabel> element: res.asSet()) {
			System.out.println(element);
		}
		
		}
}
