package uk.org.lidalia.http.impl.mutable.response;

import uk.org.lidalia.http.api.response.ResponseBody;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseBody;


public class MutableResponseBody implements uk.org.lidalia.http.api.mutable.response.MutableResponseBody {

	public static MutableResponseBody parse(String bodyString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmutableResponseBody toImmutable() {
		return new ImmutableResponseBody(this);
	}

	@Override
	public MutableResponseBody toMutable() {
		return this;
	}
}
