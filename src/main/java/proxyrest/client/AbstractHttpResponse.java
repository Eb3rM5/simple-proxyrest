package proxyrest.client;

import java.io.InputStream;

public interface AbstractHttpResponse<T> {

	int getStatusCode();
	
	String getContentType();
	
	InputStream openStream();
	
	T getClientResponse();
	
	default boolean isContentType(String expectedContentType) {
		String contentType = getContentType();
		if (contentType != null) {
			return contentType.equalsIgnoreCase(expectedContentType);
		}
		return false;
	}
	
	default boolean isStatusCode(int expectedStatusCode) {
		return getStatusCode() == expectedStatusCode;
	}
	
}
