package redes.serverSide;

import java.net.Socket;

import redes.Action;
import redes.Message;
import redes.RequestStream;

public class RequestProcessor implements Runnable
{
	private String dbName = "dbBankingTest";
	private Socket socket;
	private RequestStream stream;
	private dbManager dbManager;
	
	public RequestProcessor(Socket socket, dbManager dbManager) throws Exception
	{
		this.dbManager = dbManager;
		this.socket = socket;
		this.stream = new RequestStream(socket);
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
		
		//Checar se a mensagem está no formato certo
		
		switch(message.getAction())
		{
			case AUTHENTICATE:
				
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
		
		//Chamar classes responsaveis pela acao e responder ao usuario
		
		CloseSocketAndDataStream();
	}
	
	private void CloseSocketAndDataStream() throws Exception
	{
		stream.closeOutputAndInputFromServerStream();
		socket.close();
	} 

	private Message GetMessageFromStream() throws Exception
	{
		String json = stream.GetInputFromServer();
		return Message.fromJSON(json);
	}
}
