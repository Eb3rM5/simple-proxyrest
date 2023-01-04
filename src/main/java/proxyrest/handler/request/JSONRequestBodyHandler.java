package proxyrest.handler.request;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ClassUtil;

import proxyrest.client.AbstractHttpRequest;

public class JSONRequestBodyHandler implements RequestBodyHandler {

	private final ObjectMapper mapper;
	
	public JSONRequestBodyHandler() {
		mapper = new ObjectMapper();
	}
	
	@Override
	public void handleRequestBody(AbstractHttpRequest<?> request, Object value, OutputStream output) {
		var type = value.getClass();
		try {
			if (type.equals(String.class) || ClassUtil.primitiveType(type) != null) {
				output.write(String.valueOf(value).getBytes());
			} else {
				mapper.writeValue(output, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
