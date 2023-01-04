package proxyrest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import proxyrest.advice.api.Endpoint;
import proxyrest.advice.param.QueryParam;
import proxyrest.advice.route.GetMapping;
import proxyrest.dummy.client.DummyHttpClient;
import proxyrest.dummy.client.DummyHttpClient.DummyRequestHandler;
import proxyrest.dummy.repository.DummyUserRepository;

public class TestAPIInvocationHandler {
	
	@Test
	public void testQueryParamAnnotationWhenReturningAList() {
		var api = createTestAPI((request, repository) -> repository.findByOccupation(request.getQueryParameters().get("occupation")));
		
		List<String> users = api.getUsersByOccupation("Software Engineer").stream()
												.map(user -> user.name())
													.collect(Collectors.toList());
		assertTrue(users.contains("Amanda"));
		assertTrue(users.contains("Will"));
		assertFalse(users.contains("Lauren"));
	}
	
	@Test
	public void testQueryParamAnnotation() {
		var api = createTestAPI((request, repository) -> repository.findById(request.getQueryParameters().get("userId")));
		assertEquals(api.getUser("2").name(), "Lauren");
		assertEquals(api.getUser("5").name(), "John");
		assertNotEquals(api.getUser("7").name(), "Yasmin");
	}
	
	@Endpoint(value = "http://nonexistent.endpoint.com")
	public static interface DummyTestAPI extends RestAPI {

		@GetMapping(mapping = "/users")
		public User getUser(@QueryParam(name = "userId") String userId);
		
		@GetMapping(mapping = "/users")
		public List<User> getUsersByOccupation(@QueryParam(name = "occupation") String occupation);
		
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
	
}
