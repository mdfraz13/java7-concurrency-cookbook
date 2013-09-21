/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.concurrent.TimeUnit;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class MainUnsafeTask {

    private static final int I_END = 10;
    private static final long LONG_SLEEP_INTERVAL = 2L;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        UnsafeTask unsafeTask = new UnsafeTask();
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            Thread thread = new Thread(unsafeTask);
            thread.start();
            
            try {
                TimeUnit.SECONDS.sleep(LONG_SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

}
