package greensopinion.finance.services.domain;

import javax.inject.Inject;

import greensopinion.finance.services.persistence.ConfigurationService;
import greensopinion.finance.services.persistence.TransactionsPersistenceService;

public class TransactionsService extends ConfigurationService<Transactions> {

	@Inject
	TransactionsService(TransactionsPersistenceService persistenceService) {
		super(persistenceService);
	}
}
