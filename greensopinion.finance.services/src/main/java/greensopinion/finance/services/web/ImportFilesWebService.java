/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
