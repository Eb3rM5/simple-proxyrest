package proxyrest.handler.annotation.impl;

import java.lang.reflect.Parameter;

import proxyrest.advice.param.PathParam;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.ParameterAnnotationHandler;

public class PathParamAnnotationHandler implements ParameterAnnotationHandler<PathParam> {

	@Override
	public void handleAnnotation(int index, PathParam annotation, Parameter source, AbstractHttpRequest<?> request, Object[] args) {
		request.addPath(String.valueOf(args[index]));
	}

	@Override
	public Class<PathParam> getAnnotationType() {
		return PathParam.class;
	}

}
