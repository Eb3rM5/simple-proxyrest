package proxyrest.handler.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class ParameterAnnotationProcessor extends AnnotationProcessor<Parameter> {

	public ParameterAnnotationProcessor(List<AnnotationHandler<Parameter, ? extends Annotation>> handlers) {
		super(handlers, true);
	}
	
	@Override
	protected Parameter[] getSources(Method method) {
		return method.getParameters();
	}

}
