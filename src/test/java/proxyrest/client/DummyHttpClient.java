package proxyrest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import proxyrest.client.DummyUserRepository.DummyUser;

public class DummyHttpClient implements AbstractHttpClient<DummyHttpRequest, DummyHttpResponse>{
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	public static final int STATUS_OK = 200;
	
	public static final int NOT_FOUND = 404;
	
	private final DummyUserRepository userRepository;
	private final ObjectMapper mapper;
	
	public DummyHttpClient() {
		mapper = new ObjectMapper();
		userRepository = new DummyUserRepository();
	}
	
	public void addUser(int id, String name, String occupation) {
		userRepository.save(new DummyUser(id, name, occupation));
	}
	
	@Override
	public DummyHttpResponse request(DummyHttpRequest request) {
		final String route = request.getPaths().get(0);
		return switch (route) {
			default -> null;
			case "/users" -> handleUserRequest(request);
		};
	}
	
	private DummyHttpResponse handleUserRequest(DummyHttpRequest request) {
		return switch (request.getMethod().toUpperCase()) {
			default -> null;
			case "GET" -> handleGetUser(request);
		};
	}
	
	private DummyHttpResponse handleGetUser(DummyHttpRequest request) {
		final String userId = request.getQueryParameters().get("userId");
		final DummyUser user;
		if (userId != null && !userId.isBlank()) {
			user = userRepository.findById(Integer.parseInt(userId));
		} else {
			user = null;
		}
		return createResponseFromUser(JSON_CONTENT_TYPE, user, request);
	}
	
	private DummyHttpResponse createResponseFromUser(String contentType, final DummyUser user, final DummyHttpRequest request) {
		try {
			int code = user == null ? NOT_FOUND : STATUS_OK;
			return new DummyHttpResponse(code, contentType, user == null ? null : mapper.writeValueAsString(user), request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DummyHttpRequest createEmptyRequest() {
		return new DummyHttpRequest();
	}

}