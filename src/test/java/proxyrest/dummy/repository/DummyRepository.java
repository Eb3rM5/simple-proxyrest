package proxyrest.dummy.repository;

public interface DummyRepository<T> {

	T findById(String id);
	
	void save(T object);
	
}
