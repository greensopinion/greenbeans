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
package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;

import greensopinion.finance.services.ValidationException;
import greensopinion.finance.services.web.GsonWebRenderer;

public class Invoker {

	private final GsonWebRenderer webRenderer;
	private final Logger logger;

	@Inject
	public Invoker(GsonWebRenderer webRenderer, Logger logger) {
		this.webRenderer = checkNotNull(webRenderer);
		this.logger = checkNotNull(logger);
	}

	public WebResponse invoke(WebRequest request, MatchResult match, Handler handler) {
		try {
			Map<String, Object> parameters = createParameters(request, match, handler);
			Object value = handler.invoke(parameters);
			ResponseBuilder builder = value == null ? Response.noContent() : Response.ok(value);
			return toWebResponse(builder.build());
		} catch (Exception e) {
			logInvocationException(e);
			return toWebResponse(e);
		}
	}

	protected void logInvocationException(Exception e) {
		Level level = Level.SEVERE;
		if (ValidationException.class.isAssignableFrom(e.getClass())) {
			level = Level.FINE;
		}
		logger.log(level, MoreObjects.firstNonNull(e.getMessage(), e.getClass().getSimpleName()), e);
	}

	WebResponse toWebResponse(Exception e) {
		return toWebResponse(webRenderer.toResponse(e));
	}

	WebResponse toWebResponse(Response response) {
		Object entity = response.getEntity();
		String entityString = null;
		if (entity != null) {
			ByteArrayOutputStream entityStream = new ByteArrayOutputStream();
			try {
				webRenderer.writeTo(entity, entity.getClass(), entity.getClass(), null, null, null, entityStream);
				entityString = entityStream.toString(StandardCharsets.UTF_8.name());
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		}
		return new WebResponse(response.getStatus(), entityString);
	}

	Map<String, Object> createParameters(WebRequest request, MatchResult match, Handler handler) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		for (Map.Entry<String, Type> parameter : handler.getParameterNameToType().entrySet()) {
			String name = parameter.getKey();
			Object value;
			if (Handler.NAME_ENTITY.equals(name)) {
				value = request.getEntity();
			} else {
				value = match.getVariables().get(name);
			}
			parameters.put(name, convert(parameter.getValue(), value));
		}
		return parameters;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	Object convert(Type type, Object value) throws Exception {
		if (type == Integer.class) {
			String v = value.toString();
			return v.isEmpty() ? null : Integer.parseInt(v);
		} else if (type == Long.class) {
			String v = value.toString();
			return v.isEmpty() ? null : Long.parseLong(v);
		} else if (type == String.class) {
			String v = value.toString().trim();
			return v.isEmpty() ? null : v;
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
