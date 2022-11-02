package proxyrest.handler.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;
import proxyrest.util.ArrayUtils;

public abstract class SingleSourceAnnotationProcessor<T extends AnnotatedElement, O> extends AnnotationProcessor<T, O> {

	public SingleSourceAnnotationProcessor(boolean useMethodArguments) {
		super(useMethodArguments);
	}
	
	public SingleSourceAnnotationProcessor(List<AnnotationHandler<T, ? extends Annotation, O>> handlers, boolean useMethodArguments) {
		super(handlers, useMethodArguments);
	}
	
	protected abstract T getSource(Method method);
	
	@Override
	protected final T[] getSources(Method method) {
		return ArrayUtils.toArray(getSource(method));
	}
	
}
