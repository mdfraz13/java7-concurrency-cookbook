/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class MainThreadInterruption {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Thread primeGeneratorThread = new PrimeGenerator();
        primeGeneratorThread.start();
        
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
        primeGeneratorThread.interrupt();
    }

}
