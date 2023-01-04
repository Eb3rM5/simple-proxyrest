package proxyrest.handler.annotation.impl.request;

import java.lang.reflect.Parameter;

import proxyrest.advice.param.RequestBody;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.ParameterAnnotationHandler;
import proxyrest.handler.request.RequestBodyHandlerFactory;

public class RequestBodyAnnotationHandler implements ParameterAnnotationHandler<RequestBody, AbstractHttpRequest<?>> {

	private final RequestBodyHandlerFactory requestBodyHandlerFactory;
	
	public RequestBodyAnnotationHandler() {
		this.requestBodyHandlerFactory = new RequestBodyHandlerFactory();
	}
	
	@Override
	public void handleAnnotation(int index, RequestBody annotation, Parameter source, AbstractHttpRequest<?> object, Object[] args) {
		object.setContent(args[index]);
		object.setContentType(annotation.contentType());
		object.setRequestBodyHandler(requestBodyHandlerFactory.getObject(annotation));
	}

	@Override
	public Class<RequestBody> getAnnotationType() {
		return RequestBody.class;
	}

}
