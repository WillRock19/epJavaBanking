package redes.clientSide;

import java.net.Socket;
import java.util.Scanner;

import redes.Action;
import redes.Message;
import redes.RequestStream;
import redes.User;

public class ClientProcessor implements Runnable
{
	private Socket socket;
	private RequestStream stream;
	private boolean userIsLogged;
	private Scanner scanner;
	
	public ClientProcessor(Socket socket)
	{
		stream = new RequestStream(socket);
		scanner = new Scanner(System.in);
		userIsLogged = false;
		
		this.socket = socket;
	}

	@Override
	public void run() 
	{
		try{
			processRequest();
		} 
		catch (Exception e)
		{
			System.err.println("O seguinte erro ocorreu ao processar sua requisição: " + e.getMessage());
		}
	}
	
	private void processRequest() throws Exception
	{
		if(!userIsLogged) 
			makeUserLogin();
		else 
		{
			
		}
			
		closeSocketAndDataStream();
		closeScanner();
	}
	
	private void makeUserLogin() throws Exception
	{
		User userData = getUserCredentials();
		Message message = new Message();
		
		message.setUser(userData);
		message.setAction(Action.AUTHENTICATE);
		
		stream.SendToConnection(message.toJson());
	}
	
	private User getUserCredentials() 
	{
		System.out.println("Favor, insira os dados abaixo para logar no sistema.");
		
		System.out.println("Numero da conta : ");
		String accountId = scanner.nextLine();
		
		System.out.println("Senha do cliente: ");
		String password = scanner.nextLine();
		
		return new User(accountId, password);
	}
	
	private void closeSocketAndDataStream() throws Exception
	{
		stream.CloseOutputAndInputFromServerStream();
		socket.close();
	} 
	
	private void closeScanner() 
	{
		scanner.close();
	}
}