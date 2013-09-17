/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

/**
 * @author Matei
 *
 */
public class Main {
    
    private static final int I_START = 1;
    private static final int I_END = 10;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = I_START; i <= I_END; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }

}
