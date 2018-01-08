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

package fr.univLille.cristal.shex.validation;

import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.eachof;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.not;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.se;
import static fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor.tc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.xerces.impl.xs.traversers.XSAttributeChecker;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.junit.Test;

import fr.univLille.cristal.shex.graph.JenaGraph;
import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.SimpleSchemaConstructor;
import fr.univLille.cristal.shex.util.Pair;
import fr.univLille.cristal.shex.validation.RefineValidation;
import fr.univLille.cristal.shex.validation.Typing;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestRefineValidationRDF4J { 
	
	
	@Test
	public void testEmptySchemaWithEmptyModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		
		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();
		Model model = builder.build();

		RefineValidation validation = new RefineValidation(schema, new RDF4JGraph(model));

		validation.validate(null, null);

		assertTrue(validation.getTyping().asSet().isEmpty());
	}

	@Test
	public void testSimpleSchemaWithEmptyModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlallRule();

		constr.addRule("SL1", se(tc("ex:p :: .")));

		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();
		Model model = builder.build();
		
		RDFGraph graph = new RDF4JGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		assertTrue(validation.getTyping().asSet().isEmpty());
	}

	
	@Test
	public void testSimpleSchemaWithSimpleModelRightPropertyIntValue(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("hasValue :: int")));

		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();
		
		builder.add(newIRI("note"), newIRI("hasValue"), newLiteral("12", XMLSchema.INT));

		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);
		
		validation.validate(null, null);

		Typing typing = validation.getTyping();
		
		org.eclipse.rdf4j.model.Resource res = newIRI("note");
		assertTrue(typing.contains(res, newShapeLabel("SL1")));
	}
	
	

	@Test
	public void testSimpleSchemaWithSimpleModelWrongProperty(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("p :: int")));

		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();

		builder.add(newIRI("note"), newIRI("hasValue"), newLiteral("12", XMLSchema.INT));

		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		assertTrue(validation.getTyping().asSet().isEmpty());
	}


	@Test
	public void testSimpleSchemaWithSimpleModelWrongDatatype(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("hasValue :: int")));

		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();

		builder.add(newIRI("note"), newIRI("hasValue"), newLiteral("12.0", XMLSchema.DOUBLE));
		
		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		
		assertTrue(validation.getTyping().asSet().isEmpty());
	}

	
	@Test
	public void testOneTELabelWithTwoShapeLabelsSchemaWithOneStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();
		constr.addSlallRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: .")));
		
		ShexSchema schema = constr.getSchema();


		ModelBuilder builder = new ModelBuilder();
		
		builder.add(newIRI("note"), newIRI("p"), newLiteral("12", XMLSchema.INT));
		
		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		Typing typing = validation.getTyping(); 

		assertEquals(3, typing.asSet().size());
		org.eclipse.rdf4j.model.Resource res = newIRI("note");
		assertTrue(typing.contains(res, newShapeLabel("SL1")));
		assertTrue(typing.contains(res, newShapeLabel("SL2")));
		assertTrue(typing.contains(res, SimpleSchemaConstructor.slAll));
	}

	
	@Test
	public void testTwoTELabelSchemaWithOneStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();
		constr.addSlstringRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: string")));

		ShexSchema schema = constr.getSchema();

		ModelBuilder builder = new ModelBuilder();
		
		builder.add(newIRI("note"), newIRI("p"), newLiteral("12", XMLSchema.INT));
		
		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		
		Set<Pair<org.eclipse.rdf4j.model.Resource, ShapeExprLabel>> set = validation.getTyping().asSet();
		assertEquals(1, set.size());

	}

	
	@Test
	public void testTwoTELabelSchemaWithTwoStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: int")));

		ShexSchema schema = constr.getSchema();
		
		ModelBuilder builder = new ModelBuilder();
		
		builder.add(newIRI("note1"), newIRI("p"), newLiteral("12", XMLSchema.INT));
		builder.add(newIRI("note2"), newIRI("p"), newLiteral("12", XMLSchema.INT));
		
		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);


		Set<Pair<org.eclipse.rdf4j.model.Resource, ShapeExprLabel>> typingSet = validation.getTyping().asSet();

		assertEquals(4, typingSet.size());
		org.eclipse.rdf4j.model.Resource res1 = newIRI("note1");
		org.eclipse.rdf4j.model.Resource res2 = newIRI("note2");
		assertTrue(typingSet.contains(new Pair<>(res1, newShapeLabel("SL1"))));
		assertTrue(typingSet.contains(new Pair<>(res1, newShapeLabel("SL2"))));
		assertTrue(typingSet.contains(new Pair<>(res2, newShapeLabel("SL1"))));
		assertTrue(typingSet.contains(new Pair<>(res2, newShapeLabel("SL2"))));
	}

	/*
	@Test
	public void testOneComplexTELabelSchemaWithOneComplexStatementModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();
		
		constr.addRule("SL1", se(tc("hasControl :: SL2")));
		constr.addRule("SL2", se(tc("hasCoefficient :: int")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		Resource subjectABD = newJenaResource("ABD");
		Property propertyHasControl = newJenaProperty("hasControl");
		Resource subjectControl = newJenaResource("CTP");
		Property propertyHasCoefficient = newJenaProperty("hasCoefficient");
		Literal literal = ResourceFactory.createTypedLiteral("1", XSDDatatype.XSDint);

		model.add(model.createStatement(subjectABD, propertyHasControl, subjectControl));
		model.add(model.createStatement(subjectControl, propertyHasCoefficient, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		org.eclipse.rdf4j.model.Resource resABD = newRDF4JIRI("ABD");
		org.eclipse.rdf4j.model.Resource resCTP = newRDF4JIRI("CTP");
		assertTrue(typing.contains(resABD, new ShapeLabel("SL1")));
		assertTrue(typing.contains(resCTP, new ShapeLabel("SL2")));
		assertEquals(2, typing.asSet().size());

	}

	*/

	@Test
	public void testOneNegatedTELabelSchemaWithOneStatementModel_pass(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.addSlintRule();

		constr.addRule("SL1", not(se(tc("xsd:value :: int"))));

		ShexSchema schema = constr.getSchema();
		
		ModelBuilder builder = new ModelBuilder();

		builder.add(newIRI("toto"), newIRI("value"), newLiteral("1", XMLSchema.STRING));
		
		RDFGraph graph = new RDF4JGraph(builder.build());
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		assertEquals(1, typing.asSet().size());
		org.eclipse.rdf4j.model.Resource res = newIRI("toto");
		assertTrue(typing.contains(res, newShapeLabel("SL1")));
	}

	
	/*
	@Test
	public void testOneNegatedTELabelSchemaWithOneStatementModel_fail(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", not(se(tc("value :: int"))));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		Resource subject1 = newJenaResource("toto");
		Property property = newJenaProperty("value");
		Literal literalString = ResourceFactory.createTypedLiteral("1", XSDDatatype.XSDint);		

		model.add(model.createStatement(subject1, property, literalString));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		assertEquals(0, typing.asSet().size());
	}
	
	@Test
	public void testOneTEEachOfLabelSchemaWithOneStatementModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();
		constr.addSlstringRule();
		
		constr.addRule("SL1", se(eachof("value :: int ; text :: string")));
		

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		Resource subject1 = newJenaResource("toto");
		Property propertyText = newJenaProperty("text");
		Property propertyValue = newJenaProperty("value");
		Literal literalInt = ResourceFactory.createTypedLiteral("1", XSDDatatype.XSDint);

		Literal literalString = ResourceFactory.createTypedLiteral("word", XSDDatatype.XSDstring);		

		model.add(model.createStatement(subject1, propertyValue, literalInt));
		model.add(model.createStatement(subject1, propertyText, literalString));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		
		org.eclipse.rdf4j.model.Resource res = newRDF4JIRI("toto");
		assertTrue(typing.contains(res, new ShapeLabel("SL1")));
		assertEquals(1, typing.asSet().size());
	}
	
	@Test
	public void testOneTEEachOfLabelSchemaWithOneStatementModel_2(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();
		constr.addSlstringRule();
		
		constr.addRule("SL1", se(eachof("xsd:value :: int ; xsd:text :: string")));
		

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		Resource subject1 = newJenaResource("toto");
		Property propertyText = newJenaProperty("text");
		Property propertyValue = newJenaProperty("value");
		Literal literalInt = ResourceFactory.createTypedLiteral("mot", XSDDatatype.XSDstring);

		Literal literalString = ResourceFactory.createTypedLiteral("word", XSDDatatype.XSDstring);		

		model.add(model.createStatement(subject1, propertyValue, literalInt));
		model.add(model.createStatement(subject1, propertyText, literalString));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		
		assertEquals(0, typing.asSet().size());
	}
	*/
	private static IRI newIRI (String localName) {
		return SimpleValueFactory.getInstance().createIRI(SimpleSchemaConstructor.PREFIX + localName);
	}

	private static Literal newLiteral (String valueString, IRI dataType) {
		return SimpleValueFactory.getInstance().createLiteral(valueString, dataType);
	}
	
	public final static String PREFIX = "http://a.ex#";
	public static ShapeExprLabel newShapeLabel (String label){
		return new ShapeExprLabel(SimpleValueFactory.getInstance().createIRI(PREFIX + label));
	}
	
}
