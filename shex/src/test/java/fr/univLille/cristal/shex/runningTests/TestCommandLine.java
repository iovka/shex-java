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
