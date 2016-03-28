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
package com.greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;

public class ExceptionContent {
	private final String errorCode;
	private final String message;

	public ExceptionContent(Throwable e) {
		this.errorCode = e.getClass().getSimpleName();
		this.message = getMessage(e);
	}

	private String getMessage(Throwable e) {
		Optional<Throwable> firstMatch = FluentIterable.from(Throwables.getCausalChain(e))
				.firstMatch(new Predicate<Throwable>() {
					@Override
					public boolean apply(Throwable throwable) {
						Throwable cause = throwable.getCause();
						String message = throwable.getMessage();
						return !Strings.isNullOrEmpty(message) && (cause == null || !message.equals(cause.toString()));
					}
				});
		if (firstMatch.isPresent()) {
			return firstMatch.get().getMessage();
		}
		Iterable<String> exceptionNames = FluentIterable.from(Throwables.getCausalChain(e))
				.transform(new Function<Throwable, String>() {

					@Override
					public String apply(Throwable input) {
						return checkNotNull(input).getClass().getSimpleName();
					}
				});
		return format("Unexpected exception: {0}", Joiner.on(": ").join(exceptionNames));
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
}
