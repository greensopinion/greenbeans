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
package com.greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.greensopinion.finance.services.AboutService;
import com.greensopinion.finance.services.web.model.About;

@Path(AboutWebService.BASE_PATH)
public class AboutWebService {

	private static final String PATH_CURRENT = "current";

	public static final String BASE_PATH = "/abouts";

	private final AboutService service;

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
