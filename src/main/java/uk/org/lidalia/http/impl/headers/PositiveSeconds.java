package uk.org.lidalia.http.impl.headers;

import java.nio.charset.CharacterCodingException;

import org.apache.commons.lang.Validate;
import org.joda.time.Seconds;

import uk.org.lidalia.http.api.Text;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.headerfield.AbstractHeaderFieldValue;

public final class PositiveSeconds extends AbstractHeaderFieldValue<Seconds> {

	public PositiveSeconds(Seconds seconds) throws IllegalHeaderFieldValueException {
		super(seconds);
		Validate.notNull(seconds, "seconds is null");
		Validate.isTrue(seconds.getSeconds() >= 0, "seconds is negative");
	}

	public Seconds getSeconds() {
		return wrappedValue;
	}

	@Override
	public Text toText() {
		try {
			return new Text(Integer.toString(wrappedValue.getSeconds()));
		} catch (CharacterCodingException e) {
			throw new IllegalStateException("It should be impossible for [" + wrappedValue.getSeconds() + "] to throw this exception", e);
		}
	}
}
