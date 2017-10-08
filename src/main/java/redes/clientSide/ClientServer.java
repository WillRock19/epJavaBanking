package redes.clientSide;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import redes.RequestStream;

public class ClientServer 
{
	private RequestStream stream;
	private Socket socket;
	private Scanner scanner;
	
	public ClientServer(String clientHost, int clientPort) 
	{
		CreateSocket(clientHost, clientPort);
	}
	
	public void Execute() 
	{
		try 
		{	
			printInConsoleSocketIPAndPort();
			scanner = new Scanner(System.in);

			ClientProcessor clientProcessor = new ClientProcessor(socket, scanner);
			
			Thread thread = new Thread(clientProcessor);
			thread.start(); 
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
	
	private void printInConsoleSocketIPAndPort()
	{
		System.out.println("\n*****DADOS DE CONEXAO DO CLIENTE: *****");
		
		try {
	        InetAddress iAddress = InetAddress.getLocalHost();
	        String server_IP = iAddress.getHostAddress();
	        
	        System.out.println("\t 1.Server IP address  : " + server_IP);
	        System.out.println("\t 2.Server PORT number : " + socket.getLocalPort());
	    } 
		catch (UnknownHostException e) 
		{
			System.err.println("Erro ao recuperar IP do servidor.");
			System.exit(0);;
	    }
	}
}
