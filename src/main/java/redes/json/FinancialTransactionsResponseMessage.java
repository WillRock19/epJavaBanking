package redes.json;

import java.util.List;

public class FinancialTransactionsResponseMessage extends Message{
    private static final long serialVersionUID = -3097866607500246532L;
    private List<Double> financialItems;
    private boolean financialTransactionDone;

    public FinancialTransactionsResponseMessage createServerStatementsSuccess(List<Double> financialItems) {
        this.financialItems = financialItems;
        return this;
    }

    public FinancialTransactionsResponseMessage createServerStatementsFailure() {
        return this;
    }

    public List<Double> getFinancialItems() {
        return financialItems;
    }

    public void setFinancialItems(List<Double> financialItems) {
        this.financialItems = financialItems;
    }

    public boolean isFinancialTransactionDone() {
        return financialTransactionDone;
    }

    public void setFinancialTransactionDone(boolean financialTransactionDone) {
        this.financialTransactionDone = financialTransactionDone;
    }
}
