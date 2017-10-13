package redes.serverSide;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import redes.SSLConnection;

public class WebServer {
	private ServerSocket serverSocket;
	private dbManager dbManager;

	private String dbName = "dbBankingTest";
	private int serverPort;

	public WebServer(int serverPort) {
		this.serverPort = serverPort;
		CreateServerSocket();

		dbManager = new dbManager(dbName);
		dbManager.CreateAndPopulateDataBase();
	}

	public void Execute() throws Exception {
		try {
			printInConsoleSocketIPAndPort();
			System.out.println("Aguardando mensagens do cliente...");

			while (true) {
				Socket socket = serverSocket.accept();

				RequestProcessor requestProcessor = new RequestProcessor(socket, dbManager);

				Thread thread = new Thread(requestProcessor);
				thread.start();
			}
		} catch (Exception e) {
			System.err.println("VISHE");
		}
	}

	private void CreateServerSocket() {
		try {
			serverSocket = getSSLServerSocket("server_key_store_file", "senha@123", serverPort);
			//serverSocket = new ServerSocket(serverPort);
			
		} 
		catch (IOException e) {
			System.err.println("O seguinte erro ocorreu ao criar o Socket TCP do servidor: " + e.getMessage());
			System.err.println("Encerrando aplicação...");
			System.exit(0);
		}
		catch(GeneralSecurityException e) {
			System.err.println("O servidor nao foi capaz de criar uma conexao SSL segura.O seguinte erro ocorreu: " + e.getMessage());
			System.err.println("Encerrando aplicação...");
			System.exit(0);
		}
	}
	
	static ServerSocket getSSLServerSocket(String keyStoreFile, String keyStoreFilePassword, int port) throws GeneralSecurityException, IOException 
    {
        SSLContext sslContext = SSLConnection.createSSLContext(keyStoreFile, keyStoreFilePassword);
        
        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        
        return sslServerSocket;
}

	private void printInConsoleSocketIPAndPort() {
		System.out.println("\n*****DADOS DE CONEXAO DO SERVIDOR: *****\n");

		try {
			InetAddress iAddress = InetAddress.getLocalHost();
			String server_IP = iAddress.getHostAddress();

			System.out.println("\t\t 1.Server IP address  : " + server_IP);
			System.out.println("\t\t 2.Server PORT number : " + serverPort);
		} 
		catch (UnknownHostException e) {
			System.err.println("Erro ao recuperar IP do servidor.");
			System.exit(0);
		}
	}
}
