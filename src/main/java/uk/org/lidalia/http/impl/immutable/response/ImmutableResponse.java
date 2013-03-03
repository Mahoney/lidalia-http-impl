package uk.org.lidalia.http.impl.immutable.response;

import org.apache.commons.lang3.Validate;

import uk.org.lidalia.http.api.exception.InvalidResponseException;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.api.response.Code;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.Response;
import uk.org.lidalia.http.impl.headerfield.HeaderField;
import uk.org.lidalia.http.impl.immutable.ImmutableBody;
import uk.org.lidalia.http.impl.mutable.response.MutableResponse;
import uk.org.lidalia.http.impl.response.AbstractResponse;
import uk.org.lidalia.http.impl.response.ResponseStringParser;

public class ImmutableResponse extends AbstractResponse implements uk.org.lidalia.http.api.immutable.response.ImmutableResponse {

    private final uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader header;
    private final uk.org.lidalia.http.api.immutable.ImmutableBody body;

    public ImmutableResponse(String responseString) throws InvalidResponseException {
        try {
            ResponseStringParser responseStringParser = new ResponseStringParser(responseString);
            this.header = new ImmutableResponseHeader(responseStringParser.getHeaderString());
            this.body = new ImmutableBody(responseStringParser.getBodyString().getBytes());
        } catch (Exception e) {
            throw new InvalidResponseException(responseString, e);
        }
    }

    public ImmutableResponse(ImmutableResponseHeader header) {
        this(header, null);
    }

    public ImmutableResponse(uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader header, uk.org.lidalia.http.api.immutable.ImmutableBody body) {
        Validate.notNull(header, "header is null");
        this.header = header;
        this.body = body;
    }

    public ImmutableResponse(Code code) {
        this(code, null, (ImmutableHeaderFields) null, null);
    }

    public ImmutableResponse(Code code, Reason reason) {
        this(code, reason, (ImmutableHeaderFields) null, null);
    }

    public ImmutableResponse(Code code, ImmutableHeaderFields headers) {
        this(code, null, headers, null);
    }

    public ImmutableResponse(Code code, Reason reason, ImmutableHeaderFields headers) {
        this(code, reason, headers, null);
    }

    public ImmutableResponse(Code code, ImmutableBody body) {
        this(code, null, null, body);
    }

    public ImmutableResponse(Code code, Reason reason, ImmutableBody body) {
        this(code, reason, null, body);
    }

    public ImmutableResponse(Code code, ImmutableHeaderFields headers, ImmutableBody body) {
        this(code, null, headers, body);
    }

    public ImmutableResponse(Code code, Reason reason, ImmutableHeaderFields headers, ImmutableBody body) {
        this(new ImmutableResponseHeader(code, reason, headers), body);
    }

    public ImmutableResponse(Response response) {
        this(response.getHeader().toImmutable(), response.getBody().toImmutable());
    }

    public ImmutableResponse(Code code, HeaderField... headers) {
        this(code, null, null, headers);
    }

    public ImmutableResponse(Code code, Reason reason, HeaderField... headers) {
        this(code, reason, null, headers);
    }

    public ImmutableResponse(Code code, ImmutableBody body, HeaderField... headers) {
        this(code, null, body, headers);
    }

    public ImmutableResponse(Code code, Reason reason, ImmutableBody body, HeaderField... headers) {
        this(new ImmutableResponseHeader(code, reason, headers), body);
    }

    @Override
    public uk.org.lidalia.http.api.immutable.response.ImmutableResponseHeader getHeader() {
        return header;
    }

    @Override
    public uk.org.lidalia.http.api.immutable.ImmutableBody getBody() {
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
