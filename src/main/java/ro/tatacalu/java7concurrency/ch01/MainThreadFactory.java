/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import ro.tatacalu.java7concurrency.ch01.util.TCNumberUtils;

/**
 * @author Matei
 *
 */
public class MainThreadFactory {

    private static final int I_END = 10;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        MyThreadFactory myThreadFactory = new MyThreadFactory("This book is a piece of shit !");
        FactoryTask task = new FactoryTask();
        
        System.out.println("Starting the threads...");
        for (int i = TCNumberUtils.INT_ZERO; i < I_END; i++) {
            Thread thread = myThreadFactory.newThread(task);
            thread.start();
        }
        
        System.out.println("Factory statistics:");
        System.out.println(myThreadFactory.getStatistics());
    }

}
