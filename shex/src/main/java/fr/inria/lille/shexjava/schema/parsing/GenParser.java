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
package fr.inria.lille.shexjava.schema.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.inria.lille.shexjava.schema.Label;
import org.apache.commons.rdf.api.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;

/** Parses a ShEx schema.
 * The schema format is determined by the file extension. Can be .shex for ShExC or .json for ShExJ or .ttl for ShExR.
 * @author Jérémie Dusart
 * @author Iovka Boneva
 *
 */
public class GenParser {

	/** Parses a schema. If import directories are provided, tries to resolve imports in these directories.
	 * @param filepath the schema file path
	 * @param importDirectories can be null or empty
	 * @param rdfFactory if null, a default factory is used
	 * @return the parsed ShexSchema
	 * @throws Exception
	 */
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories, RDF rdfFactory) throws Exception {
		if (importDirectories == null)
			importDirectories=Collections.emptyList();

		if (rdfFactory == null)
			rdfFactory = GlobalFactory.RDFFactory;

		Set<Path> loaded = new HashSet<Path>();
		Map<Label,ShapeExpr> allRules = new HashMap<Label,ShapeExpr>();

		Deque<Path> toload = new ArrayDeque<Path>();
		toload.add(filepath);

		ShapeExpr start = null;
		boolean isTopMostFile = true;

		while(! toload.isEmpty()) {
			Path currentPath = toload.remove();
			loaded.add(currentPath);

			Parser parser = getAppropriateSchemaParser(currentPath);
			allRules.putAll(parser.getRules(rdfFactory, currentPath));
			if (isTopMostFile) {
				start = parser.getStart();
				isTopMostFile = false;
			}
			for (String importedSchemaFileName : parser.getImports()) {
				Path schemaFile = getSchemaFile(importedSchemaFileName, importDirectories);
				if (schemaFile != null)
					throw new FileNotFoundException("Failed to resolved import "+schemaFile+" imported in "+currentPath+".");
				else if (! loaded.contains(schemaFile))
					toload.add(schemaFile);
			}
		}
		return new ShexSchema(allRules, start, rdfFactory);
	}

	private static Parser getAppropriateSchemaParser (Path schemaFilePath) {
		String fileName = schemaFilePath.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		if ("json".equals(extension))
			return new ShExJParser();
		if ("shex".equals(extension))
			return new ShExCParser();
		if (ShExRParser.FILE_EXTENSIONS.contains(extension))
			return new ShExRParser();
		throw new IllegalArgumentException("File extension not recognized : " + extension);
	}

	/** Returns the actual file that contains the schema with the given name, whenever it exists in one of the import directories.
	 * Returns null if no such file exists.
	 * @param fileNameBase
	 * @param importDirectories
	 * @return
	 */
	private static Path getSchemaFile(String fileNameBase, List<Path> importDirectories) {
		List<String> allowedExtensions = new ArrayList<>(ShExRParser.FILE_EXTENSIONS);
		allowedExtensions.add("json");
		allowedExtensions.add("shex");
		for (Path dir : importDirectories) {
			for (String ext : allowedExtensions) {
				Path candidateFile = Paths.get(dir.toString(), fileNameBase, ".", ext);
				if (Files.exists(candidateFile))
					return candidateFile;
			}
		}
		return null;
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF)}  instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath) throws Exception{
		return parseSchema(filepath,Collections.emptyList(),null);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath) throws Exception{
		return parseSchema(filepath,Collections.emptyList(),rdfFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath, Path importDir) throws Exception{
		return parseSchema(filepath,Collections.singletonList(importDir),null);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, Path importDir) throws Exception{
		return parseSchema(filepath,Collections.singletonList(importDir),rdfFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories) throws Exception{
		return parseSchema(filepath,importDirectories,null);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, List<Path> importDirectories) throws Exception {
		return parseSchema(filepath, importDirectories, rdfFactory);
	}



}
