package redes.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer 
{
	private ServerSocket serverSocket;
	private dbManager dbManager; 

	private String dbName = "dbBankingTest";
	private int serverPort;
	
	
	public WebServer(int serverPort) 
	{
		this.serverPort = serverPort;
		CreateServerSocket();
		
		dbManager = new dbManager(dbName);
		dbManager.CreateAndPopulateDataBase();
	}
	
	public void Execute() throws Exception
	{
		try 
		{
			while(true) 
			{	
				Socket socket = serverSocket.accept();
				
				RequestProcessor requestProcessor = new RequestProcessor(socket, dbManager);
				
				Thread thread = new Thread(requestProcessor);
				thread.start(); 
			}
		}
		catch(Exception e) {
			System.err.println("VISHE");
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
}
