package greensopinion.finance.services.web;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;

public class ExceptionContent {
	private String errorCode;
	private String message;

	public ExceptionContent(Throwable e) {
		this.errorCode = e.getClass().getSimpleName();
		this.message = getMessage(e);
	}

	private String getMessage(Throwable e) {
		Optional<Throwable> firstMatch = FluentIterable.from(Throwables.getCausalChain(e))
				.firstMatch(new Predicate<Throwable>() {
					@Override
					public boolean apply(Throwable throwable) {
						return !Strings.isNullOrEmpty(throwable.getMessage());
					}
				});
		if (firstMatch.isPresent()) {
			return firstMatch.get().getMessage();
		}
		return "Unexpected exception";
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
}
