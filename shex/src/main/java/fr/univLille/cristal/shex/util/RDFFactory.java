package fr.univLille.cristal.shex.util;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.impl.AbstractValueFactory;

public class RDFFactory extends AbstractValueFactory {
	
	@Override
	public BNode createBNode(String nodeID) {
		if (nodeID.startsWith("genid-")) {
			String originalSt = nodeID.substring(39);
			if (originalSt.length()>0)
				return super.createBNode(originalSt);				
		}
		return super.createBNode(nodeID);
	}

	public static String MyBnodePrefix = "GENERATEDLABELFORBNODE#^§%*$";
	@Override
	public synchronized BNode createBNode() {
		BNode result = super.createBNode();
		//System.err.println(result.stringValue());
		return createBNode(MyBnodePrefix+result.stringValue());
}
	
	private static final RDFFactory sharedInstance = new RDFFactory();

	/**
	 * Provide a single shared instance of a SimpleValueFactory.
	 * 
	 * @return a singleton instance of SimpleValueFactory.
	 */
	public static RDFFactory getInstance() {
		return sharedInstance;
	}

	/**
	 * Hidden constructor to enforce singleton pattern.
	 */
	protected RDFFactory() {
}

}
