package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import javafx.scene.Scene;

public class MainScene extends Scene {
	private final WebApplicationRegion region;

	@Inject
	MainScene(WebApplicationRegion region) {
		super(region, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, Constants.DEFAULT_FILL_COLOUR);
		this.region = checkNotNull(region);
	}

	public void initialize() {
		region.initialize();
	}
}
