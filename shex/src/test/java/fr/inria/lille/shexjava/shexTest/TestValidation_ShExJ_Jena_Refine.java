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
package fr.inria.lille.shexjava.shexTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.inria.lille.shexjava.validation.*;
import org.apache.commons.rdf.api.Graph;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;


/** Run the validation tests of the shexTest suite using ShExJ parser, JenaGraph and refine validation.
 * @author Jérémie Dusart
 * @author Iovka Boneva
 *
 */
@RunWith(Parameterized.class)
public class TestValidation_ShExJ_Jena_Refine extends AbstractValidationTest {

	@Override
	protected String skipTestReason () {
		String reason = super.skipTestReason();
		if (reason != null)
			return reason;
		if (ignoredTests.contains(testCase.testName)) {
			return String.format("Test [%s] skipped. Unsupported test on bnode with Jena.", testCase.testName);
		}
		return null;
	}

	//A list of tests to ignore because they contain a test on the name of a bnode and jena do not give stable bnode name
	protected static final Set<String> ignoredTests = new HashSet<>(Arrays.asList(
			"1focusMaxLength-dot_pass-bnode-short",
			"1nonliteralMinlength_fail-bnode-short",
			"1bnodeMaxlength_pass-bnode-equal",
			"1nonliteralMaxlength_pass-bnode-short",
			"1focusBNODELength_dot_pass",
			"1nonliteralMaxlength_pass-bnode-equal",
			"1focusMaxLength-dot_pass-bnode-equal",
			"1bnodeLength_pass-bnode-equal",
			"1bnodePattern_fail-bnode-long",
			"1nonliteralLength_pass-bnode-equal",
			"1nonliteralPattern_pass-bnode-long",
			"1focusLength-dot_pass-bnode-equal",
			"1nonliteralPattern_pass-bnode-match",
			"1focusMinLength-dot_pass-bnode-long",
			"1bnodePattern_pass-bnode-match",
			"1bnodeMaxlength_pass-bnode-short",
			"1focusBNODE_dot_pass",
			"1focusPatternB-dot_pass-bnode-long",
			"1focusMinLength-dot_pass-bnode-equal",
			"1bnodeMinlength_fail-bnode-short",
			"1focusPatternB-dot_pass-bnode-match",
			"1iriPattern_fail-bnode-match",
			"1nonliteralPattern_fail-lit-match",
			"1bnodePattern_fail-bnode-short"));

	@Override
	protected void fixTest() {
		if (testCase.focusNode instanceof org.apache.commons.rdf.api.IRI) {
			org.apache.commons.rdf.api.IRI focus = (org.apache.commons.rdf.api.IRI) testCase.focusNode;
			if (focus.getIRIString().startsWith(BASE_IRI)) {
				if (TEST_DIR.toString().contains(":")) {
					String newURI = TEST_DIR.toString().substring(0,TEST_DIR.toString().indexOf(":")+1);
					newURI += focus.getIRIString().substring(BASE_IRI.length()+11);
					testCase.focusNode = GlobalFactory.RDFFactory.createIRI(newURI);
				}else {
					Path fullpath = TEST_DIR.resolve(focus.getIRIString().substring(BASE_IRI.length()));
					testCase.focusNode = GlobalFactory.RDFFactory.createIRI("file://"+fullpath.toString());
				}
			}
		}
	}

	@Override
	protected Path getSchemaFile() {
		return getSchemaFile(testCase.schemaFileName, "json");
	}

	@Override
	protected Graph getRDFGraph() throws IOException {
		return getRDFGraph_Jena(testCase.dataFileName);
	}

	@Override
	protected ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		return new RefineValidation(schema, dataGraph);
	}


}
