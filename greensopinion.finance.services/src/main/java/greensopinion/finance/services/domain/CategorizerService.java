package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;

public class CategorizerService {
	private final CategoriesService categoriesService;

	@Inject
	CategorizerService(CategoriesService categoriesService) {
		this.categoriesService = checkNotNull(categoriesService);
	}

	public List<Transaction> categorize(List<Transaction> transactions) {
		checkNotNull(transactions);
		ImmutableList.Builder<Transaction> builder = ImmutableList.builder();
		for (Transaction transaction : transactions) {
			builder.add(categorize(transaction));
		}
		return builder.build();
	}

	private Transaction categorize(Transaction transaction) {
		Categories categories = categoriesService.retrieve();
		for (Category category : categories.getCategories()) {
			if (category.matches(transaction)) {
				return transaction.withCategory(category);
			}
		}
		return transaction;
	}
}
