package redes.serverSide;

import redes.User;
import redes.json.FinancialTransactionsResponseMessage;

import java.sql.SQLException;
import java.util.List;

public class FinancialTransactions {
    private final Authenticator authenticator;
    private final dbManager dbManager;

    public FinancialTransactions(Authenticator authenticator, dbManager dbManager) {
        this.authenticator = authenticator;
        this.dbManager = dbManager;
    }

    public FinancialTransactionsResponseMessage getStatements(User user) throws SQLException {
        FinancialTransactionsResponseMessage result;
        if(authenticator.wasAlreadyAuthenticated()){
            List<Double> financialItems = dbManager.listFinancialTransaction(user);
            result = new FinancialTransactionsResponseMessage().createServerStatementsSuccess(financialItems);
        }else{
            result = new FinancialTransactionsResponseMessage().createServerStatementsFailure();
        }
        return result;
    }
}
