package proxyrest.handler.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import proxyrest.handler.annotation.AnnotationHandler;

public abstract class AnnotationProcessor<T extends AnnotatedElement, O> {

	private final boolean useMethodArguments;
	private final Map<String, AnnotationHandler<T, ? extends Annotation, O>> handlers;
	
	protected AnnotationProcessor(boolean useMethodArguments) {
		handlers = new HashMap<>();
		this.useMethodArguments = useMethodArguments;
	}
	
	protected AnnotationProcessor(List<AnnotationHandler<T, ? extends Annotation, O>> handlers, boolean useMethodArguments) {
		this(useMethodArguments);
		if (handlers != null) {
			this.handlers.putAll(handlers.parallelStream().collect(Collectors.toMap(handler -> handler.getAnnotationType().getName(), handler -> handler)));
		}
	}
	
	public void process(Method method, O object, Object[] args) {
		if (!useMethodArguments) args = null;
		var sources = getSources(method);
		if (sources != null) {
			for (T source : sources) {
				processSource(source, object, args);
			}
		}
	}
	
	protected void processSource(T source, O object, Object[] args) {
		if (source != null) {
			final Annotation[] annotations = source.getDeclaredAnnotations();
			for (int i = 0; i < annotations.length; i++) {
				Annotation annotation = annotations[i];
				var annotationHandler = handlers.get(getAnnotationKey(annotation));
				if (annotationHandler != null) {
					handleAnnotation(i, annotation, annotationHandler, source, object, args);
				}
			}
		}
	}
	
	private <A extends Annotation> void handleAnnotation(int index, Annotation annotation, AnnotationHandler<T, A, O> handler, T source, O object, Object[] args) {
		A typesafeAnnotation = handler.getAnnotationType().cast(annotation);
		handler.handleAnnotation(index, typesafeAnnotation, source, object, args);
	}
	
	public void addHandler(Annotation annotation, AnnotationHandler<T, ? extends Annotation, O> handler) {
		handlers.put(getAnnotationKey(annotation), handler);
	}
	
	protected String getAnnotationKey(Annotation annotation) {
		return annotation.annotationType().getName();
	}
	
	protected abstract T[] getSources(Method method);
	
}
