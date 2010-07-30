package uk.org.lidalia.http.impl.response;

import java.nio.charset.CharacterCodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.Validate;

import uk.org.lidalia.Utils;
import uk.org.lidalia.http.api.response.Reason;

public final class Code implements uk.org.lidalia.http.api.response.Code {
	
	public static final Code OK = register(200, "OK");
	
	private final int code;
	private final Reason defaultReason;

	private static final ConcurrentMap<Integer, Code> codes = new ConcurrentHashMap<Integer, Code>();
	
	private Code(int code) {
		this(code, null);
	}
	
	private Code(int code, Reason defaultReason) {
		Validate.isTrue(code >= 100);
		Validate.isTrue(code <= 999);
		this.code = code;
		this.defaultReason = defaultReason;
	}
	
	@Override
	public int getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return Integer.toString(code);
	}

	@Override
	public Reason getDefaultReason() {
		return defaultReason;
	}
	
	@Override
	public Code toImmutable() {
		return this;
	}

	public static Collection<Code> values() {
		return Collections.unmodifiableCollection(codes.values());
	}

	public static Code Code(Integer code) {
		Code result = codes.get(code);
		if (result == null) {
			result = Utils.putIfAbsentReturningObjectInMap(codes, code, new Code(code));
		}
		return result;
	}

	public static Code register(Integer code, String defaultReason) {
		return Utils.putIfAbsentReturningObjectInMap(codes, code, new Code(code, new Reason(defaultReason)));
	}

}
