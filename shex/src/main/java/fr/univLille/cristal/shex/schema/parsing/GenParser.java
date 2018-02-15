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

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;

public class GenParser {
	
	public static ShexSchema parseSchema(Path filepath) throws Exception{
		return parseSchema(filepath,Collections.emptyList());
	}
	
	public static ShexSchema parseSchema(Path filepath, Path importDir) throws Exception{
		List<Path> importDirs = new ArrayList<>();
		importDirs.add(importDir);
		return parseSchema(filepath,importDirs);
	}
	
	
	public static ShexSchema parseSchema(Path filepath, List<Path> importDirectories) throws Exception{
		Set<Path> loaded = new HashSet<Path>();
		
		Map<ShapeExprLabel,ShapeExpr> allRules = new HashMap<ShapeExprLabel,ShapeExpr>();
		
		List<Path> toload = new ArrayList<Path>();
		toload.add(filepath);
		
		while(toload.size()>0) {
			Path selectedPath = toload.get(0);
			loaded.add(selectedPath);
			toload.remove(0);

			Parser parser;			
			if (selectedPath.toString().endsWith(".json")) {
				parser = new ShexJParser();
			} else {
				parser = new ShExCParser();
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
