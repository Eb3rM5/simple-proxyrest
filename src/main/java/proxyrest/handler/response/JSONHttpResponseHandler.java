package proxyrest.handler.response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.ObjectMapper;

import proxyrest.client.AbstractHttpResponse;
import proxyrest.handler.ResponseHandler;
import proxyrest.util.MimeUtils;

public class JSONHttpResponseHandler implements ResponseHandler {

	public static final String HANDLER_NAME = JSONHttpResponseHandler.class.getName();
	
	private final ObjectMapper mapper;
	
	public JSONHttpResponseHandler() {
		this(new ObjectMapper());
	}
	
	public JSONHttpResponseHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public <T> T handleResponse(AbstractHttpResponse<?> response, Object proxy, Method method, Class<T> returnType, Object... args) {
		try (InputStream input = response.openStream()) {
			return mapper.readValue(input, returnType);
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean canHandle(String contentType) {
		return MimeUtils.isJsonContent(contentType);
	}

}
