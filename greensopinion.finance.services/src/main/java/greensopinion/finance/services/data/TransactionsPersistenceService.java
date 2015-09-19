package greensopinion.finance.services.data;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Transactions;

public class TransactionsPersistenceService extends PersistenceService<Transactions> {

	private static final String FILENAME = "transactions.json";

	@Inject
	TransactionsPersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		super(gsonProvider, dataDirectoryLocator, Transactions.class);
	}

	@Override
	String getFilename() {
		return FILENAME;
	}

	@Override
	Transactions defaultInstance() {
		return new Transactions();
	}

}
