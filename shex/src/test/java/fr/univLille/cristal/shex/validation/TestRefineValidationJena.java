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

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.Test;

import fr.univLille.cristal.shex.graph.JenaGraph;
import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.abstrsynt.SchemaRules;
import fr.univLille.cristal.shex.schema.ShapeLabel;
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
public class TestRefineValidationJena { 
	
	
	@Test
	public void testEmptySchemaWithEmptyModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		
		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		RefineValidation validation = new RefineValidation(schema, new JenaGraph(model));

		validation.validate(null, null);

		assertTrue(validation.getTyping().asSet().isEmpty());
	}

	@Test
	public void testSimpleSchemaWithEmptyModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlallRule();

		constr.addRule("SL1", se(tc("ex:p :: .")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		assertTrue(validation.getTyping().asSet().isEmpty());
	}


	@Test
	public void testSimpleSchemaWithSimpleModelRightPropertyIntValue(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("hasValue :: int")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject = newJenaResource("note");
		Property property = newJenaProperty("hasValue");
		Literal literal = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);


		model.add(model.createStatement(subject, property, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);
		
		validation.validate(null, null);

		Typing typing = validation.getTyping();
		
		org.eclipse.rdf4j.model.Resource res = newRDF4JIRI("note");
		assertTrue(typing.contains(res, new ShapeLabel("SL1")));
	}

	@Test
	public void testSimpleSchemaWithSimpleModelWrongProperty(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("p :: int")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject = newJenaResource("note");
		Property property = newJenaProperty("hasValue");
		Literal literal = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);

		model.add(model.createStatement(subject, property, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		assertTrue(validation.getTyping().asSet().isEmpty());
	}


	@Test
	public void testSimpleSchemaWithSimpleModelWrongDatatype(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("hasValue :: int")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject = newJenaResource("note");
		Property property = newJenaProperty("hasValue");
		Literal literal = ResourceFactory.createTypedLiteral("12.0", XSDDatatype.XSDdouble);


		model.add(model.createStatement(subject, property, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		
		assertTrue(validation.getTyping().asSet().isEmpty());
	}

	@Test
	public void testOneTELabelWithTwoShapeLabelsSchemaWithOneStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();
		constr.addSlallRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: .")));
		
		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject = newJenaResource("note");
		Property property = newJenaProperty("p");
		Literal literal = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);

		model.add(model.createStatement(subject, property, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		Typing typing = validation.getTyping(); 

		assertEquals(3, typing.asSet().size());
		org.eclipse.rdf4j.model.Resource res = newRDF4JIRI("note");
		assertTrue(typing.contains(res, new ShapeLabel("SL1")));
		assertTrue(typing.contains(res, new ShapeLabel("SL2")));
		assertTrue(typing.contains(res, SimpleSchemaConstructor.slAll));
	}


	@Test
	public void testTwoTELabelSchemaWithOneStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();
		constr.addSlstringRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: string")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject = newJenaResource("note");
		Property property = newJenaProperty("p");
		Literal literal = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);


		
		model.add(model.createStatement(subject, property, literal));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);
		
		Set<Pair<org.eclipse.rdf4j.model.Resource, ShapeLabel>> set = validation.getTyping().asSet();
		assertEquals(1, set.size());

	}


	@Test
	public void testTwoTELabelSchemaWithTwoStatementsModel(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", se(tc("p :: int")));
		constr.addRule("SL2", se(tc("p :: int")));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));


		Model model = ModelFactory.createDefaultModel();

		Resource subject1 = newJenaResource("note1");
		Property property1 = newJenaProperty("p");
		Literal literal1 = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);

		Resource subject2 = newJenaResource("note2");
		Property property2 = newJenaProperty("p");
		Literal literal2 = ResourceFactory.createTypedLiteral("12", XSDDatatype.XSDint);


		model.add(model.createStatement(subject1, property1, literal1));
		model.add(model.createStatement(subject2, property2, literal2));

		RDFGraph graph = new JenaGraph(model);
		
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);


		Set<Pair<org.eclipse.rdf4j.model.Resource, ShapeLabel>> typingSet = validation.getTyping().asSet();

		assertEquals(4, typingSet.size());
		org.eclipse.rdf4j.model.Resource res1 = newRDF4JIRI("note1");
		org.eclipse.rdf4j.model.Resource res2 = newRDF4JIRI("note2");
		assertTrue(typingSet.contains(new Pair<>(res1, new ShapeLabel("SL1"))));
		assertTrue(typingSet.contains(new Pair<>(res1, new ShapeLabel("SL2"))));
		assertTrue(typingSet.contains(new Pair<>(res2, new ShapeLabel("SL1"))));
		assertTrue(typingSet.contains(new Pair<>(res2, new ShapeLabel("SL2"))));
	}

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

	@Test
	public void testOneNegatedTELabelSchemaWithOneStatementModel_pass(){

		SimpleSchemaConstructor constr = new SimpleSchemaConstructor();
		constr.clearRuleMaps();
		constr.addSlintRule();

		constr.addRule("SL1", not(se(tc("xsd:value :: int"))));

		ShexSchema schema = new ShexSchema(new SchemaRules(constr.getRulesMap()));

		Model model = ModelFactory.createDefaultModel();

		Resource subject1 = newJenaResource("toto");
		Property property = newJenaProperty("value");
		Literal literalString = ResourceFactory.createTypedLiteral("1", XSDDatatype.XSDstring);		

		model.add(model.createStatement(subject1, property, literalString));

		RDFGraph graph = new JenaGraph(model);
		RefineValidation validation = new RefineValidation(schema, graph);

		validation.validate(null, null);

		Typing typing = validation.getTyping();
		assertEquals(1, typing.asSet().size());
		org.eclipse.rdf4j.model.Resource res = newRDF4JIRI("toto");
		assertTrue(typing.contains(res, new ShapeLabel("SL1")));
	}

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
	
	private static org.apache.jena.rdf.model.Resource newJenaResource (String localName) {
		return ResourceFactory.createResource(SimpleSchemaConstructor.PREFIX + localName);
	}
	private static org.apache.jena.rdf.model.Property newJenaProperty (String localName) {
		return ResourceFactory.createProperty(SimpleSchemaConstructor.PREFIX + localName);
	}

	private static org.eclipse.rdf4j.model.Resource newRDF4JIRI (String localName) {
		return SimpleValueFactory.getInstance().createIRI(SimpleSchemaConstructor.PREFIX + localName);
	}

	
	
}
