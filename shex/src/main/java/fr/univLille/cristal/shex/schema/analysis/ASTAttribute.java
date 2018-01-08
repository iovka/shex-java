package fr.univLille.cristal.shex.schema.analysis;

import java.util.function.Function;

import fr.univLille.cristal.shex.schema.abstrsynt.ASTElement;

public abstract class ASTAttribute<F extends ASTElement, T> implements Function<F, T> {

	protected Object context;
	
	public void set (F on) {
		on.getDynamicAttributes().put(this, this.apply(on));
	}
	
	@SuppressWarnings("unchecked")
	public T get (F from) {
		return (T) from.getDynamicAttributes().get(this);
	}

	public void setContext (Object context) {
		this.context = context;
	}
	
	public Object getContext () {
		return this.getContext();
	}
	
	public void clearContext (Object context) {
		this.context = null;
	}
	
	/*
	
	
	ALL_RULES_ON_SCHEMA (ShexSchema.class, Map.class, null),
	HAS_INVERSE_PROPERTIES_ON_SHAPE(Shape.class, Boolean.class, null),
	TRIPLE_EXPRESSION_FOR_VALIDATION_ON_SHAPE(Shape.class, TripleExpr.class, null),
	MENTIONED_PROPERTIES_ON_SHAPE(Shape.class, Set.class, null),
	LIST_TRIPLE_CONSTRAINTS_ON_TRIPLE_EXPR(TripleExpr.class, List.class, null);
	
	
	Class<? extends ASTElement> fromType;
	Class<?> toType;
	Instrument<?,?> instrument;
	
	ASTAttribute(Class<? extends ASTElement> fromType, 
						 Class<?> toType,
						 Instrument<? ,?> instrument) {
		this.fromType = fromType;
		this.toType = toType;
		this.instrument = instrument;
	}
	
	public void set(ASTElement on) {
		if (! on.getClass().equals(fromType))
			throw new IllegalArgumentException("Attribute " + this.toString() + " not applicable on " + on.getClass().toString());
		on.set(this, this.instrument.apply(on));
	}
	*/
	
	
}
