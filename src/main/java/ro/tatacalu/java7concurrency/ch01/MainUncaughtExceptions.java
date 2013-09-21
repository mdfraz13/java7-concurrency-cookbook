/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class MainUncaughtExceptions {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }

}
