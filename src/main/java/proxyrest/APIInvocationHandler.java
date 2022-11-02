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

public class APIInvocationHandler implements InvocationHandler {

	private final AbstractHttpClient<? extends AbstractHttpRequest<?>, ? extends AbstractHttpResponse<?, ?>> httpClient;
	private final ResponseHandlerFactory responseHandlerFactory;
	private final List<AnnotationProcessor<?>> annotationProcessors;
	
	public APIInvocationHandler(AbstractHttpClient<? extends AbstractHttpRequest<?>, ? extends AbstractHttpResponse<?, ?>> httpClient) {
		this(httpClient, new JSONHttpResponseHandler());
	}
	
	public APIInvocationHandler(AbstractHttpClient<? extends AbstractHttpRequest<?>, ? extends AbstractHttpResponse<?, ?>> httpClient, ResponseHandler defaultResponseHandler) {
		this.httpClient = httpClient;
		this.responseHandlerFactory = new ResponseHandlerFactory(defaultResponseHandler);
		this.annotationProcessors = getAnnotationProcessors();
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		final String methodName = method.getName();
		return switch(methodName) {
			default -> handleInvocation(proxy, method, args, httpClient);
			case "toString" -> toString();
			case "hashCode" -> hashCode();
		};
	}
	
	private <I extends AbstractHttpRequest<?>, O extends AbstractHttpResponse<?, I>> Object handleInvocation(Object proxy, Method method, Object[] args, AbstractHttpClient<I, O> httpClient) throws Exception {
		var request = httpClient.createEmptyRequest();
		
		for (var annotationProcessor : annotationProcessors) {
			annotationProcessor.process(method, request, args);
		}
		
		final var response = httpClient.request(request);
		if (response != null) {
			return handleResponse(response, request.getResponseHandler(), method, args);
		}
		return null;
	}
	
	protected Object handleResponse(AbstractHttpResponse<?, ?> response, ResponseHandler responseHandler, Method method, Object[] args) {
		if (responseHandler == null) responseHandler = responseHandlerFactory.getDefaultResponseHandler();
		return responseHandler.handleResponse(response, responseHandler, method, method.getReturnType(), args);
	}
	
	public List<AnnotationProcessor<?>> getAnnotationProcessors(){
		return Arrays.asList(
				new APIClassAnnotationProcessor(),
				new APIMethodAnnotationProcessor(),
				new APIParameterAnnotationProcessor()
				);
	}
	
}
