package greensopinion.finance.services.data;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class DataGsonProvider {
	public Gson get() {
		GsonBuilder gsonBuilder = builder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		return gsonBuilder.create();
	}

	GsonBuilder builder() {
		return new GsonBuilder().disableHtmlEscaping();
	}
}
