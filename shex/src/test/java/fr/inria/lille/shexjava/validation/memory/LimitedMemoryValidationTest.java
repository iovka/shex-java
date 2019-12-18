package fr.inria.lille.shexjava.validation.memory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.RecursiveValidationWithMemorization;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.ValidationAlgorithm;

public class LimitedMemoryValidationTest {

	
	@Test//(expected=OutOfMemoryError.class)
	//public void shouldFailWithLimitedMemory() throws Exception {
	public static void main (String[] args) throws Exception {
	
		Path dataFile = Paths.get("/", "home","io","Documents", "Research", "tmp", "data", "classicaldb-20091211-d.ttl"); 
		Path schemaFile = Paths.get("/", "home","io","Documents", "Research", "tmp", "schemas", "classical.shex");  
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
		
		ValidationAlgorithm validation = new RecursiveValidationWithMemorization(schema, dataGraph);
		
		// create the input shape map
		IRI rdftype = factory.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		IRI moComposition = factory.createIRI("http://purl.org/ontology/mo/Composition");
		Label compositionShapeLabel = new Label(factory.createIRI("http://shex.gen/Composition"));
		ShapeSelector ss = new ShapeSelectorLabel(compositionShapeLabel);

		
		
		// With single shape association
		NodeSelector ns = new NodeSelectorFilterSubject(rdftype, moComposition);
		ShapeAssociation sa = new ShapeAssociation(ns, ss);
		BaseShapeMap shmap = new BaseShapeMap(Collections.singletonList(sa)); 
		ResultShapeMap result = validation.validate(shmap);	
		
		// With multiple shape associations
		/*
		Set<RDFTerm> nodes = dataGraph.stream(null, rdftype, moComposition)
				.map(tr -> tr.getSubject()).collect(Collectors.toSet());
		List<ShapeAssociation> sa = new ArrayList<>();
		for (RDFTerm n : nodes) {
			sa.add(new ShapeAssociation(new NodeSeletorRDFTerm(n), ss));
		}
		BaseShapeMap shmap = new BaseShapeMap(sa); 
		ResultShapeMap result = validation.validate(shmap);*/
		
		// Common to both validations with shape associations
		
		int nb = 0;
		for (ShapeAssociation x : result.getAssociations()) {
			System.out.println(x);
			nb++;
			if (nb % 10 == 0)
				System.out.print(".");
		}
		System.out.println("Number of valid associations : " + nb);
		
		
		// Iterating through all the nodes, no shape association
		/*Set<RDFTerm> nodes = dataGraph.stream(null, rdftype, moComposition)
				.map(tr -> tr.getSubject()).collect(Collectors.toSet());
		for (RDFTerm n : nodes) {
			validation.validate(n, compositionShapeLabel);
		}
		
		int nb = 0;
		for (Map.Entry<Pair<RDFTerm,Label>, Status> e: validation.getTyping().getStatusMap().entrySet()) {
			if (e.getKey().two.equals(compositionShapeLabel)) {
				System.out.println(e.getKey().one + " " + e.getKey().two + " : " + e.getValue());
				nb++;
			}
		}
		System.out.println("" + nb + " nodes validated");
		*/	
	}
	
	
}
