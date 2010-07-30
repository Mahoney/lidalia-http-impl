package uk.org.lidalia.http.impl.immutable.response;

import org.apache.commons.lang.Validate;

import uk.org.lidalia.http.api.exception.InvalidResponseException;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.api.response.Code;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.Response;
import uk.org.lidalia.http.impl.mutable.response.MutableResponse;
import uk.org.lidalia.http.impl.response.AbstractResponse;
import uk.org.lidalia.http.impl.response.ResponseStringParser;

public class ImmutableResponse extends AbstractResponse implements uk.org.lidalia.http.api.immutable.response.ImmutableResponse {

	private final uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader header;
	private final uk.org.lidalia.http.api.immutable.response.ImmutableResponseBody body;
	
	public ImmutableResponse(String responseString) throws InvalidResponseException {
		try {
			ResponseStringParser responseStringParser = new ResponseStringParser(responseString);
			this.header = new ImmutableResponseHeader(responseStringParser.getHeaderString());
			this.body = ImmutableResponseBody.parse(responseStringParser.getBodyString());
		} catch (Exception e) {
			throw new InvalidResponseException(responseString, e);
		}
	}
	
	public ImmutableResponse(ImmutableResponseHeader header) {
		this(header, null);
	}
	
	public ImmutableResponse(uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader header, uk.org.lidalia.http.api.immutable.response.ImmutableResponseBody body) {
		Validate.notNull(header, "header is null");
		this.header = header;
		this.body = body;
	}
	
	public ImmutableResponse(Code code) {
		this(code, null, null, null);
	}
	
	public ImmutableResponse(Code code, Reason reason) {
		this(code, reason, null, null);
	}
	
	public ImmutableResponse(Code code, ImmutableHeaderFields headers) {
		this(code, null, headers, null);
	}

	public ImmutableResponse(Code code, Reason reason, ImmutableHeaderFields headers) {
		this(code, reason, headers, null);
	}
	
	public ImmutableResponse(Code code, ImmutableResponseBody body) {
		this(code, null, null, body);
	}
	
	public ImmutableResponse(Code code, Reason reason, ImmutableResponseBody body) {
		this(code, reason, null, body);
	}
	
	public ImmutableResponse(Code code, ImmutableHeaderFields headers, ImmutableResponseBody body) {
		this(code, null, headers, body);
	}

	public ImmutableResponse(Code code, Reason reason, ImmutableHeaderFields headers, ImmutableResponseBody body) {
		this(new ImmutableResponseHeader(code, reason, headers), body);
	}

	public ImmutableResponse(Response response) {
		this(response.getHeader().toImmutable(), response.getBody().toImmutable());
	}

	@Override
	public uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader getHeader() {
		return header;
	}
	
	@Override
	public uk.org.lidalia.http.api.immutable.response.ImmutableResponseBody getBody() {
		return body;
	}
	
	@Override
	public ImmutableHeaderFields getHeaderFields() {
		return header.getHeaderFields();
	}

	@Override
	public ImmutableResponse toImmutable() {
		return this;
	}

	@Override
	public MutableResponse toMutable() {
		return new MutableResponse(this);
	}
	
}