package proxyrest.dummy.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import proxyrest.client.AbstractHttpResponse;

public class DummyHttpResponse implements AbstractHttpResponse<DummyHttpResponse, DummyHttpRequest> {

	private final int statusCode;
	private final String contentType;
	private final String response;
	private final DummyHttpRequest request;
	
	public DummyHttpResponse(int statusCode, String contentType, String response, DummyHttpRequest request) {
		this.statusCode = statusCode;
		this.contentType = contentType;
		this.response = response;
		this.request = request;
	}
	
	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public InputStream openStream() {
		if (response != null) {
			return new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
		}
		return null;
	}

	@Override
	public DummyHttpResponse getClientResponse() {
		return this;
	}
	
	@Override
	public DummyHttpRequest getRequest() {
		return request;
	}

}
