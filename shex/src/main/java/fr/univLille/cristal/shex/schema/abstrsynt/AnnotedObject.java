package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.List;

public interface AnnotedObject {
	public List<Annotation> getAnnotations();
	public void setAnnotations (List<Annotation> annotations);
}
