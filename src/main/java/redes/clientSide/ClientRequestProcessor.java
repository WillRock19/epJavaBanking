package redes.clientSide;

import redes.RequestStream;

public class ClientRequestProcessor implements Runnable
{
	private RequestStream stream;
	
	public ClientRequestProcessor(RequestStream stream) 
	{
		this.stream = stream;
	}

	@Override
	public void run() 
	{
		try{
			processRequest();
		} 
		catch (Exception e)
		{
			System.out.println("The following error has occured while processing the request: " + e.getMessage());
			System.out.println(e);
		}
	}
	
	private void processRequest() throws Exception
	{
		
	}
}