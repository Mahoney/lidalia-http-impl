package uk.org.lidalia.http.impl.mutable.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import uk.org.lidalia.http.api.response.ResponseBody;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseBody;

public class MutableResponseBody implements uk.org.lidalia.http.api.mutable.response.MutableResponseBody {

    private final byte[] bytes;

    public MutableResponseBody(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    public MutableResponseBody(ResponseBody other) {
        this(other.getBytes());
    }

    @Override
    public ImmutableResponseBody toImmutable() {
        return new ImmutableResponseBody(this);
    }

    @Override
    public MutableResponseBody toMutable() {
        return this;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }
}
