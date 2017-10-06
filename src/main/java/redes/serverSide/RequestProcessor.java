package redes.serverSide;

import java.net.Socket;

import redes.Message;
import redes.RequestStream;

public class RequestProcessor implements Runnable
{
	private String dbName = "bankingTest";
	private Socket socket;
	private RequestStream stream;
	private dbGenerator dbGenerator;
	
	public RequestProcessor(Socket socket) throws Exception
	{
		this.socket = socket;
		this.stream = new RequestStream(socket);
		this.dbGenerator = new dbGenerator(dbName);
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
		dbGenerator.OpenDataBaseConnection();
		dbGenerator.CreateTables();
		
		Message message = GetMessageFromStream();
		
		//Checar se a mensagem está no formato certo
		
		//Verificar qual a acao que usuario quer realizar
		
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
