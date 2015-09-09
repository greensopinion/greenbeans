package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ImportFilesService {

	private final Window window;

	@Inject
	ImportFilesService(Window window) {
		this.window = checkNotNull(window);
	}

	public List<String> selectedFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(initialDirectory());
		fileChooser.setTitle("Select Files to Import");
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("CSV files (*.csv)", ImmutableList.of("*.csv")));
		List<File> files = fileChooser.showOpenMultipleDialog(window);
		if (files == null) {
			return ImmutableList.of();
		}
		return FluentIterable.from(files).transform(new Function<File, String>() {

			@Override
			public String apply(File input) {
				return checkNotNull(input).getPath();
			}
		}).toSortedList(Ordering.natural());
	}

	private File initialDirectory() {
		File file = new File(System.getProperty("user.home"), "Downloads");
		if (file.exists() && file.isDirectory()) {
			return file;
		}
		return new File(System.getProperty("user.home"));
	}
}
