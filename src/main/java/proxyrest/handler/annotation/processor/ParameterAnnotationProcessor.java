package proxyrest.handler.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class ParameterAnnotationProcessor<O> extends AnnotationProcessor<Parameter, O> {

	public ParameterAnnotationProcessor(List<AnnotationHandler<Parameter, ? extends Annotation, O>> handlers) {
		super(handlers, true);
	}
	
	@Override
	protected Parameter[] getSources(Method method) {
		return method.getParameters();
	}

}
