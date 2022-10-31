package proxyrest.handler.annotation.processor;

import java.lang.reflect.Method;
import java.util.List;

import proxyrest.handler.annotation.AnnotationHandler;

public class ClassAnnotationProcessor extends SingleSourceAnnotationProcessor<Class<?>>{

	public ClassAnnotationProcessor(List<AnnotationHandler<Class<?>, ?>> handlers) {
		super(handlers, false);
	}

	@Override
	protected Class<?> getSource(Method method) {
		return method.getDeclaringClass();
	}

}
