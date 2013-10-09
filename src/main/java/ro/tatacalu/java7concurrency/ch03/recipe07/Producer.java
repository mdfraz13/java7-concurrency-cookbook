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
public class Producer implements Runnable {

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

    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
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
        // Implement 10 cycles of interchange
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Producer: Cycle %d\n", cycle);

            for (int j = 0; j < 10; j++) {
                String message = "Event " + ((i * 10) + j);
                System.out.printf("Producer: %s\n", message);
                this.buffer.add(message);
            }

            try {
                // ---------- Exchange data ----------
                buffer = this.exchanger.exchange(this.buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer: " + this.buffer.size());
            cycle++;
        }

    }

}
