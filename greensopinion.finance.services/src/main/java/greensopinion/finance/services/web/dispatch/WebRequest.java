package greensopinion.finance.services.web.dispatch;

import com.google.common.base.MoreObjects;

public class WebRequest {
	private final String httpMethod;
	private final String path;
	private final String entity;

	public WebRequest(String httpMethod, String path, String entity) {
		this.httpMethod = httpMethod;
		this.path = path;
		this.entity = entity;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public String getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(WebRequest.class).add("httpMethod", httpMethod).add("path", path).toString();
	}
}
