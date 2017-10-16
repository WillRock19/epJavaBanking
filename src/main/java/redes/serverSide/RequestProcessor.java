package redes.serverSide;

import java.net.Socket;

import redes.ActionMessage;
import redes.RequestStream;
import redes.json.*;

public class RequestProcessor implements Runnable {

    private RequestStream stream;
    private dbManager dbManager;
    private Authenticator authenticator;
    private Deposit deposit;
    private Withdrawl withdrawl;

    public RequestProcessor(Socket socket, dbManager dbManager) {
        this.dbManager = dbManager;
        this.stream = new RequestStream(socket);

        authenticator = new Authenticator(this.dbManager);
        deposit = new Deposit(dbManager, authenticator);
        withdrawl = new Withdrawl(dbManager, authenticator);
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
                    DepositResponseMessage depositResponse = this.deposit.deposit(message.getUser(), message.getValue());
                    stream.SendToConnection(depositResponse.toJson());
                    break;

                case WITHDRAWL:
                    WithdrawResponseMessage withdrawResponse = this.withdrawl.withdraw(message.getUser(), message.getValue());
                    stream.SendToConnection(withdrawResponse.toJson());
                    break;

                case FINANCIAL_TRANSACTIONS:
                    FinancialTransactions financialTransactions = new FinancialTransactions(authenticator,dbManager);
                    FinancialTransactionsResponseMessage financialTransactionsResponse = financialTransactions.getStatements(message.getUser());
                    stream.SendToConnection(financialTransactionsResponse.toJson());
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
