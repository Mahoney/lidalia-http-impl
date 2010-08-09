package uk.org.lidalia.testutils;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

public class Assert {
	
	public static <E extends Throwable> E shouldThrow(Class<E> throwableType, Callable<Void> callable) throws Throwable {
		return shouldThrow(null, throwableType, callable);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Throwable> E shouldThrow(String diagnosticMessage, Class<E> throwableType, Callable<Void> callable) throws Throwable {
		try {
			callable.call();
			reportFailure(throwableType, diagnosticMessage);
			return null;
		} catch (Throwable actual) {
			if (instanceOf(actual, throwableType)) {
				return (E) actual;
			}
			throw actual;
		}
	}

	private static void reportFailure(Object expected, String diagnosticMessage) throws AssertionError {
		String message = "No exception thrown; expected " + expected;
		if (diagnosticMessage != null) {
			message += (System.getProperty("line.separator") + diagnosticMessage);
		}
		fail(message);
	}
	
	public static void shouldThrow(Throwable expected, Callable<Void> callable) throws Throwable {
		shouldThrow(null, expected, callable);
	}
	
	public static void shouldThrow(String diagnosticMessage, Throwable expected, Callable<Void> callable) throws Throwable {
		Throwable actual = shouldThrow(diagnosticMessage, expected.getClass(), callable);
		assertSame("Actual exception was not the expected instance. " + diagnosticMessage, expected, actual);
	}
	
	public static boolean instanceOf(Object o, Class<?> c) {
		return c.isAssignableFrom(o.getClass());
	}
	
	private Assert() {
		throw new UnsupportedOperationException("Not instantiable");
	}
}
