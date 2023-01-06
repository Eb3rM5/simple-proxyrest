package proxyrest;

import java.lang.reflect.Proxy;

import org.apache.hc.client5.http.impl.classic.HttpClients;

import proxyrest.client.AbstractHttpClient;
import proxyrest.client.apache.ApacheHttpClient;

public interface RestAPI {

	public static <T extends RestAPI> T createAPI(Class<T> apiClass) {
		final ApacheHttpClient apacheHttpClient = new ApacheHttpClient(HttpClients.createDefault());
		return createAPI(apiClass, apacheHttpClient);
	}
	
	public static <T extends RestAPI> T createAPI(Class<T> apiClass, AbstractHttpClient<?, ?> httpClient) {
		final Object instance = Proxy.newProxyInstance(RestAPI.class.getClassLoader(), new Class<?>[] { apiClass }, new APIInvocationHandler<>(httpClient));
		return apiClass.isInstance(instance) ? apiClass.cast(instance) : null;
	}
	
}
