package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

import greensopinion.finance.services.web.GsonWebRenderer;

public class Invoker {

	private final GsonWebRenderer webRenderer;

	@Inject
	public Invoker(GsonWebRenderer webRenderer) {
		this.webRenderer = checkNotNull(webRenderer);
	}

	public WebResponse invoke(WebRequest request, MatchResult match, Handler handler) {
		try {
			Map<String, Object> parameters = createParameters(request, match, handler);
			Object value = handler.invoke(parameters);
			ResponseBuilder builder = value == null ? Response.noContent() : Response.ok(value);
			return toWebResponse(builder.build());
		} catch (Exception e) {
			return toWebResponse(webRenderer.toResponse(e));
		}
	}

	WebResponse toWebResponse(Response response) {
		Object entity = response.getEntity();
		String entityString = null;
		ByteArrayOutputStream entityStream = new ByteArrayOutputStream();
		try {
			webRenderer.writeTo(entity, entity.getClass(), entity.getClass(), null, null, null, entityStream);
			entityString = entityStream.toString(StandardCharsets.UTF_8.name());
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		return new WebResponse(response.getStatus(), entityString);
	}

	private Map<String, Object> createParameters(WebRequest request, MatchResult match, Handler handler)
			throws Exception {
		ImmutableMap.Builder<String, Object> parametersBuilder = ImmutableMap.builder();
		for (Map.Entry<String, Type> parameter : handler.getParameterNameToType().entrySet()) {
			String name = parameter.getKey();
			Object value;
			if (Handler.NAME_ENTITY.equals(name)) {
				value = request.getEntity();
			} else {
				value = match.getVariables().get(name);
			}
			parametersBuilder.put(name, convert(parameter.getValue(), value));
		}
		return parametersBuilder.build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object convert(Type type, Object value) throws Exception {
		if (type == Integer.class) {
			String v = value.toString();
			return v.isEmpty() ? null : Integer.parseInt(v);
		} else if (type == Long.class) {
			String v = value.toString();
			return v.isEmpty() ? null : Long.parseLong(v);
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(value.toString().getBytes(StandardCharsets.UTF_8));
		Class clazz = classOf(type);
		return webRenderer.readFrom(clazz, type, null, null, null, stream);
	}

	private Class<?> classOf(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			return classOf(pt.getRawType());
		}
		throw new IllegalArgumentException(format("{0} type {1}", type.getTypeName(), type.getClass().getSimpleName()));
	}

}
