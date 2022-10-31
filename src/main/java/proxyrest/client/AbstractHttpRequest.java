package proxyrest.client;

import proxyrest.handler.ResponseHandler;

public interface AbstractHttpRequest<T> {
	
	void setURL(String url);
	
	void setMethod(String method);
	
	void addPath(String path);
	
	void addQueryParameter(String key, String value);
	
	void addHeader(String key, Object value);
	
	void setResponseHandler(ResponseHandler responseHandler);
	
	ResponseHandler getResponseHandler();
	
	T build();
	
}