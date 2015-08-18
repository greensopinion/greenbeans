package greensopinion.finance.services;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebApplicationRegion extends Region {

	private final WebView webView = new WebView();
	private final WebEngine webEngine = webView.getEngine();

	public WebApplicationRegion() {
		webEngine.load(Constants.webViewLocation());
		getChildren().add(webView);
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(webView, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return Constants.DEFAULT_WIDTH;
	}

	@Override
	protected double computePrefHeight(double width) {
		return Constants.DEFAULT_HEIGHT;
	}
}
