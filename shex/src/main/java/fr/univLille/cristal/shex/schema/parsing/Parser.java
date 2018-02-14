package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import com.github.jsonldjava.core.JsonLdError;

import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;

public interface Parser {
	
	public ShexSchema parseSchema(Path path) throws Exception;

}
