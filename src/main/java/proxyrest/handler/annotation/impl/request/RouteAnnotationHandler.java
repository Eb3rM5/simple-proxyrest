package proxyrest.handler.annotation.impl.request;

import java.lang.reflect.Method;

import proxyrest.advice.route.Route;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.MethodAnnotationHandler;

public class RouteAnnotationHandler implements MethodAnnotationHandler<Route, AbstractHttpRequest<?>> {

	@Override
	public void handleAnnotation(int index, Route annotation, Method source, AbstractHttpRequest<?> request, Object[] args) {
		request.addPath(annotation.mapping());
		request.setMethod(annotation.method());
	}
	
	@Override
	public Class<Route> getAnnotationType() {
		return Route.class;
	}

}
