/**
 * This class represents a bank account whose current balance is a nonnegative
 * amount in US dollars.
 */
public class Account {

    private int balance;
    private Account parentAccount;

    /** Initialize an account with the given balance. */
    public Account(int balance) {
        this.balance = balance;
        parentAccount = null;
    }

    public Account(int balance, Account parentAccount){
        this.balance = balance;
        this.parentAccount = parentAccount;
    }

    /** Returns the balance for the current account. */
    public int getBalance() {
        return balance;
    }

    /** Deposits amount into the current account. */
    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Cannot deposit negative amount.");
        } else {
            balance += amount;
        }
    }

    /**
     * Subtract amount from the account if possible. If subtracting amount
     * would leave a negative balance, print an error message and leave the
     * balance unchanged.
     */
    public boolean withdraw(int amount) {
        if (amount < 0) {
            System.out.println("Cannot withdraw negative amount.");
            return false;
        } else if (balance < amount && parentAccount == null) {
            // balance insufficient, no parentAccount
            System.out.println("Insufficient funds");
            return false;
        } else if (balance < amount){
            // balance insufficient, but has parentAccount
            int overdraft = amount - balance;
            if(parentAccount.withdraw(overdraft)){
                // parentAccount can be overdrafted
                // clear this balance
                balance = 0;
                return true;
            } else {
                // parentAccount balance insufficient
                System.out.println("Insufficient funds for overdraft");
                return false;
            }
        } else {
            // balance sufficient
            balance -= amount;
            return true;
        }
    }

    /**
     * Merge account other into this account by removing all money from other
     * and depositing it into this account.
     */
    public void merge(Account other) {
        // cannot visit other.balance directly.
        int otherBalance = other.getBalance();
        balance += otherBalance;
        other.withdraw(otherBalance);
    }
}
