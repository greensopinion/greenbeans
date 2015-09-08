package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.PathParam;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import jersey.repackaged.com.google.common.base.Throwables;

class Handler {
	static final String NAME_ENTITY = "<request-entity>";

	private final Object webService;
	private final Method method;

	private List<String> parameterOrder;
	private Map<String, Type> parameterNameToType;

	public Handler(Object webService, Method method) {
		this.webService = checkNotNull(webService);
		this.method = checkNotNull(method);
		compileParameters(method.getAnnotatedParameterTypes(), method.getParameterAnnotations());
	}

	public Map<String, Type> getParameterNameToType() {
		return parameterNameToType;
	}

	public Object invoke(Map<String, Object> parameters) throws Exception {
		Object[] params = new Object[parameterOrder.size()];
		for (int x = 0; x < parameterOrder.size(); ++x) {
			String parameterName = parameterOrder.get(x);
			params[x] = parameters.get(parameterName);
		}
		try {
			return method.invoke(webService, params);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			logInvocationError(cause);
			throw Throwables.propagate(cause);
		}
	}

	private void logInvocationError(Throwable cause) {
		Logger.getLogger(method.getDeclaringClass().getName()).log(Level.SEVERE,
				format("exception: {0}", cause.getMessage()), cause);
	}

	private void compileParameters(AnnotatedType[] annotatedParameterTypes, Annotation[][] annotations) {
		ImmutableList.Builder<String> parameterOrderBuilder = ImmutableList.builder();
		ImmutableMap.Builder<String, Type> nameToTypeBuilder = ImmutableMap.builder();

		for (int x = 0; x < annotatedParameterTypes.length; ++x) {
			AnnotatedType annotatedType = annotatedParameterTypes[x];
			PathParam pathParam = getPathParam(annotations[x]);
			String name;
			Type type;
			if (pathParam != null) {
				name = pathParam.value();
				type = annotatedType.getType();
			} else {
				name = NAME_ENTITY;
				type = annotatedType.getType();
			}
			parameterOrderBuilder.add(name);
			nameToTypeBuilder.put(name, type);
		}
		parameterOrder = parameterOrderBuilder.build();
		parameterNameToType = nameToTypeBuilder.build();
	}

	private PathParam getPathParam(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (PathParam.class == annotation.annotationType()) {
				return (PathParam) annotation;
			}
		}
		return null;
	}

}
