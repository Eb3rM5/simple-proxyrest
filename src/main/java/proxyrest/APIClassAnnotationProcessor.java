package proxyrest;

import java.util.Arrays;

import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.impl.request.EndpointAnnotationHandler;
import proxyrest.handler.annotation.processor.ClassAnnotationProcessor;

public class APIClassAnnotationProcessor extends ClassAnnotationProcessor<AbstractHttpRequest<?>> {

	public APIClassAnnotationProcessor() {
		super(Arrays.asList(
				new EndpointAnnotationHandler()
				));
	}
	
}
