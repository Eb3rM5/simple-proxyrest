package proxyrest.client.apache;

import java.io.IOException;

import org.apache.hc.client5.http.classic.HttpClient;

import proxyrest.client.AbstractHttpClient;

public class ApacheHttpClient implements AbstractHttpClient<ApacheHttpRequest, ApacheHttpResponse>{

	private final HttpClient httpClient;
	
	public ApacheHttpClient(final HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public ApacheHttpResponse request(ApacheHttpRequest request) {
		try {
			final var response = httpClient.execute(request.build());
			return new ApacheHttpResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ApacheHttpRequest createEmptyRequest() {
		return new ApacheHttpRequest();
	}
	
}
