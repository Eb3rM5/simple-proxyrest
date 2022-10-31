package proxyrest.handler.annotation.impl;

import java.lang.reflect.Method;

import proxyrest.advice.handler.JsonPropertyMapping;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.MethodAnnotationHandler;
import proxyrest.handler.response.JSONPropertyHttpResponseHandler;

public class JsonPropertyMappingAnnotationHandler implements MethodAnnotationHandler<JsonPropertyMapping> {

	public static final JSONPropertyHttpResponseHandler RESPONSE_HANDLER = new JSONPropertyHttpResponseHandler();
	
	@Override
	public void handleAnnotation(int index, JsonPropertyMapping annotation, Method source, AbstractHttpRequest<?> request, Object[] args) {
		request.addPath(annotation.mapping());
		request.setResponseHandler(RESPONSE_HANDLER);
		request.setMethod(annotation.routeMethod());
	}
	
	@Override
	public Class<JsonPropertyMapping> getAnnotationType() {
		return JsonPropertyMapping.class;
	}
	
}
