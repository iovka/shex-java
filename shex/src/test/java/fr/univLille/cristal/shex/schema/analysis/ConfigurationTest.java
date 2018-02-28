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
package fr.univLille.cristal.shex.schema.analysis;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class ConfigurationTest {
	public static Path shexTestPath = Paths.get("src","test","ressources");

	
	public static List<Object[]> getTestFromDirectory(Path testDirectory,int result) throws IOException{
		List<Object[]> listOfParameters = new LinkedList<Object[]>();

		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(testDirectory);
		for (Path path : directoryStream) {
			if (path.toString().endsWith(".json")) {
				Object [] parameters = new Object[2]; 
				parameters[0] = path;
				parameters[1] = result;
				listOfParameters.add(parameters);

			}
		}
		return listOfParameters;
	}
}
