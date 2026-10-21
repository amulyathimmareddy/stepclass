package week10.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract account base class defining balance state and withdrawal abstraction.
 */
abstract class Account {
    protected String id;
    protected int balance;

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public abstract String withdraw(int amount);
}

/**
 * Savings account maintaining minimum balance threshold of 1000.
 */
class SavingsAccount extends Account {
    private static final int MINIMUM_BALANCE = 1000;

    public SavingsAccount(String id, int balance) {
        super(id, balance);
    }

    @Override
    public String withdraw(int amount) {
        if (balance - amount < MINIMUM_BALANCE) {
            return String.format("%s rejected: minimum balance %d", id, MINIMUM_BALANCE);
        }
        balance -= amount;
        return String.format("%s balance %d", id, balance);
    }
}

/**
 * Current account supporting overdraft limit of 5000 (minimum allowed balance -5000).
 */
class CurrentAccount extends Account {
    private static final int OVERDRAFT_LIMIT = 5000;

    public CurrentAccount(String id, int balance) {
        super(id, balance);
    }

    @Override
    public String withdraw(int amount) {
        if (balance - amount < -OVERDRAFT_LIMIT) {
            return String.format("%s rejected: overdraft limit %d", id, OVERDRAFT_LIMIT);
        }
        balance -= amount;
        return String.format("%s balance %d", id, balance);
    }
}

/**
 * Problem 1: Bank Account Withdrawal System
 * Models account withdrawal rules using polymorphism and minimum/overdraft balance constraints.
 */
public class BankAccountWithdrawalSystem {

    private Map<String, Account> accounts = new HashMap<>();

    public void processOperation(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            return;
        }

        String[] parts = operation.trim().split("\\s+");
        String command = parts[0];

        switch (command) {
            case "Savings":
                if (parts.length >= 3) {
                    accounts.put(parts[1], new SavingsAccount(parts[1], Integer.parseInt(parts[2])));
                }
                break;

            case "Current":
                if (parts.length >= 3) {
                    accounts.put(parts[1], new CurrentAccount(parts[1], Integer.parseInt(parts[2])));
                }
                break;

            case "WITHDRAW":
                if (parts.length >= 3) {
                    String id = parts[1];
                    int amount = Integer.parseInt(parts[2]);
                    Account acc = accounts.get(id);
                    if (acc != null) {
                        System.out.println(acc.withdraw(amount));
                    } else {
                        System.out.println("Account not found");
                    }
                }
                break;

            default:
                break;
        }
    }

    public static void main(String[] args) {
        try {
            BankAccountWithdrawalSystem system = new BankAccountWithdrawalSystem();

            String[] inputOperations = {
                    "Savings S1 5000",
                    "WITHDRAW S1 4500",
                    "Current C1 2000",
                    "WITHDRAW C1 6000",
                    "WITHDRAW S1 3000",
                    "WITHDRAW C1 2000"
            };

            for (String op : inputOperations) {
                system.processOperation(op);
            }
        } catch (Exception e) {
            System.err.println("Error running bank account withdrawal system: " + e.getMessage());
        }
    }
}
