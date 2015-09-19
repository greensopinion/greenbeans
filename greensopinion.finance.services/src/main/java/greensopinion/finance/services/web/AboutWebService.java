package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.AboutService;
import greensopinion.finance.services.web.model.About;

@Path(AboutWebService.BASE_PATH)
public class AboutWebService {

	private static final String PATH_CURRENT = "current";

	public static final String BASE_PATH = "/abouts";

	private AboutService service;

	@Inject
	public AboutWebService(AboutService service) {
		this.service = checkNotNull(service);
	}

	@Path(PATH_CURRENT)
	@GET
	public About get() {
		return service.getAbout();
	}
}
