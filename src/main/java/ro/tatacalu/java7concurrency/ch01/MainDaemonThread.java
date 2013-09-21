/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.ArrayDeque;
import java.util.Deque;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class MainDaemonThread {

    private static final int INT_NR_WRITER_THREADS = 3;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Deque<Event> deque = new ArrayDeque<>();
        
        WriterTask writer = new WriterTask(deque);
        for (int i = TCNumberUtils.INT_ZERO; i < INT_NR_WRITER_THREADS; i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }
        
        CleanerTask cleanerTask = new CleanerTask(deque);
        cleanerTask.start();
    }

}
