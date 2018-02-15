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

package fr.univLille.cristal.shex.commandLine;

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

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.univLille.cristal.shex.graph.RDF4JGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.schema.parsing.ShexJParser;
import fr.univLille.cristal.shex.util.RDFFactory;
import fr.univLille.cristal.shex.validation.RecursiveValidation;
import fr.univLille.cristal.shex.validation.RefineValidation;
import fr.univLille.cristal.shex.validation.ValidationAlgorithm;

/** Command line tool for validation.
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class Validate {
	
	// FIXME not tested
	
	private static RDFFactory factory = RDFFactory.getInstance();
	
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
	 */
	public static void main(String[] args) {
		System.out.println(COPYRIGHT);
		
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
		
		if (! parameters.get("-a").equals("refine") && parameters.get("-a").equals("recursive")) {
			System.out.println("Invalid algorithm : " + parameters.get("-a"));
			System.out.println(USAGE);
			return;
		}
		
		if (parameters.get("-a").equals("recursive")) {
			System.out.println("Recursive validation not supported in the current version.");
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
			System.err.println("Was unable to the parse data. Aborting.");
			return;
		}
		
		Resource focusNode = null;
		if (parameters.get("-f") != null)
			focusNode = factory.createIRI(parameters.get("-f"));
		
		ShapeExprLabel shapeLabel = null;
		if (parameters.get("-l") != null)
			shapeLabel = new ShapeExprLabel(SimpleValueFactory.getInstance().createIRI(parameters.get("-l")));
		
		ValidationAlgorithm val = null;
		switch (parameters.get("-a")) {
		case "refine" : val = new RefineValidation(schema, new RDF4JGraph(dataModel)); break;
		case "recursive" : val = new RecursiveValidation(schema, new RDF4JGraph(dataModel)); break;
		}
		
		System.out.println("Validating graph " + parameters.get("-d") + " against schema " + parameters.get("-s") + ".");
		val.validate(focusNode, shapeLabel);
		System.out.println("Typing constructed.");
		
		if (focusNode != null && shapeLabel != null)
			if (val.getTyping().contains(focusNode, shapeLabel))
				System.out.println(String.format("%s SATISFIES %s", focusNode, shapeLabel));
			else 
				System.out.println(String.format("%s DOES NOT SATISFY %s", focusNode, shapeLabel));
		
		
		if (parameters.get("-out") != null) {
			String output = val.getTyping().asSet().toString();
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
			schema = GenParser.parseSchema(Paths.get(schemaFileName));
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
	
	private static final String COPYRIGHT;
	static {
		StringBuilder text = new StringBuilder();
		text.append("ShEx validator v0.5 (binary).\n");
		text.append("Copyright Universite de Lille--Sciences et Technologies and Inria.\n");
		text.append("Author and contact: Iovka Boneva (iovka.boneva@univ-lille1.fr)\n");
		text.append("November 2016, proprietary licence. See the LICENSE file for details.\n");
		COPYRIGHT = text.toString();
	}

}
