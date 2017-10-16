package redes.json;

public class WithdrawResponseMessage extends Message{
    private static final long serialVersionUID = 635328382432144197L;
    private boolean amountWithdrawDone;
    private boolean authenticationError;
    private boolean insuficientAmountError;


    public WithdrawResponseMessage createServerWithdrawSuccess(){
        setAmountWithdrawDone(true);
        return this;
    }

    public WithdrawResponseMessage createServerWithdrawAuthenticationFailure(){
        setAmountWithdrawDone(false);
        setAuthenticationError(true);
        return this;
    }

    public WithdrawResponseMessage createServerWithdrawInsuficientAmountFailure(){
        setAmountWithdrawDone(false);
        setInsuficientAmountError(true);
        return this;
    }

    public boolean isAmountWithdrawDone() {
        return amountWithdrawDone;
    }

    public void setAmountWithdrawDone(boolean amountWithdrawDone) {
        this.amountWithdrawDone = amountWithdrawDone;
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    public boolean isInsuficientAmountError() {
        return insuficientAmountError;
    }

    public void setInsuficientAmountError(boolean insuficientAmountError) {
        this.insuficientAmountError = insuficientAmountError;
    }
}
