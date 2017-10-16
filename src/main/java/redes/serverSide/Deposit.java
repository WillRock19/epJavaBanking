package redes.serverSide;

import redes.User;
import redes.json.DepositResponseMessage;

import java.sql.SQLException;

public class Deposit {

    private final dbManager dbManager;
    private final Authenticator authenticator;

    public Deposit(dbManager dbManager,Authenticator authenticator){
        this.dbManager = dbManager;
        this.authenticator = authenticator;
    }

    public DepositResponseMessage deposit(User user, Integer amount) throws SQLException {
        DepositResponseMessage result;
        if(authenticator.wasAlreadyAuthenticated()) {
            dbManager.insertFinancialTransaction(user, amount);
            result = new DepositResponseMessage().createServerDepositSuccess();
        }else{
            result = new DepositResponseMessage().createServerDepositFailure();
        }
        return result;
    }
}
