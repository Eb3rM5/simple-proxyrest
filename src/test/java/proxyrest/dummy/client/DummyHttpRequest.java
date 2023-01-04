package proxyrest.dummy.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.ResponseHandler;
import proxyrest.handler.request.RequestBodyHandler;

public class DummyHttpRequest implements AbstractHttpRequest<DummyHttpRequest>{

	private String url;
	private String method;
	public final List<String> paths;
	public final Map<String, String> queryParameters;
	public final Map<String, String> headers;
	private Object content;
	private String contentType; 
	
	private ResponseHandler responseHandler;
	private RequestBodyHandler requestBodyHandler;
	
	public DummyHttpRequest() {
		url = "";
		method = "";
		paths = new ArrayList<>();
		headers = new HashMap<>();
		queryParameters = new HashMap<>();
	}
	
	@Override
	public void setURL(String url) {
		this.url = url;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void addPath(String path) {
		paths.add(path);
	}

	@Override
	public void addQueryParameter(String key, String value) {
		queryParameters.put(key, value);
	}

	@Override
	public void addHeader(String key, Object value) {
		headers.put(key, key);
	}

	@Override
	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler; 
	}
	
	@Override
	public void setContent(Object content) {
		this.content = content;
	}
	
	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}
	
	public String getMethod() {
		return method;
	}
	
	public List<String> getPaths() {
		return paths;
	}
	
	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public Object getContent() {
		return content;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	@Override
	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	@Override
	public DummyHttpRequest build() {
		return this;
	}

	@Override
	public void setRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
		this.requestBodyHandler = requestBodyHandler;
	}
	
	public String getBodyAsString() {
		if (requestBodyHandler != null) {
			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			requestBodyHandler.handleRequestBody(this, content, output);
			return new String(output.toByteArray());
		}
		return null;
	}
	
}
