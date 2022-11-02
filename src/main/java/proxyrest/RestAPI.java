package proxyrest;

import java.lang.reflect.Proxy;

import proxyrest.client.AbstractHttpClient;

public interface RestAPI {

	public static <T extends RestAPI> T createAPI(Class<T> apiClass, AbstractHttpClient<?, ?> httpClient) {
		final Object instance = Proxy.newProxyInstance(RestAPI.class.getClassLoader(), new Class<?>[] { apiClass }, new APIInvocationHandler<>(httpClient));
		return apiClass.isInstance(instance) ? apiClass.cast(instance) : null;
	}
	
}
