package Bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);   // This is to insure the fairness
    // When threads are trying to access it will be letting on First come first serve basis

    private Lock rLock = rwLock.readLock();
    private Lock wLock = rwLock.writeLock();
    private List<String> transactions = new ArrayList<>();
    private String accountNumber;
    private BigDecimal balance;


    public BankAccount(String accountNumber, BigDecimal initialBalance) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        rLock.lock();
        try {
            return balance;
        } finally {
            rLock.unlock();
        }
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public List<String> getTransactionsHistory() {
        rLock.lock();
        try {
            return this.transactions;
        } finally {
            rLock.unlock();
        }
    }

    public  void withdraw(BigDecimal amount, int transactionId) throws InsufficientBalanceException {
        wLock.lock();
        try {
            if (amount.doubleValue() > 0 && getBalance().doubleValue() >= amount.doubleValue()) {
                this.balance = getBalance().subtract(amount);
                transactions.add(Thread.currentThread().getName() + " withdraw " + transactionId + " Amount " + amount);
            } else {
                throw new InsufficientBalanceException("Insufficient Money in the account");
            }
        } finally {
            wLock.unlock();
        }
    }

    public void deposit(BigDecimal amount, int transactionId) throws InvalidAmountException {
        wLock.lock();
        try {
            if(amount.doubleValue() > 0) {
                this.balance = this.balance.add(amount);
                transactions.add(Thread.currentThread().getName() + " deposit " + transactionId + " Amount " + amount);
            }
            else {
                throw new InvalidAmountException("The amount cannot be less than 0");
            }
        } finally {
            wLock.unlock();
        }
    }

    public void transfer(BankAccount toAccount, BigDecimal amount, int transactionId)
            throws InsufficientBalanceException, InvalidAmountException {
        BankAccount firstLock, secondLock;

        // This locking order use to prevent deadlocks
        if (this.getAccountNumber().hashCode() > toAccount.getAccountNumber().hashCode()) {
            firstLock = this;
            secondLock = toAccount;
        } else {
            firstLock = toAccount;
            secondLock = this;
        }

        firstLock.wLock.lock();
        try {
            secondLock.wLock.lock();
            try {
                try {
                    this.withdraw(amount, transactionId);
                    toAccount.deposit(amount, transactionId);
                } catch (Exception e) {
                    this.deposit(amount, transactionId); // Undo withdrawal
                    throw e; // Re-throw exception to indicate failure
                }
                //return true;
            } finally {
                secondLock.wLock.unlock();
            }
        } finally {
            firstLock.wLock.unlock();
        }
    }

}

