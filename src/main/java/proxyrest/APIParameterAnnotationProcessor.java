package proxyrest;

import java.util.Arrays;

import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.annotation.impl.PathParamAnnotationHandler;
import proxyrest.handler.annotation.processor.ParameterAnnotationProcessor;

public class APIParameterAnnotationProcessor extends ParameterAnnotationProcessor<AbstractHttpRequest<?>> {

	public APIParameterAnnotationProcessor() {
		super(Arrays.asList(
				new PathParamAnnotationHandler()
				));
	}

}
