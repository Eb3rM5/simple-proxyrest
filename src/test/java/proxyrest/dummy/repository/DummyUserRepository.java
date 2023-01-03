package proxyrest.dummy.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<DummyUser> findByOccupation(String occupation) {
		return users.stream()
				.filter(user -> user.occupation != null && user.occupation.equals(occupation))
				.collect(Collectors.toList());
	}
	
	public void save(int id, String user, String occupation) {
		save(new DummyUser(id, user, occupation));
	}
	
	public void save(DummyUser user) {
		users.add(user);
	}
	
	public static record DummyUser(int id, String name, String occupation) {
	}
	
}
