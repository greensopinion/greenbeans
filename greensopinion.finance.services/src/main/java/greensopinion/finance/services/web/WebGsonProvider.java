package greensopinion.finance.services.web;

import java.util.Date;

import javax.inject.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.persistence.DateTypeAdapter;

class WebGsonProvider implements Provider<Gson> {

	@Override
	public Gson get() {
		GsonBuilder gsonBuilder = builder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		return gsonBuilder.create();
	}

	GsonBuilder builder() {
		return new GsonBuilder().disableHtmlEscaping();
	}
}
