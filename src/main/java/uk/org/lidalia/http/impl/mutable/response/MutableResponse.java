package uk.org.lidalia.http.impl.mutable.response;

import uk.org.lidalia.http.api.exception.InvalidResponseException;
import uk.org.lidalia.http.api.immutable.response.ImmutableResponse;
import uk.org.lidalia.http.api.mutable.MutableHeaderFields;
import uk.org.lidalia.http.api.mutable.response.MutableResponseBody;
import uk.org.lidalia.http.api.mutable.response.MutableResponseHeader;
import uk.org.lidalia.http.api.response.Code;
import uk.org.lidalia.http.api.response.Reason;
import uk.org.lidalia.http.api.response.Response;
import uk.org.lidalia.http.impl.response.AbstractResponse;
import uk.org.lidalia.http.impl.response.ResponseStringParser;

import static uk.org.lidalia.lang.RichOptional.fromNullable;

public class MutableResponse extends AbstractResponse implements uk.org.lidalia.http.api.mutable.response.MutableResponse {

    private final MutableResponseHeader header;
    private MutableResponseBody body;

    public MutableResponse(String responseString) throws InvalidResponseException {
        try {
            ResponseStringParser responseStringParser = new ResponseStringParser(responseString);
            this.header = new uk.org.lidalia.http.impl.mutable.response.MutableResponseHeader(responseStringParser.getHeaderString());
            this.body = new uk.org.lidalia.http.impl.mutable.response.MutableResponseBody(responseStringParser.getBodyString().getBytes());
        } catch (Exception e) {
            throw new InvalidResponseException(responseString, e);
        }
    }

    public MutableResponse() {
        this(null, null, null, null);
    }

    public MutableResponse(MutableResponseHeader header) {
        this(header, null);
    }

    public MutableResponse(MutableResponseHeader header, MutableResponseBody body) {
        this.header = fromNullable(header).or(new uk.org.lidalia.http.impl.mutable.response.MutableResponseHeader());
        this.body = body;
    }

    public MutableResponse(Code code) {
        this(code, null, null, null);
    }

    public MutableResponse(Code code, Reason reason) {
        this(code, reason, null, null);
    }

    public MutableResponse(Code code, MutableHeaderFields headers) {
        this(code, null, headers, null);
    }

    public MutableResponse(Code code, Reason reason, MutableHeaderFields headers) {
        this(code, reason, headers, null);
    }

    public MutableResponse(Code code, MutableResponseBody body) {
        this(code, null, null, body);
    }

    public MutableResponse(Code code, Reason reason, MutableResponseBody body) {
        this(code, reason, null, body);
    }

    public MutableResponse(Code code, MutableHeaderFields headers, MutableResponseBody body) {
        this(code, null, headers, body);
    }

    public MutableResponse(Code code, Reason reason, MutableHeaderFields headers, MutableResponseBody body) {
        this(new uk.org.lidalia.http.impl.mutable.response.MutableResponseHeader(code, reason, headers), body);
    }

    public MutableResponse(Response response) {
        this(response.getHeader().toMutable(), response.getBody().toMutable());
    }

    @Override
    public MutableResponseHeader getHeader() {
        return header;
    }

    @Override
    public MutableResponseBody getBody() {
        return body;
    }

    @Override
    public void setBody(MutableResponseBody body) {
        this.body = body;
    }

    @Override
    public MutableHeaderFields getHeaderFields() {
        return header.getHeaderFields();
    }

    @Override
    public ImmutableResponse toImmutable() {
        return new uk.org.lidalia.http.impl.immutable.response.ImmutableResponse(this);
    }

    @Override
    public MutableResponse toMutable() {
        return this;
    }
}
