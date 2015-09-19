package greensopinion.finance.services.web.model;

import java.util.List;

public class FileSelection {
	private final List<String> files;

	public FileSelection(List<String> files) {
		this.files = files;
	}

	public List<String> getFiles() {
		return files;
	}
}
