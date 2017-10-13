package redes.clientSide;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

import redes.SSLConnection;

public class ClientServer {
	private Socket socket;
	private Scanner scanner;

	public ClientServer(String clientHost, int clientPort) {
		createSSLSocket(clientHost, clientPort);
	}

	public void Execute() {
		try {
			printInConsoleSocketIPAndPort();
			scanner = new Scanner(System.in);

			ClientProcessor clientProcessor = new ClientProcessor(socket, scanner);

			Thread thread = new Thread(clientProcessor);
			thread.start();
		} 
		catch (Exception e) {
			System.err.println("Um erro ocorreu no servidor do cliente. Encerrando aplicacao...");
			System.exit(0);
		}
	}

	private void createSSLSocket(String clientHost, int clientPort) {
		try {
			InetAddress adress = InetAddress.getLocalHost();
			socket = getSSLSocket("client_trust_store_file", "senha@123", adress, clientPort);
			//socket = new Socket(clientHost, clientPort);
		} 
		catch (IOException e) {
			System.err.println("O seguinte erro ocorreu ao criar o Socket TCP do cliente: " + e.getMessage());
			System.exit(0);
		}
		catch(GeneralSecurityException e) {
			System.err.println("O sistema não conseguiu criar uma conexao SSL segura. O seguinte erro ocorreu: " + e.getMessage());
			System.exit(0);
		}
	}
	
	 private static SSLSocket getSSLSocket(String keyStoreFile, String keyStoreFilePassword, InetAddress serverAddress, int serverPort) throws GeneralSecurityException, IOException 
	 {        
	        SSLContext sslContext = SSLConnection.createSSLContext(keyStoreFile, keyStoreFilePassword);
	        SSLSocket sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(serverAddress, serverPort);
	        sslSocket.startHandshake();
	        
	        return sslSocket;
	}

	private void printInConsoleSocketIPAndPort() {
		System.out.println("\n*****DADOS DE CONEXAO DO CLIENTE: *****\n");

		try {
			InetAddress iAddress = InetAddress.getLocalHost();
			String server_IP = iAddress.getHostAddress();

			System.out.println("\t 1.Server IP address  : " + server_IP);
			System.out.println("\t 2.Server PORT number : " + socket.getLocalPort());
		} 
		catch (UnknownHostException e) {
			System.err.println("Erro ao recuperar IP do servidor.");
			System.exit(0);
		}
	}
}
