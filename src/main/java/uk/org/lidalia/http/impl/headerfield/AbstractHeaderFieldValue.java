package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;
import uk.org.lidalia.lang.Immutable;
import uk.org.lidalia.lang.WrappedValue;

public abstract class AbstractHeaderFieldValue<E, I extends AbstractHeaderFieldValue<E, I>> extends WrappedValue<E> implements HeaderFieldValue<I> {

    public AbstractHeaderFieldValue(E wrappedValue) {
        super(wrappedValue);
    }

    @Override
    public final String toString() {
        return toText().toString();
    }

    @Override
    public I toImmutable() {
        return (I) this;
    }
}
