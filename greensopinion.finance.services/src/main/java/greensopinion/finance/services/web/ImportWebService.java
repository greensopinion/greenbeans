package greensopinion.finance.services.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.ImportFilesService;

@Path(ImportWebService.BASE_PATH)
public class ImportWebService {
	static final String BASE_PATH = "/imports";

	static final String PATH_NEW = "new";

	private final ImportFilesService importFilesService;

	@Inject
	ImportWebService(ImportFilesService importFilesService) {
		this.importFilesService = importFilesService;
	}

	@Path(PATH_NEW)
	@GET
	public void importFiles() {
		importFilesService.importFiles();
	}
}
