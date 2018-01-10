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

public abstract class AbstractNaryTripleExpr extends NonRefTripleExpr {

	private final List<TripleExpr> subExpressions;
	
	public AbstractNaryTripleExpr (List<TripleExpr> subExpressions) {
		if (subExpressions.size() < 2)
			throw new IllegalArgumentException("At least two subexpressions required");
		this.subExpressions = new ArrayList<>(subExpressions);
	}
	
	public List<TripleExpr> getSubExpressions () {
		return Collections.unmodifiableList(this.subExpressions);
	}
	
}
