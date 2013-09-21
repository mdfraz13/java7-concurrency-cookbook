/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 * 
 */
public class WriterTask implements Runnable {

    private static final int I_END = 100;
    private static final String STRING_EVENT_DESCRIPTION_FORMAT = "The thread %s has generated an event";
    
    private Deque<Event>     deque;

    public WriterTask(Deque<Event> deque) {
        this.deque = deque;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = TCNumberUtils.INT_ONE; i < I_END; i++) {
            String eventDescription = String.format(STRING_EVENT_DESCRIPTION_FORMAT, Thread.currentThread().getId());
            Event event = new Event(new Date(), eventDescription);
            this.deque.addFirst(event);
            
            try {
                TimeUnit.SECONDS.sleep(TCNumberUtils.LONG_ONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
