package greensopinion.finance.services.application;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	private final ServiceLocator serviceLocator = new ServiceLocator();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Title Placeholder");

		Scene scene = createMainScene();
		primaryStage.setScene(scene);
		setSizeAndLocation(primaryStage);
		primaryStage.show();
	}

	private void setSizeAndLocation(Stage primaryStage) {
		Screen primaryScreen = Screen.getPrimary();
		Rectangle2D visualBounds = primaryScreen.getVisualBounds();

		primaryStage.setWidth(Constants.DEFAULT_WIDTH);
		primaryStage.setHeight(Math.min(Constants.DEFAULT_HEIGHT, visualBounds.getHeight()));
	}

	private Scene createMainScene() {
		return new Scene(new WebApplicationRegion(serviceLocator, isDebugUi()), Constants.DEFAULT_WIDTH,
				Constants.DEFAULT_HEIGHT, Constants.DEFAULT_FILL_COLOUR);
	}

	private boolean isDebugUi() {
		return getParameters().getUnnamed().contains(Constants.PARAM_DEBUG_UI);
	}

	public static final void main(String[] args) {
		launch(args);
	}
}
