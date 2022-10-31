package proxyrest.client;

public interface AbstractHttpClient<I extends AbstractHttpRequest<?>, O extends AbstractHttpResponse<?>> {

	O request(I request);
	
	I createEmptyRequest();
	
}
