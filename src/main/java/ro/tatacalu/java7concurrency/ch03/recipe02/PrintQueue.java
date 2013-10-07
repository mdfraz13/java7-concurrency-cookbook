package ro.tatacalu.java7concurrency.ch03.recipe02;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author tatacalu
 * 
 */
public class PrintQueue {

    private static final int INT_PRINTER_COUNT = 3;

    private final Semaphore  semaphore;

    /**
     * This array stores printers that are free to print a job and printers that
     * are printing a document
     */
    private boolean          freePrinters[];

    /**
     * Protect the access to the freePrinters array
     */
    private Lock             lockPrinters;

    public PrintQueue() {
        this.semaphore = new Semaphore(INT_PRINTER_COUNT);
        this.freePrinters = new boolean[INT_PRINTER_COUNT];

        for (int i = TCNumberUtils.INT_ZERO; i < INT_PRINTER_COUNT; i++) {
            freePrinters[i] = true;
        }

        this.lockPrinters = new ReentrantLock();
    }

    public void printJob(Object document) {

        try {
            this.semaphore.acquire();

            int assignedPrinter = this.getPrinter();

            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer %d during %d seconds\n", Thread.currentThread().getName(), assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration);

            freePrinters[assignedPrinter] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.semaphore.release();
        }

    }

    private int getPrinter() {
        int ret = -1;

        try {
            this.lockPrinters.lock();

            for (int i = TCNumberUtils.INT_ZERO; i < INT_PRINTER_COUNT; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.lockPrinters.unlock();
        }

        return ret;
    }
}
