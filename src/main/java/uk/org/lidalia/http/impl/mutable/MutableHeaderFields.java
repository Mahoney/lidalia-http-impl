package uk.org.lidalia.http.impl.mutable;

import java.util.Iterator;

import uk.org.lidalia.http.api.HeaderFields;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.headerfield.HeaderField;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.impl.AbstractHeaderFields;

public class MutableHeaderFields extends AbstractHeaderFields implements uk.org.lidalia.http.api.mutable.MutableHeaderFields {

	public MutableHeaderFields() {
		super();
	}

	public MutableHeaderFields(String headersString) throws IllegalHeaderFieldNameException, IllegalHeaderFieldValueException {
		super(headersString);
	}

	public MutableHeaderFields(HeaderField... newHeaders) throws IllegalHeaderFieldValueException {
		super(newHeaders);
	}

	public MutableHeaderFields(HeaderFields headerFields) {
		super(headerFields);
	}

	@Override
	public Iterator<HeaderField> iterator() {
		return headers.values().iterator();
	}
	
	@Override
	public void set(HeaderField header) {
		HeaderFieldName name = header.getName();
		headers.put(name, header);
	}

	@Override
	public void add(HeaderField header) throws IllegalHeaderFieldValueException {
		HeaderFieldName name = header.getName();
		HeaderField existingHeader = headers.get(name);
		if (existingHeader == null) {
			headers.put(name, header);
		} else {
			headers.put(name, new HeaderField(name, name.parseValue(existingHeader.getValue() + ", " + header.getValue())));
		}
	}

	@Override
	public void clear() {
		headers.clear();
	}

	@Override
	public boolean remove(HeaderField header) {
		return headers.remove(header) != null;
	}

	@Override
	public ImmutableHeaderFields toImmutable() {
		return new uk.org.lidalia.http.impl.immutable.ImmutableHeaderFields(this);
	}

	@Override
	public MutableHeaderFields toMutable() {
		return this;
	}
}
