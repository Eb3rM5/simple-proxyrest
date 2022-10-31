package proxyrest;

import java.util.Arrays;

import proxyrest.handler.annotation.impl.EndpointAnnotationHandler;
import proxyrest.handler.annotation.processor.ClassAnnotationProcessor;

public class APIClassAnnotationProcessor extends ClassAnnotationProcessor {

	public APIClassAnnotationProcessor() {
		super(Arrays.asList(
				new EndpointAnnotationHandler()
				));
	}
	
}
