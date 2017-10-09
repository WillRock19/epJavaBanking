package redes.serverSide;

import redes.User;
import redes.json.AuthenticationResponseMessage;

public class Authenticator {
	private boolean isAuthenticated;
	private dbManager dbManager;

	public Authenticator(dbManager dbManager) {
		isAuthenticated = false;
		this.dbManager = dbManager;
	}

	public AuthenticationResponseMessage AuthenticateUser(User user) {
		if (isAuthenticated(user)) {
			isAuthenticated = true;
			return new AuthenticationResponseMessage().CreateServerAuthenticationSuccess();
		} else {
			isAuthenticated = false;
			return new AuthenticationResponseMessage().CreateServerAuthenticationError();
		}
	}

	public boolean wasAlreadyAuthenticated() {
		return isAuthenticated;
	}

	private boolean isAuthenticated(User user) {
		return dbManager.UserDataExistsInDataBase(user);
	}
}
