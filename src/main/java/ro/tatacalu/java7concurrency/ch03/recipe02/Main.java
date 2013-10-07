/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe02;

/**
 * @author tatacalu
 * 
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();

        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }

}
