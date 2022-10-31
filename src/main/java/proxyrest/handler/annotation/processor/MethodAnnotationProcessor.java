package proxyrest.handler.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class MethodAnnotationProcessor extends SingleSourceAnnotationProcessor<Method> {
	
	public MethodAnnotationProcessor(List<AnnotationHandler<Method, ? extends Annotation>> handlers) {
		super(handlers, false);
	}
	
	@Override
	protected Method getSource(Method method) {
		return method;
	}

}
