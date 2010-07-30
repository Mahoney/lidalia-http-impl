package uk.org.lidalia.http.impl.headerfield;

import org.apache.commons.lang.Validate;
import org.joda.time.Seconds;

import uk.org.lidalia.http.api.Text;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;

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
		return new Text(Integer.toString(wrappedValue.getSeconds()));
	}
}
