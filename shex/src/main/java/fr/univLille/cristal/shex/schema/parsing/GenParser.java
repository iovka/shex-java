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
package fr.univLille.cristal.shex.schema.parsing;

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

import org.eclipse.rdf4j.rio.RDFFormat;

import fr.univLille.cristal.shex.schema.Label;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;

/** For all the functions, the parser used depend of the file extension of the files.
 * @author Jérémie Dusart
 *
 */
public class GenParser {
	
	
	public static ShexSchema parseSchema(Path filepath) throws Exception{
		return parseSchema(filepath,Collections.emptyList());
	}
	
	public static ShexSchema parseSchema(Path filepath, Path importDir) throws Exception{
		List<Path> importDirs = new ArrayList<>();
		importDirs.add(importDir);
		return parseSchema(filepath,importDirs);
	}
	
	
	/** The function try to find the imports, if any, in the list of directories provided.
	 * @param filepath
	 * @param importDirectories
	 * @return
	 * @throws Exception
	 */
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories) throws Exception{
		if (!filepath.toFile().exists())
			throw new FileNotFoundException("File "+filepath+" not found.");
		
		Set<Path> loaded = new HashSet<Path>();
		Map<Label,ShapeExpr> allRules = new HashMap<Label,ShapeExpr>();
		
		List<Path> toload = new ArrayList<Path>();
		toload.add(filepath);
		
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
			allRules.putAll(parser.getRules(selectedPath));
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
		ShexSchema schema = new ShexSchema(allRules);
		return schema;
	}


}
