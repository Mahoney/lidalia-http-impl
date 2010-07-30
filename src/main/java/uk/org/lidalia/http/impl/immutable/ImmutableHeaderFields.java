package uk.org.lidalia.http.impl.immutable;

import java.util.Collections;
import java.util.Iterator;

import uk.org.lidalia.http.api.HeaderFields;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.headerfield.HeaderField;
import uk.org.lidalia.http.impl.AbstractHeaderFields;
import uk.org.lidalia.http.impl.mutable.MutableHeaderFields;

public final class ImmutableHeaderFields extends AbstractHeaderFields implements uk.org.lidalia.http.api.immutable.ImmutableHeaderFields {
	
	public ImmutableHeaderFields() {
		super();
	}
	
	public ImmutableHeaderFields(String headersString) throws IllegalHeaderFieldNameException, IllegalHeaderFieldValueException {
		super(headersString);
	}

	public ImmutableHeaderFields(HeaderField... newHeaders) throws IllegalHeaderFieldValueException {
		super(newHeaders);
	}
	
	public ImmutableHeaderFields(HeaderFields headerFields) {
		super(headerFields);
	}

	@Override
	public Iterator<HeaderField> iterator() {
		return Collections.unmodifiableCollection(headers.values()).iterator();
	}
	
	@Override
	public ImmutableHeaderFields toImmutable() {
		return this;
	}

	@Override
	public uk.org.lidalia.http.api.mutable.MutableHeaderFields toMutable() {
		return new MutableHeaderFields(this);
	}
}
