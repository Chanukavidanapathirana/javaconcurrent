package Bank;

import java.math.BigDecimal;

public class AccountUser extends Thread {
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private BigDecimal amount;
    private int transactionId;

    public AccountUser(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount, int transactionId, String threadName) {
        super(threadName);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    @Override
    public void run() {
        try {
            fromAccount.transfer(toAccount, amount, transactionId);
        } catch (InsufficientBalanceException | InvalidAmountException e) {
            throw new RuntimeException(e);
        }
    }
}
