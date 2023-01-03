package proxyrest.client;

import java.util.ArrayList;
import java.util.List;

public class DummyUserRepository {

	private final List<DummyUser> users;
	
	public DummyUserRepository() {
		users = new ArrayList<>();
	}
	
	public DummyUser findById(int id) {
		return users.stream()
					.filter(user -> user.id == id)
					.findFirst()
					.orElse(null);
	}
	
	public void save(DummyUser user) {
		users.add(user);
	}
	
	public static record DummyUser(int id, String name, String occupation) {
	}
	
}
