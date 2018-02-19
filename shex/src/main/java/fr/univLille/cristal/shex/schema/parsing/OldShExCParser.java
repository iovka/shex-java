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
package fr.univLille.cristal.shex.schema.parsing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

import es.weso.shex.Schema;
import fr.univLille.cristal.shex.exception.CyclicReferencesException;
import fr.univLille.cristal.shex.exception.NotStratifiedException;
import fr.univLille.cristal.shex.exception.UndefinedReferenceException;
import fr.univLille.cristal.shex.schema.ShexSchema;
import scala.util.Try;

public class OldShExCParser extends Parser{

	public ShexSchema parseSchema(Path path) throws IOException, ParseException, UndefinedReferenceException,
	CyclicReferencesException, NotStratifiedException {
		byte[] encoded = Files.readAllBytes(path);
		String fileContent = new String(encoded, Charset.defaultCharset());
		Try<Schema> trySchema = Schema.fromString(fileContent, "SHEXC",null);
		Schema sh = trySchema.get();
		ConverterFromShaclex converter = new ConverterFromShaclex(sh);
		converter.convert();
		ShexSchema result = converter.convert();
		return result;
	}
}

