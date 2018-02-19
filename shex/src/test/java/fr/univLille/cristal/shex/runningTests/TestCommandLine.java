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
package fr.univLille.cristal.shex.runningTests;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;

import fr.univLille.cristal.shex.runningTests.RunTests.TestCase;

/** Generate all the test cases as command line parameters and write them in a file" 
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestCommandLine {
	
	public static void main(String[] args) throws IOException {

		Model manifest = RunTests.loadManifest();
		String outFileName = args[0];
						
		try (PrintStream out = new PrintStream(Files.newOutputStream(Paths.get(outFileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE))) {
		
			for (Resource testNode : RunTests.getValidationTestsList(manifest)) {
				//Object[] shouldRun = RunTests.shouldRunTest(testNode, manifest);
				//if ((Boolean) shouldRun[1]) {
					TestCase testCase = RunTests.parseTestCase(manifest, testNode);
					out.println(String.format(" -s %s -d %s -a refine -f %s -l %s", 
							RunTests.TEST_DIR + "/" + testCase.schemaFileName,
							RunTests.TEST_DIR + "/validation/"  + testCase.dataFileName,
							testCase.focusNode, 
							testCase.shapeLabel));
				//}		
			}

			for (Resource testNode : RunTests.getFailureTestsList(manifest)) {
				//Object[] shouldRun = RunTests.shouldRunTest(testNode, manifest);
				//if ((Boolean) shouldRun[1]) {
					TestCase testCase = RunTests.parseTestCase(manifest, testNode);
					out.println(String.format(" -s %s -d %s -a refine -f %s -l %s", 
							RunTests.TEST_DIR + "/" + testCase.schemaFileName,
							RunTests.TEST_DIR + "/validation/" + testCase.dataFileName,
							testCase.focusNode, 
							testCase.shapeLabel));
				//}
			}
		}
		
	}
	
	/*
	-s /tmp/schema.json
	-d /tmp/data.ttl
	-a "refine"
	-f http://a.example/s1
	-l http://a.example/S1
	-out /tmp/typing.txt
	*/
}
