package proxyrest.client;

import proxyrest.handler.ResponseHandler;
import proxyrest.handler.request.RequestBodyHandler;

public interface AbstractHttpRequest<T> {
	
	void setURL(String url);
	
	void setMethod(String method);
	
	void addPath(String path);
	
	void addQueryParameter(String key, String value);
	
	void addHeader(String key, Object value);
	
	void setResponseHandler(ResponseHandler responseHandler);
	
	void setRequestBodyHandler(RequestBodyHandler requestBodyHandler);
	
	void setContent(Object content);
	
	void setContentType(String contentType);
	
	ResponseHandler getResponseHandler();
	
	Object getContent();
	
	String getContentType();
	
	T build();
	
}