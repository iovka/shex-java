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
