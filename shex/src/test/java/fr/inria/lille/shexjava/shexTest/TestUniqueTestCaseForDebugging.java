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

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.RecursiveValidationWithMemorization;
import fr.inria.lille.shexjava.validation.RefineValidation;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;
import org.apache.commons.rdf.api.Graph;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;


/** Run a unique test case, for debugging purposes.
 * @author Iovka Boneva
 *
 */
@RunWith(Parameterized.class)
public class TestUniqueTestCaseForDebugging extends AbstractValidationTest {

	private static String uniqueTestName = "0_empty";

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() throws IOException {
		return allValidationTestsInManifestFile()
				.parallelStream()
				.filter(tc -> uniqueTestName.equals(tc.testName))
				.map(tc -> new Object[]{tc})
				.collect(Collectors.toList());
	}

	@Override
    protected String getSchemaFileName () {
		// Choose here the schema format
		//return getSchemaFileName_ShExJ(testCase.schemaFileName);
		//return getSchemaFileName_ShExR(testCase.schemaFileName);
		return getSchemaFileName_ShExC(testCase.schemaFileName);
	}

	@Override
	protected Graph getRDFGraph() throws IOException {
		// Choose here the graph model format
		//return getRDFGraph_Jena(testCase.dataFileName);
		return getRDFGraph_RDF4J(testCase.dataFileName);
	}

	@Override
	protected ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		// Choose here the validation algorithm
		//return new RecursiveValidation(schema, dataGraph);
		return new RefineValidation(schema, dataGraph);
		//return new RecursiveValidationWithMemorization(schema, dataGraph);
	}

	
}
