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
public class FactoryTask implements Runnable {

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(TCNumberUtils.LONG_ONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
