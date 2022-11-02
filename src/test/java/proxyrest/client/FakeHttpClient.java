package proxyrest.client;

public class FakeHttpClient implements AbstractHttpClient<FakeHttpRequest, FakeHttpResponse>{
	
	@Override
	public FakeHttpResponse request(FakeHttpRequest request) {
		return null;
	}

	@Override
	public FakeHttpRequest createEmptyRequest() {
		return new FakeHttpRequest();
	}

}
