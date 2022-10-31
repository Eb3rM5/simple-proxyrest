package proxyrest.client.apache;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ProtocolException;

import proxyrest.client.AbstractHttpResponse;

public class ApacheHttpResponse implements AbstractHttpResponse<HttpResponse> {

	private final HttpResponse clientResponse;
	
	public ApacheHttpResponse(final HttpResponse clientResponse) {
		this.clientResponse = clientResponse;
	}
	
	@Override
	public int getStatusCode() {
		return clientResponse.getCode();
	}

	@Override
	public String getContentType() {
		try {
			Header header = clientResponse.getHeader("Content-Type");
			if (header != null) {
				return header.getValue();
			}
			return null;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public InputStream openStream() {
		if (clientResponse instanceof ClassicHttpResponse classicHttpResponse) {
			try {
				HttpEntity entity = classicHttpResponse.getEntity();
				return entity != null ? entity.getContent() : null;
			} catch (UnsupportedOperationException | IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Override
	public HttpResponse getClientResponse() {
		return clientResponse;
	}
	
}
