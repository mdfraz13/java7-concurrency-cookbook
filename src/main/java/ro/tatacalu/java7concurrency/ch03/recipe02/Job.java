/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe02;

/**
 * @author tatacalu
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());

        this.printQueue.printJob(new Object());

        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }

}
