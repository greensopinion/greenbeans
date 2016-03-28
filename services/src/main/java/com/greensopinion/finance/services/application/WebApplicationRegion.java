/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import javax.inject.Inject;

import com.greensopinion.finance.services.bridge.ConsoleBridge;

import javafx.application.Application.Parameters;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

class WebApplicationRegion extends Region {

	private static final String JS_MEMBER_NAME_APP_SERVICE_LOCATOR = "appServiceLocator";
	private static final String JS_MEMBER_NAME_CONSOLE_BRIDGE = "consoleBridge";

	private WebView webView;
	private WebEngine webEngine;
	private final ServiceLocator serviceLocator;
	private final Parameters parameters;
	private final ConsoleBridge consoleBridge;

	@Inject
	WebApplicationRegion(ServiceLocator serviceLocator, Parameters parameters, ConsoleBridge consoleBridge) {
		this.serviceLocator = checkNotNull(serviceLocator);
		this.parameters = checkNotNull(parameters);
		this.consoleBridge = checkNotNull(consoleBridge);
	}

	public void initialize() {
		checkState(webView == null);
		webView = new WebView();
		webView.setContextMenuEnabled(false);
		webEngine = webView.getEngine();
		installConsoleBridge();
		installServiceLocator(serviceLocator);
		webEngine.load(Constants.webViewLocation(parameters));

		getChildren().add(webView);
	}

	private void installServiceLocator(ServiceLocator serviceLocator) {
		JSObject windowObject = getJsWindow();
		windowObject.setMember(JS_MEMBER_NAME_APP_SERVICE_LOCATOR, serviceLocator);
	}

	private void installConsoleBridge() {
		JSObject windowObject = getJsWindow();
		windowObject.setMember(JS_MEMBER_NAME_CONSOLE_BRIDGE, consoleBridge);
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
