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
package fr.inria.lille.shexjava.commandLine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.RefineValidation;
import fr.inria.lille.shexjava.validation.ValidationAlgorithm;

/** Command line tool for validation.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class Validate {	
	private final static RDF rdfFactory = new SimpleRDF();
	
	/** Validates a graph against a schema.
	 * 
	 * Options:
	 * -s <schema file>
	 * -d <data file>
	 * -f <focus node> : optional
	 * -l <shape label> : optional
	 * -a "refine" | "recursive"
	 * -out <file name>
	 * 
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println(USAGE);
			return;
		}
		
		Map<String, String> parameters = parseParameters(args);
		if (parameters == null) {
			System.out.println("Incorrect list of parameters.");
			System.out.println(USAGE);
			return;
		}
		
		if (! parameters.keySet().contains("-s") ||
				! parameters.keySet().contains("-d") ||
				! parameters.keySet().contains("-a")) {
			System.out.println("Mandatory argument missing.");
			System.out.println(USAGE);
			return;
		}
		
		if (! parameters.get("-a").equals("refine") && ! parameters.get("-a").equals("recursive")) {
			System.out.println("Invalid algorithm : " + parameters.get("-a"));
			System.out.println(USAGE);
			return;
		}
				
		ShexSchema schema = getSchema(parameters.get("-s"));
		if (schema == null) {
			System.err.println("Was unable to parse the schema. Aborting.");
			return;
		}
		
		Model dataModel = getData(parameters.get("-d"));
		if (dataModel == null) {
			System.err.println("Was unable to parse the data. Aborting.");
			return;
		}
		
		RDFTerm focusNode = null;
		if (parameters.get("-f") != null)
			focusNode = rdfFactory.createIRI(parameters.get("-f"));
		
		Label shapeLabel = null;
		if (parameters.get("-l") != null)
			shapeLabel = new Label(rdfFactory.createIRI(parameters.get("-l")));
		
		ValidationAlgorithm val = null;
		switch (parameters.get("-a")) {
			case "refine" : val = new RefineValidation(schema, (new RDF4J()).asGraph(dataModel)); break;
			case "recursive" : val = new RecursiveValidation(schema, (new RDF4J()).asGraph(dataModel)); break;
		}
		
		System.out.println("Validating graph " + parameters.get("-d") + " against schema " + parameters.get("-s") + ".");
		val.validate(focusNode, shapeLabel);
		System.out.println("Typing constructed.");
		
		if (focusNode != null && shapeLabel != null)
			if (val.getShapeMap().isConformant(focusNode, shapeLabel))
				System.out.println(String.format("%s SATISFIES %s", focusNode, shapeLabel));
			else 
				System.out.println(String.format("%s DOES NOT SATISFY %s", focusNode, shapeLabel));
		
		
		if (parameters.get("-out") != null) {
			String output = val.getShapeMap().getAllStatus().toString();
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(parameters.get("-out")))) {
			    writer.write(output, 0, output.length());
			    System.out.println("Typing written in " + parameters.get("-out"));
			} catch (IOException x) {
			    System.err.format("I/O Error while writing the output");
			    return;
			}
		}
	}
	
	
	private static ShexSchema getSchema (String schemaFileName) {
		ShexSchema schema;
		try {
			schema = GenParser.parseSchema(rdfFactory,Paths.get(schemaFileName));
		} catch (IOException e) {
			System.err.println("Error reading the schema file.");
			System.err.println("Caused by: ");
			System.err.println(e.getMessage());;
			return null;
		} catch (Exception e) {
			System.err.println("Error while parsing the schema file. Caused by: " + e.getMessage());
			e.printStackTrace(System.err);
			return null;
		}
		return schema;
	}
	
	private static Model getData (String dataFileName) {
		
		Model dataModel;
		
		try {
			java.net.URL documentUrl = new URL(dataFileName);
			InputStream inputStream = documentUrl.openStream();
			dataModel  = Rio.parse(inputStream, documentUrl.toString(), RDFFormat.TURTLE);
		} catch (Exception e) {
			System.err.println("Error while reading the data file.");
			System.err.println("Caused by: ");
			System.err.println(e.getMessage());
			return null;
		}
		return dataModel;
	}
	
	
	
	private static Map<String,String> parseParameters(String[] args) {
		if (args.length % 2 == 1)
			return null;
		Map<String, String> parameters = new HashMap<>();
		Set<String> correctParameters = new HashSet<>();
		correctParameters.add("-s"); correctParameters.add("-d"); 	
		correctParameters.add("-f"); correctParameters.add("-l");
		correctParameters.add("-a"); correctParameters.add("-out"); 	
		
		for (int i = 0; i < args.length; i+=2) {
			String param = args[i];
			if (! correctParameters.contains(param))
				return null;
			if (parameters.containsKey(param))
				return null;
			String arg = args[i+1];
			parameters.put(param, arg);
		}
		return parameters;
	}
	
	private static final String USAGE;
	static {
		StringBuilder text = new StringBuilder();
		text.append("Usage:\n");
		text.append("  -s <schema file>          : path to a ShEx schema in json format\n");
		text.append("  -d <data file>            : path to a data file in .ttl format\n");
		text.append("  -f <focus node>           : (optional) IRI of the node to be checked\n");
		text.append("  -l <shape label>          : (optional) IRI of a sape label to be checked\n");
		text.append("  -a \"refine\" | \"recursive\" : the algorithm to be used\n");
		text.append("  -out <output file>        : (optional) a path to a file where the resulting typing will be written\n");
		USAGE = text.toString();
	}

}
