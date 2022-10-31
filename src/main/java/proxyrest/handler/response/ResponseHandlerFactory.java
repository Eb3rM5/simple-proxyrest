package proxyrest.handler.response;

import java.util.Map;
import java.util.WeakHashMap;

import proxyrest.advice.response.ExpectedResponse;
import proxyrest.handler.ResponseHandler;

public class ResponseHandlerFactory {

	private final ResponseHandler defaultResponseHandler;
	private Map<String, ResponseHandler> handlers;
	
	public ResponseHandlerFactory(ResponseHandler defaultResponseHandler) {
		this.defaultResponseHandler = defaultResponseHandler;
		handlers = new WeakHashMap<>();
	}
	
	public ResponseHandler getResponseHandler(String name) {
		if (name == null || name.isBlank()) return null;
		return handlers.computeIfAbsent(name, this::createResponseHandlerInstance);
	}
	
	public ResponseHandler getResponseHandler(ExpectedResponse expectedResponse) {
		if (expectedResponse == null) return defaultResponseHandler;
		return getResponseHandler(expectedResponse.responseHandler());
	}
	
	private ResponseHandler createResponseHandlerInstance(String name) {
		try {
			final Class<?> classObject = Class.forName(name);
			if (ResponseHandler.class.isAssignableFrom(classObject)) {
				return (ResponseHandler)classObject.getDeclaredConstructor().newInstance();
			}
			return defaultResponseHandler;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return defaultResponseHandler;
		}
	}
	
	public ResponseHandler getDefaultResponseHandler() {
		return defaultResponseHandler;
	}
	
}
