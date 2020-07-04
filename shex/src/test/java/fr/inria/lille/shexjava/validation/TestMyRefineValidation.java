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
package fr.inria.lille.shexjava.validation;

import fr.inria.lille.shexjava.schema.IRILabel;
import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.analysis.Configuration;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.shexTest.AbstractValidationTest;
import fr.inria.lille.shexjava.util.RDF;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.ParseErrorLogger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Iovka Boneva
 */
public class TestMyRefineValidation {
    private final static RDF4J rdfFactory = new RDF4J();

    @Test
    public void testEmptyGraphEmptyShape () throws Exception {
        Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"validation","S_empty.shex");
        ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);

        Model model = new LinkedHashModel();
        Graph graph = (new RDF4J()).asGraph(model);

        RDFTerm focus = RDF.buildIRI("n");

        MyRefineValidation algo = new MyRefineValidation(schema, graph);
        Label label = new IRILabel(RDF.buildIRI("S"));

        assertTrue(algo.performValidation(focus, label));
        assertTrue(algo.getTyping().isConformant(focus, label));
    }

    @Test
    public void testOneTripleOneTripleConstraint () throws Exception {
        Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"validation","S_p_dot.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);

		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		RDFTerm focus = RDF.buildIRI("n");

        graph.add(RDF.buildTriple("n", "p", "m"));

        MyRefineValidation algo = new MyRefineValidation(schema, graph);
        Label label = new IRILabel(RDF.buildIRI("S"));

        algo.validate();
        assertTrue(algo.getTyping().isConformant(focus, label));
    }

    @Test
    public void testTwoTriplesOneTripleConstraintSatisfied () throws Exception {
        Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"validation","S_p_dot.shex");
		ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);

		Model model = new LinkedHashModel();
		Graph graph = (new RDF4J()).asGraph(model);

		RDFTerm focus = RDF.buildIRI("n");

        graph.add(RDF.buildTriple("n", "p", "m"));
        graph.add(RDF.buildTriple("n", "q", "k"));

        MyRefineValidation algo = new MyRefineValidation(schema, graph);
        Label label = new IRILabel(RDF.buildIRI("S"));

        algo.validate();
        assertTrue(algo.getTyping().isConformant(focus, label));
    }

    @Test
    public void testTwoTriplesOneTripleConstraintNotSatisfied () throws Exception {
        Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"validation","S_p_dot.shex");
        ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);

        Model model = new LinkedHashModel();
        Graph graph = (new RDF4J()).asGraph(model);

        RDFTerm focus = RDF.buildIRI("n");

        graph.add(RDF.buildTriple("n", "p", "m"));
        graph.add(RDF.buildTriple("n", "p", "k"));

        MyRefineValidation algo = new MyRefineValidation(schema, graph);
        Label label = new IRILabel(RDF.buildIRI("S"));

        algo.validate();
        assertFalse(algo.getTyping().isConformant(focus, label));
    }

    @Test
    public void testTwoTriplesTwoTripleConstraintsSatisfied () throws Exception {
        Path schema_file = Paths.get(Configuration.shexTestPath.toString(),"validation","S_p_dot_and_q_dot.shex");
        ShexSchema schema = GenParser.parseSchema(rdfFactory,schema_file);

        Model model = new LinkedHashModel();
        Graph graph = (new RDF4J()).asGraph(model);

        RDFTerm focus = RDF.buildIRI("n");

        graph.add(RDF.buildTriple("n", "p", "m"));
        graph.add(RDF.buildTriple("n", "q", "k"));

        MyRefineValidation algo = new MyRefineValidation(schema, graph);
        Label label = new IRILabel(RDF.buildIRI("S"));

        algo.validate();
        assertTrue(algo.getTyping().isConformant(focus, label));
    }

}
