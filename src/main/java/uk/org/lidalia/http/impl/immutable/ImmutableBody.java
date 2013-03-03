package uk.org.lidalia.http.impl.immutable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import uk.org.lidalia.http.api.Body;
import uk.org.lidalia.http.api.mutable.MutableBody;

public class ImmutableBody implements uk.org.lidalia.http.api.immutable.ImmutableBody {

    private final byte[] bytes;

    public ImmutableBody(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    public ImmutableBody(Body other) {
        this(other.getBytes());
    }

    @Override
    public String toString() {
        return bytes.length + " bytes in body";
    }

    @Override
    public ImmutableBody toImmutable() {
        return this;
    }

    @Override
    public MutableBody toMutable() {
        return new uk.org.lidalia.http.impl.mutable.MutableBody(this);
    }

    @Override
    public byte[] getBytes() {
        return bytes.clone();
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }
}
