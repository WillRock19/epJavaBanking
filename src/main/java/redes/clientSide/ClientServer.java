package redes.clientSide;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import redes.RequestStream;

public class ClientServer 
{
	private ClientRequestProcessor messageProcessor;
	private RequestStream stream;
	private Socket socket;
	
	public ClientServer(String clientHost, int clientPort) 
	{
		CreateSocket(clientHost, clientPort);
		CreateRequestStream(socket);
		
		messageProcessor = new ClientRequestProcessor(stream);
	}
	
	public void ExecuteTcpConnection() 
	{
		while(true) 
		{
			
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
