package proxyrest;

import java.util.Arrays;

import proxyrest.handler.annotation.impl.PathParamAnnotationHandler;
import proxyrest.handler.annotation.processor.ParameterAnnotationProcessor;

public class APIParameterAnnotationProcessor extends ParameterAnnotationProcessor {

	public APIParameterAnnotationProcessor() {
		super(Arrays.asList(
				new PathParamAnnotationHandler()
				));
	}

}
