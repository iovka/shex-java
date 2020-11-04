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

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.github.jsonldjava.utils.JsonUtils;
import com.google.common.io.Files;
import fr.inria.lille.shexjava.shapeMap.BaseShapeMap;
import fr.inria.lille.shexjava.shapeMap.ResultShapeMap;
import fr.inria.lille.shexjava.shapeMap.abstrsynt.ShapeAssociation;
import fr.inria.lille.shexjava.shapeMap.parsing.ShapeMapParsing;
import fr.inria.lille.shexjava.util.Pair;
import fr.inria.lille.shexjava.validation.*;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.apache.jena.rdf.model.RDFNode;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

/** Command line tool for validation.
 * 
 * @author Iovka Boneva
 * @author Jérémie Dusart
 */
public class Validate {	
	private final static RDF rdfFactory = new SimpleRDF();

	public static final String USAGE;
	static {
		StringBuilder text = new StringBuilder();
		text.append("Usage:\n");
		text.append("  -s <schema file>          : path to a ShEx schema\n");
		text.append("  -d <data file>            : path to a data graph\n");
		text.append("  -a \"refine\" | \"recursive\" | \"memrecursive\" : the algorithm to be used\n");
		text.append("  -sm <shape map file>      : (optional) path to an input shape map\n");
		text.append("  -f <focus node> -l <shape label> : (optional) IRIs of the node and the shape label to be checked for validity\n");
		text.append("  -out <output file>        : (optional) a path to a file where the result shape map is to be written\n");
		text.append("  -h                        : prints a help message\n");
		USAGE = text.toString();
	}

	public static final String HELP;
	static {
		StringBuilder text = new StringBuilder();
		text.append(USAGE);
		text.append("\n");
		text.append("Use -sm to validate an input shape map.\n");
		text.append("Alternatively, use -f and -l to validate a single node against a single shape label; the validity result is printed on standard output.\n");
		text.append("\n");
		text.append("If -out is given, then writes the result shape map in the file.\n");
		text.append("\n");
		text.append("If none of -sm or -f and -l is given, then validates all nodes against all shapes using refine validation (ignores the algorithm). No result is printed on standard output, so useless w/o -out.\n");
		text.append("\n");
		text.append("The supported algorithms are: \n");
		text.append("  - refine         Validates all nodes against all shapes, the result is the maximal typing.\n");
		text.append("                   To be used to compute the maximal typing once and query it as many times as necessary w/o the need for further validation.\n");
		text.append("                   Can also be interesting in case where the size of the input shape map is big, i.e. comparable to the size of the input graph.\n");
		text.append("  - recursive      Validates only portion of the graph to determine validity of the input shape map.\n");
		text.append("                   To be used with small input shape maps, with small input data or with input graphs with limited amount of sharing and schemas with limited amount of recursion.\n");
		text.append("  - memrecursive   Similar to recursive validation, but stores intermediary results.\n");
		text.append("                   Preferable to refine when the size of the input shape map is small relatively to the size of the graph.\n");
		text.append("                   Preferable to recursive if the schema has lots of recursion or there is lots of sharing in the graph.\n");
		HELP = text.toString();
	}

	/** Validates a graph against a schema.
	 * See {@link #USAGE} and {@link #HELP}.
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println(USAGE);
			System.exit(1);
		}

		if (args.length == 1 && "-h".equals(args[0])) {
			System.out.println(HELP);
			System.exit(1);
		}

		Map<String, String> parameters = parseParameters(args);
		if (parameters == null) {
			System.out.println("Incorrect list of parameters.");
			System.out.println(USAGE);
			System.exit(1);
		}
		
		if (! parameters.containsKey("-s") ||
				! parameters.containsKey("-d") ||
				! parameters.containsKey("-a")) {
			System.out.println("Mandatory argument among -s -d -a missing.");
			System.out.println(USAGE);
			System.exit(1);
		}
		
		if (!Arrays.asList("refine", "recursive", "memrecursive").contains(parameters.get("-a"))) {
			System.out.println("Invalid algorithm : " + parameters.get("-a"));
			System.out.println(USAGE);
			System.exit(1);
		}

		if (! (parameters.containsKey("-f") && parameters.containsKey("-l"))
			^ parameters.containsKey("-sm")) {
			System.out.println("Invalid list of parameters: use either -sm or -f and -l\n");
			System.out.println(USAGE);
			System.exit(1);
		}
				
		ShexSchema schema = getSchema(parameters.get("-s"));
		if (schema == null) {
			System.err.println("Was unable to parse the schema. Aborting.");
			System.exit(1);
		}
		
		Graph dataModel = new RDF4J().asGraph(getData(parameters.get("-d")));
		if (dataModel == null) {
			System.err.println("Was unable to parse the data. Aborting.");
			System.exit(1);
		}

		RDFTerm focusNode = null;
		Label shapeLabel = null;

		try {
			if (parameters.containsKey("-f"))
				focusNode = rdfFactory.createIRI(parameters.get("-f"));
			if (parameters.containsKey("-l"))
				shapeLabel = new Label(rdfFactory.createIRI(parameters.get("-l")));
		} catch (Exception e) {
			System.err.println("Incorrect format for focus node or shape label");
			System.err.println("Caused by:");
			System.err.println(e.getMessage());
		}

		BaseShapeMap inputShapeMap = null;
		if (parameters.containsKey("-sm")) {
			inputShapeMap = getShapeMap(parameters.get("-sm"));
			if (inputShapeMap == null) {
				System.err.println("Was unable to parse the shape map.");
				System.exit(1);
			}
		}

		ValidationAlgorithmAbstract val = null;
		switch (parameters.get("-a")) {
			case "refine" : val = new RefineValidation(schema, dataModel); break;
			case "recursive" : val = new RecursiveValidation(schema, dataModel); break;
			case "memrecursive" : val = new RecursiveValidationWithMemorization(schema, dataModel); break;
 		}
		
		System.out.println("Validating graph " + parameters.get("-d") + " against schema " + parameters.get("-s") + ".");
		if (inputShapeMap != null) {
			ResultShapeMap resultMap = val.validate(inputShapeMap);

			if (parameters.containsKey("-out"))
				try {
					writeResultMapToFile(resultMap, parameters.get("-out"));
				} catch (Exception e) {
					System.err.println("Error writing the result.");
					System.err.println("Caused by: ");
					System.err.println(e.getMessage());
				}
		} else {
			boolean result = val.validate(focusNode, shapeLabel);
			System.out.println(String.format("%s %s %s", focusNode, result ? "SATISFIES" : "DOES NOT SATISFY", shapeLabel));

			if (parameters.containsKey("-out"))
				try {
					writeStatusMapToFile(val.getTyping().getStatusMap(), parameters.get("-out"));
				} catch (Exception e) {
					System.err.println("Error writing the result.");
					System.err.println("Caused by: ");
					System.err.println(e.getMessage());
				}
		}
		System.out.println("Validation complete.");
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
			InputStream inputStream = new FileInputStream(new File(dataFileName));
			dataModel = Rio.parse(inputStream, dataFileName, Rio.getParserFormatForFileName(dataFileName).orElse(RDFFormat.RDFXML));
		} catch (Exception e) {
			System.err.println("Error while reading the data file.");
			System.err.println("Caused by: ");
			System.err.println(e.getMessage());
			return null;
		}
		return dataModel;
	}


	private static BaseShapeMap getShapeMap (String shapeMapFileName) {

		BaseShapeMap shapeMap = null;
		try {
			InputStream inputStream = new FileInputStream(new File(shapeMapFileName));
			ShapeMapParsing parser = new ShapeMapParsing();
			shapeMap = parser.parse(inputStream);
		} catch (Exception e) {
			System.err.println("Error while reading the shape map file.");
			System.err.println("Caused by: ");
			System.err.println(e.getMessage());
			return null;
		}
		return shapeMap;
	}

	private static Map<String,String> parseParameters(String[] args) {
		if (args.length % 2 == 1)
			return null;
		Map<String, String> parameters = new HashMap<>();
		Set<String> correctParameters = new HashSet<>();
		correctParameters.add("-s"); correctParameters.add("-d"); 	
		correctParameters.add("-f"); correctParameters.add("-l");
		correctParameters.add("-a"); correctParameters.add("-out");
		correctParameters.add("-sm"); correctParameters.add("-h");
		
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

	/** Writes a result shape map to a file, i.e. a shape map in which all node selectors are RDF nodes. */
	private static void writeResultMapToFile (ResultShapeMap shapeMap, String outputFile) throws IOException {
		BufferedWriter fw = Files.newWriter(Paths.get(outputFile).toFile(), Charset.defaultCharset());
		List<Object> associations = new ArrayList<>();
		for (ShapeAssociation sa : shapeMap.getAssociations()) {
			RDFTerm node = sa.getNodeSelector().apply(null).iterator().next();
			Label label = sa.getShapeSelector().apply(null);
			Status status = sa.getStatus().get();
			Map<String, String> associationMap = new LinkedHashMap<>(3);
			associationMap.put("nodeSelector", node.ntriplesString());
			associationMap.put("shapeLabel", label.stringValue());
			associationMap.put("status", status.name().toLowerCase());
			associations.add(associationMap);
		}
		JsonUtils.writePrettyPrint(fw, associations);
	}

	private static void writeStatusMapToFile (Map<Pair<RDFTerm, Label>, Status> statusMap, String outputFile) throws IOException {
		BufferedWriter fw = Files.newWriter(Paths.get(outputFile).toFile(), Charset.defaultCharset());
		List<Object> associations = new ArrayList<>();
		for (Map.Entry<Pair<RDFTerm, Label>, Status> e : statusMap.entrySet()) {
			RDFTerm node = e.getKey().one;
			Label label = e.getKey().two;
			Status status = e.getValue();
			Map<String, String> associationMap = new LinkedHashMap<>(3);
			associationMap.put("nodeSelector", node.ntriplesString());
			associationMap.put("shapeLabel", label.stringValue());
			associationMap.put("status", status.name().toLowerCase());
			associations.add(associationMap);
		}
		JsonUtils.writePrettyPrint(fw, associations);
	}

}
