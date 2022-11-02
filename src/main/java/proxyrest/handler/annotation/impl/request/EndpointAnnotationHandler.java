package proxyrest.handler.annotation.impl.request;

import proxyrest.advice.api.Endpoint;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.ClassAnnotationHandler;

public class EndpointAnnotationHandler implements ClassAnnotationHandler<Endpoint, AbstractHttpRequest<?>> {

	@Override
	public void handleAnnotation(int index, Endpoint annotation, Class<?> source, AbstractHttpRequest<?> request, Object[] args) {
		request.setURL(annotation.value());
	}

	@Override
	public Class<Endpoint> getAnnotationType() {
		return Endpoint.class;
	}
	
}
