package redes.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import redes.RequestStream;
import redes.clientSide.ClientRequestProcessor;

public class WebServer 
{
	private RequestStream stream;
	private ServerSocket serverSocket;
	private Socket socket;
	private int serverPort;
	
	public WebServer(int serverPort) 
	{
		this.serverPort = serverPort;
		CreateServerSocket();
	}
	
	public void Execute() throws Exception
	{
		while(true) 
		{	
			socket = serverSocket.accept();
			CreateRequestStream(socket);
			
			
			

			Thread thread = new Thread();
			

			thread.start(); 
		}	
	} 
	
	private void CreateServerSocket() 
	{
		try{
			serverSocket = new ServerSocket(serverPort);
		}
		catch(IOException e) {
			System.err.println("O seguinte erro ocorreu ao criar o Socket TCP do servidor: " + e.getMessage());
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
