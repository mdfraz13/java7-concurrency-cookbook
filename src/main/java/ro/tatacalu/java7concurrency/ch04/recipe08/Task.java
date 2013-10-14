/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe08;

import java.util.concurrent.Callable;

/**
 * @author tatacalu
 * 
 */
public class Task implements Callable<String> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        while (true) {
            System.out.printf("Task: Test\n");
            Thread.sleep(100);
        }
    }

}
