/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Task task = new Task();

        System.out.printf("Main: Executing the Task\n");
        Future<String> result = executor.submit(task);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Canceling the Task\n");
        result.cancel(true);

        System.out.printf("Main: Canceled: %s\n", result.isCancelled());
        System.out.printf("Main: Done: %s\n", result.isDone());

        executor.shutdown();
        System.out.printf("Main: The executor has finished\n");
    }

}
