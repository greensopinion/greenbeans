package greensopinion.finance.services.data;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Transactions;

public class TransactionsService extends ConfigurationService<Transactions> {

	@Inject
	TransactionsService(TransactionsPersistenceService persistenceService) {
		super(persistenceService);
	}

}
