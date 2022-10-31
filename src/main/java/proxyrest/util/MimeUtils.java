package proxyrest.util;

import org.apache.hc.core5.http.ContentType;

public final class MimeUtils {

	public static boolean isSameContentType(final ContentType contentType, final String anotherContentType) {
		if (contentType == null || anotherContentType == null || anotherContentType.isBlank()) return false;
		return contentType.isSameMimeType(ContentType.parseLenient(anotherContentType));
	}
	
	public static boolean isJsonContent(final String contentType) {
		return isSameContentType(ContentType.APPLICATION_JSON, contentType);
	}
	
	private MimeUtils() {}
	
}
