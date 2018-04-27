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

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.abstrsynt.ShapeExpr;

public interface Parser {
	
	/** Return the set of rules contains in the file.
	 * @param path
	 * @return the set of rules contains in the file
	 * @throws Exception
	 */
	public Map<Label,ShapeExpr> getRules(Path path) throws Exception;
	
	/** Return the set of rules contains in the inputstream.
	 * @param is
	 * @return the set of rules contains in the inputstream
	 * @throws Exception
	 */
	public Map<Label,ShapeExpr> getRules(InputStream is) throws Exception;
	
	/** Return the list of import declarations found during the last parsing.
	 * @return the list of import declarations found during the last parsing
	 */
	public List<String> getImports();

}
