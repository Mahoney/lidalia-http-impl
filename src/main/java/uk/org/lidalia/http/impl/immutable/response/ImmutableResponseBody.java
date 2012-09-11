package uk.org.lidalia.http.impl.immutable.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import uk.org.lidalia.http.api.response.ResponseBody;
import uk.org.lidalia.http.impl.mutable.response.MutableResponseBody;
import uk.org.lidalia.lang.Utils;

public class ImmutableResponseBody implements uk.org.lidalia.http.api.immutable.response.ImmutableResponseBody {

	private final byte[] bytes;

	public ImmutableResponseBody(byte[] bytes) {
		this.bytes = Utils.copyOf(bytes);
	}

	public ImmutableResponseBody(ResponseBody other) {
		this(other.getBytes());
	}

	@Override
	public String toString() {
		return bytes.length + " bytes in body";
	}

	@Override
	public ImmutableResponseBody toImmutable() {
		return this;
	}

	@Override
	public MutableResponseBody toMutable() {
		return new MutableResponseBody(this);
	}

	@Override
	public byte[] getBytes() {
		return Utils.copyOf(bytes);
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(bytes);
	}
}
