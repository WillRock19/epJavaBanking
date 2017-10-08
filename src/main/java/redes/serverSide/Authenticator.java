package redes.serverSide;

import redes.Message;
import redes.User;

public class Authenticator 
{
	private dbGenerator dbGenerator;
	private Message message;
	
	public Authenticator(dbGenerator dbGenerator, Message message) 
	{
		this.dbGenerator = dbGenerator;
		this.message = message;
	}
	
	public void Authenticate() 
	{
		
		
	}
	
	private boolean isAuthenticathed() 
	{
		User user = message.getUser();
		
		
		
		return true;
	}
}
