package uk.org.lidalia.http.impl.headerfield;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.Validate;

import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.exception.IllegalTokenException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;

public class HeaderFieldNameRegistry {

	private static final ConcurrentMap<String, HeaderFieldName> headerFieldNames = new ConcurrentHashMap<String, HeaderFieldName>();
	
	static {
		registerHeaderFieldName(new PositiveSecondsHeaderFieldName("Age"));
	}

	public static HeaderFieldName get(String headerName) throws IllegalHeaderFieldNameException {
		Validate.notNull(headerName, "headerName is null");
		HeaderFieldName headerFieldName = headerFieldNames.get(headerName.toLowerCase());
		if (headerFieldName == null) {
			try {
				headerFieldName = new DefaultHeaderFieldName(headerName);
			} catch (IllegalTokenException e) {
				throw new IllegalHeaderFieldNameException(headerName, e);
			}
			HeaderFieldName actual = headerFieldNames.putIfAbsent(headerName.toLowerCase(), headerFieldName);
			if (actual != null) {
				headerFieldName = actual;
			}
		}
		return headerFieldName;
	}
	
	public static void registerHeaderFieldName(HeaderFieldName newHeaderFieldName) {
		Validate.notNull(newHeaderFieldName, "newHeaderFieldName is null");
		headerFieldNames.putIfAbsent(newHeaderFieldName.toString().toLowerCase(), newHeaderFieldName);
	}
}
