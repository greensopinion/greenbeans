/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validate;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;

import greensopinion.finance.services.GreenBeans;
import greensopinion.finance.services.domain.Settings;
import greensopinion.finance.services.domain.SettingsService;
import greensopinion.finance.services.web.model.Eula;
import greensopinion.finance.services.web.model.UserEulaStatus;

@Path(EulaWebService.BASE_PATH)
public class EulaWebService {
	static final String BASE_PATH = "/eula";
	static final String CURRENT_EULA = "current";
	static final String CURRENT = "user-agreements/current";

	private final SettingsService settingsService;

	@Inject
	EulaWebService(SettingsService settingsService) {
		this.settingsService = checkNotNull(settingsService);
	}

	@Path(CURRENT_EULA)
	@GET
	public Eula current() {
		try {
			return new Eula(Resources.toString(Resources.getResource(EulaWebService.class, "model/eula.html"),
					StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	@Path(CURRENT)
	@GET
	public UserEulaStatus currentUserEulaStatus() {
		return new UserEulaStatus(settingsService.retrieve().userHasAgreedToLicense());
	}

	@Path(CURRENT)
	@PUT
	public void currentUserEulaStatus(UserEulaStatus newStatus) {
		validateNotNull(newStatus, "Must provide a EulaStatus.");
		validate(newStatus.userHasAgreedToLicense(),
				format("You must agree to the license in order to use {0}.  Either agree to the license or exit the application.",
						GreenBeans.APP_NAME));

		Settings settings = settingsService.retrieve();
		settingsService.update(settings.withUserHasAgreedToLicense(newStatus.userHasAgreedToLicense()));
	}
}
