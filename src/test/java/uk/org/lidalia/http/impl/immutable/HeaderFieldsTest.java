package uk.org.lidalia.http.impl.immutable;

import static org.junit.Assert.assertEquals;

import org.joda.time.Seconds;
import org.junit.Test;

import uk.org.lidalia.http.api.Text;
import uk.org.lidalia.http.impl.headerfield.DefaultHeaderFieldValue;
import uk.org.lidalia.http.impl.headerfield.HeaderFieldNameRegistry;
import uk.org.lidalia.http.impl.headerfield.PositiveSeconds;

public class HeaderFieldsTest {

	@Test
	public void stringConstructorIsParsedIntoHeaders() throws Exception {
		String input = "header1: value1\r\nheader2: value2\r\nAge: 100\r\n";
		ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
		assertEquals(3, headers.size());
		assertEquals(new DefaultHeaderFieldValue(new Text("value1")), headers.get(HeaderFieldNameRegistry.get("header1")));
		assertEquals(new DefaultHeaderFieldValue(new Text("value2")), headers.get(HeaderFieldNameRegistry.get("header2")));
		assertEquals(new PositiveSeconds(Seconds.seconds(100)), headers.get(HeaderFieldNameRegistry.get("Age")));
	}
	
	@Test
	public void stringConstructorWithLinearWhitespaceIsParsedIntoHeaders() throws Exception {
		String input = "header1: value\r\n   \t \t\t  and more value\r\nheader2: value2\r\n";
		ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
		assertEquals(2, headers.size());
		assertEquals(new DefaultHeaderFieldValue(new Text("value and more value")), headers.get(HeaderFieldNameRegistry.get("header1")));
		assertEquals(new DefaultHeaderFieldValue(new Text("value2")), headers.get(HeaderFieldNameRegistry.get("header2")));
	}
	
	@Test
	public void multipleHeadersWithTheSameNameAreConcatenated() throws Exception {
		String input = "header1: value\r\nheader1: value2\r\n";
		ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
		assertEquals(1, headers.size());
		assertEquals(new DefaultHeaderFieldValue(new Text("value, value2")), headers.get(HeaderFieldNameRegistry.get("header1")));
	}
}
