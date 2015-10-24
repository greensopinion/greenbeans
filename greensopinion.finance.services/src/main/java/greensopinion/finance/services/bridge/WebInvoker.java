package greensopinion.finance.services.bridge;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.web.dispatch.WebDispatch;
import greensopinion.finance.services.web.dispatch.WebRequest;
import greensopinion.finance.services.web.dispatch.WebResponse;

public class WebInvoker {

	private final WebDispatch dispatch;

	@Inject
	public WebInvoker(WebDispatch dispatch) {
		this.dispatch = checkNotNull(dispatch);
	}

	public WebResponse invoke(String httpMethod, String path, String entity) {
		return dispatch.dispatch(new WebRequest(httpMethod, path, entity));
	}
}
