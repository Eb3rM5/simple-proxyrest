package proxyrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import proxyrest.advice.api.Endpoint;
import proxyrest.advice.param.PathParam;
import proxyrest.advice.param.QueryParam;
import proxyrest.advice.param.RequestBody;
import proxyrest.advice.route.GetMapping;
import proxyrest.advice.route.Route;
import proxyrest.dummy.client.DummyHttpClient;
import proxyrest.dummy.client.DummyHttpClient.DummyRequestHandler;
import proxyrest.dummy.repository.DummyUserRepository;

public class TestAPIInvocationHandler {
	
	@Test
	public void queryParamListReturn() {
		var api = createTestAPI((request, repository) -> repository.findByOccupation(request.queryParameters.get("occupation")));
		
		List<String> users = api.getUsersByOccupationWithQueryParam("Software Engineer").stream()
												.map(user -> user.name())
													.collect(Collectors.toList());
		assertTrue(users.contains("Amanda"));
		assertTrue(users.contains("Will"));
		assertFalse(users.contains("Lauren"));
	}
	
	@Test
	public void queryParamSingleReturn() {
		var api = createTestAPI((request, repository) -> repository.findById(request.queryParameters.get("userId")));
		assertEquals(api.getUserWithQueryParam("2").name(), "Lauren");
		assertEquals(api.getUserWithQueryParam("5").name(), "John");
		assertNotEquals(api.getUserWithQueryParam("7").name(), "Yasmin");
	}
	
	@Test
	public void pathParamListReturn() {
		var api = createTestAPI((request, repository) -> repository.findByOccupation(request.paths.get(1)));
		List<String> users = api.getUsersByOccupationWithPath("Software Engineer").stream()
														.map(user -> user.name())
															.collect(Collectors.toList());
		assertTrue(users.contains("Amanda"));
		assertTrue(users.contains("Will"));
		assertFalse(users.contains("Lauren"));
	}
	
	@Test
	public void pathParamSingleReturn() {
		var api = createTestAPI((request, repository) -> repository.findById(request.paths.get(1)));
		assertEquals(api.getUserWithPath("6").name(), "Will");
	}
	
	@Test
	public void echoRequestBodyContent() {
		var api = createTestAPI((request, repository) -> request.getBodyAsString());
		
		final DummyReturnMessage message = api.postMessageWithRequestBody(new DummyMessage("Ok", "200"));
		assertEquals(message.message(), "Ok");
	}
	
	@Test
	public void echoRequestListReturn() {
		var api = createTestAPI((request, repository) -> request.getBodyAsString());
		
		final List<DummyReturnMessage> messages = api.postMessagesWithRequestBody(Arrays.asList(new DummyMessage("Ok", "200"), new DummyMessage("Warn", "200")));
		final List<String> texts = messages.stream()
												.map(message -> message.message())
													.collect(Collectors.toList());

		assertTrue(texts.contains("Ok"));
		assertTrue(texts.contains("Warn"));
	}
	
	@Endpoint(value = "http://nonexistent.endpoint.com")
	public static interface DummyTestAPI extends RestAPI {

		@GetMapping(mapping = "/users")
		public User getUserWithPath(@PathParam String userId);

		@GetMapping(mapping = "/occupation")
		public List<User> getUsersByOccupationWithPath(@PathParam String occupation);
		
		@GetMapping(mapping = "/users")
		public User getUserWithQueryParam(@QueryParam(name = "userId") String userId);
		
		@GetMapping(mapping = "/users")
		public List<User> getUsersByOccupationWithQueryParam(@QueryParam(name = "occupation") String occupation);
		
		@Route(mapping = "/message", method = "POST")
		public DummyReturnMessage postMessageWithRequestBody(@RequestBody(contentType = "application/json") DummyMessage message);
		
		@Route(mapping = "/messages", method = "POST")
		public List<DummyReturnMessage> postMessagesWithRequestBody(@RequestBody(contentType = "application/json") List<DummyMessage> message);
		
	}
	
	private static DummyTestAPI createTestAPI(DummyRequestHandler<DummyUserRepository> handler) {
		DummyUserRepository repository = new DummyUserRepository();
		repository.save("2", "Lauren", "Product Manager");
		repository.save("3", "Amanda", "Software Engineer");
		repository.save("4", "Jack", "UX Designer");
		repository.save("5", "John", "QA Analyst");
		repository.save("6", "Will", "Software Engineer");
		repository.save("7", "Lilian", "Software Manager");
		
		var httpClient = new DummyHttpClient<>(repository, handler);
		var api = RestAPI.createAPI(DummyTestAPI.class, httpClient);
		return api;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static record User(String name, String occupation) {
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static record DummyReturnMessage(String message) {
	}
	
	public static record DummyMessage(String message, String status) {
	}
	
}
