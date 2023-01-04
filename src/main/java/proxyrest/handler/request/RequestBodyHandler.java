package proxyrest.handler.request;

import java.io.OutputStream;

import proxyrest.client.AbstractHttpRequest;

public interface RequestBodyHandler {

	void handleRequestBody(AbstractHttpRequest<?> request, Object value, OutputStream output);
	
}
