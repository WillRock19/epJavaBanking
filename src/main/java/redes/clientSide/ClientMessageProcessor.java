package redes.clientSide;

import redes.RequestStream;

public class ClientMessageProcessor 
{
	private RequestStream stream;
	
	public ClientMessageProcessor(RequestStream stream) 
	{
		this.stream = stream;
	}
}