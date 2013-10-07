package ro.tatacalu.java7concurrency.ch03.recipe01;

import java.util.concurrent.Semaphore;

/**
 * @author tatacalu
 * 
 */
public class PrintQueue {

    private final Semaphore semaphore;

    public PrintQueue() {
        this.semaphore = new Semaphore(1);
    }

    public void printJob(Object document) {

        try {
            this.semaphore.acquire();

            long duration = (long) (Math.random() * 10_000);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), duration / 1000);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.semaphore.release();
        }

    }
}
