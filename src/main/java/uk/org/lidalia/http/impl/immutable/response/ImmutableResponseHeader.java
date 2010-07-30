package uk.org.lidalia.http.impl.immutable.response;

import java.util.regex.Matcher;

import org.apache.commons.lang.Validate;

import uk.org.lidalia.Utils;
import uk.org.lidalia.http.api.exception.InvalidHeaderException;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.api.mutable.response.MutableResponseHeader;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.ResponseCode;
import uk.org.lidalia.http.api.response.ResponseCodeRegistry;
import uk.org.lidalia.http.api.response.ResponseHeader;
import uk.org.lidalia.http.impl.response.AbstractResponseHeader;

public class ImmutableResponseHeader extends AbstractResponseHeader implements uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader {
	
	private final ResponseCode code;
	private final Reason reason;
	private final ImmutableHeaderFields headers;
	
	public ImmutableResponseHeader(ResponseCode code) {
		this(code, null, null);
	}
	
	public ImmutableResponseHeader(ResponseCode code, Reason reason) {
		this(code, reason, null);
	}
	
	public ImmutableResponseHeader(ResponseCode code, ImmutableHeaderFields headers) {
		this(code, null, headers);
	}

	public ImmutableResponseHeader(ResponseCode code, Reason reason, ImmutableHeaderFields headers) {
		Validate.notNull(code);
		this.code = code;
		this.reason = Utils.valueOrDefault(reason, code.getDefaultReason());
		this.headers = Utils.valueOrDefault(headers, new uk.org.lidalia.http.impl.immutable.ImmutableHeaderFields());
	}
	
	public ImmutableResponseHeader(String headerString) throws InvalidHeaderException {
		try {
			Matcher headerMatcher = parseHeader(headerString);
			code = ResponseCodeRegistry.get(Integer.valueOf(headerMatcher.group(1)));
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
	public ResponseCode getCode() {
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