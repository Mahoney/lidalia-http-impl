package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.http.api.Text;

public final class DefaultHeaderFieldValue extends AbstractHeaderFieldValue<Text, DefaultHeaderFieldValue> {

    public DefaultHeaderFieldValue(Text value) {
        super(value);
    }

    @Override
    public Text toText() {
        return getWrappedValue();
    }
}
