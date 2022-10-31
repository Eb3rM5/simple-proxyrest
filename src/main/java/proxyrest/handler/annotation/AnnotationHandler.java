package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import proxyrest.client.AbstractHttpRequest;

public interface AnnotationHandler<T extends AnnotatedElement, A extends Annotation> {

	void handleAnnotation(int index, A annotation, T source, AbstractHttpRequest<?> request, Object[] args);
	
	Class<A> getAnnotationType();
	
}
