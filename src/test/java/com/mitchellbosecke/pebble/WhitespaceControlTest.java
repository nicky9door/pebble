/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Copyright (c) 2014 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class WhitespaceControlTest extends AbstractTest {

	@Test
	public void testLeadingWhitespaceTrimWithPrintDelimiter() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>    	{{- foo }}</li>");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("<li>bar</li>", writer.toString());
	}
	
	@Test
	public void testLeadingWhitespaceTrimWithoutOutsideText() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("{{- foo -}}");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("bar", writer.toString());
	}
	
	@Test
	public void testTrailingWhitespaceTrimWithPrintDelimiter() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>{{ foo -}}   	</li>");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("<li>bar</li>", writer.toString());
	}
	
	@Test
	public void testWhitespaceTrimInPresenceOfMultipleTags() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("{{ foo }} <li>   {{- foo -}}   	</li> {{ foo }}");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("bar <li>bar</li> bar", writer.toString());
	}
	
	@Test
	public void testWhitespaceTrimRemovesNewlines() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>\n{{- foo -}}\n</li>");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("<li>bar</li>", writer.toString());
	}
	
	@Test
	public void testWhitespaceTrimWithExecuteDelimiter() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>    	{%- if true %} success {% else %} fail {% endif -%}   	</li>");
		Writer writer = new StringWriter();

		Map<String, Object> context = new HashMap<>();
		context.put("foo", "bar");
		template.evaluate(writer, context);
		assertEquals("<li> success </li>", writer.toString());
	}
	
	@Test
	public void testLeadingWhitespaceTrimWithCommentDelimiter() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>    	{#- comment #}</li>");
		Writer writer = new StringWriter();

		template.evaluate(writer);
		assertEquals("<li></li>", writer.toString());
	}
	
	@Test
	public void testTrailingWhitespaceTrimWithCommentDelimiter() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>{# comment -#}   	</li>");
		Writer writer = new StringWriter();

		template.evaluate(writer);
		assertEquals("<li></li>", writer.toString());
	}
	
	
	@Test
	public void testLeadingWhitespaceTrimWithVerbatimTag() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li> {%- verbatim %}{{ bar }} {%- endverbatim %}</li>");
		Writer writer = new StringWriter();

		template.evaluate(writer);
		assertEquals("<li>{{ bar }}</li>", writer.toString());
	}
	
	
	@Test
	public void testTrailingWhitespaceTrimWithVerbatimTag() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble
				.getTemplate("<li>{% verbatim -%} {{ bar }}{% endverbatim -%} </li>");
		Writer writer = new StringWriter();

		template.evaluate(writer);
		assertEquals("<li>{{ bar }}</li>", writer.toString());
	}

}
