/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe01;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class Bank implements Runnable {

    private static final int I_END = 100;
    private static final double DOUBLE_AMOUNT_TO_SUBSTRACT = 1_000D;
    
    private Account account;
    
    public Bank(Account account) {
        this.account = account;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            this.account.substractAmount(DOUBLE_AMOUNT_TO_SUBSTRACT);
        }

    }

}
