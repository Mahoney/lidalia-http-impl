package uk.org.lidalia.http.impl.mutable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import uk.org.lidalia.http.api.Body;
import uk.org.lidalia.http.impl.immutable.ImmutableBody;

public class MutableBody implements uk.org.lidalia.http.api.mutable.MutableBody {

    private final byte[] bytes;

    public MutableBody(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    public MutableBody(Body other) {
        this(other.getBytes());
    }

    @Override
    public uk.org.lidalia.http.api.immutable.ImmutableBody toImmutable() {
        return new ImmutableBody(this);
    }

    @Override
    public MutableBody toMutable() {
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
