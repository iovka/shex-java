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


package fr.univLille.cristal.shex.schema.abstrsynt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractNaryTripleExpression extends AbstractTripleExpression {

	private final List<TripleExpression> subExpressions;
	
	public AbstractNaryTripleExpression (List<TripleExpression> subExpressions) {
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<TripleExpression> getSubExpressions () {
		return Collections.unmodifiableList(this.subExpressions);
	}

	/*
	// FIXME: are the hashCode and equals methods useful ?
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subExpressions == null) ? 0 : subExpressions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractNaryTripleExpression other = (AbstractNaryTripleExpression) obj;
		if (subExpressions == null) {
			if (other.subExpressions != null)
				return false;
		} else if (!subExpressions.equals(other.subExpressions))
			return false;
		return true;
	}
	*/
	
	
}
