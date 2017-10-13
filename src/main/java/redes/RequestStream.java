package redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestStream {
	private BufferedReader inputFromConnection;
	private DataOutputStream outputToConnection;
	private Socket socket;

	public RequestStream(Socket socket) {
		defineInputStream(socket);
		defineOutputStream(socket);
		this.socket = socket;
	}

	public String GetInputFromConnection() throws IOException
	{	
		return inputFromConnection.readLine();
	}

	public void SendToConnection(String message) throws Exception {
		outputToConnection.writeBytes(message + "\r\n");
	}

	public void CloseOutputAndInputFromServerStream() {
		try {
			outputToConnection.close();
			inputFromConnection.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("ERRO AO FECHAR SOCKET: " + e.getMessage());
		}
	}

	private void defineInputStream(Socket socket) {
		try {
			inputFromConnection = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para receber dados do socket TCP.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}

	private void defineOutputStream(Socket socket) {
		try {
			outputToConnection = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("--------------ERRO--------------");
			System.err.println("Nao foi possivel criar o stream para enviar dados do socket TCP.");
			System.err.println("O seguinte erro ocorreu: " + e.getMessage());
		}
	}
}
