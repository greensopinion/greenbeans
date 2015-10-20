package greensopinion.finance.services.web.model;

public class UserEulaStatus {

	private final boolean userHasAgreedToLicense;

	public UserEulaStatus(boolean userHasAgreedToLicense) {
		this.userHasAgreedToLicense = userHasAgreedToLicense;
	}

	public boolean userHasAgreedToLicense() {
		return userHasAgreedToLicense;
	}
}
