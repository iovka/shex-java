package fr.univLille.cristal.shex.schema.concrsynt;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;

/**
 * 
 * @author Iovka Boneva
 * @author Antonin Durey
 *
 */
public interface SetOfNodes {

	public boolean contains (Value node);
	
	public static final SetOfNodes Blank = new SetOfNodes(){

		@Override
		public boolean contains(Value node) {
			return node instanceof BNode;
		}
		
		@Override
		public String toString() {
			return "BLANK";
		}		
	};
	
	public static final SetOfNodes AllIRI = new SetOfNodes() {

		@Override
		public boolean contains(Value node) {
			return node instanceof IRI;
		}
		
		@Override
		public String toString() {
			return "ALL_IRI";
		};
				
	};
	
	public static final SetOfNodes AllNodes = new SetOfNodes() {

		@Override
		public boolean contains(Value node) {
			return true;
		}
		
		@Override
		public String toString() {
			return "ALL_NODES";
		}
		
	};

	public static final SetOfNodes AllLiteral = new SetOfNodes() {
		
		@Override
		public boolean contains(Value node) {
			return node instanceof Literal;
		}
		
		public String toString() {
			return "ALL_LITERALS";
		}
	};

	public static SetOfNodes complement(SetOfNodes s) {
		return new SetOfNodes () {

			@Override
			public boolean contains(Value node) {
				return ! s.contains(node);
			}
			
			@Override
			public String toString() {
				return "COMPL(" + s.toString() + ")";
			}
		};
		
		
	}
		
	
	
}
