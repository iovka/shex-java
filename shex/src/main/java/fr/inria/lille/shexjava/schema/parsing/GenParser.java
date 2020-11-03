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

import org.apache.commons.rdf.api.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;

/** For all the functions, the parser used depend of the file extension.
 * @author Jérémie Dusart
 *
 */
public class GenParser {
	
	public static ShexSchema parseSchema(Path filepath) throws Exception{
		return parseSchema(GlobalFactory.RDFFactory,filepath,Collections.emptyList());
	}
	
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath) throws Exception{
		return parseSchema(rdfFactory,filepath,Collections.emptyList());
	}
	
	public static ShexSchema parseSchema(Path filepath, Path importDir) throws Exception{
		List<Path> importDirs = new ArrayList<>();
		importDirs.add(importDir);
		return parseSchema(GlobalFactory.RDFFactory,filepath,importDirs);
	}
	
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, Path importDir) throws Exception{
		List<Path> importDirs = new ArrayList<>();
		importDirs.add(importDir);
		return parseSchema(rdfFactory,filepath,importDirs);
	}
	
	
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories) throws Exception{
		return parseSchema(GlobalFactory.RDFFactory,filepath,importDirectories);
	}
	
	/** The function try to find the imports, if any, in the list of directories provided. The format of the schema is infer from the file extension.
	 * @param filepath
	 * @param importDirectories
	 * @return the parsed ShexSchema
	 * @throws Exception
	 */
	public static ShexSchema parseSchema(RDF rdfFactory, Path filepath, List<Path> importDirectories) throws Exception{
		if (!filepath.toFile().exists())
			throw new FileNotFoundException("File "+filepath+" not found.");
		
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
			}else {
				parser = new ShExRParser();
			}
			Map<Label,ShapeExpr> newRules = parser.getRules(rdfFactory,selectedPath);
			if (init) {
				start = parser.getStart();
				init = false;
			} else {
				// The start rule has a null key. We don't want IMPORTS
				// to evict the start rule from the first schema.
				newRules.remove(null);
			}
			allRules.putAll(newRules);
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
					throw new FileNotFoundException("Faild to resolved import "+imp+" from "+selectedPath+".");
				}
				if (! loaded.contains(res))
					toload.add(res);					
			}
		}
		ShexSchema schema = new ShexSchema(rdfFactory,allRules,start);
		return schema;
	}


}
