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
package fr.inria.lille.shexjava.schema.analysis;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.exception.CyclicReferencesException;
import fr.inria.lille.shexjava.exception.UndefinedReferenceException;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

/**
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestReferences {
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		Path testReferences = Paths.get(Configuration.shexTestPath.toString(),"success","TestReferences");
		List<Object[]> listOfParameters = Configuration.getTestFromDirectory(testReferences, 0);
		
		Path undefinedReferences = Paths.get(Configuration.shexTestPath.toString(),"failure","UndefinedReference");
		listOfParameters.addAll(Configuration.getTestFromDirectory(undefinedReferences, 1));
		
		Path cyclicReferences = Paths.get(Configuration.shexTestPath.toString(),"failure","CyclicReferences");
		listOfParameters.addAll(Configuration.getTestFromDirectory(cyclicReferences, 2));
		
		return listOfParameters;
	}
		
	@Parameter
	public Path schemaFile;
	@Parameter(1)
	public int status;
	
	
	// status == 0 -> success
	// status == 1 -> UndeFinedReferenceException
	// status == 2 -> CyclicReferenceException
	@Test
	public void parse (){
		try {
			GenParser.parseSchema(schemaFile,null, new SimpleRDF());
		}catch(UndefinedReferenceException e) {
			if (status!=1) {
				fail("Error: undefined reference catch on test ("+schemaFile+")");
			}
		}catch(CyclicReferencesException e) {
			if (status!=2) {
				fail("Error: cyclic references catch on test ("+schemaFile+")");
			}
		}catch(Exception e){
//			System.out.println(schemaFile);
//			System.out.println(e.getClass().getName()+':'+e.getMessage());
			fail(schemaFile+" create error "+e.getClass().getName()+": "+e.getMessage());
		}
	}

}
