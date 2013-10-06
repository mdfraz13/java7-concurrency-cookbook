/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe07;

import java.util.Random;

/**
 * @author tatacalu
 * 
 */
public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (buffer.hasPendingLines()) {
            String line = buffer.get();
            this.processLine(line);
        }
    }

    private void processLine(String line) {
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
