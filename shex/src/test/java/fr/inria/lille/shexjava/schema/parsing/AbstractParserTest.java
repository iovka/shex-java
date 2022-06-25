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

import fr.inria.lille.shexjava.shexTest.AbstractShexTestRunner;
import org.eclipse.rdf4j.model.Resource;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Iovka Boneva
 */
public abstract class AbstractParserTest extends AbstractShexTestRunner {

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws IOException {
        Set<Resource> schemaNames = new HashSet<>();
        return allValidationTestsInManifestFile()
                .parallelStream()
                .filter(tc -> {
                    boolean result;
                    if (! schemaNames.contains(tc.schemaFileName)) {
                        result = true;
                        schemaNames.add(tc.schemaFileName);
                    } else {
                        result = false;
                    }
                    return result;
                })
                .map(tc -> new Object[]{tc})
                .collect(Collectors.toList());
    }


}
