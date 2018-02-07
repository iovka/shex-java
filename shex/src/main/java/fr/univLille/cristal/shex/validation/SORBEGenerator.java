package fr.univLille.cristal.shex.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univLille.cristal.shex.schema.ShapeExprLabel;
import fr.univLille.cristal.shex.schema.TripleExprLabel;
import fr.univLille.cristal.shex.schema.abstrsynt.EachOf;
import fr.univLille.cristal.shex.schema.abstrsynt.EmptyTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.OneOf;
import fr.univLille.cristal.shex.schema.abstrsynt.RepeatedTripleExpression;
import fr.univLille.cristal.shex.schema.abstrsynt.Shape;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleConstraint;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExpr;
import fr.univLille.cristal.shex.schema.abstrsynt.TripleExprRef;
import fr.univLille.cristal.shex.util.Interval;
import fr.univLille.cristal.shex.util.RDFFactory;

public class SORBEGenerator {
	private final static RDFFactory RDF_FACTORY = RDFFactory.getInstance();
	private static int tripleLabelNb = 0;
	private static String TRIPLE_LABEL_PREFIX = "LABEL_FOR_SORBE_GENERATED";
	
	private Map<ShapeExprLabel,TripleExpr> sorbeMap;
	
	public SORBEGenerator() {
		this.sorbeMap=new HashMap<ShapeExprLabel,TripleExpr>();
	}
	
	// Get ride of the tripleExprRef by copying 
	public TripleExpr getSORBETripleExpr(Shape shape) {
		if (this.sorbeMap.containsKey(shape.getId()))
			return this.sorbeMap.get(shape.getId());
		TripleExpr result = generateTripleExpr(shape.getTripleExpression());
		this.sorbeMap.put(shape.getId(), result);
		return result;
	}
	
	
	private TripleExpr generateTripleExpr(TripleExpr expr) {
		if (expr instanceof TripleExprRef) 
			return generateTripleExpr(((TripleExprRef) expr).getTripleExp());
		if (expr instanceof EachOf)
			return generateEachOf((EachOf) expr);
		if (expr instanceof OneOf)
			return generateOneOf((OneOf) expr);
		if (expr instanceof TripleConstraint)
			return generateTripleConstraint((TripleConstraint) expr);
		if (expr instanceof RepeatedTripleExpression)
			return generateRepeatedTripleExpression((RepeatedTripleExpression) expr);
		if (expr instanceof EmptyTripleExpression)
			return generateEmptyTripleExpression((EmptyTripleExpression) expr);
		System.err.println(expr.getClass());
		return null;
	}
	
	private TripleExpr generateEmptyTripleExpression(EmptyTripleExpression expr ) {
		TripleExpr result = new EmptyTripleExpression();
		setTripleLabel(result);
		return result;
	}
	
	
	private TripleExpr generateEachOf(EachOf expr) {
		List<TripleExpr> newSubExprs = new ArrayList<TripleExpr>();
		for (TripleExpr subExpr:expr.getSubExpressions()) {
			newSubExprs.add(generateTripleExpr(subExpr));
		}
		TripleExpr result = new EachOf(newSubExprs);
		setTripleLabel(result);
		return result;
	}
	

	private TripleExpr generateOneOf(OneOf expr) {
		List<TripleExpr> newSubExprs = new ArrayList<TripleExpr>();
		for (TripleExpr subExpr:expr.getSubExpressions()) {
			newSubExprs.add(generateTripleExpr(subExpr));
		}
		TripleExpr result = new OneOf(newSubExprs);
		setTripleLabel(result);
		return result;
	}
	
	
	private TripleExpr generateTripleConstraint(TripleConstraint expr) {
		TripleExpr result = expr.clone();
		setTripleLabel(result);
		return result;
	}
	

	private TripleExpr generateRepeatedTripleExpression(RepeatedTripleExpression expr) {
		if (expr.getCardinality().equals(Interval.PLUS) 
				|| expr.getCardinality().equals(Interval.STAR)
				|| expr.getCardinality().equals(Interval.ONE)){
			TripleExpr result = new RepeatedTripleExpression(generateTripleExpr(expr.getSubExpression()),expr.getCardinality());
			setTripleLabel(result);
			return result;
		}else {
			Interval card = expr.getCardinality();
			int nbClones = 0, nbOptClones = 0;
			List<TripleExpr> clones = new ArrayList<TripleExpr>();

			if (card.max == Interval.UNBOUND) {
				nbClones = card.min -1;
				TripleExpr tmp = new RepeatedTripleExpression(generateTripleExpr(expr.getSubExpression()), Interval.PLUS);
				setTripleLabel(tmp);
				clones.add(tmp);
			}else {
				nbClones = card.min;
				nbOptClones = card.max - card.min;
			}

			for (int i=0; i<nbClones;i++) {
				clones.add(generateTripleExpr(expr.getSubExpression()));	
			}
			for (int i=0; i<nbOptClones;i++) {
				TripleExpr tmp = new RepeatedTripleExpression(generateTripleExpr(expr.getSubExpression()), Interval.OPT);
				setTripleLabel(tmp);
				clones.add(tmp);
			}
			TripleExpr result = new EachOf(clones);
			setTripleLabel(result);
			return result;
		}
	}
	

	private void setTripleLabel(TripleExpr triple) {
		triple.setId(new TripleExprLabel(RDF_FACTORY.createBNode(TRIPLE_LABEL_PREFIX+"_"+tripleLabelNb),true));
		tripleLabelNb++;
	}

}
