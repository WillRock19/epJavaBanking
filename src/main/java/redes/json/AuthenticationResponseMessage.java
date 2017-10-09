package redes.json;

public class AuthenticationResponseMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8691142675989194680L;

	private boolean userAuthenticated;

	public AuthenticationResponseMessage CreateServerAuthenticationError() {
		setUserAuthenticated(false);
		return this;
	}

	public AuthenticationResponseMessage CreateServerAuthenticationSuccess() {
		setUserAuthenticated(true);
		return this;
	}

	public boolean isUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserAuthenticated(boolean userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}
}
