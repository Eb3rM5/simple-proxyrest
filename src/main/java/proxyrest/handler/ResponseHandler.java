package proxyrest.handler;

import java.lang.reflect.Method;

import proxyrest.client.AbstractHttpResponse;

public interface ResponseHandler {

	<T> T handleResponse(AbstractHttpResponse<?> response, Object proxy, Method method, Class<T> returnType, Object... args);
	
	boolean canHandle(String contentType);
	
}
