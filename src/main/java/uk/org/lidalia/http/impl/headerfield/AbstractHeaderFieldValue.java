package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.Immutable;
import uk.org.lidalia.WrappedValue;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;

public abstract class AbstractHeaderFieldValue<E> extends WrappedValue<E> implements HeaderFieldValue {

	public AbstractHeaderFieldValue(E wrappedValue) {
		super(wrappedValue);
	}
	
	@Override
	public final String toString() {
		return toText().toString();
	}

	@Override
	public Immutable toImmutable() {
		return this;
	}
}
