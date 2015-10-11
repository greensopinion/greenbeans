package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.persistence.ConfigurationService;
import greensopinion.finance.services.persistence.TransactionsPersistenceService;

public class TransactionsService extends ConfigurationService<Transactions> {

	private final CategorizerService categorizerService;

	@Inject
	TransactionsService(TransactionsPersistenceService persistenceService, CategorizerService categorizerService,
			EntityEventSupport eventSupport) {
		super(persistenceService, eventSupport);
		this.categorizerService = checkNotNull(categorizerService);
		eventSupport.addListener(new EntityListener<Categories>(Categories.class) {

			@Override
			public void updated(Categories entity) {
				clearState();
			}
		});
	}

	@Override
	protected Transactions load() {
		return applyCategories(super.load());
	}

	@Override
	public void update(Transactions value) {
		Transactions newValue = applyCategories(value);
		super.update(newValue);
	}

	private Transactions applyCategories(Transactions transactions) {
		return new Transactions(categorizerService.categorize(transactions.getTransactions()));
	}
}
