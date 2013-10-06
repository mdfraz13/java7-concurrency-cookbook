/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe05;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 * 
 */
public class Writer implements Runnable {

    private static final int ITERATIONS = 3;
    private static final double MULTIPLIER1 = 10D;
    private static final double MULTIPLIER2 = 8D;
    private static final long SLEEP_DURATION = 2L;
    
    private PricesInfo pricesInfo;

    public Writer(PricesInfo pricesInfo) {
        this.pricesInfo = pricesInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = TCNumberUtils.INT_ZERO; i < ITERATIONS; i++) {
            System.out.println("Writer: Attempt to modify the prices.");
            this.pricesInfo.setPrices(Math.random() * MULTIPLIER1, Math.random() * MULTIPLIER2);
            System.out.println("Writer: Prices have been modified.");
            
            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
