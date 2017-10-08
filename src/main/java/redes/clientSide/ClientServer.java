package redes.clientSide;

import java.io.IOException;
import java.net.Socket;

import redes.RequestStream;

public class ClientServer 
{
	private RequestStream stream;
	private Socket socket;
	
	public ClientServer(String clientHost, int clientPort) 
	{
		CreateSocket(clientHost, clientPort);
		CreateRequestStream(socket);
	}
	
	public void Execute() 
	{
		try 
		{	
			while(true) 
			{			
				ClientProcessor clientProcessor = new ClientProcessor(stream);
				
				Thread thread = new Thread(clientProcessor);
				thread.start(); 
			}
		}
		catch(Exception e) 
		{
			System.err.println("Um erro ocorreu no servidor do cliente. Encerrando aplicacao...");
			System.exit(0);
		}
	}
	
	private void CreateSocket(String clientHost, int clientPort) 
	{
		try{
			socket = new Socket(clientHost, clientPort);
		}
		catch(IOException e)
		{
			System.err.println("O seguinte erro ocorreu ao criar o Socket TCP do cliente: " + e.getMessage());
		}
	}
	
	private void CreateRequestStream(Socket socket) 
	{
		try {
			stream = new RequestStream(socket);
		}
		catch(Exception e) {
			System.err.println("O seguinte erro ocorreu ao criar um RequestStream: " + e.getMessage());
		}
	}
}
