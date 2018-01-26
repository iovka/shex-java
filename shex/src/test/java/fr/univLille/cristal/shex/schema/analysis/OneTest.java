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

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import fr.univLille.cristal.shex.Configuration;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.JsonldParser;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class OneTest {

	
	
	@Test
	public void parse (){
		Path schemaFile = Paths.get(Configuration.shexTestPath.toString(),"success","TestExtra","ShapeExtra.json");;
		//Path schemaFile = Paths.get(Configuration.shexTestPath.toString(),"failure","CyclicReferences","TripleReferences.json");;
		int status = 1;
		try {
			JsonldParser parser = new JsonldParser(schemaFile);
			ShexSchema schema = parser.parseSchema();
			if (status==1) {
				fail("Error : test success but it should fail.");
			}
		}catch(IllegalArgumentException e){
			System.out.println(schemaFile);
			System.out.println(e);
			e.printStackTrace();
			if (status == 0) {	
				fail("Error : exception raises but schema should be fine");
			}
		}catch (Exception e) {
			System.out.println(schemaFile);
			e.printStackTrace();
		}
	}
	

}
