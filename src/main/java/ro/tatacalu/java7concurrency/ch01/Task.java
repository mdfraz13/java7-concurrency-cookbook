/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class Task implements Runnable {

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        @SuppressWarnings("unused")
        int number = Integer.parseInt("This will throw an unchecked exception");
    }

}
