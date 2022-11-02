package proxyrest.handler.annotation.processor;

import java.lang.reflect.Method;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class MethodAnnotationProcessor<O> extends SingleSourceAnnotationProcessor<Method, O> {
	
	public MethodAnnotationProcessor(List<AnnotationHandler<Method, ?, O>> handlers) {
		super(handlers, false);
	}
	
	@Override
	protected Method getSource(Method method) {
		return method;
	}

}
