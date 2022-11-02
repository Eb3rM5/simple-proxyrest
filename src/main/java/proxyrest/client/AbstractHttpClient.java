package proxyrest.client;

public interface AbstractHttpClient<I extends AbstractHttpRequest<?>, O extends AbstractHttpResponse<?, I>> {

	O request(I request);
	
	I createEmptyRequest();
	
}
