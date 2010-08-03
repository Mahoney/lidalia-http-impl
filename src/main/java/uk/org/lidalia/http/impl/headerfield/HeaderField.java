package uk.org.lidalia.http.impl.headerfield;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;
import uk.org.lidalia.lang.Immutable;

public final class HeaderField implements Immutable, uk.org.lidalia.http.api.headerfield.HeaderField {
	
	private final HeaderFieldName name;
	private final HeaderFieldValue value;
	
	public HeaderField(String headerString) throws IllegalHeaderFieldNameException, IllegalHeaderFieldValueException {
		String headerName = StringUtils.substringBefore(headerString, ":");
		String headerValue = StringUtils.substringAfter(headerString, ":").trim();
		this.name = HeaderFieldNameRegistry.get(headerName);
		this.value = name.parseValue(headerValue);
	}

	public HeaderField(HeaderFieldName name, HeaderFieldValue value) {
		Validate.notNull(name, "HeaderName cannot be null");
		Validate.notNull(value, "HeaderValue cannot be null");
		this.name = name;
		this.value = value;
	}
	
	public HeaderFieldName getName() {
		return name;
	}
	
	public HeaderFieldValue getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + ": " + value;
	}
	
	@Override
	public HeaderField toImmutable() {
		return this;
	}
}
