/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class Calculator implements Runnable {

    private static final int I_START = 1;
    private static final int I_END = 10;
    
    private static final String LINE_SEPARATOR = System.lineSeparator();
    
    private int number;
    
    public Calculator (int number) {
        this.number = number;
    }
    
    /**
     * Calculate the multiplication table of the number.
     */
    @Override
    public void run() {
        for (int i = I_START; i <= I_END; i++) {
            System.out.printf("%s: %d * %d = %d%s", Thread.currentThread().getName(), this.number, i, this.number * i, LINE_SEPARATOR);
        }
    }

}
