package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface AnnotationHandler<T extends AnnotatedElement, A extends Annotation, O> {

	void handleAnnotation(int index, A annotation, T source, O object, Object[] args);
	
	Class<A> getAnnotationType();
	
}
