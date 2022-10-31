package proxyrest.exception;

import org.apache.hc.core5.http.HttpResponse;

import proxyrest.advice.response.ExpectedResponse;

public class UnexpectedHttpResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ExpectedResponse expectedResponse;
	private final HttpResponse httpResponse;
	
	public UnexpectedHttpResponseException(ExpectedResponse expectedResponse, HttpResponse httpResponse, String message) {
		super(message);
		this.expectedResponse = expectedResponse;
		this.httpResponse = httpResponse;
	}
	
	public ExpectedResponse getExpectedResponse() {
		return expectedResponse;
	}
	
	public int getExpectedStatus() {
		return expectedResponse.status();
	}
	
	public String getExpectedContentType() {
		return expectedResponse.contentType();
	}
	
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}
	
}
