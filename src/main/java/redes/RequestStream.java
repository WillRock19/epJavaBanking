package redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestStream 
{
	private BufferedReader inputFromServer;
	private DataOutputStream outputToServer;
	
	public RequestStream(Socket socket) throws Exception
	{
		defineInputFromServerStream(socket);
	
		defineOutputToServerStream(socket);
	}
	
	public String GetInputFromServer() throws Exception
	{
		return inputFromServer.readLine();
	}
	
	private void defineInputFromServerStream(Socket socket) 
	{
		try {
			inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
		}
		catch(IOException e) 
		{
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para receber dados do servidor");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}
	
	private void defineOutputToServerStream(Socket socket) 
	{
		try {
			outputToServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException e) 
		{
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para enviar dados do servidor");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}

	public void closeOutputAndInputFromServerStream() throws Exception
	{
		outputToServer.close();
		inputFromServer.close();
	}
}
