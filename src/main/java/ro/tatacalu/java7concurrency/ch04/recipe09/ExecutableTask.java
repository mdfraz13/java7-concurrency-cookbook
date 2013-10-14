/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe09;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class ExecutableTask implements Callable<String> {

    private String name;

    public String getName() {
        return name;
    }

    public ExecutableTask(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
        }
        return "Hello, world. I'm " + name;
    }
}
