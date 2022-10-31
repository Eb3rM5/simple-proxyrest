package proxyrest.exception;

import org.apache.hc.core5.http.HttpResponse;

import proxyrest.advice.response.ExpectedResponse;

public class UnexpectedHttpStatusException extends UnexpectedHttpResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634259623341122211L;

	public UnexpectedHttpStatusException(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, int actualStatus) {
		super(expectedResponse, httpResponse, "Expected status was " + expectedResponse.status() + ", but the request got " + actualStatus);
	}
	
}
