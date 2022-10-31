package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodAnnotationHandler<T extends Annotation> extends AnnotationHandler<Method, T> {
	
}
