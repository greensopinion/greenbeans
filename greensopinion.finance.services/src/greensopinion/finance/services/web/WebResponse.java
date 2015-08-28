package greensopinion.finance.services.web;

public class WebResponse {

	private int responseCode;
	private String entity;

	public WebResponse(int responseCode, String entity) {
		this.responseCode = responseCode;
		this.entity = entity;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getEntity() {
		return entity;
	}
}
