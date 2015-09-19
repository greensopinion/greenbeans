package greensopinion.finance.services.domain;

import javax.inject.Inject;

import greensopinion.finance.services.persistence.CategoriesPersistenceService;
import greensopinion.finance.services.persistence.ConfigurationService;

public class CategoriesService extends ConfigurationService<Categories> {

	@Inject
	CategoriesService(CategoriesPersistenceService persistenceService) {
		super(persistenceService);
	}
}
