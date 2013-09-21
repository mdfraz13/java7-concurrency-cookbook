/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ro.tatacalu.java7concurrency.ch01.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class FileClock implements Runnable {

    private static final int I_END = 10; 
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            System.out.printf("%s\n", new Date());
            
            try {
                TimeUnit.SECONDS.sleep(TCNumberUtils.LONG_ONE);
            } catch (InterruptedException ie) {
                System.out.println("The FileClock has been interrupted");
            }
        }
    }

}
