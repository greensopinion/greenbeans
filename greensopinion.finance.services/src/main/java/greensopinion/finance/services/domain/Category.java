package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

public class Category {
	private final String name;
	private final List<MatchRule> matchRules;

	public Category(String name) {
		this(name, ImmutableList.of());
	}

	public Category(String name, List<MatchRule> matchRules) {
		this.name = checkNotNull(name);
		this.matchRules = ImmutableList.copyOf(checkNotNull(matchRules));
	}

	public String getName() {
		return name;
	}

	public List<MatchRule> getMatchRules() {
		return MoreObjects.firstNonNull(matchRules, ImmutableList.<MatchRule> of());
	}

	public boolean matches(Transaction transaction) {
		checkNotNull(transaction);
		for (MatchRule matchRule : getMatchRules()) {
			if (matchRule.matches(transaction)) {
				return true;
			}
		}
		return false;
	}

	public Category withMatchRule(MatchRule rule) {
		checkNotNull(rule);
		ImmutableList.Builder<MatchRule> newRules = ImmutableList.builder();
		newRules.addAll(getMatchRules());
		newRules.add(rule);
		return new Category(name, newRules.build());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(Category.class).add("name", name).toString();
	}
}
