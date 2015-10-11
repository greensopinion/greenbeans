package greensopinion.finance.services.web.model;

public class CategoryRuleModel {
	private final String matchDescription;

	public CategoryRuleModel(String matchDescription) {
		this.matchDescription = matchDescription;
	}

	public String getMatchDescription() {
		return matchDescription;
	}
}
