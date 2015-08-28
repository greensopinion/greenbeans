package greensopinion.finance.services;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private ServiceLocator serviceLocator = new ServiceLocator();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Title Placeholder");

		Scene scene = createMainScene();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Scene createMainScene() {
		return new Scene(new WebApplicationRegion(serviceLocator), Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT,
				Constants.DEFAULT_FILL_COLOUR);
	}

	public static final void main(String[] args) {
		launch(args);
	}
}
