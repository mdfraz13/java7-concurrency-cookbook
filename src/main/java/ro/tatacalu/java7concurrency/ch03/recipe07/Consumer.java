/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe07;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @author tatacalu
 * 
 */
public class Consumer implements Runnable {

    /**
     * This will be the data structure that the producer will interchange with
     * the consumer.
     */
    private List<String>                  buffer;

    /**
     * This will be the exchanger object that will be used to synchronize
     * producer and consumer.
     */
    private final Exchanger<List<String>> exchanger;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // 10 cycles of interchange
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);
            
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("Consumer: " + buffer.size());
            
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer: " + message);
                buffer.remove(0);
            }
            cycle++;
        }
    }

}
