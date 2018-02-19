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
package sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

public class ParseRDFWithRio {
	
	public static void main(String[] args) throws IOException {
	
		java.net.URL documentUrl = new URL("file:///home/io/Documents/Research/Projects/Shex/github/shexTest/validation/manifest.ttl");
		InputStream inputStream = documentUrl.openStream();
		
		Model results = Rio.parse(inputStream, documentUrl.toString(), RDFFormat.TURTLE);

	}
	
	

}
