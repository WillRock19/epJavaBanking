package redes.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import redes.RequestStream;
import redes.clientSide.ClientRequestProcessor;

public class WebServer 
{
	private ServerSocket serverSocket;
	private dbGenerator dbGenerator; 

	private String dbName = "dbBankingTest";
	private int serverPort;
	
	
	public WebServer(int serverPort) 
	{
		this.serverPort = serverPort;
		
		CreateDataBase();
		PrepareDataBase();
		
		CreateServerSocket();
	}
	
	public void Execute() throws Exception
	{
		try 
		{
			while(true) 
			{	
				Socket socket = serverSocket.accept();
				
				RequestProcessor requestProcessor = new RequestProcessor(socket, dbGenerator);
				
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
	
	private void CreateDataBase() 
	{
		dbGenerator = new dbGenerator(dbName);
	}
	
	private void PrepareDataBase() 
	{
		dbGenerator.OpenDataBaseConnection();
		dbGenerator.CreateTablesIfNotExists();
		dbGenerator.PopulateTableIfEmpty();
	}
}
