/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe07;

import java.util.Date;

/**
 * @author tatacalu
 * 
 */
public class Task implements Runnable {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Starting at : %s\n", name, new Date());
    }

}
