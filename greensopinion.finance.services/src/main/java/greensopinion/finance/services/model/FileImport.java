package greensopinion.finance.services.model;

import java.util.List;

public class FileImport {
	private boolean deleteAfterImport;
	private List<String> files;

	public List<String> getFiles() {
		return files;
	}

	public boolean isDeleteAfterImport() {
		return deleteAfterImport;
	}
}
