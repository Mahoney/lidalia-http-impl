package uk.org.lidalia.http.impl.response;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import uk.org.lidalia.http.api.exception.IllegalHeaderFieldNameException;
import uk.org.lidalia.http.api.headerfield.HeaderFieldName;
import uk.org.lidalia.http.api.headerfield.HeaderFieldNameRegistry;
import uk.org.lidalia.http.impl.headers.PositiveSecondsHeaderFieldName;

public class ResponseHeaderFieldNames {
	
	public static final HeaderFieldName		ACCEPT_RANGES	= getFromRegistry	("Accept-Ranges");
	public static final PositiveSecondsHeaderFieldName AGE	= (PositiveSecondsHeaderFieldName) getFromRegistry		("Age");
	public static final HeaderFieldName	ETAG				= getFromRegistry	("Etag");
	public static final HeaderFieldName	LOCATION			= getFromRegistry	("Location");
	public static final HeaderFieldName	PROXY_AUTHENTICATE	= getFromRegistry	("Proxy-Authenticate");
	public static final HeaderFieldName	RETRY_AFTER			= getFromRegistry	("Retry-After");
	public static final HeaderFieldName	SERVER				= getFromRegistry	("Server");
	public static final HeaderFieldName	VARY				= getFromRegistry	("Vary");
	public static final HeaderFieldName	WWW_AUTHENTICATE	= getFromRegistry	("WWW-Authenticate");
	public static final HeaderFieldName	SET_COOKIE			= getFromRegistry	("Set-Cookie");

	private static HeaderFieldName getFromRegistry(String safeHeaderName) {
		try {
			return HeaderFieldNameRegistry.get(safeHeaderName);
		} catch (IllegalHeaderFieldNameException e) {
			throw new IllegalStateException("It should not be possible to get an illegal header name exception from any of the predefined ResponseHeaderFieldNames", e);
		}
	}
	
	private static final Map<String, HeaderFieldName> values;
	static {
		Map<String, HeaderFieldName> tempValues = new HashMap<String, HeaderFieldName>();
		for (Field field : ResponseHeaderFieldNames.class.getFields()) {
			if (isPublicStaticFinal(field)) {
				try {
					tempValues.put(field.getName(), (HeaderFieldName) field.get(ResponseHeaderFieldNames.class));
				} catch (IllegalAccessException e) {
					throw new IllegalStateException("It should not be possible to get an IllegalAccessException on a public field", e);
				}
			}
		}
		values = Collections.unmodifiableMap(tempValues);
	}
	public static Set<HeaderFieldName> values() {
		return Collections.unmodifiableSet(new HashSet<HeaderFieldName>(values.values()));
	}

	private static boolean isPublicStaticFinal(Field field) {
		return Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers());
	}
}
