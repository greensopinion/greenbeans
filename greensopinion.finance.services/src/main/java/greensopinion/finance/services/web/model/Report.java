package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Report {
	private final String title;

	protected Report(String title) {
		this.title = checkNotNull(title);
	}

	public String getTitle() {
		return title;
	}
}
