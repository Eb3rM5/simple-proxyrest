package proxyrest;

import java.util.Arrays;

import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.impl.GetMappingAnnotationHandler;
import proxyrest.handler.annotation.impl.JsonPropertyMappingAnnotationHandler;
import proxyrest.handler.annotation.impl.RouteAnnotationHandler;
import proxyrest.handler.annotation.processor.MethodAnnotationProcessor;

public class APIMethodAnnotationProcessor extends MethodAnnotationProcessor<AbstractHttpRequest<?>> {

	public APIMethodAnnotationProcessor() {
		super(Arrays.asList(
				new RouteAnnotationHandler(),
				new GetMappingAnnotationHandler(),
				new JsonPropertyMappingAnnotationHandler()
					));
	}
	
}
