package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;

class MasterPasswordChangeSupport implements EncryptorListener {

	private final CategoriesService categoriesService;
	private final TransactionsService transactionsService;
	private Transactions transactions;
	private Categories categories;

	@Inject
	MasterPasswordChangeSupport(TransactionsService transactionsService, CategoriesService categoriesService) {
		this.transactionsService = checkNotNull(transactionsService);
		this.categoriesService = checkNotNull(categoriesService);
	}

	@Override
	public void aboutToChangeEncryptor() {
		transactions = transactionsService.retrieve();
		categories = categoriesService.retrieve();
	}

	@Override
	public void encryptorChanged() {
		checkNotNull(transactions);
		checkNotNull(categories);
		transactionsService.update(transactions);
		transactions = null;
		categoriesService.update(categories);
		categories = null;
	}
}
