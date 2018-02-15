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
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;
import fr.univLille.cristal.shex.schema.parsing.ShexJParser;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
@RunWith(Parameterized.class)
public class TestStratification {
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		Path testStratification = Paths.get(ConfigurationTest.shexTestPath.toString(),"success","TestStratification");
		List<Object[]> listOfParameters = ConfigurationTest.getTestFromDirectory(testStratification, 0);
		
		Path errorStratification = Paths.get(ConfigurationTest.shexTestPath.toString(),"failure","TestExtra");
		listOfParameters.addAll(ConfigurationTest.getTestFromDirectory(errorStratification, 1));
		
		errorStratification = Paths.get(ConfigurationTest.shexTestPath.toString(),"failure","TestNot");
		listOfParameters.addAll(ConfigurationTest.getTestFromDirectory(errorStratification, 1));
		
		return listOfParameters;
	}
		
	@Parameter
	public Path schemaFile;
	@Parameter(1)
	public int status;
	
	
	// status == 0 -> success
	// status == 1 -> NotStratifiedException
	@Test
	public void parse (){
		try {
			ShexSchema schema = GenParser.parseSchema(schemaFile);
		}catch(NotStratifiedException e) {
			if (status!=1) {
				fail("Error: schema not stratified for test: "+schemaFile+".");
			}
		}catch(Exception e){
//			System.out.println(schemaFile);
//			System.out.println(e.getClass().getName()+':'+e.getMessage());
//			e.printStackTrace();
			fail(schemaFile+" create error "+e.getClass().getName()+": "+e.getMessage());
		}
	}

}
