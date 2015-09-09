package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import com.google.common.io.Resources;

import greensopinion.finance.services.bridge.ConsoleBridge;
import javafx.application.Application.Parameters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jersey.repackaged.com.google.common.base.Throwables;
import netscape.javascript.JSObject;

class WebApplicationRegion extends Region {

	private static final String JS_MEMBER_NAME_APP_SERVICE_LOCATOR = "appServiceLocator";
	private static final String JS_MEMBER_NAME_CONSOLE_BRIDGE = "consoleBridge";

	private WebView webView;
	private WebEngine webEngine;
	private final ServiceLocator serviceLocator;
	private final Parameters parameters;

	@Inject
	WebApplicationRegion(ServiceLocator serviceLocator, Parameters parameters) {
		this.serviceLocator = checkNotNull(serviceLocator);
		this.parameters = checkNotNull(parameters);
	}

	public void initialize() {
		checkState(webView == null);
		webView = new WebView();
		webEngine = webView.getEngine();
		installConsoleBridge();
		installServiceLocator(serviceLocator);
		webEngine.load(Constants.webViewLocation());
		if (isDebugUi(parameters)) {
			installFirebugLite();
		}
		getChildren().add(webView);
	}

	private boolean isDebugUi(Parameters parameters) {
		return parameters.getUnnamed().contains(Constants.PARAM_DEBUG_UI);
	}

	private void installFirebugLite() {
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (State.SUCCEEDED.equals(newValue)) {
					webEngine.executeScript(installFirebugLiteScript());
				}
			}

			private String installFirebugLiteScript() {
				try {
					return Resources.toString(WebApplicationRegion.class.getResource("install-firebug-lite.js"),
							StandardCharsets.UTF_8);
				} catch (IOException e) {
					throw Throwables.propagate(e);
				}
			}
		});
	}

	private void installServiceLocator(ServiceLocator serviceLocator) {
		JSObject windowObject = getJsWindow();
		windowObject.setMember(JS_MEMBER_NAME_APP_SERVICE_LOCATOR, serviceLocator);
	}

	private void installConsoleBridge() {
		JSObject windowObject = getJsWindow();
		windowObject.setMember(JS_MEMBER_NAME_CONSOLE_BRIDGE, new ConsoleBridge());
		webEngine.executeScript("console.log = function(message) {\nconsoleBridge.log(message);\n};");
	}

	private JSObject getJsWindow() {
		return (JSObject) webEngine.executeScript("window");
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
