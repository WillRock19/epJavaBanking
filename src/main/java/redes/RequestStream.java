package redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import redes.messages.ServerResponseMessage;

public class RequestStream 
{
	private BufferedReader inputFromConnection;
	private DataOutputStream outputToConnection;
	
	public RequestStream(Socket socket) throws Exception
	{
		defineInputStream(socket);
		defineOutputStream(socket);
	}
	
	public String GetInputFromConnection() throws IOException
	{
		return inputFromConnection.readLine();
	}
	
	public void SendToConnection(String message) throws Exception
	{
		outputToConnection.writeBytes(message);
	}
	
	public void CloseOutputAndInputFromServerStream() throws Exception
	{
		outputToConnection.close();
		inputFromConnection.close();
	}
	
	private void defineInputStream(Socket socket) 
	{
		try {
			inputFromConnection = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
		}
		catch(IOException e) 
		{
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para receber dados do socket TCP.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}
	
	private void defineOutputStream(Socket socket) 
	{
		try {
			outputToConnection = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException e) 
		{
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para enviar dados do socket TCP.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}

}
