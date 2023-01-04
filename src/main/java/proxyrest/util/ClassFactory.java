package proxyrest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class ClassFactory<T, A extends Annotation> {

	private final T defaultObject;
	private Map<String, T> pool;
	
	public ClassFactory(T defaultObject) {
		this.defaultObject = defaultObject;
		pool = new WeakHashMap<>();
	}
	
	public T getObject(String name) {
		if (name == null || name.isBlank()) return defaultObject;
		return pool.computeIfAbsent(name, this::createInstance);
	}
	
	public T getObject(A annotation) {
		if (annotation == null) return null;
		return getObject(getDefinedName(annotation));
	}
	
	protected T createInstance(String name) {
		try {
			Class<?> objectClass = Class.forName(name);
			if (getObjectClass().isAssignableFrom(objectClass)) {
				Object object = objectClass.getDeclaredConstructor().newInstance();
				return getObjectClass().cast(object);
			}
			return defaultObject;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return defaultObject;
		}
	}
	
	public T getDefault() {
		return defaultObject;
	}
	
	protected abstract Class<T> getObjectClass();
	
	protected abstract String getDefinedName(A annotation);
	
}
