package ro.tatacalu.java7concurrency.ch02.recipe01;

public class Account {

    private static final long LONG_THREAD_SLEEP_MILIS = 10L;
    
    private double balance;
    
    public synchronized void addAmount(double amount) {
        double tmp = this.balance;
        
        try {
            Thread.sleep(LONG_THREAD_SLEEP_MILIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        tmp += amount;
        this.balance = tmp;
    }
    
    public synchronized void substractAmount(double amount) {
        double tmp = this.balance;
        
        try {
            Thread.sleep(LONG_THREAD_SLEEP_MILIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        tmp -= amount;
        this.balance = tmp;
    }
    
    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}
