/**
 * 
 */
package ro.tatacalu.java7concurrency.ch04.recipe03;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class FactorialCalculator implements Callable<Integer> {

    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Integer call() throws Exception {

        int result = 1;

        if ((this.number == 0) || (this.number == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= this.number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
        
        return result;
    }

}
