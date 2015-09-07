package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PathParam;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

class Handler {
	static final String NAME_ENTITY = "<request-entity>";

	private Object webService;
	private Method method;

	private List<String> parameterOrder;
	private Map<String, Type> parameterNameToType;

	public Handler(Object webService, Method method) {
		this.webService = checkNotNull(webService);
		this.method = checkNotNull(method);
		compileParameters(method.getAnnotatedParameterTypes());
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
		return method.invoke(webService, params);
	}

	private void compileParameters(AnnotatedType[] annotatedParameterTypes) {
		ImmutableList.Builder<String> parameterOrderBuilder = ImmutableList.builder();
		ImmutableMap.Builder<String, Type> nameToTypeBuilder = ImmutableMap.builder();

		for (AnnotatedType annotatedParameter : annotatedParameterTypes) {
			PathParam pathParam = annotatedParameter.getAnnotation(PathParam.class);
			String name;
			Type type;
			if (pathParam != null) {
				name = pathParam.value();
				type = annotatedParameter.getType();
			} else {
				name = NAME_ENTITY;
				type = annotatedParameter.getType();
			}
			parameterOrderBuilder.add(name);
			nameToTypeBuilder.put(name, type);
		}
		parameterOrder = parameterOrderBuilder.build();
		parameterNameToType = nameToTypeBuilder.build();
	}

}
