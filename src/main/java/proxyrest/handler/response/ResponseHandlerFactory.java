package proxyrest.handler.response;

import proxyrest.advice.response.ExpectedResponse;
import proxyrest.handler.ResponseHandler;
import proxyrest.util.ClassFactory;

public class ResponseHandlerFactory extends ClassFactory<ResponseHandler, ExpectedResponse> {

	public ResponseHandlerFactory(ResponseHandler defaultObject) {
		super(defaultObject);
	}

	@Override
	protected Class<ResponseHandler> getObjectClass() {
		return ResponseHandler.class;
	}

	@Override
	protected String getDefinedName(ExpectedResponse annotation) {
		return annotation.responseHandler();
	}
	
}
