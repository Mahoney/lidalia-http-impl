package uk.org.lidalia.http.impl.headerfield;

import static uk.org.lidalia.http.api.Text.Text;
import static uk.org.lidalia.http.api.headerfield.HeaderFieldName.HeaderFieldName;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;

public class HeaderFieldParsers {
	
	private static final ConcurrentMap<HeaderFieldName, HeaderFieldParser> parsers = new ConcurrentHashMap<HeaderFieldName, HeaderFieldParser>();
	
	static {
		registerParser(HeaderFieldName("Age"), new PositiveSecondsHeaderFieldParser());
	}

	public static HeaderFieldValue parse(HeaderFieldName headerName, String value) {
		HeaderFieldParser headerFieldParser = parsers.get(headerName);
		if (headerFieldParser == null) {
			return new DefaultHeaderFieldValue(Text(value));
		} else {
			return headerFieldParser.parse(value);
		}
	}
	
	public static void registerParser(HeaderFieldName headerFieldName, HeaderFieldParser parser) {
		parsers.putIfAbsent(headerFieldName, parser);
	}
}
