/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe01;

import ro.tatacalu.java7concurrency.util.TCStringUtils;

/**
 * @author Matei
 *
 */
public class Main {
    
    private static final double DOUBLE_INITIAL_AMOUNT = 1_000D;
    private static final String STRING_FORMAT_INITIAL_BALANCE = "Account : Initial Balance: %f" + TCStringUtils.NL;
    private static final String STRING_FORMAT_FINAL_BALANCE = "Account : Final Balance: %f" + TCStringUtils.NL;
    
    public static void main(String[] args) {
        Account account = new Account();
        account.setBalance(DOUBLE_INITIAL_AMOUNT);
        
        Company company = new Company(account);
        Thread companyThread = new Thread(company);
        
        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);
        
        System.out.printf(STRING_FORMAT_INITIAL_BALANCE, account.getBalance());
        
        // start the threads
        companyThread.start();
        bankThread.start();
        
        // wait for both of the threads to finish
        try {
            companyThread.join();
            bankThread.join();
            
            System.out.printf(STRING_FORMAT_FINAL_BALANCE, account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}
