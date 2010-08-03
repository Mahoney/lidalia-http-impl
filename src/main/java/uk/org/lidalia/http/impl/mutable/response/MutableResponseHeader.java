package uk.org.lidalia.http.impl.mutable.response;

import static uk.org.lidalia.http.impl.response.Code.Code;

import java.util.regex.Matcher;

import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.exception.InvalidHeaderException;
import uk.org.lidalia.http.api.headerfield.HeaderField;
import uk.org.lidalia.http.api.mutable.MutableHeaderFields;
import uk.org.lidalia.http.api.response.Code;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.ResponseHeader;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseHeader;
import uk.org.lidalia.http.impl.response.AbstractResponseHeader;
import uk.org.lidalia.lang.Utils;

public class MutableResponseHeader extends AbstractResponseHeader implements uk.org.lidalia.http.api.mutable.response.MutableResponseHeader {

	private Code code;
	private Reason reason;
	private final MutableHeaderFields headers;

	public MutableResponseHeader() {
		this(null, null, null);
	}

	public MutableResponseHeader(Code code) {
		this(code, null, null);
	}

	public MutableResponseHeader(Code code, Reason reason) {
		this(code, reason, null);
	}

	public MutableResponseHeader(Code code, MutableHeaderFields mutableHeaderFields) {
		this(code, null, mutableHeaderFields);
	}

	public MutableResponseHeader(Code code, Reason reason, MutableHeaderFields headers) {
		this.code = code;
		Reason defaultReason = code == null ? null : code.getDefaultReason();
		this.reason = Utils.valueOrDefault(reason, defaultReason);
		this.headers = Utils.valueOrDefault(headers, new uk.org.lidalia.http.impl.mutable.MutableHeaderFields());
	}

	public MutableResponseHeader(String headerString) throws InvalidHeaderException {
		try {
			Matcher headerMatcher = parseHeader(headerString);
			code = Code(Integer.valueOf(headerMatcher.group(1)));
			reason = new Reason(headerMatcher.group(2));
			headers = new uk.org.lidalia.http.impl.mutable.MutableHeaderFields(headerMatcher.group(3));
		} catch (Exception e) {
			throw new InvalidHeaderException(headerString, e);
		}
	}

	public MutableResponseHeader(ResponseHeader responseHeader) {
		this(responseHeader.getCode(), responseHeader.getReason(), responseHeader.getHeaderFields().toMutable());
	}

	@Override
	public Code getCode() {
		return code;
	}

	@Override
	public void setCode(Code code) {
		this.code = code;
	}

	@Override
	public Reason getReason() {
		return reason;
	}

	@Override
	public void setReason(Reason reason) {
		this.reason = reason;
	}

	@Override
	public MutableHeaderFields getHeaderFields() {
		return headers;
	}

	@Override
	public void setHeaderField(HeaderField header) {
		headers.set(header);
	}

	@Override
	public void addHeaderField(HeaderField header) throws IllegalHeaderFieldValueException {
		headers.add(header);
	}

	@Override
	public boolean removeHeaderField(HeaderField header) {
		return headers.remove(header);
	}

	@Override
	public ImmutableResponseHeader toImmutable() {
		return new ImmutableResponseHeader(this);
	}

	@Override
	public MutableResponseHeader toMutable() {
		return this;
	}
}