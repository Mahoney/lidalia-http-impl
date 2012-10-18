package uk.org.lidalia.http.impl.immutable;

import static org.junit.Assert.assertEquals;
import static uk.org.lidalia.http.api.Text.Text;
import static uk.org.lidalia.http.api.headerfield.HeaderFieldName.HeaderFieldName;

import org.joda.time.Seconds;
import org.junit.Test;

import uk.org.lidalia.http.impl.headerfield.DefaultHeaderFieldValue;
import uk.org.lidalia.http.impl.headerfield.PositiveSeconds;

public class HeaderFieldsTest {

    @Test
    public void stringConstructorIsParsedIntoHeaders() throws Exception {
        String input = "header1: value1\r\nheader2: value2\r\nAge: 100\r\n";
        ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
        assertEquals(3, headers.size());
        assertEquals(new DefaultHeaderFieldValue(Text("value1")), headers.get(HeaderFieldName("header1")));
        assertEquals(new DefaultHeaderFieldValue(Text("value2")), headers.get(HeaderFieldName("header2")));
        assertEquals(new PositiveSeconds(Seconds.seconds(100)), headers.get(HeaderFieldName("Age")));
    }

    @Test
    public void stringConstructorWithLinearWhitespaceIsParsedIntoHeaders() throws Exception {
        String input = "header1: value\r\n   \t \t\t  and more value\r\nheader2: value2\r\n";
        ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
        assertEquals(2, headers.size());
        assertEquals(new DefaultHeaderFieldValue(Text("value and more value")), headers.get(HeaderFieldName("header1")));
        assertEquals(new DefaultHeaderFieldValue(Text("value2")), headers.get(HeaderFieldName("header2")));
    }

    @Test
    public void multipleHeadersWithTheSameNameAreConcatenated() throws Exception {
        String input = "header1: value\r\nheader1: value2\r\n";
        ImmutableHeaderFields headers = new ImmutableHeaderFields(input);
        assertEquals(1, headers.size());
        assertEquals(new DefaultHeaderFieldValue(Text("value, value2")), headers.get(HeaderFieldName("header1")));
    }
}
