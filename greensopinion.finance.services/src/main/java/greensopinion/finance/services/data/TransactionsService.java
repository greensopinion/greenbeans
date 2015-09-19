package greensopinion.finance.services.data;

import javax.inject.Inject;

public class TransactionsService extends ConfigurationService<Transactions> {

	@Inject
	TransactionsService(TransactionsPersistenceService persistenceService) {
		super(persistenceService);
	}

}
