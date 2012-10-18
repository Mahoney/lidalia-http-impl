package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.http.api.Text;

public final class DefaultHeaderFieldValue extends AbstractHeaderFieldValue<Text> {

	public DefaultHeaderFieldValue(Text value) {
		super(value);
	}

	@Override
	public Text toText() {
		return getWrappedValue();
	}
}
