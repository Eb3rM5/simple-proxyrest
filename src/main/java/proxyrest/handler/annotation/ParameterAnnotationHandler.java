package proxyrest.handler.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public interface ParameterAnnotationHandler<T extends Annotation, O> extends AnnotationHandler<Parameter, T, O> {
}
