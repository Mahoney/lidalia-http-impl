package uk.org.lidalia.http.impl.immutable;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createMockAndExpectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static uk.org.lidalia.testutils.Assert.shouldThrow;

import java.util.concurrent.Callable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.org.lidalia.http.api.exception.InvalidResponseException;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponse;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseBody;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseHeader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImmutableResponse.class, ImmutableResponseBody.class})
public class ResponseTest {

	@Test
	public void constructedWithNullHeaderThrowsIllegalArgumentException() throws Throwable {
		IllegalArgumentException exception = shouldThrow(IllegalArgumentException.class, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				new ImmutableResponse((ImmutableResponseHeader)null, (ImmutableResponseBody)null);
				return null;
			}
		});
		
		assertEquals("header is null", exception.getMessage());
	}
	
	@Test
	public void constructByStringWithNoDoubleLineBreakThrowsInvalidResponseException() throws Throwable {
		InvalidResponseException exception = shouldThrow(InvalidResponseException.class, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				new ImmutableResponse("HTTP/1.1 200 OK\r\n");
				return null;
			}
		});
		
		assertEquals("Unable to parse [HTTP/1.1 200 OK\r\n] into a valid HTTP Response", exception.getMessage());
		assertSame(IllegalArgumentException.class, exception.getCause().getClass());
		assertEquals("A Response must have a double CLRF after the header", exception.getCause().getMessage());
	}
	
	@Test
	public void constructByStringDelegatesToHeaderAndBodyConstructByString() throws Exception {
		ImmutableResponseHeader headerMock = createMockAndExpectNew(ImmutableResponseHeader.class, "header");
		ImmutableResponseBody bodyMock = createMock(ImmutableResponseBody.class);
		mockStatic(ImmutableResponseBody.class);
		expect(ImmutableResponseBody.parse("body")).andReturn(bodyMock);
		replayAll();
		
		ImmutableResponse immutableResponse = new ImmutableResponse(
				"header\r\n" +
				"\r\n" +
				"body");
		
		assertSame(headerMock, immutableResponse.getHeader());
		assertSame(bodyMock, immutableResponse.getBody());
		
		verifyAll();
	}
	
	@Test
	public void toStringReturnsSemanticallySameResponseAsStringConstructorTakes() throws Exception {
		String responseString = "HTTP/1.1 200 OK\r\n" +
				"\r\n" +
				"somebody";
		ImmutableResponse immutableResponse = new ImmutableResponse(responseString);
		assertEquals(responseString, immutableResponse.toString());
	}
}
