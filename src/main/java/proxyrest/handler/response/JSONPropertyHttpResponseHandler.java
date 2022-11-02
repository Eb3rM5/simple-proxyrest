package proxyrest.handler.response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.ObjectMapper;

import proxyrest.advice.handler.JsonPropertyMapping;
import proxyrest.client.AbstractHttpResponse;
import proxyrest.handler.ResponseHandler;
import proxyrest.util.MimeUtils;

public class JSONPropertyHttpResponseHandler implements ResponseHandler {

	public static final String JSON_PROPERTY_HANDLER = "proxyrest.handler.response.JSONPropertyHttpResponseHandler";
	
	private final ObjectMapper mapper;
	
	public JSONPropertyHttpResponseHandler() {	
		this(new ObjectMapper());
	}
	
	public JSONPropertyHttpResponseHandler(final ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public <T> T handleResponse(AbstractHttpResponse<?, ?> response, Object proxy, Method method, Class<T> returnType, Object... args) {
		final String propertyPath = getProperty(method);
		try (InputStream input = response.openStream()){
			var json = mapper.readTree(input);
			return mapper.readValue(json.path(propertyPath).traverse(), returnType);
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getProperty(Method method) {
		var annotation = method.getDeclaredAnnotation(JsonPropertyMapping.class);
		return annotation == null ? null : annotation.property();
	}
	
	@Override
	public boolean canHandle(String contentType) {
		return MimeUtils.isJsonContent(contentType);
	}
	
}
