/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author Matei
 *
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

    /* (non-Javadoc)
     * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s\n", t.getId());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace: \n");
        e.printStackTrace(System.out);
        System.out.printf("Thread state: %s\n", t.getState());
        
    }

}
