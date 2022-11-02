package proxyrest.client;

import java.io.InputStream;

public interface AbstractHttpResponse<T, R extends AbstractHttpRequest<?>> {

	int getStatusCode();
	
	String getContentType();
	
	InputStream openStream();
	
	T getClientResponse();
	
	R getRequest();
	
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
