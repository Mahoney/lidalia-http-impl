package uk.org.lidalia.http.impl.response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class ResponseStringParser {

	private final String headerString;
	private final String bodyString;
	
	public ResponseStringParser(String responseString) {
		Validate.isTrue(responseString.contains("\r\n\r\n"), "A Response must have a double CLRF after the header");
		this.headerString = StringUtils.substringBefore(responseString, "\r\n\r\n");
		this.bodyString = StringUtils.substringAfter(responseString, "\r\n\r\n");
	}
	
	public String getHeaderString() {
		return headerString;
	}
	
	public String getBodyString() {
		return bodyString;
	}
}
