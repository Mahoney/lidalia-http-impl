package uk.org.lidalia.http.impl.immutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMockAndExpectNew;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static uk.org.lidalia.test.ShouldThrow.shouldThrow;

import java.util.concurrent.Callable;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.org.lidalia.http.api.exception.InvalidResponseException;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponse;
import uk.org.lidalia.http.impl.immutable.response.ImmutableResponseHeader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImmutableResponse.class})
public class ResponseTest {

    @Test
    public void constructedWithNullHeaderThrowsIllegalArgumentException() {
        shouldThrow(NullPointerException.class, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                new ImmutableResponse((ImmutableResponseHeader) null, null);
                return null;
            }
        });
    }

    @Test
    public void constructByStringWithNoDoubleLineBreakThrowsInvalidResponseException() {
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

    @Test @Ignore
    public void constructByStringDelegatesToHeaderAndBodyConstructByString() throws Exception {
        ImmutableResponseHeader headerMock = createMockAndExpectNew(ImmutableResponseHeader.class, "header");
        byte[] bytes = "body".getBytes();
        ImmutableBody bodyMock = createMockAndExpectNew(ImmutableBody.class, bytes);
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
        assertEquals("HTTP/1.1 200 OK\r\n", immutableResponse.getHeader().toString());
    }
}
