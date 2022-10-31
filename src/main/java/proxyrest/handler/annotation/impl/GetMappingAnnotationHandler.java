package proxyrest.handler.annotation.impl;

import java.lang.reflect.Method;

import proxyrest.advice.route.GetMapping;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.MethodAnnotationHandler;

public class GetMappingAnnotationHandler implements MethodAnnotationHandler<GetMapping> {

	@Override
	public void handleAnnotation(int index, GetMapping annotation, Method source, AbstractHttpRequest<?> request, Object[] args) {
		request.addPath(annotation.mapping());
		request.setMethod("GET");
	}
	
	@Override
	public Class<GetMapping> getAnnotationType() {
		return GetMapping.class;
	}

}