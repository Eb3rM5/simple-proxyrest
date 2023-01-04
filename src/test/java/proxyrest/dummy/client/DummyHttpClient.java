package proxyrest.dummy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import proxyrest.client.AbstractHttpClient;
import proxyrest.dummy.repository.DummyRepository;

public class DummyHttpClient<R extends DummyRepository<?>> implements AbstractHttpClient<DummyHttpRequest, DummyHttpResponse>{
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final int STATUS_OK = 200;
	public static final int NOT_FOUND = 404;
	
	private R repository;
	private DummyRequestHandler<R> handler;
	private final ObjectMapper mapper;
	
	public DummyHttpClient(R repository, DummyRequestHandler<R> handler) {
		this.repository = repository;
		this.handler = handler;
		mapper = new ObjectMapper();
	}
	
	public void setHandler(DummyRequestHandler<R> handler) {
		this.handler = handler;
	}
	
	@Override
	public DummyHttpResponse request(DummyHttpRequest request) {
		if (handler != null) {
			Object data = handler.handleRequest(request, repository);
			if (data != null) {
				try {
					String response = data.getClass().equals(String.class) ? (String)data : mapper.writeValueAsString(data);
					return new DummyHttpResponse(STATUS_OK, JSON_CONTENT_TYPE, response, request);
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
	
	public static interface DummyRequestHandler<R extends DummyRepository<?>> {

		public Object handleRequest(DummyHttpRequest request, R repository);
		
	}

}