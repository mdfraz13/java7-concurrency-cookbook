/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ro.tatacalu.java7concurrency.ch01.util.TCNumberUtils;

/**
 * @author Matei
 * 
 */
public class SearchTask implements Runnable {
    
    private static final String STRING_FORMAT_THREAD_START = "Thread %s: Start\n";
    private static final String STRING_FORMAT_THREAD_INTERRUPTED = "Thread %s: Interrupted\n";
    private static final String STRING_FORMAT_THREAD_END = "Thread %s: End\n";
    private static final String STRING_FORMAT_THREAD_NAME = "Thread %s: %d\n";
    
    private Result result;

    public SearchTask(Result result) {
        this.result = result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.printf(STRING_FORMAT_THREAD_START, currentThreadName);
        
        try {
            this.doTask();
            this.result.setName(currentThreadName);
        } catch (InterruptedException e) {
            System.out.printf(STRING_FORMAT_THREAD_INTERRUPTED, currentThreadName);
            return;
        }
        
        System.out.printf(STRING_FORMAT_THREAD_END, currentThreadName);
    }

    /**
     * Prints out the current thread name and sleeps a random period of time.
     * @throws InterruptedException
     */
    private void doTask() throws InterruptedException {
        Random random = new Random((new Date()).getTime());
        long randomLong = (long) (random.nextDouble() * TCNumberUtils.LONG_ONE_HUNDRED);
        
        System.out.printf(STRING_FORMAT_THREAD_NAME, Thread.currentThread().getName(), randomLong);
        
        TimeUnit.SECONDS.sleep(randomLong);
    }
}
