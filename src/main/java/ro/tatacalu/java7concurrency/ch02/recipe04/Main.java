/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe04;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class Main {

    private static final int NR_THREADS = 10;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        
        Thread thread[] = new Thread[NR_THREADS];
        for (int i = TCNumberUtils.INT_ONE; i < NR_THREADS; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread " + i);
        }
        
        for (int i = TCNumberUtils.INT_ONE; i < NR_THREADS; i++) {
            thread[i].start();
        }
    }

}
