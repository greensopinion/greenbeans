package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonWebRenderer
		implements MessageBodyReader<Object>, MessageBodyWriter<Object>, ExceptionMapper<Exception> {

	private Gson gson;

	@Inject
	public GsonWebRenderer(Gson gson) {
		this.gson = checkNotNull(gson);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {
		Writer writer = new OutputStreamWriter(entityStream, StandardCharsets.UTF_8);
		gson.toJson(object, writer);
		writer.flush();
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
					throws IOException, WebApplicationException {
		InputStreamReader reader = new InputStreamReader(entityStream, StandardCharsets.UTF_8);
		try {
			return gson.fromJson(reader, type);
		} catch (JsonSyntaxException e) {
			throw new WebApplicationException(
					Response.status(Status.BAD_REQUEST).entity(new ExceptionContent(e)).build());
		}
	}

	@Override
	public Response toResponse(Exception exception) {
		int statusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		ExceptionContent exceptionContent;
		if (exception instanceof WebApplicationException) {
			WebApplicationException applicationException = (WebApplicationException) exception;
			statusCode = applicationException.getResponse().getStatus();
			Object entity = applicationException.getResponse().getEntity();
			if (entity instanceof ExceptionContent) {
				exceptionContent = (ExceptionContent) entity;
			} else {
				exceptionContent = new ExceptionContent(applicationException.getCause() == null ? applicationException
						: applicationException.getCause());
			}
		} else {
			exceptionContent = new ExceptionContent(exception);
		}
		return Response.status(statusCode).entity(exceptionContent).build();
	}

}
