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

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		if (!filepath.toFile().exists())
			throw new FileNotFoundException("File "+filepath+" not found.");

		if (importDirectories == null)
			importDirectories=Collections.emptyList();

		Set<Path> loaded = new HashSet<Path>();
		Map<Label,ShapeExpr> allRules = new HashMap<Label,ShapeExpr>();

		List<Path> toload = new ArrayList<Path>();
		toload.add(filepath);

		ShapeExpr start = null;
		boolean init = true;

		while(toload.size()>0) {
			Path selectedPath = toload.get(0);
			loaded.add(selectedPath);
			toload.remove(0);

			Parser parser;
			if (selectedPath.toString().endsWith(".json")) {
				parser = new ShExJParser();
			} else if (selectedPath.toString().endsWith(".shex")) {
				parser = new ShExCParser();
			}else if (selectedPath.toString().endsWith(".ttl")){
				parser = new ShExRParser();
			} else
				throw new IllegalArgumentException("File format not recognized. Files with extension .json or .shex or .ttl are allowed");

			allRules.putAll(parser.getRules(rdfFactory,selectedPath));
			if (init) {
				start = parser.getStart();
				init = false;
			}
			List<String> imports = parser.getImports();

			for (String imp:imports) {
				Path res = null;
				for (Path p:importDirectories) {
					if (Paths.get(p.toString(),imp+".shex").toFile().exists()) {
						res = Paths.get(p.toString(),imp+".shex");
						break;
					} else if (Paths.get(p.toString(),imp+".json").toFile().exists()) {
						res = Paths.get(p.toString(),imp+".json");
						break;
					} else {
						for (RDFFormat format:ShExRParser.RDFFormats) {
							for (String ext:format.getFileExtensions()) {
								if (Paths.get(p.toString(),imp+"."+ext).toFile().exists()) {
									res = Paths.get(p.toString(),imp+"."+ext);
								}
							}
						}
					}
				}
				if (res == null){
					throw new FileNotFoundException("Failed to resolved import "+imp+" from "+selectedPath+".");
				}
				if (! loaded.contains(res))
					toload.add(res);
			}
		}
		return new ShexSchema(rdfFactory,allRules,start);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF)}  instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath) throws Exception{
		return parseSchema(filepath,Collections.emptyList(),GlobalFactory.RDFFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath) throws Exception{
		if (rdfFactory == null)
			rdfFactory = GlobalFactory.RDFFactory;
		return parseSchema(filepath,Collections.emptyList(),rdfFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath, Path importDir) throws Exception{
		return parseSchema(filepath,Collections.singletonList(importDir),GlobalFactory.RDFFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, Path importDir) throws Exception{
		if (rdfFactory == null)
			rdfFactory = GlobalFactory.RDFFactory;
		return parseSchema(filepath,Collections.singletonList(importDir),rdfFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories) throws Exception{
		return parseSchema(filepath,importDirectories,GlobalFactory.RDFFactory);
	}

	/**
	 * @deprecated Use {@link #parseSchema(Path, List, RDF) instead}
	 */
	@Deprecated
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, List<Path> importDirectories) throws Exception {
		return parseSchema(filepath, importDirectories, rdfFactory);
	}



}
