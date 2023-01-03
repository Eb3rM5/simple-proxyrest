package proxyrest.handler.annotation.impl.request;

import java.lang.reflect.Parameter;

import proxyrest.advice.param.QueryParam;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.ParameterAnnotationHandler;

public class QueryParamAnnotationHandler implements ParameterAnnotationHandler<QueryParam, AbstractHttpRequest<?>>{

	@Override
	public void handleAnnotation(int index, QueryParam annotation, Parameter source, AbstractHttpRequest<?> object, Object[] args) {
		String name = annotation.name();
		if (name.isEmpty()) {
			name = source.getName();
		}
		object.addQueryParameter(name, String.valueOf(args[index]));
	}

	@Override
	public Class<QueryParam> getAnnotationType() {
		return QueryParam.class;
	}
	
}
