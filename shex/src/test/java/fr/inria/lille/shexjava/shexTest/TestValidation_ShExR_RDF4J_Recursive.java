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

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.lille.shexjava.GlobalFactory;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.Status;
import fr.inria.lille.shexjava.validation.Typing;
import fr.inria.lille.shexjava.validation.ValidationAlgorithmAbstract;


/** Run the validation tests of the shexTest suite using ShExC parser, RDF4JGraph and recursive validation.
 * @author Jérémie Dusart
 * @author Iovka Boneva
 *
 */
@RunWith(Parameterized.class)
public class TestValidation_ShExR_RDF4J_Recursive extends AbstractValidationTest {

	@Override
    protected String getSchemaFileName () {
		return getSchemaFileName_ShExR(testCase.schemaFileName);
	}

	@Override
	protected Graph getRDFGraph() throws IOException {
		return getRDFGraph_RDF4J(testCase.dataFileName);
	}

	@Override
	protected ValidationAlgorithmAbstract getValidationAlgorithm(ShexSchema schema, Graph dataGraph ) {
		return new RecursiveValidation(schema, dataGraph);
	}

}
