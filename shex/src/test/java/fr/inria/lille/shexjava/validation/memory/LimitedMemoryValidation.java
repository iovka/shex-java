package fr.inria.lille.shexjava.validation.memory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import fr.inria.lille.shexjava.schema.parsing.ShExC.ShExDocParser;
import fr.inria.lille.shexjava.util.RDF4JFactory;
import fr.inria.lille.shexjava.validation.*;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.junit.Test;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.ResultShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSelector;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSelectorFilterSubject;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.NodeSeletorRDFTerm;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelector;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeSelectorLabel;
import fr.inria.lille.shexjava.util.Pair;

public class LimitedMemoryValidation {

	
	@Test//(expected=OutOfMemoryError.class)
	//public void shouldFailWithLimitedMemory() throws Exception {
	public static void main (String[] args) throws Exception {

		Pair<String, String> dataSchemaFiles = getDataSchemaFilesNames();

		Path dataFile = Paths.get("/", "home","io","Documents", "Research", "tmp", "data", dataSchemaFiles.one);
		Path schemaFile = Paths.get("/", "home","io","Documents", "Research", "tmp", "schemas", dataSchemaFiles.two);
		List<Path> importDirectories = Collections.emptyList();
	
		RDF4J factory = new RDF4J(); 
		GlobalFactory.RDFFactory = factory; //set the global factory used in shexjava
		
		// load and create the shex schema
		ShexSchema schema = GenParser.parseSchema(schemaFile,importDirectories);
		
		// load the model
		String baseIRI = "http://a.example.shex/";
		// TODO: il faut isoler ça de la mémoire pour mon test. Comment faire ? serveur RDF4J avec SPARQL endpoint ?
		Model data = Rio.parse(new FileInputStream(dataFile.toFile()), baseIRI, RDFFormat.TURTLE);
		
		// create the graph
		Graph dataGraph = factory.asGraph(data);
		doValidation(schema, dataGraph, factory);

	}

	// ----------------------------------------------------------------------------------------
	// Test Customization section
	// ----------------------------------------------------------------------------------------

	// Customize the data and schema files
	static Pair<String, String> getDataSchemaFilesNames () {
		String dataFile;
		String schemaFile;

		dataFile = "classicaldb.ttl";
		schemaFile = "classical-recanalyse.shex";

		return new Pair<>(dataFile, schemaFile);
	}

	// Customize the validation algorithm to run in test
	static ValidationAlgorithm getValidationAlgorithm (ShexSchema schema, Graph dataGraph) {
		// return new RecursiveValidation(schema, dataGraph);
		//return new RecursiveValidationWithMemorization(schema, dataGraph);
		return new RefineValidation(schema, dataGraph);
	}

	// Customize the type and shape to use in test
	static Pair<IRI, Label> getTypeShapeForValidation (RDF4J factory) {
		IRI type;
		Label shape;

		/*
		type = factory.createIRI("http://purl.org/ontology/mo/MusicalWork");
		shape = new Label(factory.createIRI("http://shex.gen/MusicalWork"));
		 */

		type = factory.createIRI("http://dbtune.org/classical/resource/type/Composer");
		shape = new Label(factory.createIRI("http://shex.gen/Composer"));

		return new Pair<>(type, shape);
	}

	// ----------------------------------------------------------------------------------------

	static void doValidation (ShexSchema schema, Graph dataGraph, RDF4J factory) {
		ValidationAlgorithm validation = getValidationAlgorithm(schema, dataGraph);
		Pair<IRI, Label> typeShape = getTypeShapeForValidation(factory);

		IRI type = typeShape.one;
		Label shape = typeShape.two;

		// create the input shape map
		IRI rdftype = factory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

		ShapeSelector ss = new ShapeSelectorLabel(shape);
		NodeSelector ns = new NodeSelectorFilterSubject(rdftype, type);
		ShapeAssociation sa = new ShapeAssociation(ns, ss);
		BaseShapeMap shmap = new BaseShapeMap(Collections.singletonList(sa));
		ResultShapeMap result = validation.validate(shmap);

		int nb = 0;
		for (ShapeAssociation x : result.getAssociations()) {
			//System.out.println(x);
			nb++;
		}
		System.out.println("Number of valid associations : " + nb);

		Scanner s = new Scanner(System.in);
		int count = 0;
		Map<Pair<RDFTerm, Label>, Status> typingMap = validation.getTyping().getStatusMap();
		for (Map.Entry<Pair<RDFTerm, Label>, Status> e : typingMap.entrySet()) {
			System.out.println(e);
			count++;
			if (count % 10 == 0)
				s.nextLine();
		}
	}

}
