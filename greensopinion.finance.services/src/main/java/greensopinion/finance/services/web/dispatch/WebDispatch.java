package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;

import greensopinion.finance.services.web.model.ExceptionContent;

public class WebDispatch {

	private static final List<Class<? extends Annotation>> webMethodAnnotations = ImmutableList
			.<Class<? extends Annotation>> of(GET.class, PUT.class, DELETE.class, POST.class);

	private final Map<WebPath, Handler> pathToHandler;

	private final Invoker invoker;

	private final Logger logger;

	@Inject
	public WebDispatch(Injector injector, Invoker invoker, Logger logger) {
		this.invoker = checkNotNull(invoker);
		pathToHandler = createPathToHandler(checkNotNull(injector));
		this.logger = checkNotNull(logger);
	}

	public WebResponse dispatch(WebRequest request) {
		try {
			for (Entry<WebPath, Handler> handlerEntry : pathToHandler.entrySet()) {
				WebPath webPath = handlerEntry.getKey();
				MatchResult match = webPath.match(request.getHttpMethod(), request.getPath());
				if (match.matches()) {
					return invoker.invoke(request, match, handlerEntry.getValue());
				}
			}
			return notFound(request);
		} catch (Exception e) { // fault barrier
			logger.log(Level.SEVERE, format("Unexpected exception: {0}", e.getMessage()), e);
			return invoker.toWebResponse(e);
		}
	}

	private WebResponse notFound(WebRequest request) {
		ExceptionContent entity = new ExceptionContent(new NotFoundException(request));
		return invoker.toWebResponse(Response.status(Status.NOT_FOUND).entity(entity).build());
	}

	private Map<WebPath, Handler> createPathToHandler(Injector injector) {
		ImmutableMap.Builder<WebPath, Handler> builder = ImmutableMap.builder();

		Map<Key<?>, Binding<?>> bindings = injector.getBindings();
		for (Entry<Key<?>, Binding<?>> entry : bindings.entrySet()) {
			Key<?> key = entry.getKey();
			if (isWebService(key)) {
				introspectWebService(builder, entry.getValue());
			}
		}

		return builder.build();
	}

	private void introspectWebService(Builder<WebPath, Handler> builder, Binding<?> binding) {
		Object webService = binding.getProvider().get();
		String basePath = webService.getClass().getAnnotation(Path.class).value();

		for (Method method : webService.getClass().getDeclaredMethods()) {
			for (Class<? extends Annotation> webMethodAnnotation : webMethodAnnotations) {
				Object annotation = method.getAnnotation(webMethodAnnotation);
				if (annotation != null) {
					introspectWebServiceMethod(builder, webMethodAnnotation.getSimpleName(), basePath, webService,
							method);
				}
			}
		}
	}

	private void introspectWebServiceMethod(Builder<WebPath, Handler> builder, String httpMethod, String basePath,
			Object webService, Method method) {
		String path = basePath;
		Path methodPath = method.getAnnotation(Path.class);
		if (methodPath != null) {
			path = Joiner.on("/").join(Arrays.asList(basePath, methodPath.value()));
		}
		path = CharMatcher.is('/').collapseFrom(path, '/');

		WebPath webPath = new WebPath(httpMethod, path);
		builder.put(webPath, new Handler(webService, method));

	}

	private boolean isWebService(Key<?> key) {
		return key.getTypeLiteral().getRawType().getAnnotation(Path.class) != null;
	}
}
