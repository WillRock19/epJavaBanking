package redes.clientSide;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import redes.Action;
import redes.ActionMessage;
import redes.PasswordManager;
import redes.RequestStream;
import redes.User;
import redes.json.AuthenticationResponseMessage;
import redes.json.Message;

public class ClientProcessor implements Runnable {
	private boolean userIsLogged;
	private RequestStream stream;
	private PasswordManager passwordManager;
	private Scanner scanner;

	public ClientProcessor(Socket socket, Scanner scanner) {
		stream = new RequestStream(socket);
		userIsLogged = false;
		passwordManager = new PasswordManager();

		this.scanner = scanner;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.err.println("ERRO AO PROCESSAR REQUISIÇÃO: " + e.getMessage());
		} finally {
			stream.CloseOutputAndInputFromServerStream();
		}
	}

	private void processRequest() {
		while (true) {
			if (!userIsLogged)
				tryLogin();
			else {
				try {
					stream.GetInputFromConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void tryLogin() {
		User userData = getUserCredentials();
		ActionMessage message = new ActionMessage();

		message.setUser(userData);
		message.setAction(Action.AUTHENTICATE);

		String serverResponse = null;

		try {
			stream.SendToConnection(message.toJson());
			serverResponse = stream.GetInputFromConnection();
		} catch (Exception e) {
			System.out.println("NÃO FOI POSSÍVEL AUTENTICAR USUÁRIO: " + e.getMessage());
		}

		AuthenticationResponseMessage responseMessage = (AuthenticationResponseMessage) Message
				.fromJSON(serverResponse);

		userIsLogged = responseMessage.isUserAuthenticated();

		if (userIsLogged) {
			System.out.println("Usuário autenticado com êxito.");
		} else {
			System.out.println("Usuário/senha inválidos.");
		}
	}

	private User getUserCredentials() {
		System.out.println("Favor, insira os dados abaixo para logar no sistema.");

		System.out.println("Numero da conta : ");
		String accountId = scanner.nextLine();

		System.out.println("Senha do cliente: ");
		String password = scanner.nextLine();

		return new User(accountId, passwordManager.EncodePassword(password));
	}
}