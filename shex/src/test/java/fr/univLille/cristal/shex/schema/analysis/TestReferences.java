/**
Copyright 2017 University of Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package fr.univLille.cristal.shex.schema.analysis;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.univLille.cristal.shex.ConfigurationTest;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
@RunWith(Parameterized.class)
public class TestReferences {
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		Path testReferences = Paths.get(ConfigurationTest.shexTestPath.toString(),"success","TestReferences");
		List<Object[]> listOfParameters = ConfigurationTest.getTestFromDirectory(testReferences, 0);
		
		Path undefinedReferences = Paths.get(ConfigurationTest.shexTestPath.toString(),"failure","UndefinedReference");
		listOfParameters.addAll(ConfigurationTest.getTestFromDirectory(undefinedReferences, 1));
		
		Path cyclicReferences = Paths.get(ConfigurationTest.shexTestPath.toString(),"failure","CyclicReferences");
		listOfParameters.addAll(ConfigurationTest.getTestFromDirectory(cyclicReferences, 2));
		
		
		
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
			ShexSchema schema = GenParser.parseSchema(schemaFile);
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
