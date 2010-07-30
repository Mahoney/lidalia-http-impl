package uk.org.lidalia.http.impl.immutable.response;

import uk.org.lidalia.http.api.mutable.response.MutableResponseBody;

public class ImmutableResponseBody implements uk.org.lidalia.http.api.immutable.response.ImmutableResponseBody {
	
	private final String body;

	private ImmutableResponseBody(String body) {
		this.body = body;
	}

	public static ImmutableResponseBody parse(String bodyString) {
		return bodyString == null ? null : new ImmutableResponseBody(bodyString);
	}

	public String getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return body;
	}

	@Override
	public ImmutableResponseBody toImmutable() {
		return this;
	}

	@Override
	public MutableResponseBody toMutable() {
		// TODO Auto-generated method stub
		return null;
	}
}