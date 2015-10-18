package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application.Parameters;
import javafx.scene.paint.Color;

class Constants {

	public static final int DEFAULT_HEIGHT = 1024;
	public static final int DEFAULT_WIDTH = 1024;
	public static final Color DEFAULT_FILL_COLOUR = Color.web("#666970");
	public static final Object PARAM_DEBUG_UI = "-XdebugUI";
	public static final Object PARAM_EXTERNAL_UI = "-XexternalUI";

	public static boolean isDebugUi(Parameters parameters) {
		return parameters.getUnnamed().contains(Constants.PARAM_DEBUG_UI);
	}

	public static boolean isExternalUi(Parameters parameters) {
		return parameters.getUnnamed().contains(Constants.PARAM_EXTERNAL_UI);
	}

	public static String webViewLocation(Parameters parameters) {
		if (isExternalUi(parameters)) {
			return "http://localhost:9000";
		}
		URL selfUri = checkNotNull(Constants.class.getResource(Constants.class.getSimpleName() + ".class"));
		String protocol = selfUri.getProtocol();
		if ("file".equals(protocol)) {
			String path = selfUri.getPath();
			checkState(path.contains("target/classes"));
			String rootPath = path.substring(0, path.indexOf("target/classes"));

			File file = new File(rootPath);

			String localPath = file.getParentFile().getPath() + "/greensopinion.finance.ui/dist/index.html";

			checkState(new File(localPath).exists(), "%s", localPath);

			return "file://" + localPath;
		} else if ("jar".equals(protocol)) {
			String path = selfUri.getPath();
			Matcher matcher = Pattern.compile("(.*?/)([^/]+\\.jar!).*").matcher(path);
			checkState(matcher.matches(), "%s", path);

			String prefix = matcher.group(1);
			return prefix + "web-assets/index.html";
		} else {
			throw new IllegalStateException(selfUri.toString());
		}
	}
}
