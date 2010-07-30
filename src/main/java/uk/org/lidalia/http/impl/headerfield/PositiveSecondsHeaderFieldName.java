package uk.org.lidalia.http.impl.headerfield;

import org.joda.time.Seconds;

import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.exception.IllegalTokenException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;

public class PositiveSecondsHeaderFieldName extends HeaderFieldName {

	public PositiveSecondsHeaderFieldName(String headerName) throws IllegalTokenException {
		super(headerName);
	}

	@Override
	public PositiveSeconds parseValue(String headerValue) throws IllegalHeaderFieldValueException {
		try {
			return new PositiveSeconds(Seconds.seconds(Integer.parseInt(headerValue)));
		} catch (Exception e) {
			throw new IllegalHeaderFieldValueException(headerValue, this, e);
		}
	}
}
