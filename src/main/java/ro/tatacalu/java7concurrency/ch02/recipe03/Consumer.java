/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe03;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class Consumer implements Runnable {

    private static final int I_END = 100;
    
    private EventStorage eventStorage;
    
    public Consumer(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            this.eventStorage.get();
        }
    }

}
