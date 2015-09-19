package greensopinion.finance.services.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import greensopinion.finance.services.ImportFilesService;
import greensopinion.finance.services.web.model.FileImport;
import greensopinion.finance.services.web.model.FileSelection;

@Path(ImportFilesWebService.BASE_PATH)
public class ImportFilesWebService {
	static final String BASE_PATH = "/imports";

	static final String PATH_SELECTED = "selected";

	private final ImportFilesService importFilesService;

	@Inject
	ImportFilesWebService(ImportFilesService importFilesService) {
		this.importFilesService = importFilesService;
	}

	@Path(PATH_SELECTED)
	@GET
	public FileSelection selectedFiles() {
		return new FileSelection(importFilesService.selectedFiles());
	}

	@POST
	public void importFiles(FileImport fileImport) {
		importFilesService.importFiles(fileImport.getFiles(), fileImport.isDeleteAfterImport());
	}
}
