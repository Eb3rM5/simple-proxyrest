package proxyrest.dummy.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import proxyrest.dummy.repository.DummyUserRepository.DummyUser;

public class DummyUserRepository implements DummyRepository<DummyUser> {

	private final List<DummyUser> users;
	
	public DummyUserRepository() {
		users = new ArrayList<>();
	}
	
	@Override
	public DummyUser findById(String id) {
		return users.stream()
					.filter(user -> user.id != null && user.id.equals(id))
					.findFirst()
					.orElse(null);
	}
	
	public List<DummyUser> findByOccupation(String occupation) {
		return users.stream()
				.filter(user -> user.occupation != null && user.occupation.equals(occupation))
				.collect(Collectors.toList());
	}
	
	public void save(String id, String user, String occupation) {
		save(new DummyUser(id, user, occupation));
	}
	
	@Override
	public void save(DummyUser user) {
		users.add(user);
	}
	
	public static record DummyUser(String id, String name, String occupation) {
	}
	
}
