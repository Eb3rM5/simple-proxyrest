package proxyrest.util;

public final class ArrayUtils {

	@SafeVarargs
	public static <T> T[] toArray(T... elements){
		return elements;
	}
	
	private ArrayUtils() {}
	
}
