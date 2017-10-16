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
import redes.json.DepositResponseMessage;
import redes.json.Message;
import redes.json.WithdrawResponseMessage;

public class ClientProcessor implements Runnable {
    private boolean userIsLogged;
    private RequestStream stream;
    private PasswordManager passwordManager;
    private Scanner scanner;
    private User user;

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
                makeUserLogin();
            else
                createMenuAndGetInputs();
        }
    }

    private void makeUserLogin() {
        User userData = getUserCredentials();
        ActionMessage message = new ActionMessage();

        message.setUser(userData);
        message.setAction(Action.AUTHENTICATE);

        String serverResponse = null;

        try {
            stream.SendToConnection(message.toJson());
            serverResponse = stream.GetInputFromConnection();

            AuthenticationResponseMessage responseMessage = Message.authenticationFromJSON(serverResponse);
            userIsLogged = responseMessage.isUserAuthenticated();
            user = (responseMessage.isUserAuthenticated()) ? userData : null;
        } catch (Exception e) {
            System.out.println("NÃO FOI POSSÍVEL AUTENTICAR USUÁRIO: " + e.getMessage());
        }

        if (userIsLogged)
            System.out.println("Usuário autenticado com êxito.");
        else
            System.out.println("Usuário/senha inválidos.");
    }

    private User getUserCredentials() {
        System.out.println("Favor, insira os dados abaixo para logar no sistema.");

        System.out.println("Numero da conta : ");
        String accountId = scanner.nextLine();

        System.out.println("Senha do cliente: ");
        String password = scanner.nextLine();

        return new User(accountId, passwordManager.EncodePassword(password));
    }

    private void createMenuAndGetInputs() {
        try {
            System.out.println("Selecione a opção desejada:");
            System.out.println("1-deposito;\n2-retirada;\n3-extrato.");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    makeDeposit();
                    break;
                case "2":
                    makeWithdrawl();
                    break;
                case "3":
                    ActionMessage actionMessage = new ActionMessage();
                    break;
                default:
                    System.out.println("Opção não disponível no client.");
            }
            //TODO: AÇÕES DO USUÁRIO AQUI: DEPOSITO, SAQUE E EXTRATO FINANCEIRO.

            stream.GetInputFromConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void makeWithdrawl() {
        System.out.println("Digite o valor da retirada:");
        int withdrawAmount = scanner.nextInt();
        ActionMessage actionMessage = new ActionMessage();
        actionMessage.setValue(withdrawAmount);
        actionMessage.setAction(Action.WITHDRAWL);
        actionMessage.setUser(user);
        try {
            stream.SendToConnection(actionMessage.toJson());
            String serverResponse = stream.GetInputFromConnection();
            WithdrawResponseMessage withdrawResponseMessage = Message.WithDrawlResponseMessageFromJson(serverResponse);
            if (withdrawResponseMessage.isAmountWithdrawDone()) {
                System.out.println("Retirada realizada.");
            } else if (withdrawResponseMessage.isInsuficientAmountError()) {
                System.out.println("O usuário não possui saldo para efetuar a operação.");
            } else {
                System.out.println("Usuário não autorizado a efetuar a operação.");
            }
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    private void makeDeposit() {
        System.out.println("Digite o valor do depósito:");
        int depositAmount = scanner.nextInt();
        ActionMessage actionMessage = new ActionMessage();
        actionMessage.setAction(Action.DEPOSIT);
        actionMessage.setValue(depositAmount);
        actionMessage.setUser(user);
        try {
            stream.SendToConnection(actionMessage.toJson());
            String serverResponse = stream.GetInputFromConnection();
            DepositResponseMessage depositResponseMessage = Message.depositFromJSON(serverResponse);
            if (depositResponseMessage.isAmountDepositDone()) {
                System.out.println("Depósito realizado.");
            } else {
                System.out.println("Usuário não autorizado a efetuar a operação.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}