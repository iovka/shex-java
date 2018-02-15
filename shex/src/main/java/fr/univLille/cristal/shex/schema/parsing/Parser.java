package fr.univLille.cristal.shex.schema.parsing;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.ShapeExpr;

public interface Parser {
	
	public Map<ShapeExprLabel,ShapeExpr> getRules(Path path) throws Exception;
	
	public List<String> getImports();

}
