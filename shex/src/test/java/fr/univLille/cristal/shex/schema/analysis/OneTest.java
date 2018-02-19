package fr.univLille.cristal.shex.schema.analysis;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import fr.univLille.cristal.shex.ConfigurationTest;
import fr.univLille.cristal.shex.schema.ShexSchema;
import fr.univLille.cristal.shex.schema.parsing.GenParser;


/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class OneTest {

	
	
	@Test
	public void parse (){
		Path schemaFile = Paths.get(ConfigurationTest.shexTestPath.toString(),"success","TestStratification","ShapeExtra.json");;
		//Path schemaFile = Paths.get(Configuration.shexTestPath.toString(),"failure","CyclicReferences","TripleReferences.json");;
		int status = 0;
		try {
			ShexSchema schema = GenParser.parseSchema(schemaFile);
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
