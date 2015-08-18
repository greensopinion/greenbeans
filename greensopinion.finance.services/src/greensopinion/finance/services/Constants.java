package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.net.URL;

import javafx.scene.paint.Color;

class Constants {

	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_WIDTH = 800;
	public static final Color DEFAULT_FILL_COLOUR = Color.web("#666970");

	public static String webViewLocation() {
		URL selfUri = checkNotNull(Constants.class.getResource("Constants.class"));
		String protocol = selfUri.getProtocol();
		checkState("file".equals(protocol), "%s", protocol);

		String path = selfUri.getPath();
		checkState(path.contains("target/classes"));
		String rootPath = path.substring(0, path.indexOf("target/classes"));

		File file = new File(rootPath);

		String localPath = file.getParentFile().getPath() + "/greensopinion.finance.ui/dist/index.html";

		checkState(new File(localPath).exists(), "%s", localPath);

		return "file://" + localPath;
	}
}
