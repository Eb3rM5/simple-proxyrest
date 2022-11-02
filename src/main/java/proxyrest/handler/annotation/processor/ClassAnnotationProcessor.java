package proxyrest.handler.annotation.processor;

import java.lang.reflect.Method;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class ClassAnnotationProcessor<O> extends SingleSourceAnnotationProcessor<Class<?>, O>{

	public ClassAnnotationProcessor(List<AnnotationHandler<Class<?>, ?, O>> handlers) {
		super(handlers, false);
	}

	@Override
	protected Class<?> getSource(Method method) {
		return method.getDeclaringClass();
	}

}
