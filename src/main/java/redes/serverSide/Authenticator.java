package redes.serverSide;

import redes.User;
import redes.messages.ServerResponseMessage;

public class Authenticator 
{
	private boolean isAuthenticated;
	private dbManager dbManager;
	
	public Authenticator(dbManager dbManager) 
	{
		isAuthenticated = false;
		this.dbManager = dbManager;
	}
	
	public ServerResponseMessage AuthenticateUser(User user) 
	{
		if(isAuthenticated(user)) 
		{
			isAuthenticated = true;
			return new ServerResponseMessage().CreateServerAuthenticationSuccess();					
		}
		else 
		{
			isAuthenticated = false;
			return new ServerResponseMessage().CreateServerAuthenticationError();	
		}
	}
	
	public boolean wasAlreadyAuthenticated() 
	{
		return isAuthenticated;
	}
	
	private boolean isAuthenticated(User user) 
	{
		return dbManager.UserDataExistsInDataBase(user);
	}
}
