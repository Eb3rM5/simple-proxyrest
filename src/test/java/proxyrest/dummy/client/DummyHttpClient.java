package proxyrest.dummy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import proxyrest.client.AbstractHttpClient;

public class DummyHttpClient implements AbstractHttpClient<DummyHttpRequest, DummyHttpResponse>{
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final int STATUS_OK = 200;
	public static final int NOT_FOUND = 404;
	
	private DummyRequestHandler handler;
	private final ObjectMapper mapper;
	
	public DummyHttpClient() {
		mapper = new ObjectMapper();
	}
	
	public void setHandler(DummyRequestHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public DummyHttpResponse request(DummyHttpRequest request) {
		if (handler != null) {
			Object data = handler.handleRequest(request);
			if (data != null) {
				try {
					return new DummyHttpResponse(STATUS_OK, JSON_CONTENT_TYPE, mapper.writeValueAsString(data), request);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return new DummyHttpResponse(NOT_FOUND, "text/plain", null, request);
	}

	@Override
	public DummyHttpRequest createEmptyRequest() {
		return new DummyHttpRequest();
	}
	
	public static interface DummyRequestHandler {

		public Object handleRequest(DummyHttpRequest request);
		
	}

}