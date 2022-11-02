package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;

public interface ClassAnnotationHandler<T extends Annotation, O> extends AnnotationHandler<Class<?>, T, O>{
}
