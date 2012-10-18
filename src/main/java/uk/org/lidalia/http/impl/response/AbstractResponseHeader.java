package uk.org.lidalia.http.impl.response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.joda.time.Seconds;

import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;
import uk.org.lidalia.http.api.response.ResponseHeader;
import uk.org.lidalia.http.impl.headerfield.PositiveSeconds;

public abstract class AbstractResponseHeader implements ResponseHeader {

    private static final String ANYTHING = "(?:.|\u0085|\r|\n)*";
    private static final String CODE = "(\\d\\d\\d)";
    private static final String REASON = "((?:.|\u0085)*)";
    private static final String CRLF = "\\r\\n";
    private static final String OPTIONAL_CRLF = "(?:" + CRLF + ")?";
    private static final String HEADERS = "(" + ANYTHING + ")";
    private static final String HEADER_REGEX = "^HTTP/1.1 " + CODE + " " + REASON + OPTIONAL_CRLF + HEADERS + "$";

    private static final Pattern STATUS_LINE_PATTERN = Pattern.compile(HEADER_REGEX);

    protected Matcher parseHeader(String headerString) {
        Matcher headerMatcher = STATUS_LINE_PATTERN.matcher(headerString);
        Validate.isTrue(headerMatcher.matches(), "[" + headerString + "] must match " + HEADER_REGEX);
        return headerMatcher;
    }

    @Override
    public String toString() {
        return "HTTP/1.1 " + getCode() + " " + getReason() + "\r\n" + getHeaderFields();
    }

    @Override
    public HeaderFieldValue getAcceptRanges() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seconds getAge() {
        PositiveSeconds positiveSeconds = (PositiveSeconds) getHeaderFields().get(HeaderFieldName.AGE);
        return positiveSeconds == null ? null : positiveSeconds.getSeconds();
    }

    @Override
    public HeaderFieldValue getEtag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HeaderFieldValue getLocation() {
        // TODO Auto-generated method stub
        return null;
    }
}
