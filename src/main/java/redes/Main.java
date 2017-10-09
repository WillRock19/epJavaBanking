package redes;

import redes.clientSide.ClientServer;
import redes.serverSide.WebServer;

public class Main {
	public static void main(String[] args) {
		final int portNumber = 6789;

		if (args.length == 0) {
			executeAsServer(portNumber);
		} else if (args.length == 1) {
			executeAsClient(args[0], 6789);
		} else {
			System.out.println("MODO DE EXECUÇÃO INVÁLIDO.");
			System.exit(-3);
		}
	}

	private static void executeAsServer(int portNumber) {
		try {
			WebServer server = new WebServer(portNumber);
			server.Execute();
		} catch (Exception e) {
			System.err.println("ERRO AO EXECUTAR SERVIDOR: " + e.getMessage());
			System.exit(-1);
		}
	}

	private static void executeAsClient(String serverIP, int portNumber) {
		try {
			ClientServer server = new ClientServer(serverIP, portNumber);
			server.Execute();
		} catch (Exception e) {
			System.err.println("ERRO AO EXECUTAR CLIENTE: " + e.getMessage());
			System.exit(-2);
		}
	}
}
