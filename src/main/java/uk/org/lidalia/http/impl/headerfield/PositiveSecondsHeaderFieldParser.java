package uk.org.lidalia.http.impl.headerfield;

import org.joda.time.Seconds;

import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;

public class PositiveSecondsHeaderFieldParser implements HeaderFieldParser {

    @Override
    public HeaderFieldValue parse(String headerFieldValueString) {
        Seconds seconds = Seconds.seconds(Integer.valueOf(headerFieldValueString));
        return new PositiveSeconds(seconds);
    }

}
