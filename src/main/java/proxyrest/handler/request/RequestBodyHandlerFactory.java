package proxyrest.handler.request;

import proxyrest.advice.param.RequestBody;
import proxyrest.util.ClassFactory;

public class RequestBodyHandlerFactory extends ClassFactory<RequestBodyHandler, RequestBody>{
	
	public RequestBodyHandlerFactory() {
		super(new JSONRequestBodyHandler());
	}

	@Override
	protected Class<RequestBodyHandler> getObjectClass() {
		return RequestBodyHandler.class;
	}

	@Override
	protected String getDefinedName(RequestBody annotation) {
		return annotation.handler();
	}
	
}
