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
public class MainThreadGroup {

    private static final int I_END = 10;
    private static final long LONG_SLEEP_INTERVAL = 1L;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
        
        Result result = new Result();
        SearchTask searchTask = new SearchTask(result);
        
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            
            try {
                TimeUnit.SECONDS.sleep(LONG_SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.printf("Number of threads: %d\n", threadGroup.activeCount());
        System.out.printf("Information about the ThreadGroup:");
        threadGroup.list();
        
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s : %s\n", threads[i].getName(), threads[i].getState());
        }
        
        waitFinish(threadGroup);
        
        threadGroup.interrupt();
    }
    
    /**
     * Waits for ONE of the threads in the ThreadGroup to finish.
     * 
     * @param threadGroup The input ThreadGroup.
     */
    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > (I_END - 1)) {
            try {
                TimeUnit.SECONDS.sleep(TCNumberUtils.LONG_ONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
