package proxyrest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import proxyrest.client.AbstractHttpClient;
import proxyrest.client.AbstractHttpRequest;
import proxyrest.client.AbstractHttpResponse;
import proxyrest.handler.ResponseHandler;
import proxyrest.handler.annotation.processor.AnnotationProcessor;
import proxyrest.handler.response.JSONHttpResponseHandler;
import proxyrest.handler.response.ResponseHandlerFactory;

public class APIInvocationHandler<C extends AbstractHttpClient<I, O>, I extends AbstractHttpRequest<?>, O extends AbstractHttpResponse<?, I>> implements InvocationHandler {

	private final C httpClient;
	private final ResponseHandlerFactory responseHandlerFactory;
	private final List<AnnotationProcessor<?, AbstractHttpRequest<?>>> requestAnnotationProcessors;
	
	public APIInvocationHandler(C httpClient) {
		this(httpClient, new JSONHttpResponseHandler());
	}
	
	public APIInvocationHandler(C httpClient, ResponseHandler defaultResponseHandler) {
		this.httpClient = httpClient;
		this.responseHandlerFactory = new ResponseHandlerFactory(defaultResponseHandler);
		this.requestAnnotationProcessors = getRequestAnnotationProcessors();
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		final String methodName = method.getName();
		return switch(methodName) {
			default -> handleInvocation(proxy, method, args);
			case "toString" -> toString();
			case "hashCode" -> hashCode();
		};
	}
	
	private Object handleInvocation(Object proxy, Method method, Object[] args) throws Exception {
		I request = httpClient.createEmptyRequest();
		
		for (var annotationProcessor : requestAnnotationProcessors) {
			annotationProcessor.process(method, request, args);
		}
		
		final var response = httpClient.request(request);
		if (response != null) {
			return handleResponse(response, request.getResponseHandler(), method, args);
		}
		return null;
	}
	
	protected Object handleResponse(O response, ResponseHandler responseHandler, Method method, Object[] args) {
		if (responseHandler == null) responseHandler = responseHandlerFactory.getDefault();
		return responseHandler.handleResponse(response, responseHandler, method, method.getReturnType(), args);
	}
	
	public List<AnnotationProcessor<?, AbstractHttpRequest<?>>> getRequestAnnotationProcessors(){
		return Arrays.asList(
				new APIClassAnnotationProcessor(),
				new APIMethodAnnotationProcessor(),
				new APIParameterAnnotationProcessor()
				);
	}
	
}
