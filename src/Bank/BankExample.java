package Bank;

import java.math.BigDecimal;

public class BankExample {

    public static void main(String[] args) {

        BankAccount accountA = new BankAccount("1000", new BigDecimal(10000));
        BankAccount accountB = new BankAccount("2000", new BigDecimal(15000));
        BankAccount accountC = new BankAccount("1101", new BigDecimal(20000));

        AccountUser thread1 = new AccountUser(accountA, accountB, new BigDecimal(2000), 1, "Transfer 1");
        AccountUser thread2 = new AccountUser(accountB, accountC, new BigDecimal(5000), 2, "Transfer 2");
        AccountUser thread3 = new AccountUser(accountC, accountA, new BigDecimal(5000), 3, "Transfer 3");

        AccountUser thread4 = new AccountUser(accountA, accountB, new BigDecimal(1000), 4, "Transfer 4");
        AccountUser thread5 = new AccountUser(accountB, accountA, new BigDecimal(2000), 5, "Transfer 5");
        Thread thread6 = new Thread(() -> {
            System.out.println("After Transfer");
            System.out.println("Account A - Balance: " + accountA.getBalance());
            System.out.println("Account B - Balance: " + accountB.getBalance());
            System.out.println("Account C - Balance: " + accountC.getBalance());
        });

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread4.start();
        thread5.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread6.start();
    }
}

