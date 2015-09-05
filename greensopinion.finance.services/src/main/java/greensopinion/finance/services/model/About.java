package greensopinion.finance.services.model;

public class About {
	private String applicationName;
	private String copyrightNotice;

	public About() {
	}

	public About(String applicationName, String copyrightNotice) {
		this.applicationName = applicationName;
		this.copyrightNotice = copyrightNotice;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getCopyrightNotice() {
		return copyrightNotice;
	}
}
