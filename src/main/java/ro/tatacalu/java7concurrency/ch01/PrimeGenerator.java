/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class PrimeGenerator extends Thread {

    private static long LONG_ZERO = 0L;
    private static long LONG_ONE = 1L;
    private static long LONG_TWO = 2L;
    
    
    @Override
    public void run() {
        long number = LONG_ONE;
        
        while (true) {
            // check if the number is prime
            if (this.isPrime(number)) {
                System.out.printf("Number %d is Prime\n", number);
            }
            
            // check if the current thread is interrupted
            if (this.isInterrupted()) {
                System.out.println("The Prime Number Generator has been interrupted");
                
                // !!!
                return;
            }
            
            number++;
        }
    }
    
    /**
     * [Captain Obvious] Check whether a number is prime.
     * 
     * @param number the number to check.
     * @return true if the number is prime, false otherwise
     */
    private boolean isPrime(long number) {
        
        if (number <= LONG_TWO) {
            return true;
        }
        
        for (long i = LONG_TWO; i < number; i++) {
            if ((number % LONG_TWO) == LONG_ZERO) {
                return false;
            }
        }
        
        return true;
    }
}
