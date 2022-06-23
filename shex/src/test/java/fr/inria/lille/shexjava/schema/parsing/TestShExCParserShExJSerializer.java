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
package fr.inria.lille.shexjava.schema.parsing;

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

import fr.inria.lille.shexjava.shexTest.AbstractShexTestRunner;
import org.apache.commons.rdf.api.RDF;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.util.CommonFactory;
import fr.inria.lille.shexjava.util.SchemaEquality;
import fr.inria.lille.shexjava.util.TestCase;
import fr.inria.lille.shexjava.util.TestResultForTestReport;


/** Parse ShExC schemas from the shexTestSuite.
 * @author Jérémie Dusart
 * @author Iovka Boneva
 *
 */
public class TestShExCParserShExJSerializer extends AbstractShexTestRunner {

	@Override
	public void executeTest () throws Exception {
		ShexSchema fromJson = null;
		ShexSchema toJson = null;
		Path schemaFile = getSchemaFile(testCase.schemaFileName, "shex");
		RDF factory = new CommonFactory();

		fromJson = GenParser.parseSchema(schemaFile, Collections.singletonList(AbstractShexTestRunner.SCHEMAS_DIR), factory); // exception possible
		Path tmp = Paths.get("tmp_fromjson.json");
		ShExJSerializer.ToJson(fromJson, tmp);
		toJson = GenParser.parseSchema(tmp, null, factory);
		tmp.toFile().delete();

		if (SchemaEquality.areEquals(fromJson, toJson)) {
			passed.add(new TestResultForTestReport(testCase.testName, true, null, "same"));
		} else {
			failed.add(new TestResultForTestReport(testCase.testName, false, null, "failed"));
			fail("Failed: " + testCase.testName);
		}
	}
}
