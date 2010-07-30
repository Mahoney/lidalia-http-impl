package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.http.api.Text;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.exception.IllegalTokenException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;

class DefaultHeaderFieldName extends HeaderFieldName {

	DefaultHeaderFieldName(String headerName) throws IllegalTokenException {
		super(headerName);
	}

	@Override
	public HeaderFieldValue parseValue(String headerValue) throws IllegalHeaderFieldValueException {
		return new DefaultHeaderFieldValue(new Text(headerValue));
	}
}
