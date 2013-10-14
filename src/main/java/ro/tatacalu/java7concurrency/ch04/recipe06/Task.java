/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe06;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author tatacalu
 * 
 */
public class Task implements Callable<String> {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public String call() throws Exception {
        System.out.printf("%s: Starting at : %s\n", name, new Date());
        return "Hello, world";
    }

}
