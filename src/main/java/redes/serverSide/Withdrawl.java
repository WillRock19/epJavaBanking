package redes.serverSide;

import redes.User;
import redes.json.WithdrawResponseMessage;

import java.sql.SQLException;

public class Withdrawl {

    private final Authenticator authenticator;
    private final dbManager dbManager;

    public Withdrawl(dbManager dbManager, Authenticator authenticator) {
        this.dbManager = dbManager;
        this.authenticator = authenticator;
    }

    public WithdrawResponseMessage withdraw(User user, Integer amount) throws SQLException {
        WithdrawResponseMessage result;
        if(authenticator.wasAlreadyAuthenticated()){
            if(dbManager.getSaldoUsuario(user) >= amount) {
                dbManager.insertFinancialTransaction(user, -amount);
                result = new WithdrawResponseMessage().createServerWithdrawSuccess();
            }else{
                result = new WithdrawResponseMessage().createServerWithdrawInsuficientAmountFailure();
            }
        }else{
            result = new WithdrawResponseMessage().createServerWithdrawAuthenticationFailure();
        }
        return result;
    }
}
