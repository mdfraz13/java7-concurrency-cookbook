/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe06;

import ro.tatacalu.java7concurrency.util.TCStringUtils;

/**
 * @author Matei
 *
 */
public class Job implements Runnable {

    private PrintQueue printQueue;
    
    
    
    /**
     * @param printQueue
     */
    public Job(PrintQueue printQueue) {
        super();
        this.printQueue = printQueue;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.printf("%s: Going to print a document%s", Thread.currentThread().getName(), TCStringUtils.NL);
        this.printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed%s", Thread.currentThread().getName(), TCStringUtils.NL);
    }

}
