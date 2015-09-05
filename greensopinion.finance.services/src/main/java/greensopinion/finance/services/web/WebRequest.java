package greensopinion.finance.services.web;

public class WebRequest {
	private String httpMethod;
	private String path;
	private String entity;

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
}
