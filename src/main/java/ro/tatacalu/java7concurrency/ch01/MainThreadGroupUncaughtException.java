/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 * 
 */
public class MainThreadGroupUncaughtException {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyThreadGroup threadGroup = new MyThreadGroup("MyThreadGroup");
        ThreadGroupExceptionTask task = new ThreadGroupExceptionTask();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(threadGroup, task);
            t.start();
        }
    }

}
