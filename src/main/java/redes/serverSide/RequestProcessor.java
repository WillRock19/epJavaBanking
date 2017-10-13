package redes.serverSide;

import java.net.Socket;

import redes.ActionMessage;
import redes.RequestStream;
import redes.json.AuthenticationResponseMessage;
import redes.json.Message;

public class RequestProcessor implements Runnable {

	private RequestStream stream;
	private dbManager dbManager;
	private Authenticator authenticator;

	public RequestProcessor(Socket socket, dbManager dbManager) {
		this.dbManager = dbManager;
		this.stream = new RequestStream(socket);

		authenticator = new Authenticator(this.dbManager);
	}

	@Override
	public void run() {
		try {
			while (true) {
				ActionMessage message = GetMessageFromStream();
				if (message == null)
					break;

				processRequest(message);
			}

		} catch (Exception e) {
			System.err.println("O seguinte erro ocorreu ao processar sua requisição: " + e.getMessage());
		} finally {
			stream.CloseOutputAndInputFromServerStream();
		}
	}

	private void processRequest(ActionMessage message) throws Exception {

		if (message != null) {
			// Chamar classes responsaveis pela acao e responder ao usuario
			switch (message.getAction()) {
			case AUTHENTICATE:
				AuthenticationResponseMessage response = authenticator.AuthenticateUser(message.getUser());
				stream.SendToConnection(response.toJson());
				break;

			case DEPOSIT:
				
				break;

			case WITHDRAWL:

				break;

			case FINANCIAL_TRANSACTIONS:

				break;

			default:
				System.out.println("Servidor nao reconhece a acao desejada pelo cliente.");
				break;
			}
		}

		//
	}

	private ActionMessage GetMessageFromStream() {
		try {
			String json = stream.GetInputFromConnection();
			
			System.out.println("-------------------------------------------");
			System.out.println("A mensagem recebida foi: " + json);
			
			Message t = Message.fromJSON(json);
			
			return (ActionMessage) t;
			
		} catch (Exception e) {
			System.err.println("ERRO AO RECEBER RESPOSTA DO CLIENTE: " + e.getMessage());

			return null;
		}
	}
}
