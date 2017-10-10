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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class ASElementAttributes {
	
	private Map<Object, Object> attributes = new HashMap<>();
	
	public Object getAttribute (Object key) {
		return attributes.get(key);
	}
	
	public void setAttribute (Object key, Object value) {
		if (attributes.containsKey(key))
			throw new IllegalStateException("Property already set :"  + key);
		attributes.put(key, value);
	}
	
	public Object hasAttribute (Object key) {
		return attributes.containsKey(key);
	}
	
	public void cleanAttribute (Object key) {
		attributes.remove(key);
	}
	
}
