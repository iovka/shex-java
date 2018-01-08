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

package fr.univLille.cristal.shex.validation;

import org.eclipse.rdf4j.model.Resource;

import fr.univLille.cristal.shex.graph.RDFGraph;
import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.ShexSchema;

/** <p>Allows to validate a graph against a ShEx schema.</p>
 * 
 * <p>A {@link ValidationAlgorithm} must be initialized with an {@link RDFGraph} and a {@link ShexSchema}.
 * Then the {@link #validate(Resource, ShapeLabel)} method allows to construct a typing of the graph by the schema.
 * The {@link #getTyping()} method allows to retrieve the typing constructed by {@link #validate(Resource, ShapeLabel)}.</p>
 * 
 * <p>The method {@link #validate(Resource, ShapeLabel)} can take as parameter a node and a shape label, which both can be <code>null</code>.</p>
 * 
 * <p>If both are null, then {@link #validate(Resource, ShapeLabel)} constructs the complete typing of the graph by the shex schema.
 * If the focus node is not null, the constructed typing is guaranteed to be complete only for the given node.
 * If the shape label is not null, the constructed typing is guaranteed to be complete only for the given shape label.
 * In all cases, the constructed typing is a subset of the complete typing, thus is correct w.r.t. to the semantics of ShEx schemas.
 * In all cases, the constructed typing is locally valid for all node. That is, all node-label association can be shown correct within this typing.</p>
 * 
 * <p>For example, this is how to test whether a particular node satisfies a given type.</p>
 * 
 * <pre>
 * RDFGraph graph = new JenaRDFGraph(model); // with model of class org.apache.jena.rdf.model.Model
 * ShexSchema schema = new JsonParser().parse(jsonFilePath); 
 * ValidationAlgorithm val = new ValidationAlgorithmImplementation(graph, schema);
 * Resource focusNode = ... // the node that we want to validate 
 * ShapeLabel label = ... // the type to be checked on that node 
 * validate(focusNode, label)
 * // there, val.getTyping().contains(focusNode, label) if and only if focusNode satisfies the shape named label
 * </pre>
 * 
 * <p>For constructing the complete typing of a given <code>org.apache.jena.rdf.model.Model model</code>, the usage is as follows</p>
 
 * <pre>
 * ValidationAlgorithm val = new ValidationAlgorithmImplementation(graph, schema);
 * validate(null, null)
 * // here, val.getTyping() is the complete typing for graph by schema
 * </pre> 
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface ValidationAlgorithm {

	/** Constructs a typing that allows to validate a focus node against a type.
	 * The constructed typing can be retrieved using {@link #getTyping()}
	 * 
	 * @param focusNode The focus node for which the typing is to be complete. If null, then the typing will be complete for all nodes. The node might not belong to the graph, in which case it does not have a neighborhood.
	 * @param label The label for which the typing is to be complete. If null, then the typing will be complete for all labels.
	 */
	public void validate(Resource focusNode, ShapeExprLabel label);
	
	/** Retrieves the typing constructed by a previous call of {@link #validate(Resource, ShapeLabel)}.
	 * 
	 * @return
	 */
	public Typing getTyping();
}
