package uk.org.lidalia.http.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import uk.org.lidalia.http.api.HeaderFields;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.exception.IllegalHeaderFieldValueException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;
import uk.org.lidalia.http.api.headerfield.HeaderField;
import uk.org.lidalia.http.api.immutable.ImmutableHeaderFields;
import uk.org.lidalia.http.impl.headerfield.HeaderFieldParsers;

public abstract class AbstractHeaderFields implements HeaderFields {

    protected final Map<HeaderFieldName, HeaderField> headers = new LinkedHashMap<HeaderFieldName, HeaderField>();

    private static HeaderField[] parseHeaders(String headersString) throws IllegalHeaderFieldNameException, IllegalHeaderFieldValueException {
        String headersWithoutLinearWhitespace = headersString.replaceAll("\r\n( |\t)+", " ");
        String[] headerStrings = StringUtils.split(headersWithoutLinearWhitespace, "\r\n");
        List<HeaderField> headers = new ArrayList<HeaderField>();
        for (String headerString : headerStrings) {
            headers.add(new uk.org.lidalia.http.impl.headerfield.HeaderField(headerString));
        }
        return headers.toArray(new HeaderField[] {});
    }
    protected AbstractHeaderFields() {
        super();
    }

    protected AbstractHeaderFields(String headersString) throws IllegalHeaderFieldNameException, IllegalHeaderFieldValueException {
        this(parseHeaders(headersString));
    }

    protected AbstractHeaderFields(HeaderField... newHeaders) throws IllegalHeaderFieldValueException {
        for (HeaderField header : newHeaders) {
            HeaderFieldName name = header.getName();
            HeaderField existingHeader = headers.get(name);
            if (existingHeader == null) {
                headers.put(name, header);
            } else {
                headers.put(name, new uk.org.lidalia.http.impl.headerfield.HeaderField(name, HeaderFieldParsers.parse(name, existingHeader.getValue() + ", " + header.getValue())));
            }
        }
    }

    protected AbstractHeaderFields(HeaderFields headerFields) {
        for (HeaderField header : headerFields) {
            headers.put(header.getName(), header);
        }
    }

    @Override
    public HeaderFieldValue get(HeaderFieldName headerFieldName) {
        Validate.notNull(headerFieldName, "headerFieldName is null");
        HeaderField header = headers.get(headerFieldName);
        return header != null ? header.getValue() : null;
    }

    public boolean contains(HeaderFieldName name) {
        return headers.containsKey(name);
    }

    @Override
    public String toString() {
        return StringUtils.join(headers.values(), "\r\n");
    }

    @Override
    public boolean isEmpty() {
        return headers.isEmpty();
    }

    @Override
    public int size() {
        return headers.size();
    }
}
