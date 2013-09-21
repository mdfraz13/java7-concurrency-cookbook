/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Matei
 *
 */
public class SafeTask implements Runnable {

    private static final String STRING_THREAD_START = "Starting Thread: %s : %s\n";
    private static final String STRING_THREAD_END = "Thread Finished: %s : %s\n";
    
    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>(){
        
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.printf(STRING_THREAD_START, Thread.currentThread().getId(), startDate.get());
        
        long sleepTime = (long) Math.rint(Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.printf(STRING_THREAD_END, Thread.currentThread().getId(), startDate.get());
    }

}
