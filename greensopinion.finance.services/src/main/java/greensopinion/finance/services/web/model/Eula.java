package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Eula {

	private final String text;

	public Eula(String text) {
		this.text = checkNotNull(text);
	}

	public String getText() {
		return text;
	}
}
