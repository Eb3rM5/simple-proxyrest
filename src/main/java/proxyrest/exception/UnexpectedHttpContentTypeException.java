package proxyrest.exception;

import org.apache.hc.core5.http.HttpResponse;

import proxyrest.advice.response.ExpectedResponse;

public class UnexpectedHttpContentTypeException extends UnexpectedHttpResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634259623341122211L;

	public UnexpectedHttpContentTypeException(final ExpectedResponse expectedResponse, final HttpResponse httpResponse, String actualContentType) {
		super(expectedResponse, httpResponse, "Expected content type was " + expectedResponse.contentType() + ", but the request got " + actualContentType);
	}
	
}
