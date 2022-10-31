package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;

public interface ClassAnnotationHandler<T extends Annotation> extends AnnotationHandler<Class<?>, T>{
}
