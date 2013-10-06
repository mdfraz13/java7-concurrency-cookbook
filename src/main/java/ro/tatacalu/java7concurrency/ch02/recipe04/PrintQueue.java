/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Matei
 *
 */
public class PrintQueue {
    
    private final Lock queueLock = new ReentrantLock();
    
    public void printJob(Object document) {
        this.queueLock.lock();
        
        try {
            Long duration = (long)(Math.random() * 10_000);
            System.out.println(Thread.currentThread().getName() + ": PrintQueue: Printing a job during " + (duration/1_000) + " seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.queueLock.unlock();
        }
    }
    
}
