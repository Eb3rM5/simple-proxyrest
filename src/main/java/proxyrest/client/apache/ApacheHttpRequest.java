package proxyrest.client.apache;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;

import proxyrest.client.AbstractHttpRequest;
import proxyrest.handler.ResponseHandler;
import proxyrest.handler.request.RequestBodyHandler;

public class ApacheHttpRequest implements AbstractHttpRequest<ClassicHttpRequest>{

	private String method;
	private ResponseHandler responseHandler;
	private URIBuilder uriBuilder;
	private Object content;
	private String contentType;
	private final List<Header> headers;
	
	private RequestBodyHandler requestBodyHandler;
	
	ApacheHttpRequest() {
		this.headers = new ArrayList<>();
	}
	
	@Override
	public void setURL(String url) {
		try {
			uriBuilder = new URIBuilder(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void addPath(String path) {
		if (uriBuilder == null) {
			setURL(path);
		} else {
			uriBuilder.appendPath(path);
		}
	}

	@Override
	public void addQueryParameter(String key, String value) {
		uriBuilder.addParameter(key, value);
	}

	@Override
	public void addHeader(String key, Object value) {
		headers.add(new BasicHeader(key, value));
	}
	
	@Override
	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	@Override
	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}
	
	@Override
	public void setContent(Object content) {
		this.content = content;
	}
	
	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public Object getContent() {
		return content;
	}
	
	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
		this.requestBodyHandler = requestBodyHandler;
	}
	
	@Override
	public ClassicHttpRequest build() {
		try {
			var builder = ClassicRequestBuilder.create(method);
			builder.setUri(uriBuilder.build());
			if (!headers.isEmpty()) {
				builder.setHeaders(headers.iterator());
			}
			if (requestBodyHandler != null) {
				builder.setEntity(HttpEntities.create(
										output -> requestBodyHandler.handleRequestBody(this, content, output),
										ContentType.parse(contentType)
										));
			}
			return builder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}


}
