package proxyrest.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ProtocolException;

import proxyrest.advice.response.ExpectedResponse;
import proxyrest.exception.UnexpectedHttpContentTypeException;
import proxyrest.exception.UnexpectedHttpResponseException;
import proxyrest.exception.UnexpectedHttpStatusException;

public final class ResponseUtils {

	public static boolean isExpectedResponse(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, final Method method) throws UnexpectedHttpResponseException {
		var exceptionTypes = Arrays.asList(method.getExceptionTypes());
		return isExpectedResponse(expectedResponse, httpResponse, exceptionTypes.contains(UnexpectedHttpStatusException.class), exceptionTypes.contains(UnexpectedHttpContentTypeException.class));
	}
	
	public static boolean isExpectedResponse(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, boolean throwStatusException, boolean throwContentTypeException) throws UnexpectedHttpResponseException {
		if (expectedResponse == null) return true;
		return isExpectedStatus(expectedResponse, httpResponse, throwStatusException) && isExpectedContentType(expectedResponse, httpResponse, throwContentTypeException);
	}
	
	public static boolean isExpectedStatus(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, boolean throwException) throws UnexpectedHttpStatusException {
		final int expectedStatus = expectedResponse.status();
		if (expectedStatus == -1 || expectedStatus == httpResponse.getCode()) {
			return true;
		} else if (throwException) {
			throw new UnexpectedHttpStatusException(expectedResponse, httpResponse, expectedStatus);
		}
		return false;
	}
	
	public static boolean isExpectedContentType(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, boolean throwException) throws UnexpectedHttpContentTypeException {
		final String expectedContentType = expectedResponse.contentType();
		if (expectedContentType.isBlank()) return true;
		try {
			Header header = httpResponse.getHeader("Content-Type");
			if (header != null) {
				final String contentType = header.getValue();
				if (contentType != null && contentType.equalsIgnoreCase(expectedContentType)) {
					return true;
				} else if (throwException) {
					throw new UnexpectedHttpContentTypeException(expectedResponse, httpResponse, contentType);
				}
			}
			return false;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return false;
		}
	}
		
	private ResponseUtils() {}
	
}
