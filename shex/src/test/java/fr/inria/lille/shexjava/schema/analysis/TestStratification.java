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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.exception.NotStratifiedException;
import fr.inria.lille.shexjava.schema.parsing.GenParser;

/**
 * @author Jérémie Dusart
 *
 */
@RunWith(Parameterized.class)
public class TestStratification {
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		Path testStratification = Paths.get(Configuration.shexTestPath.toString(),"success","TestStratification");
		List<Object[]> listOfParameters = Configuration.getTestFromDirectory(testStratification, 0);
		
		Path errorStratification = Paths.get(Configuration.shexTestPath.toString(),"failure","TestExtra");
		listOfParameters.addAll(Configuration.getTestFromDirectory(errorStratification, 1));
		
		errorStratification = Paths.get(Configuration.shexTestPath.toString(),"failure","TestNot");
		listOfParameters.addAll(Configuration.getTestFromDirectory(errorStratification, 1));
		
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
			GenParser.parseSchema(schemaFile);
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
