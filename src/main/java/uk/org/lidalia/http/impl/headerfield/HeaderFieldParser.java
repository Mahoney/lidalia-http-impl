package uk.org.lidalia.http.impl.headerfield;

import uk.org.lidalia.http.api.headerfield.HeaderFieldValue;

public interface HeaderFieldParser {

	HeaderFieldValue parse(String headerFieldValueString);
	
}
