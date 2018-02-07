package fr.univLille.cristal.shex.validation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.util.Pair;

public class RecursiveTyping implements Typing {
	private Set<Pair<Value, ShapeExprLabel>> typing;
	private Set<Pair<Value, ShapeExprLabel>> lastSetOfHyp;

	public RecursiveTyping() {
		typing = new HashSet<Pair<Value, ShapeExprLabel>>();
		lastSetOfHyp = new HashSet<Pair<Value, ShapeExprLabel>>();
	}

	@Override
	public boolean contains(Value node, ShapeExprLabel label) {
		return typing.contains(new Pair<Value, ShapeExprLabel>(node,label));
	}

	@Override
	public Set<Pair<Value, ShapeExprLabel>> asSet() {
		return typing;
	}
	
	public void addHypothesis(Value node, ShapeExprLabel label) {
		typing.add(new Pair<Value, ShapeExprLabel>(node,label));
		lastSetOfHyp.add(new Pair<Value, ShapeExprLabel>(node,label));
	}
	
	public void removeHypothesis(Value node, ShapeExprLabel label) {
		typing.remove(new Pair<Value, ShapeExprLabel>(node,label));
	}
	
	public void keepLastSessionOfHypothesis() {
		lastSetOfHyp = new HashSet<Pair<Value, ShapeExprLabel>>();
	}
	
	public void removeLastSessionOfHypothesis() {
		for (Pair<Value, ShapeExprLabel> hyp:lastSetOfHyp)
			typing.remove(hyp);		
		lastSetOfHyp = new HashSet<Pair<Value, ShapeExprLabel>>();
	}

}
