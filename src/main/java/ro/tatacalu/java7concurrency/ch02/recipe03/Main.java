/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe03;

/**
 * @author Matei
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        EventStorage eventStorage = new EventStorage();
        
        Producer producer = new Producer(eventStorage);
        Thread thread1 = new Thread(producer);
        
        Consumer consumer = new Consumer(eventStorage);
        Thread thread2 = new Thread(consumer);
        
        // not sure if the order swap was intended or not by the book author
        thread2.start();
        thread1.start();
    }

}
