package uk.org.lidalia.http.impl.immutable.response;

import static uk.org.lidalia.http.api.response.Code.Code;

import java.util.regex.Matcher;

import org.apache.commons.lang.Validate;

import uk.org.lidalia.http.api.exception.InvalidHeaderException;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.api.mutable.response.MutableResponseHeader;
import uk.org.lidalia.http.api.response.Code;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.ResponseHeader;
import uk.org.lidalia.http.impl.headerfield.HeaderField;
import uk.org.lidalia.http.impl.response.AbstractResponseHeader;
import uk.org.lidalia.lang.Utils;

public class ImmutableResponseHeader extends AbstractResponseHeader implements uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader {
	
	private final Code code;
	private final Reason reason;
	private final ImmutableHeaderFields headers;
	
	public ImmutableResponseHeader(Code code) {
		this(code, null, (ImmutableHeaderFields) null);
	}
	
	public ImmutableResponseHeader(Code code, Reason reason) {
		this(code, reason, (ImmutableHeaderFields) null);
	}
	
	public ImmutableResponseHeader(Code code, ImmutableHeaderFields headers) {
		this(code, null, headers);
	}

	public ImmutableResponseHeader(Code code, Reason reason, ImmutableHeaderFields headers) {
		Validate.notNull(code);
		this.code = code;
		this.reason = Utils.valueOrDefault(reason, code.getDefaultReason());
		this.headers = Utils.valueOrDefault(headers, new uk.org.lidalia.http.impl.immutable.ImmutableHeaderFields());
	}
	
	public ImmutableResponseHeader(Code code, HeaderField... headers) {
		this(code, null, headers);
	}

	public ImmutableResponseHeader(Code code, Reason reason, HeaderField... headers) {
		Validate.notNull(code);
		this.code = code;
		this.reason = Utils.valueOrDefault(reason, code.getDefaultReason());
		this.headers = new uk.org.lidalia.http.impl.immutable.ImmutableHeaderFields(headers);
	}
	
	public ImmutableResponseHeader(String headerString) throws InvalidHeaderException {
		try {
			Matcher headerMatcher = parseHeader(headerString);
			code = Code(Integer.valueOf(headerMatcher.group(1)));
			reason = new Reason(headerMatcher.group(2));
			headers = new uk.org.lidalia.http.impl.immutable.ImmutableHeaderFields(headerMatcher.group(3));
		} catch (Exception e) {
			throw new InvalidHeaderException(headerString, e);
		}
	}
	
	public ImmutableResponseHeader(ResponseHeader responseHeader) {
		this(responseHeader.getCode(), responseHeader.getReason(), responseHeader.getHeaderFields().toImmutable());
	}

	@Override
	public Code getCode() {
		return code;
	}

	@Override
	public Reason getReason() {
		return reason;
	}
	
	@Override
	public ImmutableHeaderFields getHeaderFields() {
		return headers;
	}

	public MutableResponseHeader toMutable() {
		return new uk.org.lidalia.http.impl.mutable.response.MutableResponseHeader(this);
	}

	@Override
	public ImmutableResponseHeader toImmutable() {
		return this;
	}
}