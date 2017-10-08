package redes.serverSide;

import java.io.IOException;
import java.net.Socket;

import redes.Action;
import redes.Message;
import redes.RequestStream;
import redes.messages.ServerResponseMessage;

public class RequestProcessor implements Runnable
{
	private String dbName = "dbBankingTest";
	private Socket socket;
	private RequestStream stream;
	private dbManager dbManager;
	private Authenticator authenticator;
	
	public RequestProcessor(Socket socket, dbManager dbManager) throws Exception
	{
		this.dbManager = dbManager;
		this.socket = socket;
		this.stream = new RequestStream(socket);
		
		authenticator = new Authenticator(this.dbManager);
	}
	
	@Override
	public void run() 
	{
		try {
			processRequest();
		}
		catch(Exception e) 
		{
			System.err.println("O seguinte erro ocorreu ao processar sua requisição: ");
			System.err.println(e.getMessage());
		}
	}
	
	private void processRequest() throws Exception
	{
		Message message = GetMessageFromStream();
		
		if(message != null) 
		{
			//Chamar classes responsaveis pela acao e responder ao usuario
			switch(message.getAction())
			{
				case AUTHENTICATE:
					ServerResponseMessage response = authenticator.AuthenticateUser(message.getUser());
					stream.SendToConnection(response.toJson());
					break;
				
				case EXTRACT:
					
					break;
					
				case LIST:
					
					break;
					
				case TRANSFER:
					
					break;
					
				default:
					System.out.println("Servidor nao reconhece a acao desejada pelo cliente.");
					break;
			}
		}
		
		CloseSocketAndDataStream();
	}
	
	private void CloseSocketAndDataStream() throws Exception
	{
		stream.CloseOutputAndInputFromServerStream();
		socket.close();
	} 

	private Message GetMessageFromStream()
	{
		try {
			String json = stream.GetInputFromConnection();
			return Message.fromJSON(json);			
		}
		catch(Exception e) {
			System.err.println("Nao foi possivel extrair a mensagem da conexao TCP.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
			
			return null;
		}
	}
}
