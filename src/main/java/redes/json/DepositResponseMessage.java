package redes.json;

public class DepositResponseMessage extends Message {

    private static final long serialVersionUID = 8638038545024270384L;
    private boolean amountDepositDone;

    public DepositResponseMessage createServerDepositSuccess(){
        setAmountDepositDone(true);
        return this;
    }

    public DepositResponseMessage createServerDepositFailure(){
        setAmountDepositDone(false);
        return this;
    }

    public boolean isAmountDepositDone() {
        return amountDepositDone;
    }

    public void setAmountDepositDone(boolean amountDepositDone) {
        this.amountDepositDone = amountDepositDone;
    }
}
