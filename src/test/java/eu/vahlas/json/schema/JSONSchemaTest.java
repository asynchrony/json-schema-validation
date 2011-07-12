/**
 * Copyright (C) 2010 Nicolas Vahlas <nico@vahlas.eu>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.vahlas.json.schema;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import eu.vahlas.json.schema.impl.JacksonSchemaProvider;

public class JSONSchemaTest {
	
	public JSONSchemaTest() {
		
	}
	
	@Test
	public void testCard() throws Exception {
		// Jackson parsing API: the ObjectMapper can be provided 
		// and configured differently depending on the application 
		ObjectMapper mapper = new ObjectMapper();
		
		// Allows to retrieve a JSONSchema object on various sources
		// supported by the ObjectMapper provided
		JSONSchemaProvider schemaProvider = new JacksonSchemaProvider(mapper);
		
		// Retrieves a JSON Schema object based on a URL
		JSONSchema schema = schemaProvider.getSchema( new URL("http://json-schema.org/card") );
		
		// Validates a JSON Instance object stored in a file against the schema
		InputStream instanceIS = getClass().getResourceAsStream("/card.json");
		List<String> errors = schema.validate(instanceIS);
		
		assertThat(errors.size(), is(4));
		// YES THIS IS A REF !!!!
		assertThat(errors.get(0), is("$.geo.longitude: is missing and it is not optional"));
		
		assertThat(errors.get(1), is("$.email: string found, object expected"));
		assertThat(errors.get(2), is("$.email.value: is missing and it is not optional"));
		assertThat(errors.get(3), is("$.email.type: is missing and it is not optional"));
		
	}
}
